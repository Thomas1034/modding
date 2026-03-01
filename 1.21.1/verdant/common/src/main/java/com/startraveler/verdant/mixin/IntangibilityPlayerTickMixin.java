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

import com.startraveler.verdant.effect.IntangibilityEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// TODO needs opcode in At?
@Mixin(Player.class)
public abstract class IntangibilityPlayerTickMixin extends LivingEntity {

    protected IntangibilityPlayerTickMixin(EntityType<? extends LivingEntity> p_20966_, Level p_20967_) {
        super(p_20966_, p_20967_);
    }

    @Inject(method = "tick", at = @At(value = "FIELD", target = "net/minecraft/world/entity/player/Player.takeXpDelay : I", ordinal = 0))
    public void verdant$setNoPhysicsForIntangibility(CallbackInfo ci) {
        if (IntangibilityEffect.isIntangible(((Player) (Object) this))) {
            boolean shouldBeIntangible = IntangibilityEffect.canGoThroughBlockBeneath(this);
            this.noPhysics = shouldBeIntangible;
            this.setOnGround(!shouldBeIntangible);
        }
    }
}

