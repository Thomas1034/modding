package com.thomas.zirconmod.entity.ai;

import com.thomas.zirconmod.block.ModBlocks;
import com.thomas.zirconmod.entity.custom.WispEntity;

import net.minecraft.core.BlockPos;

/*
public class WispGoHomeWhenThunderingGoal extends WispMoveToPositionGoal {

	public WispGoHomeWhenThunderingGoal(WispEntity entity, double speedFactor) {
		super(entity, 1.0, speedFactor, entity.getHomePos());
	}

	@Override
	public boolean canUse() {
		if (this.wisp.level().isThundering()) {
			System.out.println("Checking if can return home. It is thundering.");
			if (!super.canUse()) {
				System.out.println("Super cannot use. What failed?");

				BlockPos blockpos = this.target;
				if (blockpos != null) {
					System.out.println("Block pos isn't null; this is good.");
					if (this.isTooFarAway(blockpos, this.stopDistance)) {
						System.out.println("Too far away from the goal, this is good.");
					} else {
						System.out.println("Too close! This is bad.");
					}
				} else {
					System.out.println("Block pos is null. This is bad.");
				}

			}
		}
		if (this.wisp.level().isThundering() && super.canUse())
			System.out.println("Can activate return home ai.");
		return this.wisp.level().isThundering() && super.canUse();
	}

	@Override
	protected void onArrival() {
		// Check if there is still a bed there.
		BlockPos pos = this.wisp.getHomePos();
		if (!this.wisp.level().getBlockState(pos).is(ModBlocks.WISP_BED.get())) {
			// If there is not a bed, set the home position to null.
			this.wisp.setHomePos(null);
			this.wisp.setHasHome(false);
		}
	}
}
*/
