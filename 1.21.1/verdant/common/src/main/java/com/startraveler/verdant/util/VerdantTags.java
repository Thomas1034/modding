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
package com.startraveler.verdant.util;

import com.startraveler.verdant.Constants;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.structure.Structure;

import java.util.HashSet;
import java.util.Set;

public class VerdantTags {

    public static final Set<TagKey<?>> TAGS = new HashSet<>();

    public static class DamageSources {
        protected static final Set<String> TAG_NAMES = new HashSet<>();
        public static final TagKey<DamageType> TOXIC_ASH = tag("toxic_ash");

        private static TagKey<DamageType> tag(String name) {
            if (TAG_NAMES.contains(name)) {
                throw new IllegalArgumentException("Duplicate damage type tag: " + name);
            } else {
                TAG_NAMES.add(name);
            }
            TagKey<DamageType> key = TagKey.create(
                    Registries.DAMAGE_TYPE,
                    ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, name)
            );
            TAGS.add(key);
            return key;
        }
    }

    public static class MobEffects {
        protected static final Set<String> TAG_NAMES = new HashSet<>();

        public static final TagKey<MobEffect> UNBREAKABLE = tag("unbreakable");
        public static final TagKey<MobEffect> AIRLESS_BREATHING = tag("airless_breathing");
        public static final TagKey<MobEffect> WEAK_VERDANT_FRIENDLINESS = tag("weak_verdant_friendliness");
        public static final TagKey<MobEffect> STRONG_VERDANT_FRIENDLINESS = tag("strong_verdant_friendliness");
        public static final TagKey<MobEffect> VERDANT_FRIEND = tag("verdant_friend");

        private static TagKey<MobEffect> tag(String name) {
            if (TAG_NAMES.contains(name)) {
                throw new IllegalArgumentException("Duplicate mob effect tag: " + name);
            } else {
                TAG_NAMES.add(name);
            }
            TagKey<MobEffect> key = TagKey.create(
                    Registries.MOB_EFFECT,
                    ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, name)
            );
            TAGS.add(key);
            return key;
        }

    }

    public static class Blocks {
        protected static final Set<String> TAG_NAMES = new HashSet<>();
        public static final TagKey<Block> SAP_FIRE_BASE_BLOCKS = tag("sap_fire_base_blocks");
        public static final TagKey<Block> TERRAIN_ALTERING_FEATURES_AFFECT = tag("terrain_altering_features_affect");
        public static final TagKey<Block> NATURAL_HEARTWOOD_LOGS = tag("natural_heartwood_logs");
        public static final TagKey<Block> SUSTAINS_STRANGLER_LEAVES = tag("sustains_strangler_leaves");
        public static final TagKey<Block> SUPPORTS_STRANGLER_VINES = tag("supports_strangler_vines");
        public static final TagKey<Block> DOES_NOT_SUPPORT_STRANGLER_VINES = tag("does_not_support_strangler_vines");
        public static final TagKey<Block> STRANGLER_VINE_REPLACEABLES = tag("strangler_vine_replaceables");
        public static final TagKey<Block> STRANGLER_LEAVES = tag("strangler_leaves");
        public static final TagKey<Block> STRANGLER_VINES = tag("strangler_vines");
        public static final TagKey<Block> ROTTEN_WOOD = tag("rotten_wood");
        public static final TagKey<Block> VERDANT_GROUND = tag("verdant_ground");
        public static final TagKey<Block> TENDRILS = tag("tendrils");
        public static final TagKey<Block> ROPE_HOOKS = tag("rope_hooks");
        public static final TagKey<Block> ROPES_EXTEND = tag("ropes_extend");
        public static final TagKey<Block> ROPES = tag("ropes");
        public static final TagKey<Block> INCORRECT_FOR_HEARTWOOD_TOOL = tag("incorrect_for_heartwood_tool");
        public static final TagKey<Block> INCORRECT_FOR_THORNY_HEARTWOOD_TOOL = tag(
                "incorrect_for_thorny_heartwood_tool");
        public static final TagKey<Block> INCORRECT_FOR_IMBUED_HEARTWOOD_TOOL = tag(
                "incorrect_for_imbued_heartwood_tool");
        public static final TagKey<Block> BLOCKS_INTANGIBLE = tag("blocks_intangible");
        public static final TagKey<Block> ALLOWS_ASH_SPREAD = tag("allows_ash_spread");
        public static final TagKey<Block> BLOCKS_ASH_SPREAD = tag("blocks_ash_spread");
        public static final TagKey<Block> VERDANT_RESIN_BLOCKS = tag("verdant_resin_blocks");
        public static final TagKey<Block> SUSTAINS_OOZE_FISSURE = tag("sustains_ooze_fissure");
        public static final TagKey<Block> REPLACEABLE_BY_BRAMBLE = tag("replaceable_by_bramble");
        public static final TagKey<Block> MINEABLE_WITH_MACHETE = tag("mineable/machete");

        private static TagKey<Block> tag(String name) {
            if (TAG_NAMES.contains(name)) {
                throw new IllegalArgumentException("Duplicate block tag: " + name);
            } else {
                TAG_NAMES.add(name);
            }
            TagKey<Block> tag = TagKey.create(
                    Registries.BLOCK,
                    ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, name)
            );
            TAGS.add(tag);
            return tag;
        }
    }

    public static class Structures {

        protected static final Set<String> TAG_NAMES = new HashSet<>();

        public static final TagKey<Structure> CONTAINS_VERDANT = tag("contains_verdant");

        @SuppressWarnings("unused")
        private static TagKey<Structure> tag(String name) {
            if (TAG_NAMES.contains(name)) {
                throw new IllegalArgumentException("Duplicate structure tag: " + name);
            } else {
                TAG_NAMES.add(name);
            }
            TagKey<Structure> tag = TagKey.create(
                    Registries.STRUCTURE,
                    ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, name)
            );
            TAGS.add(tag);
            return tag;
        }
    }

    public static class Items {

        protected static final Set<String> TAG_NAMES = new HashSet<>();
        public static final TagKey<Item> EMPTY_FOR_TESTING = tag("empty_for_testing");
        public static final TagKey<Item> ALOES = tag("aloes");
        public static final TagKey<Item> HAS_THORNS = tag("has_thorns");
        public static final TagKey<Item> STARCHES = tag("starches");
        public static final TagKey<Item> VERDANT_FRIENDLY_ARMORS = tag("verdant_friendly_armors");
        public static final TagKey<Item> STRANGLER_VINES = tag("strangler_vines");
        public static final TagKey<Item> CRAFTS_TO_ROPES = tag("crafts_to_ropes");
        public static final TagKey<Item> ROPES = tag("ropes");
        public static final TagKey<Item> NETHER_VINES = tag("nether_vines");
        public static final TagKey<Item> VERDANT_GROUND = tag("verdant_ground");
        public static final TagKey<Item> REPAIRS_HEARTWOOD_ARMOR = tag("repairs_heartwood_armor");
        public static final TagKey<Item> REPAIRS_THORNY_HEARTWOOD_ARMOR = tag("repairs_thorny_heartwood_armor");
        public static final TagKey<Item> REPAIRS_IMBUED_HEARTWOOD_ARMOR = tag("repairs_imbued_heartwood_armor");
        public static final TagKey<Item> HEARTWOOD_TOOL_MATERIALS = tag("heartwood_tool_materials");
        public static final TagKey<Item> THORNY_HEARTWOOD_TOOL_MATERIALS = tag("thorny_heartwood_tool_materials");
        public static final TagKey<Item> IMBUED_HEARTWOOD_TOOL_MATERIALS = tag("imbued_heartwood_tool_materials");
        public static final TagKey<Item> DARTS = tag("darts");
        public static final TagKey<Item> BLASTING_BLOSSOM_BOMBS = tag("blasting_blossom_bombs");
        public static final TagKey<Item> METAL_BOMBS = tag("metal_bombs");
        public static final TagKey<Item> TERRACOTTA_BOMBS = tag("terracotta_bombs");
        public static final TagKey<Item> DART_EFFECT_BINDERS = tag("dart_effect_binders");
        public static final TagKey<Item> MULCH_INGREDIENTS = tag("mulch_ingredients");
        public static final TagKey<Item> VERDANT_SMALL_FLOWERS = tag("verdant_small_flowers");

        private static TagKey<Item> tag(String name) {
            if (TAG_NAMES.contains(name)) {
                throw new IllegalArgumentException("Duplicate item tag: " + name);
            } else {
                TAG_NAMES.add(name);
            }
            TagKey<Item> tag = TagKey.create(
                    Registries.ITEM,
                    ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, name)
            );
            TAGS.add(tag);
            return tag;
        }
    }

    public static class EntityTypes {

        protected static final Set<String> TAG_NAMES = new HashSet<>();
        public static final TagKey<EntityType<?>> VERDANT_FRIENDLY_ENTITIES = tag("verdant_friendly_entities");
        public static final TagKey<EntityType<?>> TOXIC_ASH_DAMAGES = tag("toxic_ash_damages");

        private static TagKey<EntityType<?>> tag(String name) {
            if (TAG_NAMES.contains(name)) {
                throw new IllegalArgumentException("Duplicate entity type tag: " + name);
            } else {
                TAG_NAMES.add(name);
            }
            TagKey<EntityType<?>> tag = TagKey.create(
                    Registries.ENTITY_TYPE,
                    ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, name)
            );
            TAGS.add(tag);
            return tag;
        }
    }

    public static class Biomes {
        protected static final Set<String> TAG_NAMES = new HashSet<>();

        @SuppressWarnings("unused")
        private static TagKey<Biome> tag(String name) {
            if (TAG_NAMES.contains(name)) {
                throw new IllegalArgumentException("Duplicate biome tag: " + name);
            } else {
                TAG_NAMES.add(name);
            }
            TagKey<Biome> tag = TagKey.create(
                    Registries.BIOME,
                    ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, name)
            );
            TAGS.add(tag);
            return tag;
        }
    }
}

