package com.thomas.zirconmod.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class UnstableLightningBlock extends LightningBlock {

	public UnstableLightningBlock(Properties properties) {
		super(properties);
	}

	// Randomly dissipates.
	@Override
	public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
		level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
	}
}
