/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * If you modify this file, please include a notice stating the changes:
 * Example: "Modified by [Your Name] on [Date] - [Short Description of Changes]"
 */
package com.startraveler.verdant.block.custom;

import com.mojang.serialization.MapCodec;
import com.startraveler.verdant.util.OptionalDirection;
import com.startraveler.verdant.util.VerdantTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import org.jetbrains.annotations.NotNull;

public class GradientLeavesBlock extends LeavesBlock {
    public static final MapCodec<GradientLeavesBlock> CODEC = simpleCodec(GradientLeavesBlock::new);

    public static final EnumProperty<OptionalDirection> GRADIENT = EnumProperty.create(
            "gradient",
            OptionalDirection.class
    );
    public static final int GRADIENT_MIN_DISTANCE = 1;
    public static final int GRADIENT_MAX_DISTANCE = 8;
    public static final IntegerProperty GRADIENT_DISTANCE = IntegerProperty.create(
            "gradient_distance",
            GRADIENT_MIN_DISTANCE,
            GRADIENT_MAX_DISTANCE
    );


    public GradientLeavesBlock(Properties properties) {
        super(properties);
    }

    @Override
    public MapCodec<? extends GradientLeavesBlock> codec() {
        return CODEC;
    }

    protected void randomTick(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull RandomSource random) {
        if (this.decaying(state)) {
            dropResources(state, level, pos);
            level.removeBlock(pos, false);
        }
    }

    protected boolean decaying(BlockState state) {
        return !state.getValue(BlockStateProperties.PERSISTENT) && state.getValue(GRADIENT_DISTANCE) == GRADIENT_MAX_DISTANCE;
    }

    protected void tick(@NotNull BlockState state, ServerLevel level, @NotNull BlockPos pos, @NotNull RandomSource random) {
        level.setBlockAndUpdate(pos, this.updateDistance(state, level, pos));
    }

    // Updates the block whenever there is a change next to it.
    @Override
    @NotNull
    protected BlockState updateShape(BlockState state, LevelReader level, ScheduledTickAccess tickAccess, BlockPos currentPos, Direction facing, BlockPos facingPos, BlockState facingState, RandomSource random) {
        if (state.getValue(BlockStateProperties.WATERLOGGED)) {
            tickAccess.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }

        int i = getDistanceAt(facingState) + 1;
        if (i != 1 || state.getValue(GRADIENT_DISTANCE) != i) {
            tickAccess.scheduleTick(currentPos, this, 1);
        }

        return state;
    }

    // Very important!
    // Defines the properties for the block.
    @Override
    protected void createBlockStateDefinition(StateDefinition.@NotNull Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(GRADIENT, GRADIENT_DISTANCE);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        FluidState fluidstate = context.getLevel().getFluidState(context.getClickedPos());
        BlockState state = this.defaultBlockState()
                .setValue(BlockStateProperties.PERSISTENT, true)
                .setValue(BlockStateProperties.WATERLOGGED, fluidstate.is(FluidTags.WATER));
        return updateDistance(state, context.getLevel(), context.getClickedPos());
    }

    // May have an infinite loop if the gradient is not well-defined or has non-zero curl.
    // If so, returns null.
    public BlockPos gradientDescent(BlockPos pos, LevelAccessor level, BlockState state) {
        int counter = 0;
        int maxIterations = GRADIENT_MAX_DISTANCE + 2;
        while (true) {
            counter++;
            Direction gradient = state.getOptionalValue(GRADIENT).orElse(OptionalDirection.EMPTY).direction();
            if (gradient == null) {
                break;
            }
            pos = pos.relative(gradient);
            state = level.getBlockState(pos);
            if (counter > maxIterations) {
                return null;
            }
        }

        return pos;
    }

    // Returns the distance to the nearest available log, as stored in the given state.
    // Very similar to how leaf blocks work; just changed to check for water instead of logs.
    // Also doesn't bother redirecting through an optional, to save on computational resources.
    // There's no need to make that many extra objects.
    protected int getDistanceAt(BlockState state) {
        if (state.is(VerdantTags.Blocks.SUSTAINS_STRANGLER_LEAVES)) {
            return 0;
        } else {
            return state.hasProperty(GRADIENT_DISTANCE) ? state.getValue(GRADIENT_DISTANCE) : GRADIENT_MAX_DISTANCE;
        }
    }

    protected BlockState updateDistance(BlockState state, LevelAccessor level, BlockPos pos) {
        int distance = GRADIENT_MAX_DISTANCE;
        OptionalDirection gradient = OptionalDirection.EMPTY;

        BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

        for (Direction direction : Direction.values()) {
            blockpos$mutableblockpos.setWithOffset(pos, direction);
            int neighborDistance = getDistanceAt(level.getBlockState(blockpos$mutableblockpos)) + 1;
            if (neighborDistance < distance) {
                distance = neighborDistance;
                gradient = OptionalDirection.of(direction);
            }

            if (distance == 1) {
                break;
            }
        }

        return state.setValue(GRADIENT_DISTANCE, distance).setValue(GRADIENT, gradient);
    }

}

