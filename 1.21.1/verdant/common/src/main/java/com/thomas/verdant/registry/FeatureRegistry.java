package com.thomas.verdant.registry;

import com.thomas.verdant.Constants;
import com.thomas.verdant.registration.RegistrationProvider;
import com.thomas.verdant.registration.RegistryObject;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.Feature;

public class FeatureRegistry {

    public static final RegistrationProvider<Feature<?>> FEATURES = RegistrationProvider.get(
            Registries.FEATURE,
            Constants.MOD_ID
    );

    public static final RegistryObject<Feature<?>, Feature<?>> AROUND_ABOVE_FEATURE = null;


    public static void init() {

    }

}

