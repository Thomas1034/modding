package com.thomas.verdant;

import com.thomas.verdant.registry.properties.WoodSets;
import com.thomas.verdant.util.blocktransformer.BlockTransformer;
import com.thomas.verdant.util.featureset.FeatureSet;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.registry.DynamicRegistries;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.fabricmc.fabric.api.registry.FuelRegistryEvents;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.FuelValues;

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
        DynamicRegistries.registerSynced(FeatureSet.KEY, FeatureSet.CODEC);

        BlockEntityType.SIGN.addSupportedBlock(WoodSets.STRANGLER.getSign().get());
        BlockEntityType.SIGN.addSupportedBlock(WoodSets.STRANGLER.getWallSign().get());
        BlockEntityType.HANGING_SIGN.addSupportedBlock(WoodSets.STRANGLER.getHangingSign().get());
        BlockEntityType.HANGING_SIGN.addSupportedBlock(WoodSets.STRANGLER.getWallHangingSign().get());

        FuelRegistryEvents.BUILD.register(new FuelRegistryEvents.BuildCallback() {
            @Override
            public void build(FuelValues.Builder builder, FuelRegistryEvents.Context context) {
                WoodSets.STRANGLER.registerFuels((builder::add));
            }
        });

        WoodSets.STRANGLER.registerFlammability(FlammableBlockRegistry.getDefaultInstance()::add);


    }
}
