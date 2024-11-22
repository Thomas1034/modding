package com.thomas.verdant.data;

import com.thomas.verdant.Constants;
import com.thomas.verdant.data.definitions.FeatureSetDefinitions;
import com.thomas.verdant.registry.FeatureSetRegistry;
import com.thomas.verdant.util.featureset.FeatureSet;
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

public class VerdantFeatureSetProvider extends DatapackBuiltinEntriesProvider {

    public VerdantFeatureSetProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, getRegistrySetBuilder(), Set.of(Constants.MOD_ID));
    }

    private static RegistrySetBuilder getRegistrySetBuilder() {
        return new RegistrySetBuilder().add(FeatureSet.KEY, VerdantFeatureSetProvider::register);
    }

    private static void register(BootstrapContext<FeatureSet> bootstrap) {
        bootstrap.register(key(FeatureSetRegistry.ABOVE_GROUND), FeatureSetDefinitions.aboveGround());
    }

    private static ResourceKey<FeatureSet> key(ResourceLocation location) {
        return ResourceKey.create(FeatureSet.KEY, location);
    }

    @Override
    @NotNull
    public String getName() {
        return "Feature Sets";
    }
}
