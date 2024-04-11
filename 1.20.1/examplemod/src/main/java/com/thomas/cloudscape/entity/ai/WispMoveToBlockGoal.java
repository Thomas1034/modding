package com.thomas.cloudscape.entity.ai;

import com.thomas.cloudscape.entity.custom.WispEntity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class WispMoveToBlockGoal extends MoveToBlockGoal {
	private final Block target;

	public WispMoveToBlockGoal(WispEntity wisp, double speedModifier, Block target) {
		super(wisp, speedModifier, 8);
		this.target = target;
	}

	public WispMoveToBlockGoal(WispEntity wisp, double speedModifier, int range, Block target) {
		super(wisp, speedModifier, range);
		this.target = target;
	}

	protected boolean isValidTarget(LevelReader level, BlockPos pos) {
		if (!level.isEmptyBlock(pos.above())) {
			return false;
		} else {
			BlockState blockstate = level.getBlockState(pos);
			return blockstate.is(this.target);
		}
	}

	@Override
	public void tick() {
		super.tick();

		if (this.isReachedTarget()) {
			this.onArrival();
		}
	}

	// Empty, overridden by inheriting classes.
	protected void onArrival() {
	}
}
