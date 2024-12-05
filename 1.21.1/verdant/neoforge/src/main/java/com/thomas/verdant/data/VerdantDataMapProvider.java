package com.thomas.verdant.data;

import com.thomas.verdant.registry.properties.WoodSets;
import com.thomas.verdant.woodset.WoodSet;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.data.DataMapProvider;
import net.neoforged.neoforge.registries.datamaps.builtin.FurnaceFuel;
import net.neoforged.neoforge.registries.datamaps.builtin.NeoForgeDataMaps;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

public class VerdantDataMapProvider extends DataMapProvider {
    public VerdantDataMapProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(packOutput, lookupProvider);
    }

    @Override
    protected void gather(HolderLookup.Provider provider) {

        generateFor(WoodSets.STRANGLER);
    }

    @SuppressWarnings({"nullable", "deprecation"})
    public void generateFor(WoodSet woodSet) {
        BiConsumer<ItemLike, Integer> consumer = (itemLike, burnTime) -> this.builder(NeoForgeDataMaps.FURNACE_FUELS).add(itemLike.asItem().builtInRegistryHolder(), new FurnaceFuel(burnTime), false);
        woodSet.registerFuels(consumer);
    }
}
