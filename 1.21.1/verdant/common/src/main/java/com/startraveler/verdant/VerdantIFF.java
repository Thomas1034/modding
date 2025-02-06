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
        return !isFriend(entity);
    }

    public static boolean isFriend(Entity entity) {
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

        return friendliness > 1;
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
