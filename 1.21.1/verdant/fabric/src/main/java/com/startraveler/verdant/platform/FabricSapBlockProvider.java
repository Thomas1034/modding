package com.startraveler.verdant.platform;

import com.startraveler.verdant.block.custom.SapBlock;
import com.startraveler.verdant.platform.services.ISapBlockProvider;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class FabricSapBlockProvider implements ISapBlockProvider {
    @Override
    public SapBlock getSapBlock(BlockBehaviour.Properties properties) {
        return new SapBlock(properties);
    }
}
