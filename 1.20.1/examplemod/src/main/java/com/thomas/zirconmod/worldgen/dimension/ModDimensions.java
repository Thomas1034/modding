package com.thomas.zirconmod.worldgen.dimension;

import java.util.List;
import java.util.OptionalLong;

import com.mojang.datafixers.util.Pair;
import com.thomas.zirconmod.ZirconMod;
import com.thomas.zirconmod.worldgen.biome.ModBiomes;
import com.thomas.zirconmod.worldgen.dimension.noise.ModNoiseSettings;

import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.biome.MultiNoiseBiomeSource;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;

public class ModDimensions {
	public static final ResourceKey<LevelStem> SKY_DIM_KEY = ResourceKey.create(Registries.LEVEL_STEM,
			new ResourceLocation(ZirconMod.MOD_ID, "sky_dim"));
	public static final ResourceKey<Level> SKY_DIM_LEVEL_KEY = ResourceKey.create(Registries.DIMENSION,
			new ResourceLocation(ZirconMod.MOD_ID, "sky_dim"));
	public static final ResourceKey<DimensionType> SKY_DIM_TYPE = ResourceKey.create(Registries.DIMENSION_TYPE,
			new ResourceLocation(ZirconMod.MOD_ID, "sky_type"));

	public static void bootstrapType(BootstapContext<DimensionType> context) {
		context.register(SKY_DIM_TYPE, new DimensionType(OptionalLong.empty(), // fixedTime
				true, // hasSkylight
				false, // hasCeiling
				false, // ultraWarm
				true, // natural
				1.0, // coordinateScale
				true, // bedWorks
				false, // respawnAnchorWorks
				-64, // minY
				320, // height
				320, // logicalHeight
				BlockTags.INFINIBURN_OVERWORLD, // infiniburn
				BuiltinDimensionTypes.OVERWORLD_EFFECTS, // effectsLocation
				0.0f, // ambientLight
				new DimensionType.MonsterSettings(false, true, UniformInt.of(0, 7), 0)));
	}

	public static void bootstrapStem(BootstapContext<LevelStem> context) {
		HolderGetter<Biome> biomeRegistry = context.lookup(Registries.BIOME);
		HolderGetter<DimensionType> dimTypes = context.lookup(Registries.DIMENSION_TYPE);
		HolderGetter<NoiseGeneratorSettings> noiseGenSettings = context.lookup(Registries.NOISE_SETTINGS);

		NoiseBasedChunkGenerator noiseBasedChunkGenerator = new NoiseBasedChunkGenerator(
				MultiNoiseBiomeSource.createFromList(new Climate.ParameterList<>(List.of(
						Pair.of(Climate.parameters(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F),
								biomeRegistry.getOrThrow(ModBiomes.CLEAR_SKY_BIOME)),
						Pair.of(Climate.parameters(-0.3F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F),
								biomeRegistry.getOrThrow(ModBiomes.CLOUDY_SKY_BIOME)),
						Pair.of(Climate.parameters(0.5F, 0.0F, 0.0F, 0.1F, 0.0F, 0.0F, 0.0F),
								biomeRegistry.getOrThrow(Biomes.DESERT)),
						Pair.of(Climate.parameters(0.6F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F),
								biomeRegistry.getOrThrow(Biomes.JAGGED_PEAKS))

				))), noiseGenSettings.getOrThrow(ModNoiseSettings.SKY));

		LevelStem stem = new LevelStem(dimTypes.getOrThrow(ModDimensions.SKY_DIM_TYPE), noiseBasedChunkGenerator);

		context.register(SKY_DIM_KEY, stem);
	}
}
