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

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LadderBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;

import java.util.Optional;

public class HangingLadderBlock extends LadderBlock {

    public static final BooleanProperty UP = BooleanProperty.create("up");
    public static final BooleanProperty LEFT = BooleanProperty.create("left");
    public static final BooleanProperty RIGHT = BooleanProperty.create("right");

    public HangingLadderBlock(Properties properties) {
        super(properties);
    }

    @Override
    public BlockState updateShape(BlockState state, LevelReader level, ScheduledTickAccess scheduledTickAccess, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, RandomSource random) {

        if (!this.canSurvive(state, level, pos)) {
            return Blocks.AIR.defaultBlockState();
        } else {
            if (state.getValue(WATERLOGGED)) {
                scheduledTickAccess.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
            }

            // Update the directional stuff.
            Direction facing = state.getValue(LadderBlock.FACING);

            BlockState above = level.getBlockState(pos.above());
            BlockState left = level.getBlockState(pos.relative(facing.getClockWise()));
            BlockState right = level.getBlockState(pos.relative(facing.getCounterClockWise()));

            if (above.hasProperty(LadderBlock.FACING) && facing == above.getValue(LadderBlock.FACING)) {
                state = state.setValue(HangingLadderBlock.UP, true);
            } else {
                state = state.setValue(HangingLadderBlock.UP, false);
            }
            if (left.hasProperty(LadderBlock.FACING) && facing == left.getValue(LadderBlock.FACING)) {
                state = state.setValue(HangingLadderBlock.LEFT, true);
            } else {
                state = state.setValue(HangingLadderBlock.LEFT, false);
            }
            if (right.hasProperty(LadderBlock.FACING) && facing == right.getValue(LadderBlock.FACING)) {
                state = state.setValue(HangingLadderBlock.RIGHT, true);
            } else {
                state = state.setValue(HangingLadderBlock.RIGHT, false);
            }

            return state;
        }
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        boolean sup = super.canSurvive(state, level, pos);
        if (sup) {
            return true;
        } else {
            Direction facing = state.getValue(FACING);
            BlockState above = level.getBlockState(pos.above());
            BlockState aside = level.getBlockState(pos.relative(facing.getClockWise()));
            BlockState bside = level.getBlockState(pos.relative(facing.getCounterClockWise()));

            Optional<Direction> dir = above.getOptionalValue(FACING);
            Optional<Direction> adir = aside.getOptionalValue(FACING);
            Optional<Direction> bdir = bside.getOptionalValue(FACING);

            return (dir.isPresent() && (dir.get() == state.getValue(FACING))) || ((adir.isPresent() && (adir.get() == state.getValue(
                    FACING))) && (bdir.isPresent() && (bdir.get() == state.getValue(FACING))));
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(UP, RIGHT, LEFT);
    }

    @Override
    public InteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        // Check if the user is holding this item.
        // If not, return.
        if (!stack.is(this.asItem())) {
            return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
        }

        // Check if the used on block is this.
        BlockState usedOn = level.getBlockState(pos);
        if (usedOn.is(this)) {

            Direction usedOnFacing = usedOn.getValue(FACING);

            // Start scanning for blocks.
            boolean hasFound = false;
            BlockPos.MutableBlockPos scanPos = new BlockPos.MutableBlockPos().set(pos);
            BlockState investigating;
            while ((investigating = level.getBlockState(scanPos)).is(this) && investigating.getValue(FACING) == usedOnFacing) {
                hasFound = true;
                scanPos.move(Direction.DOWN);
            }

            BlockState toPlace = this.defaultBlockState().setValue(FACING, usedOnFacing);

            // If the bottom of the ladder has been found, check if a ladder can be placed
            // there.
            BlockState replaceState = level.getBlockState(scanPos);
            if (hasFound && replaceState.is(BlockTags.REPLACEABLE) && this.canSurvive(toPlace, level, scanPos)) {
                // If it's a server level, place the block and take the item.
                if (level instanceof ServerLevel serverLevel) {
                    if (replaceState.hasProperty(BlockStateProperties.WATERLOGGED)) {
                        toPlace = toPlace.setValue(
                                LadderBlock.WATERLOGGED,
                                replaceState.getValue(BlockStateProperties.WATERLOGGED)
                        );
                    } else if (replaceState.is(Blocks.WATER)) {
                        toPlace = toPlace.setValue(LadderBlock.WATERLOGGED, true);
                    }
                    serverLevel.setBlockAndUpdate(scanPos, toPlace);
                    serverLevel.addDestroyBlockEffect(scanPos, toPlace);
                    if (!player.getAbilities().instabuild) {
                        if (hand == InteractionHand.MAIN_HAND) {
                            player.getInventory().removeFromSelected(false);
                        } else {
                            player.getInventory().removeItem(Inventory.SLOT_OFFHAND, 1);
                        }
                    }
                }
                // On success
                return InteractionResult.SUCCESS;
            }
        }

        // On failure
        return super.useItemOn(stack, state, level, pos, player, hand, hitResult);

    }
}

