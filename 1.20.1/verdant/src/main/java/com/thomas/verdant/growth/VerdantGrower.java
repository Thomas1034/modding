package com.thomas.verdant.growth;

import com.thomas.verdant.block.ModBlocks;
import com.thomas.verdant.block.custom.VerdantVineBlock;
import com.thomas.verdant.util.ModTags;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.lighting.LightEngine;

public interface VerdantGrower extends BonemealableBlock {

	public static final float MULTI_ERODE_CHANCE = 0.25f;

	default float growthChance() {
		return 1.0f;
	}

	abstract void grow(BlockState state, Level level, BlockPos pos);

	default void erode(Level level, BlockPos pos, boolean isNearWater) {
		erodeStatic(level, pos, isNearWater);
	}

	public static boolean erodeStatic(Level level, BlockPos pos, boolean isNearWater) {
		BlockState state = level.getBlockState(pos);
		BlockState next = VerdantEroder.getNext(state);
		BlockState nextIfWet = VerdantEroder.getNext(next);
		if (state == next && !(isNearWater && state != nextIfWet)) {
			return false;
		}
		if (isNearWater) {
			level.setBlockAndUpdate(pos, nextIfWet);
		} else {
			level.setBlockAndUpdate(pos, next);
		}
		// Chance to recurse.
		if (level.random.nextFloat() < MULTI_ERODE_CHANCE) {
			erodeStatic(level, pos, isNearWater);
		}
		return true;
	};

	public static BlockPos withinDist(BlockPos pos, int dist, RandomSource rand) {
		return pos.offset(rand.nextIntBetweenInclusive(-dist, dist), rand.nextIntBetweenInclusive(-dist, dist),
				rand.nextIntBetweenInclusive(-dist, dist));
	}

