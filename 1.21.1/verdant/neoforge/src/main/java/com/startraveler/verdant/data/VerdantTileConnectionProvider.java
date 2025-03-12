package com.startraveler.verdant.data;

import com.startraveler.rootbound.tiling.data.TileConnection;
import com.startraveler.verdant.Constants;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

import java.util.List;

public class VerdantTileConnectionProvider {

    public static final ResourceLocation GRASS = name("grass");
    public static final ResourceLocation AIR = name("air");
    public static final ResourceLocation DIRT = name("dirt");
    public static final ResourceLocation PATH = name("path");

    public static void register(BootstrapContext<TileConnection> bootstrap) {
        bootstrap.register(key(GRASS), new TileConnection(GRASS, List.of(), List.of(GRASS)));
        bootstrap.register(key(AIR), new TileConnection(AIR, List.of(), List.of(AIR)));
        bootstrap.register(key(DIRT), new TileConnection(DIRT, List.of(), List.of(DIRT)));
        bootstrap.register(key(PATH), new TileConnection(PATH, List.of(), List.of(PATH)));
    }

    public static ResourceKey<TileConnection> key(String location) {
        return ResourceKey.create(TileConnection.KEY, name(location));
    }

    public static ResourceKey<TileConnection> key(ResourceLocation location) {
        return ResourceKey.create(TileConnection.KEY, location);
    }

    public static ResourceLocation name(String location) {
        return ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, location);
    }
}
