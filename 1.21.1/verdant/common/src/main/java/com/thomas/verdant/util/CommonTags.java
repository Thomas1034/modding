package com.thomas.verdant.util;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class CommonTags {

    public static class Blocks {

        public static final TagKey<Block> ORES_IN_GROUND_DIRT = tag("ores_in_ground/dirt");
        public static final TagKey<Block> ORE_BEARING_GROUND_DIRT = tag("ore_bearing_ground/dirt");

        private static TagKey<Block> tag(String name) {
            return TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath("c", name));
        }
    }
}
