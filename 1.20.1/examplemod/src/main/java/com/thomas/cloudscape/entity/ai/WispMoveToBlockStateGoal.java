package com.thomas.cloudscape.entity.ai;

import com.thomas.cloudscape.entity.custom.WispEntity;
import com.thomas.cloudscape.util.Utilities;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;

public class WispMoveToBlockStateGoal extends MoveToBlockGoal {
	protected final WispEntity wisp;
	protected final BlockState target;

	public WispMoveToBlockStateGoal(WispEntity wisp, double speedModifier, BlockState target) {
		super(wisp, speedModifier, 8);
		this.wisp = wisp;
		this.target = target;
	}

	public WispMoveToBlockStateGoal(WispEntity wisp, double speedModifier, int range, BlockState target) {
		super(wisp, speedModifier, range);
		this.wisp = wisp;
		this.target = target;
	}

	protected boolean isValidTarget(LevelReader level, BlockPos pos) {
		if (!level.isEmptyBlock(pos.above())) {
			return false;
		} else {
			BlockState blockstate = level.getBlockState(pos);
			return Utilities.checkEqualStates(this.target, blockstate);
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
