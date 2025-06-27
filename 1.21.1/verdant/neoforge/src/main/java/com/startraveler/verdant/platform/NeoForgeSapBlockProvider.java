package com.startraveler.verdant.platform;

import com.startraveler.verdant.block.custom.NeoForgeSapBlock;
import com.startraveler.verdant.block.custom.SapBlock;
import com.startraveler.verdant.platform.services.ISapBlockProvider;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class NeoForgeSapBlockProvider implements ISapBlockProvider {
    @Override
    public SapBlock getSapBlock(BlockBehaviour.Properties properties) {
        return new NeoForgeSapBlock(properties);
    }
}
