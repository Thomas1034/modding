package com.thomas.verdant.registry;

import com.thomas.verdant.Constants;
import com.thomas.verdant.advancement.VerdantPlantAttackTrigger;
import com.thomas.verdant.registration.RegistrationProvider;
import com.thomas.verdant.registration.RegistryObject;
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
