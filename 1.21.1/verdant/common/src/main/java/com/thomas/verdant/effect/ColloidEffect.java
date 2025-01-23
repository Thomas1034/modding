package com.thomas.verdant.effect;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;

public class ColloidEffect extends MobEffect {
    public ColloidEffect(MobEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public boolean applyEffectTick(ServerLevel level, LivingEntity entity, int amplifier) {
        int timeSinceHurt = entity.invulnerableDuration - entity.invulnerableTime;
        if (timeSinceHurt < 2) {
            // Inflict stacked slowness debuffs.
            int baseTime = 5;
            int max = 5 * (amplifier + 2);
            for (int i = 0; i < max; i++) {
                entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, baseTime * (max - i), i));
            }
        }
        return true;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return true;
    }
}
