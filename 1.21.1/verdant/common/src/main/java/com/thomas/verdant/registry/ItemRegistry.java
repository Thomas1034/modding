package com.thomas.verdant.registry;

import com.thomas.verdant.Constants;
import com.thomas.verdant.item.custom.RopeCoilItem;
import com.thomas.verdant.registration.RegistrationProvider;
import com.thomas.verdant.registration.RegistryObject;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

import java.util.function.Supplier;

public class ItemRegistry {

    public static final RegistrationProvider<Item> ITEMS = RegistrationProvider.get(Registries.ITEM, Constants.MOD_ID);
    public static final RegistryObject<Item, Item> ROASTED_COFFEE = register(
            "roasted_coffee",
            () -> new Item(properties("roasted_coffee"))
    );
    public static final RegistryObject<Item, Item> THORN = register("thorn", () -> new Item(properties("thorn")));
    public static final RegistryObject<Item, Item> ROPE_COIL = register(
            "rope_coil",
            () -> new RopeCoilItem(properties("rope_coil").component(
                    DataComponentRegistry.ROPE_LENGTH.get(),
                    RopeCoilItem.DEFAULT_DATA_COMPONENT
            ))
    );

    public static void init() {
    }

    public static RegistryObject<Item, Item> register(String name, Supplier<Item> supplier) {
        return ITEMS.register(name, supplier);
    }

    public static Item.Properties properties(String name) {
        return new Item.Properties().setId(ResourceKey.create(
                Registries.ITEM,
                ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, name)
        ));
    }


}
