package com.startraveler.verdant;


import com.startraveler.verdant.entity.custom.RootedEntity;
import com.startraveler.verdant.entity.custom.TimbermiteEntity;
import com.startraveler.verdant.registry.*;
import com.startraveler.verdant.util.baitdata.BaitData;
import com.startraveler.verdant.util.blocktransformer.BlockTransformer;
import com.startraveler.verdant.util.featureset.FeatureSet;
import com.startraveler.verdant.woodset.WoodSet;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FireBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.ItemAbility;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BlockEntityTypeAddBlocksEvent;
import net.neoforged.neoforge.event.brewing.RegisterBrewingRecipesEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.living.LivingEvent;
import net.neoforged.neoforge.event.entity.player.CanPlayerSleepEvent;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.neoforged.neoforge.items.wrapper.InvWrapper;
import net.neoforged.neoforge.items.wrapper.SidedInvWrapper;
import net.neoforged.neoforge.registries.DataPackRegistryEvent;

import java.util.List;

@Mod(Constants.MOD_ID)
public class Verdant {

    public Verdant(final IEventBus eventBus) {

        // This method is invoked by the NeoForge mod loader when it is ready
        // to load your mod. You can access NeoForge and Common code in this
        // project.

        // Use NeoForge to bootstrap the Common mod.
        // Constants.LOG.info("Hello NeoForge world!");
        CommonClass.init();

        eventBus.addListener(Verdant::registerDatapackRegistries);
        eventBus.addListener(Verdant::registerContainerCapabilities);
        // For wood sets.
        eventBus.addListener(Verdant::addBlocksToBlockEntities);
        eventBus.addListener(Verdant::onFinishSetup);
        eventBus.addListener(Verdant::registerEntityAttributes);
        NeoForge.EVENT_BUS.addListener(Verdant::registerStrippingLogs);

        // Caffeine
        NeoForge.EVENT_BUS.addListener(Verdant::onPlayerTryToSleepEvent);

        // Potions
        NeoForge.EVENT_BUS.addListener(Verdant::registerBrewingRecipes);
    }

    public static void onFinishSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {

            FlammablesRegistry.init(((FireBlock) Blocks.FIRE)::setFlammable);
            for (WoodSet woodSet : WoodSets.WOOD_SETS) {
                woodSet.registerFlammability(((FireBlock) Blocks.FIRE)::setFlammable);
                DispenserBehaviors.woodSet(woodSet);
            }
            DispenserBehaviors.init();
            CommonClass.addCakeCandles();
        });
    }

    // Using some method to listen to the event
    public static void registerBrewingRecipes(RegisterBrewingRecipesEvent event) {
        // Gets the builder to add recipes to
        PotionBrewing.Builder builder = event.getBuilder();

        PotionRecipeRegistry.init(
                builder::addMix,
                (input, ingredient, output) -> builder.addRecipe(
                        Ingredient.of(input),
                        ingredient,
                        new ItemStack(output)
                )
        );
    }

    public static void registerEntityAttributes(final EntityAttributeCreationEvent event) {
        event.put(EntityTypeRegistry.TIMBERMITE.get(), TimbermiteEntity.createAttributes().build());
        event.put(EntityTypeRegistry.ROOTED.get(), RootedEntity.createAttributes().build());
    }

    public static void registerStrippingLogs(final BlockEvent.BlockToolModificationEvent event) {
        ItemStack stack = event.getHeldItemStack();
        ItemAbility ability = event.getItemAbility();
        // If it isn't stripping, or if it can't do the action, return.
        if (ability != ItemAbilities.AXE_STRIP || !stack.canPerformAction(ability)) {
            return;
        }
        BlockState state = event.getState();
        BlockState finalState = null;
        // Check if this is a strippable log from a wood set.
        // If so, set the result and return.
        for (WoodSet woodSet : WoodSets.WOOD_SETS) {
            if (state.is(woodSet.getLog().get())) {
                finalState = woodSet.getStrippedLog().get().defaultBlockState();
            } else if (state.is(woodSet.getWood().get())) {
                finalState = woodSet.getStrippedWood()
                        .get()
                        .defaultBlockState()
                        .setValue(BlockStateProperties.AXIS, state.getValue(BlockStateProperties.AXIS));
            }
            if (finalState != null) {
                finalState = finalState.setValue(BlockStateProperties.AXIS, state.getValue(BlockStateProperties.AXIS));
                break;
            }
        }
        // If the final state was changed, set it.
        if (finalState != null) {
            event.setFinalState(finalState);
        }
    }


    public static void addBlocksToBlockEntities(final BlockEntityTypeAddBlocksEvent event) {
        for (WoodSet woodSet : WoodSets.WOOD_SETS) {
            event.modify(BlockEntityType.SIGN, woodSet.getSign().get(), woodSet.getWallSign().get());
            event.modify(
                    BlockEntityType.HANGING_SIGN,
                    woodSet.getHangingSign().get(),
                    woodSet.getWallHangingSign().get()
            );
        }
    }


    public static void registerContainerCapabilities(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(
                Capabilities.ItemHandler.BLOCK,
                BlockEntityTypeRegistry.FISH_TRAP_BLOCK_ENTITY.get(),
                (sidedContainer, side) -> side == null ? new InvWrapper(sidedContainer) : new SidedInvWrapper(
                        sidedContainer,
                        side
                )
        );


        // Boats, modified from CapabilityHooks.
        List<? extends EntityType<? extends Container>> woodSetChestBoats = WoodSets.WOOD_SETS.stream()
                .map(woodSet -> woodSet.getChestBoat().get())
                .toList();
        for (EntityType<? extends Container> entityType : woodSetChestBoats) {
            event.registerEntity(Capabilities.ItemHandler.ENTITY, entityType, (entity, ctx) -> new InvWrapper(entity));
            event.registerEntity(
                    Capabilities.ItemHandler.ENTITY_AUTOMATION,
                    entityType,
                    (entity, ctx) -> new InvWrapper(entity)
            );
        }
    }

    public static void registerDatapackRegistries(final DataPackRegistryEvent.NewRegistry event) {

        Constants.LOG.warn("Registering datapack registries");
        // System.out.println(BlockTransformer.CODEC);
        event.dataPackRegistry(BlockTransformer.KEY, BlockTransformer.CODEC, BlockTransformer.CODEC);

        event.dataPackRegistry(BaitData.KEY, BaitData.CODEC, BaitData.CODEC);

        event.dataPackRegistry(FeatureSet.KEY, FeatureSet.CODEC, FeatureSet.CODEC);
    }

    public static void onPlayerTryToSleepEvent(CanPlayerSleepEvent event) {
        LivingEntity sleepingEntity = event.getEntity();

        if (sleepingEntity.getActiveEffectsMap().get(MobEffectRegistry.CAFFEINATED.asHolder()) != null) {
            event.setProblem(Player.BedSleepingProblem.OTHER_PROBLEM);
            if (sleepingEntity instanceof ServerPlayer sleepingPlayer) {
                sleepingPlayer.sendSystemMessage(Component.translatable("block.minecraft.bed.caffeine"));
            }
        }
    }

    @SubscribeEvent
    public void reduceVisibility(LivingEvent.LivingVisibilityEvent event) {
        LivingEntity entity = event.getEntity();
        int stenchLevel = 0;
        MobEffectInstance instance = entity.getEffect(MobEffectRegistry.STENCH.asHolder());
        if (instance != null) {
            stenchLevel += instance.getAmplifier() + 1;
        }
        double stenchMultiplier = 1.0 / (1.0 + stenchLevel);
        event.modifyVisibility(stenchMultiplier);
    }

}