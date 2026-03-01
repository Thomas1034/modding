package com.startraveler.verdant;

import com.startraveler.rootbound.Rootbound;
import com.startraveler.verdant.entity.custom.*;
import com.startraveler.verdant.registry.*;
import com.startraveler.verdant.timer.BaseTimer;
import com.startraveler.verdant.timer.BlockTransformerTimer;
import com.startraveler.verdant.timer.PlaceBlocksTimer;
import com.startraveler.verdant.timer.PrintForTestingTimer;
import com.startraveler.verdant.util.baitdata.BaitData;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.EntitySleepEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.fabricmc.fabric.api.event.registry.DynamicRegistries;
import net.fabricmc.fabric.api.item.v1.DefaultItemComponentEvents;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.registry.*;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.packs.resources.CloseableResourceManager;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class Verdant implements ModInitializer {

    @Override
    public void onInitialize() {
        // WoodSet woodSet = new WoodSet()

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
            builder.addContainer(Items.GLASS_BOTTLE);
        });

        FabricDefaultAttributeRegistry.register(
                EntityTypeRegistry.TIMBERMITE.get(),
                TimbermiteEntity.createAttributes()
        );
        FabricDefaultAttributeRegistry.register(EntityTypeRegistry.ROOTED.get(), RootedEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(EntityTypeRegistry.POISONER.get(), PoisonerEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(EntityTypeRegistry.BRAMBLE.get(), BrambleEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(
                EntityTypeRegistry.OOZE.get(),
                Monster.createMonsterAttributes().build()
        );
        FabricDefaultAttributeRegistry.register(
                EntityTypeRegistry.SKULL_SPIDER.get(),
                SkullSpiderEntity.createSkullSpiderAttributes().build()
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

        TillableBlockRegistry.register(
                BlockRegistry.STONY_GRUS.get(),
                HoeItem::onlyIfAirAbove,
                HoeItem.changeIntoState(BlockRegistry.GRUS.get().defaultBlockState())
        );

        DefaultItemComponentEvents.MODIFY.register(context -> BlowdartTippingIngredientRegistry.addIngredients((item, biConsumerConsumer) -> context.modify(item,
                builder -> biConsumerConsumer.accept(builder::set)
        )));

        BaseTimer.CODEC_REGISTRY.register(PrintForTestingTimer.TYPE, PrintForTestingTimer.CODEC);
        BaseTimer.CODEC_REGISTRY.register(PlaceBlocksTimer.TYPE, PlaceBlocksTimer.CODEC);
        BaseTimer.CODEC_REGISTRY.register(BlockTransformerTimer.TYPE, BlockTransformerTimer.CODEC);

        PlayerBlockBreakEvents.AFTER.register((Level level, Player player, BlockPos blockPos, BlockState blockState, BlockEntity blockEntity) -> CommonClass.spawnSpiderlingsOnBlockBreak(level,
                player,
                blockPos,
                blockState
        ));

        ServerTickEvents.END_WORLD_TICK.register(CommonClass::tickTimers);


        CommonClass.addCakeCandles();

        Rootbound.initializeWoodSets(WoodSets.WOOD_SETS);
    }
}
