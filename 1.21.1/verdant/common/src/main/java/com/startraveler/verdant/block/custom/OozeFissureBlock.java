package com.startraveler.verdant.block.custom;

import com.mojang.serialization.MapCodec;
import com.startraveler.verdant.block.custom.entity.OozeFissureBlockEntity;
import com.startraveler.verdant.registry.BlockEntityTypeRegistry;
import com.startraveler.verdant.util.VerdantTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class OozeFissureBlock extends BaseEntityBlock {

    public static final BooleanProperty ACTIVE = BooleanProperty.create("active");
    public static final EnumProperty<Direction> FACING = BlockStateProperties.FACING;
    private static final Map<Direction, VoxelShape> BLOCK_SUPPORT_SHAPE = Map.of(
            Direction.NORTH, Block.box(0, 0, 15, 16, 16, 16),
            Direction.SOUTH, Block.box(0, 0, 0, 16, 16, 1),
            Direction.EAST, Block.box(0, 0, 0, 1, 16, 16),
            Direction.WEST, Block.box(15, 0, 0, 16, 16, 16),
            Direction.UP, Block.box(0, 0, 0, 16, 1, 16),
            Direction.DOWN, Block.box(0, 15, 0, 16, 16, 16)
    );

    public OozeFissureBlock(Properties properties) {
        super(properties);
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        for (Direction direction : context.getNearestLookingDirections()) {
            BlockState blockState;
            if (direction.getAxis() == Direction.Axis.Y) {
                blockState = this.defaultBlockState()
                        .setValue(FACING, context.getHorizontalDirection());
            } else {
                blockState = this.defaultBlockState()
                        .setValue(FACING, direction.getOpposite());
            }

            if (blockState.canSurvive(context.getLevel(), context.getClickedPos())) {
                return blockState;
            }
        }

        return null;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.@NotNull Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(ACTIVE, FACING);
    }

    @Override
    public @NotNull MapCodec<ConduitBlock> codec() {
        throw new IllegalStateException("Codecs aren't implemented yet!");
    }

    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        return BlockEntityTypeRegistry.OOZE_FISSURE_BLOCK_ENTITY.get().create(pos, state);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, @NotNull BlockState state, @NotNull BlockEntityType<T> type) {
        return createTickerHelper(
                type,
                BlockEntityTypeRegistry.OOZE_FISSURE_BLOCK_ENTITY.get(),
                level.isClientSide ? OozeFissureBlockEntity::clientTick : OozeFissureBlockEntity::serverTick
        );
    }

    @Override
    protected @NotNull BlockState updateShape(
            @NotNull BlockState state,
            @NotNull LevelReader level,
            @NotNull ScheduledTickAccess tickAccess,
            @NotNull BlockPos pos,
            @NotNull Direction direction,
            @NotNull BlockPos otherPos,
            @NotNull BlockState otherState,
            @NotNull RandomSource rand
    ) {
        return !this.canSurvive(state, level, pos)
                ? Blocks.AIR.defaultBlockState()
                : super.updateShape(state, level, tickAccess, pos, direction, otherPos, otherState, rand);
    }

    @Override
    public @NotNull RenderShape getRenderShape(@NotNull BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    protected boolean canSurvive(BlockState state, @NotNull LevelReader level, @NotNull BlockPos pos) {
        return FaceAttachedHorizontalDirectionalBlock.canAttach(level, pos, state.getValue(FACING).getOpposite()) && level.getBlockState(pos.relative(state.getValue(FACING).getOpposite())).is(
                VerdantTags.Blocks.SUSTAINS_OOZE_FISSURE);
    }

    @Override
    protected @NotNull VoxelShape getShape(BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, CollisionContext context) {
        return BLOCK_SUPPORT_SHAPE.get(state.getValue(FACING));
    }
}
