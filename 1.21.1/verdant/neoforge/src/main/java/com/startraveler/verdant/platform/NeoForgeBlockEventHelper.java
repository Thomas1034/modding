package com.startraveler.verdant.platform;

import com.startraveler.verdant.platform.services.IBlockEventHelper;
import com.startraveler.verdant.util.PentaConsumer;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.block.state.BlockState;

public class NeoForgeBlockEventHelper implements IBlockEventHelper {
    @Override
    public boolean fire(ServerLevel level, GameType gameType, ServerPlayer player, BlockPos pos, BlockState state, PentaConsumer<ServerLevel, GameType, ServerPlayer, BlockPos, BlockState> consumer) {
        if (!net.neoforged.neoforge.common.CommonHooks.fireBlockBreak(level, gameType, player, pos, state)
                .isCanceled()) {
            consumer.accept(level, gameType, player, pos, state);
            return true;
        }
        return false;
    }
}
