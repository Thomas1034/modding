package com.thomas.zirconmod.util;

import com.thomas.zirconmod.block.ModBlocks;
import com.thomas.zirconmod.entity.ModEntities;
import com.thomas.zirconmod.entity.custom.WoodGolemEntity;
import com.thomas.zirconmod.entity.variant.WoodGolemVariant;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction.Axis;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CarvedPumpkinBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathComputationType;

public abstract class WoodGolemPlacer {

	// Returns the pattern of a wood golem, oriented on the x-plane
	private static Block[][][] getWoodGolemPattern(Block material) {
		Block[][][] pattern = {
				// x=-1
				{
						// y=-1
						{
								// z=-1
								Blocks.AIR,
								// z=0
								Blocks.AIR,
								// z=1
								Blocks.AIR },
						// y=0
						{
								// z=-1
								Blocks.AIR,
								// z=0
								Blocks.AIR,
								// z=1
								Blocks.AIR },
						// y=1
						{
								// z=-1
								Blocks.AIR,
								// z=0
								Blocks.AIR,
								// z=1
								Blocks.AIR } },
				// x=0
				{
						// y=-1
						{
								// z=-1
								Blocks.AIR,
								// z=0
								material,
								// z=1
								Blocks.AIR },
						// y=0
						{
								// z=-1
								material,
								// z=0
								material,
								// z=1
								material },
						// y=1
						{
								// z=-1
								Blocks.AIR,
								// z=0
								Blocks.CARVED_PUMPKIN,
								// z=1
								Blocks.AIR } },
				// x=1
				{
						// y=-1
						{
								// z=-1
								Blocks.AIR,
								// z=0
								Blocks.AIR,
								// z=1
								Blocks.AIR },
						// y=0
						{
								// z=-1
								Blocks.AIR,
								// z=0
								Blocks.AIR,
								// z=1
								Blocks.AIR },
						// y=1
						{
								// z=-1
								Blocks.AIR,
								// z=0
								Blocks.AIR,
								// z=1
								Blocks.AIR } } };

		return pattern;
	}

	public static boolean isCarvedPumpkin(BlockState state) {
		return state.is(Blocks.CARVED_PUMPKIN);
	}

	public static boolean isWoodGolem(LevelAccessor level, BlockPos pos, BlockState state) {
		boolean isCorrectPlacement = true;
		// Check if the placed block is a carved pumpkin. If not, it's not a golem.
		if (!isCarvedPumpkin(state))
			return false;

		// Gets the axis of the pumpkin.
		Axis axis = state.getValue(CarvedPumpkinBlock.FACING).getAxis();

		// Checks if the block beneath the pumpkin is a wood log.
		BlockState heart = getRelative(level, pos, 0, -1, 0);
		// If it isn't, return false.
		if (!heart.is(BlockTags.LOGS)) {
			return false;
		}

		// Gets the pattern for a wood golem, with the correct material.
		Block[][][] pattern = getWoodGolemPattern(heart.getBlock());

		if (axis == Axis.X) {
			// The pumpkin is along the x-axis.

			// Checks if the pattern matches.
			for (int i = -1; i <= 1; i++) {
				for (int j = -2; j <= 0; j++) {
					for (int k = -1; k <= 1; k++) {
						Block match = pattern[i + 1][j + 2][k + 1];
						if (match == Blocks.AIR)
							isCorrectPlacement = isCorrectPlacement && getRelative(level, pos, i, j, k)
									.isPathfindable(level, pos.offset(i, j, k), PathComputationType.LAND);
						else
							isCorrectPlacement = isCorrectPlacement && getRelative(level, pos, i, j, k).is(match);

					}
				}
			}
		} else if (axis == Axis.Z) {
			// The pumpkin is along the z-axis.

			// Checks if the pattern matches. Swaps the x and z variables in the pattern.
			for (int i = -1; i <= 1; i++) {
				for (int j = -2; j <= 0; j++) {
					for (int k = -1; k <= 1; k++) {
						Block match = pattern[k + 1][j + 2][i + 1];
						if (match == Blocks.AIR)
							isCorrectPlacement = isCorrectPlacement && getRelative(level, pos, i, j, k)
									.isPathfindable(level, pos.offset(i, j, k), PathComputationType.LAND);
						else
							isCorrectPlacement = isCorrectPlacement && getRelative(level, pos, i, j, k).is(match);

					}
				}
			}
		} else {
			isCorrectPlacement = false;
		}

		return isCorrectPlacement;
	}

