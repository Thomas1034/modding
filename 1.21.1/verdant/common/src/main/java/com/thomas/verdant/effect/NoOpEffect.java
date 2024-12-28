package com.thomas.verdant.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

public class NoOpEffect extends MobEffect {
    public NoOpEffect(MobEffectCategory category, int color) {
        super(category, color);
    }
}