	public static BlockPos withinSphereDist(BlockPos pos, int dist, RandomSource rand) {

		int xOffset = 0;
		int yOffset = 0;
		int zOffset = 0;
		do {
			xOffset = rand.nextIntBetweenInclusive(-dist, dist);
			yOffset = rand.nextIntBetweenInclusive(-dist, dist);
			zOffset = rand.nextIntBetweenInclusive(-dist, dist);
			;
		} while (xOffset * xOffset + yOffset * yOffset + zOffset * zOffset <= dist * dist);

		return pos.offset(xOffset, yOffset, zOffset);
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

	public static void replaceLeavesWithVine(Level level, BlockPos pos) {

		// Store the previous block there.
		BlockState replaced = level.getBlockState(pos);
		if (replaced.is(ModBlocks.VERDANT_LEAVES.get()) && VerdantVineBlock.canGrowToAnyFace(level, pos)) {
			// //System.out.println("Converting to leafy vines.");
			// Grow to leafy vines.
			// Place the vine block there.
			BlockState placed = ModBlocks.LEAFY_VERDANT_VINE.get().defaultBlockState();

			// Find every direction it can grow there.
			for (Direction d : Direction.values()) {
				if (VerdantVineBlock.canGrowToFace(level, pos, d)) {
					placed = placed.setValue(VerdantVineBlock.SIDES.get(d), 1);
				}
			}
			// Waterlog if possible
			if (replaced.hasProperty(BlockStateProperties.WATERLOGGED)) {
				if (replaced.getValue(BlockStateProperties.WATERLOGGED)) {
					placed = placed.setValue(BlockStateProperties.WATERLOGGED, true);
				}
			}
			// Update the block.
			// level.destroyBlock(pos, false);
			// System.out.println("Replacing leaves with " + placed + ".");
			level.setBlockAndUpdate(pos, placed);
		}
	}

	public static void replaceLeafyVineWithVine(Level level, BlockPos pos) {
		// Store the previous block there.
		BlockState replaced = level.getBlockState(pos);
		if (replaced.is(ModBlocks.LEAFY_VERDANT_VINE.get()) && VerdantVineBlock.canGrowToAnyFace(level, pos)) {
			// //System.out.println("Converting to leafless vine.");
			// Grow to leafy vines.
			// Place the vine block there.
			BlockState placed = ModBlocks.VERDANT_VINE.get().defaultBlockState();

			// Find every direction it can grow there.
			for (Direction d : Direction.values()) {
				if (VerdantVineBlock.canGrowToFace(level, pos, d)) {
					placed = placed.setValue(VerdantVineBlock.SIDES.get(d), 1);
				}
			}
			// Waterlog if possible
			if (replaced.hasProperty(BlockStateProperties.WATERLOGGED)) {
				if (replaced.getValue(BlockStateProperties.WATERLOGGED)) {
					placed = placed.setValue(BlockStateProperties.WATERLOGGED, true);
				}
			}
			// Update the block.
			// level.destroyBlock(pos, false);
			// System.out.println("Replacing leafy vines with " + placed + ".");
			level.setBlockAndUpdate(pos, placed);
		}
	}

	// Returns true if it succeeded in converting the leaves.
	public static boolean convertLeaves(Level level, BlockPos pos) {
		// //System.out.println("Attempting to convert leaves at " + pos + ".");
		BlockState replaced = level.getBlockState(pos);
		BlockState placed = ModBlocks.VERDANT_LEAVES.get().defaultBlockState();

		// If the target is already leaves, don't do anything.
		if (replaced.is(ModTags.Blocks.VERDANT_LEAFY_BLOCKS)) {
			return false;
		}

		// Waterlog if possible
		if (replaced.hasProperty(BlockStateProperties.WATERLOGGED)) {
			if (replaced.getValue(BlockStateProperties.WATERLOGGED)) {
				// //System.out.println("Waterlogging.");
				placed = placed.setValue(BlockStateProperties.WATERLOGGED, true);
			}
		}
		// Update distance if possible
		if (replaced.hasProperty(LeavesBlock.DISTANCE)) {
			// //System.out.println("Setting distance.");
			placed = placed.setValue(LeavesBlock.DISTANCE, replaced.getValue(LeavesBlock.DISTANCE));
		}

		if (replaced.is(BlockTags.LEAVES)) {
			// System.out.println("Converting to " + placed + ".");
			level.addDestroyBlockEffect(pos, replaced);
			level.setBlockAndUpdate(pos, placed);
			return true;
		}
		// //System.out.println("Failed to convert.");
		return false;
	}

	// Converts a block to a verdant form if possible.
	// If not, returns false.
	public static boolean convertGround(Level level, BlockPos pos, boolean isNearWater) {
		// //System.out.println("Attempting to convert " + pos);
		BlockState atPos = level.getBlockState(pos);

		if (VerdantRootGrower.isRootable(atPos)) {
			BlockState rooted = VerdantRootGrower.getRooted(atPos);
			if (VerdantGrower.canBeGrass(atPos, level, pos) && VerdantGrassGrower.isGrassable(rooted)) {
				// System.out.println("Placing grass: " + rooted);
				level.setBlockAndUpdate(pos, rooted);

			} else {
				// Check for surrounding rooted blocks.
				// //System.out.println("Attempting to place roots.");
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
				// Maximum of 3 blocks.
				// //System.out.println("Root count is " + rootCount);
				if (rootCount <= 3) {
					// System.out.println("Placing roots: " + rooted);
					level.setBlockAndUpdate(pos, rooted);
					level.scheduleTick(pos, rooted.getBlock(), 1);
				}

			}
		} else {
			// //System.out.println(atPos + " is not a valid place to grow.");
		}

		return false;
	}

	@Override
	default boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state, boolean unknownBool) {
		return true;
	}

	@Override
	default boolean isBonemealSuccess(Level level, RandomSource rand, BlockPos pos, BlockState state) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	default void performBonemeal(ServerLevel level, RandomSource rand, BlockPos pos, BlockState state) {
		this.grow(state, level, pos);
	}

}
