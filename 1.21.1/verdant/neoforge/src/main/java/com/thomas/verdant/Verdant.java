package com.thomas.verdant;


import com.thomas.verdant.data.*;
import com.thomas.verdant.registry.NeoForgeBlockRegistry;
import com.thomas.verdant.registry.NeoForgeItemRegistry;
import com.thomas.verdant.util.blocktransformer.BlockTransformer;
import com.thomas.verdant.util.featureset.FeatureSet;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.WritableRegistry;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;
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

        NeoForgeBlockRegistry.register(eventBus);
        NeoForgeItemRegistry.register(eventBus);
    }

    public static void gatherData(GatherDataEvent event) {
        try {
            DataGenerator generator = event.getGenerator();
            PackOutput output = generator.getPackOutput();
            ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
            CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

            generator.addProvider(true, new VerdantItemModelProvider(output, existingFileHelper));
            generator.addProvider(true, new VerdantBlockStateProvider(output, existingFileHelper));
            generator.addProvider(true, new VerdantBlockTransformerProvider(output, lookupProvider));
            generator.addProvider(true, new VerdantFeatureSetProvider(output, lookupProvider));
            generator.addProvider(true, new VerdantBlockTagProvider(output, lookupProvider, existingFileHelper));
            generator.addProvider(event.includeServer(), new LootTableProvider(output, Collections.emptySet(),
                    List.of(new LootTableProvider.SubProviderEntry(VerdantBlockLootTableProvider::new, LootContextParamSets.BLOCK)), lookupProvider) {
                @Override
                protected void validate(@NotNull WritableRegistry<LootTable> writableregistry, @NotNull ValidationContext context, ProblemReporter.Collector collector) {
                    // Do not validate at all, per what people online said.
                }
            });

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