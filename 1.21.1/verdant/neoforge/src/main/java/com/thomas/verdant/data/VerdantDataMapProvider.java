package com.thomas.verdant.data;

import com.thomas.verdant.registry.CompostablesRegistry;
import com.thomas.verdant.registry.FuelsRegistry;
import com.thomas.verdant.registry.WoodSets;
import com.thomas.verdant.woodset.WoodSet;
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
        for (WoodSet woodSet : WoodSets.WOOD_SETS) {
            generateFor(woodSet);
        }

        CompostablesRegistry.init(this::addCompostable);

        FuelsRegistry.init(this::addFurnaceFuel);
    }

    public void generateFor(WoodSet woodSet) {
        woodSet.registerFuels(this::addFurnaceFuel);
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
