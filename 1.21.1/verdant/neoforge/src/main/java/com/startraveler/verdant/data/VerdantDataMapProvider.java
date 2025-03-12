package com.startraveler.verdant.data;

import com.startraveler.verdant.registry.CompostablesRegistry;
import com.startraveler.verdant.registry.FuelsRegistry;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.data.DataMapProvider;
import net.neoforged.neoforge.registries.datamaps.builtin.Compostable;
import net.neoforged.neoforge.registries.datamaps.builtin.FurnaceFuel;
import net.neoforged.neoforge.registries.datamaps.builtin.NeoForgeDataMaps;

import java.util.concurrent.CompletableFuture;

public class VerdantDataMapProvider extends DataMapProvider {
    public VerdantDataMapProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(packOutput, lookupProvider);
    }

    @Override
    protected void gather(HolderLookup.Provider provider) {

        CompostablesRegistry.init(this::addCompostable);

        FuelsRegistry.init(this::addFurnaceFuel);
    }

    public void addCompostable(ItemLike item, float compostChance) {
        this.builder(NeoForgeDataMaps.COMPOSTABLES)
                .add(BuiltInRegistries.ITEM.wrapAsHolder(item.asItem()), new Compostable(compostChance, true), false);
    }

    public void addFurnaceFuel(ItemLike itemLike, int burnTime) {
        this.builder(NeoForgeDataMaps.FURNACE_FUELS)
                .add(BuiltInRegistries.ITEM.wrapAsHolder(itemLike.asItem()), new FurnaceFuel(burnTime), false);
    }
}
