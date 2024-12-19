package com.thomas.verdant.block.custom;

import com.thomas.verdant.entity.custom.ThrownRopeEntity;
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
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SupportType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.EntityCollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class RopeBlock extends Block {

    private static final VoxelShape SHAPE = Block.box(7, 0, 7, 9, 16, 9);
    private static final VoxelShape LARGE_SHAPE = Block.box(4, 0, 4, 12, 16, 12);
    private static final BooleanProperty IS_TALL = BooleanProperty.create("is_tall");

    public RopeBlock(Properties properties) {
        super(properties);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    // Thrown ropes can hit this.
    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        if (context instanceof EntityCollisionContext entitycollisioncontext) {
            Entity entity = entitycollisioncontext.getEntity();

            if (entity instanceof ThrownRopeEntity thrownRope) {
                return LARGE_SHAPE;
            } else {
                return super.getCollisionShape(state, level, pos, entitycollisioncontext);
            }

        } else {
            return super.getCollisionShape(state, level, pos, context);
        }
    }

    protected boolean canAttachTo(BlockState state, LevelReader level, BlockPos pos) {
        return state.isFaceSturdy(level, pos, Direction.DOWN, SupportType.CENTER) || state.is(this) || state.is(
                VerdantTags.Blocks.ROPE_HOOKS);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        BlockPos hangingFrom = pos.relative(Direction.UP);
        BlockState hangingOn = level.getBlockState(hangingFrom);
        return this.canAttachTo(hangingOn, level, hangingFrom);
    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource rand) {

        if (!this.canSurvive(state, level, pos)) {
            level.destroyBlock(pos, true);
        } else {
            BlockPos abovePos = pos.above();
            BlockState aboveState = level.getBlockState(abovePos);
            if (aboveState.is(Blocks.TRIPWIRE_HOOK)) {
                level.setBlockAndUpdate(
                        abovePos,
                        BlockTransformer.copyProperties(aboveState, BlockRegistry.ROPE_HOOK.get())
                );
            }
        }
    }

    protected void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston) {
        super.onPlace(state, level, pos, oldState, movedByPiston);
        BlockPos abovePos = pos.above();
        BlockState aboveState = level.getBlockState(abovePos);
        if (aboveState.is(Blocks.TRIPWIRE_HOOK)) {
            level.setBlockAndUpdate(
                    abovePos,
                    BlockTransformer.copyProperties(aboveState, BlockRegistry.ROPE_HOOK.get())
            );
        }
    }

    @Override
    protected BlockState updateShape(BlockState state, LevelReader level, ScheduledTickAccess tickAccess, BlockPos pos, Direction direction, BlockPos otherPos, BlockState otherState, RandomSource random) {
        if (direction == Direction.UP && !state.canSurvive(level, pos)) {
            tickAccess.scheduleTick(pos, this, 1);
        }
        return super.updateShape(state, level, tickAccess, pos, direction, otherPos, otherState, random);
    }

    @Override
    public InteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {

        // Check if the user is holding this item.
        // If not, return.
        if (!player.getItemInHand(hand).is(this.asItem())) {
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
                serverLevel.setBlockAndUpdate(scanPos, this.defaultBlockState());
                serverLevel.addDestroyBlockEffect(scanPos, this.defaultBlockState());
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

}
