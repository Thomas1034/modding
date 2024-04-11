package com.thomas.cloudscape.worldgen.custom;

import com.mojang.serialization.Codec;
import com.thomas.cloudscape.block.ModBlocks;
import com.thomas.cloudscape.util.OpenSimplexNoise;
import com.thomas.cloudscape.util.Utilities;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class SimplexCloudFeature extends Feature<NoneFeatureConfiguration> {

	private static final BlockState CLOUD = ModBlocks.CLOUD.get().defaultBlockState();
	private static final BlockState THUNDER_CLOUD = ModBlocks.THUNDER_CLOUD.get().defaultBlockState();
	private final double threshold;
	private final long seed;

	public SimplexCloudFeature(Codec<NoneFeatureConfiguration> codec, double threshold, long seed) {
		super(codec);
		this.threshold = threshold;
		this.seed = seed;
	}

	// Places a small cloud.
	@Override
	public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
		WorldGenLevel level = context.level();
		BlockPos pos = context.origin();
		//ChunkGenerator chunks = context.chunkGenerator();

		placeSimplexNoiseCloud(level.getSeed() ^ this.seed, level, pos, this.threshold, 32, 128);

		return true;

	}

	// Places a simplex-noise-based cloud, if the biome at that position
	// has the specified feature.
	@SuppressWarnings("deprecation")
	public static void placeSimplexNoiseCloud(long seed, LevelAccessor level, BlockPos anchor, double threshold, int min, int max) {

		// If there is no chunk here, skip it.
		if (!level.hasChunkAt(anchor)) {
			System.out.println("Skipping " + anchor.getX() + " " + anchor.getY() + " " + anchor.getZ()
					+ " since it has not been generated yet.");
			return;
		}

		ChunkPos thisChunk = level.getChunk(anchor).getPos();

		OpenSimplexNoise noise = new OpenSimplexNoise(seed);
		
		double denom = (min - max);
		double offsetTerm = (min + max) / denom;
		
		double rarity = 30;

		// Iterate over the entire chunk.
		for (int i = 0; i < 16; i++) {
			for (int j = level.getMinBuildHeight(); j < level.getMaxBuildHeight(); j++) {
				for (int k = 0; k < 16; k++) {
					// The coordinates for noise generation.
					double x = (i + anchor.getX()) * 0.0125;
					double y = (j + anchor.getY()) * 0.05;
					double z = (k + anchor.getZ()) * 0.0125;
					
					double modifiedThreshold = 1/(Math.pow(-2 * j / denom + offsetTerm, 16) + 1);
					if (noise.eval(x, y, z) * modifiedThreshold > (threshold)) {
						Utilities.setSafe(level, thisChunk.getBlockAt(i, j, k), CLOUD);
					}
					
					if (noise.eval(x, y, z) * modifiedThreshold <  ((threshold + 1) / rarity - 1)) {
						Utilities.setSafe(level, thisChunk.getBlockAt(i, j, k), THUNDER_CLOUD);
					}

				}
			}
		}
	}

}
