package com.startraveler.mansioneer;


import com.startraveler.mansioneer.data.MansioneerBiomeMappingProvider;
import com.startraveler.mansioneer.data.MansioneerBiomeTagProvider;
import com.startraveler.mansioneer.data.MansioneerBlockTransformerProvider;
import com.startraveler.mansioneer.util.biomemapping.BiomeMapping;
import com.startraveler.mansioneer.util.blocktransformer.BlockTransformer;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.registries.DataPackRegistryEvent;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

@Mod(Constants.MOD_ID)
public class Mansioneer {

    public Mansioneer(IEventBus eventBus) {

        // This method is invoked by the NeoForge mod loader when it is ready
        // to load your mod. You can access NeoForge and Common code in this
        // project.

        // Use NeoForge to bootstrap the Common mod.
        CommonClass.init();

        eventBus.addListener(Mansioneer::registerDatapackRegistries);
        eventBus.addListener(Mansioneer::gatherData);
    }

    public static void registerDatapackRegistries(final DataPackRegistryEvent.NewRegistry event) {
        event.dataPackRegistry(BlockTransformer.KEY, BlockTransformer.CODEC, BlockTransformer.CODEC);
        event.dataPackRegistry(BiomeMapping.KEY, BiomeMapping.CODEC, BiomeMapping.CODEC);
    }


    public static void gatherData(final GatherDataEvent.Client event) {
        try {
            // Store some frequently-used fields for later use.
            DataGenerator generator = event.getGenerator();
            PackOutput packOutput = generator.getPackOutput();
            CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

            BiomeTagsProvider biomeTagsProvider = new MansioneerBiomeTagProvider(packOutput, lookupProvider);
            generator.addProvider(true, biomeTagsProvider);

            // Generate dynamic registries
            generator.addProvider(
                    true, new DatapackBuiltinEntriesProvider(
                            packOutput,
                            lookupProvider,
                            new RegistrySetBuilder().add(BlockTransformer.KEY,
                                            MansioneerBlockTransformerProvider::register
                                    )
                                    .add(BiomeMapping.KEY, MansioneerBiomeMappingProvider::register),
                            Set.of(Constants.MOD_ID, "minecraft")
                    )
            );

            /*
            Debugging purposes only.
            BuiltInRegistries.REGISTRY.stream()
                    .forEach(registry -> Constants.LOG.warn("Found registry {} ", registry.key().location()));
            */

        } catch (RuntimeException e) {
            Constants.LOG.error("Failed to generate data.", e);
        }
    }

}