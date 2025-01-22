package com.startraveler.mansioneer.data;


import com.startraveler.mansioneer.util.blocktransformer.*;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.inventory.AnvilMenu;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.Tags;

import java.util.List;
import java.util.Map;

public class MansioneerBlockTransformerProvider {

    public static void register(BootstrapContext<BlockTransformer> bootstrap) {
        bootstrap.register(key(BuiltInTransformers.EMPTY), new BlockTransformer(List.of(), BuiltInTransformers.EMPTY));
        registerWoodTransformer(bootstrap, WoodTypes.DARK_OAK, WoodTypes.ACACIA);
        registerWoodTransformer(bootstrap, WoodTypes.DARK_OAK, WoodTypes.OAK);
        registerWoodTransformer(bootstrap, WoodTypes.DARK_OAK, WoodTypes.SPRUCE);
        registerWoodTransformer(bootstrap, WoodTypes.DARK_OAK, WoodTypes.BIRCH);
        registerWoodTransformer(bootstrap, WoodTypes.DARK_OAK, WoodTypes.JUNGLE);
        registerWoodTransformer(bootstrap, WoodTypes.DARK_OAK, WoodTypes.MANGROVE);
        registerWoodTransformer(bootstrap, WoodTypes.DARK_OAK, WoodTypes.PALE_OAK);
        registerWoodTransformer(bootstrap, WoodTypes.DARK_OAK, WoodTypes.CHERRY);
        registerWoodTransformer(bootstrap, WoodTypes.BIRCH, WoodTypes.DARK_OAK);
        registerWoodTransformer(bootstrap, WoodTypes.BIRCH, WoodTypes.SPRUCE);
        registerWoodTransformer(bootstrap, WoodTypes.BIRCH, WoodTypes.OAK);
        registerWoodTransformer(bootstrap, WoodTypes.OAK, WoodTypes.SPRUCE);
        registerWoodTransformer(bootstrap, WoodTypes.OAK, WoodTypes.JUNGLE);
        registerWoodTransformer(bootstrap, WoodTypes.OAK, WoodTypes.MANGROVE);
        registerWoodTransformer(bootstrap, WoodTypes.OAK, WoodTypes.BAMBOO);
        registerStoneTransformer(bootstrap, StoneTypes.COBBLESTONE, StoneTypes.COBBLED_DEEPSLATE);
        registerStoneTransformer(bootstrap, StoneTypes.COBBLESTONE, StoneTypes.MUD_BRICKS);
        registerStoneTransformer(bootstrap, StoneTypes.COBBLESTONE, StoneTypes.SANDSTONE);
        registerStoneTransformer(bootstrap, StoneTypes.COBBLESTONE, StoneTypes.RED_SANDSTONE);
        registerStoneTransformer(bootstrap, StoneTypes.COBBLESTONE, StoneTypes.DIORITE);
        registerStoneTransformer(bootstrap, StoneTypes.COBBLESTONE, StoneTypes.BRICK);
        registerStoneTransformer(bootstrap, StoneTypes.COBBLESTONE, StoneTypes.STONE_BRICKS);
        registerCrossTransformer(bootstrap, WoodTypes.BIRCH, StoneTypes.STONE_BRICKS);
        registerCrossTransformer(bootstrap, WoodTypes.BIRCH, StoneTypes.COBBLESTONE);
        registerCrossTransformer(bootstrap, WoodTypes.DARK_OAK, StoneTypes.STONE_BRICKS);
        registerCrossTransformer(bootstrap, WoodTypes.DARK_OAK, StoneTypes.BRICK);
        registerCrossTransformer(bootstrap, WoodTypes.BIRCH, StoneTypes.GRANITE);
        registerCrossTransformer(bootstrap, StoneTypes.COBBLESTONE, WoodTypes.DARK_OAK);
        bootstrap.register(
                key(BuiltInTransformers.MANSION_TO_SAVANNA), new BlockTransformer(
                        List.of(
                                BuiltInTransformers.transformer(WoodTypes.transformName(
                                        WoodTypes.DARK_OAK,
                                        WoodTypes.ACACIA
                                )),
                                BuiltInTransformers.transformer(WoodTypes.transformName(
                                        WoodTypes.BIRCH,
                                        WoodTypes.DARK_OAK
                                )),
                                BuiltInTransformers.direct(Blocks.RED_CARPET, Blocks.BLUE_CARPET)
                        ), BuiltInTransformers.MANSION_TO_SAVANNA
                )
        );
        bootstrap.register(
                key(BuiltInTransformers.MANSION_TO_PALE_GARDEN), new BlockTransformer(
                        List.of(
                                BuiltInTransformers.transformer(WoodTypes.transformName(
                                        WoodTypes.DARK_OAK,
                                        WoodTypes.PALE_OAK
                                )),
                                BuiltInTransformers.transformer(WoodTypes.transformName(
                                        WoodTypes.BIRCH,
                                        WoodTypes.SPRUCE
                                )),
                                BuiltInTransformers.transformer(StoneTypes.transformName(
                                        StoneTypes.COBBLESTONE,
                                        StoneTypes.COBBLED_DEEPSLATE
                                )),
                                BuiltInTransformers.direct(Blocks.RED_CARPET, Blocks.WHITE_CARPET),
                                BuiltInTransformers.direct(Blocks.WHITE_CARPET, Blocks.GRAY_CARPET)
                        ), BuiltInTransformers.MANSION_TO_PALE_GARDEN
                )
        );
        bootstrap.register(
                key(BuiltInTransformers.MANSION_TO_SWAMP), new BlockTransformer(
                        List.of(
                                BuiltInTransformers.transformer(WoodTypes.transformName(
                                        WoodTypes.DARK_OAK,
                                        WoodTypes.MANGROVE
                                )),
                                BuiltInTransformers.transformer(WoodTypes.transformName(
                                        WoodTypes.BIRCH,
                                        WoodTypes.OAK
                                )),
                                BuiltInTransformers.transformer(WoodTypes.transformName(
                                        WoodTypes.OAK,
                                        WoodTypes.SPRUCE
                                )),
                                BuiltInTransformers.transformer(StoneTypes.transformName(
                                        StoneTypes.COBBLESTONE,
                                        StoneTypes.MUD_BRICKS
                                )),
                                BuiltInTransformers.direct(Blocks.RED_CARPET, Blocks.LIGHT_GRAY_CARPET),
                                BuiltInTransformers.direct(Blocks.WHITE_CARPET, Blocks.GREEN_CARPET)
                        ), BuiltInTransformers.MANSION_TO_SWAMP
                )
        );

        bootstrap.register(
                key(BuiltInTransformers.MANSION_TO_TAIGA), new BlockTransformer(
                        List.of(
                                BuiltInTransformers.probability(
                                        Blocks.COBBLESTONE,
                                        Map.of(Blocks.COBBLESTONE, 7, Blocks.MOSSY_COBBLESTONE, 1)
                                ),
                                BuiltInTransformers.transformer(WoodTypes.transformName(
                                        WoodTypes.DARK_OAK,
                                        WoodTypes.SPRUCE
                                )),
                                BuiltInTransformers.transformer(CrossTypes.transformName(
                                        WoodTypes.BIRCH,
                                        StoneTypes.STONE_BRICKS
                                )),
                                BuiltInTransformers.transformer(WoodTypes.transformName(
                                        WoodTypes.OAK,
                                        WoodTypes.JUNGLE
                                )),
                                BuiltInTransformers.direct(Blocks.TORCH, Blocks.LANTERN),
                                BuiltInTransformers.direct(Blocks.RED_CARPET, Blocks.RED_CARPET)
                        ), BuiltInTransformers.MANSION_TO_TAIGA
                )
        );

        bootstrap.register(
                key(BuiltInTransformers.MANSION_TO_JUNGLE), new BlockTransformer(
                        List.of(
                                BuiltInTransformers.transformer(WoodTypes.transformName(
                                        WoodTypes.DARK_OAK,
                                        WoodTypes.JUNGLE
                                )),
                                BuiltInTransformers.transformer(WoodTypes.transformName(
                                        WoodTypes.OAK,
                                        WoodTypes.MANGROVE
                                )),
                                BuiltInTransformers.probability(
                                        Blocks.COBBLESTONE,
                                        Map.of(Blocks.COBBLESTONE, 2, Blocks.MOSSY_COBBLESTONE, 1)
                                ),
                                BuiltInTransformers.probability(
                                        Blocks.COBBLESTONE_SLAB,
                                        Map.of(Blocks.COBBLESTONE_SLAB, 2, Blocks.MOSSY_COBBLESTONE_SLAB, 1)
                                ),
                                BuiltInTransformers.probability(
                                        Blocks.COBBLESTONE_STAIRS,
                                        Map.of(Blocks.COBBLESTONE_STAIRS, 2, Blocks.MOSSY_COBBLESTONE_STAIRS, 1)
                                ),
                                BuiltInTransformers.probability(
                                        Blocks.COBBLESTONE_WALL,
                                        Map.of(Blocks.COBBLESTONE_WALL, 2, Blocks.MOSSY_COBBLESTONE_WALL, 1)
                                ),
                                BuiltInTransformers.probabilityTag(
                                        Tags.Blocks.GLASS_BLOCKS, Map.of(
                                                Blocks.LIGHT_BLUE_STAINED_GLASS,
                                                1,
                                                Blocks.YELLOW_STAINED_GLASS,
                                                1,
                                                Blocks.RED_STAINED_GLASS,
                                                1,
                                                Blocks.CYAN_STAINED_GLASS,
                                                1,
                                                Blocks.LIME_STAINED_GLASS,
                                                1,
                                                Blocks.PINK_STAINED_GLASS,
                                                1,
                                                Blocks.GREEN_STAINED_GLASS,
                                                1
                                        )
                                ),
                                BuiltInTransformers.probabilityTag(
                                        Tags.Blocks.GLASS_PANES, Map.of(
                                                Blocks.BLUE_STAINED_GLASS_PANE,
                                                2,
                                                Blocks.RED_STAINED_GLASS_PANE,
                                                1,
                                                Blocks.PURPLE_STAINED_GLASS_PANE,
                                                1,
                                                Blocks.GREEN_STAINED_GLASS_PANE,
                                                8
                                        )
                                ),
                                BuiltInTransformers.direct(Blocks.RED_CARPET, Blocks.YELLOW_CARPET),
                                BuiltInTransformers.direct(Blocks.WHITE_CARPET, Blocks.GREEN_CARPET)
                        ), BuiltInTransformers.MANSION_TO_JUNGLE
                )
        );


        bootstrap.register(
                key(BuiltInTransformers.MANSION_TO_DESERT), new BlockTransformer(
                        List.of(
                                BuiltInTransformers.probability(
                                        Blocks.FARMLAND,
                                        Map.of(Blocks.COARSE_DIRT, 1, Blocks.SAND, 3)
                                ),
                                BuiltInTransformers.probabilityTag(
                                        BlockTags.CROPS,
                                        Map.of(Blocks.DEAD_BUSH, 1, Blocks.AIR, 5, Blocks.CACTUS, 2)
                                ),
                                BuiltInTransformers.direct(Blocks.DARK_OAK_PLANKS, Blocks.CUT_SANDSTONE),
                                BuiltInTransformers.direct(Blocks.BIRCH_PLANKS, Blocks.SANDSTONE),
                                BuiltInTransformers.direct(Blocks.DARK_OAK_LOG, Blocks.CHISELED_SANDSTONE),
                                BuiltInTransformers.direct(Blocks.DARK_OAK_WOOD, Blocks.CHISELED_SANDSTONE),
                                BuiltInTransformers.direct(Blocks.STRIPPED_DARK_OAK_LOG, Blocks.CHISELED_RED_SANDSTONE),
                                BuiltInTransformers.direct(
                                        Blocks.STRIPPED_DARK_OAK_WOOD,
                                        Blocks.CHISELED_RED_SANDSTONE
                                ),
                                BuiltInTransformers.direct(Blocks.COBBLESTONE, Blocks.CUT_RED_SANDSTONE),
                                BuiltInTransformers.direct(Blocks.COBBLESTONE_WALL, Blocks.SANDSTONE_WALL),
                                BuiltInTransformers.direct(Blocks.DARK_OAK_STAIRS, Blocks.SANDSTONE_STAIRS),
                                BuiltInTransformers.direct(Blocks.DARK_OAK_SLAB, Blocks.SANDSTONE_SLAB),
                                BuiltInTransformers.direct(Blocks.DARK_OAK_FENCE, Blocks.SANDSTONE_WALL),
                                BuiltInTransformers.probability(
                                        Blocks.TORCH, Map.of(
                                                Blocks.TORCH,
                                                24,
                                                Blocks.AIR,
                                                3,
                                                Blocks.REDSTONE_TORCH,
                                                2,
                                                Blocks.COBWEB,
                                                3
                                        )
                                ),
                                BuiltInTransformers.probability(
                                        Blocks.WALL_TORCH, Map.of(
                                                Blocks.WALL_TORCH,
                                                24,
                                                Blocks.AIR,
                                                3,
                                                Blocks.REDSTONE_WALL_TORCH,
                                                2,
                                                Blocks.COBWEB,
                                                3
                                        )
                                ),
                                BuiltInTransformers.transformer(WoodTypes.transformName(
                                        WoodTypes.DARK_OAK,
                                        WoodTypes.OAK
                                )),
                                BuiltInTransformers.transformer(CrossTypes.transformName(
                                        WoodTypes.BIRCH,
                                        StoneTypes.COBBLESTONE
                                )),
                                BuiltInTransformers.transformer(StoneTypes.transformName(
                                        StoneTypes.COBBLESTONE,
                                        StoneTypes.RED_SANDSTONE
                                )),
                                BuiltInTransformers.probabilityTag(
                                        BlockTags.WOOL_CARPETS, Map.of(
                                                Blocks.BROWN_CARPET,
                                                3,
                                                Blocks.MOSS_CARPET,
                                                1,
                                                Blocks.AIR,
                                                25,
                                                Blocks.GRAY_CARPET,
                                                1
                                        )
                                ),
                                BuiltInTransformers.probabilityTag(
                                        BlockTags.WOOL, Map.of(
                                                Blocks.GRAVEL,
                                                15,
                                                Blocks.SAND,
                                                45,
                                                Blocks.BROWN_WOOL,
                                                3,
                                                Blocks.GRAY_WOOL,
                                                1
                                        )
                                ),
                                BuiltInTransformers.direct(Blocks.DIRT, Blocks.COARSE_DIRT),
                                BuiltInTransformers.directTag(BlockTags.LEAVES, Blocks.AIR),
                                BuiltInTransformers.directTag(BlockTags.SAPLINGS, Blocks.DEAD_BUSH),
                                BuiltInTransformers.probabilityTag(
                                        BlockTags.FLOWER_POTS, Map.of(
                                                Blocks.POTTED_DEAD_BUSH,
                                                3,
                                                Blocks.COBWEB,
                                                1,
                                                Blocks.AIR,
                                                3,
                                                Blocks.REDSTONE_WIRE,
                                                1
                                        )
                                ),
                                BuiltInTransformers.probabilityTag(
                                        Tags.Blocks.GLASS_PANES, Map.of(
                                                Blocks.AIR,
                                                2,
                                                Blocks.COBWEB,
                                                1,
                                                Blocks.GLASS_PANE,
                                                3,
                                                Blocks.GRAY_STAINED_GLASS_PANE,
                                                3,
                                                Blocks.BROWN_STAINED_GLASS_PANE,
                                                7
                                        )
                                )
                        ), BuiltInTransformers.MANSION_TO_DESERT
                )
        );

        bootstrap.register(
                key(BuiltInTransformers.MANSION_TO_BIRCH), new BlockTransformer(
                        List.of(
                                BuiltInTransformers.transformer(WoodTypes.transformName(
                                        WoodTypes.DARK_OAK,
                                        WoodTypes.BIRCH
                                )),
                                BuiltInTransformers.transformer(WoodTypes.transformName(
                                        WoodTypes.BIRCH,
                                        WoodTypes.OAK
                                )),
                                BuiltInTransformers.transformer(WoodTypes.transformName(
                                        WoodTypes.OAK,
                                        WoodTypes.SPRUCE
                                )),

                                BuiltInTransformers.probability(
                                        Blocks.COBBLESTONE,
                                        Map.of(Blocks.COBBLESTONE, 7, Blocks.MOSSY_COBBLESTONE, 1)
                                ),
                                BuiltInTransformers.probability(
                                        Blocks.COBBLESTONE_SLAB,
                                        Map.of(Blocks.COBBLESTONE_SLAB, 7, Blocks.MOSSY_COBBLESTONE_SLAB, 1)
                                ),
                                BuiltInTransformers.probability(
                                        Blocks.COBBLESTONE_STAIRS,
                                        Map.of(Blocks.COBBLESTONE_STAIRS, 7, Blocks.MOSSY_COBBLESTONE_STAIRS, 1)
                                ),
                                BuiltInTransformers.probability(
                                        Blocks.COBBLESTONE_WALL,
                                        Map.of(Blocks.COBBLESTONE_WALL, 7, Blocks.MOSSY_COBBLESTONE_WALL, 1)
                                ),
                                BuiltInTransformers.direct(Blocks.RED_CARPET, Blocks.LIGHT_BLUE_CARPET),
                                BuiltInTransformers.direct(Blocks.GLASS_PANE, Blocks.LIGHT_GRAY_STAINED_GLASS_PANE)

                        ), BuiltInTransformers.MANSION_TO_BIRCH
                )
        );

        bootstrap.register(
                key(BuiltInTransformers.MANSION_TO_MOUNTAIN), new BlockTransformer(
                        List.of(
                                BuiltInTransformers.transformer(CrossTypes.transformName(
                                        WoodTypes.DARK_OAK,
                                        StoneTypes.STONE_BRICKS
                                )),
                                BuiltInTransformers.transformer(CrossTypes.transformName(
                                        WoodTypes.BIRCH,
                                        StoneTypes.GRANITE
                                )),
                                BuiltInTransformers.transformer(WoodTypes.transformName(
                                        WoodTypes.OAK,
                                        WoodTypes.SPRUCE
                                )),
                                BuiltInTransformers.direct(Blocks.COBBLESTONE_WALL, Blocks.IRON_BARS),
                                BuiltInTransformers.direct(Blocks.DARK_OAK_FENCE, Blocks.IRON_BARS),
                                BuiltInTransformers.direct(Blocks.TORCH, Blocks.LANTERN),
                                BuiltInTransformers.directTag(BlockTags.LEAVES, Blocks.IRON_ORE),
                                BuiltInTransformers.direct(Blocks.DARK_OAK_SAPLING, Blocks.SPRUCE_SAPLING),
                                BuiltInTransformers.probabilityTag(
                                        BlockTags.FLOWER_POTS, Map.of(
                                                Blocks.POTTED_BROWN_MUSHROOM,
                                                1,
                                                Blocks.POTTED_RED_MUSHROOM,
                                                1,
                                                Blocks.POTTED_SPRUCE_SAPLING,
                                                2
                                        )
                                ),
                                BuiltInTransformers.transformer(StoneTypes.transformName(
                                        StoneTypes.COBBLESTONE,
                                        StoneTypes.DIORITE
                                )),
                                BuiltInTransformers.direct(Blocks.RED_CARPET, Blocks.WHITE_CARPET),
                                BuiltInTransformers.direct(Blocks.WHITE_CARPET, Blocks.LIGHT_GRAY_CARPET),
                                BuiltInTransformers.direct(Blocks.GLASS_PANE, Blocks.GRAY_STAINED_GLASS_PANE)
                        ), BuiltInTransformers.MANSION_TO_MOUNTAIN
                )
        );

        bootstrap.register(
                key(BuiltInTransformers.MANSION_TO_CHERRY), new BlockTransformer(
                        List.of(
                                BuiltInTransformers.direct(Blocks.DARK_OAK_STAIRS, Blocks.SPRUCE_STAIRS),
                                BuiltInTransformers.transformer(WoodTypes.transformName(
                                        WoodTypes.DARK_OAK,
                                        WoodTypes.CHERRY
                                )),
                                BuiltInTransformers.transformer(WoodTypes.transformName(
                                        WoodTypes.OAK,
                                        WoodTypes.SPRUCE
                                )),
                                BuiltInTransformers.transformer(StoneTypes.transformName(
                                        StoneTypes.COBBLESTONE,
                                        StoneTypes.BRICK
                                )),
                                BuiltInTransformers.direct(Blocks.RED_CARPET, Blocks.LIGHT_BLUE_CARPET)

                        ), BuiltInTransformers.MANSION_TO_CHERRY
                )
        );

        bootstrap.register(
                key(BuiltInTransformers.MANSION_TO_ICE), new BlockTransformer(
                        List.of(
                                BuiltInTransformers.transformer(CrossTypes.transformName(
                                        WoodTypes.DARK_OAK,
                                        StoneTypes.STONE_BRICKS
                                )),
                                BuiltInTransformers.probability(
                                        Blocks.DARK_OAK_PLANKS, Map.of(
                                                Blocks.STONE_BRICKS,
                                                5,
                                                Blocks.CRACKED_STONE_BRICKS,
                                                1,
                                                Blocks.INFESTED_STONE_BRICKS,
                                                1,
                                                Blocks.INFESTED_CRACKED_STONE_BRICKS,
                                                1
                                        )
                                ),
                                BuiltInTransformers.probability(
                                        Blocks.BIRCH_PLANKS, Map.of(
                                                Blocks.PACKED_ICE,
                                                21,
                                                Blocks.BLUE_ICE,
                                                7,
                                                Blocks.SNOW_BLOCK,
                                                3,
                                                Blocks.POWDER_SNOW,
                                                1
                                        )
                                ),
                                BuiltInTransformers.transformer(WoodTypes.transformName(
                                        WoodTypes.BIRCH,
                                        WoodTypes.DARK_OAK
                                )),
                                BuiltInTransformers.transformer(WoodTypes.transformName(
                                        WoodTypes.OAK,
                                        WoodTypes.SPRUCE
                                )),
                                BuiltInTransformers.direct(Blocks.DARK_OAK_FENCE, Blocks.IRON_BARS),
                                BuiltInTransformers.direct(Blocks.TORCH, Blocks.REDSTONE_TORCH),
                                BuiltInTransformers.direct(Blocks.WALL_TORCH, Blocks.REDSTONE_WALL_TORCH),
                                BuiltInTransformers.directTag(BlockTags.LEAVES, Blocks.BLUE_ICE),
                                BuiltInTransformers.directTag(BlockTags.CROPS, Blocks.ICE),
                                BuiltInTransformers.direct(Blocks.DARK_OAK_SAPLING, Blocks.BLUE_ICE),
                                BuiltInTransformers.direct(Blocks.FARMLAND, Blocks.COARSE_DIRT),
                                BuiltInTransformers.direct(Blocks.WATER, Blocks.ICE),
                                BuiltInTransformers.probabilityTag(
                                        BlockTags.FLOWER_POTS, Map.of(
                                                Blocks.POTTED_BROWN_MUSHROOM,
                                                1,
                                                Blocks.POTTED_RED_MUSHROOM,
                                                1,
                                                Blocks.ICE,
                                                2
                                        )
                                ),
                                BuiltInTransformers.transformer(CrossTypes.transformName(
                                        StoneTypes.COBBLESTONE,
                                        WoodTypes.DARK_OAK
                                )),
                                BuiltInTransformers.probability(
                                        Blocks.RED_CARPET,
                                        Map.of(Blocks.SNOW, 1, Blocks.AIR, 2)
                                ),
                                BuiltInTransformers.probability(
                                        Blocks.WHITE_CARPET,
                                        Map.of(Blocks.SNOW, 1, Blocks.AIR, 2)
                                ),
                                BuiltInTransformers.direct(Blocks.GLASS_PANE, Blocks.ICE)
                        ), BuiltInTransformers.MANSION_TO_ICE
                )
        );

        bootstrap.register(
                key(BuiltInTransformers.MANSION_TO_FOREST), new BlockTransformer(
                        List.of(
                                BuiltInTransformers.direct(Blocks.DARK_OAK_STAIRS, Blocks.SPRUCE_STAIRS),
                                BuiltInTransformers.transformer(WoodTypes.transformName(
                                        WoodTypes.DARK_OAK,
                                        WoodTypes.OAK
                                )),
                                BuiltInTransformers.transformer(WoodTypes.transformName(
                                        WoodTypes.OAK,
                                        WoodTypes.JUNGLE
                                ))

                        ), BuiltInTransformers.MANSION_TO_FOREST
                )
        );

        bootstrap.register(
                key(BuiltInTransformers.MANSION_TO_PLAINS), new BlockTransformer(
                        List.of(
                                BuiltInTransformers.direct(Blocks.DARK_OAK_STAIRS, Blocks.SPRUCE_STAIRS),
                                BuiltInTransformers.transformer(WoodTypes.transformName(
                                        WoodTypes.DARK_OAK,
                                        WoodTypes.OAK
                                )),
                                BuiltInTransformers.transformer(WoodTypes.transformName(
                                        WoodTypes.OAK,
                                        WoodTypes.SPRUCE
                                )),
                                BuiltInTransformers.transformer(StoneTypes.transformName(
                                        StoneTypes.COBBLESTONE,
                                        StoneTypes.STONE_BRICKS
                                )),
                                BuiltInTransformers.direct(Blocks.RED_CARPET, Blocks.CYAN_CARPET)
                        ), BuiltInTransformers.MANSION_TO_PLAINS
                )
        );
    }


    public static ResourceKey<BlockTransformer> key(ResourceLocation location) {
        return ResourceKey.create(BlockTransformer.KEY, location);
    }

    protected static void registerCrossTransformer(BootstrapContext<BlockTransformer> bootstrap, WoodType from, StoneType to) {
        bootstrap.register(key(CrossTypes.transformName(from, to)), CrossTypes.transform(from, to));
    }

    protected static void registerCrossTransformer(BootstrapContext<BlockTransformer> bootstrap, StoneType from, WoodType to) {
        bootstrap.register(key(CrossTypes.transformName(from, to)), CrossTypes.transform(from, to));
    }

    protected static void registerStoneTransformer(BootstrapContext<BlockTransformer> bootstrap, StoneType from, StoneType to) {
        bootstrap.register(key(StoneTypes.transformName(from, to)), StoneTypes.transform(from, to));
    }

    protected static void registerWoodTransformer(BootstrapContext<BlockTransformer> bootstrap, WoodType from, WoodType to) {
        bootstrap.register(key(WoodTypes.transformName(from, to)), WoodTypes.transform(from, to));
    }
}
