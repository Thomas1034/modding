package com.startraveler.mansioneer.util.blocktransformer;

import net.minecraft.world.level.block.Block;

public class StoneType {
    public final String name;
    public final Block stoneBricks;
    public final Block crackedStoneBricks;
    public final Block stoneBrickStairs;
    public final Block stoneBrickSlabs;
    public final Block stoneBrickWalls;
    public final Block chiseledStoneBrick;
    public final Block mossyStoneBricks;
    public final Block mossyStoneBrickStairs;
    public final Block mossyStoneBrickSlabs;
    public final Block mossyStoneBrickWalls;

    public StoneType(String name, Block stoneBricks, Block crackedStoneBricks, Block stoneBrickStairs, Block stoneBrickSlabs, Block stoneBrickWalls, Block chiseledStoneBrick, Block mossyStoneBricks, Block mossyStoneBrickStairs, Block mossyStoneBrickSlabs, Block mossyStoneBrickWalls) {
        this.name = name;
        this.stoneBricks = stoneBricks;
        this.crackedStoneBricks = crackedStoneBricks;
        this.stoneBrickStairs = stoneBrickStairs;
        this.stoneBrickSlabs = stoneBrickSlabs;
        this.stoneBrickWalls = stoneBrickWalls;
        this.chiseledStoneBrick = chiseledStoneBrick;
        this.mossyStoneBricks = mossyStoneBricks;
        this.mossyStoneBrickStairs = mossyStoneBrickStairs;
        this.mossyStoneBrickSlabs = mossyStoneBrickSlabs;
        this.mossyStoneBrickWalls = mossyStoneBrickWalls;
    }
}