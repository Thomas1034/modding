package com.startraveler.verdant.registry;

import com.startraveler.verdant.Constants;
import com.startraveler.verdant.advancement.VerdantPlantAttackTrigger;
import com.startraveler.verdant.registration.RegistrationProvider;
import com.startraveler.verdant.registration.RegistryObject;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.core.registries.Registries;

public class TriggerRegistry {

    public static final RegistrationProvider<CriterionTrigger<?>> TRIGGERS = RegistrationProvider.get(
            Registries.TRIGGER_TYPE,
            Constants.MOD_ID
    );

    public static final RegistryObject<CriterionTrigger<?>, VerdantPlantAttackTrigger> VERDANT_PLANT_ATTACK_TRIGGER = TRIGGERS.register("verdant_plant_attack_trigger",
            VerdantPlantAttackTrigger::new
    );

    public static void init() {
    }

}
