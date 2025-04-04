/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * If you modify this file, please include a notice stating the changes:
 * Example: "Modified by [Your Name] on [Date] - [Short Description of Changes]"
 */
package com.startraveler.verdant.feature;

import com.startraveler.verdant.Constants;
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
    public static final ResourceKey<ConfiguredFeature<?, ?>> ALOES = createKey("aloes");
    public static final ResourceKey<ConfiguredFeature<?, ?>> BLEEDING_HEART = createKey("bleeding_heart");
    public static final ResourceKey<ConfiguredFeature<?, ?>> COFFEE = createKey("coffee");
    public static final ResourceKey<ConfiguredFeature<?, ?>> MIXED_BUSHES = createKey("mixed_bushes");
    public static final ResourceKey<ConfiguredFeature<?, ?>> TALL_BUSHES = createKey("tall_bushes");
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
    public static final ResourceKey<ConfiguredFeature<?, ?>> WILD_UBE = createKey("wild_ube");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SNAPLEAF = createKey("snapleaf");
    public static final ResourceKey<ConfiguredFeature<?, ?>> HANGING_ROOTS = createKey("hanging_roots");
    public static final ResourceKey<ConfiguredFeature<?, ?>> RUE = createKey("rue");
    public static final ResourceKey<ConfiguredFeature<?, ?>> BLUEWEED = createKey("blueweed");
    public static final ResourceKey<ConfiguredFeature<?, ?>> MOSS_CARPETS = createKey("moss_carpets");
    public static final ResourceKey<ConfiguredFeature<?, ?>> BLASTING_BLOSSOMS = createKey("blasting_blossoms");

    public static ResourceKey<ConfiguredFeature<?, ?>> createKey(String name) {
        return ResourceKey.create(
                Registries.CONFIGURED_FEATURE,
                ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, name)
        );
    }
}

