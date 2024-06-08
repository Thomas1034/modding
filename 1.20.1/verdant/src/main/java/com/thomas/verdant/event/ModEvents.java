package com.thomas.verdant.event;

import com.mojang.brigadier.CommandDispatcher;
import com.thomas.verdant.Verdant;
import com.thomas.verdant.command.VerdantCommandHandler;
import com.thomas.verdant.effect.ModMobEffects;
import com.thomas.verdant.entity.custom.OvergrownZombieEntity;
import com.thomas.verdant.growth.SpreadAmountProvider;
import com.thomas.verdant.growth.VerdantGrower;
import com.thomas.verdant.network.ModPacketHandler;
import com.thomas.verdant.network.SynchronizePlayerInfectionPacket;
import com.thomas.verdant.overgrowth.EntityOvergrowth;
import com.thomas.verdant.overgrowth.EntityOvergrowthEffects;
import com.thomas.verdant.overgrowth.EntityOvergrowthProvider;
import com.thomas.verdant.overgrowth.OvergrowthProgressionProvider;
import com.thomas.verdant.util.MotionHelper;
import com.thomas.verdant.util.function.DeferredAction;

import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkStatus;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.TickEvent.PlayerTickEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.ZombieEvent.SummonAidEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.SleepingLocationCheckEvent;
import net.minecraftforge.eventbus.api.Event.Result;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Verdant.MOD_ID)
public class ModEvents {

	@SubscribeEvent
	public static void registerCommandsEvent(RegisterCommandsEvent event) {
		CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();
		VerdantCommandHandler.register(dispatcher);
	}

	@SubscribeEvent
	public static void updateMotionHelper(PlayerTickEvent event) {

		if (event.phase != TickEvent.Phase.START) {
			return;
		}

		if (event.side == LogicalSide.SERVER) {
			Player player = event.player;
			if (player instanceof ServerPlayer sp) {
				MotionHelper.update(sp);
			}
		}
	}

	@SubscribeEvent
	public static void onClientMessage(ClientChatEvent event) {
		System.out.println("Message: " + event.getMessage());
	}

	@SubscribeEvent
	public static void onPlayerTryToSleepEvent(SleepingLocationCheckEvent event) {

		LivingEntity sleepingEntity = event.getEntity();

		// Do all processing on the server.
		if (sleepingEntity.level().isClientSide()) {
			return;
		}

		if (sleepingEntity.getActiveEffectsMap().get(ModMobEffects.CAFFEINATED.get()) != null) {
			event.setResult(Result.DENY);
			if (sleepingEntity instanceof ServerPlayer sleepingPlayer) {
				sleepingPlayer.sendSystemMessage(Component.translatable("block.minecraft.bed.caffeine"));
			}
		}
	}

	@SubscribeEvent
	public static void onAttachCapabilitiesLivingEntity(AttachCapabilitiesEvent<Entity> event) {
		if (event.getObject() instanceof LivingEntity) {
			if (!event.getObject().getCapability(EntityOvergrowthProvider.ENTITY_OVERGROWTH).isPresent()) {
				event.addCapability(new ResourceLocation(Verdant.MOD_ID, "infection"), new EntityOvergrowthProvider());
			}
		}
	}

	@SubscribeEvent
	public static void onAttachCapabilitiesLevel(AttachCapabilitiesEvent<Level> event) {

		if (!event.getObject().getCapability(SpreadAmountProvider.SPREAD_AMOUNT).isPresent()) {
			event.addCapability(new ResourceLocation(Verdant.MOD_ID, "spread_amount"), new SpreadAmountProvider());
		}
		if (!event.getObject().getCapability(OvergrowthProgressionProvider.OVERGROWTH_PROGRESSION).isPresent()) {
			event.addCapability(new ResourceLocation(Verdant.MOD_ID, "infection_progression"),
					new OvergrowthProgressionProvider());
		}
	}

	@SubscribeEvent
	public static void onPlayerCloned(PlayerEvent.Clone event) {
		if (!event.isWasDeath()) {
			event.getOriginal().getCapability(EntityOvergrowthProvider.ENTITY_OVERGROWTH).ifPresent(oldStore -> {
				event.getEntity().getCapability(EntityOvergrowthProvider.ENTITY_OVERGROWTH).ifPresent(newStore -> {
					newStore.copyFrom(oldStore);
				});
			});
		}
	}

