package com.thomas.zirconmod.worldgen.custom;

import com.mojang.serialization.Codec;
import com.thomas.zirconmod.util.CloudNoise;
import com.thomas.zirconmod.util.OpenSimplexNoise;
import com.thomas.zirconmod.util.Utilities;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class CloudFloorFeature extends Feature<NoneFeatureConfiguration> {

	private final ResourceKey<Biome> filter;
	private final BlockState state;

	public CloudFloorFeature(Codec<NoneFeatureConfiguration> codec, ResourceKey<Biome> filter, BlockState state) {
		super(codec);
		this.filter = filter;
		this.state = state;
	}

	// Places a cloud layer.
	@Override
	public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
		WorldGenLevel level = context.level();
		BlockPos pos = context.origin();
		// ChunkGenerator chunks = context.chunkGenerator();

		placeSimplexNoiseLayer(level.getSeed(), level, pos, 16, this.state, this.filter);

		return true;

	}

	// Places a solid layer of cloud, if the biome at that position has the
	// specified feature.
	@SuppressWarnings({ "deprecation" })
	public static void placeLayer(LevelAccessor level, BlockPos determinator, BlockState state,
			ResourceKey<Biome> biome) {

		// If there is no chunk here, skip it.
		if (!level.hasChunkAt(determinator)) {
			System.out.println(
					"Skipping " + determinator.getX() + " " + determinator.getY() + " " + determinator.getZ() + " ");
			return;
		}

		ChunkPos thisChunk = level.getChunk(determinator).getPos();

		// Iterate over the chunk, checking the biome and placing each time.
		for (int i = 0; i < 16; i++) {
			for (int j = 0; j < 16; j++) {
				BlockPos here = thisChunk.getBlockAt(i, determinator.getY(), j);
				// Check the biome.
				boolean isCorrectBiome = level.getBiome(here).is(biome);
				if (isCorrectBiome) {
					Utilities.setSafe(level, here, state);
				}
			}
		}

	}

	// Places a custom-noise-based layer of cloud, if the biome at that position has
	// the
	// specified feature.
	@SuppressWarnings("deprecation")
	public static void placeCloudNoiseLayer(long seed, LevelAccessor level, BlockPos anchor, int thickness,
			BlockState state, ResourceKey<Biome> biome) {

		// If there is no chunk here, skip it.
		if (!level.hasChunkAt(anchor)) {
			System.out.println("Skipping " + anchor.getX() + " " + anchor.getY() + " " + anchor.getZ()
					+ " since it has not been generated yet.");
			return;
		}

		ChunkPos thisChunk = level.getChunk(anchor).getPos();

		// Iterate over the chunk, checking the biome and placing each time.
		for (int i = 0; i < 16; i++) {
			for (int j = 0; j < 16; j++) {

				BlockPos here = thisChunk.getBlockAt(i, anchor.getY(), j);
				// For above the middle point
				int aboveRange = CloudNoise.at(seed, here, thickness);
				// For below the middle point.
				// The rotate just gets a different value that's hard to figure out by simple
				// observation.
				int belowRange = CloudNoise.at(Long.rotateRight(seed, 27), here, thickness);

				// Iterate over the vertical range.
				for (int k = -belowRange; k < aboveRange; k++) {
					BlockPos verticalPos = here.above(k);
					// Check the biome.
					boolean isCorrectBiome = level.getBiome(verticalPos).is(biome);
					if (isCorrectBiome) {

						Utilities.setSafe(level, verticalPos, state);
					}
				}
			}
		}
	}

	// Places a simplex-noise-based layer of cloud, if the biome at that position has
	// the
	// specified feature.
	@SuppressWarnings("deprecation")
	public static void placeSimplexNoiseLayer(long seed, LevelAccessor level, BlockPos anchor, int thickness,
			BlockState state, ResourceKey<Biome> biome) {

		// If there is no chunk here, skip it.
		if (!level.hasChunkAt(anchor)) {
			System.out.println("Skipping " + anchor.getX() + " " + anchor.getY() + " " + anchor.getZ()
					+ " since it has not been generated yet.");
			return;
		}

		ChunkPos thisChunk = level.getChunk(anchor).getPos();
		
		OpenSimplexNoise noise = new OpenSimplexNoise(seed);
		
		// Iterate over the chunk, checking the biome and placing each time.
		for (int i = 0; i < 16; i++) {
			for (int j = 0; j < 16; j++) {

				BlockPos here = thisChunk.getBlockAt(i, anchor.getY(), j);
				// The coordinates for terrain generation.
				double x = here.getX() * 0.025;
				double y = here.getY() * 0.025;
				double z = here.getZ() * 0.025;
				
				// For above the middle point
				int aboveRange = (int) (thickness * (noise.eval(x, y, z) + 1) / 2);
				// For below the middle point.
				// Then rotates just to get a different value that's hard to figure out by simple
				// observation. (TODO)
				int belowRange = (int) (thickness * (noise.eval(0.5 * x + 0.5 * y, 0.5 * x - 0.5 * y, z) + 1) / 4);

				// Iterate over the vertical range.
				for (int k = -belowRange; k < aboveRange; k++) {
					BlockPos verticalPos = here.above(k);
					// Check the biome.
					boolean isCorrectBiome = level.getBiome(verticalPos).is(biome);
					if (isCorrectBiome) {

						Utilities.setSafe(level, verticalPos, state);
					}
				}
			}
		}
	}

	// Creates a layer of clouds covering the entire chunk containing the BlockPos,
	// with the BlockPos as the highest point.
	// Will only place a block at a position if the biome there supports the
	// specified feature.
	public static void placeMultiLayer(LevelAccessor level, BlockPos anchor, int thickness, BlockState state,
			ResourceKey<Biome> biome) {
		int halfThick = thickness >> 1 + (thickness & 1);
		for (int i = -halfThick + (thickness & 1); i < halfThick; i++) {
			placeLayer(level, anchor.above(i), state, biome);
		}

	}

}
