package com.thomas.turbulent.event;

import com.mojang.brigadier.CommandDispatcher;
import com.thomas.turbulent.Turbulent;
import com.thomas.turbulent.util.MotionHelper;

import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.TickEvent.PlayerTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Turbulent.MOD_ID)
public class ModEvents {

	@SubscribeEvent
	public static void registerCommandsEvent(RegisterCommandsEvent event) {
		CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();
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
		// System.out.println("Message: " + event.getMessage());
	}

}
