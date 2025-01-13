package com.startraveler.mansioneer;

import com.startraveler.mansioneer.util.biomemapping.BiomeMapping;
import com.startraveler.mansioneer.util.blocktransformer.BlockTransformer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.registry.DynamicRegistries;

public class Mansioneer implements ModInitializer {

    @Override
    public void onInitialize() {

        // This method is invoked by the Fabric mod loader when it is ready
        // to load your mod. You can access Fabric and Common code in this
        // project.

        // Use Fabric to bootstrap the Common mod.
        Constants.LOG.info("Hello Fabric world!");
        CommonClass.init();

        // Set up dynamic registries
        DynamicRegistries.registerSynced(BlockTransformer.KEY, BlockTransformer.CODEC);
        DynamicRegistries.registerSynced(BiomeMapping.KEY, BiomeMapping.CODEC);
    }
}
