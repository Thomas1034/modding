package com.startraveler.verdant.platform;

import com.startraveler.verdant.platform.services.ICropEventHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.state.BlockState;

public class NeoForgeCropEventHelper implements ICropEventHelper {
    @Override
    public void fireEvent(ServerLevel level, BlockPos pos, BlockState state, boolean shouldRun, Runnable onSuccess) {
        if (net.neoforged.neoforge.common.CommonHooks.canCropGrow(level, pos, state, shouldRun)) {
            onSuccess.run();
            net.neoforged.neoforge.common.CommonHooks.fireCropGrowPost(level, pos, state);
        }
    }
}
