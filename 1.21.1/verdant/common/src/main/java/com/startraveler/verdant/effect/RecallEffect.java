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


import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Relative;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Portal;
import net.minecraft.world.level.portal.TeleportTransition;
import net.minecraft.world.phys.Vec3;

import java.util.Set;

public class RecallEffect extends MobEffect implements Portal {

    public RecallEffect(MobEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public boolean applyEffectTick(ServerLevel level, LivingEntity entity, int amplifier) {
        boolean superReturned = super.applyEffectTick(level, entity, amplifier);

        if (entity.level().isClientSide()) {
            // Do client effects - like particles, if desired - here.
            return superReturned;
        }

        // Get the smallest remaining duration of this effect.
        int minimumRemainingDuration = entity.getActiveEffects()
                .stream()
                .filter(mobEffectInstance -> (mobEffectInstance.getEffect().value() == this))
                .mapToInt(MobEffectInstance::getDuration)
                .min()
                .orElse(Integer.MAX_VALUE);

        if (minimumRemainingDuration == 1) {
            entity.setAsInsidePortal(this, entity.blockPosition());
        }

        return true;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return true;
    }

    @Override
    public TeleportTransition getPortalDestination(ServerLevel level, Entity entity, BlockPos start) {
        ServerLevel targetLevel = level.getServer().getLevel(Level.OVERWORLD);
        if (targetLevel == null) {
            return null;
        } else {
            if (entity instanceof ServerPlayer serverPlayer) {
                return serverPlayer.findRespawnPositionAndUseSpawnBlock(false, TeleportTransition.DO_NOTHING);
            }

            BlockPos blockpos = targetLevel.getSharedSpawnPos();
            float facing = 0.0F;
            Set<Relative> set = Relative.union(Relative.DELTA, Relative.ROTATION);
            Vec3 vec3 = entity.adjustSpawnLocation(targetLevel, blockpos).getBottomCenter();

            return new TeleportTransition(
                    targetLevel,
                    vec3,
                    Vec3.ZERO,
                    facing,
                    0.0F,
                    set,
                    TeleportTransition.DO_NOTHING
            );
        }
    }
}

