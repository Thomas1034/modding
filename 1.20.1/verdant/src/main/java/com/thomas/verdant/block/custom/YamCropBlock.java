package com.thomas.verdant.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;

public class YamCropBlock extends CropBlock {

	public YamCropBlock(Properties properties) {
		super(properties);
	}

	// Copy and override the random tick function from CropBlock
	// the yam will have a chance to spread instead of growing.
	@SuppressWarnings("deprecation")
	public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource rand) {
		if (!level.isAreaLoaded(pos, 1))
			return; 
		if (level.getRawBrightness(pos, 0) >= 9) {
			int currentAge = this.getAge(state);
			if (currentAge < this.getMaxAge()) {
				float growthSpeed = getGrowthSpeed(this, level, pos);
				if (net.minecraftforge.common.ForgeHooks.onCropsGrowPre(level, pos, state,
						rand.nextInt((int) (25.0F / growthSpeed) + 1) == 0)) {
					level.setBlock(pos, this.getStateForAge(currentAge + 1), 2);
					net.minecraftforge.common.ForgeHooks.onCropsGrowPost(level, pos, state);
				}
			}
		}
	}

	// Copy and override the bonemealing function from CropBlock
	// the yam will have a chance to spread instead of growing.
	@Override
	public void growCrops(Level level, BlockPos pos, BlockState state) {
		int nextAge = this.getAge(state) + this.getBonemealAgeIncrease(level);
		int maxAge = this.getMaxAge();
		if (nextAge > maxAge) {
			nextAge = maxAge;
		}

		level.setBlock(pos, this.getStateForAge(nextAge), 2);
	}
	
	
}
