package com.thomas.zirconmod.worldgen.biome;

import com.thomas.zirconmod.ZirconMod;
import com.thomas.zirconmod.worldgen.biome.custom.ClearSkyBiome;
import com.thomas.zirconmod.worldgen.biome.custom.PetrifiedForestBiome;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;

public class ModBiomes {
	public static final ResourceKey<Biome> PETRIFIED_FOREST_BIOME = ResourceKey.create(Registries.BIOME,
			new ResourceLocation(ZirconMod.MOD_ID, "petrified_forest_biome"));
	public static final ResourceKey<Biome> CLEAR_SKY_BIOME = ResourceKey.create(Registries.BIOME,
			new ResourceLocation(ZirconMod.MOD_ID, "clear_sky_biome"));
	
	
	//OverworldBiomes
	public static void boostrap(BootstapContext<Biome> context) {
		context.register(PETRIFIED_FOREST_BIOME, PetrifiedForestBiome.build(context));
		context.register(CLEAR_SKY_BIOME, ClearSkyBiome.build(context));

	}

	public static void globalOverworldGeneration(BiomeGenerationSettings.Builder builder) {
		BiomeDefaultFeatures.addDefaultCarversAndLakes(builder);
		BiomeDefaultFeatures.addDefaultCrystalFormations(builder);
		BiomeDefaultFeatures.addDefaultMonsterRoom(builder);
		BiomeDefaultFeatures.addDefaultUndergroundVariety(builder);
		BiomeDefaultFeatures.addDefaultSprings(builder);
		BiomeDefaultFeatures.addSurfaceFreezing(builder);
	}
}
