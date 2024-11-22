package com.thomas.verdant.data;

import com.thomas.verdant.Constants;
import com.thomas.verdant.data.definitions.BlockTransformerDefinitions;
import com.thomas.verdant.registry.BlockTransformerRegistry;
import com.thomas.verdant.util.blocktransformer.BlockTransformer;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.data.PackOutput;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import org.jetbrains.annotations.NotNull;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class VerdantBlockTransformerProvider extends DatapackBuiltinEntriesProvider {

    public VerdantBlockTransformerProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, getRegistrySetBuilder(), Set.of(Constants.MOD_ID));
    }

    private static RegistrySetBuilder getRegistrySetBuilder() {
        return new RegistrySetBuilder().add(BlockTransformer.KEY, VerdantBlockTransformerProvider::register);
    }

    private static void register(BootstrapContext<BlockTransformer> bootstrap) {
        bootstrap.register(key(BlockTransformerRegistry.VERDANT_ROOTS), BlockTransformerDefinitions.verdantRoots());
        bootstrap.register(key(BlockTransformerRegistry.EROSION), BlockTransformerDefinitions.erosion());
        bootstrap.register(key(BlockTransformerRegistry.EROSION_WET), BlockTransformerDefinitions.erosionWet());

    }

    private static ResourceKey<BlockTransformer> key(ResourceLocation location) {
        return ResourceKey.create(BlockTransformer.KEY, location);
    }

    @Override
    @NotNull
    public String getName() {
        return "Block Transformers";
    }
}
