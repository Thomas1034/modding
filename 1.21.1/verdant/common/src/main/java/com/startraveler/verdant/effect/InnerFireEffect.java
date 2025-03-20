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
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class InnerFireEffect extends MobEffect {

    public static final int FIRE_TICKS_PER_LEVEL = 100;

    public InnerFireEffect(MobEffectCategory category, int color) {
        super(category, color);
    }


    @Override
    public boolean applyEffectTick(ServerLevel level, LivingEntity entity, int amplifier) {
        if (!entity.fireImmune()) {
            int fireTime = (amplifier + 1) * FIRE_TICKS_PER_LEVEL;
            if (entity.getRemainingFireTicks() < fireTime) {
                entity.setRemainingFireTicks(fireTime);
            }
            BlockPos pos = entity.blockPosition();
            if (amplifier > 0 && BaseFireBlock.canBePlacedAt(level, pos, Direction.UP)) {
                BlockState fire = BaseFireBlock.getState(level, pos);
                level.setBlock(pos, fire, Block.UPDATE_ALL_IMMEDIATE);
            }
        }
        return true;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return true;
    }
}

