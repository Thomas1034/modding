package com.thomas.verdant.registry;

import com.thomas.verdant.Constants;
import com.thomas.verdant.effect.*;
import com.thomas.verdant.registration.RegistrationProvider;
import com.thomas.verdant.registration.RegistryObject;
import it.unimi.dsi.fastutil.Pair;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffects;

import java.util.List;

public class MobEffectRegistry {


    public static final RegistrationProvider<MobEffect> MOB_EFFECTS = RegistrationProvider.get(
            Registries.MOB_EFFECT,
            Constants.MOD_ID
    );

    public static final RegistryObject<MobEffect, MobEffect> FOOD_POISONING = MOB_EFFECTS.register(
            "food_poisoning", () -> new RandomInflictedEffect(
                    MobEffectCategory.HARMFUL,
                    0x94ac02,
                    0.005f,
                    new RandomInflictedEffect.WeightedEffectHolder(20, 200, MobEffects.HUNGER),
                    new RandomInflictedEffect.WeightedEffectHolder(10, 200, MobEffects.MOVEMENT_SLOWDOWN),
                    new RandomInflictedEffect.WeightedEffectHolder(10, 200, MobEffects.WEAKNESS),
                    new RandomInflictedEffect.WeightedEffectHolder(10, 100, MobEffects.CONFUSION),
                    new RandomInflictedEffect.WeightedEffectHolder(5, 200, MobEffects.DIG_SLOWDOWN),
                    new RandomInflictedEffect.WeightedEffectHolder(5, 200, MobEffects.POISON)
            )
    );
    public static final RegistryObject<MobEffect, MobEffect> CASSAVA_POISONING = MOB_EFFECTS.register(
            "cassava_poisoning", () -> new RandomInflictedEffect(
                    MobEffectCategory.HARMFUL, 0x4B5320, 0.25f, new RandomInflictedEffect.WeightedEffectHolder(
                    1,
                    600,
                    RandomInflictedEffect.WeightedEffectHolder.amplifierScaledByLevel(
                            MobEffects.MOVEMENT_SLOWDOWN,
                            5,
                            4,
                            0
                    )
            ), new RandomInflictedEffect.WeightedEffectHolder(
                    1,
                    600,
                    RandomInflictedEffect.WeightedEffectHolder.amplifierScaledByLevel(MobEffects.WEAKNESS, 5, 4, 1)
            ), new RandomInflictedEffect.WeightedEffectHolder(
                    1,
                    600,
                    RandomInflictedEffect.WeightedEffectHolder.amplifierScaledByLevel(MobEffects.DIG_SLOWDOWN, 5, 4, 2)
            ), new RandomInflictedEffect.WeightedEffectHolder(
                    1,
                    600,
                    RandomInflictedEffect.WeightedEffectHolder.amplifierScaledByLevel(
                            MobEffectRegistry.FOOD_POISONING.asHolder(),
                            5,
                            4,
                            3
                    )
            ), new RandomInflictedEffect.WeightedEffectHolder(
                    1,
                    600,
                    RandomInflictedEffect.WeightedEffectHolder.amplifierScaledByLevel(MobEffects.WITHER, 5, 4, 5)
            )
            )
    );
    public static final RegistryObject<MobEffect, MobEffect> CAFFEINATED = MOB_EFFECTS.register(
            "caffeinated",
            () -> new AddictiveEffect(
                    MobEffectCategory.NEUTRAL,
                    0x5c4033,
                    100,
                    MobEffects.MOVEMENT_SPEED,
                    MobEffects.DIG_SPEED
            )
    );
    public static final RegistryObject<MobEffect, MobEffect> VERDANT_ENERGY = MOB_EFFECTS.register(
            "verdant_energy",
            () -> new NoOpEffect(MobEffectCategory.BENEFICIAL, 0x2a7a1c)
    );
    public static final RegistryObject<MobEffect, MobEffect> COLLOID = MOB_EFFECTS.register(
            "colloid",
            () -> new ColloidEffect(MobEffectCategory.HARMFUL, 0xf7ebc8)
    );
    public static final RegistryObject<MobEffect, MobEffect> TRAPPED = MOB_EFFECTS.register(
            "trapped",
            () -> new NoOpEffect(MobEffectCategory.HARMFUL, 0x000000)
    );
    public static final RegistryObject<MobEffect, MobEffect> ANTIDOTE = MOB_EFFECTS.register(
            "antidote", () -> new ImmunityEffect(
                    MobEffectCategory.BENEFICIAL, 0x3dffb5, List.of(
                    Pair.of(0, MobEffects.CONFUSION),
                    Pair.of(0, MobEffects.POISON),
                    Pair.of(1, MobEffects.MOVEMENT_SLOWDOWN),
                    Pair.of(1, MobEffects.WITHER),
                    Pair.of(1, MobEffects.HUNGER),
                    Pair.of(2, MobEffects.BLINDNESS),
                    Pair.of(2, MobEffects.INFESTED),
                    Pair.of(2, MobEffects.OOZING)
            )
            )
    );
    public static final RegistryObject<MobEffect, MobEffect> ASPHYXIATING = MOB_EFFECTS.register(
            "asphyxiating",
            () -> new ChokingEffect(MobEffectCategory.HARMFUL, 0x0094ff)
    );
    public static final RegistryObject<MobEffect, MobEffect> STENCH = MOB_EFFECTS.register(
            "stench",
            () -> new NoOpEffect(MobEffectCategory.BENEFICIAL, 0x324500)
    );
    public static final RegistryObject<MobEffect, MobEffect> INTANGIBLE = MOB_EFFECTS.register(
            "intangible",
            () -> new IntangibilityEffect(MobEffectCategory.BENEFICIAL, 0xFFFFFF)
    );
    public static final RegistryObject<MobEffect, MobEffect> WEIGHTLESS = MOB_EFFECTS.register(
            "weightless",
            () -> new AntiGravityEffect(MobEffectCategory.NEUTRAL, 0x000000)
    );
    public static final RegistryObject<MobEffect, MobEffect> BROKEN_ARMOR = MOB_EFFECTS.register(
            "broken_armor",
            () -> new BrokenArmorEffect(MobEffectCategory.HARMFUL, 0.1f, 0x000050)
    );
    public static final RegistryObject<MobEffect, MobEffect> NUMBNESS = MOB_EFFECTS.register(
            "numbness",
            () -> new NoOpEffect(MobEffectCategory.HARMFUL, 0x000050)
    );
    public static final RegistryObject<MobEffect, MobEffect> BLURRED = MOB_EFFECTS.register(
            "blurred",
            () -> new NoOpEffect(MobEffectCategory.HARMFUL, 0x808080)
    );
    public static final RegistryObject<MobEffect, MobEffect> COLORBLIND = MOB_EFFECTS.register(
            "colorblind",
            () -> new NoOpEffect(MobEffectCategory.HARMFUL, 0xC0C0C0)
    );
    public static final RegistryObject<MobEffect, MobEffect> RED_GREEN = MOB_EFFECTS.register(
            "red_green",
            () -> new NoOpEffect(MobEffectCategory.HARMFUL, 0x0000FF)
    );
    public static final RegistryObject<MobEffect, MobEffect> SEPIA = MOB_EFFECTS.register(
            "sepia",
            () -> new NoOpEffect(MobEffectCategory.NEUTRAL, 0xAD9A78)
    );


    public static void init() {
    }
}
