package com.startraveler.verdant.platform.services;

import com.startraveler.verdant.block.custom.SapBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;

public interface ISapBlockProvider {
    SapBlock getSapBlock(BlockBehaviour.Properties properties);
}
