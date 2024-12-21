package com.thomas.verdant.block.custom;

import com.thomas.verdant.registry.BlockRegistry;
import com.thomas.verdant.util.VerdantTags;
import com.thomas.verdant.util.blocktransformer.BlockTransformer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

/**
 * Mostly copied from TripWireHookBlock, since this is a reduced-functionality version.
 * It doesn't actually attach to strings.
 */
public class RopeHookBlock extends Block {
    public static final EnumProperty<Direction> FACING = HorizontalDirectionalBlock.FACING;
    protected static final VoxelShape NORTH_AABB = Block.box(5.0, 0.0, 6.0, 11.0, 10.0, 16.0);
    protected static final VoxelShape SOUTH_AABB = Block.box(5.0, 0.0, 0.0, 11.0, 10.0, 10.0);
    protected static final VoxelShape WEST_AABB = Block.box(6.0, 0.0, 5.0, 16.0, 10.0, 11.0);
    protected static final VoxelShape EAST_AABB = Block.box(0.0, 0.0, 5.0, 10.0, 10.0, 11.0);

    public RopeHookBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return switch (state.getValue(FACING)) {
            case WEST -> WEST_AABB;
            case SOUTH -> SOUTH_AABB;
            case NORTH -> NORTH_AABB;
            default -> EAST_AABB;
        };
    }

    @Override
    protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        Direction facing = state.getValue(FACING);
        BlockPos behind = pos.relative(facing.getOpposite());
        BlockState stateBehind = level.getBlockState(behind);
        return facing.getAxis().isHorizontal() && stateBehind.isFaceSturdy(level, behind, facing);
    }

    protected BlockState updateShape(BlockState state, LevelReader level, ScheduledTickAccess tickAccess, BlockPos pos, Direction direction, BlockPos otherPos, BlockState otherState, RandomSource random) {
        if (direction == Direction.DOWN && !otherState.is(BlockRegistry.ROPE.get())) {
            return BlockTransformer.copyProperties(state, Blocks.TRIPWIRE_HOOK);
        }
        return this.canSurvive(state, level, pos) ? super.updateShape(
                state,
                level,
                tickAccess,
                pos,
                direction,
                otherPos,
                otherState,
                random
        ) : Blocks.AIR.defaultBlockState();
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState state = this.defaultBlockState();
        LevelReader level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        Direction[] nearestDirections = context.getNearestLookingDirections();

        for (Direction direction : nearestDirections) {
            if (direction.getAxis().isHorizontal()) {
                Direction direction1 = direction.getOpposite();
                state = state.setValue(FACING, direction1);
                if (state.canSurvive(level, pos)) {
                    return state;
                }
            }
        }

        return null;
    }

    @Override
    public ItemStack getCloneItemStack(LevelReader level, BlockPos pos, BlockState state) {
        return new ItemStack(Blocks.TRIPWIRE_HOOK);
    }

    @Override
    public InteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {

        Block ropeBlock = BlockRegistry.ROPE.get();
        // Check if the user is holding this item.
        // If not, return.
        if (!player.getItemInHand(hand).is(ropeBlock.asItem())) {
            return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
        }

        // Start scanning for blocks, if it is server side.
        boolean hasFound = false;
        BlockPos.MutableBlockPos scanPos = new BlockPos.MutableBlockPos().set(pos);
        while (level.getBlockState(scanPos).is(VerdantTags.Blocks.ROPES_EXTEND)) {
            hasFound = true;
            scanPos.move(Direction.DOWN);
        }
        if (hasFound && level.getBlockState(scanPos).is(BlockTags.REPLACEABLE) && level.getFluidState(scanPos)
                .isEmpty()) {
            if (level instanceof ServerLevel serverLevel) {
                serverLevel.setBlockAndUpdate(scanPos, ropeBlock.defaultBlockState());
                serverLevel.addDestroyBlockEffect(scanPos, ropeBlock.defaultBlockState());
                if (!player.getAbilities().instabuild) {
                    stack.shrink(1);
                    return InteractionResult.CONSUME;
                }
            }
            // On success
            return InteractionResult.SUCCESS;
        }

        // On failure
        return InteractionResult.TRY_WITH_EMPTY_HAND;
    }

    @Override
    protected BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    @Override
    protected BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }
}
