package com.startraveler.verdant.data.definitions;

import com.startraveler.rootbound.blocktransformer.BlockTransformer;
import com.startraveler.rootbound.blocktransformer.data.BlockTransformerData;
import com.startraveler.rootbound.blocktransformer.data.BlockTransformerResultOption;
import com.startraveler.verdant.Constants;
import com.startraveler.verdant.registry.BlockRegistry;
import com.startraveler.verdant.registry.BlockTransformerRegistry;
import com.startraveler.verdant.registry.WoodSets;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.Tags;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BlockTransformerDefinitions {

    public static BlockTransformer erosion() {

        List<BlockTransformerData> data = new ArrayList<>();

        data.add(direct(Blocks.STONE, Blocks.COBBLESTONE));
        data.add(direct(Blocks.STONE_SLAB, Blocks.COBBLESTONE_SLAB));
        data.add(direct(Blocks.STONE_STAIRS, Blocks.COBBLESTONE_STAIRS));
        data.add(direct(Blocks.INFESTED_STONE, Blocks.AIR));
        data.add(direct(Blocks.COBBLESTONE, BlockRegistry.PACKED_GRAVEL.get()));
        data.add(direct(Blocks.MOSSY_COBBLESTONE, BlockRegistry.PACKED_GRAVEL.get()));
        data.add(direct(Blocks.DEEPSLATE, Blocks.COBBLED_DEEPSLATE));
        data.add(direct(Blocks.COBBLED_DEEPSLATE, BlockRegistry.PACKED_SCREE.get()));
        data.add(direct(BlockRegistry.PACKED_GRAVEL.get(), Blocks.COARSE_DIRT));
        data.add(direct(BlockRegistry.PACKED_SCREE.get(), BlockRegistry.STONY_GRUS.get()));
        data.add(direct(BlockRegistry.SCREE.get(), BlockRegistry.STONY_GRUS.get()));
        data.add(direct(BlockRegistry.FUSED_GRAVEL.get(), BlockRegistry.PACKED_GRAVEL.get()));
        data.add(direct(BlockRegistry.FUSED_SCREE.get(), BlockRegistry.PACKED_SCREE.get()));
        data.add(direct(Blocks.GRAVEL, Blocks.COARSE_DIRT));
        data.add(direct(BlockRegistry.STONY_GRUS.get(), BlockRegistry.GRUS.get()));
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
        data.add(direct(Blocks.STONE_BRICKS, Blocks.MOSSY_STONE_BRICKS));
        data.add(direct(Blocks.STONE_BRICK_STAIRS, Blocks.COBBLESTONE_STAIRS));
        data.add(direct(Blocks.STONE_BRICK_SLAB, Blocks.COBBLESTONE_SLAB));
        data.add(direct(Blocks.STONE_BRICK_WALL, Blocks.COBBLESTONE_WALL));
        data.add(direct(Blocks.CHISELED_STONE_BRICKS, Blocks.COBBLESTONE));
        data.add(direct(Blocks.CRACKED_STONE_BRICKS, Blocks.COBBLESTONE));
        data.add(direct(Blocks.MOSSY_STONE_BRICKS, Blocks.CRACKED_STONE_BRICKS));
        data.add(direct(Blocks.MOSSY_STONE_BRICK_STAIRS, Blocks.COBBLESTONE_STAIRS));
        data.add(direct(Blocks.MOSSY_STONE_BRICK_SLAB, Blocks.COBBLESTONE_SLAB));
        data.add(direct(Blocks.MOSSY_STONE_BRICK_WALL, Blocks.COBBLESTONE_WALL));
        // Deepslate partial blocks.
        data.add(direct(Blocks.COBBLED_DEEPSLATE_STAIRS, BlockRegistry.SCREE.get()));
        data.add(direct(Blocks.COBBLED_DEEPSLATE_SLAB, BlockRegistry.SCREE.get()));
        data.add(direct(Blocks.COBBLED_DEEPSLATE_WALL, BlockRegistry.SCREE.get()));
        data.add(direct(Blocks.DEEPSLATE_BRICKS, Blocks.COBBLED_DEEPSLATE));
        data.add(direct(Blocks.DEEPSLATE_BRICK_SLAB, Blocks.COBBLED_DEEPSLATE_SLAB));
        data.add(direct(Blocks.DEEPSLATE_BRICK_STAIRS, Blocks.COBBLED_DEEPSLATE_STAIRS));
        data.add(direct(Blocks.DEEPSLATE_BRICK_WALL, Blocks.COBBLED_DEEPSLATE_WALL));
        data.add(direct(Blocks.DEEPSLATE_BRICKS, Blocks.COBBLED_DEEPSLATE));
        data.add(direct(Blocks.DEEPSLATE_TILES, Blocks.COBBLED_DEEPSLATE));
        data.add(direct(Blocks.DEEPSLATE_TILE_SLAB, Blocks.COBBLED_DEEPSLATE_SLAB));
        data.add(direct(Blocks.DEEPSLATE_TILE_STAIRS, Blocks.COBBLED_DEEPSLATE_STAIRS));
        data.add(direct(Blocks.DEEPSLATE_TILE_WALL, Blocks.COBBLED_DEEPSLATE_WALL));
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
        data.add(direct(Blocks.SANDSTONE_WALL, Blocks.SAND));
        data.add(direct(Blocks.SAND, Blocks.COARSE_DIRT));
        // Mud
        data.add(direct(Blocks.PACKED_MUD, Blocks.COARSE_DIRT));
        data.add(direct(Blocks.MUD_BRICKS, Blocks.PACKED_MUD));
        data.add(direct(Blocks.MUD_BRICK_SLAB, Blocks.COARSE_DIRT));
        data.add(direct(Blocks.MUD_BRICK_STAIRS, Blocks.COARSE_DIRT));
        data.add(direct(Blocks.MUD_BRICK_WALL, Blocks.COARSE_DIRT));
        // Deepslate ores to grus ores.
        data.add(direct(Blocks.DEEPSLATE_COAL_ORE, BlockRegistry.GRUS_COAL_ORE.get()));
        data.add(direct(Blocks.DEEPSLATE_COPPER_ORE, BlockRegistry.GRUS_COPPER_ORE.get()));
        data.add(direct(Blocks.DEEPSLATE_IRON_ORE, BlockRegistry.GRUS_IRON_ORE.get()));
        data.add(direct(Blocks.DEEPSLATE_GOLD_ORE, BlockRegistry.GRUS_GOLD_ORE.get()));
        data.add(direct(Blocks.DEEPSLATE_REDSTONE_ORE, BlockRegistry.GRUS_REDSTONE_ORE.get()));
        data.add(direct(Blocks.DEEPSLATE_LAPIS_ORE, BlockRegistry.GRUS_LAPIS_ORE.get()));
        data.add(direct(Blocks.DEEPSLATE_EMERALD_ORE, BlockRegistry.GRUS_EMERALD_ORE.get()));
        data.add(direct(Blocks.DEEPSLATE_DIAMOND_ORE, BlockRegistry.GRUS_EMERALD_ORE.get()));
        // Stone ores to dirt ores
        data.add(direct(Blocks.COAL_ORE, BlockRegistry.DIRT_COAL_ORE.get()));
        data.add(direct(Blocks.COPPER_ORE, BlockRegistry.DIRT_COPPER_ORE.get()));
        data.add(direct(Blocks.IRON_ORE, BlockRegistry.DIRT_IRON_ORE.get()));
        data.add(direct(Blocks.GOLD_ORE, BlockRegistry.DIRT_GOLD_ORE.get()));
        data.add(direct(Blocks.REDSTONE_ORE, BlockRegistry.DIRT_REDSTONE_ORE.get()));
        data.add(direct(Blocks.LAPIS_ORE, BlockRegistry.DIRT_LAPIS_ORE.get()));
        data.add(direct(Blocks.EMERALD_ORE, BlockRegistry.DIRT_EMERALD_ORE.get()));
        data.add(direct(Blocks.DIAMOND_ORE, BlockRegistry.DIRT_DIAMOND_ORE.get()));
        // Glass to stained glass, mostly a joke
        // Maybe make a common tag?
        data.add(probabilityTag(
                Tags.Blocks.GLASS_BLOCKS_CHEAP, Map.<Block, Integer>of(
                        Blocks.LIME_STAINED_GLASS,
                        1,
                        Blocks.GREEN_STAINED_GLASS,
                        7,
                        Blocks.AIR,
                        1,
                        Blocks.BROWN_STAINED_GLASS,
                        7
                )
        ));
        data.add(probabilityTag(
                Tags.Blocks.GLASS_PANES, Map.<Block, Integer>of(
                        Blocks.LIME_STAINED_GLASS_PANE,
                        1,
                        Blocks.GREEN_STAINED_GLASS_PANE,
                        7,
                        Blocks.AIR,
                        1,
                        Blocks.BROWN_STAINED_GLASS_PANE,
                        7
                )
        ));

        return new BlockTransformer(data, BlockTransformerRegistry.EROSION);
    }

    public static BlockTransformer erosionWet() {

        List<BlockTransformerData> data = new ArrayList<>();

        data.add(transformer("erosion"));
        data.add(direct(Blocks.DIRT, Blocks.MUD));
        data.add(direct(Blocks.TERRACOTTA, Blocks.CLAY));
        data.add(direct(Blocks.RED_TERRACOTTA, Blocks.CLAY));
        data.add(direct(Blocks.ORANGE_TERRACOTTA, Blocks.CLAY));
        data.add(direct(Blocks.YELLOW_TERRACOTTA, Blocks.CLAY));
        data.add(direct(Blocks.LIME_TERRACOTTA, Blocks.CLAY));
        data.add(direct(Blocks.GREEN_TERRACOTTA, Blocks.CLAY));
        data.add(direct(Blocks.CYAN_TERRACOTTA, Blocks.CLAY));
        data.add(direct(Blocks.LIGHT_BLUE_TERRACOTTA, Blocks.CLAY));
        data.add(direct(Blocks.BLUE_TERRACOTTA, Blocks.CLAY));
        data.add(direct(Blocks.PURPLE_TERRACOTTA, Blocks.CLAY));
        data.add(direct(Blocks.MAGENTA_TERRACOTTA, Blocks.CLAY));
        data.add(direct(Blocks.BROWN_TERRACOTTA, Blocks.CLAY));
        data.add(direct(Blocks.BLACK_TERRACOTTA, Blocks.CLAY));
        data.add(direct(Blocks.GRAY_TERRACOTTA, Blocks.CLAY));
        data.add(direct(Blocks.LIGHT_GRAY_TERRACOTTA, Blocks.CLAY));
        data.add(direct(Blocks.WHITE_TERRACOTTA, Blocks.CLAY));
        data.add(direct(Blocks.PINK_TERRACOTTA, Blocks.CLAY));

        return new BlockTransformer(data, BlockTransformerRegistry.EROSION_WET);
    }

    public static BlockTransformer verdantRoots() {

        List<BlockTransformerData> data = new ArrayList<>();

        data.add(direct(Blocks.MUDDY_MANGROVE_ROOTS, BlockRegistry.VERDANT_ROOTED_MUD.get()));
        data.add(direct(Blocks.ROOTED_DIRT, BlockRegistry.VERDANT_ROOTED_DIRT.get()));
        data.add(direct(Blocks.DIRT, BlockRegistry.VERDANT_ROOTED_DIRT.get()));
        data.add(direct(Blocks.PODZOL, BlockRegistry.VERDANT_GRASS_DIRT.get()));
        data.add(direct(Blocks.MUD, BlockRegistry.VERDANT_ROOTED_MUD.get()));
        data.add(direct(Blocks.CLAY, BlockRegistry.VERDANT_ROOTED_CLAY.get()));
        data.add(direct(BlockRegistry.GRUS.get(), BlockRegistry.VERDANT_ROOTED_GRUS.get()));

        return new BlockTransformer(data, BlockTransformerRegistry.VERDANT_ROOTS);
    }

    public static BlockTransformer hoeing() {

        List<BlockTransformerData> data = new ArrayList<>();

        data.add(direct(BlockRegistry.VERDANT_ROOTED_DIRT.get(), Blocks.DIRT));
        data.add(direct(BlockRegistry.VERDANT_GRASS_DIRT.get(), BlockRegistry.VERDANT_ROOTED_DIRT.get()));
        data.add(direct(BlockRegistry.VERDANT_ROOTED_MUD.get(), Blocks.MUD));
        data.add(direct(BlockRegistry.VERDANT_GRASS_MUD.get(), BlockRegistry.VERDANT_ROOTED_MUD.get()));
        data.add(direct(BlockRegistry.VERDANT_ROOTED_CLAY.get(), Blocks.CLAY));
        data.add(direct(BlockRegistry.VERDANT_GRASS_CLAY.get(), BlockRegistry.VERDANT_ROOTED_CLAY.get()));
        data.add(direct(BlockRegistry.VERDANT_ROOTED_GRUS.get(), BlockRegistry.GRUS.get()));
        data.add(direct(BlockRegistry.VERDANT_GRASS_GRUS.get(), BlockRegistry.VERDANT_ROOTED_GRUS.get()));

        return new BlockTransformer(data, BlockTransformerRegistry.HOEING);
    }


    public static BlockTransformer toxicAsh() {

        List<BlockTransformerData> data = new ArrayList<>();

        data.add(directTag(BlockTags.LEAVES, Blocks.AIR));
        data.add(directTag(BlockTags.FLOWERS, Blocks.AIR));
        data.add(direct(BlockRegistry.SNAPLEAF.get(), Blocks.AIR));
        data.add(probability(Blocks.SHORT_GRASS, Map.of(BlockRegistry.DEAD_GRASS.get(), 1, Blocks.AIR, 2)));
        data.add(probabilityTag(
                BlockTags.LOGS_THAT_BURN,
                Map.of(WoodSets.DEAD.getStrippedWood().get(), 2, WoodSets.DEAD.getStrippedLog().get(), 1)
        ));
        data.add(direct(Blocks.VINE, Blocks.AIR));
        data.add(direct(Blocks.TALL_GRASS, Blocks.AIR));
        data.add(probability(Blocks.FERN, Map.of(BlockRegistry.DEAD_GRASS.get(), 1, Blocks.AIR, 2)));
        data.add(direct(Blocks.LARGE_FERN, Blocks.AIR));
        data.add(directTag(BlockTags.UNDERWATER_BONEMEALS, Blocks.WATER));

        Map<Block, Block> corals = getCoralsBlockMap();

        for (Map.Entry<Block, Block> coral : corals.entrySet()) {
            data.add(direct(coral.getKey(), coral.getValue()));
        }

        data.add(direct(BlockRegistry.DROWNED_HEMLOCK.get(), Blocks.WATER));
        data.add(direct(BlockRegistry.DROWNED_HEMLOCK_PLANT.get(), Blocks.WATER));
        data.add(direct(Blocks.KELP, Blocks.WATER));
        data.add(direct(Blocks.KELP_PLANT, Blocks.WATER));
        data.add(direct(Blocks.SEAGRASS, Blocks.WATER));
        data.add(direct(Blocks.TALL_SEAGRASS, Blocks.WATER));
        data.add(direct(BlockRegistry.STRANGLER_TENDRIL.get(), Blocks.AIR));
        data.add(direct(BlockRegistry.STRANGLER_TENDRIL_PLANT.get(), Blocks.AIR));
        data.add(direct(BlockRegistry.STRANGLER_VINE.get(), Blocks.AIR));
        data.add(direct(BlockRegistry.LEAFY_STRANGLER_VINE.get(), Blocks.AIR));
        data.add(direct(BlockRegistry.BUSH.get(), Blocks.DEAD_BUSH));
        data.add(direct(BlockRegistry.THORN_BUSH.get(), Blocks.DEAD_BUSH));
        data.add(direct(WoodSets.DEAD.getStrippedWood().get(), WoodSets.DEAD.getStrippedWood().get()));
        data.add(direct(WoodSets.DEAD.getStrippedLog().get(), WoodSets.DEAD.getStrippedLog().get()));
        data.add(directTag(BlockTags.CROPS, Blocks.AIR));
        data.add(directTag(BlockTags.DIRT, BlockRegistry.TOXIC_DIRT.get()));
        data.add(direct(BlockRegistry.GRUS.get(), BlockRegistry.TOXIC_GRUS.get()));
        data.add(direct(BlockRegistry.STONY_GRUS.get(), BlockRegistry.TOXIC_GRUS.get()));
        data.add(direct(Blocks.LILY_PAD, Blocks.AIR));
        data.add(direct(Blocks.MOSS_BLOCK, Blocks.DIRT));
        data.add(direct(Blocks.PALE_MOSS_BLOCK, Blocks.DIRT));
        data.add(direct(Blocks.PALE_HANGING_MOSS, Blocks.AIR));
        data.add(direct(Blocks.FARMLAND, BlockRegistry.TOXIC_DIRT.get()));

        return new BlockTransformer(data, BlockTransformerRegistry.TOXIC_ASH);
    }

    public static Map<Block, Block> getCoralsBlockMap() {
        Map<Block, Block> coralPlants = Map.of(
                Blocks.BRAIN_CORAL,
                Blocks.DEAD_BRAIN_CORAL,
                Blocks.BUBBLE_CORAL,
                Blocks.DEAD_BUBBLE_CORAL,
                Blocks.FIRE_CORAL,
                Blocks.DEAD_FIRE_CORAL,
                Blocks.HORN_CORAL,
                Blocks.DEAD_HORN_CORAL,
                Blocks.TUBE_CORAL,
                Blocks.DEAD_TUBE_CORAL
        );
        Map<Block, Block> coralBlocks = Map.of(
                Blocks.BRAIN_CORAL_BLOCK,
                Blocks.DEAD_BRAIN_CORAL_BLOCK,
                Blocks.BUBBLE_CORAL_BLOCK,
                Blocks.DEAD_BUBBLE_CORAL_BLOCK,
                Blocks.FIRE_CORAL_BLOCK,
                Blocks.DEAD_FIRE_CORAL_BLOCK,
                Blocks.HORN_CORAL_BLOCK,
                Blocks.DEAD_HORN_CORAL_BLOCK,
                Blocks.TUBE_CORAL_BLOCK,
                Blocks.DEAD_TUBE_CORAL_BLOCK
        );
        Map<Block, Block> coralFans = Map.of(
                Blocks.BRAIN_CORAL_FAN,
                Blocks.DEAD_BRAIN_CORAL_FAN,
                Blocks.BUBBLE_CORAL_FAN,
                Blocks.DEAD_BUBBLE_CORAL_FAN,
                Blocks.FIRE_CORAL_FAN,
                Blocks.DEAD_FIRE_CORAL_FAN,
                Blocks.HORN_CORAL_FAN,
                Blocks.DEAD_HORN_CORAL_FAN,
                Blocks.TUBE_CORAL_FAN,
                Blocks.DEAD_TUBE_CORAL_FAN
        );
        Map<Block, Block> coralWallFans = Map.of(
                Blocks.BRAIN_CORAL_WALL_FAN,
                Blocks.DEAD_BRAIN_CORAL_WALL_FAN,
                Blocks.BUBBLE_CORAL_WALL_FAN,
                Blocks.DEAD_BUBBLE_CORAL_WALL_FAN,
                Blocks.FIRE_CORAL_WALL_FAN,
                Blocks.DEAD_FIRE_CORAL_WALL_FAN,
                Blocks.HORN_CORAL_WALL_FAN,
                Blocks.DEAD_HORN_CORAL_WALL_FAN,
                Blocks.TUBE_CORAL_WALL_FAN,
                Blocks.DEAD_TUBE_CORAL_WALL_FAN
        );
        Map<Block, Block> corals = new HashMap<>();
        corals.putAll(coralPlants);
        corals.putAll(coralBlocks);
        corals.putAll(coralFans);
        corals.putAll(coralWallFans);
        return corals;
    }


    private static ResourceLocation name(Block block) {
        return BuiltInRegistries.BLOCK.getKey(block);
    }

    private static BlockTransformerData direct(Block from, Block to) {
        return new BlockTransformerData(null, name(to), null, null, name(from));
    }

    private static BlockTransformerData probability(Block from, Map<Block, Integer> to) {
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

    private static BlockTransformerData probabilityTag(TagKey<Block> from, Map<Block, Integer> to) {
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

    private static BlockTransformerData directTag(TagKey<Block> from, Block to) {
        return new BlockTransformerData(null, name(to), null, from, null);
    }

    private static BlockTransformerData transformer(String transformer) {
        return new BlockTransformerData(
                ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, transformer),
                null,
                null,
                null,
                null
        );
    }
}
