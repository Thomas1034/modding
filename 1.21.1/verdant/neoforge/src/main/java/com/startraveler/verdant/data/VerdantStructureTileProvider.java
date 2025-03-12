package com.startraveler.verdant.data;

import com.startraveler.rootbound.tiling.data.StructureTile;
import com.startraveler.rootbound.tiling.data.TileConnection;
import com.startraveler.verdant.Constants;
import net.minecraft.core.Direction;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

import java.util.Map;

public class VerdantStructureTileProvider {


    public static void register(BootstrapContext<StructureTile> bootstrap) {

        bootstrap.register(
                key("fallback_tile"), new StructureTile(
                        name("fallback_tile"), name("air"), Map.of(
                        Direction.NORTH,
                        TileConnection.EMPTY,
                        Direction.SOUTH,
                        TileConnection.EMPTY,
                        Direction.EAST,
                        TileConnection.EMPTY,
                        Direction.WEST,
                        TileConnection.EMPTY,
                        Direction.UP,
                        TileConnection.EMPTY,
                        Direction.DOWN,
                        TileConnection.EMPTY
                )
                )
        );

        bootstrap.register(
                key("dirt_tile"), new StructureTile(
                        name("dirt_tile"), name("dirt"), Map.of(
                        Direction.NORTH,
                        VerdantTileConnectionProvider.DIRT,
                        Direction.SOUTH,
                        VerdantTileConnectionProvider.DIRT,
                        Direction.EAST,
                        VerdantTileConnectionProvider.DIRT,
                        Direction.WEST,
                        VerdantTileConnectionProvider.DIRT,
                        Direction.UP,
                        VerdantTileConnectionProvider.DIRT,
                        Direction.DOWN,
                        VerdantTileConnectionProvider.DIRT
                )
                )
        );

        bootstrap.register(
                key("air_tile"), new StructureTile(
                        name("air_tile"), name("air"), Map.of(
                        Direction.NORTH,
                        VerdantTileConnectionProvider.AIR,
                        Direction.SOUTH,
                        VerdantTileConnectionProvider.AIR,
                        Direction.EAST,
                        VerdantTileConnectionProvider.AIR,
                        Direction.WEST,
                        VerdantTileConnectionProvider.AIR,
                        Direction.UP,
                        VerdantTileConnectionProvider.AIR,
                        Direction.DOWN,
                        VerdantTileConnectionProvider.AIR
                )
                )
        );

        bootstrap.register(
                key("trees_tile"), new StructureTile(
                        name("trees_tile"), name("trees"), Map.of(
                        Direction.NORTH,
                        VerdantTileConnectionProvider.GRASS,
                        Direction.SOUTH,
                        VerdantTileConnectionProvider.GRASS,
                        Direction.EAST,
                        VerdantTileConnectionProvider.GRASS,
                        Direction.WEST,
                        VerdantTileConnectionProvider.GRASS,
                        Direction.UP,
                        VerdantTileConnectionProvider.AIR,
                        Direction.DOWN,
                        VerdantTileConnectionProvider.DIRT
                )
                )
        );

        bootstrap.register(
                key("straight_tile"), new StructureTile(
                        name("straight_tile"), name("straight_road"), Map.of(
                        Direction.NORTH,
                        VerdantTileConnectionProvider.PATH,
                        Direction.SOUTH,
                        VerdantTileConnectionProvider.PATH,
                        Direction.EAST,
                        VerdantTileConnectionProvider.GRASS,
                        Direction.WEST,
                        VerdantTileConnectionProvider.GRASS,
                        Direction.UP,
                        VerdantTileConnectionProvider.AIR,
                        Direction.DOWN,
                        VerdantTileConnectionProvider.DIRT
                )
                )
        );

        bootstrap.register(
                key("tee_tile"), new StructureTile(
                        name("tee_tile"), name("crossroad_3"), Map.of(
                        Direction.NORTH,
                        VerdantTileConnectionProvider.PATH,
                        Direction.SOUTH,
                        VerdantTileConnectionProvider.GRASS,
                        Direction.EAST,
                        VerdantTileConnectionProvider.PATH,
                        Direction.WEST,
                        VerdantTileConnectionProvider.PATH,
                        Direction.UP,
                        VerdantTileConnectionProvider.AIR,
                        Direction.DOWN,
                        VerdantTileConnectionProvider.DIRT
                )
                )
        );

        bootstrap.register(
                key("bend_tile"), new StructureTile(
                        name("bend_tile"), name("corner"), Map.of(
                        Direction.NORTH,
                        VerdantTileConnectionProvider.PATH,
                        Direction.SOUTH,
                        VerdantTileConnectionProvider.GRASS,
                        Direction.EAST,
                        VerdantTileConnectionProvider.PATH,
                        Direction.WEST,
                        VerdantTileConnectionProvider.GRASS,
                        Direction.UP,
                        VerdantTileConnectionProvider.AIR,
                        Direction.DOWN,
                        VerdantTileConnectionProvider.DIRT
                )
                )
        );

        bootstrap.register(
                key("cross_tile"), new StructureTile(
                        name("cross_tile"), name("crossroad_4"), Map.of(
                        Direction.NORTH,
                        VerdantTileConnectionProvider.PATH,
                        Direction.SOUTH,
                        VerdantTileConnectionProvider.PATH,
                        Direction.EAST,
                        VerdantTileConnectionProvider.PATH,
                        Direction.WEST,
                        VerdantTileConnectionProvider.PATH,
                        Direction.UP,
                        VerdantTileConnectionProvider.AIR,
                        Direction.DOWN,
                        VerdantTileConnectionProvider.DIRT
                )
                )
        );

        bootstrap.register(
                key("fort_tile"), new StructureTile(
                        name("fort_tile"), name("fort"), Map.of(
                        Direction.NORTH,
                        TileConnection.EMPTY,
                        Direction.SOUTH,
                        TileConnection.EMPTY,
                        Direction.EAST,
                        TileConnection.EMPTY,
                        Direction.WEST,
                        TileConnection.EMPTY,
                        Direction.UP,
                        VerdantTileConnectionProvider.AIR,
                        Direction.DOWN,
                        VerdantTileConnectionProvider.DIRT
                )
                )
        );

        bootstrap.register(
                key("house_tile"), new StructureTile(
                        name("house_tile"), name("house"), Map.of(
                        Direction.NORTH,
                        VerdantTileConnectionProvider.PATH,
                        Direction.SOUTH,
                        VerdantTileConnectionProvider.GRASS,
                        Direction.EAST,
                        VerdantTileConnectionProvider.GRASS,
                        Direction.WEST,
                        VerdantTileConnectionProvider.GRASS,
                        Direction.UP,
                        VerdantTileConnectionProvider.AIR,
                        Direction.DOWN,
                        VerdantTileConnectionProvider.DIRT
                )
                )
        );

    }

    public static ResourceKey<StructureTile> key(String location) {
        return ResourceKey.create(StructureTile.KEY, name(location));
    }

    public static ResourceLocation name(String location) {
        return ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, location);
    }
}
