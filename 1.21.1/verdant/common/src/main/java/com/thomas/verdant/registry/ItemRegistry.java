package com.thomas.verdant.registry;

import com.thomas.verdant.Constants;
import com.thomas.verdant.registration.RegistrationProvider;
import com.thomas.verdant.registration.RegistryObject;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;

public class ItemRegistry {

    public static void init() {}

    public static final RegistrationProvider<Item> ITEMS = RegistrationProvider.get(Registries.ITEM, Constants.MOD_ID);

    public static final RegistryObject<Item, Item> ROASTED_COFFEE = ITEMS.register("roasted_coffee",
            () -> new Item(getItemProperties()));

    public static Item.Properties getItemProperties() {
        return new Item.Properties();
    }
}
