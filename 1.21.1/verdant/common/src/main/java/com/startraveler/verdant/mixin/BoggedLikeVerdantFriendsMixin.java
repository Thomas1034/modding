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
import com.startraveler.verdant.VerdantIFF;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.entity.monster.Bogged;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.function.BiFunction;

@Mixin(AbstractSkeleton.class)
public class BoggedLikeVerdantFriendsMixin {

    @ModifyExpressionValue(method = "registerGoals", at = @At(value = "NEW", target = "net/minecraft/world/entity/ai/goal/target/NearestAttackableTargetGoal"))
    private NearestAttackableTargetGoal<Player> verdant$makeBoggedLikeVerdantFriends(NearestAttackableTargetGoal<Player> original) {
        BiFunction<TargetingConditions.Selector, TargetingConditions.Selector, TargetingConditions.Selector> mergeAnd = ((selector1, selector2) -> (selector1 == null) ? selector2 : (selector2 == null) ? selector1 : ((livingEntity, serverLevel) -> (selector1.test(
                livingEntity,
                serverLevel
        ) && selector2.test(livingEntity, serverLevel))));

        TargetingConditions.Selector newConditions = (livingEntity, level) -> !(((AbstractSkeleton) (Object) this) instanceof Bogged) || VerdantIFF.isEnemy(
                livingEntity);

        TargetingConditions.Selector originalConditions = original.targetConditions.selector;

        original.targetConditions.selector(mergeAnd.apply(newConditions, originalConditions));

        return original;
    }

}

