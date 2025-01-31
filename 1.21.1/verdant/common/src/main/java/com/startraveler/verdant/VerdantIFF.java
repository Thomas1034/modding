package com.startraveler.verdant;

import com.startraveler.verdant.item.component.VerdantFriendliness;
import com.startraveler.verdant.registry.DataComponentRegistry;
import com.startraveler.verdant.registry.MobEffectRegistry;
import com.startraveler.verdant.util.VerdantTags;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class VerdantIFF {

    public static boolean isEnemy(Entity entity) {
        return !isFriend(entity);
    }

    public static boolean isFriend(Entity entity) {
        if (entity.getType().is(VerdantTags.EntityTypes.VERDANT_FRIENDLY_ENTITIES)) {
            return true;
        }
        if (entity instanceof LivingEntity livingEntity) {
            MobEffectInstance verdantEnergy = livingEntity.getEffect(MobEffectRegistry.VERDANT_ENERGY.asHolder());
            if (verdantEnergy != null) {
                return true;
            }

            float armorFriendliness = getArmorFriendliness(entity);

            if (armorFriendliness >= 1) {
                return true;
            }

        }
        // Stops compile warnings to preserve the structure of the code.
        boolean b;

        return false;
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
