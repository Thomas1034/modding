package com.thomas.verdant.registry;

import com.thomas.verdant.Constants;
import com.thomas.verdant.registration.RegistrationProvider;
import com.thomas.verdant.registration.RegistryObject;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.alchemy.Potion;

public class PotionRegistry {

    public static final RegistrationProvider<Potion> POTIONS = RegistrationProvider.get(
            Registries.POTION,
            Constants.MOD_ID
    );

    public static final RegistryObject<Potion, Potion> FOOD_POISONING = POTIONS.register(
            "food_poisoning",
            () -> new Potion(
                    "food_poisoning",
                    new MobEffectInstance(MobEffectRegistry.FOOD_POISONING.asHolder(), 3600, 0)
            )
    );
    public static final RegistryObject<Potion, Potion> LONG_FOOD_POISONING = POTIONS.register(
            "long_food_poisoning",
            () -> new Potion(
                    "long_food_poisoning",
                    new MobEffectInstance(MobEffectRegistry.FOOD_POISONING.asHolder(), 9600, 0)
            )
    );
    public static final RegistryObject<Potion, Potion> STRONG_FOOD_POISONING = POTIONS.register(
            "strong_food_poisoning", () -> new Potion(
                    "strong_food_poisoning",
                    new MobEffectInstance(MobEffectRegistry.FOOD_POISONING.asHolder(), 1800, 1)
            )
    );

    public static final RegistryObject<Potion, Potion> ASPHYXIATING = POTIONS.register(
            "asphyxiating",
            () -> new Potion("asphyxiating", new MobEffectInstance(MobEffectRegistry.ASPHYXIATING.asHolder(), 3600, 0))
    );
    public static final RegistryObject<Potion, Potion> LONG_ASPHYXIATING = POTIONS.register(
            "long_asphyxiating",
            () -> new Potion(
                    "long_asphyxiating",
                    new MobEffectInstance(MobEffectRegistry.ASPHYXIATING.asHolder(), 9600, 0)
            )
    );
    public static final RegistryObject<Potion, Potion> STRONG_ASPHYXIATING = POTIONS.register(
            "strong_asphyxiating",
            () -> new Potion(
                    "strong_asphyxiating",
                    new MobEffectInstance(MobEffectRegistry.ASPHYXIATING.asHolder(), 1800, 1)
            )
    );

    public static final RegistryObject<Potion, Potion> CAFFEINE = POTIONS.register(
            "caffeine",
            () -> new Potion("caffeine", new MobEffectInstance(MobEffectRegistry.CAFFEINATED.asHolder(), 3600, 0))
    );
    public static final RegistryObject<Potion, Potion> LONG_CAFFEINE = POTIONS.register(
            "long_caffeine",
            () -> new Potion("long_caffeine", new MobEffectInstance(MobEffectRegistry.CAFFEINATED.asHolder(), 9600, 0))
    );
    public static final RegistryObject<Potion, Potion> STRONG_CAFFEINE = POTIONS.register(
            "strong_caffeine",
            () -> new Potion(
                    "strong_caffeine",
                    new MobEffectInstance(MobEffectRegistry.CAFFEINATED.asHolder(), 1800, 1)
            )
    );
    public static final RegistryObject<Potion, Potion> LONG_STRONG_CAFFEINE = POTIONS.register(
            "long_strong_caffeine",
            () -> new Potion(
                    "long_strong_caffeine",
                    new MobEffectInstance(MobEffectRegistry.CAFFEINATED.asHolder(), 3600, 1)
            )
    );

    public static final RegistryObject<Potion, Potion> COLLOID = POTIONS.register(
            "colloid",
            () -> new Potion("colloid", new MobEffectInstance(MobEffectRegistry.COLLOID.asHolder(), 3600, 0))
    );
    public static final RegistryObject<Potion, Potion> LONG_COLLOID = POTIONS.register(
            "long_colloid",
            () -> new Potion("long_colloid", new MobEffectInstance(MobEffectRegistry.COLLOID.asHolder(), 9600, 0))
    );
    public static final RegistryObject<Potion, Potion> STRONG_COLLOID = POTIONS.register(
            "strong_colloid",
            () -> new Potion("strong_colloid", new MobEffectInstance(MobEffectRegistry.COLLOID.asHolder(), 1800, 1))
    );

    public static final RegistryObject<Potion, Potion> ANTIDOTE = POTIONS.register(
            "antidote",
            () -> new Potion("antidote", new MobEffectInstance(MobEffectRegistry.ANTIDOTE.asHolder(), 1800, 0))
    );
    public static final RegistryObject<Potion, Potion> LONG_ANTIDOTE = POTIONS.register(
            "long_antidote",
            () -> new Potion("long_antidote", new MobEffectInstance(MobEffectRegistry.ANTIDOTE.asHolder(), 4800, 0))
    );
    public static final RegistryObject<Potion, Potion> STRONG_ANTIDOTE = POTIONS.register(
            "strong_antidote",
            () -> new Potion("strong_antidote", new MobEffectInstance(MobEffectRegistry.ANTIDOTE.asHolder(), 900, 1))
    );

    public static final RegistryObject<Potion, Potion> STENCH = POTIONS.register(
            "stench",
            () -> new Potion("stench", new MobEffectInstance(MobEffectRegistry.STENCH.asHolder(), 3600, 0))
    );
    public static final RegistryObject<Potion, Potion> LONG_STENCH = POTIONS.register(
            "long_stench",
            () -> new Potion("long_stench", new MobEffectInstance(MobEffectRegistry.STENCH.asHolder(), 9600, 0))
    );
    public static final RegistryObject<Potion, Potion> STRONG_STENCH = POTIONS.register(
            "strong_stench",
            () -> new Potion("strong_stench", new MobEffectInstance(MobEffectRegistry.STENCH.asHolder(), 1800, 1))
    );

    public static void init() {
    }

}
