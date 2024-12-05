package com.thomas.verdant;


import com.thomas.verdant.data.*;
import com.thomas.verdant.registry.properties.WoodSets;
import com.thomas.verdant.util.blocktransformer.BlockTransformer;
import com.thomas.verdant.util.featureset.FeatureSet;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.WritableRegistry;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.event.BlockEntityTypeAddBlocksEvent;
import net.neoforged.neoforge.registries.DataPackRegistryEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Mod(Constants.MOD_ID)
public class Verdant {

    public Verdant(IEventBus eventBus) {

        // This method is invoked by the NeoForge mod loader when it is ready
        // to load your mod. You can access NeoForge and Common code in this
        // project.

        // Use NeoForge to bootstrap the Common mod.
        Constants.LOG.info("Hello NeoForge world!");
        CommonClass.init();

        eventBus.addListener(Verdant::registerDatapackRegistries);
        eventBus.addListener(Verdant::gatherData);
        eventBus.addListener(Verdant::addBlocksToBlockEntities);
    }


    public static void addBlocksToBlockEntities(BlockEntityTypeAddBlocksEvent event) {
        event.modify(BlockEntityType.SIGN, WoodSets.STRANGLER.getSign().get(), WoodSets.STRANGLER.getWallSign().get());
        event.modify(BlockEntityType.HANGING_SIGN, WoodSets.STRANGLER.getHangingSign().get(), WoodSets.STRANGLER.getWallHangingSign().get());
    }

    public static void gatherData(GatherDataEvent event) {
        try {
            DataGenerator generator = event.getGenerator();
            PackOutput packOutput = generator.getPackOutput();
            ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
            CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

            generator.addProvider(event.includeServer(), new LootTableProvider(packOutput, Collections.emptySet(),
                    List.of(new LootTableProvider.SubProviderEntry(VerdantBlockLootTableProvider::new, LootContextParamSets.BLOCK)), lookupProvider) {
                @Override
                protected void validate(@NotNull WritableRegistry<LootTable> writableregistry, @NotNull ValidationContext context, ProblemReporter.Collector collector) {
                    // Do not validate at all, per what people online said.
                }
            });

            // generator.addProvider(event.includeServer(), new VerdantRecipeProvider(packOutput, lookupProvider));

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

    public static void registerDatapackRegistries(DataPackRegistryEvent.NewRegistry event) {
        // System.out.println(BlockTransformer.CODEC);
        event.dataPackRegistry(
                BlockTransformer.KEY,
                BlockTransformer.CODEC,
                BlockTransformer.CODEC
        );

        event.dataPackRegistry(
                FeatureSet.KEY,
                FeatureSet.CODEC,
                FeatureSet.CODEC
        );
    }
}