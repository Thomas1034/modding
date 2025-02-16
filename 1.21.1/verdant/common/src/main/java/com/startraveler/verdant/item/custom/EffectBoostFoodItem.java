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
package com.startraveler.verdant.item.custom;

import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.function.Function;
import java.util.function.Supplier;

public class EffectBoostFoodItem extends Item {

    private final Supplier<Holder<MobEffect>> effect;
    private final Function<Integer, MobEffectInstance> getEffect;

    public EffectBoostFoodItem(Properties properties, Supplier<Holder<MobEffect>> effect, Function<Integer, MobEffectInstance> getEffect) {
        super(properties);
        this.effect = effect;
        this.getEffect = getEffect;

    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity eater) {
        FoodProperties food = stack.get(DataComponents.FOOD);

        if (food != null && !level.isClientSide) {
            // Find the mob effect instance that the eater has.
            MobEffectInstance instance = eater.getEffect(this.effect.get());

            int amplifier = -1;
            // Check if it exists.
            if (instance != null) {
                amplifier = instance.getAmplifier();
            }
            MobEffectInstance newEffect = this.getEffect.apply(amplifier);
            eater.addEffect(newEffect);
        }
        return super.finishUsingItem(stack, level, eater);
    }

}
