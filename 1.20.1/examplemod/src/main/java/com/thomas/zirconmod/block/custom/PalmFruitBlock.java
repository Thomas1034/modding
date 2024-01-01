package com.thomas.zirconmod.block.custom;

import com.thomas.zirconmod.block.ModBlocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.CocoaBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class PalmFruitBlock extends CocoaBlock {

	public PalmFruitBlock(Properties properties) {
		super(properties);
	}

	// Overrides the shape to return the shape of a fully grown cocoa pod.
	@Override
	public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		switch ((Direction) state.getValue(FACING)) {
		case SOUTH:
			return SOUTH_AABB[2];
		case NORTH:
		default:
			return NORTH_AABB[2];
		case WEST:
			return WEST_AABB[2];
		case EAST:
			return EAST_AABB[2];
		}
	}

	@Override
	public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
		BlockState trunk = level.getBlockState(pos.relative(state.getValue(FACING)));
		return trunk.is(ModBlocks.PALM_TRUNK.get());
	}
}