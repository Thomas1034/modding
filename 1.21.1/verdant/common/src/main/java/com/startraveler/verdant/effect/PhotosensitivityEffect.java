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

import com.startraveler.verdant.registry.DamageSourceRegistry;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.LightLayer;

public class PhotosensitivityEffect extends MobEffect {

    public static final int FIRE_TICKS_PER_LEVEL = 100;

    public PhotosensitivityEffect(MobEffectCategory category, int color) {
        super(category, color);
    }


    @Override
    public boolean applyEffectTick(ServerLevel level, LivingEntity entity, int amplifier) {
        int entityLightLevel = entity.level().getBrightness(LightLayer.SKY, entity.blockPosition());
        if (entityLightLevel >= 0xF - (amplifier << 1) && (entity.tickCount % (40 / (amplifier + 1)) == 0) && level.isDay()) {
            entity.hurtServer(
                    level,
                    new DamageSource(DamageSourceRegistry.get(
                            level.registryAccess(),
                            DamageSourceRegistry.PHOTOSENSITIVE
                    )),
                    1
            );
        }
        return true;
    }


    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return true;
    }


}

