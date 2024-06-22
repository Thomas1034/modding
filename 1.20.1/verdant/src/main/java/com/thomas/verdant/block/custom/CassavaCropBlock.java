package com.thomas.verdant.block.custom;

import com.thomas.verdant.block.ModBlocks;
import com.thomas.verdant.item.ModItems;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.IPlantable;

// 8 stages, 0-7.
// LOOK UP KAUPENJOE TUTORIAL ON DOUBLE-HIGH CROP!!!

public class CassavaCropBlock extends CropBlock {
	public static final int FIRST_STAGE_MAX_AGE = 7;
	public static final int MAX_AGE = 11;

	private static final VoxelShape[] SHAPE_BY_AGE = new VoxelShape[] { Block.box(0.0D, 0.0D, 0.0D, 16.0D, 2.0D, 16.0D),
			Block.box(0.0D, 0.0D, 0.0D, 16.0D, 4.0D, 16.0D), Block.box(0.0D, 0.0D, 0.0D, 16.0D, 6.0D, 16.0D),
			Block.box(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D), Block.box(0.0D, 0.0D, 0.0D, 16.0D, 10.0D, 16.0D),
			Block.box(0.0D, 0.0D, 0.0D, 16.0D, 12.0D, 16.0D), Block.box(0.0D, 0.0D, 0.0D, 16.0D, 14.0D, 16.0D),
			Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D), Block.box(0.0D, 0.0D, 0.0D, 16.0D, 2.0D, 16.0D),
			Block.box(0.0D, 0.0D, 0.0D, 16.0D, 4.0D, 16.0D), Block.box(0.0D, 0.0D, 0.0D, 16.0D, 6.0D, 16.0D),
			Block.box(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D), Block.box(0.0D, 0.0D, 0.0D, 16.0D, 10.0D, 16.0D),
			Block.box(0.0D, 0.0D, 0.0D, 16.0D, 12.0D, 16.0D), Block.box(0.0D, 0.0D, 0.0D, 16.0D, 14.0D, 16.0D),
			Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D) };

	public static final IntegerProperty AGE = IntegerProperty.create("age", 0, MAX_AGE);

	public CassavaCropBlock(Properties properties) {
		super(properties);
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {

		try {
			return SHAPE_BY_AGE[this.getAge(state)];
		} catch (ArrayIndexOutOfBoundsException e) {
			return SHAPE_BY_AGE[FIRST_STAGE_MAX_AGE];
		}
	}

	public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
		if (!level.isLoaded(pos) || !level.isLoaded(pos.above())) {
			return;
		}

		if (level.getRawBrightness(pos, 0) >= 9) {
			int currentAge = this.getAge(state);

			if (currentAge < this.getMaxAge()) {
				float growthSpeed = getGrowthSpeed(this, level, pos);

				if (net.minecraftforge.common.ForgeHooks.onCropsGrowPre(level, pos, state,
						random.nextInt((int) (25.0F / growthSpeed) + 1) == 0)) {

					if (currentAge == FIRST_STAGE_MAX_AGE - 1) {

						BlockState aboveState = level.getBlockState(pos.above(1));

						if (aboveState.is(Blocks.AIR)) {
							level.setBlockAndUpdate(pos.above(), this.getStateForAge(currentAge + 2));
							level.setBlockAndUpdate(pos, this.getStateForAge(currentAge + 1));
						} else if (aboveState.is(this)) {
							int aboveAge = this.getAge(aboveState);
							if (aboveAge < MAX_AGE) {
								level.setBlockAndUpdate(pos.above(), this.getStateForAge(aboveAge + 1));
								if (aboveAge == MAX_AGE - 1) {
									level.setBlockAndUpdate(pos.below(1),
											ModBlocks.CASSAVA_ROOTED_DIRT.get().defaultBlockState());
								}
							}
							level.setBlockAndUpdate(pos, this.getStateForAge(currentAge + 1));
						}

					} else if (currentAge == FIRST_STAGE_MAX_AGE) {

						BlockState aboveState = level.getBlockState(pos.above(1));

						if (aboveState.is(Blocks.AIR)) {
							level.setBlockAndUpdate(pos.above(), this.getStateForAge(currentAge + 1));
						} else if (aboveState.is(this)) {
							int aboveAge = this.getAge(aboveState);
							if (aboveAge < MAX_AGE) {
								level.setBlockAndUpdate(pos.above(), this.getStateForAge(aboveAge + 1));
								if (aboveAge == MAX_AGE - 1) {
									level.setBlockAndUpdate(pos.below(1),
											ModBlocks.CASSAVA_ROOTED_DIRT.get().defaultBlockState());
								}
							}
						}

					} else {
						level.setBlockAndUpdate(pos, this.getStateForAge(currentAge + 1));
						if (currentAge == MAX_AGE - 1) {
							level.setBlockAndUpdate(pos.below(2),
									ModBlocks.CASSAVA_ROOTED_DIRT.get().defaultBlockState());
						}
					}

					net.minecraftforge.common.ForgeHooks.onCropsGrowPost(level, pos, state);
				}
			}
		}
	}

	@Override
	public boolean canSustainPlant(BlockState state, BlockGetter world, BlockPos pos, Direction facing,
			IPlantable plantable) {
		return super.mayPlaceOn(state, world, pos);
	}

	@Override
	public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
		return (super.canSurvive(state, level, pos)
				|| (level.getBlockState(pos.below(1)).is(this)
						&& level.getBlockState(pos.below()).getValue(AGE) == FIRST_STAGE_MAX_AGE)
				|| (level.getBlockState(pos.below(1)).is(ModBlocks.CASSAVA_ROOTED_DIRT.get())))
				&& (this.getAge(state) != FIRST_STAGE_MAX_AGE || level.getBlockState(pos.above()).is(this));
	}

	@Override
	public void growCrops(Level level, BlockPos pos, BlockState state) {
		// Deny client side.
		if (level.isClientSide) {
			return;
		}

		int thisAge = this.getAge(state);
		int nextAge = thisAge + this.getBonemealAgeIncrease(level);
		int maxAge = this.getMaxAge();
		BlockState above = level.getBlockState(pos.above());
		if (nextAge > maxAge) {
			nextAge = maxAge;
		}

		if (thisAge < FIRST_STAGE_MAX_AGE - 1 && nextAge >= FIRST_STAGE_MAX_AGE) {
			nextAge = FIRST_STAGE_MAX_AGE - 1;
		}
		if (thisAge == FIRST_STAGE_MAX_AGE - 1 && above.is(Blocks.AIR)) {
			level.setBlockAndUpdate(pos, this.getStateForAge(FIRST_STAGE_MAX_AGE));
			level.setBlockAndUpdate(pos.above(), this.getStateForAge(FIRST_STAGE_MAX_AGE + 1));
		} else if (thisAge == FIRST_STAGE_MAX_AGE && above.is(this)) {
			if (this.getAge(above) < this.getMaxAge()) {
				this.growCrops(level, pos.above(), state);
			}
		} else if (nextAge == maxAge) {
			level.setBlockAndUpdate(pos.below(2), ModBlocks.CASSAVA_ROOTED_DIRT.get().defaultBlockState());
			level.setBlockAndUpdate(pos, this.getStateForAge(nextAge));
		} else {
			level.setBlockAndUpdate(pos, this.getStateForAge(nextAge));
		}
	}

	@Override
	public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state, boolean bool) {
		// Check if it is first stage max; if so, check above it.
		if (this.getAge(state) == FIRST_STAGE_MAX_AGE) {
			BlockState above = level.getBlockState(pos.above());
			if (above.is(this)) {
				return this.getAge(above) < this.getMaxAge();
			}
		}
		return !this.isMaxAge(state);
	}

	@Override
	public int getMaxAge() {
		return MAX_AGE;
	}

	@Override
	protected ItemLike getBaseSeedId() {
		return ModItems.COFFEE_BERRIES.get();
	}

	@Override
	public IntegerProperty getAgeProperty() {
		return AGE;
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(AGE);
	}
}
