package com.thomas.verdant.util;

import com.thomas.verdant.Constants;
import com.thomas.verdant.registry.WoodSets;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.structure.Structure;

public class VerdantTags {

    public static class MobEffects {

        public static final TagKey<MobEffect> AIRLESS_BREATHING = tag("airless_breathing");

        private static TagKey<MobEffect> tag(String name) {
            return TagKey.create(Registries.MOB_EFFECT, ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, name));
        }

    }

    public static class Blocks {

        public static final TagKey<Block> SUSTAINS_STRANGLER_LEAVES = tag("sustains_strangler_leaves");
        public static final TagKey<Block> STRANGLER_LOGS = WoodSets.STRANGLER.getLogs();
        public static final TagKey<Block> SUPPORTS_STRANGLER_VINES = tag("supports_strangler_vines");
        public static final TagKey<Block> STRANGLER_VINE_REPLACEABLES = tag("strangler_vine_replaceables");
        public static final TagKey<Block> HEARTWOOD_LOGS = WoodSets.HEARTWOOD.getLogs();
        public static final TagKey<Block> STRANGLER_LEAVES = tag("strangler_leaves");
        public static final TagKey<Block> STRANGLER_VINES = tag("strangler_vines");
        public static final TagKey<Block> ROTTEN_WOOD = tag("rotten_wood");
        public static final TagKey<Block> VERDANT_GROUND = tag("verdant_ground");
        public static final TagKey<Block> TENDRILS = tag("tendrils");
        public static final TagKey<Block> ROPE_HOOKS = tag("rope_hooks");
        public static final TagKey<Block> ROPES_EXTEND = tag("ropes_extend");
        public static final TagKey<Block> NEEDS_HEARTWOOD_TOOL = tag("needs_heartwood_tool");
        public static final TagKey<Block> NEEDS_IMBUED_HEARTWOOD_TOOL = tag("needs_imbued_heartwood_tool");
        public static final TagKey<Block> BLOCKS_INTANGIBLE = tag("blocks_intangible");

        private static TagKey<Block> tag(String name) {
            return TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, name));
        }
    }

    public static class Structures {


        public static final TagKey<Structure> CONTAINS_VERDANT = tag("contains_verdant");

        @SuppressWarnings("unused")
        private static TagKey<Structure> tag(String name) {
            return TagKey.create(Registries.STRUCTURE, ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, name));
        }
    }

    public static class Items {
        public static final TagKey<Item> VERDANT_FRIENDLY_ARMORS = tag("verdant_friendly_armors");
        public static final TagKey<Item> STRANGLER_VINES = tag("strangler_vines");
        public static final TagKey<Item> CRAFTS_TO_ROPES = tag("crafts_to_ropes");
        public static final TagKey<Item> VERDANT_GROUND = tag("verdant_ground");

        private static TagKey<Item> tag(String name) {

            return TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, name));
        }
    }

    public static class EntityTypes {

        public static final TagKey<EntityType<?>> VERDANT_FRIENDLY_ENTITIES = tag("verdant_friendly_entities");

        private static TagKey<EntityType<?>> tag(String name) {
            return TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, name));
        }
    }

    public static class Biomes {

        @SuppressWarnings("unused")
        private static TagKey<Biome> tag(String name) {
            return TagKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, name));
        }
    }
}
