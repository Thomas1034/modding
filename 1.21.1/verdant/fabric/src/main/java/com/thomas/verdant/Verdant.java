package com.thomas.verdant;

import com.thomas.verdant.util.blocktransformer.BlockTransformer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.registry.DynamicRegistries;

public class Verdant implements ModInitializer {
    
    @Override
    public void onInitialize() {
        
        // This method is invoked by the Fabric mod loader when it is ready
        // to load your mod. You can access Fabric and Common code in this
        // project.
        // Use Fabric to bootstrap the Common mod.
        Constants.LOG.info("Hello Fabric world!");
        CommonClass.init();

        DynamicRegistries.registerSynced(BlockTransformer.KEY, BlockTransformer.CODEC);



    }
}
