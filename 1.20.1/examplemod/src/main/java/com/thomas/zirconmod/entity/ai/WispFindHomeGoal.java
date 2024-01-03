package com.thomas.zirconmod.entity.ai;

import com.thomas.zirconmod.block.ModBlocks;
import com.thomas.zirconmod.block.custom.WispBedBlock;
import com.thomas.zirconmod.entity.custom.WispEntity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

public class WispFindHomeGoal extends WispMoveToBlockStateGoal {

	public WispFindHomeGoal(WispEntity wisp, double speedModifier) {
		super(wisp, speedModifier, 32,
				ModBlocks.WISP_BED.get().defaultBlockState().setValue(WispBedBlock.IS_UNOCCUPIED, true));
	}

	@Override
	public boolean canUse() {
		return super.canUse(); //&& !wisp.getHasHome();
	}

	// Sets the bed as occupied.
	@Override
	protected void onArrival() {
		BlockPos pos = this.blockPos;
		Level level = this.wisp.level();

		//this.wisp.setHomePos(pos);
		//this.wisp.setHasHome(true);
		level.setBlockAndUpdate(pos,
				ModBlocks.WISP_BED.get().defaultBlockState().setValue(WispBedBlock.IS_UNOCCUPIED, false));

	}
}
