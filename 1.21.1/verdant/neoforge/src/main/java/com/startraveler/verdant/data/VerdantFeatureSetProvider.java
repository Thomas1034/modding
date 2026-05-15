package com.startraveler.verdant.data;

import com.startraveler.rootbound.featureset.FeatureSet;
import com.startraveler.verdant.data.definitions.FeatureSetDefinitions;
import com.startraveler.verdant.registry.FeatureSetRegistry;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;

public class VerdantFeatureSetProvider {

    public static void register(BootstrapContext<FeatureSet> bootstrap) {
        bootstrap.register(key(FeatureSetRegistry.ABOVE_GROUND), FeatureSetDefinitions.aboveGround(bootstrap));
        bootstrap.register(key(FeatureSetRegistry.HANGING), FeatureSetDefinitions.hanging(bootstrap));
        bootstrap.register(key(FeatureSetRegistry.WATER), FeatureSetDefinitions.water(bootstrap));
        bootstrap.register(key(FeatureSetRegistry.ALWAYS), FeatureSetDefinitions.always(bootstrap));
        bootstrap.register(key(FeatureSetRegistry.BELOW_LOG), FeatureSetDefinitions.belowLog(bootstrap));
        bootstrap.register(key(FeatureSetRegistry.MULCH), FeatureSetDefinitions.mulch(bootstrap));
        bootstrap.register(key(FeatureSetRegistry.LARGE_MULCH), FeatureSetDefinitions.largeMulch(bootstrap));
    }

    private static ResourceKey<FeatureSet> key(Identifier location) {
        return ResourceKey.create(FeatureSet.KEY, location);
    }
}
