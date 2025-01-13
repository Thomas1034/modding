package com.startraveler.mansioneer.util.blocktransformer;

import com.startraveler.mansioneer.data.blocktransformer.BlockTransformerData;
import it.unimi.dsi.fastutil.Pair;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StoneTypes {

    public static final StoneType COBBLED_DEEPSLATE = new StoneType(
            "cobbled_deepslate",
            Blocks.COBBLED_DEEPSLATE,
            Blocks.COBBLED_DEEPSLATE,
            Blocks.COBBLED_DEEPSLATE_STAIRS,
            Blocks.COBBLED_DEEPSLATE_SLAB,
            Blocks.COBBLED_DEEPSLATE_WALL,
            Blocks.COBBLED_DEEPSLATE,
            Blocks.DEEPSLATE,
            Blocks.COBBLED_DEEPSLATE_STAIRS,
            Blocks.COBBLED_DEEPSLATE_SLAB,
            Blocks.COBBLED_DEEPSLATE_WALL
    );

    public static final StoneType DEEPSLATE_BRICKS = new StoneType(
            "deepslate_bricks",
            Blocks.DEEPSLATE_BRICKS,
            Blocks.CRACKED_DEEPSLATE_BRICKS,
            Blocks.DEEPSLATE_BRICK_STAIRS,
            Blocks.DEEPSLATE_BRICK_SLAB,
            Blocks.DEEPSLATE_BRICK_WALL,
            Blocks.CHISELED_DEEPSLATE,
            Blocks.POLISHED_DEEPSLATE,
            Blocks.POLISHED_DEEPSLATE_STAIRS,
            Blocks.POLISHED_DEEPSLATE_SLAB,
            Blocks.POLISHED_DEEPSLATE_WALL
    );


    public static final StoneType MUD_BRICKS = new StoneType(
            "mud_bricks",
            Blocks.MUD_BRICKS,
            Blocks.PACKED_MUD,
            Blocks.MUD_BRICK_STAIRS,
            Blocks.MUD_BRICK_SLAB,
            Blocks.MUD_BRICK_WALL,
            Blocks.MUD_BRICKS,
            Blocks.MUD_BRICKS,
            Blocks.MUD_BRICK_STAIRS,
            Blocks.MUD_BRICK_SLAB,
            Blocks.MUD_BRICK_WALL
    );

    public static final StoneType STONE_BRICKS = new StoneType(
            "stone_bricks",
            Blocks.STONE_BRICKS,
            Blocks.CRACKED_STONE_BRICKS,
            Blocks.STONE_BRICK_STAIRS,
            Blocks.STONE_BRICK_SLAB,
            Blocks.STONE_BRICK_WALL,
            Blocks.CHISELED_STONE_BRICKS,
            Blocks.MOSSY_STONE_BRICKS,
            Blocks.MOSSY_STONE_BRICK_STAIRS,
            Blocks.MOSSY_STONE_BRICK_SLAB,
            Blocks.MOSSY_STONE_BRICK_WALL
    );

    public static final StoneType COBBLESTONE = new StoneType(
            "cobblestone",
            Blocks.COBBLESTONE,
            Blocks.INFESTED_COBBLESTONE,
            Blocks.COBBLESTONE_STAIRS,
            Blocks.COBBLESTONE_SLAB,
            Blocks.COBBLESTONE_WALL,
            Blocks.SMOOTH_STONE,
            Blocks.MOSSY_COBBLESTONE,
            Blocks.MOSSY_COBBLESTONE_STAIRS,
            Blocks.MOSSY_COBBLESTONE_SLAB,
            Blocks.MOSSY_COBBLESTONE_WALL
    );

    public static final StoneType SANDSTONE = new StoneType(
            "sandstone",
            Blocks.SANDSTONE,
            Blocks.SAND,
            Blocks.SANDSTONE_STAIRS,
            Blocks.SANDSTONE_SLAB,
            Blocks.SANDSTONE_WALL,
            Blocks.CHISELED_SANDSTONE,
            Blocks.SMOOTH_SANDSTONE,
            Blocks.SMOOTH_SANDSTONE_STAIRS,
            Blocks.SMOOTH_SANDSTONE_SLAB,
            Blocks.SANDSTONE_WALL
    );

    public static final StoneType RED_SANDSTONE = new StoneType(
            "red_sandstone",
            Blocks.RED_SANDSTONE,
            Blocks.RED_SAND,
            Blocks.RED_SANDSTONE_STAIRS,
            Blocks.RED_SANDSTONE_SLAB,
            Blocks.RED_SANDSTONE_WALL,
            Blocks.CHISELED_RED_SANDSTONE,
            Blocks.SMOOTH_RED_SANDSTONE,
            Blocks.SMOOTH_RED_SANDSTONE_STAIRS,
            Blocks.SMOOTH_RED_SANDSTONE_SLAB,
            Blocks.RED_SANDSTONE_WALL
    );

    protected static final Map<Pair<StoneType, StoneType>, BlockTransformer> MAPPINGS = new HashMap<>();

    public static Pair<ResourceLocation, BlockTransformer> fullTransformer(StoneType from, StoneType to) {
        return Pair.of(transformName(from, to), transform(from, to));
    }


    public static BlockTransformer transform(StoneType from, StoneType to) {
        Pair<StoneType, StoneType> pair = Pair.of(from, to);
        BlockTransformer result = MAPPINGS.get(pair);

        if (result == null) {

            List<BlockTransformerData> data = new ArrayList<>();
            data.add(BuiltInTransformers.direct(from.stoneBricks, to.stoneBricks));
            data.add(BuiltInTransformers.direct(from.crackedStoneBricks, to.crackedStoneBricks));
            data.add(BuiltInTransformers.direct(from.stoneBrickStairs, to.stoneBrickStairs));
            data.add(BuiltInTransformers.direct(from.stoneBrickSlabs, to.stoneBrickSlabs));
            data.add(BuiltInTransformers.direct(from.stoneBrickWalls, to.stoneBrickWalls));
            data.add(BuiltInTransformers.direct(from.chiseledStoneBrick, to.chiseledStoneBrick));
            data.add(BuiltInTransformers.direct(from.mossyStoneBricks, to.mossyStoneBricks));
            data.add(BuiltInTransformers.direct(from.mossyStoneBrickStairs, to.mossyStoneBrickStairs));
            data.add(BuiltInTransformers.direct(from.mossyStoneBrickSlabs, to.mossyStoneBrickSlabs));
            data.add(BuiltInTransformers.direct(from.mossyStoneBrickWalls, to.mossyStoneBrickWalls));
            result = new BlockTransformer(data, transformName(from, to));
            MAPPINGS.put(pair, result);
        }
        return result;
    }

    public static ResourceLocation transformName(StoneType from, StoneType to) {
        return BuiltInTransformers.name("from_" + from.name + "_to_" + to.name);
    }

}
