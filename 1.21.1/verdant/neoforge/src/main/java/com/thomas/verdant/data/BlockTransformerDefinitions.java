package com.thomas.verdant.data;

import com.thomas.verdant.Constants;
import com.thomas.verdant.data.blocktransformer.BlockTransformerData;
import com.thomas.verdant.registry.BlockRegistry;
import com.thomas.verdant.util.blocktransformer.BlockTransformer;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.ArrayList;
import java.util.List;

public class BlockTransformerDefinitions {

    public static BlockTransformer erosion() {

        List<BlockTransformerData> data = new ArrayList<>();

        data.add(direct(Blocks.STONE, Blocks.COBBLESTONE));
        data.add(direct(Blocks.STONE_SLAB, Blocks.COBBLESTONE_SLAB));
        data.add(direct(Blocks.STONE_STAIRS, Blocks.COBBLESTONE_STAIRS));
        data.add(direct(Blocks.INFESTED_STONE, Blocks.AIR));
        data.add(direct(Blocks.COBBLESTONE, BlockRegistry.DENSE_GRAVEL.get()));
        data.add(direct(Blocks.MOSSY_COBBLESTONE, BlockRegistry.DENSE_GRAVEL.get()));
        data.add(direct(Blocks.DEEPSLATE, Blocks.COBBLED_DEEPSLATE));
        data.add(direct(Blocks.COBBLED_DEEPSLATE, Blocks.COBBLESTONE));
        data.add(direct(BlockRegistry.DENSE_GRAVEL.get(), Blocks.COARSE_DIRT));
        data.add(direct(Blocks.GRAVEL, Blocks.COARSE_DIRT));
        data.add(direct(Blocks.COARSE_DIRT, Blocks.DIRT));
        data.add(direct(Blocks.DIRT_PATH, Blocks.DIRT));
        data.add(direct(Blocks.MYCELIUM, Blocks.DIRT));
        data.add(direct(Blocks.MOSS_BLOCK, Blocks.DIRT));
        data.add(direct(Blocks.GRASS_BLOCK, Blocks.DIRT));
        // Cobblestone partial blocks.
        data.add(direct(Blocks.COBBLESTONE_STAIRS, Blocks.GRAVEL));
        data.add(direct(Blocks.COBBLESTONE_SLAB, Blocks.GRAVEL));
        data.add(direct(Blocks.COBBLESTONE_WALL, Blocks.GRAVEL));
        data.add(direct(Blocks.INFESTED_COBBLESTONE, Blocks.AIR));
        data.add(direct(Blocks.MOSSY_COBBLESTONE_STAIRS, Blocks.GRAVEL));
        data.add(direct(Blocks.MOSSY_COBBLESTONE_SLAB, Blocks.GRAVEL));
        data.add(direct(Blocks.MOSSY_COBBLESTONE_WALL, Blocks.GRAVEL));
        // Stone bricks (smooth stone is now immune)
        data.add(direct(Blocks.STONE_BRICKS, Blocks.CRACKED_STONE_BRICKS));
        data.add(direct(Blocks.STONE_BRICK_STAIRS, Blocks.COBBLESTONE_STAIRS));
        data.add(direct(Blocks.STONE_BRICK_SLAB, Blocks.COBBLESTONE_SLAB));
        data.add(direct(Blocks.STONE_BRICK_WALL, Blocks.COBBLESTONE_WALL));
        data.add(direct(Blocks.CHISELED_STONE_BRICKS, Blocks.COBBLESTONE));
        data.add(direct(Blocks.CRACKED_STONE_BRICKS, Blocks.COBBLESTONE));
        data.add(direct(Blocks.MOSSY_STONE_BRICKS, Blocks.CRACKED_STONE_BRICKS));
        data.add(direct(Blocks.MOSSY_STONE_BRICK_STAIRS, Blocks.COBBLESTONE_STAIRS));
        data.add(direct(Blocks.MOSSY_STONE_BRICK_SLAB, Blocks.COBBLESTONE_SLAB));
        data.add(direct(Blocks.MOSSY_STONE_BRICK_WALL, Blocks.COBBLESTONE_WALL));
        // Sandstones.
        data.add(direct(Blocks.SMOOTH_SANDSTONE, Blocks.SANDSTONE));
        data.add(direct(Blocks.SMOOTH_SANDSTONE_STAIRS, Blocks.SANDSTONE_STAIRS));
        data.add(direct(Blocks.SMOOTH_SANDSTONE_SLAB, Blocks.SANDSTONE_SLAB));
        data.add(direct(Blocks.CHISELED_SANDSTONE, Blocks.SANDSTONE));
        data.add(direct(Blocks.CUT_SANDSTONE, Blocks.SANDSTONE));
        data.add(direct(Blocks.CUT_SANDSTONE_SLAB, Blocks.SANDSTONE_SLAB));
        data.add(direct(Blocks.SANDSTONE, Blocks.SAND));
        data.add(direct(Blocks.SANDSTONE_STAIRS, Blocks.SAND));
        data.add(direct(Blocks.SANDSTONE_SLAB, Blocks.SAND));
        data.add(direct(Blocks.SAND, Blocks.COARSE_DIRT));
        // Mud
        data.add(direct(Blocks.PACKED_MUD, Blocks.COARSE_DIRT));
        data.add(direct(Blocks.MUD_BRICKS, Blocks.PACKED_MUD));
        data.add(direct(Blocks.MUD_BRICK_SLAB, Blocks.COARSE_DIRT));
        data.add(direct(Blocks.MUD_BRICK_STAIRS, Blocks.COARSE_DIRT));
        data.add(direct(Blocks.MUD_BRICK_WALL, Blocks.COARSE_DIRT));
        // Deepslate ores to stone ores.
        data.add(direct(Blocks.DEEPSLATE_COAL_ORE, Blocks.COAL_ORE));
        data.add(direct(Blocks.DEEPSLATE_COPPER_ORE, Blocks.COPPER_ORE));
        data.add(direct(Blocks.DEEPSLATE_IRON_ORE, Blocks.IRON_ORE));
        data.add(direct(Blocks.DEEPSLATE_GOLD_ORE, Blocks.GOLD_ORE));
        data.add(direct(Blocks.DEEPSLATE_REDSTONE_ORE, Blocks.REDSTONE_ORE));
        data.add(direct(Blocks.DEEPSLATE_LAPIS_ORE, Blocks.LAPIS_ORE));
        data.add(direct(Blocks.DEEPSLATE_EMERALD_ORE, Blocks.EMERALD_ORE));
        data.add(direct(Blocks.DEEPSLATE_DIAMOND_ORE, Blocks.DIAMOND_ORE));
        // Stone ores to dirt ores
        data.add(direct(Blocks.COAL_ORE, BlockRegistry.DIRT_COAL_ORE.get()));
        data.add(direct(Blocks.COPPER_ORE, BlockRegistry.DIRT_COPPER_ORE.get()));
        data.add(direct(Blocks.IRON_ORE, BlockRegistry.DIRT_IRON_ORE.get()));
        data.add(direct(Blocks.GOLD_ORE, BlockRegistry.DIRT_GOLD_ORE.get()));
        data.add(direct(Blocks.REDSTONE_ORE, BlockRegistry.DIRT_REDSTONE_ORE.get()));
        data.add(direct(Blocks.LAPIS_ORE, BlockRegistry.DIRT_LAPIS_ORE.get()));
        data.add(direct(Blocks.EMERALD_ORE, BlockRegistry.DIRT_EMERALD_ORE.get()));
        data.add(direct(Blocks.DIAMOND_ORE, BlockRegistry.DIRT_DIAMOND_ORE.get()));

        return new BlockTransformer(data);
    }

