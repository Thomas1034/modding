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

import com.startraveler.verdant.util.VerdantTags;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class ChokingEffect extends MobEffect {

    protected static final int ROUNDING_TO = 20;

    public ChokingEffect(MobEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public boolean applyEffectTick(ServerLevel level, LivingEntity entity, int amplifier) {
        super.applyEffectTick(level, entity, amplifier);

        float amountOfAirToTake = 2 * amplifier + 5.5f;

        for (MobEffectInstance effectInstance : entity.getActiveEffects()) {
            if (effectInstance.getEffect().is(VerdantTags.MobEffects.AIRLESS_BREATHING)) {
                amountOfAirToTake -= 2 * (1 + effectInstance.getAmplifier());
            }
        }

        amountOfAirToTake -= 0.5f * (float) entity.getAttributeValue(Attributes.OXYGEN_BONUS);

        int finalAmountOfAirToTake = (int) amountOfAirToTake;
        amountOfAirToTake -= finalAmountOfAirToTake;
        if (amountOfAirToTake * ROUNDING_TO < level.getGameTime() % ROUNDING_TO) {
            finalAmountOfAirToTake++;
        }

        if (finalAmountOfAirToTake > 0) {
            int newAirSupply = entity.getAirSupply() - finalAmountOfAirToTake;
            if (newAirSupply <= 0) {
                entity.hurt(entity.damageSources().drown(), 2.0f);
            } else {
                entity.setAirSupply(entity.getAirSupply() - finalAmountOfAirToTake);

            }
        }

        return true;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return true;
    }
}

