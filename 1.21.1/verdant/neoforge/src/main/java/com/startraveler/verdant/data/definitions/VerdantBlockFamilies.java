package com.startraveler.verdant.data.definitions;

import com.startraveler.verdant.registry.BlockRegistry;
import net.minecraft.data.BlockFamily;

public class VerdantBlockFamilies {

    public static final BlockFamily EARTH_BRICKS = new BlockFamily.Builder(BlockRegistry.EARTH_BRICKS.get()).slab(
                    BlockRegistry.EARTH_BRICK_SLAB.get())
            .stairs(BlockRegistry.EARTH_BRICK_STAIRS.get())
            .wall(BlockRegistry.EARTH_BRICK_WALL.get())
            .getFamily();
    public static final BlockFamily VERDANT_RESIN_BRICKS = new BlockFamily.Builder(BlockRegistry.VERDANT_RESIN_BRICKS.get()).slab(
                    BlockRegistry.VERDANT_RESIN_BRICK_SLAB.get())
            .stairs(BlockRegistry.VERDANT_RESIN_BRICK_STAIRS.get())
            .wall(BlockRegistry.VERDANT_RESIN_BRICK_WALL.get())
            .chiseled(BlockRegistry.CHISELED_VERDANT_RESIN_BRICKS.get())
            .getFamily();
}
