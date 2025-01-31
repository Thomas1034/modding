package com.thomas.verdant.advancement;


import com.mojang.serialization.Codec;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.server.level.ServerPlayer;

public class VerdantPlantAttackTrigger extends SimpleCriterionTrigger<VerdantPlantAttackTriggerInstance> {
    @Override
    public Codec<VerdantPlantAttackTriggerInstance> codec() {
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
}
