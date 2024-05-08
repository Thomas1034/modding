package com.thomas.verdant.growth;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.lighting.LightEngine;

public interface VerdantGrower {

	public static final float MULTI_ERODE_CHANCE = 0.25f;

	default float growthChance() {
		return 1.0f;
	}

	abstract void grow(BlockState state, Level level, BlockPos pos);

	default void erode(Level level, BlockPos pos, boolean isNearWater) {

		level.setBlockAndUpdate(pos, VerdantEroder.getNext(level.getBlockState(pos)));
		if (isNearWater) {
			level.setBlockAndUpdate(pos, VerdantEroder.getNextIfWet(level.getBlockState(pos)));
		}
		// Chance to recurse.
		if (level.random.nextFloat() < MULTI_ERODE_CHANCE) {
			erode(level, pos, isNearWater);
		}

	}

	public static BlockPos withinDist(BlockPos pos, int dist, RandomSource rand) {

		return pos.offset(rand.nextIntBetweenInclusive(-dist, dist), rand.nextIntBetweenInclusive(-dist, dist),
				rand.nextIntBetweenInclusive(-dist, dist));

	}

	// From the grass block class, except no snow and no underwater.
	public static boolean canBeGrass(BlockState state, LevelReader level, BlockPos pos) {
		BlockPos blockpos = pos.above();
		BlockState blockstate = level.getBlockState(blockpos);
		if (blockstate.getFluidState().getAmount() > 0) {
			return false;
		} else {
			int i = LightEngine.getLightBlockInto(level, state, pos, blockstate, blockpos, Direction.UP,
					blockstate.getLightBlock(level, blockpos));
			return i < level.getMaxLightLevel();
		}
	}

	// Converts a block to a verdant form if possible.
	// If not, returns false.
	public static boolean convert(Level level, BlockPos pos, boolean isNearWater) {
		// System.out.println("Attempting to convert " + pos);
		BlockState atPos = level.getBlockState(pos);

		if (VerdantRootGrower.isRootable(atPos)) {
			BlockState rooted = VerdantRootGrower.getRooted(atPos);
			if (VerdantGrower.canBeGrass(atPos, level, pos) && VerdantGrassGrower.isGrassable(rooted)) {
				// System.out.println("Placing grass.");
				level.setBlockAndUpdate(pos, rooted);

			} else {
				// Check for surrounding rooted blocks.
				// System.out.println("Attempting to place roots.");
				int rootCount = 0;
				for (int i = -1; i <= 1; i++) {
					for (int j = -1; j <= 1; j++) {
						for (int k = -1; k <= 1; k++) {
							if (VerdantRootGrower.isRoots(level.getBlockState(pos.offset(i, j, k)))) {
								rootCount++;
							}
						}
					}
				}
				// Maximum of 9 blocks.
				// System.out.println("Root count is " + rootCount);
				if (rootCount <= 6) {
					level.setBlockAndUpdate(pos, rooted);
				}

			}
		} else {
			// System.out.println(atPos + " is not a valid place to grow.");
		}

		return false;
	}

}
