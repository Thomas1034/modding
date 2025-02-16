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

import it.unimi.dsi.fastutil.Pair;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;

import java.util.List;

public class ImmunityEffect extends MobEffect {

    private final List<Pair<Integer, Holder<MobEffect>>> immuneTo;

    public ImmunityEffect(MobEffectCategory category, int color, List<Pair<Integer, Holder<MobEffect>>> immuneTo) {
        super(category, color);
        this.immuneTo = immuneTo;
    }

    @Override
    public boolean applyEffectTick(ServerLevel level, LivingEntity entity, int amplifier) {
        super.applyEffectTick(level, entity, amplifier);
        boolean removedAny = false;
        // Remove listed effects
        for (Pair<Integer, Holder<MobEffect>> requiredAmplifierAndEffect : this.immuneTo) {
            removedAny |= requiredAmplifierAndEffect.left() <= amplifier && entity.removeEffect(
                    requiredAmplifierAndEffect.right());
        }
        if (removedAny && amplifier > 0) {
            entity.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 60, amplifier - 1));
        }
        return true;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return true;
    }
}

