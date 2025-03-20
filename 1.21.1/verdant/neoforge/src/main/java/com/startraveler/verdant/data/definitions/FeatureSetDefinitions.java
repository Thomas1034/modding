package com.startraveler.verdant.data.definitions;

import com.startraveler.rootbound.featureset.ConfiguredFeatureSetEntry;
import com.startraveler.rootbound.featureset.FeatureSet;
import com.startraveler.verdant.feature.JSONFeatures;
import com.startraveler.verdant.registry.FeatureSetRegistry;
import com.startraveler.verdant.util.Rarity;
import net.minecraft.data.worldgen.features.CaveFeatures;
import net.minecraft.data.worldgen.features.VegetationFeatures;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

import java.util.ArrayList;
import java.util.List;

public class FeatureSetDefinitions {

    public static FeatureSet aboveGround() {

        List<FeatureSet.Entry> entries = new ArrayList<>();

        entries.add(configured(Rarity.EXTREMELY_COMMON, VegetationFeatures.PATCH_GRASS_JUNGLE));
        entries.add(configured(Rarity.VERY_COMMON, VegetationFeatures.PATCH_TALL_GRASS));
        entries.add(configured(Rarity.VERY_COMMON, VegetationFeatures.PATCH_LARGE_FERN));
        entries.add(configured(Rarity.COMMON, JSONFeatures.MIXED_BUSHES));
        entries.add(configured(Rarity.UNCOMMON, JSONFeatures.STINKING_BLOSSOM_FLOOR));
        entries.add(configured(Rarity.UNCOMMON, VegetationFeatures.VINES));
        entries.add(configured(Rarity.VERY_UNCOMMON, JSONFeatures.MOSS_CARPETS));
        entries.add(configured(Rarity.EXTREMELY_UNCOMMON, JSONFeatures.RUE));
        entries.add(configured(Rarity.EXTREMELY_UNCOMMON, JSONFeatures.BLEEDING_HEART));
        entries.add(configured(Rarity.EXTREMELY_UNCOMMON, JSONFeatures.WILD_CASSAVA));

        entries.add(configured(Rarity.RARE, JSONFeatures.BLASTING_BLOSSOMS));
        entries.add(configured(Rarity.RARE, JSONFeatures.ALOES));
        entries.add(configured(Rarity.RARE, JSONFeatures.LILIES));
        entries.add(configured(Rarity.RARE, JSONFeatures.ORCHIDS));
        entries.add(configured(Rarity.RARE, JSONFeatures.MIXED_FLOWERS));
        entries.add(configured(Rarity.RARE, JSONFeatures.MIXED_MUSHROOMS));
        entries.add(configured(Rarity.RARE, JSONFeatures.COFFEE));
        entries.add(configured(Rarity.VERY_RARE, JSONFeatures.BAMBOO));

        entries.add(configured(Rarity.EXTREMELY_RARE, JSONFeatures.SNAPLEAF));

        return new FeatureSet(entries, FeatureSetRegistry.ABOVE_GROUND);
    }

    public static FeatureSet hanging() {
        List<FeatureSet.Entry> entries = new ArrayList<>();

        entries.add(configured(Rarity.COMMON, JSONFeatures.HANGING_ROOTS));
        entries.add(configured(Rarity.COMMON, JSONFeatures.VINES_CEILING));
        entries.add(configured(Rarity.VERY_UNCOMMON, JSONFeatures.GLOW_LICHEN_CEILING));
        entries.add(configured(Rarity.RARE, JSONFeatures.STRANGLER_TENDRIL_CEILING));
        entries.add(configured(Rarity.VERY_RARE, JSONFeatures.POISON_IVY_CEILING));
        entries.add(configured(Rarity.EXTREMELY_RARE, JSONFeatures.STINKING_BLOSSOM_CEILING));
        entries.add(configured(Rarity.EXTREMELY_RARE, CaveFeatures.SPORE_BLOSSOM));
        entries.add(configured(Rarity.EXTREMELY_RARE, CaveFeatures.CAVE_VINE));
        return new FeatureSet(entries, FeatureSetRegistry.HANGING);
    }

    public static FeatureSet water() {

        List<FeatureSet.Entry> entries = new ArrayList<>();

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
