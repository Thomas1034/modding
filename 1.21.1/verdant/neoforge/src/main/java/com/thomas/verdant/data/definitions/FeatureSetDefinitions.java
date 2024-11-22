package com.thomas.verdant.data.definitions;

import com.thomas.verdant.registry.FeatureSetRegistry;
import com.thomas.verdant.util.featureset.ConfiguredFeatureSetEntry;
import com.thomas.verdant.util.featureset.FeatureSet;
import net.minecraft.data.worldgen.features.VegetationFeatures;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

import java.util.ArrayList;
import java.util.List;

public class FeatureSetDefinitions {

    public static FeatureSet aboveGround() {

        List<FeatureSet.Entry> entries = new ArrayList<>();

        entries.add(configured(3, VegetationFeatures.PATCH_GRASS_JUNGLE));
        entries.add(configured(1, VegetationFeatures.FLOWER_SWAMP));

        return new FeatureSet(entries, FeatureSetRegistry.ABOVE_GROUND);
    }

    public static ConfiguredFeatureSetEntry configured(int weight, ResourceKey<ConfiguredFeature<?, ?>> feature) {
        return new ConfiguredFeatureSetEntry(feature.location(), weight);
    }

}
