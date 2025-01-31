package com.startraveler.verdant.platform.services;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;

public interface ICropGrowthSpeedChecker {
    float getGrowthSpeed(BlockState state, BlockGetter level, BlockPos pos);
}
