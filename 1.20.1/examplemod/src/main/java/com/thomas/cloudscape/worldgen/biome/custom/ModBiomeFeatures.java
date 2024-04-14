package com.thomas.cloudscape.worldgen.biome.custom;

import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.biome.MobSpawnSettings.Builder;

public class ModBiomeFeatures {

	public static void commonSkySpawns(MobSpawnSettings.Builder builder) {
		skySpawns(builder);
		BiomeDefaultFeatures.monsters(builder, 100, 0, 100, false);
	}

	public static void skySpawns(Builder builder) {
		builder.addSpawn(MobCategory.AMBIENT, new MobSpawnSettings.SpawnerData(EntityType.BAT, 10, 2, 8));
	}

}
