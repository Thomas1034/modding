package com.startraveler.verdant.effect;

import it.unimi.dsi.fastutil.Pair;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;

import java.util.List;

public class ImmunityEffect extends MobEffect {

    private final List<Pair<Integer, Holder<MobEffect>>> immuneTo;

    public ImmunityEffect(MobEffectCategory category, int color, List<Pair<Integer, Holder<MobEffect>>> immuneTo) {
        super(category, color);
        this.immuneTo = immuneTo;
    }

    @Override
    public boolean applyEffectTick(ServerLevel level, LivingEntity entity, int amplifier) {
        super.applyEffectTick(level, entity, amplifier);
        boolean removedAny = false;
        // Remove listed effects
        for (Pair<Integer, Holder<MobEffect>> requiredAmplifierAndEffect : this.immuneTo) {
            removedAny |= requiredAmplifierAndEffect.left() <= amplifier && entity.removeEffect(
                    requiredAmplifierAndEffect.right());
        }
        if (removedAny && amplifier > 0) {
            entity.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 60, amplifier - 1));
        }
        return true;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return true;
    }
}
