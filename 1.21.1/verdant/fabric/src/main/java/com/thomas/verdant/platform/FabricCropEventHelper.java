package com.thomas.verdant.platform;

import com.thomas.verdant.platform.services.ICropEventHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.state.BlockState;

public class FabricCropEventHelper implements ICropEventHelper {
    @Override
    public void fireEvent(ServerLevel level, BlockPos pos, BlockState state, boolean shouldRun, Runnable onSuccess) {
        if (shouldRun) {
            onSuccess.run();
        }
    }
}
