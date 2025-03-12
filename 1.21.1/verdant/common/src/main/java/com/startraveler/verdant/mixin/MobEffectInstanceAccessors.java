package com.startraveler.verdant.mixin;

import net.minecraft.world.effect.MobEffectInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(MobEffectInstance.class)
public interface MobEffectInstanceAccessors {
    @Accessor("hiddenEffect")
    MobEffectInstance verdant$hiddenEffect();
}
