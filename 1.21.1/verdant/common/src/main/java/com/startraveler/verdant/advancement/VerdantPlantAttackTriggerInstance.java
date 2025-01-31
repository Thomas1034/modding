package com.startraveler.verdant.advancement;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.startraveler.verdant.registry.TriggerRegistry;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;

import java.util.Optional;

public record VerdantPlantAttackTriggerInstance(Optional<ContextAwarePredicate> player) implements SimpleCriterionTrigger.SimpleInstance {
    public static final Codec<VerdantPlantAttackTriggerInstance> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                    ContextAwarePredicate.CODEC.optionalFieldOf("location").forGetter(VerdantPlantAttackTriggerInstance::player))
            .apply(instance, VerdantPlantAttackTriggerInstance::new));

    // In this example, EXAMPLE_TRIGGER is a DeferredHolder<CriterionTrigger<?>, ExampleTrigger>.
    // See below for how to register triggers.
    public static Criterion<VerdantPlantAttackTriggerInstance> instance(ContextAwarePredicate player) {
        return TriggerRegistry.VERDANT_PLANT_ATTACK_TRIGGER.get().createCriterion(new VerdantPlantAttackTriggerInstance(Optional.of(player)));
    }

    public boolean matches() {
        return true;
    }

}