package com.thomas.verdant.registry;

import com.thomas.verdant.Constants;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class NeoForgeItemRegistry {

    public static final DeferredRegister.Items ITEMS =
            DeferredRegister.createItems(Constants.MOD_ID);

    public static void register(IEventBus eventBus) {
        VerdantRegistryHelpers.ITEM.register(ITEMS::register);
        ITEMS.register(eventBus);
    }
}
