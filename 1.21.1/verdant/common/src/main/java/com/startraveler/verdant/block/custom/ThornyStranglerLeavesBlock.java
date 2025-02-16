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
package com.startraveler.verdant.block.custom;

import com.startraveler.verdant.VerdantIFF;
import com.startraveler.verdant.registry.DamageSourceRegistry;
import com.startraveler.verdant.registry.TriggerRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import java.util.function.Supplier;

// TODO Implement damage types, with data generation!
// https://docs.neoforged.net/docs/resources/server/damagetypes/
public class ThornyStranglerLeavesBlock extends StranglerLeavesBlock {

    protected static final Supplier<MobEffectInstance> POISON = () -> new MobEffectInstance(MobEffects.POISON, 100, 0);

    public ThornyStranglerLeavesBlock(Properties properties) {
        super(properties);
    }

    // Harm players who step on it.
    // Copied from ThornBushBlock.
    @Override
    public void stepOn(Level level, BlockPos pos, BlockState state, Entity entity) {
        if (entity instanceof LivingEntity le && entity.getType() != EntityType.FOX && entity.getType() != EntityType.BEE && VerdantIFF.isEnemy(
                le)) {
            entity.makeStuckInBlock(state, new Vec3((double) 0.9F, 1.0D, (double) 0.9F));
            if (level instanceof ServerLevel serverLevel) {
                Holder<DamageType> type = DamageSourceRegistry.get(level.registryAccess(), DamageSourceRegistry.BRIAR);
                DamageSource source = new DamageSource(type, (Entity) null);
                if (le instanceof ServerPlayer player) {
                    TriggerRegistry.VERDANT_PLANT_ATTACK_TRIGGER.get().trigger(player);
                }
                entity.hurtServer(serverLevel, source, 2.0F);
            }
        }
    }
}

