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

import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public interface DurabilityTickingItem {

    default void handleDurabilityChangeTick(ItemStack stack, Level level, Entity holder, int unknownParameter, boolean isHeld) {
        if (!(holder instanceof Player player) || (!player.getAbilities().instabuild && (isHeld || this.changesEvenIfNotHeld()))) {
            this.changeDurability(stack, level, holder);
        }
    }

    // Changes the item's durability over time, allowing both positive and negative changes
    default void changeDurability(ItemStack thisItem, Level level, Entity owner) {
        int damage = thisItem.getDamageValue();
        int maxDamage = thisItem.getMaxDamage();
        damage += this.getDamageAmt(level.random);

        // Ensure durability remains within valid bounds: between 0 and maxDamage
        if (damage > maxDamage) {
            damage = maxDamage;
        } else if (damage < 0) {
            damage = 0;
        }
        thisItem.setDamageValue(damage);
        // Handle item removal if its durability reaches max damage
        if (damage == thisItem.getMaxDamage() && thisItem.isStackable()) {
            thisItem.shrink(1);
        }
    }

    // Randomly calculates the amount to change durability by, based on the specified chance
    default int getDamageAmt(RandomSource rand) {
        // Chance to change durability every 'changesEvery' ticks
        int ticksBetweenChange = this.ticksBetweenChange();
        if (ticksBetweenChange < 0) {
            return 0;
        }
        boolean doStep = rand.nextInt(ticksBetweenChange) == 0;
        return doStep ? this.durabilityStep() : 0;
    }

    default int ticksBetweenChange() {
        return -1;
    }

    default int durabilityStep() {
        return 0;
    }

    default boolean changesEvenIfNotHeld() {
        return this.durabilityStep() < 0;
    }
}

