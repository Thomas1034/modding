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
package com.startraveler.verdant.advancement;


import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.startraveler.verdant.registry.TriggerRegistry;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.criterion.ContextAwarePredicate;
import net.minecraft.advancements.criterion.SimpleCriterionTrigger;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class VerdantPlantAttackTrigger extends SimpleCriterionTrigger<VerdantPlantAttackTrigger.@NotNull VerdantPlantAttackTriggerInstance> {
    @Override
    public @NotNull Codec<VerdantPlantAttackTriggerInstance> codec() {
        return VerdantPlantAttackTriggerInstance.CODEC;
    }

    public void trigger(ServerPlayer player) {

        this.trigger(
                player,
                // The condition checker method within the SimpleCriterionTrigger.SimpleInstance subclass
                VerdantPlantAttackTriggerInstance::matches
        );
    }

    // ...
    public record VerdantPlantAttackTriggerInstance(Optional<ContextAwarePredicate> player) implements SimpleInstance {
        public static final Codec<VerdantPlantAttackTriggerInstance> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                        ContextAwarePredicate.CODEC.optionalFieldOf("location")
                                .forGetter(VerdantPlantAttackTriggerInstance::player))
                .apply(instance, VerdantPlantAttackTriggerInstance::new));

        public static Criterion<@NotNull VerdantPlantAttackTriggerInstance> instance(ContextAwarePredicate player) {
            return TriggerRegistry.VERDANT_PLANT_ATTACK_TRIGGER.get()
                    .createCriterion(new VerdantPlantAttackTriggerInstance(Optional.of(player)));
        }

        public boolean matches() {
            return true;
        }

    }
}

