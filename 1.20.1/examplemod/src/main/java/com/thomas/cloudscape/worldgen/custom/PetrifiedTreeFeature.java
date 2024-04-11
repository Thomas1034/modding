package com.thomas.cloudscape.worldgen.custom;

import com.mojang.serialization.Codec;
import com.thomas.cloudscape.block.ModBlocks;
import com.thomas.cloudscape.util.Utilities;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class PetrifiedTreeFeature extends Feature<NoneFeatureConfiguration> {

	public static final BlockState LOG = ModBlocks.PETRIFIED_LOG.get().defaultBlockState();

	public PetrifiedTreeFeature(Codec<NoneFeatureConfiguration> codec) {
		super(codec);
	}

	@Override
	public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
		WorldGenLevel level = context.level();
		BlockPos pos = context.origin();

		placePetrifiedTree(level, pos);

		return true;
	}

	// Places a petrified tree.
	public static void placePetrifiedTree(LevelAccessor level, BlockPos pos) {
		
		pos = Utilities.sink(level, pos);
		
		double type = level.getRandom().nextDouble();

		if (type < 0.05) {
			placeFancyTree(level, pos);
		} else {
			placeFallenTree(level, pos);
		}

	}

	// Places a fallen tree.
	private static void placeFallenTree(LevelAccessor level, BlockPos pos) {

		Direction facing = Utilities.randomHorizontalDirection(level.getRandom());
		BlockState logstate = LOG.setValue(RotatedPillarBlock.AXIS, facing.getAxis());
		Direction trend = level.getRandom().nextBoolean() ? facing.getClockWise() : facing.getCounterClockWise();

		int length = level.getRandom().nextIntBetweenInclusive(2, 8);

		for (int i = 0; i < length; i++) {

			pos = Utilities.findSurface(level, pos);

			if (!Utilities.canReplaceBlockAt(level, pos)) {
				return;
			}

			level.setBlock(pos, logstate, 3);

			pos = pos.relative(facing);

			int whichWay = level.getRandom().nextInt(4);

			switch (whichWay) {
			case 0:
				break;
			case 1:
				break;
			case 2:
				pos = pos.relative(trend);
				break;
			default:
				pos = pos.relative(facing);
				break;
			}
		}

	}

	// Places a fancy petrified tree.
	public static void placeFancyTree(LevelAccessor level, BlockPos pos) {

		pos = placeStraightTrunk(level, pos, Direction.UP, 2, 5);
		placeBranch(level, pos, Direction.NORTH, 0, 4);
		placeBranch(level, pos, Direction.EAST, 0, 4);
		placeBranch(level, pos, Direction.SOUTH, 0, 4);
		placeBranch(level, pos, Direction.WEST, 0, 4);
		placeStraightTrunk(level, pos, Direction.UP, 0, 4);

	}

	// Places a simple tree.
	public static void placeSimpleTree(LevelAccessor level, BlockPos pos) {
		pos = placeStraightTrunk(level, pos, Direction.UP, 2, 4);
		placeBranch(level, pos, Utilities.randomHorizontalDirection(level.getRandom()), 0, 2);
		placeStraightTrunk(level, pos, Direction.UP, 1, 3);
	}

	// Places a straight trunk, and returns the position of the top block.
	private static BlockPos placeStraightTrunk(LevelAccessor level, BlockPos pos, Direction facing, int minHeight,
			int maxHeight) {

		int randHeight = level.getRandom().nextIntBetweenInclusive(minHeight, maxHeight);

		for (int i = 0; i < randHeight; i++) {
			// Check if the block can be set.
			if (!Utilities.canReplaceBlockAt(level, pos)) {
				return pos;
			}

			level.setBlock(pos, LOG.setValue(RotatedPillarBlock.AXIS, facing.getAxis()), 3);
			pos = pos.relative(facing);
		}

		return pos.below();
	}

	// Places a branch starting at the given position, and returns the position of
	// the end block.
	// The facing direction must be horizontal.
	private static BlockPos placeBranch(LevelAccessor level, BlockPos pos, Direction facing, int minLength,
			int maxLength) {

		BlockState logstate = LOG.setValue(RotatedPillarBlock.AXIS, facing.getAxis());

		int length = level.getRandom().nextIntBetweenInclusive(minLength, maxLength);

		Direction trend = level.getRandom().nextBoolean() ? facing.getClockWise() : facing.getCounterClockWise();

		for (int i = 0; i < length; i++) {
			
			// Check if the block can be set.
			if (!Utilities.canReplaceBlockAt(level, pos)) {
				return pos;
			}

			level.setBlock(pos, logstate, 3);

			pos = pos.relative(facing);

			int whichWay = level.getRandom().nextInt(5);

			switch (whichWay) {
			case 0:
				break;
			case 1:
				break;
			case 2:
				pos = pos.relative(trend);
				break;
			default:
				pos = pos.relative(Direction.UP);
				break;

			}
		}

		return pos;
	}

}
