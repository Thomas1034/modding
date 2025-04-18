package com.startraveler.verdant.data;

import com.startraveler.verdant.registry.DamageSourceRegistry;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageEffects;
import net.minecraft.world.damagesource.DamageScaling;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DeathMessageType;

public class VerdantDamageSourceProvider {

    public static void register(BootstrapContext<DamageType> context) {
        context.register(
                DamageSourceRegistry.BRIAR, new DamageType(
                        deathMessage(DamageSourceRegistry.BRIAR),
                        DamageScaling.ALWAYS,
                        0.02f,
                        DamageEffects.POKING,
                        DeathMessageType.INTENTIONAL_GAME_DESIGN
                )
        );
        context.register(
                DamageSourceRegistry.PHOTOSENSITIVE, new DamageType(
                        deathMessage(DamageSourceRegistry.PHOTOSENSITIVE),
                        DamageScaling.ALWAYS,
                        0.1f,
                        DamageEffects.BURNING,
                        DeathMessageType.INTENTIONAL_GAME_DESIGN
                )
        );
        context.register(
                DamageSourceRegistry.TOXIC_ASH, new DamageType(
                        deathMessage(DamageSourceRegistry.TOXIC_ASH),
                        DamageScaling.WHEN_CAUSED_BY_LIVING_NON_PLAYER,
                        0.1f,
                        DamageEffects.BURNING,
                        DeathMessageType.INTENTIONAL_GAME_DESIGN
                )
        );
    }

    private static String deathMessage(ResourceKey<DamageType> key) {
        return key.location().getNamespace() + "." + key.location().getPath();
    }
}