	@SubscribeEvent
	public static void updateClientInfection(TickEvent.PlayerTickEvent event) {
		if (event.side == LogicalSide.SERVER) {
			event.player.getCapability(EntityOvergrowthProvider.ENTITY_OVERGROWTH).ifPresent(infection -> {
				// TODO disables overgrowth for now, disable later.
				infection.setLevel(EntityOvergrowth.MIN_LEVEL);

				if (infection.getLevel() == EntityOvergrowth.MAX_LEVEL) {
					infection.setLevel(EntityOvergrowth.MIN_LEVEL);
				} else {
					EntityOvergrowthEffects.updateLevel(event.player);
				}
				ModPacketHandler.sendToPlayer(
						new SynchronizePlayerInfectionPacket(infection.getLevel(), event.player.getUUID()),
						((ServerPlayer) event.player));
			});
		}
	}

	@SubscribeEvent
	public static void reinforcementsEvent(SummonAidEvent event) {
		// System.out.println("Calling reinforcements!");
	}

	// @SubscribeEvent
//	public static void illuminate(TagsUpdatedEvent event) {
//
//		// This event should fire just after the list of tags are updated.
//		// Due to the way that Minecraft handles lighting, newly-tagged ores will need
//		// to have blocks updated next to them for them to light up areas around them.
//		// However, this is not needed if emissive rendering is the only thing that's
//		// desired. That should update automatically, if I'm interpreting this
//		// correctly.
//
//		// Use these to control the light levels and emissive rendering. You could
//		// probably bind them to config or gamerule values, if so desired.
//		boolean forcedDoEmissive = true;
//		int forcedLightLevel = 10;
//
//		// Iterate over all the blocks in the Forge block registry. This should contain
//		// every block in the game.
//		ForgeRegistries.BLOCKS.forEach((block) -> {
//			// Filter to process only blocks tagged as Forge ores. This is a way of ensuring
//			// compatibility with other mods, especially ones written in other languages.
//			// If you want to make your own tag for the ores, substitute that here.
//			if (!block.defaultBlockState().is(Tags.Blocks.ORES)) {
//				return;
//			}
//
//			// Set the light level of the block itself.
//			// This won't impact the actual states that are placed in the world; however, it
//			// might be needed for some compatibility. I'm not sure; I just thought it safer
//			// to include this as well.
//			// First, store the existing state and light levels.
//			// Access transformers were used to broaden the permissions of these fields.
//			ToIntFunction<BlockState> defaultLightLevel = block.properties.lightEmission;
//			StatePredicate defaultDoEmissive = block.properties.emissiveRendering;
//
//			// Second, build composite predicates. This is a way of respecting the original
//			// values, if the ore is already set up to glow.
//			ToIntFunction<BlockState> newLightLevel = (blockTest) -> Math.max(defaultLightLevel.applyAsInt(blockTest),
//					forcedLightLevel);
//			StatePredicate newDoEmissive = (state, level,
//					pos) -> (defaultDoEmissive.test(state, level, pos) || forcedDoEmissive);
//
//			// Third, set the block's predicates to the new values.
//			// Access transformers were used to broaden the permissions of these fields.
//			block.properties.lightEmission = newLightLevel;
//			block.properties.emissiveRendering = newDoEmissive;
//
//			// Now, set the light level and emissivity of all the block states.
//			// Do this by streaming and setting each state's field.
//			block.getStateDefinition().getPossibleStates().stream().forEach((state) -> {
//
//				// Get the state as its state behavior; this is needed to access the
//				// light-handling fields, since they're private to that class.
//				BlockBehaviour.BlockStateBase stateBehavior = (BlockBehaviour.BlockStateBase) state;
//
//				// First, store the existing state and light levels.
//				// Access transformers were used to broaden the permissions of these fields.
//				int defaultLight = stateBehavior.lightEmission;
//				StatePredicate defaultEmissive = stateBehavior.emissiveRendering;
//
//				// Second, build composite variables. This allows ores that already glow to not
//				// be overridden by the new values.
//				int newLight = Math.max(defaultLight, forcedLightLevel);
//				StatePredicate newEmissive = (stateTest, level,
//						pos) -> (defaultEmissive.test(stateTest, level, pos) || forcedDoEmissive);
//
//				// Third, set the state's variables to the newly-calculated variables.
//				// Access transformers were used to broaden the permissions of these fields.
//				stateBehavior.lightEmission = newLight;
//				stateBehavior.emissiveRendering = newEmissive;
//			});
//		});
//	}

}
