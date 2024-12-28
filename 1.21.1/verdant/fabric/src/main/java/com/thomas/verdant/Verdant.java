package com.thomas.verdant;

import com.thomas.verdant.registry.FlammablesRegistry;
import com.thomas.verdant.registry.WoodSets;
import com.thomas.verdant.util.baitdata.BaitData;
import com.thomas.verdant.util.blocktransformer.BlockTransformer;
import com.thomas.verdant.util.featureset.FeatureSet;
import com.thomas.verdant.woodset.WoodSet;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.registry.DynamicRegistries;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.fabricmc.fabric.api.registry.FuelRegistryEvents;
import net.fabricmc.fabric.api.registry.StrippableBlockRegistry;
import net.minecraft.core.dispenser.BoatDispenseItemBehavior;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class Verdant implements ModInitializer {

    @Override
    public void onInitialize() {
        
        // This method is invoked by the Fabric mod loader when it is ready
        // to load your mod. You can access Fabric and Common code in this
        // project.
        // Use Fabric to bootstrap the Common mod.
        Constants.LOG.info("Hello Fabric world!");
        CommonClass.init();
        CommonClass.initCompostables();

        // Set up dynamic registries
        DynamicRegistries.registerSynced(BlockTransformer.KEY, BlockTransformer.CODEC);
        DynamicRegistries.registerSynced(FeatureSet.KEY, FeatureSet.CODEC);
        DynamicRegistries.registerSynced(BaitData.KEY, BaitData.CODEC);

        // Add wood sets.
        for (WoodSet woodSet : WoodSets.WOOD_SETS) {
            BlockEntityType.SIGN.addSupportedBlock(woodSet.getSign().get());
            BlockEntityType.SIGN.addSupportedBlock(woodSet.getWallSign().get());
            BlockEntityType.HANGING_SIGN.addSupportedBlock(woodSet.getHangingSign().get());
            BlockEntityType.HANGING_SIGN.addSupportedBlock(woodSet.getWallHangingSign().get());
            FuelRegistryEvents.BUILD.register((builder, context) -> woodSet.registerFuels((builder::add)));
            StrippableBlockRegistry.register(woodSet.getLog().get(), woodSet.getStrippedLog().get());
            StrippableBlockRegistry.register(woodSet.getWood().get(), woodSet.getStrippedWood().get());
            DispenserBlock.registerBehavior(
                    woodSet.getBoatItem().get(),
                    new BoatDispenseItemBehavior(woodSet.getBoat().get())
            );
            DispenserBlock.registerBehavior(
                    woodSet.getChestBoatItem().get(),
                    new BoatDispenseItemBehavior(woodSet.getChestBoat().get())
            );
            woodSet.registerFlammability(FlammableBlockRegistry.getDefaultInstance()::add);
        }

        // Register Fire
        FlammablesRegistry.init(FlammableBlockRegistry.getDefaultInstance()::add);
    }
}
