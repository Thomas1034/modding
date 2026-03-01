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
package com.startraveler.verdant.entity.custom;

import com.startraveler.verdant.block.custom.RopeBlock;
import com.startraveler.verdant.item.component.RopeCoilData;
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
import net.minecraft.world.entity.projectile.throwableitemprojectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;

public class ThrownRopeEntity extends ThrowableItemProjectile {

    public ThrownRopeEntity(Level level, LivingEntity thrower, ItemStack stack) {
        super(EntityTypeRegistry.THROWN_ROPE.get(), thrower, level, stack);
    }

    public ThrownRopeEntity(EntityType<? extends ThrowableItemProjectile> type, Level level) {
        super(type, level);
    }

    @SuppressWarnings("unused")
    public ThrownRopeEntity(Level level) {
        super(EntityTypeRegistry.THROWN_ROPE.get(), level);
    }

    public ThrownRopeEntity(Level level, double x, double y, double z, ItemStack stack) {
        super(EntityTypeRegistry.THROWN_ROPE.get(), x, y, z, level, stack);
    }

    @Override
    protected @NotNull Item getDefaultItem() {
        return ItemRegistry.ROPE_COIL.get();
    }

    @Override
    protected void onHitBlock(@NotNull BlockHitResult hitResult) {
        super.onHitBlock(hitResult);
        // The level.
        Level level = this.level();

        // Ensure server side.
        if (level.isClientSide()) {
            return;
        }

        // Get the item stack.
        ItemStack stack = this.getItem();
        RopeCoilData ropeCoilData = stack.getOrDefault(DataComponentRegistry.ROPE_COIL.get(), RopeCoilData.DEFAULT);

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
        Block ropeDataRopeBlock = ropeCoilData.ropeBlock();
        RopeItem.tryPlaceRope(
                level,
                pos,
                ropeCoilData.length(),
                true,
                ropeDataRopeBlock.defaultBlockState().setValue(RopeBlock.GLOW_LEVEL, ropeCoilData.lightLevel()),
                ropeCoilData.hasHook() ? (ropeDataRopeBlock instanceof RopeBlock actualRopeBlock ? actualRopeBlock.getHook() : BlockRegistry.ROPE_HOOK.get()) : null,
                ropeCoilData.hangingBlock().getState()
        );

        // Discard the entity
        this.discard();
    }

    @Override
    protected boolean canHitEntity(@NotNull Entity entity) {
        return false;
    }
}

