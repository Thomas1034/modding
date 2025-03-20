package com.startraveler.verdant.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.startraveler.verdant.entity.custom.DartEntity;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.projectile.Arrow;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Arrow.class)
public class ArrowPostHurtEffectMixin {


    @ModifyExpressionValue(method = "doPostHurtEffects", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/effect/MobEffectInstance;mapDuration(Lit/unimi/dsi/fastutil/ints/Int2IntFunction;)I"))
    int t(int original, @Local MobEffectInstance mobEffectInstance) {

        return ((Object) this) instanceof DartEntity ? original : mobEffectInstance.getDuration();
    }
}
