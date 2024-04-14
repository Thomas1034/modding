package com.thomas.cloudscape.worldgen.biome.custom;

import com.thomas.cloudscape.util.Utilities;
import com.thomas.cloudscape.worldgen.ModPlacedFeatures;
import com.thomas.cloudscape.worldgen.biome.ModBiomes;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.sounds.Musics;
import net.minecraft.world.level.biome.AmbientMoodSettings;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;

public class PetrifiedForestBiome {

	public static final String NAME = "petrified_forest";

	public static Biome build(BootstapContext<Biome> context) {
		// ForgeRegistries.BIOMES.getDefaultKey();

		MobSpawnSettings.Builder spawnBuilder = new MobSpawnSettings.Builder();

		BiomeDefaultFeatures.commonSpawns(spawnBuilder);

		BiomeGenerationSettings.Builder biomeBuilder = new BiomeGenerationSettings.Builder(
				context.lookup(Registries.PLACED_FEATURE), context.lookup(Registries.CONFIGURED_CARVER));
		// we need to follow the same order as vanilla biomes for the
		// BiomeDefaultFeatures
		// Local Modifications
		// Vegetal Decorations
		// Underground Ores
		BiomeDefaultFeatures.addFossilDecoration(biomeBuilder);
		ModBiomes.globalOverworldGeneration(biomeBuilder);
		BiomeDefaultFeatures.addDefaultOres(biomeBuilder);
		BiomeDefaultFeatures.addExtraGold(biomeBuilder);
		BiomeDefaultFeatures.addDefaultSoftDisks(biomeBuilder);
		BiomeDefaultFeatures.addBadlandGrass(biomeBuilder);
		BiomeDefaultFeatures.addDefaultMushrooms(biomeBuilder);
		BiomeDefaultFeatures.addBadlandExtraVegetation(biomeBuilder);

		biomeBuilder.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS,
				ModPlacedFeatures.COARSE_DIRT_PATCH_PLACED_KEY);
		biomeBuilder.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS,
				ModPlacedFeatures.STONE_PATCH_PLACED_KEY);
		biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION,
				ModPlacedFeatures.PETRIFIED_TREE_PLACED_KEY);
		biomeBuilder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ModPlacedFeatures.ZIRCON_ORE_PLACED_KEY);
		biomeBuilder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ModPlacedFeatures.CALCITE_ORE_PLACED_KEY);

		int skyColor = Utilities.toHexColor(110, 177, 255);
		int fogColor = Utilities.toHexColor(192, 216, 255);
		int waterColor = Utilities.toHexColor(102, 111, 122);
		int waterFogColor = Utilities.toHexColor(152, 129, 123);
		int grassColor = Utilities.toHexColor(175, 126, 65);
		int foliageColor = Utilities.toHexColor(158, 129, 77);

		float temperature = 2.0f;
		float downfall = 0.0f;
		boolean hasPrecipitation = false;

		return new Biome.BiomeBuilder().hasPrecipitation(hasPrecipitation).downfall(downfall).temperature(temperature)
				.generationSettings(biomeBuilder.build()).mobSpawnSettings(spawnBuilder.build())
				.specialEffects((new BiomeSpecialEffects.Builder()).waterColor(waterColor).waterFogColor(waterFogColor)
						.skyColor(skyColor).grassColorOverride(grassColor).foliageColorOverride(foliageColor)
						.fogColor(fogColor).ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
						.backgroundMusic(Musics.GAME).build())
				.build();

	}

}
