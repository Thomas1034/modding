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
package com.startraveler.verdant.item.custom;

import com.startraveler.verdant.block.VerdantGrower;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class HeartOfTheForestItem extends Item implements VerdantGrower {

    public HeartOfTheForestItem(Properties properties) {
        super(properties);
    }

    // Generates a random position within the given distance of the block.
    // dist is the maximum Chebyshev distance, bounded between 0 and 128.
    // To avoid expensive random calls, this uses bit shifting to
    // extract the data from only one integer.
    // I probably don't need to do this, but I'm keeping it around. It can't hurt.
    public static BlockPos withinDist(BlockPos pos, int dist, RandomSource rand) {
        // Throw an error if it's outside the bounds.
        if (dist < 0 || dist > 127) {
            throw new IllegalArgumentException("Chebyshev must be in range 0 to 127.");
        }

        int range = 2 * dist + 1;
        // Generate a single random 32-bit number
        int num = rand.nextInt();
        // Extract offsets from different parts of the number
        int offsetX = (num & 0x7F) % range - dist;       // Lowest 7 bits
        int offsetY = ((num >> 7) & 0x7F) % range - dist; // Next 7 bits
        int offsetZ = ((num >> 14) & 0x7F) % range - dist; // Following 7 bits

        return pos.offset(offsetX, offsetY, offsetZ);
    }

    // Spreads the verdant around the player that holds it.
    @Override
    public void inventoryTick(@NotNull ItemStack stack, @NotNull ServerLevel level, @NotNull Entity holder, EquipmentSlot slot) {
        super.inventoryTick(stack, level, holder, slot);
        // Ensure it is on the server.
        if (level instanceof ServerLevel serverLevel) {

            // Repeat based on the number of items in the stack.
            for (int i = 0; i < stack.getCount(); i++) {
                // System.out.println("Heart is ticking");
                // The range to convert blocks in; radius of 3.
                BlockPos posToTry = withinDist(holder.getOnPos(), 3, level.getRandom());

                // Try to erode the block, then try to convert it.
                this.erodeOrGrow(serverLevel, posToTry, holder.isInWaterOrRain());
            }
        }
    }


}

