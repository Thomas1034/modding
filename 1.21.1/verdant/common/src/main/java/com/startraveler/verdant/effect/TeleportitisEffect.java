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
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Fox;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;

public class TeleportitisEffect extends MobEffect {
    private final float diameter;

    public TeleportitisEffect(MobEffectCategory category, float diameter, int color) {
        super(category, color);
        this.diameter = diameter;
    }

    @Override
    public boolean applyEffectTick(ServerLevel level, LivingEntity entity, int amplifier) {
        int timeSinceHurt = entity.invulnerableDuration - entity.invulnerableTime;
        if (timeSinceHurt < 2) {
            int radius = (int) (this.diameter * (amplifier + 1));
            boolean teleportSucceeded = false;
            for (int i = 0; i < 16; ++i) {
                double newX = entity.getX() + (entity.getRandom().nextDouble() - 0.5F) * radius;
                double newY = Mth.clamp(
                        entity.getY() + (entity.getRandom().nextDouble() - 0.5F) * radius,
                        level.getMinY(),
                        (level.getMinY() + level.getLogicalHeight() - 1)
                );
                double newZ = entity.getZ() + (entity.getRandom().nextDouble() - 0.5F) * radius;
                if (entity.isPassenger()) {
                    entity.stopRiding();
                }

                Vec3 vec3 = entity.position();
                if (entity.randomTeleport(newX, newY, newZ, true)) {
                    level.gameEvent(GameEvent.TELEPORT, vec3, GameEvent.Context.of(entity));
                    SoundSource soundsource;
                    SoundEvent soundevent;
                    if (entity instanceof Fox) {
                        soundevent = SoundEvents.FOX_TELEPORT;
                        soundsource = SoundSource.NEUTRAL;
                    } else {
                        soundevent = SoundEvents.CHORUS_FRUIT_TELEPORT;
                        soundsource = SoundSource.PLAYERS;
                    }

                    level.playSound(null, entity.getX(), entity.getY(), entity.getZ(), soundevent, soundsource);
                    entity.resetFallDistance();
                    teleportSucceeded = true;
                    break;
                }
            }

            if (teleportSucceeded && entity instanceof Player player) {
                player.resetCurrentImpulseContext();
            }
        }
        return true;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return true;
    }
}

