package com.thomas.verdant.block.custom;

import com.thomas.verdant.item.ModItems;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;

public class YamCropBlock extends CropBlock {

	public YamCropBlock(Properties properties) {
		super(properties);
	}

	protected ItemLike getBaseSeedId() {
		return ModItems.YAM.get();
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
					// Repeat multiple times to grow more.
					int repetitions = rand.nextInt(3);
					for (int i = 0; i < repetitions; i++) {
						state = this.growAndSpread(level, pos, state);
					}
					net.minecraftforge.common.ForgeHooks.onCropsGrowPost(level, pos, state);
				}
			}
		}
	}

	// Copy and override the bonemealing function from CropBlock
	// the yam will have a chance to spread instead of growing.
	@Override
	public void growCrops(Level level, BlockPos pos, BlockState state) {
		int bonemealGrowth = this.getBonemealAgeIncrease(level);
		for (int i = 0; i < bonemealGrowth; i++) {
			state = this.growAndSpread(level, pos, state);
		}
	}

	// Rewrite.
	// Get all blocks in a 3x3.
	// Select a block at random to grow or spread to.
	// Can also grow other blocks of the same type!
	// TODO
	protected BlockState growAndSpread(Level level, BlockPos pos, BlockState state) {
		BlockState toReturn = state;
		int xOffset = level.random.nextIntBetweenInclusive(-1, 1);
		int yOffset = 0;
		int zOffset = level.random.nextIntBetweenInclusive(-1, 1);

		int thisAge = this.getAge(state);
		if (!(this.getMaxAge() == thisAge)) {
			toReturn = this.getStateForAge(thisAge + 1);
			level.setBlockAndUpdate(pos, toReturn);
		}

		if (0 != xOffset || 0 != zOffset) {
			BlockPos offset = pos.offset(xOffset, yOffset, zOffset);
			BlockState otherState = level.getBlockState(offset);

			BlockState setState = null;
			if (otherState.is(this)) {
				setState = otherState.setValue(AGE, Math.min(otherState.getValue(AGE) + 1, MAX_AGE));
			} else if (otherState.isAir() && this.canSurvive(this.defaultBlockState(), level, offset)) {
				setState = this.defaultBlockState().setValue(AGE, 0);
			}

			if (setState != null) {
				level.setBlockAndUpdate(offset, setState);
			}
		}

		return toReturn;
	}

}
