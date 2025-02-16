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
package com.startraveler.verdant.entity.custom;

import com.startraveler.verdant.registry.EntityTypeRegistry;
import com.startraveler.verdant.registry.ItemRegistry;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;

import java.util.function.Supplier;

public class PoisonArrowEntity extends AbstractArrow {

    public final Supplier<MobEffectInstance> effect = () -> new MobEffectInstance(MobEffects.POISON, 60, 1);

    public PoisonArrowEntity(Level level, double x, double y, double z, ItemStack itemStack, ItemStack firedFromWeapon) {
        super(EntityTypeRegistry.POISON_ARROW.get(), x, y, z, level, itemStack, firedFromWeapon);
    }

    public PoisonArrowEntity(Level level, LivingEntity shooter, ItemStack itemStack, ItemStack weapon) {
        super(EntityTypeRegistry.POISON_ARROW.get(), shooter, level, itemStack, weapon);
    }

    public PoisonArrowEntity(EntityType<? extends AbstractArrow> type, Level level) {
        super(type, level);
    }

    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        Entity e = result.getEntity();

        if (!e.level().isClientSide && e instanceof LivingEntity le) {
            le.addEffect(this.effect.get());
        }
    }


    @Override
    protected ItemStack getDefaultPickupItem() {
        return new ItemStack(ItemRegistry.POISON_ARROW.get());
    }
}

