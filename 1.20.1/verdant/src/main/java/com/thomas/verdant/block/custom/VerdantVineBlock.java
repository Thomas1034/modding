package com.thomas.verdant.block.custom;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Nullable;

import com.thomas.verdant.block.ModBlocks;
import com.thomas.verdant.growth.VerdantGrower;
import com.thomas.verdant.util.ModTags;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
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
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import oshi.util.tuples.Quartet;

public class VerdantVineBlock extends Block implements VerdantGrower, SimpleWaterloggedBlock {

	public static final int MIN_GROWTH = 0;
	public static final int MAX_GROWTH = 2;

	public static final IntegerProperty UP = IntegerProperty.create("up", MIN_GROWTH, MAX_GROWTH);
	public static final IntegerProperty DOWN = IntegerProperty.create("down", MIN_GROWTH, MAX_GROWTH);
	public static final IntegerProperty NORTH = IntegerProperty.create("north", MIN_GROWTH, MAX_GROWTH);
	public static final IntegerProperty SOUTH = IntegerProperty.create("south", MIN_GROWTH, MAX_GROWTH);
	public static final IntegerProperty EAST = IntegerProperty.create("east", MIN_GROWTH, MAX_GROWTH);
	public static final IntegerProperty WEST = IntegerProperty.create("west", MIN_GROWTH, MAX_GROWTH);

	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	public static final EnumProperty<Axis> AXIS = BlockStateProperties.AXIS;

	public static final Map<Direction, IntegerProperty> SIDES = Map.of(Direction.UP, UP, Direction.DOWN, DOWN,
			Direction.NORTH, NORTH, Direction.SOUTH, SOUTH, Direction.WEST, WEST, Direction.EAST, EAST);

	public static final List<VoxelShape> UP_SHAPE = List.of(Block.box(0.0f, 16.0f, 0.0f, 16.0f, 16.0f, 16.0f),
			Block.box(0.0f, 12.0f, 0.0f, 16.0f, 16.0f, 16.0f), Block.box(0.0f, 8.0f, 0.0f, 16.0f, 16.0f, 16.0f));

	public static final List<VoxelShape> DOWN_SHAPE = List.of(Block.box(0.0f, 0.0f, 0.0f, 16.0f, 0.0f, 16.0f),
			Block.box(0.0f, 0.0f, 0.0f, 16.0f, 4.0f, 16.0f), Block.box(0.0f, 0.0f, 0.0f, 16.0f, 8.0f, 16.0f));

	public static final List<VoxelShape> NORTH_SHAPE = List.of(Block.box(0.0f, 0.0f, 0.0f, 16.0f, 16.0f, 0.0f),
			Block.box(0.0f, 0.0f, 0.0f, 16.0f, 16.0f, 4.0f), Block.box(0.0f, 0.0f, 0.0f, 16.0f, 16.0f, 8.0f));

	public static final List<VoxelShape> SOUTH_SHAPE = List.of(Block.box(0.0f, 0.0f, 16.0f, 16.0f, 16.0f, 16.0f),
			Block.box(0.0f, 0.0f, 12.0f, 16.0f, 16.0f, 16.0f), Block.box(0.0f, 0.0f, 8.0f, 16.0f, 16.0f, 16.0f));

	public static final List<VoxelShape> WEST_SHAPE = List.of(Block.box(0.0f, 0.0f, 0.0f, 0.0f, 16.0f, 16.0f),
			Block.box(0.0f, 0.0f, 0.0f, 4.0f, 16.0f, 16.0f), Block.box(0.0f, 0.0f, 0.0f, 8.0f, 16.0f, 16.0f));

	public static final List<VoxelShape> EAST_SHAPE = List.of(Block.box(16.0f, 0.0f, 0.0f, 16.0f, 16.0f, 16.0f),
			Block.box(12.0f, 0.0f, 0.0f, 16.0f, 16.0f, 16.0f), Block.box(8.0f, 0.0f, 0.0f, 16.0f, 16.0f, 16.0f));

