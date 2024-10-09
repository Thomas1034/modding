package com.thomas.verdant.growth;

import com.thomas.verdant.block.ModBlocks;
import com.thomas.verdant.block.custom.VerdantVineBlock;
import com.thomas.verdant.util.ModTags;
import com.thomas.verdant.util.blocktransformers.BlockTransformer;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

public interface VerdantGrower extends BonemealableBlock {

	public static final float MULTI_ERODE_CHANCE = 0.25f;
	public static final int MAX_ROOTS = 3;
	static BlockTransformer EROSION = null;
	static BlockTransformer EROSION_WET = null;

	default float growthChance(Level level) {
		return level == null ? 1.0f : SpreadAmount.getAmount(level);
	}

	abstract void grow(BlockState state, Level level, BlockPos pos);

	default void erode(Level level, BlockPos pos, boolean isNearWater) {
		erodeStatic(level, pos, isNearWater);
	}

	public static boolean erodeStatic(Level level, BlockPos pos, boolean isNearWater) {
		BlockState state = level.getBlockState(pos);
		BlockTransformer transformer = isNearWater ? VerdantBlockTransformer.EROSION_WET.get()
				: VerdantBlockTransformer.EROSION.get();
		if (!transformer.hasInput(state.getBlock())) {
			return false;
		}
		BlockState next = transformer.next(state);

		level.setBlockAndUpdate(pos, next);
		return true;

	};

	// Converts a block to a verdant form if possible.
	// If not, erodes it. Returns false if no change was made..
	public static boolean convertOrErodeGround(Level level, BlockPos pos, boolean isNearWater) {
		BlockState state = level.getBlockState(pos);
		Block block = state.getBlock();
		BlockTransformer erosion = isNearWater ? VerdantBlockTransformer.EROSION_WET.get()
				: VerdantBlockTransformer.EROSION.get();
		boolean change = false;
		if (erosion.hasInput(block)) {
			state = erosion.next(state);
			change = true;
		} else {
			BlockTransformer roots = VerdantBlockTransformer.ROOTS.get();
			if (roots.hasInput(block)) {
				state = roots.next(state);
				change = true;
			}
		}
		if (change) {
			level.setBlockAndUpdate(pos, state);
		}
		return change;
	}

	// Returns true if it can grow.
	public static boolean checkForRoots(Level level, BlockPos pos) {
		BlockTransformer roots = VerdantBlockTransformer.ROOTS.get();
		int rootCount = 0;
		for (int i = -1; i <= 1; i++) {
			for (int j = -1; j <= 1; j++) {
				for (int k = -1; k <= 1; k++) {
					if (roots.hasOutput(level.getBlockState(pos.offset(i, j, k)).getBlock())) {
						if (++rootCount > MAX_ROOTS) {
							return false;
						}
					}
				}
			}
		}

		return rootCount <= MAX_ROOTS;
	}

	public static BlockPos withinDist(BlockPos pos, int dist, RandomSource rand) {
		int num = rand.nextInt();
		int range = 2 * dist + 1;
		int int1 = num & 0xFF; // Use the lowest 7 bits
		int int2 = (num >> 7) & 0xFF; // Use the next 7 bits
		int int3 = (num >> 14) & 0xFF; // Use the next 7 bits after that

		int offset1 = (int1 * range) / 0xFF - dist;
		int offset2 = (int2 * range) / 0xFF - dist;
		int offset3 = (int3 * range) / 0xFF - dist;

		return pos.offset(offset1, offset2, offset3);
	}

	public static BlockPos withinSphereDist(BlockPos pos, int dist, RandomSource rand) {

		int xOffset = 0;
		int yOffset = 0;
		int zOffset = 0;
		do {
			xOffset = rand.nextIntBetweenInclusive(-dist, dist);
			yOffset = rand.nextIntBetweenInclusive(-dist, dist);
			zOffset = rand.nextIntBetweenInclusive(-dist, dist);

		} while (xOffset * xOffset + yOffset * yOffset + zOffset * zOffset <= dist * dist);

		return pos.offset(xOffset, yOffset, zOffset);
	}

	// From the grass block class, except no snow and no underwater.
	// EDIT: now only checks if the block above is not full and is not fluid.
	// This could lead to jank with things like stairs and slabs growing grass
	// underneath, but should be *mostly* harmless.
	public static boolean canBeGrass(LevelReader level, BlockPos pos) {
		BlockPos abovePos = pos.above();
		BlockState aboveState = level.getBlockState(abovePos);
		if (aboveState.getFluidState().getAmount() > 0) {
			return false;
		} else {
			return !aboveState.isCollisionShapeFullBlock(level, abovePos);
//			int i = LightEngine.getLightBlockInto(level, state, pos, aboveState, abovePos, Direction.UP,
//					aboveState.getLightBlock(level, abovePos));
//			return i < level.getMaxLightLevel();
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

	public static boolean replaceLeafyVineWithVine(Level level, BlockPos pos) {
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
			return level.setBlockAndUpdate(pos, placed);
		}
		return false;
	}

	// Returns true if it succeeded in converting the leaves.
	public static boolean convertLeaves(Level level, BlockPos pos) {
		// //System.out.println("Attempting to convert leaves at " + pos + ".");
		BlockState replaced = level.getBlockState(pos);
		BlockState placed = ModBlocks.VERDANT_LEAVES.get().defaultBlockState();

		// If the target is already verdant leaves, don't do anything.
		if (replaced.is(ModTags.Blocks.VERDANT_LEAFY_BLOCKS)) {
			return false;
		}

		// Don't do anything if it isn't leaves already.
		if (replaced.is(BlockTags.LEAVES)) {
			// Waterlog if possible
			if (replaced.hasProperty(BlockStateProperties.WATERLOGGED)
					&& replaced.getValue(BlockStateProperties.WATERLOGGED)) {
				// //System.out.println("Waterlogging.");
				placed = placed.setValue(BlockStateProperties.WATERLOGGED, true);
			}
			// Update distance if possible
			if (replaced.hasProperty(LeavesBlock.DISTANCE)) {
				// //System.out.println("Setting distance.");
				placed = placed.setValue(LeavesBlock.DISTANCE, replaced.getValue(LeavesBlock.DISTANCE));
			}

			// System.out.println("Converting to " + placed + ".");
			level.addDestroyBlockEffect(pos, replaced);
			level.setBlockAndUpdate(pos, placed);
			return true;
		}

		return false;
	}

	// Converts a block to a verdant form if possible.
	// If not, returns false.
	public static boolean convertGround(Level level, BlockPos pos) {
		// //System.out.println("Attempting to convert " + pos);
		BlockState state = level.getBlockState(pos);
		BlockTransformer roots = VerdantBlockTransformer.ROOTS.get();
		if (roots.hasInput(state.getBlock())) {
			BlockState rooted = roots.next(state);
			level.setBlockAndUpdate(pos, rooted);
			return true;
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
