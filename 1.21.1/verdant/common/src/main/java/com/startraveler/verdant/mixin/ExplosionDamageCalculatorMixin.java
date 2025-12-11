package com.startraveler.verdant.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.startraveler.verdant.Constants;
import net.minecraft.world.level.ExplosionDamageCalculator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ExplosionDamageCalculator.class)
public class ExplosionDamageCalculatorMixin {

    @ModifyReturnValue(method = "getEntityDamageAmount(Lnet/minecraft/world/level/Explosion;Lnet/minecraft/world/entity/Entity;F)F", at = @At("RETURN"))
    public float multiplyThreadLocal(float original) {
        return original * Constants.EXPLOSION_DAMAGE_MULTIPLIER.get();
    }
}
