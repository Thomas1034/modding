package com.thomas.verdant;


import com.thomas.verdant.data.*;
import com.thomas.verdant.registry.Flammables;
import com.thomas.verdant.registry.WoodSets;
import com.thomas.verdant.util.blocktransformer.BlockTransformer;
import com.thomas.verdant.util.featureset.FeatureSet;
import com.thomas.verdant.woodset.WoodSet;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.WritableRegistry;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FireBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.ItemAbility;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.event.BlockEntityTypeAddBlocksEvent;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.neoforged.neoforge.registries.DataPackRegistryEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Mod(Constants.MOD_ID)
public class Verdant {

    public Verdant(final IEventBus eventBus) {
        // This method is invoked by the NeoForge mod loader when it is ready
        // to load your mod. You can access NeoForge and Common code in this
        // project.

        // Use NeoForge to bootstrap the Common mod.
        Constants.LOG.info("Hello NeoForge world!");
        CommonClass.init();

        eventBus.addListener(Verdant::registerDatapackRegistries);
        eventBus.addListener(Verdant::gatherData);
        // For wood sets.
        eventBus.addListener(Verdant::addBlocksToBlockEntities);
        eventBus.addListener(Verdant::onFinishSetup);
        NeoForge.EVENT_BUS.addListener(Verdant::registerStrippingLogs);
    }

    public static void registerFlammability(Block block, int flammability, int spreadSpeed) {
        ((FireBlock) Blocks.FIRE).setFlammable(block, flammability, spreadSpeed);
    }



    public static void onFinishSetup(final FMLCommonSetupEvent event) {
        Flammables.init(((FireBlock) Blocks.FIRE)::setFlammable);
        for (WoodSet woodSet : WoodSets.WOOD_SETS) {
            woodSet.registerFlammability(((FireBlock) Blocks.FIRE)::setFlammable);
        }
    }

    public static void registerStrippingLogs(final BlockEvent.BlockToolModificationEvent event) {
        ItemStack stack = event.getHeldItemStack();
        ItemAbility ability = event.getItemAbility();
        // If it isn't stripping, or if it can't do the action, return.
        if (ability != ItemAbilities.AXE_STRIP || !stack.canPerformAction(ability)) {
            return;
        }
        BlockState state = event.getState();
        BlockState finalState = null;
        // Check if this is a strippable log from a wood set.
        // If so, set the result and return.
        for (WoodSet woodSet : WoodSets.WOOD_SETS) {
            if (state.is(woodSet.getLog().get())) {
                finalState = woodSet.getStrippedLog().get().defaultBlockState();
            } else if (state.is(woodSet.getWood().get())) {
                finalState = woodSet.getStrippedWood().get().defaultBlockState().setValue(BlockStateProperties.AXIS, state.getValue(BlockStateProperties.AXIS));
            }
            if (finalState != null) {
                finalState = finalState.setValue(BlockStateProperties.AXIS, state.getValue(BlockStateProperties.AXIS));
                break;
            }
        }
        // If the final state was changed, set it.
        if (finalState != null) {
            event.setFinalState(finalState);
        }
    }


    public static void addBlocksToBlockEntities(final BlockEntityTypeAddBlocksEvent event) {
        for (WoodSet woodSet : WoodSets.WOOD_SETS) {
            event.modify(BlockEntityType.SIGN, woodSet.getSign().get(), woodSet.getWallSign().get());
            event.modify(BlockEntityType.HANGING_SIGN, woodSet.getHangingSign().get(), woodSet.getWallHangingSign().get());
        }
    }

    public static void gatherData(final GatherDataEvent event) {
        try {
            DataGenerator generator = event.getGenerator();
            PackOutput packOutput = generator.getPackOutput();
            ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
            CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

            generator.addProvider(event.includeServer(), new LootTableProvider(packOutput, Collections.emptySet(), List.of(new LootTableProvider.SubProviderEntry(VerdantBlockLootTableProvider::new, LootContextParamSets.BLOCK)), lookupProvider) {
                @Override
                protected void validate(@NotNull WritableRegistry<LootTable> writableregistry, @NotNull ValidationContext context, ProblemReporter.Collector collector) {
                    // Do not validate at all, per what people online said.
                }
            });

            generator.addProvider(event.includeClient(), new VerdantRecipeProvider.Runner(packOutput, event.getLookupProvider()));

            BlockTagsProvider blockTagsProvider = new VerdantBlockTagProvider(packOutput, lookupProvider, existingFileHelper);
            generator.addProvider(event.includeServer(), blockTagsProvider);
            generator.addProvider(event.includeServer(), new VerdantItemTagProvider(packOutput, lookupProvider, blockTagsProvider.contentsGetter(), existingFileHelper));
            generator.addProvider(event.includeClient(), new VerdantBlockStateProvider(packOutput, existingFileHelper));
            generator.addProvider(event.includeClient(), new VerdantItemModelProvider(packOutput, existingFileHelper));


            generator.addProvider(true, new VerdantBlockTransformerProvider(packOutput, lookupProvider));
            generator.addProvider(true, new VerdantFeatureSetProvider(packOutput, lookupProvider));
            generator.addProvider(true, new VerdantDataMapProvider(packOutput, lookupProvider));

        } catch (RuntimeException e) {
            Constants.LOG.error("Failed to generate data.", e);
        }
    }

    public static void registerDatapackRegistries(final DataPackRegistryEvent.NewRegistry event) {
        // System.out.println(BlockTransformer.CODEC);
        event.dataPackRegistry(BlockTransformer.KEY, BlockTransformer.CODEC, BlockTransformer.CODEC);

        event.dataPackRegistry(FeatureSet.KEY, FeatureSet.CODEC, FeatureSet.CODEC);
    }
}