package com.thomas.verdant.event;

import com.thomas.verdant.Verdant;
import com.thomas.verdant.client.OvergrowthHudOverlay;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class ClientEvents {

	@Mod.EventBusSubscriber(modid = Verdant.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
	public static class ClientModBusEvents {

		@SubscribeEvent
		public static void registerGuiOverlays(RegisterGuiOverlaysEvent event) {
			event.registerAbove(VanillaGuiOverlay.PLAYER_HEALTH.id(), "infection", OvergrowthHudOverlay.HUD_INFECTION);
		}
	}
}
