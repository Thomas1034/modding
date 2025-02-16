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
import com.startraveler.verdant.effect.IntangibilityEffect;
import net.minecraft.client.renderer.ScreenEffectRenderer;
import net.minecraft.world.entity.player.Player;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ScreenEffectRenderer.class)
public class IntangibilityBlockVisionMixin {

    @ModifyExpressionValue(method = "renderScreenEffect", at = @At(value = "FIELD", target = "net/minecraft/world/entity/player/Player.noPhysics : Z", opcode = Opcodes.GETFIELD, ordinal = 0))
    private static boolean verdant$blockVisionWhenInsideBlocks(boolean original, @Local(ordinal = 0) Player player) {
        return original && !IntangibilityEffect.isIntangible(player);
    }

}

