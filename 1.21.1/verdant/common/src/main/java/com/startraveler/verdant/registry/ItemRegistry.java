/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * If you modify this file, please include a notice stating the changes:
 * Example: "Modified by [Your Name] on [Date] - [Short Description of Changes]"
 */
package com.startraveler.verdant.registry;

import com.startraveler.verdant.Constants;
import com.startraveler.verdant.block.custom.BombPileBlock;
import com.startraveler.verdant.item.component.DurabilityChanging;
import com.startraveler.verdant.item.component.VerdantFriendliness;
import com.startraveler.verdant.item.custom.*;
import com.startraveler.verdant.registration.RegistrationProvider;
import com.startraveler.verdant.registration.RegistryObject;
import com.startraveler.verdant.registry.properties.ConsumablesList;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.food.Foods;
import net.minecraft.world.item.*;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.component.BlockItemStateProperties;
import net.minecraft.world.item.component.Consumables;
import net.minecraft.world.item.component.UseCooldown;
import net.minecraft.world.item.component.UseRemainder;
import net.minecraft.world.item.equipment.ArmorType;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

public class ItemRegistry {

    public static final RegistrationProvider<Item> ITEMS = RegistrationProvider.get(Registries.ITEM, Constants.MOD_ID);

    // TODO add Dry Aloe Leaf
    // TODO add Young Aloe Leaf
    public static final RegistryObject<Item, Item> ALOE_LEAF = register(
            "aloe_leaf", (properties) -> new Item(properties.food(
                    new FoodProperties.Builder().nutrition(0).saturationModifier(0.02F).alwaysEdible().build(),
                    ConsumablesList.ALOE_LEAF
            ).component(
                    DataComponents.USE_COOLDOWN,
                    new UseCooldown(
                            5,
                            Optional.of(ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "aloe_leaf"))
                    )
            ))
    );
    public static final RegistryObject<Item, Item> YOUNG_ALOE_LEAF = register(
            "young_aloe_leaf", (properties) -> new Item(properties.food(
                    new FoodProperties.Builder().nutrition(0).saturationModifier(0.02F).alwaysEdible().build(),
                    ConsumablesList.YOUNG_ALOE_LEAF
            ).component(
                    DataComponents.USE_COOLDOWN,
                    new UseCooldown(
                            10,
                            Optional.of(ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "aloe_leaf"))
                    )
            ))
    );
    public static final RegistryObject<Item, Item> OLD_ALOE_LEAF = register(
            "old_aloe_leaf", (properties) -> new Item(properties.food(
                    new FoodProperties.Builder().nutrition(0).saturationModifier(0.02F).alwaysEdible().build(),
                    ConsumablesList.OLD_ALOE_LEAF
            ).component(
                    DataComponents.USE_COOLDOWN,
                    new UseCooldown(
                            15,
                            Optional.of(ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "aloe_leaf"))
                    )
            ))
    );

    public static final RegistryObject<Item, Item> ALOE_PUP = register(
            "aloe_pup",
            (properties) -> new BlockItem(BlockRegistry.SMALL_ALOE.get(), properties)
    );

    public static final RegistryObject<Item, Item> ROASTED_COFFEE = register(
            "roasted_coffee", (properties) -> new Item(properties.food(
                    new FoodProperties.Builder().nutrition(1).saturationModifier(0.02F).alwaysEdible().build(),
                    ConsumablesList.ROASTED_COFFEE
            ))
    );

    // Feed to goats?
    public static final RegistryObject<Item, Item> COFFEE_BERRIES = register(
            "coffee_berries", (properties) -> new BlockItem(
                    BlockRegistry.COFFEE_CROP.get(), properties.food(
                    new FoodProperties.Builder().nutrition(1).saturationModifier(0.02F).alwaysEdible().build(),
                    ConsumablesList.COFFEE_BERRY
            )
            )
    );

    public static final RegistryObject<Item, Item> ROTTEN_COMPOST = register(
            "rotten_compost",
            (properties) -> new BoneMealItem(properties.food(
                    new FoodProperties.Builder().nutrition(2).saturationModifier(0.02F).build(),
                    ConsumablesList.ROTTEN_COMPOST
            ))
    );

    public static final RegistryObject<Item, Item> RANCID_SLIME = register(
            "rancid_slime",
            (properties) -> new BoneMealItem(properties.food(
                    new FoodProperties.Builder().nutrition(2).saturationModifier(0.02F).build(),
                    ConsumablesList.RANCID_SLIME
            ))
    );

    public static final RegistryObject<Item, Item> THORN = register("thorn", Item::new);
    public static final RegistryObject<Item, Item> ROPE_COIL = register(
            "rope_coil",
            (properties) -> new RopeCoilItem(properties.stacksTo(8)
                    .component(DataComponentRegistry.ROPE_COIL.get(), RopeCoilItem.DEFAULT_DATA_COMPONENT))
    );
    public static final RegistryObject<Item, Item> ROPE = register(
            "rope",
            (properties) -> new RopeItem(BlockRegistry.ROPE.get(), properties)
    );
    public static final RegistryObject<Item, Item> POISON_ARROW = register("poison_arrow", PoisonArrowItem::new);
    public static final RegistryObject<Item, Item> HEART_OF_THE_FOREST = register(
            "heart_of_the_forest",
            (properties) -> new HeartOfTheForestItem(properties.rarity(Rarity.UNCOMMON))
    );
    public static final RegistryObject<Item, Item> HEART_FRAGMENT = register(
            "heart_fragment",
            (properties) -> new Item(properties.rarity(Rarity.UNCOMMON))
    );
    public static final RegistryObject<Item, Item> CASSAVA_CUTTINGS = register(
            "cassava_cuttings",
            (properties) -> new BlockItem(BlockRegistry.CASSAVA_CROP.get(), properties)
    );
    public static final RegistryObject<Item, Item> BITTER_CASSAVA_CUTTINGS = register(
            "bitter_cassava_cuttings",
            (properties) -> new BlockItem(BlockRegistry.BITTER_CASSAVA_CROP.get(), properties)
    );
    public static final RegistryObject<Item, Item> CASSAVA = register("cassava", Item::new);
    public static final RegistryObject<Item, Item> BITTER_CASSAVA = register("bitter_cassava", Item::new);
    public static final RegistryObject<Item, Item> STARCH = register("starch", Item::new);
    public static final RegistryObject<Item, Item> BITTER_STARCH = register("bitter_starch", Item::new);
    public static final RegistryObject<Item, Item> BITTER_BREAD = register(
            "bitter_bread", (properties) -> new EffectBoostFoodItem(
                    properties.food(Foods.BREAD),
                    MobEffectRegistry.CASSAVA_POISONING::asHolder,
                    (i) -> new MobEffectInstance(
                            MobEffectRegistry.CASSAVA_POISONING.asHolder(),
                            (i + 2) * 20 * 60,
                            i + 1
                    )
            )
    );
    public static final RegistryObject<Item, Item> GOLDEN_BREAD = register(
            "golden_bread", (properties) -> new Item(properties.food(
                    (new FoodProperties.Builder()).nutrition(5).saturationModifier(0.6F).alwaysEdible().build(),
                    ConsumablesList.GOLDEN_BREAD
            ))
    );
    public static final RegistryObject<Item, Item> COOKED_CASSAVA = register(
            "cooked_cassava",
            (properties) -> new Item(properties.food(Foods.COOKED_CHICKEN))
    );
    public static final RegistryObject<Item, Item> GOLDEN_CASSAVA = register("golden_cassava", Item::new);
    public static final RegistryObject<Item, Item> COOKED_GOLDEN_CASSAVA = register(
            "cooked_golden_cassava",
            (properties) -> new Item(properties.stacksTo(64).food(
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
            "ube",
            (properties) -> new BlockItem(
                    BlockRegistry.UBE_CROP.get(),
                    properties.food(Foods.POISONOUS_POTATO, Consumables.POISONOUS_POTATO)
            )

    );

    public static final RegistryObject<Item, Item> HEARTWOOD_HORSE_ARMOR = register(
            "heartwood_horse_armor", (properties) -> new AnimalArmorItem(
                    ArmorMaterialRegistry.HEARTWOOD,
                    AnimalArmorItem.BodyType.EQUESTRIAN,
                    SoundEvents.HORSE_ARMOR,
                    false,
                    properties.stacksTo(1)
                            .component(
                                    DataComponentRegistry.VERDANT_FRIENDLINESS.get(),
                                    VerdantFriendliness.HEARTWOOD_HORSE_ARMOR
                            )
            )
    );

    public static final RegistryObject<Item, Item> HEARTWOOD_HELMET = register(
            "heartwood_helmet", ((properties) -> new ArmorItem(
                    ArmorMaterialRegistry.HEARTWOOD,
                    ArmorType.HELMET,
                    properties.stacksTo(1)
                            .component(
                                    DataComponentRegistry.DURABILITY_CHANGING.get(),
                                    DurabilityChanging.HEARTWOOD_ARMOR
                            )
                            .component(
                                    DataComponentRegistry.VERDANT_FRIENDLINESS.get(),
                                    VerdantFriendliness.HEARTWOOD_ARMOR
                            )
            ))
    );

    public static final RegistryObject<Item, Item> HEARTWOOD_CHESTPLATE = register(
            "heartwood_chestplate", ((properties) -> new ArmorItem(
                    ArmorMaterialRegistry.HEARTWOOD,
                    ArmorType.CHESTPLATE,
                    properties.stacksTo(1)
                            .component(
                                    DataComponentRegistry.DURABILITY_CHANGING.get(),
                                    DurabilityChanging.HEARTWOOD_ARMOR
                            )
                            .component(
                                    DataComponentRegistry.VERDANT_FRIENDLINESS.get(),
                                    VerdantFriendliness.HEARTWOOD_ARMOR
                            )
            ))
    );

    public static final RegistryObject<Item, Item> HEARTWOOD_LEGGINGS = register(
            "heartwood_leggings", ((properties) -> new ArmorItem(
                    ArmorMaterialRegistry.HEARTWOOD,
                    ArmorType.LEGGINGS,
                    properties.stacksTo(1)
                            .component(
                                    DataComponentRegistry.DURABILITY_CHANGING.get(),
                                    DurabilityChanging.HEARTWOOD_ARMOR
                            )
                            .component(
                                    DataComponentRegistry.VERDANT_FRIENDLINESS.get(),
                                    VerdantFriendliness.HEARTWOOD_ARMOR
                            )
            ))
    );

    public static final RegistryObject<Item, Item> HEARTWOOD_BOOTS = register(
            "heartwood_boots", ((properties) -> new ArmorItem(
                    ArmorMaterialRegistry.HEARTWOOD,
                    ArmorType.BOOTS,
                    properties.stacksTo(1)
                            .component(
                                    DataComponentRegistry.DURABILITY_CHANGING.get(),
                                    DurabilityChanging.HEARTWOOD_ARMOR
                            )
                            .component(
                                    DataComponentRegistry.VERDANT_FRIENDLINESS.get(),
                                    VerdantFriendliness.HEARTWOOD_ARMOR
                            )
            ))
    );

    public static final RegistryObject<Item, Item> HEARTWOOD_SWORD = register(
            "heartwood_sword", ((properties) -> new SwordItem(
                    ToolMaterialRegistry.HEARTWOOD,
                    3.0F,
                    -2.4F,
                    properties.stacksTo(1)
                            .component(
                                    DataComponentRegistry.DURABILITY_CHANGING.get(),
                                    DurabilityChanging.HEARTWOOD_TOOLS
                            )
            ))
    );

    public static final RegistryObject<Item, Item> HEARTWOOD_SHOVEL = register(
            "heartwood_shovel", ((properties) -> new ShovelItem(
                    ToolMaterialRegistry.HEARTWOOD,
                    1.5F,
                    -3.0F,
                    properties.stacksTo(1)
                            .component(
                                    DataComponentRegistry.DURABILITY_CHANGING.get(),
                                    DurabilityChanging.HEARTWOOD_TOOLS
                            )
            ))
    );

    public static final RegistryObject<Item, Item> HEARTWOOD_PICKAXE = register(
            "heartwood_pickaxe", ((properties) -> new PickaxeItem(
                    ToolMaterialRegistry.HEARTWOOD,
                    1.0F,
                    -2.8F,
                    properties.stacksTo(1)
                            .component(
                                    DataComponentRegistry.DURABILITY_CHANGING.get(),
                                    DurabilityChanging.HEARTWOOD_TOOLS
                            )
            ))
    );

    public static final RegistryObject<Item, Item> HEARTWOOD_AXE = register(
            "heartwood_axe", ((properties) -> new AxeItem(
                    ToolMaterialRegistry.HEARTWOOD,
                    6.0F,
                    -3.2F,
                    properties.stacksTo(1)
                            .component(
                                    DataComponentRegistry.DURABILITY_CHANGING.get(),
                                    DurabilityChanging.HEARTWOOD_TOOLS
                            )
            ))
    );

    public static final RegistryObject<Item, Item> HEARTWOOD_HOE = register(
            "heartwood_hoe", ((properties) -> new HoeItem(
                    ToolMaterialRegistry.HEARTWOOD,
                    0.0F,
                    -3.0F,
                    properties.stacksTo(1)
                            .component(
                                    DataComponentRegistry.DURABILITY_CHANGING.get(),
                                    DurabilityChanging.HEARTWOOD_TOOLS
                            )
            ))
    );

    public static final RegistryObject<Item, Item> IMBUED_HEARTWOOD_HORSE_ARMOR = register(
            "imbued_heartwood_horse_armor", (properties) -> new AnimalArmorItem(
                    ArmorMaterialRegistry.IMBUED_HEARTWOOD,
                    AnimalArmorItem.BodyType.EQUESTRIAN,
                    SoundEvents.HORSE_ARMOR,
                    false,
                    properties.stacksTo(1).component(
                            DataComponentRegistry.VERDANT_FRIENDLINESS.get(),
                            VerdantFriendliness.IMBUED_HEARTWOOD_HORSE_ARMOR
                    )
            )
    );

    public static final RegistryObject<Item, Item> IMBUED_HEARTWOOD_HELMET = register(
            "imbued_heartwood_helmet", ((properties) -> new ArmorItem(
                    ArmorMaterialRegistry.IMBUED_HEARTWOOD, ArmorType.HELMET, properties.stacksTo(1)
                    .component(
                            DataComponentRegistry.DURABILITY_CHANGING.get(),
                            DurabilityChanging.IMBUED_HEARTWOOD_ARMOR
                    )
                    .component(
                            DataComponentRegistry.VERDANT_FRIENDLINESS.get(),
                            VerdantFriendliness.IMBUED_HEARTWOOD_ARMOR
                    )
            ))
    );

    public static final RegistryObject<Item, Item> IMBUED_HEARTWOOD_CHESTPLATE = register(
            "imbued_heartwood_chestplate", ((properties) -> new ArmorItem(
                    ArmorMaterialRegistry.IMBUED_HEARTWOOD, ArmorType.CHESTPLATE, properties.stacksTo(1)
                    .component(
                            DataComponentRegistry.DURABILITY_CHANGING.get(),
                            DurabilityChanging.IMBUED_HEARTWOOD_ARMOR
                    )
                    .component(
                            DataComponentRegistry.VERDANT_FRIENDLINESS.get(),
                            VerdantFriendliness.IMBUED_HEARTWOOD_ARMOR
                    )
            ))
    );

    public static final RegistryObject<Item, Item> IMBUED_HEARTWOOD_LEGGINGS = register(
            "imbued_heartwood_leggings", ((properties) -> new ArmorItem(
                    ArmorMaterialRegistry.IMBUED_HEARTWOOD, ArmorType.LEGGINGS, properties.stacksTo(1)
                    .component(
                            DataComponentRegistry.DURABILITY_CHANGING.get(),
                            DurabilityChanging.IMBUED_HEARTWOOD_ARMOR
                    )
                    .component(
                            DataComponentRegistry.VERDANT_FRIENDLINESS.get(),
                            VerdantFriendliness.IMBUED_HEARTWOOD_ARMOR
                    )
            ))
    );

    public static final RegistryObject<Item, Item> IMBUED_HEARTWOOD_BOOTS = register(
            "imbued_heartwood_boots", ((properties) -> new ArmorItem(
                    ArmorMaterialRegistry.IMBUED_HEARTWOOD, ArmorType.BOOTS, properties.stacksTo(1)
                    .component(
                            DataComponentRegistry.DURABILITY_CHANGING.get(),
                            DurabilityChanging.IMBUED_HEARTWOOD_ARMOR
                    )
                    .component(
                            DataComponentRegistry.VERDANT_FRIENDLINESS.get(),
                            VerdantFriendliness.IMBUED_HEARTWOOD_ARMOR
                    )
            ))
    );

    public static final RegistryObject<Item, Item> IMBUED_HEARTWOOD_SWORD = register(
            "imbued_heartwood_sword", ((properties) -> new SwordItem(
                    ToolMaterialRegistry.IMBUED_HEARTWOOD,
                    3.0F,
                    -2.4F,
                    properties.stacksTo(1)
                            .component(
                                    DataComponentRegistry.DURABILITY_CHANGING.get(),
                                    DurabilityChanging.IMBUED_HEARTWOOD_TOOLS
                            )
            ))
    );

    public static final RegistryObject<Item, Item> IMBUED_HEARTWOOD_SHOVEL = register(
            "imbued_heartwood_shovel", ((properties) -> new ShovelItem(
                    ToolMaterialRegistry.IMBUED_HEARTWOOD,
                    1.5F,
                    -3.0F,
                    properties.stacksTo(1)
                            .component(
                                    DataComponentRegistry.DURABILITY_CHANGING.get(),
                                    DurabilityChanging.IMBUED_HEARTWOOD_TOOLS
                            )
            ))
    );

    public static final RegistryObject<Item, Item> IMBUED_HEARTWOOD_PICKAXE = register(
            "imbued_heartwood_pickaxe", ((properties) -> new PickaxeItem(
                    ToolMaterialRegistry.IMBUED_HEARTWOOD,
                    1.0F,
                    -2.8F,
                    properties.stacksTo(1)
                            .component(
                                    DataComponentRegistry.DURABILITY_CHANGING.get(),
                                    DurabilityChanging.IMBUED_HEARTWOOD_TOOLS
                            )
            ))
    );

    public static final RegistryObject<Item, Item> IMBUED_HEARTWOOD_AXE = register(
            "imbued_heartwood_axe", ((properties) -> new AxeItem(
                    ToolMaterialRegistry.IMBUED_HEARTWOOD,
                    6.0F,
                    -3.2F,
                    properties.stacksTo(1)
                            .component(
                                    DataComponentRegistry.DURABILITY_CHANGING.get(),
                                    DurabilityChanging.IMBUED_HEARTWOOD_TOOLS
                            )
            ))
    );

    public static final RegistryObject<Item, Item> IMBUED_HEARTWOOD_HOE = register(
            "imbued_heartwood_hoe", ((properties) -> new HoeItem(
                    ToolMaterialRegistry.IMBUED_HEARTWOOD,
                    0.0F,
                    -3.0F,
                    properties.stacksTo(1)
                            .component(
                                    DataComponentRegistry.DURABILITY_CHANGING.get(),
                                    DurabilityChanging.IMBUED_HEARTWOOD_TOOLS
                            )
            ))
    );

    public static final RegistryObject<Item, Item> IMBUEMENT_UPGRADE_SMITHING_TEMPLATE = register("imbuement_upgrade_smithing_template",
            SmithingTemplateExtensions::createImbuementUpgradeTemplate
    );

    public static final RegistryObject<Item, Item> TOXIC_ASH = register(
            "toxic_ash",
            (properties) -> new ToxicAshItem(properties, 2, 1)
    );

    public static final RegistryObject<Item, Item> BUCKET_OF_TOXIC_ASH = register(
            "toxic_ash_bucket", (properties) -> new ToxicAshItem(
                    properties.stacksTo(1)
                            .component(DataComponents.USE_REMAINDER, new UseRemainder(new ItemStack(Items.BUCKET))),
                    8,
                    3
            )
    );

    public static final RegistryObject<Item, Item> BUCKET_OF_TOXIC_SOLUTION = register(
            "toxic_solution_bucket", (properties) -> new ToxicAshItem(
                    properties.stacksTo(1)
                            .component(DataComponents.USE_REMAINDER, new UseRemainder(new ItemStack(Items.BUCKET))),
                    32,
                    8
            )
    );

    /*
    public static final RegistryObject<Item, Item> HUNTING_SPEAR = register(
            "hunting_spear",
            (properties) -> new HuntingSpearItem(properties.stacksTo(16))
    );
    */


    public static final RegistryObject<Item, Item> DART = register("dart", DartItem::new);

    public static final RegistryObject<Item, Item> TIPPED_DART = register(
            "tipped_dart",
            properties -> new TippedDartItem(properties.component(DataComponents.POTION_CONTENTS, PotionContents.EMPTY))
    );

    public static final RegistryObject<Item, Item> ROOTED_SPAWN_EGG = register(
            "rooted_spawn_egg",
            properties -> new SpawnEggItem(EntityTypeRegistry.ROOTED.get(), properties)
    );

    public static final RegistryObject<Item, Item> TIMBERMITE_SPAWN_EGG = register(
            "timbermite_spawn_egg",
            properties -> new SpawnEggItem(EntityTypeRegistry.TIMBERMITE.get(), properties)
    );

    public static final RegistryObject<Item, Item> POISONER_SPAWN_EGG = register(
            "poisoner_spawn_egg",
            properties -> new SpawnEggItem(EntityTypeRegistry.POISONER.get(), properties)
    );

    // TODO
    public static final RegistryObject<Item, Item> BLOWGUN = register(
            "blowgun",
            properties -> new BlowgunItem(properties)
    );


    public static final RegistryObject<Item, Item> BLASTING_BLOSSOM_SPROUT = register(
            "blasting_blossom_sprout",
            (properties) -> new BlockItem(BlockRegistry.BLASTING_BLOSSOM.get(), properties)
    );
    public static final RegistryObject<Item, Item> STABLE_BLASTING_BLOOM = register(
            "stable_blasting_bloom",
            (properties) -> new BlockItem(BlockRegistry.BLASTING_BUNCH.get(), properties)
    );
    public static final RegistryObject<Item, Item> BLASTING_BLOOM = register(
            "blasting_bloom", (properties) -> new ThrowableBombItem(
                    properties.component(
                            DataComponents.BLOCK_STATE,
                            new BlockItemStateProperties(Map.of()).with(BombPileBlock.BOMBS, BombPileBlock.MIN_BOMBS)
                    ), () -> BlockRegistry.BLASTING_BUNCH.get().defaultBlockState()
            )
    );

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

    protected static abstract class SmithingTemplateExtensions {
        private static final Component IMBUEMENT_UPGRADE_APPLIES_TO;
        private static final Component IMBUEMENT_UPGRADE_INGREDIENTS;
        private static final Component IMBUEMENT_UPGRADE_BASE_SLOT_DESCRIPTION;
        private static final Component IMBUEMENT_UPGRADE_ADDITIONS_SLOT_DESCRIPTION;
        private static final ChatFormatting DESCRIPTION_FORMAT;

        private static final ResourceLocation EMPTY_SLOT_HELMET;
        private static final ResourceLocation EMPTY_SLOT_CHESTPLATE;
        private static final ResourceLocation EMPTY_SLOT_LEGGINGS;
        private static final ResourceLocation EMPTY_SLOT_BOOTS;
        private static final ResourceLocation EMPTY_SLOT_HOE;
        private static final ResourceLocation EMPTY_SLOT_AXE;
        private static final ResourceLocation EMPTY_SLOT_SWORD;
        private static final ResourceLocation EMPTY_SLOT_SHOVEL;
        private static final ResourceLocation EMPTY_SLOT_PICKAXE;
        private static final ResourceLocation EMPTY_SLOT_HEART_FRAGMENT;

        static {

            DESCRIPTION_FORMAT = ChatFormatting.BLUE;

            IMBUEMENT_UPGRADE_APPLIES_TO = Component.translatable(Util.makeDescriptionId(
                    "item",
                    ResourceLocation.withDefaultNamespace("smithing_template.imbuement_upgrade.applies_to")
            )).

                    withStyle(DESCRIPTION_FORMAT);

            IMBUEMENT_UPGRADE_INGREDIENTS = Component.translatable(Util.makeDescriptionId(
                    "item",
                    ResourceLocation.withDefaultNamespace("smithing_template.imbuement_upgrade.ingredients")
            )).

                    withStyle(DESCRIPTION_FORMAT);

            IMBUEMENT_UPGRADE_BASE_SLOT_DESCRIPTION = Component.translatable(Util.makeDescriptionId(
                    "item",
                    ResourceLocation.withDefaultNamespace("smithing_template.imbuement_upgrade.base_slot_description")
            ));
            IMBUEMENT_UPGRADE_ADDITIONS_SLOT_DESCRIPTION = Component.translatable(Util.makeDescriptionId(
                    "item",
                    ResourceLocation.withDefaultNamespace(
                            "smithing_template.imbuement_upgrade.additions_slot_description")
            ));

            EMPTY_SLOT_HELMET = ResourceLocation.withDefaultNamespace("container/slot/helmet");
            EMPTY_SLOT_CHESTPLATE = ResourceLocation.withDefaultNamespace("container/slot/chestplate");
            EMPTY_SLOT_LEGGINGS = ResourceLocation.withDefaultNamespace("container/slot/leggings");
            EMPTY_SLOT_BOOTS = ResourceLocation.withDefaultNamespace("container/slot/boots");
            EMPTY_SLOT_HOE = ResourceLocation.withDefaultNamespace("container/slot/hoe");
            EMPTY_SLOT_AXE = ResourceLocation.withDefaultNamespace("container/slot/axe");
            EMPTY_SLOT_SWORD = ResourceLocation.withDefaultNamespace("container/slot/sword");
            EMPTY_SLOT_SHOVEL = ResourceLocation.withDefaultNamespace("container/slot/shovel");
            EMPTY_SLOT_PICKAXE = ResourceLocation.withDefaultNamespace("container/slot/pickaxe");

            EMPTY_SLOT_HEART_FRAGMENT = ResourceLocation.fromNamespaceAndPath(
                    Constants.MOD_ID,
                    "container/slot/empty_slot_heart_fragment"
            );
        }

        public static SmithingTemplateItem createImbuementUpgradeTemplate(Item.Properties properties) {
            return new SmithingTemplateItem(
                    IMBUEMENT_UPGRADE_APPLIES_TO,
                    IMBUEMENT_UPGRADE_INGREDIENTS,
                    IMBUEMENT_UPGRADE_BASE_SLOT_DESCRIPTION,
                    IMBUEMENT_UPGRADE_ADDITIONS_SLOT_DESCRIPTION,
                    createImbuementUpgradeIconList(),
                    createImbuementUpgradeMaterialList(),
                    properties
            );
        }

        private static List<ResourceLocation> createImbuementUpgradeIconList() {
            return List.of(
                    EMPTY_SLOT_HELMET,
                    EMPTY_SLOT_SWORD,
                    EMPTY_SLOT_CHESTPLATE,
                    EMPTY_SLOT_PICKAXE,
                    EMPTY_SLOT_LEGGINGS,
                    EMPTY_SLOT_AXE,
                    EMPTY_SLOT_BOOTS,
                    EMPTY_SLOT_HOE,
                    EMPTY_SLOT_SHOVEL
            );
        }

        private static List<ResourceLocation> createImbuementUpgradeMaterialList() {
            return List.of(EMPTY_SLOT_HEART_FRAGMENT);
        }

    }


}

