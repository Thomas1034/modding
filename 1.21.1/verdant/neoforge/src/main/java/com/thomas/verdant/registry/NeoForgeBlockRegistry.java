package com.thomas.verdant.registry;

import com.thomas.verdant.Constants;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class NeoForgeBlockRegistry {

    public static final DeferredRegister.Blocks BLOCKS =
            DeferredRegister.createBlocks(Constants.MOD_ID);

    public static void register(IEventBus eventBus) {
        VerdantRegistryHelpers.BLOCK.register(BLOCKS::register);
        BLOCKS.register(eventBus);
    }
}
