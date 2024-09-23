package com.thomas.turbulent.event;

import com.thomas.turbulent.Turbulent;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.common.Mod;

public class ClientEvents {

	@Mod.EventBusSubscriber(modid = Turbulent.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
	public static class ClientModBusEvents {

	}
}
