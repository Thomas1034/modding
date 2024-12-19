package com.thomas.verdant.item.custom;

import com.thomas.verdant.block.custom.RopeBlock;
import com.thomas.verdant.block.custom.RopeHookBlock;
import com.thomas.verdant.registry.BlockRegistry;
import com.thomas.verdant.util.VerdantTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import java.util.Collection;

public class RopeItem extends BlockItem {
    public RopeItem(Block block, Properties properties) {
        super(block, properties);
    }

    // Tries to place `maxToPlace` rope blocks starting at the given position.
    // If `dropExtras` is true, any remaining rope will be dropped at the bottom of the rope column.
    // If `hasHook` is true, then it will place a hook at the top if possible or drop one at the bottom if not.
    // Returns true if it succeeded in placing the rope or dropping extras.
    public static boolean tryPlaceRope(Level level, BlockPos pos, int maxToPlace, boolean dropExtras, boolean hasHook) {

        boolean anySucceeded = false;

        boolean shouldDropHook = hasHook;

        // Try to place a hook if such is needed.
        if (hasHook) {
            RopeHookBlock hook = (RopeHookBlock) BlockRegistry.ROPE_HOOK.get();
            // Iterate over all the directions, in a random order.
            Collection<Direction> directions = Direction.allShuffled(level.random);
            for (Direction side : directions) {
                // Skip vertical directions, hooks can't hang from the ceiling.
                if (side.getAxis() == Direction.Axis.Y) {
                    continue;
                }
                // Check if the hook can survive.
                BlockState hookState = hook.defaultBlockState().setValue(RopeHookBlock.FACING, side);
                if (hookState.canSurvive(level, pos)) {
                    // If so, place the hook
                    level.setBlockAndUpdate(pos, hookState);
                    // Mark that a hook no longer needs to be dropped.
                    shouldDropHook = false;
                    // Move to the block below.
                    pos = pos.below();
                    // At least one block placed.
                    anySucceeded = true;
                }
            }
        }

        // The current block where the top of the rope is trying to be placed.
        BlockState state = level.getBlockState(pos);

        // Rope block
        RopeBlock rope = ((RopeBlock) BlockRegistry.ROPE.get());

        // Check if it can place a rope at the given position.
        boolean canPlace = level.getBlockState(pos).isAir() && rope.canSurvive(state, level, pos);

        // Store the maximum length of the rope.
        int remainingLength = maxToPlace;

        // The end of the line.
        BlockPos.MutableBlockPos mutpos = new BlockPos.MutableBlockPos().set(pos);

        if (canPlace) {
            BlockState cursor = level.getBlockState(mutpos);
            // If a rope can be placed, continue placing them downwards.
            while ((cursor.is(BlockTags.REPLACEABLE) || cursor.is(VerdantTags.Blocks.ROPES_EXTEND)) && remainingLength > 0) {
                if (cursor.is(BlockTags.REPLACEABLE)) {
                    setRope(level, mutpos);
                    remainingLength--;
                    // At least one block placed.
                    anySucceeded = true;
                }
                mutpos.move(Direction.DOWN);
                cursor = level.getBlockState(mutpos);
            }
        }

        Vec3 dropPos = canPlace ? mutpos.above().getCenter() : mutpos.getCenter();
        // Drop leftover rope if such is permitted.
        if (dropExtras) {
            level.addFreshEntity(new ItemEntity(
                    level,
                    dropPos.x,
                    dropPos.y,
                    dropPos.z,
                    new ItemStack(BlockRegistry.ROPE.get(), remainingLength)
            ));
            if (shouldDropHook) {
                level.addFreshEntity(new ItemEntity(
                        level,
                        dropPos.x,
                        dropPos.y,
                        dropPos.z,
                        new ItemStack(Blocks.TRIPWIRE_HOOK)
                ));
            }
        }

        return anySucceeded;
    }

    // Sets a rope with an effect.
    protected static void setRope(Level level, BlockPos pos) {
        BlockState rope = BlockRegistry.ROPE.get().defaultBlockState();
        level.destroyBlock(pos, true);
        level.setBlockAndUpdate(pos, rope);
    }

    public InteractionResult useOn(UseOnContext context) {
        Player player = context.getPlayer();
        ItemStack stack = context.getItemInHand();
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();

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
                serverLevel.setBlockAndUpdate(scanPos, this.getBlock().defaultBlockState());
                serverLevel.addDestroyBlockEffect(scanPos, this.getBlock().defaultBlockState());
                if (!player.getAbilities().instabuild) {
                    stack.shrink(1);
                    return InteractionResult.CONSUME;
                }
            }
            // On success
            return InteractionResult.SUCCESS;
        }

        return super.useOn(context);
    }

}
