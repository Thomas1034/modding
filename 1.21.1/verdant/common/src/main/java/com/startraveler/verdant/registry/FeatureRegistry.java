package com.startraveler.verdant.registry;

import com.startraveler.verdant.Constants;
import com.startraveler.verdant.feature.custom.FixedSeagrassFeature;
import com.startraveler.verdant.feature.custom.StranglerVineFeature;
import com.startraveler.verdant.registration.RegistrationProvider;
import com.startraveler.verdant.registration.RegistryObject;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.ProbabilityFeatureConfiguration;

public class FeatureRegistry {

    public static final RegistrationProvider<Feature<?>> FEATURES = RegistrationProvider.get(
            Registries.FEATURE,
            Constants.MOD_ID
    );

    public static final RegistryObject<Feature<?>, Feature<?>> FIXED_SEAGRASS = FEATURES.register(
            "fixed_seagrass",
            () -> new FixedSeagrassFeature(ProbabilityFeatureConfiguration.CODEC)
    );
    public static final RegistryObject<Feature<?>, Feature<?>> STRANGLER_VINES = FEATURES.register(
            "strangler_vines",
            () -> new StranglerVineFeature(NoneFeatureConfiguration.CODEC)
    );


    public static void init() {

    }

}

