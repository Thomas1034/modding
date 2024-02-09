package com.thomas.zirconmod.worldgen.biome.custom;

import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.biome.MobSpawnSettings.Builder;

public class ModBiomeFeatures {

	public static void commonSkySpawns(MobSpawnSettings.Builder builder) {
		skySpawns(builder);
		BiomeDefaultFeatures.monsters(builder, 95, 5, 100, false);
	}

	public static void skySpawns(Builder builder) {
		builder.addSpawn(MobCategory.AMBIENT, new MobSpawnSettings.SpawnerData(EntityType.BAT, 10, 8, 8));
		builder.addSpawn(MobCategory.MONSTER,
				new MobSpawnSettings.SpawnerData(EntityType.PHANTOM, 10, 4, 6));
	}

}
