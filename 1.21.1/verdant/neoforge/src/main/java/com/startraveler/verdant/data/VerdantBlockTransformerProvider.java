package com.startraveler.verdant.data;

import com.startraveler.verdant.data.definitions.BlockTransformerDefinitions;
import com.startraveler.verdant.registry.BlockTransformerRegistry;
import com.startraveler.verdant.util.blocktransformer.BlockTransformer;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

public class VerdantBlockTransformerProvider {

    public static void register(BootstrapContext<BlockTransformer> bootstrap) {
        bootstrap.register(key(BlockTransformerRegistry.VERDANT_ROOTS), BlockTransformerDefinitions.verdantRoots());
        bootstrap.register(key(BlockTransformerRegistry.EROSION), BlockTransformerDefinitions.erosion());
        bootstrap.register(key(BlockTransformerRegistry.EROSION_WET), BlockTransformerDefinitions.erosionWet());
        bootstrap.register(key(BlockTransformerRegistry.HOEING), BlockTransformerDefinitions.hoeing());
        bootstrap.register(key(BlockTransformerRegistry.TOXIC_ASH), BlockTransformerDefinitions.toxicAsh());
    }

    public static ResourceKey<BlockTransformer> key(ResourceLocation location) {
        return ResourceKey.create(BlockTransformer.KEY, location);
    }
}
