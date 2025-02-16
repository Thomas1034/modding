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
package com.startraveler.verdant;

import com.startraveler.verdant.item.component.VerdantFriendliness;
import com.startraveler.verdant.registry.DataComponentRegistry;
import com.startraveler.verdant.util.VerdantTags;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

import java.util.Collection;

public class VerdantIFF {

    public static boolean isEnemy(Entity entity) {
        return getFriendliness(entity) < 1;
    }

    @SuppressWarnings("unused")
    public static boolean isFriend(Entity entity) {
        return getFriendliness(entity) >= 1;
    }

    public static float getFriendliness(Entity entity) {
        float friendliness = 0;
        if (entity.getType().is(VerdantTags.EntityTypes.VERDANT_FRIENDLY_ENTITIES)) {
            friendliness += 1;
        }
        friendliness += getArmorFriendliness(entity);
        if (entity instanceof LivingEntity livingEntity) {
            Collection<MobEffectInstance> instances = livingEntity.getActiveEffects();
            for (MobEffectInstance instance : instances) {
                if (instance.getEffect().is(VerdantTags.MobEffects.VERDANT_FRIEND)) {
                    friendliness += 1;
                }
                if (instance.getEffect().is(VerdantTags.MobEffects.STRONG_VERDANT_FRIENDLINESS)) {
                    friendliness += 0.5f;
                }
                if (instance.getEffect().is(VerdantTags.MobEffects.STRONG_VERDANT_FRIENDLINESS)) {
                    friendliness += 0.5f;
                }
                if (instance.getEffect().is(VerdantTags.MobEffects.WEAK_VERDANT_FRIENDLINESS)) {
                    friendliness += 0.3f;
                }
            }
        }
        return friendliness;
    }


    protected static float getArmorFriendliness(Entity entity) {
        if (entity instanceof LivingEntity livingEntity) {
            float armorFriendliness = 0;
            for (ItemStack stack : livingEntity.getArmorAndBodyArmorSlots()) {
                VerdantFriendliness value = stack.get(DataComponentRegistry.VERDANT_FRIENDLINESS.get());
                armorFriendliness += value == null ? 0 : value.sway();
            }

            // Get rider and ridden.
            Entity vehicle = livingEntity.getControlledVehicle();

            armorFriendliness += getArmorFriendliness(vehicle);

            return armorFriendliness;
        }
        return 0;
    }

}