	// Returns the state of the block at the relative position (i, j, k).
	public static BlockState getRelative(LevelAccessor level, BlockPos pos, int i, int j, int k) {
		BlockPos newPos = new BlockPos(pos.getX() + i, pos.getY() + j, pos.getZ() + k);
		return level.getBlockState(newPos);
	}

	// Checks if the block and position is a valid placement for a wood golem.
	// If so, breaks the construction blocks and creates the golem.
	public static void checkAndPlaceWoodGolem(LevelAccessor levelAccessor, BlockPos pos, BlockState state) {
		// If it is a wood golem, destroy all construction blocks and spawn a wood
		// golem.
		if (isWoodGolem(levelAccessor, pos, state)) {
			// Stores the wood type
			BlockState log = getRelative(levelAccessor, pos, 0, -1, 0);

			// Gets the axis of the pumpkin.
			Axis axis = state.getValue(CarvedPumpkinBlock.FACING).getAxis();

			// Breaks the frame of blocks.
			if (axis == Axis.X) {
				BlockPos newPos;
				newPos = pos.offset(0, 0, 0);
				levelAccessor.destroyBlock(newPos, false);
				newPos = pos.offset(0, -1, 0);
				levelAccessor.destroyBlock(newPos, false);
				newPos = pos.offset(0, -1, 1);
				levelAccessor.destroyBlock(newPos, false);
				newPos = pos.offset(0, -1, -1);
				levelAccessor.destroyBlock(newPos, false);
				newPos = pos.offset(0, -2, 0);
				levelAccessor.destroyBlock(newPos, false);
			} else if (axis == Axis.Z) {
				BlockPos newPos;
				newPos = pos.offset(0, 0, 0);
				levelAccessor.destroyBlock(newPos, false);
				newPos = pos.offset(0, -1, 0);
				levelAccessor.destroyBlock(newPos, false);
				newPos = pos.offset(1, -1, 0);
				levelAccessor.destroyBlock(newPos, false);
				newPos = pos.offset(-1, -1, 0);
				levelAccessor.destroyBlock(newPos, false);
				newPos = pos.offset(0, -2, 0);
				levelAccessor.destroyBlock(newPos, false);
			}

			// Places a wood golem.
			WoodGolemEntity golem = ModEntities.WOOD_GOLEM_ENTITY.get().create((Level) levelAccessor);
			golem.absMoveTo(pos.getX() + 0.5, pos.getY() - 2, pos.getZ() + 0.5, 0.0f, 0.0f);

			// Gets the type to set it to.
			golem.setTypeVariant(matchLogToType(log));
			// Adds the golem
			levelAccessor.addFreshEntity(golem);
		}
	}

	// Returns the wood golem variant that matches the given log.
	public static WoodGolemVariant matchLogToType(BlockState log) {
		if (log.is(BlockTags.OAK_LOGS))
			return WoodGolemVariant.OAK;
		else if (log.is(BlockTags.ACACIA_LOGS))
			return WoodGolemVariant.ACACIA;
		else if (log.is(BlockTags.BIRCH_LOGS))
			return WoodGolemVariant.BIRCH;
		else if (log.is(BlockTags.DARK_OAK_LOGS))
			return WoodGolemVariant.DARK_OAK;
		else if (log.is(BlockTags.JUNGLE_LOGS))
			return WoodGolemVariant.JUNGLE;
		else if (log.is(BlockTags.MANGROVE_LOGS))
			return WoodGolemVariant.MANGROVE;
		else if (log.is(BlockTags.SPRUCE_LOGS))
			return WoodGolemVariant.SPRUCE;
		else if (log.is(BlockTags.CHERRY_LOGS))
			return WoodGolemVariant.CHERRY;
		else if (log.is(ModTags.Blocks.PALM_LOGS))
			return WoodGolemVariant.PALM;

		return WoodGolemVariant.OAK;
	}

}
