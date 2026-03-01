package com.startraveler.verdant.block.custom;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class SimpleWallSkullBlock extends Block {
    public static final EnumProperty<@NotNull Direction> FACING = HorizontalDirectionalBlock.FACING;
    private static final Map<Direction, VoxelShape> AABBS = Maps.newEnumMap(
            ImmutableMap.of(
                    Direction.NORTH,
                    Block.box(4.0, 4.0, 8.0, 12.0, 12.0, 16.0),
                    Direction.SOUTH,
                    Block.box(4.0, 4.0, 0.0, 12.0, 12.0, 8.0),
                    Direction.EAST,
                    Block.box(0.0, 4.0, 4.0, 8.0, 12.0, 12.0),
                    Direction.WEST,
                    Block.box(8.0, 4.0, 4.0, 16.0, 12.0, 12.0)
            )
    );


    public SimpleWallSkullBlock(Properties properties) {
        super(properties);
    }

    @Override
    public BlockState getStateForPlacement(@NotNull BlockPlaceContext context) {
        BlockState blockState = super.getStateForPlacement(context);
        if (blockState != null) {
            BlockGetter blockGetter = context.getLevel();
            BlockPos blockPos = context.getClickedPos();
            Direction[] directions = context.getNearestLookingDirections();

            for (Direction direction : directions) {
                if (direction.getAxis().isHorizontal()) {
                    Direction opposite = direction.getOpposite();
                    blockState = blockState.setValue(FACING, opposite);
                    if (!blockGetter.getBlockState(blockPos.relative(direction)).canBeReplaced(context)) {
                        return blockState;
                    }
                }
            }
        }
        return null;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.@NotNull Builder<Block, @NotNull BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FACING);
    }

    @Override
    protected @NotNull BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    @Override
    protected @NotNull BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    @Override
    protected @NotNull VoxelShape getShape(BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return AABBS.get(state.getValue(FACING));
    }
}
