package com.startraveler.mansioneer.util.blocktransformer;

import com.startraveler.mansioneer.data.blocktransformer.BlockTransformerData;
import it.unimi.dsi.fastutil.Pair;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WoodTypes {

    public static final WoodType OAK = new WoodType(
            "oak",
            Blocks.OAK_LOG,
            Blocks.OAK_WOOD,
            Blocks.STRIPPED_OAK_LOG,
            Blocks.STRIPPED_OAK_WOOD,
            Blocks.OAK_PLANKS,
            Blocks.OAK_STAIRS,
            Blocks.OAK_SLAB,
            Blocks.OAK_FENCE,
            Blocks.OAK_FENCE_GATE,
            Blocks.OAK_DOOR,
            Blocks.OAK_TRAPDOOR,
            Blocks.OAK_BUTTON,
            Blocks.OAK_LEAVES,
            Blocks.OAK_SAPLING,
            Blocks.POTTED_OAK_SAPLING,
            Blocks.OAK_SIGN,
            Blocks.OAK_WALL_SIGN,
            Blocks.OAK_HANGING_SIGN,
            Blocks.OAK_WALL_HANGING_SIGN
    );
    public static final WoodType SPRUCE = new WoodType(
            "spruce",
            Blocks.SPRUCE_LOG,
            Blocks.SPRUCE_WOOD,
            Blocks.STRIPPED_SPRUCE_LOG,
            Blocks.STRIPPED_SPRUCE_WOOD,
            Blocks.SPRUCE_PLANKS,
            Blocks.SPRUCE_STAIRS,
            Blocks.SPRUCE_SLAB,
            Blocks.SPRUCE_FENCE,
            Blocks.SPRUCE_FENCE_GATE,
            Blocks.SPRUCE_DOOR,
            Blocks.SPRUCE_TRAPDOOR,
            Blocks.SPRUCE_BUTTON,
            Blocks.SPRUCE_LEAVES,
            Blocks.SPRUCE_SAPLING,
            Blocks.POTTED_SPRUCE_SAPLING,
            Blocks.SPRUCE_SIGN,
            Blocks.SPRUCE_WALL_SIGN,
            Blocks.SPRUCE_HANGING_SIGN,
            Blocks.SPRUCE_WALL_HANGING_SIGN
    );
    public static final WoodType BIRCH = new WoodType(
            "birch",
            Blocks.BIRCH_LOG,
            Blocks.BIRCH_WOOD,
            Blocks.STRIPPED_BIRCH_LOG,
            Blocks.STRIPPED_BIRCH_WOOD,
            Blocks.BIRCH_PLANKS,
            Blocks.BIRCH_STAIRS,
            Blocks.BIRCH_SLAB,
            Blocks.BIRCH_FENCE,
            Blocks.BIRCH_FENCE_GATE,
            Blocks.BIRCH_DOOR,
            Blocks.BIRCH_TRAPDOOR,
            Blocks.BIRCH_BUTTON,
            Blocks.BIRCH_LEAVES,
            Blocks.BIRCH_SAPLING,
            Blocks.POTTED_BIRCH_SAPLING,
            Blocks.BIRCH_SIGN,
            Blocks.BIRCH_WALL_SIGN,
            Blocks.BIRCH_HANGING_SIGN,
            Blocks.BIRCH_WALL_HANGING_SIGN
    );
    public static final WoodType JUNGLE = new WoodType(
            "jungle",
            Blocks.JUNGLE_LOG,
            Blocks.JUNGLE_WOOD,
            Blocks.STRIPPED_JUNGLE_LOG,
            Blocks.STRIPPED_JUNGLE_WOOD,
            Blocks.JUNGLE_PLANKS,
            Blocks.JUNGLE_STAIRS,
            Blocks.JUNGLE_SLAB,
            Blocks.JUNGLE_FENCE,
            Blocks.JUNGLE_FENCE_GATE,
            Blocks.JUNGLE_DOOR,
            Blocks.JUNGLE_TRAPDOOR,
            Blocks.JUNGLE_BUTTON,
            Blocks.JUNGLE_LEAVES,
            Blocks.JUNGLE_SAPLING,
            Blocks.POTTED_JUNGLE_SAPLING,
            Blocks.JUNGLE_SIGN,
            Blocks.JUNGLE_WALL_SIGN,
            Blocks.JUNGLE_HANGING_SIGN,
            Blocks.JUNGLE_WALL_HANGING_SIGN
    );
    public static final WoodType ACACIA = new WoodType(
            "acacia",
            Blocks.ACACIA_LOG,
            Blocks.ACACIA_WOOD,
            Blocks.STRIPPED_ACACIA_LOG,
            Blocks.STRIPPED_ACACIA_WOOD,
            Blocks.ACACIA_PLANKS,
            Blocks.ACACIA_STAIRS,
            Blocks.ACACIA_SLAB,
            Blocks.ACACIA_FENCE,
            Blocks.ACACIA_FENCE_GATE,
            Blocks.ACACIA_DOOR,
            Blocks.ACACIA_TRAPDOOR,
            Blocks.ACACIA_BUTTON,
            Blocks.ACACIA_LEAVES,
            Blocks.ACACIA_SAPLING,
            Blocks.POTTED_ACACIA_SAPLING,
            Blocks.ACACIA_SIGN,
            Blocks.ACACIA_WALL_SIGN,
            Blocks.ACACIA_HANGING_SIGN,
            Blocks.ACACIA_WALL_HANGING_SIGN
    );
    public static final WoodType DARK_OAK = new WoodType(
            "dark_oak",
            Blocks.DARK_OAK_LOG,
            Blocks.DARK_OAK_WOOD,
            Blocks.STRIPPED_DARK_OAK_LOG,
            Blocks.STRIPPED_DARK_OAK_WOOD,
            Blocks.DARK_OAK_PLANKS,
            Blocks.DARK_OAK_STAIRS,
            Blocks.DARK_OAK_SLAB,
            Blocks.DARK_OAK_FENCE,
            Blocks.DARK_OAK_FENCE_GATE,
            Blocks.DARK_OAK_DOOR,
            Blocks.DARK_OAK_TRAPDOOR,
            Blocks.DARK_OAK_BUTTON,
            Blocks.DARK_OAK_LEAVES,
            Blocks.DARK_OAK_SAPLING,
            Blocks.POTTED_DARK_OAK_SAPLING,
            Blocks.DARK_OAK_SIGN,
            Blocks.DARK_OAK_WALL_SIGN,
            Blocks.DARK_OAK_HANGING_SIGN,
            Blocks.DARK_OAK_WALL_HANGING_SIGN
    );
    public static final WoodType MANGROVE = new WoodType(
            "mangrove",
            Blocks.MANGROVE_LOG,
            Blocks.MANGROVE_WOOD,
            Blocks.STRIPPED_MANGROVE_LOG,
            Blocks.STRIPPED_MANGROVE_WOOD,
            Blocks.MANGROVE_PLANKS,
            Blocks.MANGROVE_STAIRS,
            Blocks.MANGROVE_SLAB,
            Blocks.MANGROVE_FENCE,
            Blocks.MANGROVE_FENCE_GATE,
            Blocks.MANGROVE_DOOR,
            Blocks.MANGROVE_TRAPDOOR,
            Blocks.MANGROVE_BUTTON,
            Blocks.MANGROVE_LEAVES,
            Blocks.MANGROVE_PROPAGULE,
            Blocks.POTTED_MANGROVE_PROPAGULE,
            Blocks.MANGROVE_SIGN,
            Blocks.MANGROVE_WALL_SIGN,
            Blocks.MANGROVE_HANGING_SIGN,
            Blocks.MANGROVE_WALL_HANGING_SIGN
    );
    public static final WoodType BAMBOO = new WoodType(
            "bamboo",
            Blocks.BAMBOO_BLOCK,
            Blocks.BAMBOO_BLOCK,
            Blocks.STRIPPED_BAMBOO_BLOCK,
            Blocks.STRIPPED_BAMBOO_BLOCK,
            Blocks.BAMBOO_PLANKS,
            Blocks.BAMBOO_STAIRS,
            Blocks.BAMBOO_SLAB,
            Blocks.BAMBOO_FENCE,
            Blocks.BAMBOO_FENCE_GATE,
            Blocks.BAMBOO_DOOR,
            Blocks.BAMBOO_TRAPDOOR,
            Blocks.BAMBOO_BUTTON,
            Blocks.AIR,
            Blocks.BAMBOO_SAPLING,
            Blocks.POTTED_BAMBOO,
            Blocks.BAMBOO_SIGN,
            Blocks.BAMBOO_WALL_SIGN,
            Blocks.BAMBOO_HANGING_SIGN,
            Blocks.BAMBOO_WALL_HANGING_SIGN
    );
    public static final WoodType CHERRY = new WoodType(
            "cherry",
            Blocks.CHERRY_LOG,
            Blocks.CHERRY_WOOD,
            Blocks.STRIPPED_CHERRY_LOG,
            Blocks.STRIPPED_CHERRY_WOOD,
            Blocks.CHERRY_PLANKS,
            Blocks.CHERRY_STAIRS,
            Blocks.CHERRY_SLAB,
            Blocks.CHERRY_FENCE,
            Blocks.CHERRY_FENCE_GATE,
            Blocks.CHERRY_DOOR,
            Blocks.CHERRY_TRAPDOOR,
            Blocks.CHERRY_BUTTON,
            Blocks.CHERRY_LEAVES,
            Blocks.CHERRY_SAPLING,
            Blocks.POTTED_CHERRY_SAPLING,
            Blocks.CHERRY_SIGN,
            Blocks.CHERRY_WALL_SIGN,
            Blocks.CHERRY_HANGING_SIGN,
            Blocks.CHERRY_WALL_HANGING_SIGN
    );
    public static final WoodType CRIMSON = new WoodType(
            "crimson",
            Blocks.CRIMSON_STEM,
            Blocks.CRIMSON_HYPHAE,
            Blocks.STRIPPED_CRIMSON_STEM,
            Blocks.STRIPPED_CRIMSON_HYPHAE,
            Blocks.CRIMSON_PLANKS,
            Blocks.CRIMSON_STAIRS,
            Blocks.CRIMSON_SLAB,
            Blocks.CRIMSON_FENCE,
            Blocks.CRIMSON_FENCE_GATE,
            Blocks.CRIMSON_DOOR,
            Blocks.CRIMSON_TRAPDOOR,
            Blocks.CRIMSON_BUTTON,
            Blocks.NETHER_WART_BLOCK,
            Blocks.CRIMSON_FUNGUS,
            Blocks.POTTED_CRIMSON_FUNGUS,
            Blocks.CRIMSON_SIGN,
            Blocks.CRIMSON_WALL_SIGN,
            Blocks.CRIMSON_HANGING_SIGN,
            Blocks.CRIMSON_WALL_HANGING_SIGN
    );
    public static final WoodType WARPED = new WoodType(
            "warped",
            Blocks.WARPED_STEM,
            Blocks.WARPED_HYPHAE,
            Blocks.STRIPPED_WARPED_STEM,
            Blocks.STRIPPED_WARPED_HYPHAE,
            Blocks.WARPED_PLANKS,
            Blocks.WARPED_STAIRS,
            Blocks.WARPED_SLAB,
            Blocks.WARPED_FENCE,
            Blocks.WARPED_FENCE_GATE,
            Blocks.WARPED_DOOR,
            Blocks.WARPED_TRAPDOOR,
            Blocks.WARPED_BUTTON,
            Blocks.WARPED_WART_BLOCK,
            Blocks.WARPED_FUNGUS,
            Blocks.POTTED_WARPED_FUNGUS,
            Blocks.WARPED_SIGN,
            Blocks.WARPED_WALL_SIGN,
            Blocks.WARPED_HANGING_SIGN,
            Blocks.WARPED_WALL_HANGING_SIGN
    );
    public static final WoodType PALE_OAK = new WoodType(
            "pale_oak",
            Blocks.PALE_OAK_LOG,
            Blocks.PALE_OAK_WOOD,
            Blocks.STRIPPED_PALE_OAK_LOG,
            Blocks.STRIPPED_PALE_OAK_WOOD,
            Blocks.PALE_OAK_PLANKS,
            Blocks.PALE_OAK_STAIRS,
            Blocks.PALE_OAK_SLAB,
            Blocks.PALE_OAK_FENCE,
            Blocks.PALE_OAK_FENCE_GATE,
            Blocks.PALE_OAK_DOOR,
            Blocks.PALE_OAK_TRAPDOOR,
            Blocks.PALE_OAK_BUTTON,
            Blocks.PALE_OAK_LEAVES,
            Blocks.PALE_OAK_SAPLING,
            Blocks.POTTED_PALE_OAK_SAPLING,
            Blocks.PALE_OAK_SIGN,
            Blocks.PALE_OAK_WALL_SIGN,
            Blocks.PALE_OAK_HANGING_SIGN,
            Blocks.PALE_OAK_WALL_HANGING_SIGN
    );

    protected static final Map<Pair<WoodType, WoodType>, BlockTransformer> MAPPINGS = new HashMap<>();

    public static Pair<ResourceLocation, BlockTransformer> fullTransformer(WoodType from, WoodType to) {
        return Pair.of(transformName(from, to), transform(from, to));
    }

    public static BlockTransformer transform(WoodType from, WoodType to) {
        Pair<WoodType, WoodType> pair = Pair.of(from, to);
        BlockTransformer result = MAPPINGS.get(pair);

        if (result == null) {

            List<BlockTransformerData> data = new ArrayList<>();

            data.add(BuiltInTransformers.direct(from.log, to.log));
            data.add(BuiltInTransformers.direct(from.wood, to.wood));
            data.add(BuiltInTransformers.direct(from.strippedLog, to.strippedLog));
            data.add(BuiltInTransformers.direct(from.strippedWood, to.strippedWood));
            data.add(BuiltInTransformers.direct(from.planks, to.planks));
            data.add(BuiltInTransformers.direct(from.stairs, to.stairs));
            data.add(BuiltInTransformers.direct(from.slab, to.slab));
            data.add(BuiltInTransformers.direct(from.fence, to.fence));
            data.add(BuiltInTransformers.direct(from.fenceGate, to.fenceGate));
            data.add(BuiltInTransformers.direct(from.door, to.door));
            data.add(BuiltInTransformers.direct(from.trapdoor, to.trapdoor));
            data.add(BuiltInTransformers.direct(from.button, to.button));
            data.add(BuiltInTransformers.direct(from.leaves, to.leaves));
            data.add(BuiltInTransformers.direct(from.sapling, to.sapling));
            data.add(BuiltInTransformers.direct(from.pottedSapling, to.pottedSapling));
            data.add(BuiltInTransformers.direct(from.sign, to.sign));
            data.add(BuiltInTransformers.direct(from.wallSign, to.wallSign));
            data.add(BuiltInTransformers.direct(from.hangingSign, to.hangingSign));
            data.add(BuiltInTransformers.direct(from.wallHangingSign, to.wallHangingSign));

            result = new BlockTransformer(data, transformName(from, to));
            MAPPINGS.put(pair, result);
        }
        return result;
    }

    public static ResourceLocation transformName(WoodType from, WoodType to) {
        return BuiltInTransformers.name("from_" + from.name + "_to_" + to.name);
    }

}
