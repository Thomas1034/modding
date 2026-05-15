package com.startraveler.verdant.data.definitions;

import com.startraveler.rootbound.featureset.FeatureSet;
import com.startraveler.rootbound.featureset.entry.ConfiguredFeatureEntry;
import com.startraveler.verdant.feature.JSONFeatures;
import com.startraveler.verdant.registry.FeatureSetRegistry;
import com.startraveler.verdant.util.Rarity;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.features.CaveFeatures;
import net.minecraft.data.worldgen.features.VegetationFeatures;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

import java.util.ArrayList;
import java.util.List;

public class FeatureSetDefinitions {

    public static FeatureSet aboveGround(BootstrapContext<FeatureSet> bootstrap) {

        List<FeatureSet.Entry> entries = new ArrayList<>();
        HolderGetter<ConfiguredFeature<?, ?>> registry = bootstrap.lookup(Registries.CONFIGURED_FEATURE);

        entries.add(configured(registry, Rarity.EXTREMELY_COMMON, VegetationFeatures.GRASS_JUNGLE));
        entries.add(configured(registry, Rarity.EXTREMELY_COMMON, VegetationFeatures.TALL_GRASS));
        entries.add(configured(registry, Rarity.EXTREMELY_COMMON, VegetationFeatures.BUSH));
        entries.add(configured(registry, Rarity.EXTREMELY_COMMON, VegetationFeatures.GRASS));
        entries.add(configured(registry, Rarity.EXTREMELY_COMMON, VegetationFeatures.LARGE_FERN));
        entries.add(configured(registry, Rarity.COMMON, VegetationFeatures.GRASS));
        entries.add(configured(registry, Rarity.UNCOMMON, JSONFeatures.TALL_BUSHES));
        entries.add(configured(registry, Rarity.UNCOMMON, JSONFeatures.MIXED_BUSHES));
        entries.add(configured(registry, Rarity.UNCOMMON, JSONFeatures.STINKING_BLOSSOM_FLOOR));
        entries.add(configured(registry, Rarity.UNCOMMON, VegetationFeatures.VINES));
        entries.add(configured(registry, Rarity.VERY_UNCOMMON, JSONFeatures.RUE));
        entries.add(configured(registry, Rarity.VERY_UNCOMMON, JSONFeatures.WILD_CASSAVA));
        entries.add(configured(registry, Rarity.VERY_UNCOMMON, JSONFeatures.WILD_UBE));
        entries.add(configured(registry, Rarity.EXTREMELY_UNCOMMON, JSONFeatures.MOSS_CARPET_PATCH));
        entries.add(configured(registry, Rarity.EXTREMELY_UNCOMMON, JSONFeatures.BLEEDING_HEART));
        entries.add(configured(registry, Rarity.EXTREMELY_UNCOMMON, JSONFeatures.MIXED_MUSHROOMS));

        entries.add(configured(registry, Rarity.RARE, JSONFeatures.MIXED_FLOWERS));
        entries.add(configured(registry, Rarity.RARE, JSONFeatures.COFFEE));
        entries.add(configured(registry, Rarity.RARE, JSONFeatures.BAMBOO));
        entries.add(configured(registry, Rarity.RARE, VegetationFeatures.FIREFLY_BUSH));

        entries.add(configured(registry, Rarity.VERY_RARE, JSONFeatures.LILIES));
        entries.add(configured(registry, Rarity.VERY_RARE, JSONFeatures.ORCHIDS));
        entries.add(configured(registry, Rarity.VERY_RARE, JSONFeatures.BLUEWEED));
        entries.add(configured(registry, Rarity.VERY_RARE, JSONFeatures.ALOES));
        entries.add(configured(registry, Rarity.VERY_RARE, VegetationFeatures.DRY_GRASS));
        entries.add(configured(registry, Rarity.VERY_RARE, JSONFeatures.MANGO_SAPLING));

        entries.add(configured(registry, Rarity.EXTREMELY_RARE, JSONFeatures.BLASTING_BLOSSOMS));
        entries.add(configured(registry, Rarity.EXTREMELY_RARE, JSONFeatures.SNAPLEAF));
        entries.add(configured(registry, Rarity.EXTREMELY_RARE, VegetationFeatures.BERRY_BUSH));
        entries.add(configured(registry, Rarity.EXTREMELY_RARE, JSONFeatures.OOZING_HEART_POOL));

        return new FeatureSet(entries, FeatureSetRegistry.ABOVE_GROUND);
    }

