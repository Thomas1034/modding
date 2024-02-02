package com.thomas.zirconmod.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;

public class CloudDetector extends CloudBlock {

	public CloudDetector(Properties properties) {
		super(properties);
	}

	@Override
	public boolean isSignalSource(BlockState state) {
		return true;
	}

	@Override
	public int getDirectSignal(BlockState state, BlockGetter level, BlockPos pos, Direction dir) {
		return state.getValue(CloudBlock.SOLIDIFIER_DISTANCE);
	}

}
