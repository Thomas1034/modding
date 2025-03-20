package com.startraveler.verdant.data.definitions;

import com.startraveler.verdant.registry.BlockRegistry;
import net.minecraft.data.BlockFamily;

public class VerdantBlockFamilies {

    public static final BlockFamily EARTH_BRICKS = new BlockFamily.Builder(BlockRegistry.EARTH_BRICKS.get()).slab(
                    BlockRegistry.EARTH_BRICK_SLAB.get())
            .stairs(BlockRegistry.EARTH_BRICK_STAIRS.get())
            .wall(BlockRegistry.EARTH_BRICK_WALL.get())
            .getFamily();
}
