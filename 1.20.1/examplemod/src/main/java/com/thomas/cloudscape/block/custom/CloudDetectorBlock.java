package com.thomas.cloudscape.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class CloudDetectorBlock extends CloudBlock {

	public CloudDetectorBlock(Properties properties) {
		super(properties);
	}
	
	@Override
	public VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		return Shapes.block();
	}

	@Override
	public boolean isSignalSource(BlockState state) {

		return true;
	}

//	@Override
//	public int getDirectSignal(BlockState state, BlockGetter level, BlockPos pos, Direction dir) {
//		return 15 - state.getValue(CloudBlock.SOLIDIFIER_DISTANCE);
//	}
	
	@Override
	public int getSignal(BlockState state, BlockGetter level, BlockPos pos, Direction dir) {
		return 15 - state.getValue(CloudBlock.SOLIDIFIER_DISTANCE);
	   }

}
