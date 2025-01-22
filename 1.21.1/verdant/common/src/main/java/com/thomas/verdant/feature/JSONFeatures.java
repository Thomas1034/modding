package com.thomas.verdant.feature;

import com.thomas.verdant.Constants;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

// Features defined in JSON, not data generated.
public class JSONFeatures {

    public static final ResourceKey<ConfiguredFeature<?, ?>> STINKING_BLOSSOM_FLOOR = createKey("stinking_blossom_floor");
    public static final ResourceKey<ConfiguredFeature<?, ?>> STINKING_BLOSSOM_CEILING = createKey(
            "stinking_blossom_ceiling");
    public static final ResourceKey<ConfiguredFeature<?, ?>> STRANGLER_TENDRIL_CEILING = createKey(
            "strangler_tendril_ceiling");
    public static final ResourceKey<ConfiguredFeature<?, ?>> POISON_IVY_CEILING = createKey("poison_ivy_ceiling");
    public static final ResourceKey<ConfiguredFeature<?, ?>> MIXED_FLOWERS = createKey("mixed_flowers");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORCHIDS = createKey("orchids");
    public static final ResourceKey<ConfiguredFeature<?, ?>> LILIES = createKey("lilies");
    public static final ResourceKey<ConfiguredFeature<?, ?>> BLEEDING_HEART = createKey("bleeding_heart");
    public static final ResourceKey<ConfiguredFeature<?, ?>> COFFEE = createKey("coffee");
    public static final ResourceKey<ConfiguredFeature<?, ?>> MIXED_BUSHES = createKey("mixed_bushes");
    public static final ResourceKey<ConfiguredFeature<?, ?>> MIXED_MUSHROOMS = createKey("mixed_mushrooms");
    public static final ResourceKey<ConfiguredFeature<?, ?>> VINES_CEILING = createKey("vines_ceiling");
    public static final ResourceKey<ConfiguredFeature<?, ?>> GLOW_LICHEN_CEILING = createKey("glow_lichen_ceiling");
    public static final ResourceKey<ConfiguredFeature<?, ?>> NO_OP = createKey("no_op");
    public static final ResourceKey<ConfiguredFeature<?, ?>> DROWNED_HEMLOCK = createKey("drowned_hemlock");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FIXED_SEAGRASS_SHORT = createKey("fixed_seagrass_short");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FIXED_SEAGRASS_TALL = createKey("fixed_seagrass_tall");
    public static final ResourceKey<ConfiguredFeature<?, ?>> STRANGLER_VINES = createKey("strangler_vines");
    public static final ResourceKey<ConfiguredFeature<?, ?>> BAMBOO = createKey("bamboo");
    public static final ResourceKey<ConfiguredFeature<?, ?>> WILD_CASSAVA = createKey("wild_cassava");

    public static ResourceKey<ConfiguredFeature<?, ?>> createKey(String name) {
        return ResourceKey.create(
                Registries.CONFIGURED_FEATURE,
                ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, name)
        );
    }
}
