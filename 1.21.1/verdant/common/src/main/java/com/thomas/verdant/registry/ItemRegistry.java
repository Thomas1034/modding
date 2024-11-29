package com.thomas.verdant.registry;

import net.minecraft.world.item.Item;

import java.util.function.Supplier;

public class ItemRegistry {

    public static void init() {}


    public static final Supplier<Item> ROASTED_COFFEE = registerItem("roasted_coffee",
            () -> new Item(getItemProperties()));

    public static <T extends Item> Supplier<T> registerItem(String name, Supplier<T> item) {
        VerdantRegistryHelpers.ITEM.add(name, item);
        return item;
    }

    public static Item.Properties getItemProperties() {
        return new Item.Properties();
    }
}
