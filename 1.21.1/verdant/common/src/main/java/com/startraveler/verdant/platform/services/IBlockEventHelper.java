package com.startraveler.verdant.platform.services;

import com.startraveler.verdant.util.PentaConsumer;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.block.state.BlockState;

public interface IBlockEventHelper {
    // Calls the runnable if the event is not canceled.
    boolean fire(ServerLevel level, GameType gameType, ServerPlayer player, BlockPos pos, BlockState state, PentaConsumer<ServerLevel, GameType, ServerPlayer, BlockPos, BlockState> consumer);
}
