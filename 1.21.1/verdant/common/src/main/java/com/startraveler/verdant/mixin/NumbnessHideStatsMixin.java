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
package com.startraveler.verdant.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.startraveler.verdant.registry.MobEffectRegistry;
import net.minecraft.client.gui.Gui;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Gui.class)
public class NumbnessHideStatsMixin {

    @ModifyExpressionValue(method = "renderHearts", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/Mth;ceil(D)I", ordinal = 0))
    private int verdant$hideHearts(int original, @Local(argsOnly = true) Player player) {
        MobEffectInstance instance = player.getEffect(MobEffectRegistry.NUMBNESS.asHolder());
        return original - (instance != null ? (instance.getAmplifier() + 1) : 0);
    }

    @ModifyExpressionValue(method = "renderFood", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/food/FoodData;getFoodLevel()I", ordinal = 0))
    private int verdant$hideHunger(int original, @Local(argsOnly = true) Player player) {
        MobEffectInstance instance = player.getEffect(MobEffectRegistry.NUMBNESS.asHolder());
        return original - (instance != null ? (2 * (instance.getAmplifier() + 1)) : 0);
    }
}

