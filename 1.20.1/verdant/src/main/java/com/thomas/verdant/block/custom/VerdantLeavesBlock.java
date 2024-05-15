package com.thomas.verdant.block.custom;

import java.util.OptionalInt;
import java.util.function.Function;
import java.util.function.Predicate;

import com.thomas.verdant.block.ModBlocks;
import com.thomas.verdant.growth.VerdantGrower;
import com.thomas.verdant.util.ModTags;
import com.thomas.verdant.util.Utilities;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class VerdantLeavesBlock extends LeavesBlock implements VerdantGrower {
	public static final double GROWING_RADIUS = 4.4;
	public static final int VERDANT_DECAY_DISTANCE = (int) (GROWING_RADIUS * 2);
	public static final IntegerProperty VERDANT_DISTANCE = IntegerProperty.create("verdant_distance", 1,
			VERDANT_DECAY_DISTANCE);
	private static final VoxelShape SUPPORT_SHAPE = Shapes.box(0.0, 0.0, 0.0, 16.0, 2.0, 16.0);

	public static final Function<RandomSource, BlockState> LEAVES = (rand) -> {
		float choice = rand.nextFloat();
		if (choice < 0.1f) {
			return ModBlocks.THORNY_VERDANT_LEAVES.get().defaultBlockState();
		} else if (choice < 0.2f) {
			return ModBlocks.POISON_IVY_VERDANT_LEAVES.get().defaultBlockState();
		}

		return ModBlocks.VERDANT_LEAVES.get().defaultBlockState();
	};

	public VerdantLeavesBlock(Properties properties) {
		super(properties);
	}

	@Override
	public VoxelShape getBlockSupportShape(BlockState state, BlockGetter level, BlockPos pos) {
		return SUPPORT_SHAPE;
	}

//	@Override
//	public void onProjectileHit(Level level, BlockState state, BlockHitResult result, Projectile projectile) {
//		if (level.isClientSide) {
//			return;
//		}
//		// System.out.println("Growing!");
//		BlockPos pos = result.getBlockPos();
//		BlockPos supportingLog = Utilities.gradientDescent(level, pos, VerdantLeavesBlock::getVerdantDistanceAt);
//		double distanceToLog = Vec3.atCenterOf(supportingLog).subtract(Vec3.atCenterOf(pos)).multiply(1, 2, 1).length();
//		// System.out.println("Distance is " + distanceToLog + ".");
//		tryToSpreadBasedOnDistanceFromLog(level, pos, 3, 4, distanceToLog);
//	}

	@Override
	protected boolean decaying(BlockState state) {
		return !state.getValue(PERSISTENT) && (state.getValue(VERDANT_DISTANCE) == VERDANT_DECAY_DISTANCE
				&& state.getValue(DISTANCE) == DECAY_DISTANCE);
	}

	@Override
	public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource rand) {
		super.randomTick(state, level, pos, rand);
		// System.out.println("Verdant leaves are ticking at " + pos + ".");
		float growthChance =  this.growthChance();
		float randomChance = rand.nextFloat();
		while (randomChance < growthChance) {
			// System.out.println("Trying to spread.");
			this.grow(state, level, pos);
			growthChance--;
		}
		level.scheduleTick(pos, level.getBlockState(pos).getBlock(), 1);

	}

	// These leaves block more light.
	@Override
	public int getLightBlock(BlockState state, BlockGetter level, BlockPos pos) {
		return 1;
	}

	@Override
	public boolean isRandomlyTicking(BlockState state) {
		return true;
	}

	@Override
	public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource rand) {
		level.setBlock(pos, updateDistance(state, level, pos), 3);
	}

	public BlockState updateShape(BlockState state, Direction direction, BlockState otherState, LevelAccessor level,
			BlockPos pos, BlockPos otherPos) {
		if (state.getValue(WATERLOGGED)) {
			level.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
		}

		int normalDistance = getDistanceAt(otherState) + 1;
		if (normalDistance != 1 || state.getValue(DISTANCE) != normalDistance) {
			level.scheduleTick(pos, this, 1);
		}
		int verdantDistance = getVerdantDistanceAt(otherState) + 1;
		if (verdantDistance != 1 || state.getValue(VERDANT_DISTANCE) != verdantDistance) {
			level.scheduleTick(pos, this, 1);
		}

		return state;
	}

	private static BlockState updateDistance(BlockState state, LevelAccessor level, BlockPos pos) {
		int normalDistance = DECAY_DISTANCE;
		int verdantDistance = VERDANT_DECAY_DISTANCE;
		BlockPos.MutableBlockPos here = new BlockPos.MutableBlockPos();

		for (Direction direction : Direction.values()) {
			here.setWithOffset(pos, direction);
			normalDistance = Math.min(normalDistance, getDistanceAt(level.getBlockState(here)) + 1);
			verdantDistance = Math.min(verdantDistance, getVerdantDistanceAt(level.getBlockState(here)) + 1);

			if (normalDistance == 1) {
				break;
			}
			if (verdantDistance == 1) {
				break;
			}
		}

		return state.setValue(DISTANCE, Integer.valueOf(normalDistance)).setValue(VERDANT_DISTANCE,
				Integer.valueOf(verdantDistance));
	}

	private static int getDistanceAt(BlockState state) {
		return getOptionalDistanceAt(state).orElse(DECAY_DISTANCE);
	}

	public static OptionalInt getOptionalDistanceAt(BlockState state) {
		if (state.is(BlockTags.LOGS)) {
			return OptionalInt.of(0);
		} else {
			return state.hasProperty(DISTANCE) ? OptionalInt.of(state.getValue(DISTANCE)) : OptionalInt.empty();
		}
	}

	public static int getVerdantDistanceAt(BlockState state) {
		return getOptionalVerdantDistanceAt(state).orElse(VERDANT_DECAY_DISTANCE);
	}

	public static OptionalInt getOptionalVerdantDistanceAt(BlockState state) {
		if (state.is(ModTags.Blocks.VERDANT_VINES) || state.is(ModTags.Blocks.VERDANT_LOGS)
				|| state.is(ModTags.Blocks.MATURE_VERDANT_LOGS) || state.is(ModTags.Blocks.VERDANT_VINES)) {
			return OptionalInt.of(0);
		} else {
			return state.hasProperty(VERDANT_DISTANCE) ? OptionalInt.of(state.getValue(VERDANT_DISTANCE))
					: OptionalInt.empty();
		}
	}

	@Override
	public void grow(BlockState state, Level level, BlockPos pos) {

		// System.out.println("Verdant leaves are attempting to grow at " + pos + ".");
		// It will not grow if artificially placed.
		if (state.getValue(PERSISTENT)) {
			return;
		}
		
		// See if it can grow into leafy vines.
		VerdantGrower.replaceLeavesWithVine(level, pos);
		// See if it should decay, since it's too close to the ground.
		
		if (tryToDecay(level, pos)) {
			return;
		}
		growLeaves(level, pos);
		spreadLeaves(level, pos);
		growTendril(level, pos);
	}

	// Decays the block if it is too close to the ground.
	public boolean tryToDecay(Level level, BlockPos pos) {
		if (!hasTransparentOrPlantSpaceBeneath(level, pos, 2) && !hasLogOrVineOrLeafBeneath(level, pos, 3)) {
			level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
			return true;
		}
		return false;
	}

	// Tries to grow a Verdant Tendril.
	public static void growTendril(Level level, BlockPos pos) {
		BlockState state = level.getBlockState(pos);
		// Ensure that the state is a leaf block still.
		// If it isn't, end all processing.
		if (!state.hasProperty(VERDANT_DISTANCE)) {
			return;
		}

		// Check if within support distance.
		int verdantDistance = state.getValue(VERDANT_DISTANCE);

		if (verdantDistance >= DECAY_DISTANCE - 1 && verdantDistance < VERDANT_DECAY_DISTANCE) {
			// System.out.println("Checking for tendril growth conditions.");
			// Check if the block below can be grown into.
			if (!level.getBlockState(pos.below()).isAir()) {
				// System.out.println("Returning, since block below isn't air.");
				return;
			}

			// Check for nearby blocks
			AABB boxToCheck = AABB.of(BoundingBox.fromCorners(pos.offset(-2, -3, -2), pos.offset(2, -2, 2)));
			if (level.getBlockStatesIfLoaded(boxToCheck)
					.anyMatch((stateToCheck) -> !(stateToCheck.isAir() || stateToCheck.is(ModTags.Blocks.VERDANT_VINES)
							|| stateToCheck.is(ModTags.Blocks.VERDANT_LOGS)
							|| stateToCheck.is(ModBlocks.POISON_IVY.get())
							|| stateToCheck.is(ModBlocks.POISON_IVY_PLANT.get())))) {
				// System.out.println("Returning, since blocks were found.");
				return;
			}

			// Now set the block.
			level.setBlockAndUpdate(pos.below(), ModBlocks.VERDANT_TENDRIL.get().defaultBlockState());
		} else {
			// Check if the block below can be grown into.
			if (!level.getBlockState(pos.below()).isAir()) {
				// System.out.println("Returning, since block below isn't air.");
				return;
			}

			// Check for nearby blocks
			AABB boxToCheck = AABB.of(BoundingBox.fromCorners(pos.offset(-1, -2, -1), pos.offset(1, -2, 1)));
			if (level.getBlockStatesIfLoaded(boxToCheck)
					.anyMatch((stateToCheck) -> !(stateToCheck.isAir() || stateToCheck.is(ModTags.Blocks.VERDANT_VINES)
							|| stateToCheck.is(ModTags.Blocks.VERDANT_LOGS)
							|| stateToCheck.is(ModBlocks.VERDANT_TENDRIL.get())
							|| stateToCheck.is(ModBlocks.VERDANT_TENDRIL_PLANT.get())))) {
				// System.out.println("Returning, since blocks were found.");
				return;
			}

			// Now set the block.
			level.setBlockAndUpdate(pos.below(), ModBlocks.POISON_IVY.get().defaultBlockState());
		}
	}

	// Attempts to place a verdant leaf block there.
	// Returns true if it changed anything.
	public static boolean trySpreadLeafBlock(Level level, BlockPos pos) {
		BlockState state = level.getBlockState(pos);

		// If it's already leaves, return early.
		if (state.is(ModTags.Blocks.VERDANT_LEAFY_BLOCKS)) {
			return false;
		}

		// If it's too close to the ground or to a non-leaf block, return early.
		int distanceToCheck = 2;
		for (int i = -distanceToCheck; i <= distanceToCheck; i++) {
			for (int j = -distanceToCheck; j <= distanceToCheck; j++) {
				for (int k = -distanceToCheck; k <= distanceToCheck; k++) {

					// Don't check if the position is on the edge of the checked cube.
					int numberOfSidesThisPositionIsOn = 0;

					if (i == -distanceToCheck || i == distanceToCheck) {
						numberOfSidesThisPositionIsOn++;
					}

					if (j == -distanceToCheck || j == distanceToCheck) {
						numberOfSidesThisPositionIsOn++;
					}

					if (k == -distanceToCheck || k == distanceToCheck) {
						numberOfSidesThisPositionIsOn++;
					}

					if (numberOfSidesThisPositionIsOn >= 2) {
						continue;
					}

					BlockState stateToCheck = level.getBlockState(pos);
					if (!stateToCheck.isAir() && !stateToCheck.is(BlockTags.REPLACEABLE)
							&& !stateToCheck.is(ModTags.Blocks.VERDANT_LOGS)
							&& !stateToCheck.is(ModTags.Blocks.MATURE_VERDANT_LOGS)
							&& !stateToCheck.is(ModTags.Blocks.VERDANT_LEAFY_BLOCKS)
							&& !stateToCheck.is(BlockTags.LEAVES) && !stateToCheck.is(BlockTags.LOGS)) {
						return false;
					}
				}
			}
		}

		// If it's a vine, leafify it.
		if (state.is(ModBlocks.VERDANT_VINE.get())) {

			//
			BlockState placed = ModBlocks.LEAFY_VERDANT_VINE.get().defaultBlockState();
			placed.setValue(VerdantVineBlock.WATERLOGGED, state.getValue(VerdantVineBlock.WATERLOGGED));
			placed.setValue(VerdantVineBlock.UP, state.getValue(VerdantVineBlock.UP));
			placed.setValue(VerdantVineBlock.DOWN, state.getValue(VerdantVineBlock.DOWN));
			placed.setValue(VerdantVineBlock.NORTH, state.getValue(VerdantVineBlock.NORTH));
			placed.setValue(VerdantVineBlock.EAST, state.getValue(VerdantVineBlock.EAST));
			placed.setValue(VerdantVineBlock.SOUTH, state.getValue(VerdantVineBlock.SOUTH));
			placed.setValue(VerdantVineBlock.WEST, state.getValue(VerdantVineBlock.WEST));
			level.setBlockAndUpdate(pos, placed);
			return true;
		} else if (state.is(ModTags.Blocks.VERDANT_VINE_REPLACABLES)) {
			BlockState placed = updateDistance(LEAVES.apply(level.random), level, pos);
			level.setBlockAndUpdate(pos, placed);
			return true;
		}

		return false;
	}

	public static void spreadLeaves(Level level, BlockPos pos) {
		BlockState state = level.getBlockState(pos);
		// System.out.println("spreadLeaves is being called by " + state + " at " + pos
		// + ".");
		// Ensure that the state is a leaf block still.
		if (!state.is(ModTags.Blocks.VERDANT_LEAFY_BLOCKS)) {
			return;
		}
		// Check if within support distance.
		int distance = state.hasProperty(VERDANT_DISTANCE) ? state.getValue(VERDANT_DISTANCE) : 1;
		if (!(distance < VERDANT_DECAY_DISTANCE - 1)) {
			return;
		}

		// Find the log supporting this leaf block.
		BlockPos supportingLog = Utilities.gradientDescent(level, pos, VerdantLeavesBlock::getVerdantDistanceAt);
		// Find the distance to that log.
		// Vertical scale is more important.
		double distanceToLog = Vec3.atCenterOf(supportingLog).subtract(Vec3.atCenterOf(pos)).multiply(1, 2, 1).length();

		// Ensure that this block is within the growing radius.
		if ((distanceToLog < GROWING_RADIUS)) {

			tryToSpreadBasedOnDistanceFromLog(level, pos, 3, 5, distanceToLog);
			tryToGrowShelf(level, pos, 2, 3);
			// tryToGrowUpBasedOnNeighbor(level, pos);

		}

	}

	// Check if the leaf is part of a valid shelf.

	private static void tryToGrowShelf(Level level, BlockPos pos, int maxShelfThickness, int minAirGap) {
		int numberOfBlocksAbove = getDistanceTillAir(level, pos, Direction.UP, maxShelfThickness + 2);
		// Check the number of blocks till there's air, to compare to the shelf
		// thickness.
		// System.out.println("There are " + numberOfBlocksAbove + " blocks above this
		// block.");
		if (numberOfBlocksAbove > maxShelfThickness) {
			// System.out.println("That's too many.");
			return;
		}

		int numberOfBlocksBelow = getDistanceTillAir(level, pos, Direction.DOWN, maxShelfThickness + 2);
		// Check the number of blocks till there's air, to compare to the shelf
		// thickness.
		// System.out.println("There are " + numberOfBlocksBelow + " blocks below this
		// block.");
		if (numberOfBlocksBelow > maxShelfThickness) {
			// System.out.println("That's too many.");
			return;
		}

		int numberOfAirBlocksAboveThat = getDistanceTillBlock(level, pos.above(numberOfBlocksAbove), Direction.UP,
				minAirGap + 2);
		// Check the number of blocks till there's air, to compare to the shelf
		// thickness.
		// System.out.println("There are " + numberOfAirBlocksAboveThat + " air blocks
		// above this block.");
		if (numberOfAirBlocksAboveThat < minAirGap) {
			// System.out.println("That's too few.");
			return;
		}

		int numberOfAirBlocksBelowThat = getDistanceTillBlock(level, pos.below(numberOfBlocksBelow), Direction.DOWN,
				minAirGap + 2);
		// Check the number of blocks till there's air, to compare to the shelf
		// thickness.
		// System.out.println("There are " + numberOfAirBlocksBelowThat + " air blocks
		// above this block.");
		if (numberOfAirBlocksBelowThat < minAirGap) {
			// System.out.println("That's too few.");
			return;
		}

		// System.out.println("The block above is leaves and has sky access above it
		// or empty space below it.");
		// If so, can grow one block outward and one block upward.
		// So do so.
		// System.out.println("Checking for growth sites.");
		boolean successfullyPlaced = false;
		for (Direction d : Utilities.HORIZONTAL_DIRECTIONS) {
			BlockPos neighbor = pos.relative(d);
			successfullyPlaced = successfullyPlaced || trySpreadLeafBlock(level, neighbor);
		}
		successfullyPlaced = successfullyPlaced || trySpreadLeafBlock(level, pos.above());
		if (successfullyPlaced) {

			// System.out.println(
			// "Distance is " + distanceToLog + " and should be no more than " +
			// GROWING_RADIUS + ".");
		}

	}

	private static void tryToSpreadBasedOnDistanceFromLog(Level level, BlockPos pos, int maxDistFromLog,
			int minDistFromGround, double calculatedDistanceToLog) {

		if (calculatedDistanceToLog <= maxDistFromLog) {
			// System.out.println("Trying to omnidirectional spread since " +
			// calculatedDistanceToLog
			// + " is less than or equal to " + maxDistFromLog + ".");

			// if (hasTransparentOrPlantSpaceBeneath(level, pos.above(), minDistFromGround))
			// {

			// Ensure that this leaf has no more than two leaf blocks below it.
			boolean hasNoMoreThanTwoBlocksBelow = getDistanceTillNonLeaf(level, pos, Direction.DOWN, 4) <= 2;
			if (!hasNoMoreThanTwoBlocksBelow) {
				return;
			}

			if (hasAirAbove(level, pos.above(), 4)) {
				trySpreadLeafBlock(level, pos.above());
			} // }

			if (hasTransparentOrPlantSpaceBeneath(level, pos.north(), minDistFromGround)) {
				trySpreadLeafBlock(level, pos.north());
			}
			if (hasTransparentOrPlantSpaceBeneath(level, pos.south(), minDistFromGround)) {
				trySpreadLeafBlock(level, pos.south());
			}
			if (hasTransparentOrPlantSpaceBeneath(level, pos.east(), minDistFromGround)) {
				trySpreadLeafBlock(level, pos.east());
			}
			if (hasTransparentOrPlantSpaceBeneath(level, pos.west(), minDistFromGround))
				trySpreadLeafBlock(level, pos.west());

		}
	}

	private static boolean hasTransparentOrPlantSpaceBeneath(Level level, BlockPos pos, int distanceToCheck) {
		// Check for nearby blocks

		Predicate<BlockState> checker = (stateToCheck) -> !(stateToCheck.isAir()
				|| stateToCheck.getBlock().propagatesSkylightDown(stateToCheck, level, pos)
				|| stateToCheck.is(BlockTags.LEAVES) || stateToCheck.is(BlockTags.LOGS)
				|| stateToCheck.is(ModTags.Blocks.VERDANT_LOGS) || stateToCheck.is(ModTags.Blocks.VERDANT_VINES)
				|| stateToCheck.is(ModTags.Blocks.VERDANT_LEAFY_BLOCKS));
		int distance = getDistanceTill(level, pos, Direction.DOWN, checker, distanceToCheck + 1);

		return distance > distanceToCheck;

	}

	private static boolean hasLogOrVineOrLeafBeneath(Level level, BlockPos pos, int distanceToCheck) {
		// Check for nearby blocks

		Predicate<BlockState> checker = (stateToCheck) -> (stateToCheck.is(BlockTags.LOGS)
				|| stateToCheck.is(ModTags.Blocks.VERDANT_LOGS) || stateToCheck.is(ModTags.Blocks.VERDANT_VINES)
				|| stateToCheck.is(ModTags.Blocks.VERDANT_LEAFY_BLOCKS));
		int distance = getDistanceTill(level, pos, Direction.DOWN, checker, distanceToCheck + 1);

		return distance > distanceToCheck;

	}

	private static boolean hasAirAbove(Level level, BlockPos pos, int distanceToCheck) {
		// Check for nearby blocks

		Predicate<BlockState> checker = (stateToCheck) -> !stateToCheck.isAir();
		int distance = getDistanceTill(level, pos, Direction.UP, checker, distanceToCheck + 1);

		return distance > distanceToCheck;

	}

	// Returns the number of blocks to move in that direction to find a non-air
	// block.
	// Negative max values are ignored.
	public static int getDistanceTillBlock(Level level, BlockPos initial, Direction direction, int max) {
		Predicate<BlockState> checker = (state) -> !state.isAir();
		return getDistanceTill(level, initial, direction, checker, max);
	}

	// Returns the number of blocks to move in that direction to find an air block.
	// Negative max values are ignored.
	public static int getDistanceTillAir(Level level, BlockPos initial, Direction direction, int max) {
		Predicate<BlockState> checker = (state) -> state.isAir();
		return getDistanceTill(level, initial, direction, checker, max);
	}

	// Returns the number of blocks to move in that direction to find a non-leaf
	// block.
	// Negative max values are ignored.
	public static int getDistanceTillNonLeaf(Level level, BlockPos initial, Direction direction, int max) {
		Predicate<BlockState> checker = (state) -> !state.is(BlockTags.LEAVES);
		return getDistanceTill(level, initial, direction, checker, max);
	}

	// Returns the number of blocks to move in a direction till the given condition
	// is satisfied. Negative max values are ignored.

	public static int getDistanceTill(Level level, BlockPos initial, Direction direction, Predicate<BlockState> checker,
			int max) {

		BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos().set(initial);
		int distance = 0;
		while ((!checker.test(level.getBlockState(pos))) && (max < 0 ? true : distance <= max)) {

			pos = pos.move(direction);
			distance++;
		}
		// System.out.println("Halted search " + direction + " on block " +
		// level.getBlockState(pos) + ".");
		return distance;
	}

	public static void growLeaves(Level level, BlockPos pos) {

		// System.out.println("growLeaves is being called by " +
		// level.getBlockState(pos) + " at " + pos + ".");

		// Try to convert nearby leaves.
		// Find a place to grow within three tries. Can use further tries to spread even
		// more.
		for (int tries = 0; tries < 3; tries++) {
			// The range to check is constant.
			BlockPos posToTry = VerdantGrower.withinDist(pos, 3, level.random);
			// If it converted successfully, break.
			if (VerdantGrower.convertLeaves(level, posToTry)) {
				// System.out.println("Successfully grew.");
				// break;
			}
		}

		// System.out.println("Done growing leaves.");
	}

	public static boolean isVerdantLeafAndHasSkyAccess(Level level, BlockPos pos) {
		BlockState state = level.getBlockState(pos);
		return state.is(ModTags.Blocks.VERDANT_LEAFY_BLOCKS) && level.canSeeSky(pos.above());
	}

	// Very important!
	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(VERDANT_DISTANCE);
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		FluidState fluidstate = context.getLevel().getFluidState(context.getClickedPos());
		BlockState blockstate = this.defaultBlockState().setValue(PERSISTENT, Boolean.valueOf(true))
				.setValue(WATERLOGGED, Boolean.valueOf(fluidstate.getType() == Fluids.WATER));
		return updateDistance(blockstate, context.getLevel(), context.getClickedPos());
	}

	// Fire!
	@Override
	public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
		return true;
	}

	@Override
	public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
		return 40;
	}

	@Override
	public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
		return 20;
	}

}
