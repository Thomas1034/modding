package com.thomas.verdant.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.thomas.verdant.Constants;
import com.thomas.verdant.registry.MobEffectRegistry;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LivingEntity.class)
public class DecreaseDetectionRangeMixin {
    @ModifyReturnValue(method = "getVisibilityPercent", at = @At("RETURN"))
    private double decreaseVisibilityPercentDueToStench(double original, Entity lookingEntity) {
        int stenchLevel = 0;

        LivingEntity lookedAtEntity = (LivingEntity) (Object) this;

        Constants.LOG.warn("Checking {}", lookedAtEntity.getType());
        MobEffectInstance instance = lookedAtEntity.getEffect(MobEffectRegistry.STENCH.asHolder());
        if (instance != null) {
            stenchLevel += instance.getAmplifier() + 1;
            Constants.LOG.warn("Level is {}", stenchLevel);

        }

        double stenchMultiplier = 1.0 / (1.0 + stenchLevel);
        return original * stenchMultiplier;
    }
}
