package com.thomas.verdant.block.custom;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Supplier;

import javax.annotation.Nullable;

import com.thomas.verdant.block.ModBlocks;
import com.thomas.verdant.growth.VerdantGrower;
import com.thomas.verdant.util.ModTags;

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
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class VerdantVineBlock extends Block implements VerdantGrower, SimpleWaterloggedBlock {

	private static final Supplier<BlockState> VERDANT_LOG = () -> ModBlocks.VERDANT_WOOD.get().defaultBlockState();
	private static final Supplier<BlockState> VERDANT_HEARTWOOD = () -> Blocks.EMERALD_ORE.defaultBlockState();
	private static final Supplier<BlockState> ROTTEN_WOOD = () -> ModBlocks.ROTTEN_WOOD.get().defaultBlockState();

	public static final int MIN_GROWTH = 0;
	public static final int MAX_GROWTH = 3;

	public static final IntegerProperty UP = IntegerProperty.create("up", MIN_GROWTH, MAX_GROWTH);
	public static final IntegerProperty DOWN = IntegerProperty.create("down", MIN_GROWTH, MAX_GROWTH);
	public static final IntegerProperty NORTH = IntegerProperty.create("north", MIN_GROWTH, MAX_GROWTH);
	public static final IntegerProperty SOUTH = IntegerProperty.create("south", MIN_GROWTH, MAX_GROWTH);
	public static final IntegerProperty EAST = IntegerProperty.create("east", MIN_GROWTH, MAX_GROWTH);
	public static final IntegerProperty WEST = IntegerProperty.create("west", MIN_GROWTH, MAX_GROWTH);

	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

	public static final Map<Direction, IntegerProperty> SIDES = Map.of(Direction.UP, UP, Direction.DOWN, DOWN,
			Direction.NORTH, NORTH, Direction.SOUTH, SOUTH, Direction.WEST, WEST, Direction.EAST, EAST);

	public static final List<VoxelShape> UP_SHAPE = List.of(Shapes.empty(),
			Block.box(0.0f, 15.0f, 0.0f, 16.0f, 16.0f, 16.0f), Block.box(0.0f, 12.0f, 0.0f, 16.0f, 16.0f, 16.0f),
			Block.box(0.0f, 8.0f, 0.0f, 16.0f, 16.0f, 16.0f));

	public static final List<VoxelShape> DOWN_SHAPE = List.of(Shapes.empty(),
			Block.box(0.0f, 0.0f, 0.0f, 16.0f, 1.0f, 16.0f), Block.box(0.0f, 0.0f, 0.0f, 16.0f, 4.0f, 16.0f),
			Block.box(0.0f, 0.0f, 0.0f, 16.0f, 8.0f, 16.0f));

	public static final List<VoxelShape> NORTH_SHAPE = List.of(Shapes.empty(),
			Block.box(0.0f, 0.0f, 0.0f, 16.0f, 16.0f, 1.0f), Block.box(0.0f, 0.0f, 0.0f, 16.0f, 16.0f, 4.0f),
			Block.box(0.0f, 0.0f, 0.0f, 16.0f, 16.0f, 8.0f));

	public static final List<VoxelShape> SOUTH_SHAPE = List.of(Shapes.empty(),
			Block.box(0.0f, 0.0f, 15.0f, 16.0f, 16.0f, 16.0f), Block.box(0.0f, 0.0f, 12.0f, 16.0f, 16.0f, 16.0f),
			Block.box(0.0f, 0.0f, 8.0f, 16.0f, 16.0f, 16.0f));

	public static final List<VoxelShape> WEST_SHAPE = List.of(Shapes.empty(),
			Block.box(0.0f, 0.0f, 0.0f, 1.0f, 16.0f, 16.0f), Block.box(0.0f, 0.0f, 0.0f, 4.0f, 16.0f, 16.0f),
			Block.box(0.0f, 0.0f, 0.0f, 8.0f, 16.0f, 16.0f));

	public static final List<VoxelShape> EAST_SHAPE = List.of(Shapes.empty(),
			Block.box(15.0f, 0.0f, 0.0f, 16.0f, 16.0f, 16.0f), Block.box(12.0f, 0.0f, 0.0f, 16.0f, 16.0f, 16.0f),
			Block.box(8.0f, 0.0f, 0.0f, 16.0f, 16.0f, 16.0f));

	public VerdantVineBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(this.stateDefinition.any().setValue(UP, 0).setValue(DOWN, 0).setValue(NORTH, 0)
				.setValue(SOUTH, 0).setValue(EAST, 0).setValue(WEST, 0).setValue(WATERLOGGED, false));
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		// Empty shape.
		VoxelShape shape = Shapes.empty();
		// Merge in the shapes based on the state.
		shape = Shapes.or(shape, UP_SHAPE.get(state.getValue(UP)));
		shape = Shapes.or(shape, DOWN_SHAPE.get(state.getValue(DOWN)));
		shape = Shapes.or(shape, NORTH_SHAPE.get(state.getValue(NORTH)));
		shape = Shapes.or(shape, SOUTH_SHAPE.get(state.getValue(SOUTH)));
		shape = Shapes.or(shape, WEST_SHAPE.get(state.getValue(WEST)));
		shape = Shapes.or(shape, EAST_SHAPE.get(state.getValue(EAST)));
		return shape;
	}

	@Override
	public VoxelShape getBlockSupportShape(BlockState state, BlockGetter level, BlockPos pos) {
		return Shapes.empty();
	}
	
	public static boolean canGrowToAnyFace(Level level, BlockPos pos) {
		for (Direction d : Direction.values()) {
			if (canGrowToFace(level, pos, d)) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean isValidGrowthSite(Level level, BlockPos pos) {
		// System.out.println("Checking if the vine can grow to " + here);
		// Get the block state at that position.
		BlockState blockState = level.getBlockState(pos);
		// System.out.println("The block state is " + hereBlockState);
		// Check if it is replaceable.
		if (blockState.is(BlockTags.REPLACEABLE)) {
			// System.out.println("It passed.");
		} else {
			// System.out.println("It failed.");
			return false;
		}
		// Get the fluid state at that position.
		FluidState fluidState = level.getFluidState(pos);
		// System.out.println("The fluid state is " + hereFluidState);
		if (fluidState.is(Fluids.WATER) || fluidState.isEmpty()) {
			// System.out.println("It passed.");
		} else {
			// System.out.println("It failed.");
			return false;
		}
		
		return canGrowToAnyFace(level, pos);
	}

	// Finds all valid growth sites in a 2n+1 cube.
	public static ArrayList<BlockPos> getGrowthSites(Level level, BlockPos pos, int n) {
		// System.out.println("Finding growth sites.");
		// Check every location in a 2n+1 cube. If it can spread there, add it to a
		// list.
		ArrayList<BlockPos> validSites = new ArrayList<>();
		for (int i = -n; i <= n; i++) {
			for (int j = -n; j <= n; j++) {
				for (int k = -n; k <= n; k++) {
					// Get the proper position to check.
					BlockPos here = pos.offset(i, j, k);
					// System.out.println("Checking if the vine can grow to " + here);
					// Get the block state at that position.
					BlockState hereBlockState = level.getBlockState(here);
					// System.out.println("The block state is " + hereBlockState);
					// Check if it is replaceable.
					if (hereBlockState.is(BlockTags.REPLACEABLE)) {
						// System.out.println("It passed.");
					} else {
						// System.out.println("It failed.");
						continue;
					}
					// Get the fluid state at that position.
					FluidState hereFluidState = level.getFluidState(here);
					// System.out.println("The fluid state is " + hereFluidState);
					if (hereFluidState.is(Fluids.WATER) || hereFluidState.isEmpty()) {
						// System.out.println("It passed.");
					} else {
						// System.out.println("It failed.");
						continue;
					}

					for (Direction d : Direction.values()) {
						if (canGrowToFace(level, here, d)) {
							validSites.add(here);
						}
					}
				}
			}
		}

		return validSites;
	}

	// Spreads the vine to a nearby block.
	private void spread(Level level, BlockPos pos) {
		// System.out.println("Spreading to nearby blocks.");

		ArrayList<BlockPos> validSites = getGrowthSites(level, pos, 1);

		// System.out.println("Found " + validSites.size() + " valid growth sites.");
		// Check if any valid sites were found.
		if (validSites.size() > 0) {
			// Pick a random location from the list.
			BlockPos site = validSites.get(level.random.nextInt(validSites.size()));

			// Place the vine block there.
			BlockState placed = ModBlocks.VERDANT_VINE.get().defaultBlockState();
			// Store the previous block there.
			BlockState replaced = level.getBlockState(site);

			// Find every direction it can grow there.
			for (Direction d : Direction.values()) {
				if (canGrowToFace(level, site, d)) {
					placed = placed.setValue(SIDES.get(d), 1);
				}
			}
			// Waterlog if possible
			if (replaced.hasProperty(BlockStateProperties.WATERLOGGED)) {
				if (replaced.getValue(BlockStateProperties.WATERLOGGED)) {
					placed = placed.setValue(WATERLOGGED, true);
				}
			}

			// Update the block.
			level.destroyBlock(site, true);
			level.setBlockAndUpdate(site, placed);
			// System.out.println("Placed vine at " + site);
		}
	}

	// Grows the current block if possible.
	// Returns true if it has grown to the maximum for the current environment.
	private boolean growInPlace(Level level, BlockPos pos) {

		BlockState state = level.getBlockState(pos);
		// Check if the state is indeed a vine.
		if (!(state.getBlock() instanceof VerdantVineBlock)) {
			return false;
		}

		boolean isMature = true;
		for (Direction d : Direction.values()) {
			// Save the growth level in this direction.
			int maturity = state.getValue(SIDES.get(d));

			// Check if it can grow
			if (maturity == MIN_GROWTH && canGrowToFace(level, pos, d)) {
				isMature = false;
				state = state.setValue(SIDES.get(d), MIN_GROWTH + 1);
			} else if (maturity > MIN_GROWTH && maturity < MAX_GROWTH) {
				isMature = false;
				state = state.setValue(SIDES.get(d), maturity + 1);
			} else {

			}
		}
		if (!isMature) {
			level.addDestroyBlockEffect(pos, state);
			level.setBlockAndUpdate(pos, state);
		}
		return isMature;
	}

	// Returns true if a block has mature verdant log neighbors.
	private boolean hasMatureVerdantLogNeighbors(Level level, BlockPos pos) {

		// If it has neighbors both above and below, then return false. It can still
		// grow to connect the two.
		BlockState above = level.getBlockState(pos.above());
		BlockState below = level.getBlockState(pos.below());
		if (above.is(ModTags.Blocks.MATURE_VERDANT_LOGS) && below.is(ModTags.Blocks.MATURE_VERDANT_LOGS)) {
			// System.out.println("There was heartwood both above and below the block.");
			return false;
		}

		for (Direction d : Direction.values()) {
			// System.out.println("Checking the " + d + " side for verdant heartwood.");
			BlockPos neighborPos = pos.relative(d);
			BlockState neighbor = level.getBlockState(neighborPos);

			if (neighbor.is(ModTags.Blocks.MATURE_VERDANT_LOGS)) {
				// System.out.println("Found verdant heartwood on the " + d + " side.");
				return true;
			}
		}
		// System.out.println("Did not find verdant heartwood.");
		return false;
	}

	// Tries to consume the neighboring log.
	// Returns true if it succeeds.
	private boolean tryConsumeLog(Level level, BlockPos pos) {

		// System.out.println("Attempting to consume a log.");

		BlockState host = level.getBlockState(pos);

		// First, check if the host is a log.
		if (!host.is(BlockTags.LOGS_THAT_BURN)) {
			return false;
		}

		// Then, check if this log is a verdant log and has a mature neighbor.
		// If so, return early.
		if (host.is(ModTags.Blocks.VERDANT_LOGS) && this.hasMatureVerdantLogNeighbors(level, pos)) {
			return false;
		}

		boolean canConsume = true;
		// Check each of the sides. Keep track of the ones that need to be grown.
		ArrayList<BlockPos> positionsToGrow = new ArrayList<>();
		for (Direction d : Direction.values()) {
			// System.out.println("Checking the " + d + " side.");
			BlockPos neighborPos = pos.relative(d);
			BlockState neighbor = level.getBlockState(neighborPos);
			// If the side is not replaceable, it's good to proceed.

			// If the side is a fully grown vine, it's good to proceed after growing it to a
			// log.
			if (neighbor.is(ModBlocks.VERDANT_VINE.get())) {

				// If the vines are mature, keep checking.
				if (neighbor.getValue(SIDES.get(d.getOpposite())) == MAX_GROWTH) {
					// System.out.println("This side is blocked by fully grown vines.");
					positionsToGrow.add(neighborPos);
				}
				// If not, do not proceed.
				else {
					// System.out.println("This side is blocked by immature vines. NOT good to
					// proceed.");
					canConsume = false;
					break;
				}
			} else if (!neighbor.is(BlockTags.REPLACEABLE)) {
				// System.out.println("This side is blocked by another block. Good to
				// proceed.");
			}
			// Otherwise, it's not good to proceed.
			else {
				// System.out.println(
				// "This side is " + neighbor.getBlock().getName().getString() + ". NOT good to
				// proceed.");
				canConsume = false;
				break;
			}
		}
		// If it can consume the log, does so.
		if (canConsume) {
			// System.out.println("Consuming the log.");
			// Grow all the neighboring vines.
			// System.out.println("There are " + positionsToGrow.size() + " vines to
			// grow.");
			for (BlockPos toGrow : positionsToGrow) {
				// System.out.println("Growing a log at " + toGrow + ".");
				level.setBlockAndUpdate(toGrow, VERDANT_LOG.get());
			}

			// Add particle and sound effect.
			level.addDestroyBlockEffect(pos, host);
			// Save the host state.
			// If the host is verdant, mature it.
			if (host.is(ModTags.Blocks.VERDANT_LOGS)) {
				level.setBlockAndUpdate(pos, VERDANT_HEARTWOOD.get());
			}
			// Otherwise destroy it.
			else {
				level.setBlockAndUpdate(pos, ROTTEN_WOOD.get());
			}
		}

		return canConsume;
	}

	@Override
	public void grow(BlockState state, Level level, BlockPos pos) {
		// System.out.println("Attempting to grow.");

		// Spread to nearby blocks.
		this.spread(level, pos);

		// Attempt to grow.
		boolean isMature = this.growInPlace(level, pos);

		// If it is mature, try to consume the log.
		boolean hasConsumed = false;
		if (isMature) {
			// Consume the log in every adjacent direction.
			for (Direction d : Direction.values()) {
				if (state.getValue(SIDES.get(d)) == MAX_GROWTH) {
					hasConsumed = hasConsumed || this.tryConsumeLog(level, pos.relative(d));
				}
			}
		}
	}

	// From SugarCaneBlock
	@Override
	public BlockState updateShape(BlockState p_57179_, Direction p_57180_, BlockState p_57181_, LevelAccessor level,
			BlockPos pos, BlockPos p_57184_) {
		// System.out.println("Running shape update.");

		level.scheduleTick(pos, this, 1);
		if (p_57179_.getValue(WATERLOGGED)) {
			level.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
		}
		return super.updateShape(p_57179_, p_57180_, p_57181_, level, pos, p_57184_);
	}

	@Override
	public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource rand) {

		// Check if any faces exist.
		boolean anyFacesExist = false;
		// Check each direction to see if it can survive, and update accordingly.
		for (Entry<Direction, IntegerProperty> side : SIDES.entrySet()) {
			// Offset to find the neighbor's position.
			BlockPos offset = pos.relative(side.getKey());
			// Get the neighboring block.
			BlockState neighbor = level.getBlockState(offset);

			// Check if the neighbor can support a block on that side, if such is needed.
			if (state.getValue(side.getValue()) > 0) {
				if (!neighbor.isFaceSturdy(level, offset, side.getKey().getOpposite())) {
					// If not, remove growth on that side.
					state = state.setValue(side.getValue(), 0);

					level.addDestroyBlockEffect(pos, state);

				} else {
					// A face has been found.
					anyFacesExist = true;
				}
			}
		}
		// If all the faces are empty, destroy the block.
		if (!anyFacesExist) {
			// Second parameter is whether it drops resources.
			level.destroyBlock(pos, false);
		} else {
			level.setBlockAndUpdate(pos, state);
		}

	}

	public static boolean canGrowToFace(Level level, BlockPos pos, Direction direction) {
		return level.getBlockState(pos.relative(direction)).is(BlockTags.LOGS_THAT_BURN);
	}

	@Override
	public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource rand) {
		// super.randomTick(state, level, pos, rand);

		// Grow.
		if (rand.nextFloat() < this.growthChance()) {
			// //System.out.println("Trying to spread.");
			this.grow(state, level, pos);
		}
	}

	@Nullable
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		LevelAccessor accessor = context.getLevel();
		BlockPos blockpos = context.getClickedPos();
		Direction side = context.getClickedFace().getOpposite();
		BlockState state = this.defaultBlockState()
				.setValue(WATERLOGGED, Boolean.valueOf(accessor.getFluidState(blockpos).getType() == Fluids.WATER))
				.setValue(SIDES.get(side), 1);
		if (accessor instanceof Level level) {
			if (VerdantVineBlock.canGrowToFace(level, blockpos, side)) {
				return state;
			}
		} else {
			System.out.println(
					"Warning! Unable to place VerdantVineBlock, cannot read properties of " + accessor.getClass());
		}
		return null;

	}

	@Override
	public float growthChance() {
		return 1.0f;
	}

	public FluidState getFluidState(BlockState p_152045_) {
		return p_152045_.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(p_152045_);
	}

	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(WATERLOGGED, UP, DOWN, NORTH, SOUTH, EAST, WEST);
	}
}
