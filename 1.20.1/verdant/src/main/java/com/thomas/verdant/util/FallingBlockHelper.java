package com.thomas.verdant.util;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class FallingBlockHelper {

	public static FallingBlockEntity fallNoDrops(Level level, BlockPos pos, BlockState state) {
		level.destroyBlock(pos, false);
		FallingBlockEntity block = FallingBlockEntity.fall(level, pos, state);
		block.dropItem = false;
		return block;
	}
	
	public static FallingBlockEntity fallNoDrops(Level level, BlockPos pos) {
		return fallNoDrops(level, pos, level.getBlockState(pos));
	}
}
