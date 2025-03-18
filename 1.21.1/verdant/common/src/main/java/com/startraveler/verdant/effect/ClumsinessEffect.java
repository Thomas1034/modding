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
package com.startraveler.verdant.effect;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;

public class ClumsinessEffect extends MobEffect {

    public static final int DROP_HOW_OFTEN_IN_TICKS = 100;
    public static final float TOSS_SPEED = 0.2f;

    public ClumsinessEffect(MobEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public boolean applyEffectTick(ServerLevel level, LivingEntity entity, int amplifier) {

        if (level.random.nextInt(DROP_HOW_OFTEN_IN_TICKS / (amplifier + 1)) == 0) {
            if (entity.hasItemInSlot(EquipmentSlot.MAINHAND)) {
                tossItemInSlot(level, entity, EquipmentSlot.MAINHAND);
            } else if (entity.hasItemInSlot(EquipmentSlot.OFFHAND)) {
                tossItemInSlot(level, entity, EquipmentSlot.OFFHAND);
            }
        }
        return true;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return true;
    }

    protected void tossItemInSlot(ServerLevel level, LivingEntity entity, EquipmentSlot slot) {
        ItemStack held = entity.getItemBySlot(EquipmentSlot.OFFHAND);
        entity.setItemSlot(EquipmentSlot.OFFHAND, ItemStack.EMPTY);
        ItemEntity item = entity.spawnAtLocation(level, held);
        if (item != null) {
            item.setDeltaMovement(entity.getLookAngle().scale(TOSS_SPEED));
        }
    }
}

