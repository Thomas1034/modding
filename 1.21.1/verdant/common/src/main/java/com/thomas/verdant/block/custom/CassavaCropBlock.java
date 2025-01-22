package com.thomas.verdant.block.custom;


import com.thomas.verdant.platform.Services;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
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

import java.util.function.Predicate;
import java.util.function.Supplier;

public class CassavaCropBlock extends CropBlock {
    public static final int FIRST_STAGE_MAX_AGE = 7;
    public static final int MAX_AGE = 11;
    public static final IntegerProperty AGE = IntegerProperty.create("age", 0, MAX_AGE);
    private static final VoxelShape[] SHAPE_BY_AGE = new VoxelShape[]{Block.box(0.0D, 0.0D, 0.0D, 16.0D, 2.0D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 4.0D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 6.0D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 10.0D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 12.0D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 14.0D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 2.0D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 4.0D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 6.0D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 10.0D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 12.0D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 14.0D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D)};
    private final Supplier<BlockState> underneath;
    private final Supplier<Item> item;
    private final Predicate<BlockState> alsoSurvivesOn;

    public CassavaCropBlock(Properties properties, Predicate<BlockState> alsoSurvivesOn, Supplier<BlockState> underneath, Supplier<Item> item) {
        super(properties);
        this.underneath = underneath;
        this.item = item;
        this.alsoSurvivesOn = alsoSurvivesOn;

    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {

        try {
            return SHAPE_BY_AGE[this.getAge(state)];
        } catch (ArrayIndexOutOfBoundsException e) {
            return SHAPE_BY_AGE[FIRST_STAGE_MAX_AGE];
        }
    }

    @Override
    public IntegerProperty getAgeProperty() {
        return AGE;
    }

    @Override
    public int getMaxAge() {
        return MAX_AGE;
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (!level.isLoaded(pos) || !level.isLoaded(pos.above())) {
            return;
        }

        if (level.getRawBrightness(pos, 0) >= 9) {
            int currentAge = this.getAge(state);

            if (currentAge < this.getMaxAge()) {
                float growthSpeed = Services.CROP_GROWTH_SPEED.getGrowthSpeed(state, level, pos);

                boolean shouldGrow = random.nextInt((int) (25.0F / growthSpeed) + 1) == 0;

                Services.CROP_EVENT_HELPER.fireEvent(
                        level, pos, state, shouldGrow, () -> {

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
                                            level.setBlockAndUpdate(pos.below(1), this.underneath.get());
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
                                            level.setBlockAndUpdate(pos.below(1), this.underneath.get());
                                        }
                                    }
                                }

                            } else {
                                level.setBlockAndUpdate(pos, this.getStateForAge(currentAge + 1));
                                if (currentAge == MAX_AGE - 1) {
                                    level.setBlockAndUpdate(pos.below(2), this.underneath.get());
                                }
                            }
                        }
                );
            }
        }
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
        if (thisAge == FIRST_STAGE_MAX_AGE - 1) {
            if (above.is(Blocks.AIR)) {
                level.setBlockAndUpdate(pos, this.getStateForAge(FIRST_STAGE_MAX_AGE));
                level.setBlockAndUpdate(pos.above(), this.getStateForAge(FIRST_STAGE_MAX_AGE + 1));
            }
        } else if (thisAge == FIRST_STAGE_MAX_AGE && above.is(this)) {
            if (this.getAge(above) < maxAge) {
                this.growCrops(level, pos.above(), above);
            }
        } else if (thisAge == FIRST_STAGE_MAX_AGE) {
            if (above.is(Blocks.AIR)) {
                level.setBlockAndUpdate(pos.above(), this.getStateForAge(FIRST_STAGE_MAX_AGE + 1));
            }
        } else if (nextAge == maxAge) {
            level.setBlockAndUpdate(pos.below(2), this.underneath.get());
            level.setBlockAndUpdate(pos, this.getStateForAge(nextAge));
        } else if (thisAge == maxAge) {
            // Do nothing
        } else {

            level.setBlockAndUpdate(pos, this.getStateForAge(nextAge));
        }
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        return (super.canSurvive(state, level, pos) || (level.getBlockState(pos.below(1))
                .is(this) && level.getBlockState(pos.below())
                .getValue(AGE) == FIRST_STAGE_MAX_AGE) || this.alsoSurvivesOn.test(level.getBlockState(pos.below(1)))) && (this.getAge(
                state) != FIRST_STAGE_MAX_AGE || level.getBlockState(pos.above()).is(this));
    }

    @Override
    protected ItemLike getBaseSeedId() {
        return this.item.get();
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state) {
        // Check if it is first stage max; if so, check above it.
        if (this.getAge(state) == FIRST_STAGE_MAX_AGE) {
            BlockState above = level.getBlockState(pos.above());
            if (above.is(this)) {
                return this.getAge(above) < this.getMaxAge();
            }
        } else if (this.getAge(state) == FIRST_STAGE_MAX_AGE - 1) {
            return level.getBlockState(pos.above()).isAir();
        }
        return !this.isMaxAge(state);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AGE);
    }
}