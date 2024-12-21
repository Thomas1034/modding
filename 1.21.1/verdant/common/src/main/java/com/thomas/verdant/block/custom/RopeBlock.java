package com.thomas.verdant.block.custom;

import com.thomas.verdant.entity.custom.ThrownRopeEntity;
import com.thomas.verdant.registry.BlockRegistry;
import com.thomas.verdant.util.VerdantTags;
import com.thomas.verdant.util.blocktransformer.BlockTransformer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SupportType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.EntityCollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class RopeBlock extends Block {

    private static final VoxelShape SHAPE = Block.box(6.5, 0.0, 6.5, 9.5, 16.0, 9.5);
    private static final VoxelShape LARGE_SHAPE = Block.box(4.5, 0, 4.5, 11.5, 16, 11.5);

    public RopeBlock(Properties properties) {
        super(properties);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    protected VoxelShape getBlockSupportShape(BlockState state, BlockGetter level, BlockPos pos) {
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
}
