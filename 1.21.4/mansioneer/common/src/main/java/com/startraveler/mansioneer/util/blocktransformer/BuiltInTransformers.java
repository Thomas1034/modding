package com.startraveler.mansioneer.util.blocktransformer;

import com.startraveler.mansioneer.Constants;
import com.startraveler.mansioneer.data.blocktransformer.BlockTransformerData;
import com.startraveler.mansioneer.data.blocktransformer.BlockTransformerResultOption;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

import java.util.Map;

public class BuiltInTransformers {

    public static final ResourceLocation MANSION_TO_SAVANNA = name("mansion_to_savanna");

    public static final ResourceLocation MANSION_TO_PALE_GARDEN = name("mansion_to_pale_garden");

    public static final ResourceLocation MANSION_TO_SWAMP = name("mansion_to_swamp");

    public static final ResourceLocation MANSION_TO_TAIGA = name("mansion_to_taiga");

    public static final ResourceLocation MANSION_TO_JUNGLE = name("mansion_to_jungle");

    public static final ResourceLocation MANSION_TO_DESERT = name("mansion_to_desert");

    public static final ResourceLocation MANSION_TO_BIRCH = name("mansion_to_birch");

    public static final ResourceLocation MANSION_TO_MOUNTAIN = name("mansion_to_mountain");

    public static final ResourceLocation MANSION_TO_CHERRY = name("mansion_to_cherry");

    public static final ResourceLocation MANSION_TO_ICE = name("mansion_to_ice");

    public static final ResourceLocation MANSION_TO_FOREST = name("mansion_to_forest");

    public static final ResourceLocation MANSION_TO_PLAINS = name("mansion_to_plains");

    public static final ResourceLocation EMPTY = name("empty");

    public static void init() {
    }

    public static ResourceLocation name(String name) {
        return ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, name);
    }

    public static ResourceLocation name(Block block) {
        return BuiltInRegistries.BLOCK.getKey(block);
    }

    public static BlockTransformerData direct(Block from, Block to) {
        return new BlockTransformerData(null, name(to), null, null, name(from));
    }

    public static BlockTransformerData probability(Block from, Map<Block, Integer> to) {
        return new BlockTransformerData(
                null,
                null,
                to.entrySet()
                        .stream()
                        .map(entry -> new BlockTransformerResultOption(name(entry.getKey()), entry.getValue()))
                        .toList(),
                null,
                name(from)
        );
    }

    public static BlockTransformerData probabilityTag(TagKey<Block> from, Map<Block, Integer> to) {
        return new BlockTransformerData(
                null,
                null,
                to.entrySet()
                        .stream()
                        .map(entry -> new BlockTransformerResultOption(name(entry.getKey()), entry.getValue()))
                        .toList(),
                from,
                null
        );
    }

    public static BlockTransformerData directTag(TagKey<Block> from, Block to) {
        return new BlockTransformerData(null, name(to), null, from, null);
    }

    public static BlockTransformerData transformer(String transformer) {
        return new BlockTransformerData(name(transformer), null, null, null, null);
    }

    public static BlockTransformerData transformer(ResourceLocation transformer) {
        return new BlockTransformerData(transformer, null, null, null, null);
    }
}
