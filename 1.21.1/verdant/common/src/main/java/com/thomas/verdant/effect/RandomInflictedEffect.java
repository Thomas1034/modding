package com.thomas.verdant.effect;


import com.thomas.verdant.util.AliasBuilder;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

public class RandomInflictedEffect extends MobEffect {

    protected final Set<WeightedEffectHolder> effects = new HashSet<>();
    protected final float baseChancePerTick;
    protected Function<RandomSource, WeightedEffectHolder> effectGetter;

    public RandomInflictedEffect(MobEffectCategory mobEffectCategory, int color, float baseChancePerTick, WeightedEffectHolder... randomEffects) {
        super(mobEffectCategory, color);
        this.baseChancePerTick = baseChancePerTick;
        this.effects.addAll(Arrays.stream(randomEffects).toList());
        this.effectGetter = AliasBuilder.build(this.effects.stream()
                .collect(Collectors.toMap(holder -> holder, WeightedEffectHolder::weight)));
    }

    public void register(int weight, int duration, Holder<MobEffect> effect) {
        effects.add(new WeightedEffectHolder(weight, duration, effect));
    }

    // This effect inflicts other harmful effects with a low chance every tick.
    // This should be interesting.
    // The inflicted level is the same as that of this effect.
    // An inflicted effect is more likely if the amplifier is higher.
    @Override
    public boolean applyEffectTick(ServerLevel level, LivingEntity entity, int amplifier) {
        super.applyEffectTick(level, entity, amplifier);

        float effectChance = this.baseChancePerTick * (amplifier + 1);
        if (entity.getRandom().nextFloat() < effectChance) {

            // Get a random effect holder.
            WeightedEffectHolder holder = effectGetter.apply(level.random);
            MobEffectInstance instance = holder.get(amplifier);
            if (instance != null) {
                entity.addEffect(instance);
            }
        }
        return true;
    }

    public record WeightedEffectHolder(int weight,
            int duration,
            Holder<MobEffect> effect,
            BiFunction<Integer, Integer, MobEffectInstance> getter) {

        public WeightedEffectHolder(int weight, int duration, Holder<MobEffect> effect) {
            this(weight, duration, effect, null);
        }

        public WeightedEffectHolder(int weight, int duration, BiFunction<Integer, Integer, MobEffectInstance> getter) {
            this(weight, duration, null, getter);
        }

        public static BiFunction<Integer, Integer, MobEffectInstance> amplifierScaledByLevel(Holder<MobEffect> effect, int shift1, int scale, int shift2) {

            return (amplifier, duration) -> {
                int level = (amplifier - shift1) / scale - shift2;
                if (level >= 0) {
                    return new MobEffectInstance(effect, duration, level);
                }
                return null;
            };
        }

        public @Nullable MobEffectInstance get(int amplifier) {
            if (this.effect != null && this.getter == null) {
                return new MobEffectInstance(this.effect, this.duration, amplifier);
            } else if (this.effect == null && this.getter != null) {
                return getter.apply(amplifier, this.duration);
            }
            return null;
        }
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return true;
    }
}
