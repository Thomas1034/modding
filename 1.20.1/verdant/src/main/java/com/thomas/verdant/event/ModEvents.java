package com.thomas.verdant.event;

import com.thomas.verdant.Verdant;
import com.thomas.verdant.effect.ModMobEffects;
import com.thomas.verdant.infection.EntityInfection;
import com.thomas.verdant.infection.EntityInfectionEffects;
import com.thomas.verdant.infection.EntityInfectionProvider;
import com.thomas.verdant.network.ModPacketHandler;
import com.thomas.verdant.network.SynchronizePlayerInfectionPacket;
import com.thomas.verdant.util.MotionHelper;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.TickEvent.PlayerTickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.SleepingLocationCheckEvent;
import net.minecraftforge.eventbus.api.Event.Result;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Verdant.MOD_ID)
public class ModEvents {

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
			if (!event.getObject().getCapability(EntityInfectionProvider.ENTITY_INFECTION).isPresent()) {
				event.addCapability(new ResourceLocation(Verdant.MOD_ID, "properties"), new EntityInfectionProvider());
			}
		}
	}

	@SubscribeEvent
	public static void onPlayerCloned(PlayerEvent.Clone event) {
		if (!event.isWasDeath()) {
			event.getOriginal().getCapability(EntityInfectionProvider.ENTITY_INFECTION).ifPresent(oldStore -> {
				event.getEntity().getCapability(EntityInfectionProvider.ENTITY_INFECTION).ifPresent(newStore -> {
					newStore.copyFrom(oldStore);
				});
			});
		}
	}

	@SubscribeEvent
	public static void updateClientInfection(TickEvent.PlayerTickEvent event) {
		if (event.side == LogicalSide.SERVER) {
			event.player.getCapability(EntityInfectionProvider.ENTITY_INFECTION).ifPresent(infection -> {

				if (infection.getLevel() == EntityInfection.MAX_LEVEL) {
					infection.setLevel(EntityInfection.MIN_LEVEL);
				} else {
					EntityInfectionEffects.updateLevel(event.player);
				}
				ModPacketHandler.sendToPlayer(
						new SynchronizePlayerInfectionPacket(infection.getLevel(), event.player.getUUID()),
						((ServerPlayer) event.player));
			});
		}
	}

}
