package com.thomas.verdant.data;

import com.thomas.verdant.data.definitions.BlockTransformerDefinitions;
import com.thomas.verdant.registry.BlockTransformerRegistry;
import com.thomas.verdant.util.blocktransformer.BlockTransformer;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

public class VerdantBlockTransformerProvider {

    public static void register(BootstrapContext<BlockTransformer> bootstrap) {
        bootstrap.register(key(BlockTransformerRegistry.VERDANT_ROOTS), BlockTransformerDefinitions.verdantRoots());
        bootstrap.register(key(BlockTransformerRegistry.EROSION), BlockTransformerDefinitions.erosion());
        bootstrap.register(key(BlockTransformerRegistry.EROSION_WET), BlockTransformerDefinitions.erosionWet());
    }

    public static ResourceKey<BlockTransformer> key(ResourceLocation location) {
        return ResourceKey.create(BlockTransformer.KEY, location);
    }
}
