package com.startraveler.verdant;

import com.startraveler.verdant.entity.custom.RootedEntity;
import com.startraveler.verdant.entity.custom.TimbermiteEntity;
import com.startraveler.verdant.registry.*;
import com.startraveler.verdant.util.baitdata.BaitData;
import com.startraveler.verdant.util.blocktransformer.BlockTransformer;
import com.startraveler.verdant.util.featureset.FeatureSet;
import com.startraveler.verdant.woodset.WoodSet;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.EntitySleepEvents;
import net.fabricmc.fabric.api.event.registry.DynamicRegistries;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.registry.*;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class Verdant implements ModInitializer {

    @Override
    public void onInitialize() {

        // This method is invoked by the Fabric mod loader when it is ready
        // to load your mod. You can access Fabric and Common code in this
        // project.
        // Use Fabric to bootstrap the Common mod.
        // Constants.LOG.info("Hello Fabric world!");
        CommonClass.init();

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
            DispenserBehaviors.woodSet(woodSet);
            woodSet.registerFlammability(FlammableBlockRegistry.getDefaultInstance()::add);
        }

        // Register Fire
        FlammablesRegistry.init(FlammableBlockRegistry.getDefaultInstance()::add);
        // Register Compost
        CompostablesRegistry.init(CompostingChanceRegistry.INSTANCE::add);
        // Register Dispenser Behaviors
        DispenserBehaviors.init();
        // Register fuels
        FuelRegistryEvents.BUILD.register((builder, context) -> FuelsRegistry.init((builder::add)));
        // Register potion recipes
        FabricBrewingRecipeRegistryBuilder.BUILD.register(builder -> {
            PotionRecipeRegistry.init(builder::addMix, builder::registerItemRecipe);
        });

        FabricDefaultAttributeRegistry.register(
                EntityTypeRegistry.TIMBERMITE.get(),
                TimbermiteEntity.createAttributes()
        );
        FabricDefaultAttributeRegistry.register(
                EntityTypeRegistry.ROOTED.get(),
                RootedEntity.createAttributes()
        );

        // Block caffeine from sleeping
        EntitySleepEvents.ALLOW_SLEEPING.register((player, pos) -> {
            if (player.getActiveEffectsMap().get(MobEffectRegistry.CAFFEINATED.asHolder()) != null) {

                if (player instanceof ServerPlayer sleepingPlayer) {
                    sleepingPlayer.sendSystemMessage(Component.translatable("block.minecraft.bed.caffeine"));
                }

                return Player.BedSleepingProblem.OTHER_PROBLEM;
            }

            return null;
        });

        CommonClass.addCakeCandles();
    }
}
