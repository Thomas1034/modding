package com.startraveler.verdant.entity.custom;

import com.startraveler.verdant.block.custom.RopeBlock;
import com.startraveler.verdant.item.component.RopeCoilData;
import com.startraveler.verdant.item.custom.RopeCoilItem;
import com.startraveler.verdant.item.custom.RopeItem;
import com.startraveler.verdant.registry.BlockRegistry;
import com.startraveler.verdant.registry.DataComponentRegistry;
import com.startraveler.verdant.registry.EntityTypeRegistry;
import com.startraveler.verdant.registry.ItemRegistry;
import com.startraveler.verdant.util.VerdantTags;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

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
        RopeCoilData dataComponent = stack.getOrDefault(
                DataComponentRegistry.ROPE_COIL.get(),
                RopeCoilItem.DEFAULT_DATA_COMPONENT
        );

        // Check if it hit a rope.
        BlockPos hitpos = hitResult.getBlockPos();
        BlockState hitState = level.getBlockState(hitpos);
        BlockPos pos;
        if (hitState.is(VerdantTags.Blocks.ROPES_EXTEND)) {
            // Extend the rope, find its bottom.
            pos = hitpos.below();
            while (level.getBlockState(pos).is(VerdantTags.Blocks.ROPES_EXTEND)) {
                pos = pos.below();
            }
        } else {
            // If not, place a rope offset from the block that was hit.
            pos = hitResult.getBlockPos().relative(hitResult.getDirection());
        }

        RopeItem.tryPlaceRope(
                level,
                pos,
                dataComponent.length(),
                true,
                BlockRegistry.ROPE.get().defaultBlockState().setValue(RopeBlock.GLOW_LEVEL, dataComponent.lightLevel()),
                dataComponent.hasHook() ? BlockRegistry.ROPE_HOOK.get() : null,
                dataComponent.lantern().state
        );

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
