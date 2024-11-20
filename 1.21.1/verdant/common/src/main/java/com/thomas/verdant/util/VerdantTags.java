package com.thomas.verdant.util;

import com.thomas.verdant.Constants;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.structure.Structure;

public class VerdantTags {
    public static class Blocks {

        public static final TagKey<Block> VERDANT_LOGS = tag("verdant_logs");
        public static final TagKey<Block> VERDANT_VINE_REPLACEABLES = tag("verdant_vine_replacables");
        public static final TagKey<Block> MATURE_VERDANT_LOGS = tag("mature_verdant_logs");
        public static final TagKey<Block> VERDANT_LEAFY_BLOCKS = tag("verdant_leafy_blocks");
        public static final TagKey<Block> VERDANT_VINES = tag("verdant_vines");
        public static final TagKey<Block> VERDANT_GROUND = tag("verdant_ground");
        public static final TagKey<Block> NEEDS_VERDANT_HEARTWOOD_TOOL = tag("needs_verdant_heartwood_tool");

        private static TagKey<Block> tag(String name) {

            return TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, name));
        }
    }

    public static class Structures {

        @SuppressWarnings("unused")
        private static TagKey<Structure> tag(String name) {
            TagKey<Structure> tag = TagKey.create(Registries.STRUCTURE, ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, name));
            return tag;
        }
    }

    public static class Items {
        public static final TagKey<Item> VERDANT_FRIENDLY_ARMORS = tag("verdant_friendly_armors");

        private static TagKey<Item> tag(String name) {

            return TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, name));
        }
    }

    public static class EntityTypes {

        public static final TagKey<EntityType<?>> VERDANT_ENTITIES = tag("verdant_entities");

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
