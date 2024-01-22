package com.thomas.zirconmod.worldgen.biome;

import com.thomas.zirconmod.ZirconMod;

import net.minecraft.resources.ResourceLocation;
import terrablender.api.Regions;

public class ModTerraBlender {
	public static void registerBiomes() {
		Regions.register(new ModOverworldRegion(new ResourceLocation(ZirconMod.MOD_ID, "overworld"), 5));
	}
}
