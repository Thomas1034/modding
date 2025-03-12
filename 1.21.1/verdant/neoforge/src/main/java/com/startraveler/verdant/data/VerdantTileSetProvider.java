package com.startraveler.verdant.data;

import com.mojang.datafixers.util.Pair;
import com.startraveler.rootbound.tiling.data.TileSet;
import com.startraveler.verdant.Constants;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

import java.util.List;

public class VerdantTileSetProvider {

    public static void register(BootstrapContext<TileSet> bootstrap) {
        bootstrap.register(
                key("test_set"), new TileSet(
                        name("test_set"), 1, List.of(), List.of(
                        new Pair<>(name("fallback_tile"), 1),
                        new Pair<>(name("air_tile"), 1),
                        new Pair<>(name("dirt_tile"), 1),
                        new Pair<>(name("trees_tile"), 4),
                        new Pair<>(name("straight_tile"), 5),
                        new Pair<>(name("tee_tile"), 2),
                        new Pair<>(name("bend_tile"), 3),
                        new Pair<>(name("cross_tile"), 1),
                        new Pair<>(name("fort_tile"), 1),
                        new Pair<>(name("house_tile"), 1)
                )
                )
        );
    }

    public static ResourceKey<TileSet> key(String location) {
        return ResourceKey.create(TileSet.KEY, name(location));
    }

    public static ResourceLocation name(String location) {
        return ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, location);
    }
}
