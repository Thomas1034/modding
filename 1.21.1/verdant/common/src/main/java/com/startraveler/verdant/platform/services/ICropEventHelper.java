package com.startraveler.verdant.platform.services;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.state.BlockState;

public interface ICropEventHelper {
    void fireEvent(ServerLevel level, BlockPos pos, BlockState state, boolean shouldRun, Runnable onSuccess);
}