    public static FeatureSet hanging(BootstrapContext<FeatureSet> bootstrap) {
        List<FeatureSet.Entry> entries = new ArrayList<>();
        HolderGetter<ConfiguredFeature<?, ?>> registry = bootstrap.lookup(Registries.CONFIGURED_FEATURE);

        entries.add(configured(registry, Rarity.COMMON, JSONFeatures.HANGING_ROOTS));
        entries.add(configured(registry, Rarity.COMMON, JSONFeatures.VINES_CEILING));
        entries.add(configured(registry, Rarity.VERY_UNCOMMON, JSONFeatures.GLOW_LICHEN_CEILING));
        entries.add(configured(registry, Rarity.RARE, JSONFeatures.STRANGLER_TENDRIL_CEILING));
        entries.add(configured(registry, Rarity.VERY_RARE, JSONFeatures.POISON_IVY_CEILING));
        entries.add(configured(registry, Rarity.EXTREMELY_RARE, JSONFeatures.STINKING_BLOSSOM_CEILING));
        entries.add(configured(registry, Rarity.EXTREMELY_RARE, CaveFeatures.SPORE_BLOSSOM));
        entries.add(configured(registry, Rarity.EXTREMELY_RARE, CaveFeatures.CAVE_VINE));
        return new FeatureSet(entries, FeatureSetRegistry.HANGING);
    }

    public static FeatureSet water(BootstrapContext<FeatureSet> bootstrap) {

        List<FeatureSet.Entry> entries = new ArrayList<>();
        HolderGetter<ConfiguredFeature<?, ?>> registry = bootstrap.lookup(Registries.CONFIGURED_FEATURE);

        entries.add(configured(registry, Rarity.VERY_COMMON, JSONFeatures.FIXED_SEAGRASS_TALL));
        entries.add(configured(registry, Rarity.COMMON, JSONFeatures.FIXED_SEAGRASS_SHORT));
        entries.add(configured(registry, Rarity.RARE, VegetationFeatures.WATERLILY));
        entries.add(configured(registry, Rarity.EXTREMELY_RARE, JSONFeatures.DROWNED_HEMLOCK));

        return new FeatureSet(entries, FeatureSetRegistry.WATER);
    }

    public static FeatureSet always(BootstrapContext<FeatureSet> bootstrap) {

        List<FeatureSet.Entry> entries = new ArrayList<>();
        HolderGetter<ConfiguredFeature<?, ?>> registry = bootstrap.lookup(Registries.CONFIGURED_FEATURE);

        entries.add(configured(registry, 5, JSONFeatures.NO_OP));

        return new FeatureSet(entries, FeatureSetRegistry.ALWAYS);
    }

    public static FeatureSet belowLog(BootstrapContext<FeatureSet> bootstrap) {

        List<FeatureSet.Entry> entries = new ArrayList<>();
        HolderGetter<ConfiguredFeature<?, ?>> registry = bootstrap.lookup(Registries.CONFIGURED_FEATURE);

        entries.add(configured(registry, 1, JSONFeatures.STRANGLER_VINES));

        return new FeatureSet(entries, FeatureSetRegistry.BELOW_LOG);
    }

    public static FeatureSet mulch(BootstrapContext<FeatureSet> bootstrap) {
        List<FeatureSet.Entry> entries = new ArrayList<>();
        HolderGetter<ConfiguredFeature<?, ?>> registry = bootstrap.lookup(Registries.CONFIGURED_FEATURE);

        entries.add(configured(registry, 63, JSONFeatures.MULCH));
        entries.add(configured(registry, 1, JSONFeatures.LARGE_MULCH));

        return new FeatureSet(entries, FeatureSetRegistry.MULCH);
    }

    public static FeatureSet largeMulch(BootstrapContext<FeatureSet> bootstrap) {
        List<FeatureSet.Entry> entries = new ArrayList<>();
        HolderGetter<ConfiguredFeature<?, ?>> registry = bootstrap.lookup(Registries.CONFIGURED_FEATURE);
        entries.add(configured(registry, 63, JSONFeatures.LARGE_MULCH));
        entries.add(configured(registry, 1, JSONFeatures.MULCH));

        return new FeatureSet(entries, FeatureSetRegistry.LARGE_MULCH);
    }

    public static ConfiguredFeatureEntry configured(int weight, Holder<ConfiguredFeature<?, ?>> feature) {
        return new ConfiguredFeatureEntry(feature, weight);
    }

    public static ConfiguredFeatureEntry configured(HolderGetter<ConfiguredFeature<?, ?>> registry, int weight, ResourceKey<ConfiguredFeature<?, ?>> feature) {
        return configured(weight, registry.getOrThrow(feature));
    }
}
