package com.thomas.zirconmod.entity.ai;

import java.util.EnumSet;

import com.thomas.zirconmod.entity.custom.WispEntity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;

public class WispMoveToPositionGoal extends Goal {
	protected final WispEntity wisp;
	protected final double stopDistance;
	protected final double speedModifier;
	protected BlockPos target;

	WispMoveToPositionGoal(WispEntity p_35899_, double p_35900_, double p_35901_, BlockPos target) {
		this.wisp = p_35899_;
		this.stopDistance = p_35900_;
		this.speedModifier = p_35901_;
		this.setFlags(EnumSet.of(Goal.Flag.MOVE));
		this.target = target;
	}

	public void stop() {
		this.wisp.setHomeTarget((BlockPos) null);
		this.wisp.getNavigation().stop();
	}

	public boolean canUse() {
		BlockPos blockpos = this.target;
		return blockpos != null && this.isTooFarAway(blockpos, this.stopDistance);
	}

	public void tick() {
		BlockPos blockpos = this.target;
		if (blockpos != null && this.wisp.getNavigation().isDone()) {
			if (this.isTooFarAway(blockpos, this.stopDistance)) {
				Vec3 vec3 = (new Vec3((double) blockpos.getX() - this.wisp.getX(),
						(double) blockpos.getY() - this.wisp.getY(),
						(double) blockpos.getZ() - this.wisp.getZ())).normalize();
				Vec3 vec31 = vec3.scale(10.0D).add(this.wisp.getX(), this.wisp.getY(), this.wisp.getZ());
				this.wisp.getNavigation().moveTo(vec31.x, vec31.y, vec31.z, this.speedModifier);
			} else {
				this.wisp.getNavigation().moveTo((double) blockpos.getX(), (double) blockpos.getY(),
						(double) blockpos.getZ(), this.speedModifier);
				
				// The wisp has arrived. Trigger arrival behavior.
				this.onArrival();
			}
		}
	
	}

	// Overridden by inheriting classes to do custom behavior on arrival.
	protected void onArrival() {		
	}

	protected boolean isTooFarAway(BlockPos p_35904_, double p_35905_) {
		return !p_35904_.closerToCenterThan(this.wisp.position(), p_35905_);
	}
}