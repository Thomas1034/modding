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
import com.startraveler.verdant.block.custom.SpreadingRootsBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class HeartOfTheForestItem extends Item implements VerdantGrower {

    public HeartOfTheForestItem(Properties properties) {
        super(properties);
    }

    // Spreads the verdant around the player that holds it.
    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity holder, int iInt, boolean isHeld) {
        // Ensure it is on the server.
        if (level instanceof ServerLevel serverLevel) {

            // Repeat based on the number of items in the stack.
            for (int i = 0; i < stack.getCount(); i++) {
                // System.out.println("Heart is ticking");
                // The range to convert blocks in; radius of 3.
                BlockPos posToTry = SpreadingRootsBlock.withinDist(holder.getOnPos(), 3, level.random);

                // Try to erode the block, then try to convert it.
                this.erodeOrGrow(serverLevel, posToTry, holder.isInWaterOrBubble());
            }
        }
    }

}

