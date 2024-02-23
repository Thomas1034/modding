package com.thomas.zirconmod.worldgen.biome.custom;

import com.thomas.zirconmod.util.Utilities;
import com.thomas.zirconmod.worldgen.ModPlacedFeatures;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.sounds.Musics;
import net.minecraft.world.level.biome.AmbientMoodSettings;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;

public class CloudySkyBiome {
	public static final String NAME = "clear_skies";

	public static Biome build(BootstapContext<Biome> context) {
		// ForgeRegistries.BIOMES.getDefaultKey();

		MobSpawnSettings.Builder spawnBuilder = new MobSpawnSettings.Builder();

		ModBiomeFeatures.commonSkySpawns(spawnBuilder);

		BiomeGenerationSettings.Builder biomeBuilder = new BiomeGenerationSettings.Builder(
				context.lookup(Registries.PLACED_FEATURE), context.lookup(Registries.CONFIGURED_CARVER));

		biomeBuilder.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS,
				ModPlacedFeatures.SMALL_CLOUD_PLACED_KEY);
		biomeBuilder.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS,
				ModPlacedFeatures.THICK_CLOUD_CEILING_PLACED_KEY);

		int skyColor = Utilities.toHexColor(110, 177, 255);
		int fogColor = Utilities.toHexColor(110, 177, 255);
		int waterColor = Utilities.toHexColor(18, 10, 143);
		int waterFogColor = Utilities.toHexColor(18, 10, 243);
		int grassColor = Utilities.toHexColor(0, 154, 68);
		int foliageColor = Utilities.toHexColor(4, 106, 56);

		float temperature = 1.0f;
		float downfall = 0.0f;
		boolean hasPrecipitation = true;

		return new Biome.BiomeBuilder().hasPrecipitation(hasPrecipitation).downfall(downfall).temperature(temperature)
				.generationSettings(biomeBuilder.build()).mobSpawnSettings(spawnBuilder.build())
				.specialEffects((new BiomeSpecialEffects.Builder()).waterColor(waterColor).waterFogColor(waterFogColor)
						.skyColor(skyColor).grassColorOverride(grassColor).foliageColorOverride(foliageColor)
						.fogColor(fogColor).ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
						.backgroundMusic(Musics.GAME).build())
				.build();

	}

}
