package com.thomas.verdant.block.custom;

import com.thomas.verdant.block.ModBlocks;
import com.thomas.verdant.growth.VerdantGrower;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class VerdantRootedDirtBlock extends Block implements VerdantGrower {

	public VerdantRootedDirtBlock(Properties properties) {
		super(properties);
	}
	
	@Override
	public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource rand) {
		super.randomTick(state, level, pos, rand);
		
		// Grow.
		if (rand.nextFloat() < this.growthChance()) {
			this.grow(state, level, pos);			
		}
	}

	@Override
	public float growthChance() {
		return 1.0f;
	}

	@Override
	public void grow(BlockState state, Level level, BlockPos pos) {
		// Find a place to grow within three tries.
		for (int tries = 0; tries < 3; tries++) {
			// The range to check is constant.
			BlockPos posToTry = VerdantGrower.withinDist(pos, 3, level.random);
			// If it converted successfully, break.
			if (VerdantGrower.convert(level, posToTry)) {
				break;
			}
		}
	}

}
