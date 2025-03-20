package com.startraveler.verdant;

import com.startraveler.rootbound.Rootbound;
import com.startraveler.verdant.entity.custom.PoisonerEntity;
import com.startraveler.verdant.entity.custom.RootedEntity;
import com.startraveler.verdant.entity.custom.TimbermiteEntity;
import com.startraveler.verdant.registry.*;
import com.startraveler.verdant.util.baitdata.BaitData;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.EntitySleepEvents;
import net.fabricmc.fabric.api.event.registry.DynamicRegistries;
import net.fabricmc.fabric.api.item.v1.DefaultItemComponentEvents;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.registry.*;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.HoeItem;

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
        DynamicRegistries.registerSynced(BaitData.KEY, BaitData.CODEC);

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
        FabricDefaultAttributeRegistry.register(EntityTypeRegistry.ROOTED.get(), RootedEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(EntityTypeRegistry.POISONER.get(), PoisonerEntity.createAttributes());

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

        TillableBlockRegistry.register(
                BlockRegistry.STONY_GRUS.get(),
                HoeItem::onlyIfAirAbove,
                HoeItem.changeIntoState(BlockRegistry.GRUS.get().defaultBlockState())
        );

        DefaultItemComponentEvents.MODIFY.register(context -> BlowdartTippingIngredientRegistry.addIngredients((item, biConsumerConsumer) -> context.modify(item,
                builder -> biConsumerConsumer.accept(builder::set)
        )));

        CommonClass.addCakeCandles();

        Rootbound.initializeWoodSets(WoodSets.WOOD_SETS);
    }
}