    public static BlockTransformer erosionWet() {

        List<BlockTransformerData> data = new ArrayList<>();

        data.add(transformer("erosion"));

        return new BlockTransformer(data);
    }

    public static BlockTransformer verdantRoots() {

        List<BlockTransformerData> data = new ArrayList<>();

        data.add(direct(Blocks.DIRT, BlockRegistry.VERDANT_ROOTED_DIRT.get()));
        data.add(direct(Blocks.PODZOL, BlockRegistry.VERDANT_GRASS_DIRT.get()));
        data.add(direct(Blocks.MUD, BlockRegistry.VERDANT_ROOTED_MUD.get()));
        data.add(direct(Blocks.CLAY, BlockRegistry.VERDANT_ROOTED_CLAY.get()));

        return new BlockTransformer(data);
    }

    public static BlockTransformer hoeing() {

        List<BlockTransformerData> data = new ArrayList<>();

        data.add(direct(BlockRegistry.VERDANT_ROOTED_DIRT.get(), Blocks.DIRT));
        data.add(direct(BlockRegistry.VERDANT_GRASS_DIRT.get(), BlockRegistry.VERDANT_ROOTED_DIRT.get()));
        data.add(direct(BlockRegistry.VERDANT_ROOTED_MUD.get(), Blocks.MUD));
        data.add(direct(BlockRegistry.VERDANT_GRASS_MUD.get(), BlockRegistry.VERDANT_ROOTED_MUD.get()));
        data.add(direct(BlockRegistry.VERDANT_ROOTED_CLAY.get(), Blocks.CLAY));
        data.add(direct(BlockRegistry.VERDANT_GRASS_CLAY.get(), BlockRegistry.VERDANT_ROOTED_CLAY.get()));

        return new BlockTransformer(data);
    }

    private static ResourceLocation name(Block block) {
        return BuiltInRegistries.BLOCK.getKey(block);
    }

    private static BlockTransformerData direct(Block from, Block to) {
        return new BlockTransformerData(null, name(to), null, null, name(from));
    }

    private static BlockTransformerData tag(TagKey<Block> from, Block to) {
        return new BlockTransformerData(null, name(to), null, from, null);
    }

    private static BlockTransformerData transformer(String transformer) {
        return new BlockTransformerData(ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, transformer), null, null, null, null);
    }
}
