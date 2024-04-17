package com.thomas.cloudscape.entity.ai;

import com.thomas.cloudscape.block.ModBlocks;
import com.thomas.cloudscape.block.custom.WispBedBlock;
import com.thomas.cloudscape.entity.custom.WispEntity;
import com.thomas.cloudscape.util.Utilities;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;

public class WispFindHomeGoal extends WispMoveToBlockStateGoal {

	public WispFindHomeGoal(WispEntity wisp, double speedModifier) {
		super(wisp, speedModifier, 32,
				ModBlocks.WISP_BED.get().defaultBlockState().setValue(WispBedBlock.IS_UNOCCUPIED, true));
	}

	@Override
	public boolean canUse() {
		
		return super.canUse() && this.wisp.getHomeTarget() == null;
	}

	// Sets the bed as occupied.
	@Override
	public void onArrival() {
		BlockPos pos = this.blockPos;
		Level level = this.wisp.level();
		this.wisp.setHomeTarget(pos);
		level.setBlockAndUpdate(pos,
				ModBlocks.WISP_BED.get().defaultBlockState().setValue(WispBedBlock.IS_UNOCCUPIED, false));
		if (level instanceof ServerLevel sl) {
			Utilities.addParticlesAroundPositionServer(sl, pos.above().getCenter(), ParticleTypes.HAPPY_VILLAGER, 1, 20);
			
		}
	}
}
