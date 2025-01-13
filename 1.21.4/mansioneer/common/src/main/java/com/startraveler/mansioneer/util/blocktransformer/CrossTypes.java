package com.startraveler.mansioneer.util.blocktransformer;

import com.startraveler.mansioneer.data.blocktransformer.BlockTransformerData;
import it.unimi.dsi.fastutil.Pair;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CrossTypes {

    protected static final Map<Pair<WoodType, StoneType>, BlockTransformer> W2S_MAPPINGS = new HashMap<>();
    protected static final Map<Pair<StoneType, WoodType>, BlockTransformer> S2W_MAPPINGS = new HashMap<>();

    public static BlockTransformer transform(StoneType from, WoodType to) {
        Pair<StoneType, WoodType> pair = Pair.of(from, to);
        BlockTransformer result = S2W_MAPPINGS.get(pair);

        if (result == null) {

            List<BlockTransformerData> data = new ArrayList<>();

            data.add(BuiltInTransformers.direct(from.chiseledStoneBrick, to.wood));
            data.add(BuiltInTransformers.direct(from.crackedStoneBricks, to.planks));
            data.add(BuiltInTransformers.direct(from.stoneBricks, to.planks));
            data.add(BuiltInTransformers.direct(from.stoneBrickStairs, to.stairs));
            data.add(BuiltInTransformers.direct(from.stoneBrickSlabs, to.slab));
            data.add(BuiltInTransformers.direct(from.stoneBrickWalls, to.fence));
            data.add(BuiltInTransformers.direct(from.mossyStoneBricks, to.planks));
            data.add(BuiltInTransformers.direct(from.mossyStoneBrickStairs, to.stairs));
            data.add(BuiltInTransformers.direct(from.mossyStoneBrickSlabs, to.slab));
            data.add(BuiltInTransformers.direct(from.mossyStoneBrickWalls, to.fence));


            result = new BlockTransformer(data, transformName(from, to));
            S2W_MAPPINGS.put(pair, result);
        }
        return result;
    }

    public static ResourceLocation transformName(StoneType from, WoodType to) {
        return BuiltInTransformers.name("from_" + from.name + "_to_" + to.name);
    }

    public static BlockTransformer transform(WoodType from, StoneType to) {
        Pair<WoodType, StoneType> pair = Pair.of(from, to);
        BlockTransformer result = W2S_MAPPINGS.get(pair);

        if (result == null) {

            List<BlockTransformerData> data = new ArrayList<>();

            data.add(BuiltInTransformers.direct(from.log, to.chiseledStoneBrick));
            data.add(BuiltInTransformers.direct(from.wood, to.chiseledStoneBrick));
            data.add(BuiltInTransformers.direct(from.strippedLog, to.chiseledStoneBrick));
            data.add(BuiltInTransformers.direct(from.strippedWood, to.chiseledStoneBrick));
            data.add(BuiltInTransformers.direct(from.planks, to.stoneBricks));
            data.add(BuiltInTransformers.direct(from.stairs, to.stoneBrickStairs));
            data.add(BuiltInTransformers.direct(from.slab, to.stoneBrickSlabs));
            data.add(BuiltInTransformers.direct(from.fence, to.stoneBrickWalls));

            result = new BlockTransformer(data, transformName(from, to));
            W2S_MAPPINGS.put(pair, result);
        }
        return result;
    }

    public static ResourceLocation transformName(WoodType from, StoneType to) {
        return BuiltInTransformers.name("from_" + from.name + "_to_" + to.name);
    }
}
