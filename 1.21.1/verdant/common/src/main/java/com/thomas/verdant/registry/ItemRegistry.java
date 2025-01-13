package com.thomas.verdant.registry;

import com.thomas.verdant.Constants;
import com.thomas.verdant.item.custom.HeartOfTheForestItem;
import com.thomas.verdant.item.custom.RopeCoilItem;
import com.thomas.verdant.item.custom.RopeItem;
import com.thomas.verdant.registration.RegistrationProvider;
import com.thomas.verdant.registration.RegistryObject;
import com.thomas.verdant.registry.properties.ConsumablesList;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.BoneMealItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

import java.util.function.Supplier;

public class ItemRegistry {

    public static final RegistrationProvider<Item> ITEMS = RegistrationProvider.get(Registries.ITEM, Constants.MOD_ID);
    public static final RegistryObject<Item, Item> ROASTED_COFFEE = register(
            "roasted_coffee", () -> new Item(properties("roasted_coffee").food(
                    new FoodProperties.Builder().nutrition(1).saturationModifier(0.02F).alwaysEdible().build(),
                    ConsumablesList.COFFEE_BERRY
            ))
    );

    // Feed to goats?
    public static final RegistryObject<Item, Item> COFFEE_BERRIES = ITEMS.register(
            "coffee_berries", () -> new BlockItem(
                    BlockRegistry.COFFEE_CROP.get(), properties("coffee_berries").food(
                    new FoodProperties.Builder().nutrition(1).saturationModifier(0.02F).alwaysEdible().build(),
                    ConsumablesList.COFFEE_BERRY
            )
            )
    );

    public static final RegistryObject<Item, Item> ROTTEN_COMPOST = ITEMS.register(
            "rotten_compost",
            () -> new BoneMealItem(properties("rotten_compost").food(
                    new FoodProperties.Builder().nutrition(2).saturationModifier(0.02F).build(),
                    ConsumablesList.ROTTEN_COMPOST
            ))
    );

    public static final RegistryObject<Item, Item> THORN = register("thorn", () -> new Item(properties("thorn")));
    public static final RegistryObject<Item, Item> ROPE_COIL = register(
            "rope_coil",
            () -> new RopeCoilItem(properties("rope_coil").stacksTo(8)
                    .component(DataComponentRegistry.ROPE_COIL.get(), RopeCoilItem.DEFAULT_DATA_COMPONENT))
    );
    public static final RegistryObject<Item, Item> ROPE = register(
            "rope",
            () -> new RopeItem(BlockRegistry.ROPE.get(), properties("rope"))
    );
    public static final RegistryObject<Item, Item> HEART_OF_THE_FOREST = register("heart_of_the_forest", () -> new HeartOfTheForestItem(properties("heart_of_the_forest").rarity(
            Rarity.UNCOMMON)));


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
