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

import com.startraveler.rootbound.blocktransformer.BlockTransformer;
import com.startraveler.verdant.entity.custom.ThrownRopeEntity;
import com.startraveler.verdant.util.VerdantTags;
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
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.EntityCollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class RopeBlock extends Block {

    public static final int GLOW_MIN = 0;
    public static final int GLOW_MAX = 4;
    public static final IntegerProperty GLOW_LEVEL = IntegerProperty.create("glow_level", GLOW_MIN, GLOW_MAX);
    private static final VoxelShape SHAPE = Block.box(6.5, 0.0, 6.5, 9.5, 16.0, 9.5);
    private static final VoxelShape LARGE_SHAPE = Block.box(4.5, 0, 4.5, 11.5, 16, 11.5);
    private final Supplier<RopeHookBlock> hookBlock;

    public RopeBlock(Properties properties, Supplier<RopeHookBlock> hookBlock) {
        super(properties);
        this.hookBlock = hookBlock;
        this.registerDefaultState(this.getStateDefinition().any().setValue(GLOW_LEVEL, GLOW_MIN));
    }

    public RopeHookBlock getHook() {
        return this.hookBlock.get();
    }

    protected boolean canAttachTo(BlockState state, LevelReader level, BlockPos pos) {
        return state.isFaceSturdy(
                level,
                pos,
                Direction.DOWN,
                SupportType.CENTER
        ) || state.is(VerdantTags.Blocks.ROPES_EXTEND);
    }

    @Override
    protected @NotNull BlockState updateShape(@NotNull BlockState state, @NotNull LevelReader level, @NotNull ScheduledTickAccess tickAccess, @NotNull BlockPos pos, @NotNull Direction direction, @NotNull BlockPos otherPos, @NotNull BlockState otherState, @NotNull RandomSource random) {
        if (direction == Direction.UP && !state.canSurvive(level, pos)) {
            tickAccess.scheduleTick(pos, this, 1);
        }
        return super.updateShape(state, level, tickAccess, pos, direction, otherPos, otherState, random);
    }

    protected void onPlace(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState oldState, boolean movedByPiston) {
        super.onPlace(state, level, pos, oldState, movedByPiston);
        BlockPos abovePos = pos.above();
        BlockState aboveState = level.getBlockState(abovePos);
        if (aboveState.is(Blocks.TRIPWIRE_HOOK)) {
            level.setBlockAndUpdate(
                    abovePos,
                    BlockTransformer.copyProperties(aboveState, this.hookBlock.get())
            );
        }
    }

    @Override
    protected @NotNull VoxelShape getBlockSupportShape(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos) {
        return SHAPE;
    }

    @Override
    public boolean canSurvive(@NotNull BlockState state, LevelReader level, BlockPos pos) {
        BlockPos hangingFrom = pos.relative(Direction.UP);
        BlockState hangingOn = level.getBlockState(hangingFrom);
        return this.canAttachTo(hangingOn, level, hangingFrom);
    }

    @Override
    public @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return SHAPE;
    }

    // Thrown ropes can hit this.
    @Override
    public @NotNull VoxelShape getCollisionShape(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext context) {

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

    @Override
    public void tick(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull RandomSource rand) {

        if (!this.canSurvive(state, level, pos)) {
            level.destroyBlock(pos, true);
        } else {
            BlockPos abovePos = pos.above();
            BlockState aboveState = level.getBlockState(abovePos);
            if (aboveState.is(Blocks.TRIPWIRE_HOOK)) {
                level.setBlockAndUpdate(abovePos, BlockTransformer.copyProperties(aboveState, this.hookBlock.get()));
            }
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, @NotNull BlockState> builder) {
        builder.add(GLOW_LEVEL);
    }
}

