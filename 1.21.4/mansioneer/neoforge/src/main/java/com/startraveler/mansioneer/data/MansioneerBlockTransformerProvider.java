package com.startraveler.mansioneer.data;


import com.startraveler.mansioneer.util.blocktransformer.*;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.Tags;

import java.util.List;
import java.util.Map;

public class MansioneerBlockTransformerProvider {

    public static void register(BootstrapContext<BlockTransformer> bootstrap) {
        registerWoodTransformer(bootstrap, WoodTypes.DARK_OAK, WoodTypes.ACACIA);
        registerWoodTransformer(bootstrap, WoodTypes.DARK_OAK, WoodTypes.OAK);
        registerWoodTransformer(bootstrap, WoodTypes.DARK_OAK, WoodTypes.SPRUCE);
        registerWoodTransformer(bootstrap, WoodTypes.DARK_OAK, WoodTypes.BIRCH);
        registerWoodTransformer(bootstrap, WoodTypes.DARK_OAK, WoodTypes.JUNGLE);
        registerWoodTransformer(bootstrap, WoodTypes.DARK_OAK, WoodTypes.MANGROVE);
        registerWoodTransformer(bootstrap, WoodTypes.BIRCH, WoodTypes.DARK_OAK);
        registerWoodTransformer(bootstrap, WoodTypes.DARK_OAK, WoodTypes.PALE_OAK);
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
        registerCrossTransformer(bootstrap, WoodTypes.BIRCH, StoneTypes.STONE_BRICKS);
        registerCrossTransformer(bootstrap, WoodTypes.BIRCH, StoneTypes.COBBLESTONE);
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
                                ))
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
                                BuiltInTransformers.direct(Blocks.RED_CARPET, Blocks.BLUE_CARPET)
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
                                                5,
                                                Blocks.COBWEB,
                                                1,
                                                Blocks.GLASS_PANE,
                                                3,
                                                Blocks.BROWN_STAINED_GLASS_PANE,
                                                7
                                        )
                                )
                        ), BuiltInTransformers.MANSION_TO_DESERT
                )
        );

    }

    public static ResourceKey<BlockTransformer> key(ResourceLocation location) {
        return ResourceKey.create(BlockTransformer.KEY, location);
    }

    protected static void registerCrossTransformer(BootstrapContext<BlockTransformer> bootstrap, WoodType from, StoneType to) {
        bootstrap.register(key(CrossTypes.transformName(from, to)), CrossTypes.transform(from, to));
    }

    protected static void registerStoneTransformer(BootstrapContext<BlockTransformer> bootstrap, StoneType from, StoneType to) {
        bootstrap.register(key(StoneTypes.transformName(from, to)), StoneTypes.transform(from, to));
    }

    protected static void registerWoodTransformer(BootstrapContext<BlockTransformer> bootstrap, WoodType from, WoodType to) {
        bootstrap.register(key(WoodTypes.transformName(from, to)), WoodTypes.transform(from, to));
    }
}
