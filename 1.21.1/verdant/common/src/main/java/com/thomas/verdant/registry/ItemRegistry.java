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
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.Consumables;

import java.util.function.Function;

public class ItemRegistry {

    public static final RegistrationProvider<Item> ITEMS = RegistrationProvider.get(Registries.ITEM, Constants.MOD_ID);
    public static final RegistryObject<Item, Item> ROASTED_COFFEE = register(
            "roasted_coffee", () -> new Item(properties("roasted_coffee").food(
                    new FoodProperties.Builder().nutrition(1).saturationModifier(0.02F).alwaysEdible().build(),
                    ConsumablesList.COFFEE_BERRY
            ))
    );

    // Feed to goats?
    public static final RegistryObject<Item, Item> COFFEE_BERRIES = register(
            "coffee_berries", () -> new BlockItem(
                    BlockRegistry.COFFEE_CROP.get(), properties("coffee_berries").food(
                    new FoodProperties.Builder().nutrition(1).saturationModifier(0.02F).alwaysEdible().build(),
                    ConsumablesList.COFFEE_BERRY
            )
            )
    );

    public static final RegistryObject<Item, Item> ROTTEN_COMPOST = register(
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
    public static final RegistryObject<Item, Item> CASSAVA_CUTTINGS = register(
            "cassava_cuttings",
            () -> new BlockItem(BlockRegistry.CASSAVA_CROP.get(), properties("cassava_cuttings"))
    );
    public static final RegistryObject<Item, Item> BITTER_CASSAVA_CUTTINGS = register(
            "bitter_cassava_cuttings",
            () -> new BlockItem(BlockRegistry.BITTER_CASSAVA_CROP.get(), properties("bitter_cassava_cuttings"))
    );
    public static final RegistryObject<Item, Item> CASSAVA = register(
            "cassava",
            () -> new Item(properties("cassava").stacksTo(64))
    );
    public static final RegistryObject<Item, Item> BITTER_CASSAVA = register(
            "bitter_cassava",
            () -> new Item(properties("bitter_cassava").stacksTo(64))
    );
    public static final RegistryObject<Item, Item> STARCH = register(
            "starch",
            () -> new Item(properties("starch").stacksTo(64))
    );
    public static final RegistryObject<Item, Item> BITTER_STARCH = register(
            "bitter_starch",
            () -> new Item(properties("bitter_starch").stacksTo(64))
    );
    public static final RegistryObject<Item, Item> BITTER_BREAD = register(
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
    public static final RegistryObject<Item, Item> GOLDEN_BREAD = register(
            "golden_bread", () -> new Item(properties("golden_bread").stacksTo(64).food(
                    (new FoodProperties.Builder()).nutrition(5).saturationModifier(0.6F).alwaysEdible().build(),
                    ConsumablesList.GOLDEN_BREAD
            ))
    );
    public static final RegistryObject<Item, Item> COOKED_CASSAVA = register(
            "cooked_cassava",
            (properties) -> new Item(properties.stacksTo(64).food(Foods.COOKED_CHICKEN))
    );
    public static final RegistryObject<Item, Item> GOLDEN_CASSAVA = register(
            "golden_cassava",
            (properties) -> new Item(properties.stacksTo(64))
    );
    public static final RegistryObject<Item, Item> COOKED_GOLDEN_CASSAVA = register(
            "cooked_golden_cassava",
            (properties) -> new Item(properties.stacksTo(64)
                    .food(
                            new FoodProperties.Builder().nutrition(8).saturationModifier(0.8F).build(),
                            ConsumablesList.COOKED_GOLDEN_CASSAVA
                    ))
    );
    public static final RegistryObject<Item, Item> SPARKLING_STARCH = register(
            "sparkling_starch",
            (properties) -> new Item(properties.stacksTo(64))
    );

    public static final RegistryObject<Item, Item> BAKED_UBE = register(
            "baked_ube",
            (properties) -> new Item(properties.food((new FoodProperties.Builder()).nutrition(5)
                    .saturationModifier(0.7F)
                    .build()))
    );

    public static final RegistryObject<Item, Item> UBE_CAKE = register(
            "ube_cake",
            (properties) -> new BlockItem(BlockRegistry.UBE_CAKE.get(), properties.stacksTo(1))
    );

    public static final RegistryObject<Item, Item> UBE_COOKIE = register(
            "ube_cookie",
            (properties) -> new Item(properties.food(new FoodProperties.Builder().nutrition(3)
                    .saturationModifier(0.2F)
                    .build()))
    );

    public static final RegistryObject<Item, Item> UBE = register(
            "ube", (properties) -> new BlockItem(
                    BlockRegistry.UBE_CROP.get(),
                    properties.food(Foods.POISONOUS_POTATO, Consumables.POISONOUS_POTATO)
            )

    );

    /**
     * LEATHER_HORSE_ARMOR = registerItem("leather_horse_armor", (p_381574_) -> new AnimalArmorItem(ArmorMaterials.LEATHER, BodyType.EQUESTRIAN, SoundEvents.HORSE_ARMOR, false, p_381574_), (new Item.Properties()).stacksTo(1));
     * LEATHER_HELMET = registerItem("leather_helmet", (Function)((p_370828_) -> new ArmorItem(ArmorMaterials.LEATHER, ArmorType.HELMET, p_370828_)));
     * LEATHER_CHESTPLATE = registerItem("leather_chestplate", (Function)((p_371038_) -> new ArmorItem(ArmorMaterials.LEATHER, ArmorType.CHESTPLATE, p_371038_)));
     * LEATHER_LEGGINGS = registerItem("leather_leggings", (Function)((p_370985_) -> new ArmorItem(ArmorMaterials.LEATHER, ArmorType.LEGGINGS, p_370985_)));
     * LEATHER_BOOTS = registerItem("leather_boots", (Function)((p_370904_) -> new ArmorItem(ArmorMaterials.LEATHER, ArmorType.BOOTS, p_370904_)));
     * WOODEN_SWORD = registerItem("wooden_sword", (Function)((p_370788_) -> new SwordItem(ToolMaterial.WOOD, 3.0F, -2.4F, p_370788_)));
     * WOODEN_SHOVEL = registerItem("wooden_shovel", (Function)((p_370807_) -> new ShovelItem(ToolMaterial.WOOD, 1.5F, -3.0F, p_370807_)));
     * WOODEN_PICKAXE = registerItem("wooden_pickaxe", (Function)((p_370925_) -> new PickaxeItem(ToolMaterial.WOOD, 1.0F, -2.8F, p_370925_)));
     * WOODEN_AXE = registerItem("wooden_axe", (Function)((p_370924_) -> new AxeItem(ToolMaterial.WOOD, 6.0F, -3.2F, p_370924_)));
     * WOODEN_HOE = registerItem("wooden_hoe", (Function)((p_371086_) -> new HoeItem(ToolMaterial.WOOD, 0.0F, -3.0F, p_371086_)));
     */


    public static void init() {
    }

    public static RegistryObject<Item, Item> register(String name, Function<Item.Properties, Item> supplier) {
        return ITEMS.register(name, () -> supplier.apply(properties(name)));
    }

    public static Item.Properties properties(String name) {
        return new Item.Properties().setId(ResourceKey.create(
                Registries.ITEM,
                ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, name)
        ));
    }


}
