package com.thomas.cloudscape.worldgen.biome;

import com.thomas.cloudscape.ZirconMod;

import net.minecraft.resources.ResourceLocation;
import terrablender.api.Regions;

public class ModTerraBlender {
	public static void registerBiomes() {
		Regions.register(new ModOverworldRegion(new ResourceLocation(ZirconMod.MOD_ID, "overworld"), 5));
	}
}
