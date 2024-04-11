package com.thomas.cloudscape.block.custom;

import java.util.Map;

import javax.annotation.Nullable;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.thomas.cloudscape.block.ModBlocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class FrondBlock extends Block {

	private static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
	private static final int MIN_COUNT = 1;
	private static final int MAX_COUNT = 3;
	private static final IntegerProperty COUNT = IntegerProperty.create("count", MIN_COUNT, MAX_COUNT);

	private static final Map<Direction, VoxelShape> SHAPES = Maps
			.newEnumMap(ImmutableMap.of(Direction.NORTH, Block.box(0.0D, 4.0D, 5.0D, 16.0D, 12.0D, 16.0D),
					Direction.SOUTH, Block.box(0.0D, 4.0D, 0.0D, 16.0D, 12.0D, 11.0D), Direction.WEST,
					Block.box(5.0D, 4.0D, 0.0D, 16.0D, 12.0D, 16.0D), Direction.EAST,
					Block.box(0.0D, 4.0D, 0.0D, 11.0D, 12.0D, 16.0D)));

	public FrondBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(
				this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(COUNT, MIN_COUNT));
	}

	public static IntegerProperty getCountProperty() {
		return COUNT;
	}
	
	public static DirectionProperty getFacingProperty() {
		return FACING;
	}
	
	

	@Override
	public boolean propagatesSkylightDown(BlockState state, BlockGetter level, BlockPos pos) {
		return true;
	}

	@Override
	public boolean isPathfindable(BlockState state, BlockGetter level, BlockPos pos, PathComputationType pathType) {
		return false;
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		return SHAPES.get(state.getValue(FACING));
	}

	@Override
	public BlockState rotate(BlockState state, Rotation rotation) {
		return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
	}

	@SuppressWarnings("deprecation")
	@Override
	public BlockState mirror(BlockState state, Mirror mirror) {
		return state.rotate(mirror.getRotation(state.getValue(FACING)));
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_49217_) {
		p_49217_.add(FACING, COUNT);
	}

	@SuppressWarnings("deprecation")
	@Override
	public BlockState updateShape(BlockState state, Direction dir, BlockState state2, LevelAccessor level,
			BlockPos pos1, BlockPos pos2) {
		return dir.getOpposite() == state.getValue(FACING) && !state.canSurvive(level, pos1)
				? Blocks.AIR.defaultBlockState()
				: super.updateShape(state, dir, state2, level, pos1, pos2);
	}

	@Override
	public boolean canSurvive(BlockState p_49200_, LevelReader p_49201_, BlockPos p_49202_) {
		Direction direction = p_49200_.getValue(FACING);
		BlockPos blockpos = p_49202_.relative(direction.getOpposite());
		BlockState blockstate = p_49201_.getBlockState(blockpos);
		return blockstate.isFaceSturdy(p_49201_, blockpos, direction) || (blockstate.getBlock() instanceof LeavesBlock)
				|| (blockstate.getBlock() instanceof PalmTrunkBlock);
	}

	@Nullable
	@Override
	public BlockState getStateForPlacement(BlockPlaceContext p_49198_) {
		BlockState blockstate = super.getStateForPlacement(p_49198_);
		LevelReader levelreader = p_49198_.getLevel();
		BlockPos blockpos = p_49198_.getClickedPos();
		Direction[] adirection = p_49198_.getNearestLookingDirections();

		for (Direction direction : adirection) {
			if (direction.getAxis().isHorizontal()) {
				blockstate = blockstate.setValue(FACING, direction.getOpposite());
				if (blockstate.canSurvive(levelreader, blockpos)) {
					return blockstate;
				}
			} else if (direction.getAxis().isVertical()) {
				return ModBlocks.PALM_FLOOR_FROND.get().defaultBlockState();
			}
		}

		return null;
	}

	@SuppressWarnings("deprecation")
	@Override
	public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand,
			BlockHitResult result) {
		if (!level.isClientSide && hand == InteractionHand.MAIN_HAND && !player.isCrouching()) {

			// Checks if the player is holding a palm frond.
			if (player.getItemInHand(hand).is(ModBlocks.PALM_FROND.get().asItem())) {

				// Gets the current count.
				int currentCount = state.getValue(COUNT);

				// If the block is currently full to the maximum, does nothing.
				if (currentCount >= MAX_COUNT) {
					return super.use(state, level, pos, player, hand, result);
				}

				// Adds a palm frond.
				level.setBlock(pos, state.setValue(COUNT, currentCount + 1), 2);

				// Takes an item from the player.
				if (!player.canUseGameMasterBlocks())
					player.getMainHandItem().shrink(1);

				return InteractionResult.CONSUME;
			}
		}
		return super.use(state, level, pos, player, hand, result);
	}

	@Override
	// Grows palm fruit underneath the frond.
	public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
		// Gets which way the frond is facing.
		Direction facing = state.getValue(FACING);

		// Checks if the frond is placed on a palm trunk.
		// Does this by getting the block at the relative position in the opposite
		// direction to the way the frond is facing.
		// In other words, the block behind the frond.
		boolean isOnPalmTrunk = level.getBlockState(pos.relative(facing.getOpposite())).is(ModBlocks.PALM_TRUNK.get());
		// Checks if the frond is above air.
		boolean isAboveAir = level.getBlockState(pos.relative(Direction.DOWN)).is(Blocks.AIR);
		// Checks if the frond has a palm trunk behind and below it.
		boolean validGrowthSite = level.getBlockState(pos.relative(facing.getOpposite()).relative(Direction.DOWN))
				.is(ModBlocks.PALM_TRUNK.get());

		// Checks if all of these are true.
		if (isOnPalmTrunk && isAboveAir && validGrowthSite) {
			// If so, places a palm fruit block facing the opposite direction as the frond.
			// The opposite direction is due to differing facing conventions.
			level.setBlockAndUpdate(pos.relative(Direction.DOWN), ModBlocks.PALM_FRUIT.get().defaultBlockState()
					.setValue(PalmFruitBlock.FACING, facing.getOpposite()).setValue(PalmFruitBlock.AGE, 0));
		}
	}
}