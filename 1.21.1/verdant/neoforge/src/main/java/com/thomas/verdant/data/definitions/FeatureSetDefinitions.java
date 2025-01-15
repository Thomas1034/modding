package com.thomas.verdant.data.definitions;

import com.thomas.verdant.feature.JSONFeatures;
import com.thomas.verdant.registry.FeatureSetRegistry;
import com.thomas.verdant.util.Rarity;
import com.thomas.verdant.util.featureset.ConfiguredFeatureSetEntry;
import com.thomas.verdant.util.featureset.FeatureSet;
import net.minecraft.data.worldgen.features.CaveFeatures;
import net.minecraft.data.worldgen.features.VegetationFeatures;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

import java.util.ArrayList;
import java.util.List;

public class FeatureSetDefinitions {

    public static FeatureSet aboveGround() {

        List<FeatureSet.Entry> entries = new ArrayList<>();

        entries.add(configured(Rarity.VERY_COMMON, VegetationFeatures.PATCH_GRASS_JUNGLE));
        entries.add(configured(Rarity.COMMON, JSONFeatures.MIXED_BUSHES));
        entries.add(configured(Rarity.COMMON, VegetationFeatures.PATCH_TALL_GRASS));
        entries.add(configured(Rarity.UNCOMMON, VegetationFeatures.PATCH_LARGE_FERN));
        entries.add(configured(Rarity.RARE, JSONFeatures.BLEEDING_HEART));
        entries.add(configured(Rarity.RARE, JSONFeatures.ORCHIDS));
        entries.add(configured(Rarity.RARE, JSONFeatures.LILIES));
        entries.add(configured(Rarity.EXTREMELY_UNCOMMON, JSONFeatures.STINKING_BLOSSOM_FLOOR));
        entries.add(configured(Rarity.VERY_RARE, JSONFeatures.MIXED_FLOWERS));
        entries.add(configured(Rarity.EXTREMELY_RARE, JSONFeatures.COFFEE));
        entries.add(configured(Rarity.EXTREMELY_RARE, JSONFeatures.MIXED_MUSHROOMS));
        entries.add(configured(Rarity.EXTREMELY_RARE, JSONFeatures.BAMBOO));


        return new FeatureSet(entries, FeatureSetRegistry.ABOVE_GROUND);
    }

    public static FeatureSet hanging() {
        List<FeatureSet.Entry> entries = new ArrayList<>();

        entries.add(configured(Rarity.COMMON, JSONFeatures.VINES_CEILING));
        entries.add(configured(Rarity.UNCOMMON, JSONFeatures.STRANGLER_TENDRIL_CEILING));
        entries.add(configured(Rarity.RARE, CaveFeatures.CAVE_VINE));
        entries.add(configured(Rarity.VERY_UNCOMMON, JSONFeatures.POISON_IVY_CEILING));
        entries.add(configured(Rarity.VERY_RARE, JSONFeatures.STINKING_BLOSSOM_CEILING));
        entries.add(configured(Rarity.VERY_UNCOMMON, JSONFeatures.GLOW_LICHEN_CEILING));
        entries.add(configured(Rarity.EXTREMELY_RARE, CaveFeatures.SPORE_BLOSSOM));
        return new FeatureSet(entries, FeatureSetRegistry.HANGING);
    }

    public static FeatureSet water() {

        List<FeatureSet.Entry> entries = new ArrayList<>();

        // TODO Fix seagrass spreading water everywhere! Seagrass feature doesn't check for water source.
        entries.add(configured(Rarity.VERY_COMMON, JSONFeatures.FIXED_SEAGRASS_TALL));
        entries.add(configured(Rarity.COMMON, JSONFeatures.FIXED_SEAGRASS_SHORT));
        entries.add(configured(Rarity.RARE, VegetationFeatures.PATCH_WATERLILY));
        entries.add(configured(Rarity.EXTREMELY_RARE, JSONFeatures.DROWNED_HEMLOCK));

        return new FeatureSet(entries, FeatureSetRegistry.WATER);
    }

    public static FeatureSet always() {

        List<FeatureSet.Entry> entries = new ArrayList<>();

        entries.add(configured(5, JSONFeatures.NO_OP));

        return new FeatureSet(entries, FeatureSetRegistry.ALWAYS);
    }

    public static FeatureSet belowLog() {

        List<FeatureSet.Entry> entries = new ArrayList<>();

        entries.add(configured(1, JSONFeatures.STRANGLER_VINES));

        return new FeatureSet(entries, FeatureSetRegistry.BELOW_LOG);
    }

    public static ConfiguredFeatureSetEntry configured(int weight, ResourceKey<ConfiguredFeature<?, ?>> feature) {
        return new ConfiguredFeatureSetEntry(feature.location(), weight);
    }

}
