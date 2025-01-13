package com.startraveler.mansioneer.util.blocktransformer;

import net.minecraft.world.level.block.Block;

public class WoodType {
    public final String name;
    public final Block log;
    public final Block wood;
    public final Block strippedLog;
    public final Block strippedWood;
    public final Block planks;
    public final Block stairs;
    public final Block slab;
    public final Block fence;
    public final Block fenceGate;
    public final Block door;
    public final Block trapdoor;
    public final Block button;
    public final Block leaves;
    public final Block sapling;
    public final Block pottedSapling;
    public final Block sign;
    public final Block wallSign;
    public final Block hangingSign;
    public final Block wallHangingSign;

    public WoodType(String name, Block log, Block wood, Block strippedLog, Block strippedWood, Block planks, Block stairs, Block slab, Block fence, Block fenceGate, Block door, Block trapdoor, Block button, Block leaves, Block sapling, Block pottedSapling, Block sign, Block wallSign, Block hangingSign, Block wallHangingSign) {
        this.name = name;
        this.log = log;
        this.wood = wood;
        this.strippedLog = strippedLog;
        this.strippedWood = strippedWood;
        this.planks = planks;
        this.stairs = stairs;
        this.slab = slab;
        this.fence = fence;
        this.fenceGate = fenceGate;
        this.door = door;
        this.trapdoor = trapdoor;
        this.button = button;
        this.leaves = leaves;
        this.sapling = sapling;
        this.pottedSapling = pottedSapling;
        this.sign = sign;
        this.wallSign = wallSign;
        this.hangingSign = hangingSign;
        this.wallHangingSign = wallHangingSign;
    }
}
