package com.startraveler.mansioneer.util.blocktransformer;

import net.minecraft.world.level.block.Block;

public record StoneType(String name,
        Block stoneBricks,
        Block crackedStoneBricks,
        Block stoneBrickStairs,
        Block stoneBrickSlabs,
        Block stoneBrickWalls,
        Block chiseledStoneBrick,
        Block mossyStoneBricks,
        Block mossyStoneBrickStairs,
        Block mossyStoneBrickSlabs,
        Block mossyStoneBrickWalls) {
}