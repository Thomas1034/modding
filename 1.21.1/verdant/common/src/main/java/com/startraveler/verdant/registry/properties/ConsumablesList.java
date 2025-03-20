/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * If you modify this file, please include a notice stating the changes:
 * Example: "Modified by [Your Name] on [Date] - [Short Description of Changes]"
 */
package com.startraveler.verdant.registry.properties;

import com.startraveler.verdant.item.component.AmplifyEffectsConsumeEffect;
import com.startraveler.verdant.item.component.DiminishEffectConsumeEffect;
import com.startraveler.verdant.registry.MobEffectRegistry;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.ItemUseAnimation;
import net.minecraft.world.item.component.Consumable;
import net.minecraft.world.item.component.Consumables;
import net.minecraft.world.item.consume_effects.ApplyStatusEffectsConsumeEffect;

import java.util.List;
import java.util.Optional;

public class ConsumablesList {

    public static final Consumable CROCODILE_TEAR = Consumables.defaultDrink()
            .consumeSeconds(Consumables.DEFAULT_DRINK.consumeSeconds() / 1.5f)
            .onConsume(new AmplifyEffectsConsumeEffect(Optional.empty()))
            .build();

    public static final Consumable ALOE_LEAF = Consumables.defaultFood()
            .consumeSeconds(Consumables.DEFAULT_FOOD.consumeSeconds() / 2.0f)
            .onConsume(new DiminishEffectConsumeEffect(MobEffectCategory.HARMFUL))
            .animation(ItemUseAnimation.CROSSBOW)
            .sound(Holder.direct(SoundEvents.EMPTY))
            .hasConsumeParticles(false)
            .build();
    public static final Consumable YOUNG_ALOE_LEAF = Consumables.defaultFood()
            .consumeSeconds(Consumables.DEFAULT_FOOD.consumeSeconds() / 2.0f)
            .onConsume(new DiminishEffectConsumeEffect(MobEffectCategory.HARMFUL))
            .animation(ItemUseAnimation.CROSSBOW)
            .sound(Holder.direct(SoundEvents.EMPTY))
            .hasConsumeParticles(false)
            .build();
    public static final Consumable OLD_ALOE_LEAF = Consumables.defaultFood()
            .consumeSeconds(Consumables.DEFAULT_FOOD.consumeSeconds() / 2.0f)
            .onConsume(new DiminishEffectConsumeEffect(MobEffectCategory.HARMFUL))
            .animation(ItemUseAnimation.CROSSBOW)
            .sound(Holder.direct(SoundEvents.EMPTY))
            .hasConsumeParticles(false)
            .build();
    public static final Consumable COFFEE_BERRY = Consumables.defaultFood()
            .consumeSeconds(Consumables.DEFAULT_FOOD.consumeSeconds() / 2.0f)
            .onConsume(new ApplyStatusEffectsConsumeEffect(
                    new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 200, 0),
                    0.9F
            ))
            .build();
    public static final Consumable ROASTED_COFFEE = Consumables.defaultFood()
            .consumeSeconds(Consumables.DEFAULT_FOOD.consumeSeconds() / 2.0f)
            .onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(MobEffects.DIG_SPEED, 200, 0), 0.9F))
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
                    new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 1200, 0),
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

