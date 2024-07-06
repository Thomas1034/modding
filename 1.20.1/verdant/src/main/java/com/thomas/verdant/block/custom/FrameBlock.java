package com.thomas.verdant.block.custom;

import javax.annotation.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class FrameBlock extends ModFlammableRotatedPillarBlock implements SimpleWaterloggedBlock {

	private static final VoxelShape ZN = Block.box(0, 0, 0, 16, 16, 1);
	private static final VoxelShape ZP = Block.box(0, 0, 15, 16, 16, 16);
	private static final VoxelShape YN = Block.box(0, 0, 0, 16, 1, 16);
	private static final VoxelShape YP = Block.box(0, 15, 0, 16, 16, 16);
	private static final VoxelShape XP = Block.box(15, 0, 0, 16, 16, 16);
	private static final VoxelShape XN = Block.box(0, 0, 0, 1, 16, 16);

	private static final VoxelShape X_SHAPE = Shapes.or(YP, YN, ZP, ZN);
	private static final VoxelShape Y_SHAPE = Shapes.or(XP, XN, ZP, ZN);
	private static final VoxelShape Z_SHAPE = Shapes.or(XP, XN, YP, YN);

	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

	public FrameBlock(Properties properties) {
		super(properties);
	}

	public FrameBlock(Properties properties, int flammability, int fireSpreadSpeed) {
		super(properties, flammability, fireSpreadSpeed);
	}

	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(WATERLOGGED);
	}

	@Override
	public VoxelShape getShape(BlockState p_51470_, BlockGetter p_51471_, BlockPos p_51472_,
			CollisionContext p_51473_) {
		switch ((Direction.Axis) p_51470_.getValue(AXIS)) {
		case X:
		default:
			return X_SHAPE;
		case Z:
			return Z_SHAPE;
		case Y:
			return Y_SHAPE;
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public BlockState updateShape(BlockState state, Direction direction, BlockState otherState, LevelAccessor level,
			BlockPos pos, BlockPos otherPos) {
		if (state.getValue(WATERLOGGED)) {
			level.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
		}

		return super.updateShape(state, direction, otherState, level, pos, otherPos);
	}

	@SuppressWarnings("deprecation")
	@Override
	public FluidState getFluidState(BlockState state) {
		return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
	}

	@Override
	@Nullable
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		FluidState fluidstate = context.getLevel().getFluidState(context.getClickedPos());
		boolean flag = fluidstate.getType() == Fluids.WATER;
		return super.getStateForPlacement(context).setValue(WATERLOGGED, Boolean.valueOf(flag));
	}

}