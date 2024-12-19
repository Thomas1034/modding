package com.thomas.verdant.entity.custom;

import com.thomas.verdant.block.custom.RopeBlock;
import com.thomas.verdant.item.component.ThrownRopeComponent;
import com.thomas.verdant.item.custom.RopeCoilItem;
import com.thomas.verdant.registry.BlockRegistry;
import com.thomas.verdant.registry.DataComponentRegistry;
import com.thomas.verdant.registry.EntityTypeRegistry;
import com.thomas.verdant.registry.ItemRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

public class ThrownRopeEntity extends ThrowableItemProjectile {

    public ThrownRopeEntity(Level level, LivingEntity thrower, ItemStack stack) {
        super(EntityTypeRegistry.THROWN_ROPE.get(), thrower, level, stack);
    }

    public ThrownRopeEntity(EntityType<? extends ThrowableItemProjectile> type, Level level) {
        super(type, level);
    }

    public ThrownRopeEntity(Level level) {
        super(EntityTypeRegistry.THROWN_ROPE.get(), level);
    }

    public ThrownRopeEntity(Level level, double x, double y, double z, ItemStack stack) {
        super(EntityTypeRegistry.THROWN_ROPE.get(), x, y, z, level, stack);
    }

    @Override
    protected Item getDefaultItem() {
        return ItemRegistry.ROPE_COIL.get();
    }

    @Override
    protected void onHitBlock(BlockHitResult hitResult) {
        super.onHitBlock(hitResult);
        // The level.
        Level level = this.level();

        // Ensure server side.
        if (level.isClientSide) {
            return;
        }

        // Get the item stack.
        ItemStack stack = this.getItem();
        ThrownRopeComponent dataComponent = stack.getOrDefault(
                DataComponentRegistry.ROPE_LENGTH.get(),
                RopeCoilItem.DEFAULT_DATA_COMPONENT
        );

        // Check if it hit a rope.
        BlockPos hitpos = hitResult.getBlockPos();
        BlockState hitState = level.getBlockState(hitpos);
        BlockPos pos;
        if (hitState.is(BlockRegistry.ROPE.get())) {
            // Extend the rope, find its bottom.
            pos = hitpos.below();
            while (level.getBlockState(pos).is(BlockRegistry.ROPE.get())) {
                pos = pos.below();
            }
        } else {
            // If not, place a rope offset from the block that was hit.
            pos = hitResult.getBlockPos().relative(hitResult.getDirection());
        }

        // The state of the block it was in when it hit.
        BlockState state = level.getBlockState(pos);
        // Rope block
        RopeBlock rope = ((RopeBlock) BlockRegistry.ROPE.get());

        // Check if it can place a rope at the given position.
        boolean canPlace = this.level().getBlockState(pos).isAir() && rope.canSurvive(state, this.level(), pos);

        // Store the maximum length of the rope.
        int remainingLength = dataComponent.length();

        // The end of the line.
        BlockPos.MutableBlockPos mutpos = new BlockPos.MutableBlockPos().set(pos);

        if (canPlace) {

            // If a rope can be placed, continue placing them downwards.

            while (level.getBlockState(mutpos).is(BlockTags.REPLACEABLE) && remainingLength > 0) {
                setRope(level, mutpos);
                remainingLength--;
                mutpos.move(Direction.DOWN);
            }
        }

        Vec3 dropPos = canPlace ? mutpos.above().getCenter() : mutpos.getCenter();
        // Drop leftover rope.
        level.addFreshEntity(new ItemEntity(
                level,
                dropPos.x,
                dropPos.y,
                dropPos.z,
                new ItemStack(BlockRegistry.ROPE.get(), remainingLength)
        ));

        // Discard the entity
        this.discard();
    }

    // Sets a rope with an effect.
    private void setRope(Level level, BlockPos pos) {
        BlockState rope = BlockRegistry.ROPE.get().defaultBlockState();
        level.addDestroyBlockEffect(pos, rope);
        level.destroyBlock(pos, true);
        level.setBlockAndUpdate(pos, rope);
    }

    @Override
    protected boolean canHitEntity(Entity entity) {
        return false;
    }
}
