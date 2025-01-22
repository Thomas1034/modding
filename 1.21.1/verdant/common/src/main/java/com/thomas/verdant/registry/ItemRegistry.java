package com.thomas.verdant.registry;

import com.thomas.verdant.Constants;
import com.thomas.verdant.item.custom.*;
import com.thomas.verdant.registration.RegistrationProvider;
import com.thomas.verdant.registration.RegistryObject;
import com.thomas.verdant.registry.properties.ConsumablesList;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.food.Foods;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.BoneMealItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.component.Consumables;

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
    public static final RegistryObject<Item, Item> POISON_ARROW = register(
            "poison_arrow",
            () -> new PoisonArrowItem(properties("poison_arrow"))
    );
    public static final RegistryObject<Item, Item> HEART_OF_THE_FOREST = register(
            "heart_of_the_forest",
            () -> new HeartOfTheForestItem(properties("heart_of_the_forest").rarity(Rarity.UNCOMMON))
    );
    public static final RegistryObject<Item, Item> HEART_FRAGMENT = register(
            "heart_fragment",
            () -> new Item(properties("heart_fragment").rarity(Rarity.UNCOMMON))
    );
    public static final RegistryObject<Item, Item> CASSAVA_CUTTINGS = ITEMS.register(
            "cassava_cuttings",
            () -> new BlockItem(BlockRegistry.CASSAVA_CROP.get(), properties("cassava_cuttings"))
    );
    public static final RegistryObject<Item, Item> BITTER_CASSAVA_CUTTINGS = ITEMS.register(
            "bitter_cassava_cuttings",
            () -> new BlockItem(BlockRegistry.BITTER_CASSAVA_CROP.get(), properties("bitter_cassava_cuttings"))
    );
    public static final RegistryObject<Item, Item> CASSAVA = ITEMS.register(
            "cassava",
            () -> new Item(properties("cassava").stacksTo(64))
    );
    public static final RegistryObject<Item, Item> BITTER_CASSAVA = ITEMS.register(
            "bitter_cassava",
            () -> new Item(properties("bitter_cassava").stacksTo(64))
    );
    public static final RegistryObject<Item, Item> STARCH = ITEMS.register(
            "starch",
            () -> new Item(properties("starch").stacksTo(64))
    );
    public static final RegistryObject<Item, Item> BITTER_STARCH = ITEMS.register(
            "bitter_starch",
            () -> new Item(properties("bitter_starch").stacksTo(64))
    );
    public static final RegistryObject<Item, Item> BITTER_BREAD = ITEMS.register(
            "bitter_bread", () -> new EffectBoostFoodItem(
                    properties("bitter_bread").stacksTo(64).food(Foods.BREAD),
                    MobEffectRegistry.CASSAVA_POISONING::asHolder,
                    (i) -> new MobEffectInstance(
                            MobEffectRegistry.CASSAVA_POISONING.asHolder(),
                            (i + 2) * 20 * 60,
                            i + 1
                    )
            )
    );
    public static final RegistryObject<Item, Item> GOLDEN_BREAD = ITEMS.register(
            "golden_bread", () -> new Item(properties("golden_bread").stacksTo(64).food(
                    (new FoodProperties.Builder()).nutrition(5).saturationModifier(0.6F).alwaysEdible().build(),
                    ConsumablesList.GOLDEN_BREAD
            ))
    );
    public static final RegistryObject<Item, Item> COOKED_CASSAVA = ITEMS.register(
            "cooked_cassava",
            () -> new Item(properties("cooked_cassava").stacksTo(64).food(Foods.COOKED_CHICKEN))
    );
    public static final RegistryObject<Item, Item> GOLDEN_CASSAVA = ITEMS.register(
            "golden_cassava",
            () -> new Item(properties("golden_cassava").stacksTo(64))
    );
    public static final RegistryObject<Item, Item> COOKED_GOLDEN_CASSAVA = ITEMS.register(
            "cooked_golden_cassava",
            () -> new Item(properties("cooked_golden_cassava").stacksTo(64)
                    .food(
                            new FoodProperties.Builder().nutrition(8).saturationModifier(0.8F).build(),
                            ConsumablesList.COOKED_GOLDEN_CASSAVA
                    ))
    );
    public static final RegistryObject<Item, Item> SPARKLING_STARCH = ITEMS.register(
            "sparkling_starch",
            () -> new Item(properties("sparkling_starch").stacksTo(64))
    );

    public static final RegistryObject<Item, Item> BAKED_UBE = ITEMS.register(
            "baked_ube",
            () -> new Item(properties("baked_ube").food((new FoodProperties.Builder()).nutrition(5)
                    .saturationModifier(0.7F)
                    .build()))
    );

    public static final RegistryObject<Item, Item> UBE_CAKE = ITEMS.register(
            "ube_cake",
            () -> new BlockItem(BlockRegistry.UBE_CAKE.get(), properties("ube_cake").stacksTo(1))
    );

    public static final RegistryObject<Item, Item> UBE_COOKIE = ITEMS.register(
            "ube_cookie",
            () -> new Item(properties("ube_cookie").food(new FoodProperties.Builder().nutrition(3)
                    .saturationModifier(0.2F)
                    .build()))
    );

    public static final RegistryObject<Item, Item> UBE = ITEMS.register(
            "ube",
            () -> new BlockItem(
                    BlockRegistry.UBE_CROP.get(), properties("ube").food(
                    Foods.POISONOUS_POTATO,
                    Consumables.POISONOUS_POTATO
            )
            )
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
