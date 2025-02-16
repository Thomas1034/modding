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
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.BoneMealItem;
import net.minecraft.world.item.Items;

public class BoneMealEffect extends MobEffect {

    public BoneMealEffect(MobEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public boolean applyEffectTick(ServerLevel level, LivingEntity entity, int amplifier) {
        super.applyEffectTick(level, entity, amplifier);

        // Random chance.
        if (entity.canBeCollidedWith() && entity.getRandom().nextFloat() >= 0.05 * (amplifier + 1)) {


            // Applies bone meal below, at, and above the player.

            BoneMealItem.growCrop(Items.BONE_MEAL.getDefaultInstance(), entity.level(), entity.blockPosition());
            BoneMealItem.growCrop(Items.BONE_MEAL.getDefaultInstance(), entity.level(), entity.blockPosition().below());
            BoneMealItem.growCrop(Items.BONE_MEAL.getDefaultInstance(), entity.level(), entity.blockPosition().above());
        }
        return true;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return true;
    }
}

