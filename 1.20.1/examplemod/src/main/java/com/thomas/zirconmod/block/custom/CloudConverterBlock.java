package com.thomas.zirconmod.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;

public class CloudConverterBlock extends CloudBlock {

	public CloudConverterBlock(Properties properties) {
		super(properties);
	}

	// Sets the distance based on the block's redstone power.
	@SuppressWarnings("unused")
	private static BlockState updateDistance(BlockState state, LevelAccessor level, BlockPos pos) {
		int pow = level.getDirectSignalTo(pos);
		return state.setValue(SOLIDIFIER_DISTANCE, Integer.valueOf(pow));
	}
	
}
