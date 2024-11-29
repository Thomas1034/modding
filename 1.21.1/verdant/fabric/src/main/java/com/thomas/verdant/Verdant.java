package com.thomas.verdant;

import com.thomas.verdant.registry.VerdantRegistryHelpers;
import com.thomas.verdant.util.blocktransformer.BlockTransformer;
import com.thomas.verdant.util.featureset.FeatureSet;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.registry.DynamicRegistries;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class Verdant implements ModInitializer {
    
    @Override
    public void onInitialize() {
        
        // This method is invoked by the Fabric mod loader when it is ready
        // to load your mod. You can access Fabric and Common code in this
        // project.
        // Use Fabric to bootstrap the Common mod.
        Constants.LOG.info("Hello Fabric world!");
        CommonClass.init();

        VerdantRegistryHelpers.BLOCK.register((name, block) -> Registry.register(BuiltInRegistries.BLOCK, ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, name), (Block) block.get()));
        VerdantRegistryHelpers.ITEM.register((name, item) -> Registry.register(BuiltInRegistries.ITEM, ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, name), (Item) item.get()));

        DynamicRegistries.registerSynced(BlockTransformer.KEY, BlockTransformer.CODEC);
        DynamicRegistries.registerSynced(FeatureSet.KEY, FeatureSet.CODEC);
    }

}
