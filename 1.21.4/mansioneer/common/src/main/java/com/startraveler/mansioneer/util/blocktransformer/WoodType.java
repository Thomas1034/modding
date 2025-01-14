package com.startraveler.mansioneer.util.blocktransformer;

import net.minecraft.world.level.block.Block;

public record WoodType(String name,
        Block log,
        Block wood,
        Block strippedLog,
        Block strippedWood,
        Block planks,
        Block stairs,
        Block slab,
        Block fence,
        Block fenceGate,
        Block door,
        Block trapdoor,
        Block button,
        Block pressurePlate,
        Block leaves,
        Block sapling,
        Block pottedSapling,
        Block sign,
        Block wallSign,
        Block hangingSign,
        Block wallHangingSign) {
}
