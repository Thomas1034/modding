package com.startraveler.verdant.data;

import com.startraveler.verdant.data.definitions.FeatureSetDefinitions;
import com.startraveler.verdant.registry.FeatureSetRegistry;
import com.startraveler.verdant.util.featureset.FeatureSet;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

public class VerdantFeatureSetProvider {

    public static void register(BootstrapContext<FeatureSet> bootstrap) {
        bootstrap.register(key(FeatureSetRegistry.ABOVE_GROUND), FeatureSetDefinitions.aboveGround());
        bootstrap.register(key(FeatureSetRegistry.HANGING), FeatureSetDefinitions.hanging());
        bootstrap.register(key(FeatureSetRegistry.WATER), FeatureSetDefinitions.water());
        bootstrap.register(key(FeatureSetRegistry.ALWAYS), FeatureSetDefinitions.always());
        bootstrap.register(key(FeatureSetRegistry.BELOW_LOG), FeatureSetDefinitions.belowLog());
    }

    private static ResourceKey<FeatureSet> key(ResourceLocation location) {
        return ResourceKey.create(FeatureSet.KEY, location);
    }
}
