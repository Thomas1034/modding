package com.thomas.verdant;

import com.thomas.verdant.registry.MobEffectRegistry;
import com.thomas.verdant.util.VerdantTags;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

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
        }
        // Stops compile warnings to preserve the structure of the code.
        boolean b;

        return false;
    }

}
