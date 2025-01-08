package com.thomas.verdant.effect;

import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;

import java.util.*;

public class AddictiveEffect extends MobEffect {

    public static final Map<Holder<MobEffect>, Holder<MobEffect>> OPPOSITES = new HashMap<>();

    static {
        registerEffects();
    }

    protected final List<Holder<MobEffect>> effects = new ArrayList<>();
    protected final float multiplier;

    protected AddictiveEffect(MobEffectCategory category, int color, float multiplier) {
        super(category, color);
        this.multiplier = multiplier;
    }

    @SafeVarargs
    public AddictiveEffect(MobEffectCategory category, int color, float multiplier, Holder<MobEffect>... effects) {
        this(category, color, multiplier);
        this.effects.addAll(Arrays.stream(effects).toList());
    }

    public static void register(Holder<MobEffect> e1, Holder<MobEffect> e2) {
        OPPOSITES.put(e1, e2);
        OPPOSITES.put(e2, e1);
    }

    public static void registerEffects() {
        register(MobEffects.GLOWING, MobEffects.INVISIBILITY);
        register(MobEffects.DAMAGE_BOOST, MobEffects.WEAKNESS);
        register(MobEffects.DIG_SLOWDOWN, MobEffects.DIG_SPEED);
        register(MobEffects.MOVEMENT_SLOWDOWN, MobEffects.MOVEMENT_SPEED);
        register(MobEffects.HARM, MobEffects.HEAL);
        register(MobEffects.HUNGER, MobEffects.SATURATION);
        register(MobEffects.REGENERATION, MobEffects.POISON);
    }

    @Override
    public boolean applyEffectTick(ServerLevel level, LivingEntity entity, int amplifier) {

        for (Holder<MobEffect> bonus : this.effects) {
            MobEffectInstance bonusInstance = new MobEffectInstance(bonus, 100, 1 + amplifier * 2);
            entity.addEffect(bonusInstance);
            Holder<MobEffect> malus = OPPOSITES.get(bonus);
            if (malus != null) {
                MobEffectInstance malusInstance = new MobEffectInstance(malus, 1800 + 1200 * (amplifier), amplifier);
                entity.addEffect(malusInstance);
            }
        }
        return super.applyEffectTick(level, entity, amplifier);
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return true;
    }
}

