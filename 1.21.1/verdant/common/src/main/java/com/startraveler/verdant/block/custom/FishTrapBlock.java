package com.startraveler.verdant.block.custom;

import com.mojang.serialization.MapCodec;
import com.startraveler.verdant.block.custom.entity.FishTrapBlockEntity;
import com.startraveler.verdant.platform.Services;
import com.startraveler.verdant.registry.BlockEntityTypeRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Map;

public class FishTrapBlock extends BaseEntityBlock implements SimpleWaterloggedBlock {
    public static final EnumProperty<Direction> FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty ENABLED = BlockStateProperties.ENABLED;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final MapCodec<? extends FishTrapBlock> CODEC = simpleCodec(FishTrapBlock::new);
    private static final Map<Direction, VoxelShape> BLOCK_SUPPORT_SHAPE = Map.of(
            Direction.NORTH, Shapes.join(
                    Shapes.join(Shapes.block(), Block.box(3, 3, 0, 13, 13, 2), BooleanOp.ONLY_FIRST),
                    Block.box(5, 5, 2, 11, 11, 4),
                    BooleanOp.ONLY_FIRST
            ), Direction.SOUTH, Shapes.join(
                    Shapes.join(Shapes.block(), Block.box(3, 3, 14, 13, 13, 16), BooleanOp.ONLY_FIRST),
                    Block.box(5, 5, 12, 11, 11, 14),
                    BooleanOp.ONLY_FIRST
            ), Direction.EAST, Shapes.join(
                    Shapes.join(Shapes.block(), Block.box(14, 3, 3, 16, 13, 13), BooleanOp.ONLY_FIRST),
                    Block.box(12, 5, 5, 14, 11, 11),
                    BooleanOp.ONLY_FIRST
            ), Direction.WEST, Shapes.join(
                    Shapes.join(Shapes.block(), Block.box(0, 3, 3, 2, 13, 13), BooleanOp.ONLY_FIRST),
                    Block.box(2, 5, 5, 4, 11, 11),
                    BooleanOp.ONLY_FIRST
            )
    );

    public FishTrapBlock(Properties properties) {
        super(properties);

        this.registerDefaultState(this.getStateDefinition()
                .any()
                .setValue(FACING, Direction.NORTH)
                .setValue(ENABLED, false)
                .setValue(WATERLOGGED, false));
    }

    // From DoublePlantBlock
    public static BlockState copyWaterloggedFrom(LevelReader level, BlockPos pos, BlockState state) {
        return state.hasProperty(BlockStateProperties.WATERLOGGED) ? state.setValue(
                BlockStateProperties.WATERLOGGED,
                level.isWaterAt(pos)
        ) : state;
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new FishTrapBlockEntity(pos, state);
    }

    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        Shapes.join(
                Shapes.join(Shapes.block(), Block.box(3, 3, 0, 13, 13, 2), BooleanOp.ONLY_FIRST),
                Block.box(5, 5, 2, 11, 11, 4),
                BooleanOp.ONLY_FIRST
        );
        return BLOCK_SUPPORT_SHAPE.get(state.getValue(FACING));
    }


    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(BlockStateProperties.WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(
                state);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        if (level.isClientSide()) {
            return null;
        }

        return createTickerHelper(
                blockEntityType,
                BlockEntityTypeRegistry.FISH_TRAP_BLOCK_ENTITY.get(),
                (lambdaLevel, lambdaPos, lambdaState, lambdaBlockEntity) -> lambdaBlockEntity.tick(
                        lambdaLevel,
                        lambdaPos,
                        lambdaState
                )
        );
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        if (pState.getBlock() != pNewState.getBlock()) {
            BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
            if (blockEntity instanceof FishTrapBlockEntity) {
                ((FishTrapBlockEntity) blockEntity).drops();
            }
        }
        super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
    }

    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {

        if (!(level.getBlockEntity(pos) instanceof FishTrapBlockEntity fishTrap) || !(player.level() instanceof ServerLevel serverLevel) || !(player instanceof ServerPlayer serverPlayer)) {
            return InteractionResult.SUCCESS;
        }
        MenuProvider provider = state.getMenuProvider(serverLevel, pos);
        if (null != provider) {
            Services.FISH_TRAP_MENU_OPENER.openMenu(serverPlayer, provider, fishTrap);
        }
        return InteractionResult.SUCCESS;
    }

    public BlockState setEnabled(LevelReader level, BlockState original, BlockPos pos) {
        BlockState result = original;
        if (!result.getValue(BlockStateProperties.WATERLOGGED)) {
            return result.setValue(ENABLED, false);
        }
        BlockState inFront = level.getBlockState(pos.relative(result.getValue(FACING)));
        if (inFront.is(Blocks.WATER)) {
            result = result.setValue(ENABLED, true);
        } else {
            result = result.setValue(ENABLED, false);
        }
        return result;
    }

    // From DoublePlantBlock
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState blockstate = super.getStateForPlacement(context);
        if (blockstate == null) {
            return null;
        }
        Direction facing = context.getHorizontalDirection().getOpposite();
        BlockState afterWaterlogged = copyWaterloggedFrom(
                context.getLevel(),
                context.getClickedPos(),
                blockstate.setValue(FACING, facing)
        );
        return setEnabled(context.getLevel(), afterWaterlogged, context.getClickedPos());
    }

    @Override
    public BlockState updateShape(BlockState state, LevelReader level, ScheduledTickAccess tickAccess, BlockPos currentPos, Direction facing, BlockPos facingPos, BlockState facingState, RandomSource random) {
        if (state.getValue(BlockStateProperties.WATERLOGGED)) {
            tickAccess.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }

        return this.setEnabled(level, state, currentPos);
    }

    @Override
    public boolean placeLiquid(LevelAccessor level, BlockPos pos, BlockState state, FluidState fluid) {
        boolean placed = SimpleWaterloggedBlock.super.placeLiquid(level, pos, state, fluid);
        if (placed && !level.isClientSide()) {
            level.setBlock(pos, this.setEnabled(level, level.getBlockState(pos), pos), 3);
        }
        return placed;
    }

    @Override
    public ItemStack pickupBlock(Player player, LevelAccessor level, BlockPos pos, BlockState state) {
        ItemStack stack = SimpleWaterloggedBlock.super.pickupBlock(player, level, pos, state);
        if (!stack.isEmpty() && !level.isClientSide()) {
            level.setBlock(pos, this.setEnabled(level, level.getBlockState(pos), pos), 3);
        }
        return stack;
    }


    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FACING, WATERLOGGED, ENABLED);
    }
}
