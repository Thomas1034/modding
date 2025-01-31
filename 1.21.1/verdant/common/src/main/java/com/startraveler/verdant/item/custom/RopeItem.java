package com.startraveler.verdant.item.custom;

import com.startraveler.verdant.Constants;
import com.startraveler.verdant.registry.BlockRegistry;
import com.startraveler.verdant.util.VerdantTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class RopeItem extends BlockItem {
    public RopeItem(Block block, Properties properties) {
        super(block, properties);
    }

    // Tries to place `maxToPlace` rope blocks starting at the given position.
    // If `dropExtras` is true, any remaining rope will be dropped at the bottom of the rope column.
    // If `hookBlock` is non-null, then it will place a hook at the top if possible or drop one at the bottom if not.
    // If `hangingBlock` is non-null and non-air, then it will place that block state at the bottom if possible or drop one at the bottom if not.
    // Returns true if it succeeded in placing the rope or dropping extras.
    public static boolean tryPlaceRope(Level level, BlockPos pos, int maxToPlace, boolean dropExtras, BlockState ropeBlock, Block hookBlock, BlockState hangingBlock) {


        boolean anySucceeded = false;

        boolean shouldDropHook = hookBlock != null;
        boolean shouldDropHanging = hangingBlock != null;

        // Try to place a hook if such is needed.
        if (hookBlock != null) {
            // Iterate over all the possible states, in a random order.
            List<BlockState> states = new ArrayList<>(hookBlock.getStateDefinition().getPossibleStates());
            Constants.LOG.warn("States {} and random {} ", states, new Random(level.random.nextInt()));
            Collections.shuffle(states, new Random(level.random.nextInt()));
            for (BlockState hookState : states) {
                // Check if the hook can survive.
                if (hookState.canSurvive(level, pos)) {
                    // If so, place the hook
                    level.setBlockAndUpdate(pos, hookState);
                    // Mark that a hook no longer needs to be dropped.
                    shouldDropHook = false;
                    // Move to the block below.
                    pos = pos.below();
                    // At least one block placed.
                    anySucceeded = true;
                    break;
                }
            }
        }

        // If it hit a rope, move downward.
        while (level.getBlockState(pos).is(VerdantTags.Blocks.ROPES_EXTEND)) {
            pos = pos.below();
        }

        // The current block where the top of the rope is trying to be placed.
        BlockState state = level.getBlockState(pos);
        // Check if it can place a rope at the given position.
        boolean canPlace = state.isAir() && ropeBlock.canSurvive(level, pos);

        // Store the maximum length of the rope.
        int remainingLength = maxToPlace;

        // The end of the line.
        BlockPos.MutableBlockPos mutpos = new BlockPos.MutableBlockPos().set(pos);

        if (canPlace) {
            BlockState cursor = level.getBlockState(mutpos);
            // If a rope can be placed, continue placing them downwards.
            while ((cursor.is(BlockTags.REPLACEABLE) && cursor.getFluidState()
                    .isEmpty() || cursor.is(VerdantTags.Blocks.ROPES_EXTEND)) && remainingLength > 0) {
                if (cursor.is(BlockTags.REPLACEABLE)) {
                    setRope(level, mutpos, ropeBlock);
                    remainingLength--;
                    // At least one block placed.
                    anySucceeded = true;
                }
                mutpos.move(Direction.DOWN);
                cursor = level.getBlockState(mutpos);
            }

            if (hangingBlock != null && !hangingBlock.isAir()) {
                if (cursor.is(BlockTags.REPLACEABLE) && cursor.getFluidState().isEmpty() && anySucceeded) {
                    shouldDropHanging = false;
                    level.setBlockAndUpdate(mutpos, hangingBlock);
                    mutpos.move(Direction.DOWN);
                }
            }
        }

        mutpos = canPlace ? mutpos.above().mutable() : mutpos;
        Vec3 dropPos = mutpos.getCenter();
        // Drop leftover rope if such is permitted.
        if (dropExtras && level instanceof ServerLevel serverLevel) {
            level.addFreshEntity(new ItemEntity(
                    level,
                    dropPos.x,
                    dropPos.y,
                    dropPos.z,
                    new ItemStack(BlockRegistry.ROPE.get(), remainingLength)
            ));
            if (shouldDropHook) {
                Block.getDrops(hookBlock.defaultBlockState(), serverLevel, mutpos, null)
                        .forEach(stack -> level.addFreshEntity(new ItemEntity(
                                level,
                                dropPos.x,
                                dropPos.y,
                                dropPos.z,
                                stack
                        )));
            }
            if (shouldDropHanging) {
                Block.getDrops(hangingBlock, serverLevel, mutpos, null)
                        .forEach(stack -> level.addFreshEntity(new ItemEntity(
                                level,
                                dropPos.x,
                                dropPos.y,
                                dropPos.z,
                                stack
                        )));
            }


        }

        return anySucceeded;
    }

    // Sets a rope with an effect.
    protected static void setRope(Level level, BlockPos pos, BlockState state) {
        level.destroyBlock(pos, true);
        level.setBlockAndUpdate(pos, state);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        ItemStack stack = context.getItemInHand();
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        boolean succeeded = tryPlaceRope(
                level,
                pos,
                1,
                false,
                BlockRegistry.ROPE.get().defaultBlockState(),
                null,
                null
        );
        if (succeeded) {
            stack.shrink(1);
            return InteractionResult.SUCCESS;
        } else {
            return super.useOn(context);
        }
    }
}
