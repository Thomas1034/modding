package com.startraveler.verdant.block.custom;

import net.minecraft.world.level.block.state.BlockState;

public class NeoForgeSapBlock extends SapBlock {
    public NeoForgeSapBlock(Properties properties) {
        super(properties);
    }

    public boolean isStickyBlock(BlockState state) {
        return true;
    }

    public boolean canStickTo(BlockState state, BlockState other) {
        return true;
    }
}
