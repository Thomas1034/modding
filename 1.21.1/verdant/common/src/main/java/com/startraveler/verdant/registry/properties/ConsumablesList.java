package com.startraveler.verdant.registry.properties;

import com.startraveler.verdant.registry.MobEffectRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.component.Consumable;
import net.minecraft.world.item.component.Consumables;
import net.minecraft.world.item.consume_effects.ApplyStatusEffectsConsumeEffect;

import java.util.List;

public class ConsumablesList {

    public static final Consumable COFFEE_BERRY = Consumables.defaultFood()
            .consumeSeconds(Consumables.DEFAULT_FOOD.consumeSeconds() / 2.0f)
            .onConsume(new ApplyStatusEffectsConsumeEffect(
                    new MobEffectInstance(
                            MobEffectRegistry.CAFFEINATED.asHolder(),
                            600,
                            0
                    ), 0.9F
            ))
            .build();
    public static final Consumable ROTTEN_COMPOST = Consumables.defaultFood()
            .soundAfterConsume(BuiltInRegistries.SOUND_EVENT.wrapAsHolder(SoundEvents.ZOMBIE_DEATH))
            .onConsume(new ApplyStatusEffectsConsumeEffect(
                    new MobEffectInstance(
                            MobEffectRegistry.FOOD_POISONING.asHolder(),
                            6000,
                            2
                    ), 0.9F
            ))
            .onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(MobEffects.HUNGER, 200, 2), 0.9F))
            .build();
    public static final Consumable RANCID_SLIME = Consumables.defaultFood()
            .soundAfterConsume(BuiltInRegistries.SOUND_EVENT.wrapAsHolder(SoundEvents.SLIME_JUMP))
            .onConsume(new ApplyStatusEffectsConsumeEffect(
                    new MobEffectInstance(
                            MobEffectRegistry.FOOD_POISONING.asHolder(),
                            1000,
                            1
                    ), 0.9F
            ))
            .onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(MobEffects.POISON, 200, 0), 0.9F))
            .build();
    public static final Consumable COOKED_GOLDEN_CASSAVA = Consumables.defaultFood()
            .onConsume(new ApplyStatusEffectsConsumeEffect(List.of(
                    new MobEffectInstance(
                            MobEffects.DAMAGE_RESISTANCE,
                            1200,
                            0
                    ),
                    new MobEffectInstance(MobEffects.ABSORPTION, 2400, 0)
            )))
            .build();
    public static final Consumable GOLDEN_BREAD = Consumables.defaultFood()
            .onConsume(new ApplyStatusEffectsConsumeEffect(List.of(new MobEffectInstance(
                    MobEffects.ABSORPTION,
                    2400,
                    1
            ))))
            .build();


    private ConsumablesList() {
    }


}
