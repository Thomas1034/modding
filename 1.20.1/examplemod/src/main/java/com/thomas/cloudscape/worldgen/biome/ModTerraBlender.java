package com.thomas.cloudscape.worldgen.biome;

import com.thomas.cloudscape.Cloudscape;

import net.minecraft.resources.ResourceLocation;
import terrablender.api.Regions;

public class ModTerraBlender {
	public static void registerBiomes() {
		Regions.register(new ModOverworldRegion(new ResourceLocation(Cloudscape.MOD_ID, "overworld"), 5));
	}
}
