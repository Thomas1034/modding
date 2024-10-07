package com.thomas.verdant.block.custom;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
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
					// Repeat multiple times to grow more.
					int repetitions = rand.nextInt(3);
					for (int i = 0; i < repetitions; i++) {
						this.growAndSpread(level, pos, state);
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
			this.growAndSpread(level, pos, state);
		}
	}

	// Rewrite.
	// Get all blocks in a 3x3.
	// Select a block at random to grow or spread to.
	// Can also grow other blocks of the same type!
	// TODO
	protected void growAndSpread(Level level, BlockPos pos, BlockState state) {

		boolean trySpread = level.getRandom().nextFloat() < this.getChanceToSpread();

		if (!trySpread) {
			level.setBlockAndUpdate(pos, this.getStateForAge(1));
		} else {

			// Find an adjacent square to grow to.
			List<BlockPos> locations = new ArrayList<>(4);

			BlockPos belowPos = pos.below();
			BlockPos northPos = pos.north();
			BlockPos southPos = pos.south();
			BlockPos eastPos = pos.east();
			BlockPos westPos = pos.west();
			BlockPos northBelowPos = belowPos.north();
			BlockPos southBelowPos = belowPos.south();
			BlockPos eastBelowPos = belowPos.east();
			BlockPos westBelowPos = belowPos.west();

			BlockState northBelowState = level.getBlockState(northBelowPos);
			BlockState southBelowState = level.getBlockState(southBelowPos);
			BlockState eastBelowState = level.getBlockState(eastBelowPos);
			BlockState westBelowState = level.getBlockState(westBelowPos);

			if (level.getBlockState(northPos).isAir()) {
				northBelowState.canSustainPlant(level, northPos, Direction.UP, this);
				locations.add(northPos);
			}
			if (level.getBlockState(southPos).isAir()) {
				southBelowState.canSustainPlant(level, southPos, Direction.UP, this);
				locations.add(southPos);
			}
			if (level.getBlockState(eastPos).isAir()) {
				eastBelowState.canSustainPlant(level, eastPos, Direction.UP, this);
				locations.add(eastPos);
			}
			if (level.getBlockState(westPos).isAir()) {
				westBelowState.canSustainPlant(level, westPos, Direction.UP, this);
				locations.add(westPos);
			}

			// Get the position to spread to.
			BlockPos spreadTo = locations.get(level.getRandom().nextInt(locations.size()));

			level.setBlockAndUpdate(spreadTo, this.defaultBlockState());
		}
	}

	private float getChanceToSpread() {
		return 0.5f;
	}

}