	public VerdantVineBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(this.stateDefinition.any().setValue(UP, 0).setValue(DOWN, 0).setValue(NORTH, 0)
				.setValue(SOUTH, 0).setValue(EAST, 0).setValue(WEST, 0).setValue(WATERLOGGED, false));
	}

	public VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
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
	public void grow(BlockState state, Level level, BlockPos pos) {
		System.out.println("Attempting to grow.");
		// A log state.
		BlockState logState = ModBlocks.VERDANT_LOG.get().defaultBlockState();
		// Check every direction of every block in a 3x3 cube. If it can grow there, add
		// it to a list.
		// Then pick from that list.
		ArrayList<Quartet<Integer, Integer, Integer, Direction>> validSites = new ArrayList<>();
		for (int i = -1; i <= 1; i++) {
			for (int j = -1; j <= 1; j++) {
				for (int k = -1; k <= 1; k++) {
					for (Direction d : Direction.values()) {
						if (this.canGrowToFace(level, pos.offset(i, j, k), d)
								&& level.getBlockState(pos.offset(i, j, k)).isAir()) {
							validSites.add(new Quartet<>(i, j, k, d));
						}
					}
				}
			}
		}
		System.out.println("Found " + validSites.size() + " valid growth sites.");
		// Check if any valid sites were found.
		if (validSites.size() > 0) {
			
			Quartet<Integer, Integer, Integer, Direction> site = validSites
					.get(level.random.nextInt(validSites.size()));

			// Place the vine block there.
			BlockState placed = ModBlocks.VERDANT_VINE.get().defaultBlockState();
			BlockPos placedPos = pos.offset(site.getA(), site.getB(), site.getC());
			placed = placed.setValue(SIDES.get(site.getD()), 1);
			level.setBlockAndUpdate(placedPos, placed);
			System.out.println("Placed vine at " + placedPos);
		}
		// Otherwise, thicken this block.
		else {
			System.out.println("Attempting to grow in place.");
			boolean hasMatured = false;
			for (Entry<Direction, IntegerProperty> side : SIDES.entrySet()) {
				int thickness = hasMatured ? MIN_GROWTH - 1 : state.getValue(side.getValue());
				System.out.println("The " + side.getKey().toString() + " side has thickness of " + thickness);
				// If it is empty and can grow there, do so.
				if (thickness == MIN_GROWTH && this.canGrowToFace(level, pos, side.getKey())) {
					System.out.println("Creating a new side at "  + (thickness + 1));
					state = state.setValue(side.getValue(), thickness + 1);
				}
				// If it is immature, grow it.
				else if (thickness > MIN_GROWTH && thickness < MAX_GROWTH) {
					System.out.println("Growing to "  + (thickness + 1));
					state = state.setValue(side.getValue(), thickness + 1);
				}
				// Check if the block is mature.
				else if (thickness == MAX_GROWTH) {
					System.out.println("At maximum growth.");
					hasMatured = true;
					// Check if the host should be consumed.
					BlockPos hostPos = pos.relative(side.getKey());
					BlockState host = level.getBlockState(hostPos);
					System.out.println("Checking if the host can be consumed.");
					if (!host.is(ModTags.Blocks.VERDANT_LOGS)) {
						System.out.println("The host is not Verdant.");
						boolean canConsumeHost = true;
						// Ensure the host is surrounded on all sides
						for (Direction d : Direction.values()) {
							System.out.println("Checking the " + d + " side.");
							BlockState neighbor = level.getBlockState(hostPos.relative(d));
							if (!neighbor.isAir()) {
								System.out.println("This side is blocked by another block. Good to proceed.");
							} else if (neighbor.is(ModBlocks.VERDANT_VINE.get())
									&& neighbor.getValue(SIDES.get(d)) == MAX_GROWTH) {
								System.out.println("This side is blocked by fully grown vines. Good to proceed.");

							} else {
								System.out.println("This side is a " + neighbor + ". NOT good to proceed.");
								canConsumeHost = false;
								break;
							}
						}
						if (canConsumeHost) {
							System.out.println("Consuming host now.");
							// Mature all of the vines into logs
							for (Direction d : Direction.values()) {
								System.out.println("Checking if there is a vine ");
								BlockState neighbor = level.getBlockState(hostPos.relative(d));
								if (neighbor.is(ModBlocks.VERDANT_VINE.get())) {
									System.out.println("There is a vine, replacing it with a log.");
									level.setBlockAndUpdate(hostPos.relative(d), logState);
								}
							}
							System.out.println("Destroying the host block.");
							// Consume the host block.
							level.destroyBlock(hostPos, false);
							state = logState;
						}

					}
					// If the host is already a Verdant log, change it to heartwood
					// TODO It's emerald for now.
					else {
						System.out.println("The host is Verdant; replacing it with heartwood.");
						level.setBlockAndUpdate(hostPos, Blocks.EMERALD_ORE.defaultBlockState());
						state = logState;
					}
				}
			}
			System.out.println("Updating the vine to " + state);
			level.setBlockAndUpdate(pos, state);
		}
	}

	// From SugarCaneBlock
	@Override
	public BlockState updateShape(BlockState p_57179_, Direction p_57180_, BlockState p_57181_, LevelAccessor level,
			BlockPos p_57183_, BlockPos p_57184_) {
		System.out.println("Running shape update.");

		level.scheduleTick(p_57183_, this, 1);
		if (p_57179_.getValue(WATERLOGGED)) {
			level.scheduleTick(p_57183_, Fluids.WATER, Fluids.WATER.getTickDelay(level));
		}
		return super.updateShape(p_57179_, p_57180_, p_57181_, level, p_57183_, p_57184_);
	}

	@Override
	public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource rand) {

		// Check if any faces are full.
		boolean anyFacesFull = false;
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
					anyFacesFull = true;
				}
			}
		}
		// If all the faces are empty, destroy the block.
		if (!anyFacesFull) {
			// Second parameter is whether it drops resources.
			level.destroyBlock(pos, false);
		}

	}

	private boolean canGrowToFace(Level level, BlockPos pos, Direction direction) {
		return level.getBlockState(pos.relative(direction)).is(BlockTags.LOGS_THAT_BURN);
	}

	@Override
	public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource rand) {
		super.randomTick(state, level, pos, rand);

		// Grow.
		if (rand.nextFloat() < this.growthChance()) {
			// System.out.println("Trying to spread.");
			this.grow(state, level, pos);
		}
	}

	@Nullable
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		LevelAccessor level = context.getLevel();
		BlockPos blockpos = context.getClickedPos();
		return this.defaultBlockState()
				.setValue(WATERLOGGED, Boolean.valueOf(level.getFluidState(blockpos).getType() == Fluids.WATER))
				.setValue(SIDES.get(context.getClickedFace().getOpposite()), 1);
	}

	@Override
	public float growthChance() {
		return 1.0f;
	}

	public FluidState getFluidState(BlockState p_152045_) {
		return p_152045_.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(p_152045_);
	}

	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_152043_) {
		p_152043_.add(WATERLOGGED, UP, DOWN, NORTH, SOUTH, EAST, WEST, AXIS);
	}
}
