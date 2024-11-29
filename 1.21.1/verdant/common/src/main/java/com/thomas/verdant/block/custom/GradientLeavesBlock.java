package com.thomas.verdant.block.custom;

import com.thomas.verdant.Constants;
import com.thomas.verdant.util.OptionalDirection;
import com.thomas.verdant.util.VerdantTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import org.jetbrains.annotations.NotNull;

public class GradientLeavesBlock extends Block {

    public static final EnumProperty<OptionalDirection> GRADIENT = EnumProperty.create("gradient", OptionalDirection.class);
    public static final int MIN_DISTANCE = 1;
    public static final int MAX_DISTANCE = 8;
    public static final IntegerProperty DISTANCE = IntegerProperty.create("distance", MIN_DISTANCE, MAX_DISTANCE);


    public GradientLeavesBlock(Properties properties) {
        super(properties);
    }

    // May have an infinite loop if the gradient is not well-defined or has non-zero curl.
    public BlockPos gradientDescent(BlockPos pos, LevelAccessor level, BlockState state) {
        int counter = 0;
        int maxIterations = MAX_DISTANCE + 2;
        while(true) {
            counter++;
            Direction gradient = state.getOptionalValue(GRADIENT).orElse(OptionalDirection.EMPTY).direction();
            if (gradient == null) {
                break;
            }
            pos = pos.relative(gradient);
            state = level.getBlockState(pos);
            if (counter > maxIterations) {
                Constants.LOG.warn("Warning: gradient descent exceeded {} iterations. Please report this error to the mod developer, along with a description of what was happening at the time.", maxIterations);
                break;
            }
        }

        return pos;
    }

    // Returns the distance to the nearest available log, as stored in the given state.
    // Very similar to how leaf blocks work; just changed to check for water instead of logs.
    // Also doesn't bother redirecting through an optional, to save on computational resources.
    // There's no need to make that many extra objects.
    protected int getDistanceAt(BlockState state) {
        if (state.is(VerdantTags.Blocks.SUPPORTS_TEST_LEAVES)) {
            return 0;
        } else {
            return state.hasProperty(DISTANCE) ? state.getValue(DISTANCE) : MAX_DISTANCE;
        }
    }

    protected void randomTick(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull RandomSource random) {
        if (this.decaying(state)) {
            dropResources(state, level, pos);
            level.removeBlock(pos, false);
        }
    }

    protected boolean decaying(BlockState state) {
        return !state.getValue(BlockStateProperties.PERSISTENT) && state.getValue(DISTANCE) == MAX_DISTANCE;
    }

    protected BlockState updateDistance(BlockState state, LevelAccessor level, BlockPos pos) {
        int distance = MAX_DISTANCE;
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

        return state.setValue(DISTANCE, distance).setValue(GRADIENT, gradient);
    }

    protected void tick(@NotNull BlockState state, ServerLevel level, @NotNull BlockPos pos, @NotNull RandomSource random) {
        level.setBlockAndUpdate(pos, this.updateDistance(state, level, pos));
    }

    // Updates the block whenever there is a change next to it.
    @Override
    @NotNull
    protected BlockState updateShape(BlockState state, @NotNull Direction facing, @NotNull BlockState facingState, @NotNull LevelAccessor level, @NotNull BlockPos currentPos, @NotNull BlockPos facingPos) {
        if (state.getValue(BlockStateProperties.WATERLOGGED)) {
            level.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }

        int i = getDistanceAt(facingState) + 1;
        if (i != 1 || state.getValue(DISTANCE) != i) {
            level.scheduleTick(currentPos, this, 1);
        }

        return state;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        FluidState fluidstate = context.getLevel().getFluidState(context.getClickedPos());
        BlockState state = this.defaultBlockState().setValue(BlockStateProperties.PERSISTENT, true).setValue(BlockStateProperties.WATERLOGGED, fluidstate.is(FluidTags.WATER));
        return updateDistance(state, context.getLevel(), context.getClickedPos());
    }

    // Very important!
    // Defines the properties for the block.
    @Override
    protected void createBlockStateDefinition(StateDefinition.@NotNull Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(GRADIENT, DISTANCE, BlockStateProperties.WATERLOGGED, BlockStateProperties.PERSISTENT);
    }

}
