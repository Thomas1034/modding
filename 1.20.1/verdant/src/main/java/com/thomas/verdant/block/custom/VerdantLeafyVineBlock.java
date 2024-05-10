package com.thomas.verdant.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class VerdantLeafyVineBlock extends VerdantVineBlock {

	public static final IntegerProperty DISTANCE = BlockStateProperties.DISTANCE;

	public VerdantLeafyVineBlock(Properties properties) {
		super(properties);
		// LeavesBlock
	}

	@Override
	public BlockState updateShape(BlockState thisState, Direction p_54441_, BlockState p_54442_, LevelAccessor level,
			BlockPos thisPos, BlockPos p_54445_) {
		thisState = super.updateShape(thisState, p_54441_, p_54442_, level, thisPos, p_54445_);
		//System.out.println("Updating shape of a Verdant Leafy Vine Block.");
		if (thisState.getValue(WATERLOGGED)) {
			level.scheduleTick(thisPos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
		}

		level.scheduleTick(thisPos, this, 1);

		return thisState.setValue(DISTANCE, 1);
	}

	@Override
	public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource rand) {
		//System.out.println("Verdant Leafy Vines are randomly ticking.");
		super.randomTick(state, level, pos, rand);
	}

	@Override
	public boolean isRandomlyTicking(BlockState state) {
		return true;
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		return Shapes.block();
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(DISTANCE);
	}
}
