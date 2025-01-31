package com.startraveler.verdant.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.startraveler.verdant.registry.MobEffectRegistry;
import net.minecraft.client.gui.Gui;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Gui.class)
public class NumbnessHideStatsMixin {

    @ModifyExpressionValue(method = "renderHearts", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/Mth;ceil(D)I", ordinal = 0))
    private int verdant$hideHearts(int original, @Local(argsOnly = true) Player player) {
        MobEffectInstance instance = player.getEffect(MobEffectRegistry.NUMBNESS.asHolder());
        return original - (instance != null ? (instance.getAmplifier() + 1) : 0);
    }

    @ModifyExpressionValue(method = "renderFood", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/food/FoodData;getFoodLevel()I", ordinal = 0))
    private int verdant$hideHunger(int original, @Local(argsOnly = true) Player player) {
        MobEffectInstance instance = player.getEffect(MobEffectRegistry.NUMBNESS.asHolder());
        return original - (instance != null ? (2 * (instance.getAmplifier() + 1)) : 0);
    }
}
