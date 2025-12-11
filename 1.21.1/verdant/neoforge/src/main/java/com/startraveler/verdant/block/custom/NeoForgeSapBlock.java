package com.startraveler.verdant.block.custom;

import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class NeoForgeSapBlock extends SapBlock {
    public NeoForgeSapBlock(Properties properties) {
        super(properties);
    }

    public boolean isStickyBlock(@NotNull BlockState state) {
        return true;
    }

    public boolean canStickTo(@NotNull BlockState state, @NotNull BlockState other) {
        return true;
    }
}
