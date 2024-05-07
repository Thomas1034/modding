package com.thomas.verdant.event;

import com.thomas.verdant.Verdant;
import com.thomas.verdant.util.MotionHelper;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.TickEvent.PlayerTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Verdant.MOD_ID)
public class ModEvents {

	@SubscribeEvent
	public static void onPlayerTick(PlayerTickEvent event) {

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

}