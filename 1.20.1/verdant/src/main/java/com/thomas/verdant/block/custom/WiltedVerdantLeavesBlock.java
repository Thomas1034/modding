package com.thomas.verdant.block.custom;

import com.thomas.verdant.growth.VerdantGrower;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class WiltedVerdantLeavesBlock extends VerdantLeavesBlock {

	public WiltedVerdantLeavesBlock(Properties properties) {
		super(properties);
	}

	@Override
	public void grow(BlockState state, Level level, BlockPos pos) {

		// System.out.println("Verdant leaves are attempting to grow at " + pos + ".");
		// It will not grow if artificially placed.
		if (state.getValue(PERSISTENT)) {
			return;
		}

		// See if it can grow into leafy vines.
		VerdantGrower.replaceLeavesWithVine(level, pos);

		// Does not grow leaves, spread leaves, or grow tendrils.
		// growLeaves(level, pos);
		// spreadLeaves(level, pos);
		// growTendril(level, pos);
	}

}
