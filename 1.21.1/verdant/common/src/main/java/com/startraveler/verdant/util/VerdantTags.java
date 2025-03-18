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
import com.startraveler.verdant.registry.WoodSets;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
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

    public static class MobEffects {

        public static final TagKey<MobEffect> UNBREAKABLE = tag("unbreakable");
        public static final TagKey<MobEffect> AIRLESS_BREATHING = tag("airless_breathing");
        public static final TagKey<MobEffect> WEAK_VERDANT_FRIENDLINESS = tag("weak_verdant_friendliness");
        public static final TagKey<MobEffect> STRONG_VERDANT_FRIENDLINESS = tag("strong_verdant_friendliness");
        public static final TagKey<MobEffect> VERDANT_FRIEND = tag("verdant_friend");

        private static TagKey<MobEffect> tag(String name) {
            return TagKey.create(Registries.MOB_EFFECT, ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, name));
        }

    }

    public static class Blocks {

        public static final TagKey<Block> SUSTAINS_STRANGLER_LEAVES = tag("sustains_strangler_leaves");
        public static final TagKey<Block> STRANGLER_LOGS = WoodSets.STRANGLER.getLogs();
        public static final TagKey<Block> SUPPORTS_STRANGLER_VINES = tag("supports_strangler_vines");
        public static final TagKey<Block> DOES_NOT_SUPPORT_STRANGLER_VINES = tag("does_not_support_strangler_vines");
        public static final TagKey<Block> STRANGLER_VINE_REPLACEABLES = tag("strangler_vine_replaceables");
        public static final TagKey<Block> HEARTWOOD_LOGS = WoodSets.HEARTWOOD.getLogs();
        public static final TagKey<Block> STRANGLER_LEAVES = tag("strangler_leaves");
        public static final TagKey<Block> STRANGLER_VINES = tag("strangler_vines");
        public static final TagKey<Block> ROTTEN_WOOD = tag("rotten_wood");
        public static final TagKey<Block> VERDANT_GROUND = tag("verdant_ground");
        public static final TagKey<Block> TENDRILS = tag("tendrils");
        public static final TagKey<Block> ROPE_HOOKS = tag("rope_hooks");
        public static final TagKey<Block> ROPES_EXTEND = tag("ropes_extend");
        public static final TagKey<Block> INCORRECT_FOR_HEARTWOOD_TOOL = tag("incorrect_for_heartwood_tool");
        public static final TagKey<Block> INCORRECT_FOR_IMBUED_HEARTWOOD_TOOL = tag(
                "incorrect_for_imbued_heartwood_tool");
        public static final TagKey<Block> BLOCKS_INTANGIBLE = tag("blocks_intangible");
        public static final TagKey<Block> ALLOWS_ASH_SPREAD = tag("allows_ash_spread");
        public static final TagKey<Block> BLOCKS_ASH_SPREAD = tag("allows_ash_spread");

        private static TagKey<Block> tag(String name) {
            TagKey<Block> tag = TagKey.create(
                    Registries.BLOCK,
                    ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, name)
            );
            TAGS.add(tag);
            return tag;
        }
    }

    public static class Structures {


        public static final TagKey<Structure> CONTAINS_VERDANT = tag("contains_verdant");

        @SuppressWarnings("unused")
        private static TagKey<Structure> tag(String name) {
            TagKey<Structure> tag = TagKey.create(
                    Registries.STRUCTURE,
                    ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, name)
            );
            TAGS.add(tag);
            return tag;
        }
    }

    public static class Items {
        public static final TagKey<Item> ALOES = tag("aloes");
        public static final TagKey<Item> STARCHES = tag("starches");
        public static final TagKey<Item> VERDANT_FRIENDLY_ARMORS = tag("verdant_friendly_armors");
        public static final TagKey<Item> STRANGLER_VINES = tag("strangler_vines");
        public static final TagKey<Item> CRAFTS_TO_ROPES = tag("crafts_to_ropes");
        public static final TagKey<Item> VERDANT_GROUND = tag("verdant_ground");
        public static final TagKey<Item> REPAIRS_HEARTWOOD_ARMOR = tag("repairs_heartwood_armor");
        public static final TagKey<Item> REPAIRS_IMBUED_HEARTWOOD_ARMOR = tag("repairs_heartwood_armor");
        public static final TagKey<Item> HEARTWOOD_TOOL_MATERIALS = tag("heartwood_tool_materials");
        public static final TagKey<Item> IMBUED_HEARTWOOD_TOOL_MATERIALS = tag("imbued_heartwood_tool_materials");
        public static final TagKey<Item> DARTS = tag("darts");
        public static final TagKey<Item> BLASTING_BLOSSOM_BOMBS = tag("blasting_blossom_bombs");

        private static TagKey<Item> tag(String name) {
            TagKey<Item> tag = TagKey.create(
                    Registries.ITEM,
                    ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, name)
            );
            TAGS.add(tag);
            return tag;
        }
    }

    public static class EntityTypes {

        public static final TagKey<EntityType<?>> VERDANT_FRIENDLY_ENTITIES = tag("verdant_friendly_entities");
        public static final TagKey<EntityType<?>> TOXIC_ASH_DAMAGES = tag("toxic_ash_damages");

        private static TagKey<EntityType<?>> tag(String name) {
            TagKey<EntityType<?>> tag = TagKey.create(
                    Registries.ENTITY_TYPE,
                    ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, name)
            );
            TAGS.add(tag);
            return tag;
        }
    }

    public static class Biomes {

        @SuppressWarnings("unused")
        private static TagKey<Biome> tag(String name) {
            TagKey<Biome> tag = TagKey.create(
                    Registries.BIOME,
                    ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, name)
            );
            TAGS.add(tag);
            return tag;
        }
    }
}

