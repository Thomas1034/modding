package com.thomas.verdant.registry.properties;

import com.thomas.verdant.registry.MobEffectRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.component.Consumable;
import net.minecraft.world.item.component.Consumables;
import net.minecraft.world.item.consume_effects.ApplyStatusEffectsConsumeEffect;

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
            .build();

    private ConsumablesList() {
    }


}
