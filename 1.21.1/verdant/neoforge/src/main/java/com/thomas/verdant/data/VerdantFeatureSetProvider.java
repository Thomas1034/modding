package com.thomas.verdant.data;

import com.thomas.verdant.data.definitions.FeatureSetDefinitions;
import com.thomas.verdant.registry.FeatureSetRegistry;
import com.thomas.verdant.util.featureset.FeatureSet;
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
