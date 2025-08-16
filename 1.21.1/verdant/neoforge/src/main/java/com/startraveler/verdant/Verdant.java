package com.startraveler.verdant;


import com.startraveler.rootbound.Rootbound;
import com.startraveler.verdant.entity.custom.BrambleEntity;
import com.startraveler.verdant.entity.custom.PoisonerEntity;
import com.startraveler.verdant.entity.custom.RootedEntity;
import com.startraveler.verdant.entity.custom.TimbermiteEntity;
import com.startraveler.verdant.fluid.VerdantFluidTypes;
import com.startraveler.verdant.fluid.VerdantFluids;
import com.startraveler.verdant.registry.*;
import com.startraveler.verdant.timer.BaseTimer;
import com.startraveler.verdant.timer.PrintForTestingTimer;
import com.startraveler.verdant.timer.TimerListSavedData;
import com.startraveler.verdant.util.baitdata.BaitData;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.storage.DimensionDataStorage;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.ItemAbility;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.AddServerReloadListenersEvent;
import net.neoforged.neoforge.event.ModifyDefaultComponentsEvent;
import net.neoforged.neoforge.event.brewing.RegisterBrewingRecipesEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.living.LivingEvent;
import net.neoforged.neoforge.event.entity.player.CanPlayerSleepEvent;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.neoforged.neoforge.event.tick.LevelTickEvent;
import net.neoforged.neoforge.items.wrapper.InvWrapper;
import net.neoforged.neoforge.items.wrapper.SidedInvWrapper;
import net.neoforged.neoforge.registries.DataPackRegistryEvent;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;

@Mod(Constants.MOD_ID)
public class Verdant {


    public static final DeferredBlock<LiquidBlock> SAP_FLOWING = null;

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
        eventBus.addListener(Verdant::onFinishSetup);
        eventBus.addListener(Verdant::registerEntityAttributes);
        // Dart Tipping Ingredients
        eventBus.addListener(Verdant::modifyDefaultComponents);


        // Caffeine
        NeoForge.EVENT_BUS.addListener(Verdant::onPlayerTryToSleepEvent);

        // Potions
        NeoForge.EVENT_BUS.addListener(Verdant::registerBrewingRecipes);

        // Tilling Grus
        NeoForge.EVENT_BUS.addListener(Verdant::registerTillables);

        // Ticking Timers
        NeoForge.EVENT_BUS.addListener(Verdant::tickTimers);

        // Clearing Cache
        NeoForge.EVENT_BUS.addListener(Verdant::addReloadListeners);


        // Fluids
        VerdantFluids.register(eventBus);
        VerdantFluidTypes.register(eventBus);
        InnerRegistration.VERDANT_NEOFORGE_BLOCKS.register(eventBus);
        InnerRegistration.VERDANT_NEOFORGE_ITEMS.register(eventBus);

        Rootbound.initializeWoodSets(eventBus, WoodSets.WOOD_SETS);
    }

    public static void tickTimers(LevelTickEvent.Post event) {
        if (event.getLevel() instanceof ServerLevel level) {

            DimensionDataStorage dataStorage = level.getDataStorage();
            TimerListSavedData timerList = dataStorage.computeIfAbsent(TimerListSavedData.TYPE);

            List<BaseTimer> timers = timerList.getTimers();

            if (!timers.isEmpty()) {
                for (BaseTimer timer : timers) {
                    boolean result = timer.handleTick(level);
                    if (!result) {
                        timerList.removeTimer(timer);
                    }
                }
                dataStorage.set(TimerListSavedData.TYPE, timerList);
            }
        }
    }

    public static void addReloadListeners(AddServerReloadListenersEvent event) {
        event.addListener(
                ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "clear_cache"),
                CommonClass.TRANSFORMERS
        );
    }

    public static void modifyDefaultComponents(ModifyDefaultComponentsEvent event) {
        BlowdartTippingIngredientRegistry.addIngredients((item, biConsumerConsumer) -> event.modify(
                item,
                builder -> biConsumerConsumer.accept(builder::set)
        ));
    }

    public static void onFinishSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {

            BaseTimer.CODEC_REGISTRY.register(PrintForTestingTimer.TYPE, PrintForTestingTimer.CODEC);

            FlammablesRegistry.init(((FireBlock) Blocks.FIRE)::setFlammable);

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
        event.put(EntityTypeRegistry.POISONER.get(), PoisonerEntity.createAttributes().build());
        event.put(EntityTypeRegistry.BRAMBLE.get(), BrambleEntity.createAttributes().build());
        event.put(EntityTypeRegistry.OOZE.get(), Monster.createMonsterAttributes().build());
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

        // Constants.LOG.warn("Registering datapack registries");
        event.dataPackRegistry(BaitData.KEY, BaitData.CODEC, BaitData.CODEC);
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

    public static void registerTillables(BlockEvent.BlockToolModificationEvent event) {
        ItemStack stack = event.getHeldItemStack();
        ItemAbility ability = event.getItemAbility();
        UseOnContext context = event.getContext();
        // If it isn't stripping, or if it can't do the action, return.
        if (ability != ItemAbilities.HOE_TILL || !stack.canPerformAction(ability)) {
            return;
        }
        BlockState state = event.getState();
        BlockState finalState = null;
        // Check if this is the right block.
        if (state.is(BlockRegistry.STONY_GRUS.get())) {
            // Check if the context is right.
            if (HoeItem.onlyIfAirAbove(context)) {
                finalState = BlockRegistry.GRUS.get().defaultBlockState();
            }
        }
        // If the final state was changed, set it.
        if (finalState != null) {
            event.setFinalState(finalState);
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

    public static class InnerRegistration {
        public static final DeferredRegister.Blocks VERDANT_NEOFORGE_BLOCKS = DeferredRegister.createBlocks(Constants.MOD_ID);
        public static final DeferredRegister.Items VERDANT_NEOFORGE_ITEMS = DeferredRegister.createItems(Constants.MOD_ID);
        public static final DeferredHolder<Item, Item> SAP_BUCKET = VERDANT_NEOFORGE_ITEMS.registerItem(
                "sap_bucket",
                (properties) -> new BucketItem(
                        VerdantFluids.SOURCE_SAP.get(),
                        properties.craftRemainder(Items.BUCKET).stacksTo(1)
                )
        );

        public static final DeferredHolder<Block, LiquidBlock> SAP = VERDANT_NEOFORGE_BLOCKS.registerBlock(
                "sap",
                (properties) -> new LiquidBlock(
                        VerdantFluids.SOURCE_SAP.get(),
                        properties.mapColor(MapColor.EMERALD)
                                .replaceable()
                                .noCollission()
                                .strength(100.0F)
                                .pushReaction(
                                        PushReaction.DESTROY)
                                .noLootTable()
                                .liquid()
                                .sound(SoundType.EMPTY)
                )
        );
    }

}