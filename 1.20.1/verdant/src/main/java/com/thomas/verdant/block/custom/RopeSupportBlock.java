package com.thomas.verdant.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;

public class RopeSupportBlock extends RopeBlock {

	public static final EnumProperty<Direction.Axis> AXIS = EnumProperty.create("axis", Direction.Axis.class,
			Direction.Axis.X, Direction.Axis.Z);

	public RopeSupportBlock(Properties properties) {
		super(properties);

	}
	
	// TODO
	@Override
	public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
		BlockPos growingFrom = pos.relative(Direction.UP);
		BlockState growingOn = level.getBlockState(growingFrom);
		return this.canAttachTo(growingOn, level, growingFrom);
	}

	// Very important!
	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(AXIS);
	}

}
