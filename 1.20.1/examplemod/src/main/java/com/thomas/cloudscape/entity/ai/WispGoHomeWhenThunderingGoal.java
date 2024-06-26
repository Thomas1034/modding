package com.thomas.cloudscape.entity.ai;

import java.util.EnumSet;

import com.thomas.cloudscape.block.ModBlocks;
import com.thomas.cloudscape.entity.custom.WispEntity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.Goal;

public class WispGoHomeWhenThunderingGoal extends Goal {
	final WispEntity trader;
	final double stopDistance;
	final double speedModifier;

	public WispGoHomeWhenThunderingGoal(WispEntity p_35899_, double p_35900_, double p_35901_) {
		this.trader = p_35899_;
		this.stopDistance = p_35900_;
		this.speedModifier = p_35901_;
		this.setFlags(EnumSet.of(Goal.Flag.MOVE));
	}

	@Override
	public void stop() {

		// this.trader.setWanderTarget((BlockPos) null);
		this.trader.getNavigation().stop();
		// If the home was removed, then set the wisp to be homeless.
		if (!this.trader.level().getBlockState(this.trader.getHomeTarget()).is(ModBlocks.WISP_BED.get())) {
			this.trader.setHomeTarget(null);
		}
	}

//	@Override
//	public void start() {
//		System.out.println("Starting to go home!");
//	}

	@Override
	public boolean canUse() {
		boolean isThundering = this.trader.level().isThundering();
		BlockPos blockpos = this.trader.getHomeTarget();
		return isThundering && blockpos != null && this.isTooFarAway(blockpos, this.stopDistance);
	}

	@Override
	public void tick() {

		BlockPos blockpos = this.trader.getHomeTarget();
		if (blockpos != null && this.trader.getNavigation().isDone()) {
			this.trader.getNavigation().moveTo((double) blockpos.getX(), (double) blockpos.getY(),
					(double) blockpos.getZ(), this.speedModifier);
		}
	}
	private boolean isTooFarAway(BlockPos p_35904_, double p_35905_) {
		return !p_35904_.closerToCenterThan(this.trader.position(), p_35905_);
	}
}
