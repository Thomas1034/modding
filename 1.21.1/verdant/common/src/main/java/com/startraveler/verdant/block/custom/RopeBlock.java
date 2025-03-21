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
import com.startraveler.verdant.registry.BlockRegistry;
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

public class RopeBlock extends Block {

    public static final int GLOW_MIN = 0;
    public static final int GLOW_MAX = 4;
    public static final IntegerProperty GLOW_LEVEL = IntegerProperty.create("glow_level", GLOW_MIN, GLOW_MAX);
    private static final VoxelShape SHAPE = Block.box(6.5, 0.0, 6.5, 9.5, 16.0, 9.5);
    private static final VoxelShape LARGE_SHAPE = Block.box(4.5, 0, 4.5, 11.5, 16, 11.5);

    public RopeBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.getStateDefinition().any().setValue(GLOW_LEVEL, GLOW_MIN));
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

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(GLOW_LEVEL);
    }
}

