package com.thomas.verdant.growth;

import com.thomas.verdant.block.ModBlocks;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public interface VerdantGrower {

	default float growthChance() {
		return 1.0f;
	}

	default BlockPos findNextGrowthLocation(Level level, BlockPos pos) {

		return pos;
	}

	abstract void grow(BlockState state, Level level, BlockPos pos);

	default void erode(Level level, BlockPos pos) {
		level.setBlockAndUpdate(pos, Eroder.getNext(level.getBlockState(pos)));
	}

	public static BlockPos withinDist(BlockPos pos, int dist, RandomSource rand) {

		return pos.offset(rand.nextIntBetweenInclusive(-dist, dist), rand.nextIntBetweenInclusive(-dist, dist),
				rand.nextIntBetweenInclusive(-dist, dist));

	}

	// Converts a block to a verdant form if possible.
	// If not, returns false.
	public static boolean convert(Level level, BlockPos pos) {

		BlockState atPos = level.getBlockState(pos);

		if (atPos.is(BlockTags.DIRT)) {
			if (level.getBlockState(pos.above()).isAir()) {
				level.setBlockAndUpdate(pos, ModBlocks.VERDANT_GRASS_BLOCK.get().defaultBlockState());

			} else {
				// Check for surrounding rooted dirt blocks.
				int rootCount = 0;
				for (int i = -1; i <= 1; i++) {
					for (int j = -1; j <= 1; j++) {
						for (int k = -1; k <= 1; k++) {
							if (level.getBlockState(pos.offset(i, j, k)).is(ModBlocks.VERDANT_ROOTED_DIRT.get())) {
								rootCount++;
							}
						}
					}
				}
				// Maximum of 12 blocks.
				if (rootCount <= 12) {
					level.setBlockAndUpdate(pos, ModBlocks.VERDANT_ROOTED_DIRT.get().defaultBlockState());
				}

			}
		}

		return false;
	}

}
