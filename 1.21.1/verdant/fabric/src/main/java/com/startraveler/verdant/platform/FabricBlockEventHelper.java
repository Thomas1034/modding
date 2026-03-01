package com.startraveler.verdant.platform;

import com.startraveler.verdant.platform.services.IBlockEventHelper;
import com.startraveler.verdant.util.PentaConsumer;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class FabricBlockEventHelper implements IBlockEventHelper {
    @Override
    public boolean fire(ServerLevel level, GameType gameType, ServerPlayer player, BlockPos pos, BlockState state, PentaConsumer<ServerLevel, GameType, ServerPlayer, BlockPos, BlockState> consumer) {
        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (PlayerBlockBreakEvents.BEFORE.invoker()
                .beforeBlockBreak(level, player, pos, state, blockEntity)) {
            consumer.accept(level, gameType, player, pos, state);
            PlayerBlockBreakEvents.AFTER.invoker().afterBlockBreak(level, player, pos, state, blockEntity);
            return true;
        }
        PlayerBlockBreakEvents.CANCELED.invoker().onBlockBreakCanceled(level, player, pos, state, blockEntity);
        return false;
    }
}
