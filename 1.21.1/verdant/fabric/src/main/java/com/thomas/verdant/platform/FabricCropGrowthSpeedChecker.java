package com.thomas.verdant.platform;

import com.thomas.verdant.platform.services.ICropGrowthSpeedChecker;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;

public class FabricCropGrowthSpeedChecker implements ICropGrowthSpeedChecker {
    @Override
    public float getGrowthSpeed(BlockState state, BlockGetter level, BlockPos pos) {
        return CropBlock.getGrowthSpeed(state.getBlock(), level, pos);
    }
}
