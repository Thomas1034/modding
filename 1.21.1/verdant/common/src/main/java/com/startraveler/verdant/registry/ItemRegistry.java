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
import com.startraveler.verdant.item.component.RopeCoilData;
import com.startraveler.verdant.item.component.VerdantFriendliness;
import com.startraveler.verdant.item.custom.*;
import com.startraveler.verdant.registration.RegistrationProvider;
import com.startraveler.verdant.registration.RegistryObject;
import com.startraveler.verdant.registry.properties.ConsumablesList;
import com.startraveler.verdant.util.VerdantTags;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Util;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.food.Foods;
import net.minecraft.world.item.*;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.component.*;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.item.equipment.EquipmentAssets;
import net.minecraft.world.item.equipment.Equippable;
import net.minecraft.world.level.material.Fluids;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

public class ItemRegistry {

    public static final RegistrationProvider<Item> ITEMS = RegistrationProvider.get(Registries.ITEM, Constants.MOD_ID);

    public static final PiercingWeapon HEARTWOOD_SWORD_PIERCING = new PiercingWeapon(
            true,
            false,
            Optional.empty(),
            Optional.of(BuiltInRegistries.SOUND_EVENT.wrapAsHolder(SoundEvents.PLAYER_ATTACK_STRONG))
    );
    public static final RegistryObject<Item, Item> FRAGILE_FLASK = register(
            "fragile_flask",
            (properties -> new Item(properties.component(DataComponents.CONSUMABLE, ConsumablesList.FRAGILE_FLASK)))
    );
    public static final RegistryObject<Item, Item> ALOE_LEAF = register(
            "aloe_leaf", (properties) -> new Item(properties.food(
                    new FoodProperties.Builder().nutrition(0).saturationModifier(0.02F).alwaysEdible().build(),
                    ConsumablesList.ALOE_LEAF
            ).component(
                    DataComponents.USE_COOLDOWN,
                    new UseCooldown(5, Optional.of(Identifier.fromNamespaceAndPath(Constants.MOD_ID, "aloe_leaf")))
            ))
    );
    public static final RegistryObject<Item, Item> YOUNG_ALOE_LEAF = register(
            "young_aloe_leaf", (properties) -> new Item(properties.food(
                    new FoodProperties.Builder().nutrition(0).saturationModifier(0.02F).alwaysEdible().build(),
                    ConsumablesList.YOUNG_ALOE_LEAF
            ).component(
                    DataComponents.USE_COOLDOWN,
                    new UseCooldown(10, Optional.of(Identifier.fromNamespaceAndPath(Constants.MOD_ID, "aloe_leaf")))
            ))
    );
    public static final RegistryObject<Item, Item> OLD_ALOE_LEAF = register(
            "old_aloe_leaf", (properties) -> new Item(properties.food(
                    new FoodProperties.Builder().nutrition(0).saturationModifier(0.02F).alwaysEdible().build(),
                    ConsumablesList.OLD_ALOE_LEAF
            ).component(
                    DataComponents.USE_COOLDOWN,
                    new UseCooldown(15, Optional.of(Identifier.fromNamespaceAndPath(Constants.MOD_ID, "aloe_leaf")))
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
                    .component(DataComponentRegistry.ROPE_COIL.get(), RopeCoilData.DEFAULT))
    );
    public static final RegistryObject<Item, Item> TWISTED_ROPE_COIL = register(
            "twisted_rope_coil",
            (properties) -> new RopeCoilItem(properties.stacksTo(8)
                    .component(DataComponentRegistry.ROPE_COIL.get(), RopeCoilData.DEFAULT_TWISTED))
    );
    public static final RegistryObject<Item, RopeItem> ROPE = register(
            "rope",
            (properties) -> new RopeItem(BlockRegistry.ROPE.get(), properties)
    );
    public static final RegistryObject<Item, RopeItem> TWISTED_ROPE = register(
            "twisted_rope",
            (properties) -> new RopeItem(BlockRegistry.TWISTED_ROPE.get(), properties)
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
            "cooked_golden_cassava", (properties) -> new Item(properties.stacksTo(64).food(
                    new FoodProperties.Builder().nutrition(8).saturationModifier(0.8F).alwaysEdible().build(),
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
    public static final RegistryObject<Item, Item> MULCH_PILE = register(
            "mulch_pile",
            (properties) -> new FeaturePlacingItem(properties, FeatureSetRegistry.MULCH)
    );
    public static final RegistryObject<Item, Item> LARGE_MULCH_PILE = register(
            "large_mulch_pile",
            (properties) -> new FeaturePlacingItem(properties, FeatureSetRegistry.LARGE_MULCH)
    );
    public static final RegistryObject<Item, Item> MULCH_BUCKET = register(
            "mulch_bucket", (properties) -> new FeaturePlacingItem(
                    properties.durability(8)
                            .component(
                                    DataComponents.USE_REMAINDER,
                                    new UseRemainder(Items.BUCKET.getDefaultInstance())
                            ),
                    FeatureSetRegistry.LARGE_MULCH
            )
    );
    public static final RegistryObject<Item, Item> SACK = register(
            "sack",
            properties -> new BundleItem(properties.stacksTo(1)
                    .component(DataComponents.BUNDLE_CONTENTS, BundleContents.EMPTY))
    );
    public static final RegistryObject<Item, Item> HEARTWOOD_HORSE_ARMOR = register(
            "heartwood_horse_armor",
            (properties) -> new Item(properties.stacksTo(1)
                    .horseArmor(ArmorMaterialRegistry.HEARTWOOD)
                    .component(
                            DataComponentRegistry.VERDANT_FRIENDLINESS.get(),
                            VerdantFriendliness.HEARTWOOD_HORSE_ARMOR
                    ))
    );
    public static final RegistryObject<Item, Item> HEARTWOOD_HELMET = register(
            "heartwood_helmet", ((properties) -> new Item(

                    properties.stacksTo(1)
                            .humanoidArmor(ArmorMaterialRegistry.HEARTWOOD, ArmorType.HELMET)
                            .component(
                                    DataComponentRegistry.DURABILITY_CHANGING.get(),
                                    DurabilityChanging.HEARTWOOD_ARMOR
                            )
                            .component(
                                    DataComponentRegistry.VERDANT_FRIENDLINESS.get(),
                                    VerdantFriendliness.HEARTWOOD_ARMOR
                            )))
    );
    public static final RegistryObject<Item, Item> HEARTWOOD_CHESTPLATE = register(
            "heartwood_chestplate",
            ((properties) -> new Item(properties.stacksTo(1)
                    .humanoidArmor(ArmorMaterialRegistry.HEARTWOOD, ArmorType.CHESTPLATE)
                    .component(DataComponentRegistry.DURABILITY_CHANGING.get(), DurabilityChanging.HEARTWOOD_ARMOR)
                    .component(DataComponentRegistry.VERDANT_FRIENDLINESS.get(), VerdantFriendliness.HEARTWOOD_ARMOR)))
    );
    public static final RegistryObject<Item, Item> HEARTWOOD_LEGGINGS = register(
            "heartwood_leggings",
            ((properties) -> new Item(properties.stacksTo(1)
                    .humanoidArmor(ArmorMaterialRegistry.HEARTWOOD, ArmorType.LEGGINGS)
                    .component(DataComponentRegistry.DURABILITY_CHANGING.get(), DurabilityChanging.HEARTWOOD_ARMOR)
                    .component(DataComponentRegistry.VERDANT_FRIENDLINESS.get(), VerdantFriendliness.HEARTWOOD_ARMOR)))
    );
    public static final RegistryObject<Item, Item> HEARTWOOD_BOOTS = register(
            "heartwood_boots",
            ((properties) -> new Item(properties.stacksTo(1)
                    .humanoidArmor(ArmorMaterialRegistry.HEARTWOOD, ArmorType.BOOTS)
                    .component(DataComponentRegistry.DURABILITY_CHANGING.get(), DurabilityChanging.HEARTWOOD_ARMOR)
                    .component(DataComponentRegistry.VERDANT_FRIENDLINESS.get(), VerdantFriendliness.HEARTWOOD_ARMOR)))
    );
    public static final RegistryObject<Item, Item> HEARTWOOD_SPEAR = register(
            "heartwood_spear",
            ((properties) -> new Item(properties.stacksTo(1)
                    .spear(ToolMaterialRegistry.HEARTWOOD, 0.65F, 0.95F, 0.6F, 2.5F, 8.0F, 6.75F, 5.1F, 11.25F, 4.6F)
                    .component(DataComponentRegistry.DURABILITY_CHANGING.get(), DurabilityChanging.HEARTWOOD_TOOLS)))
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
            "heartwood_pickaxe",
            ((properties) -> new Item(properties.stacksTo(1)
                    .pickaxe(ToolMaterialRegistry.HEARTWOOD, 1.0F, -2.8F)
                    .component(DataComponentRegistry.DURABILITY_CHANGING.get(), DurabilityChanging.HEARTWOOD_TOOLS)))
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
            "imbued_heartwood_horse_armor",
            (properties) -> new Item(properties.stacksTo(1)
                    .horseArmor(ArmorMaterialRegistry.IMBUED_HEARTWOOD)
                    .component(
                            DataComponentRegistry.VERDANT_FRIENDLINESS.get(),
                            VerdantFriendliness.IMBUED_HEARTWOOD_HORSE_ARMOR
                    ))
    );
    public static final RegistryObject<Item, Item> IMBUED_HEARTWOOD_HELMET = register(
            "imbued_heartwood_helmet",
            ((properties) -> new Item(properties.stacksTo(1)
                    .humanoidArmor(ArmorMaterialRegistry.IMBUED_HEARTWOOD, ArmorType.HELMET)
                    .component(
                            DataComponentRegistry.DURABILITY_CHANGING.get(),
                            DurabilityChanging.IMBUED_HEARTWOOD_ARMOR
                    )
                    .component(
                            DataComponentRegistry.VERDANT_FRIENDLINESS.get(),
                            VerdantFriendliness.IMBUED_HEARTWOOD_ARMOR
                    )))
    );
    public static final RegistryObject<Item, Item> IMBUED_HEARTWOOD_CHESTPLATE = register(
            "imbued_heartwood_chestplate",
            ((properties) -> new Item(properties.stacksTo(1)
                    .humanoidArmor(ArmorMaterialRegistry.IMBUED_HEARTWOOD, ArmorType.CHESTPLATE)
                    .component(
                            DataComponentRegistry.DURABILITY_CHANGING.get(),
                            DurabilityChanging.IMBUED_HEARTWOOD_ARMOR
                    )
                    .component(
                            DataComponentRegistry.VERDANT_FRIENDLINESS.get(),
                            VerdantFriendliness.IMBUED_HEARTWOOD_ARMOR
                    )))
    );
    public static final RegistryObject<Item, Item> IMBUED_HEARTWOOD_LEGGINGS = register(
            "imbued_heartwood_leggings",
            ((properties) -> new Item(properties.stacksTo(1)
                    .humanoidArmor(ArmorMaterialRegistry.IMBUED_HEARTWOOD, ArmorType.LEGGINGS)
                    .component(
                            DataComponentRegistry.DURABILITY_CHANGING.get(),
                            DurabilityChanging.IMBUED_HEARTWOOD_ARMOR
                    )
                    .component(
                            DataComponentRegistry.VERDANT_FRIENDLINESS.get(),
                            VerdantFriendliness.IMBUED_HEARTWOOD_ARMOR
                    )))
    );
    public static final RegistryObject<Item, Item> IMBUED_HEARTWOOD_BOOTS = register(
            "imbued_heartwood_boots",
            ((properties) -> new Item(properties.stacksTo(1)
                    .humanoidArmor(ArmorMaterialRegistry.IMBUED_HEARTWOOD, ArmorType.BOOTS)
                    .component(
                            DataComponentRegistry.DURABILITY_CHANGING.get(),
                            DurabilityChanging.IMBUED_HEARTWOOD_ARMOR
                    )
                    .component(
                            DataComponentRegistry.VERDANT_FRIENDLINESS.get(),
                            VerdantFriendliness.IMBUED_HEARTWOOD_ARMOR
                    )))
    );
    public static final RegistryObject<Item, Item> IMBUED_HEARTWOOD_SPEAR = register(
            "imbued_heartwood_spear",
            ((properties) -> new Item(properties.stacksTo(1)
                    .spear(ToolMaterialRegistry.IMBUED_HEARTWOOD, 0.65F, 0.7F, 0.75F, 5.0F, 14.0F, 10.0F, 5.1F, 15.0F, 4.6F)
                    .component(
                            DataComponentRegistry.DURABILITY_CHANGING.get(),
                            DurabilityChanging.IMBUED_HEARTWOOD_TOOLS
                    )))
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
            "imbued_heartwood_pickaxe",
            ((properties) -> new Item(properties.stacksTo(1)
                    .pickaxe(ToolMaterialRegistry.IMBUED_HEARTWOOD, 1.0F, -2.8F)
                    .component(
                            DataComponentRegistry.DURABILITY_CHANGING.get(),
                            DurabilityChanging.IMBUED_HEARTWOOD_TOOLS
                    )))
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
    public static final RegistryObject<Item, Item> TOXIC_SOLUTION_BUCKET = register(
            "toxic_solution_bucket", (properties) -> new ToxicAshItem(
                    properties.stacksTo(1)
                            .component(DataComponents.USE_REMAINDER, new UseRemainder(new ItemStack(Items.BUCKET))),
                    32,
                    8
            )
    );
    public static final RegistryObject<Item, Item> DART = register(
            "dart",
            properties -> new DartItem(properties.component(DataComponents.POTION_DURATION_SCALE, 1F))
    );
    public static final RegistryObject<Item, Item> TIPPED_DART = register(
            "tipped_dart",
            properties -> new TippedDartItem(properties.component(DataComponents.POTION_CONTENTS, PotionContents.EMPTY))
    );
    public static final RegistryObject<Item, SpawnEggItem> ROOTED_SPAWN_EGG = registerSpawnEgg(EntityTypeRegistry.ROOTED);
    public static final RegistryObject<Item, SpawnEggItem> TIMBERMITE_SPAWN_EGG = registerSpawnEgg(EntityTypeRegistry.TIMBERMITE);
    public static final RegistryObject<Item, SpawnEggItem> SKULL_SPIDER_SPAWN_EGG = registerSpawnEgg(EntityTypeRegistry.SKULL_SPIDER);
    public static final RegistryObject<Item, SpawnEggItem> POISONER_SPAWN_EGG = registerSpawnEgg(EntityTypeRegistry.POISONER);
    public static final RegistryObject<Item, Item> OOZE_BUCKET = register(
            "ooze_bucket", properties -> new MobBucketItem(
                    EntityTypeRegistry.OOZE.get(),
                    Fluids.WATER,
                    SoundEvents.SLIME_SQUISH_SMALL,
                    properties.stacksTo(1).component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)
            )
    );
    public static final RegistryObject<Item, SpawnEggItem> OOZE_SPAWN_EGG = registerSpawnEgg(EntityTypeRegistry.OOZE);
    public static final RegistryObject<Item, Item> BLOWGUN = register(
            "blowgun",
            properties -> new BlowgunItem(properties.durability(256))
    );
    public static final RegistryObject<Item, Item> EARTHMOVER = register(
            "earthmover",
            properties -> new CubeMiningItem(properties.tool(
                            ToolMaterial.DIAMOND,
                            BlockTags.MINEABLE_WITH_SHOVEL,
                            -1.0f,
                            -1.0f,
                            0.0f
                    )
                    .component(DataComponentRegistry.MINING_CUBE_RADIUS.get(), 1))
    );
    public static final RegistryObject<Item, Item> COPPER_MACHETE = register(
            "copper_machete",
            properties -> new CubeMiningItem(
                    properties.tool(ToolMaterial.COPPER, VerdantTags.Blocks.MINEABLE_WITH_MACHETE, 1.0f, -1.4f, 0.0f)
                            .component(DataComponentRegistry.MINING_CUBE_RADIUS.get(), 1), true
            )
    );
    public static final RegistryObject<Item, Item> IRON_MACHETE = register(
            "iron_machete",
            properties -> new CubeMiningItem(
                    properties.tool(ToolMaterial.IRON, VerdantTags.Blocks.MINEABLE_WITH_MACHETE, 1.0f, -1.4f, 0.0f)
                            .component(DataComponentRegistry.MINING_CUBE_RADIUS.get(), 1), true
            )
    );
    public static final RegistryObject<Item, Item> DIAMOND_MACHETE = register(
            "diamond_machete",
            properties -> new CubeMiningItem(
                    properties.tool(ToolMaterial.DIAMOND, VerdantTags.Blocks.MINEABLE_WITH_MACHETE, 1.0f, -1.4f, 0.0f)
                            .component(DataComponentRegistry.MINING_CUBE_RADIUS.get(), 1), true
            )
    );
    public static final RegistryObject<Item, Item> NETHERITE_MACHETE = register(
            "netherite_machete", properties -> new CubeMiningItem(
                    properties.tool(ToolMaterial.NETHERITE, VerdantTags.Blocks.MINEABLE_WITH_MACHETE, 1.0f, -1.4f, 0.0f)
                            .component(DataComponentRegistry.MINING_CUBE_RADIUS.get(), 1), true
            )
    );
    public static final RegistryObject<Item, Item> BLASTING_BLOSSOM_SPROUT = register(
            "blasting_blossom_sprout",
            (properties) -> new BlockItem(BlockRegistry.BLASTING_BLOSSOM.get(), properties)
    );
    public static final RegistryObject<Item, Item> STABLE_BLASTING_BLOOM = register(
            "stable_blasting_bloom",
            (properties) -> new BlockItem(BlockRegistry.BLASTING_BUNCH.get(), properties)
    );
    public static final RegistryObject<Item, Item> METAL_BOMB = register(
            "metal_bomb",
            (properties) -> new BlockItem(BlockRegistry.METAL_BOMB_PILE.get(), properties)
    );
    public static final RegistryObject<Item, Item> TERRACOTTA_BOMB = register(
            "terracotta_bomb",
            (properties) -> new BlockItem(BlockRegistry.TERRACOTTA_BOMB_PILE.get(), properties)
    );
    public static final RegistryObject<Item, Item> BRAMBLE_HEAD = register(
            "bramble_head",
            (properties) -> new StandingAndWallBlockItem(
                    BlockRegistry.BRAMBLE_HEAD.get(),
                    BlockRegistry.BRAMBLE_WALL_HEAD.get(),
                    Direction.DOWN,
                    properties
            )
    );
    public static final RegistryObject<Item, Item> THORNY_HEARTWOOD_HORSE_ARMOR = register(
            "thorny_heartwood_horse_armor",
            (properties) -> new Item(properties.stacksTo(1)
                    .horseArmor(ArmorMaterialRegistry.THORNY_HEARTWOOD)
                    .component(
                            DataComponentRegistry.VERDANT_FRIENDLINESS.get(),
                            VerdantFriendliness.HEARTWOOD_HORSE_ARMOR
                    ))
    );
    public static final RegistryObject<Item, Item> THORNY_HEARTWOOD_HELMET = register(
            "thorny_heartwood_helmet",
            ((properties) -> new Item(properties.stacksTo(1)
                    .humanoidArmor(ArmorMaterialRegistry.THORNY_HEARTWOOD, ArmorType.HELMET)
                    .component(DataComponentRegistry.DURABILITY_CHANGING.get(), DurabilityChanging.HEARTWOOD_ARMOR)
                    .component(DataComponentRegistry.VERDANT_FRIENDLINESS.get(), VerdantFriendliness.HEARTWOOD_ARMOR)
                    .component(
                            DataComponentRegistry.EQUIPPABLE_SPIKES.get(),
                            armorSpikes(ArmorMaterialRegistry.THORNY_HEARTWOOD_THORNS, ArmorType.HELMET)
                    )))
    );
    public static final RegistryObject<Item, Item> THORNY_HEARTWOOD_CHESTPLATE = register(
            "thorny_heartwood_chestplate",
            ((properties) -> new Item(properties.stacksTo(1)
                    .humanoidArmor(ArmorMaterialRegistry.THORNY_HEARTWOOD, ArmorType.CHESTPLATE)
                    .component(DataComponentRegistry.DURABILITY_CHANGING.get(), DurabilityChanging.HEARTWOOD_ARMOR)
                    .component(DataComponentRegistry.VERDANT_FRIENDLINESS.get(), VerdantFriendliness.HEARTWOOD_ARMOR)
                    .component(
                            DataComponentRegistry.EQUIPPABLE_SPIKES.get(),
                            armorSpikes(ArmorMaterialRegistry.THORNY_HEARTWOOD_THORNS, ArmorType.CHESTPLATE)
                    )))
    );
    public static final RegistryObject<Item, Item> THORNY_HEARTWOOD_LEGGINGS = register(
            "thorny_heartwood_leggings",
            ((properties) -> new Item(properties.stacksTo(1)
                    .humanoidArmor(ArmorMaterialRegistry.THORNY_HEARTWOOD, ArmorType.LEGGINGS)
                    .component(DataComponentRegistry.DURABILITY_CHANGING.get(), DurabilityChanging.HEARTWOOD_ARMOR)
                    .component(DataComponentRegistry.VERDANT_FRIENDLINESS.get(), VerdantFriendliness.HEARTWOOD_ARMOR)
                    .component(
                            DataComponentRegistry.EQUIPPABLE_SPIKES.get(),
                            armorSpikes(ArmorMaterialRegistry.THORNY_HEARTWOOD_THORNS, ArmorType.LEGGINGS)
                    )))
    );
    public static final RegistryObject<Item, Item> THORNY_HEARTWOOD_BOOTS = register(
            "thorny_heartwood_boots",
            ((properties) -> new Item(properties.stacksTo(1)
                    .humanoidArmor(ArmorMaterialRegistry.THORNY_HEARTWOOD, ArmorType.BOOTS)
                    .component(DataComponentRegistry.DURABILITY_CHANGING.get(), DurabilityChanging.HEARTWOOD_ARMOR)
                    .component(DataComponentRegistry.VERDANT_FRIENDLINESS.get(), VerdantFriendliness.HEARTWOOD_ARMOR)
                    .component(
                            DataComponentRegistry.EQUIPPABLE_SPIKES.get(),
                            armorSpikes(ArmorMaterialRegistry.THORNY_HEARTWOOD_THORNS, ArmorType.BOOTS)
                    )))
    );
    public static final RegistryObject<Item, Item> THORNY_HEARTWOOD_SPEAR = register(
            "thorny_heartwood_spear", ((properties) -> new Item(properties.stacksTo(1)
                    .spear(
                            ToolMaterialRegistry.THORNY_HEARTWOOD,
                            0.65F,
                            0.7F,
                            0.75F,
                            5.0F,
                            14.0F,
                            10.0F,
                            5.1F,
                            15.0F,
                            4.6F
                    )
                    .component(DataComponentRegistry.DURABILITY_CHANGING.get(), DurabilityChanging.HEARTWOOD_TOOLS)))
    );
    public static final RegistryObject<Item, Item> THORNY_HEARTWOOD_SHOVEL = register(
            "thorny_heartwood_shovel", ((properties) -> new ShovelItem(
                    ToolMaterialRegistry.THORNY_HEARTWOOD,
                    1.5F,
                    -3.0F,
                    properties.stacksTo(1)
                            .component(
                                    DataComponentRegistry.DURABILITY_CHANGING.get(),
                                    DurabilityChanging.HEARTWOOD_TOOLS
                            )
            ))
    );
    public static final RegistryObject<Item, Item> THORNY_HEARTWOOD_PICKAXE = register(
            "thorny_heartwood_pickaxe",
            ((properties) -> new Item(properties.stacksTo(1)
                    .pickaxe(ToolMaterialRegistry.THORNY_HEARTWOOD, 1.0F, -2.8F)
                    .component(DataComponentRegistry.DURABILITY_CHANGING.get(), DurabilityChanging.HEARTWOOD_TOOLS)))
    );
    public static final RegistryObject<Item, Item> THORNY_HEARTWOOD_AXE = register(
            "thorny_heartwood_axe", ((properties) -> new AxeItem(
                    ToolMaterialRegistry.THORNY_HEARTWOOD,
                    6.0F,
                    -3.2F,
                    properties.stacksTo(1)
                            .component(
                                    DataComponentRegistry.DURABILITY_CHANGING.get(),
                                    DurabilityChanging.HEARTWOOD_TOOLS
                            )
            ))
    );
    public static final RegistryObject<Item, Item> THORNY_HEARTWOOD_HOE = register(
            "thorny_heartwood_hoe", ((properties) -> new HoeItem(
                    ToolMaterialRegistry.THORNY_HEARTWOOD,
                    0.0F,
                    -3.0F,
                    properties.stacksTo(1)
                            .component(
                                    DataComponentRegistry.DURABILITY_CHANGING.get(),
                                    DurabilityChanging.HEARTWOOD_TOOLS
                            )
            ))
    );
    public static final RegistryObject<Item, Item> THORNS_UPGRADE_SMITHING_TEMPLATE = register("thorns_upgrade_smithing_template",
            SmithingTemplateExtensions::createThornsUpgradeTemplate
    );
    public static final RegistryObject<Item, Item> MANGO = register(
            "mango",
            (properties) -> new Item(properties.food(new FoodProperties.Builder().nutrition(4)
                    .saturationModifier(0.3F)
                    .build()).component(DataComponents.CONSUMABLE, ConsumablesList.MANGO))
    );
    public static final RegistryObject<Item, Item> GOLDEN_MANGO = register(
            "golden_mango",
            (properties) -> new Item(properties.food(new FoodProperties.Builder().nutrition(4)
                    .saturationModifier(0.3F)
                    .alwaysEdible()
                    .build()).component(DataComponents.CONSUMABLE, ConsumablesList.GOLDEN_MANGO))
    );
    public static final RegistryObject<Item, Item> SAP_GLOB = register("sap_glob", Item::new);
    public static final RegistryObject<Item, Item> VERDANT_RESIN_CLUMP = register("verdant_resin_clump", Item::new);
    public static final RegistryObject<Item, Item> VERDANT_RESIN_BRICK = register("verdant_resin_brick", Item::new);
    public static final RegistryObject<Item, Item> JUICE_BOTTLE = register(
            "juice_bottle",
            (properties) -> new Item(properties.food((new FoodProperties.Builder()).nutrition(2)
                            .saturationModifier(0.1F)
                            .alwaysEdible()
                            .build())
                    .component(DataComponents.CONSUMABLE, ConsumablesList.JUICE_BOTTLE)
                    .component(DataComponents.USE_REMAINDER, new UseRemainder(Items.GLASS_BOTTLE.getDefaultInstance()))
                    .stacksTo(16))
    );
    public static final RegistryObject<Item, Item> NECTAR_BOTTLE = register(
            "nectar_bottle",
            (properties) -> new Item(properties.food(Foods.HONEY_BOTTLE)
                    .component(DataComponents.CONSUMABLE, ConsumablesList.NECTAR_BOTTLE)
                    .component(DataComponents.USE_REMAINDER, new UseRemainder(Items.GLASS_BOTTLE.getDefaultInstance()))
                    .stacksTo(16))
    );
    public static final RegistryObject<Item, Item> BALSAM = register("balsam", Item::new);
    public static final RegistryObject<Item, Item> BALM = register(
            "balm", (properties) -> new Item(properties.food(
                    new FoodProperties.Builder().nutrition(0).saturationModifier(0.02F).alwaysEdible().build(),
                    ConsumablesList.BALM
            ).component(
                    DataComponents.USE_COOLDOWN,
                    new UseCooldown(30, Optional.of(Identifier.fromNamespaceAndPath(Constants.MOD_ID, "balm")))
            ))
    );
    public static final RegistryObject<Item, Item> SAP_TORCH = register(
            "sap_torch",
            (properties) -> new StandingAndWallBlockItem(
                    BlockRegistry.SAP_TORCH.get(),
                    BlockRegistry.SAP_WALL_TORCH.get(),
                    Direction.DOWN,
                    properties
            )
    );
    public static final RegistryObject<Item, Item> IMBUED_HEARTWOOD_SWORD = register(
            "imbued_heartwood_sword",
            ((properties) -> new Item(properties.stacksTo(1)
                    .sword(ToolMaterialRegistry.IMBUED_HEARTWOOD, 3.0F, -2.4F)
                    .component(
                            DataComponentRegistry.DURABILITY_CHANGING.get(),
                            DurabilityChanging.IMBUED_HEARTWOOD_TOOLS
                    )
                    .component(DataComponents.PIERCING_WEAPON, HEARTWOOD_SWORD_PIERCING)))
    );
    public static final RegistryObject<Item, Item> HEARTWOOD_SWORD = register(
            "heartwood_sword",
            ((properties) -> new Item(properties.stacksTo(1)
                    .sword(ToolMaterialRegistry.HEARTWOOD, 3.0F, -2.4F)
                    .component(DataComponentRegistry.DURABILITY_CHANGING.get(), DurabilityChanging.HEARTWOOD_TOOLS)
                    .component(DataComponents.PIERCING_WEAPON, HEARTWOOD_SWORD_PIERCING)))
    );
    public static final RegistryObject<Item, Item> THORNY_HEARTWOOD_SWORD = register(
            "thorny_heartwood_sword",
            ((properties) -> new Item(properties.stacksTo(1)
                    .sword(ToolMaterialRegistry.THORNY_HEARTWOOD, 3.0F, -2.4F)
                    .component(DataComponentRegistry.DURABILITY_CHANGING.get(), DurabilityChanging.HEARTWOOD_TOOLS)
                    .component(DataComponents.PIERCING_WEAPON, HEARTWOOD_SWORD_PIERCING)))
    );
    private static final Identifier GRENADE_COOLDOWN_ID = Constants.id("grenade_cooldown");
    public static final RegistryObject<Item, ThrowableBombItem> BLASTING_BLOOM = register(
            "blasting_bloom", (properties) -> new ThrowableBombItem(
                    properties.component(
                            DataComponents.BLOCK_STATE,
                            new BlockItemStateProperties(Map.of()).with(BombPileBlock.BOMBS, BombPileBlock.MIN_BOMBS)
                    ).component(DataComponents.USE_COOLDOWN, new UseCooldown(1.0f, Optional.of(GRENADE_COOLDOWN_ID))),
                    () -> BlockRegistry.BLASTING_BUNCH.get().defaultBlockState()
            )
    );
    public static final RegistryObject<Item, ThrowableBombItem> TERRACOTTA_GRENADE = register(
            "terracotta_grenade", (properties) -> new ThrowableBombItem(
                    properties.component(
                                    DataComponents.BLOCK_STATE,
                                    new BlockItemStateProperties(Map.of()).with(BombPileBlock.BOMBS, BombPileBlock.MIN_BOMBS)
                            )
                            .component(
                                    DataComponents.USE_COOLDOWN,
                                    new UseCooldown(1.0f, Optional.of(GRENADE_COOLDOWN_ID))
                            )
                            .component(
                                    DataComponentRegistry.BOMB_TOSS_STRENGTH.get(),
                                    ThrowableBombItem.DEFAULT_PROJECTILE_SHOOT_POWER * 2
                            ),
                    () -> BlockRegistry.TERRACOTTA_BOMB_PILE.get().defaultBlockState(),
                    ThrowableBombItem.DEFAULT_PROJECTILE_SHOOT_POWER * 1.5f,
                    3 * (ThrowableBombItem.DEFAULT_PROJECTILE_FUSE / 4),
                    ThrowableBombItem.DEFAULT_BLAST_DAMAGE_MULTIPLIER * 1.50f,
                    ThrowableBombItem.DEFAULT_PROJECTILE_BLAST_POWER
            )
    );
    public static final RegistryObject<Item, ThrowableBombItem> METAL_GRENADE = register(
            "metal_grenade", (properties) -> new ThrowableBombItem(
                    properties.component(
                                    DataComponents.BLOCK_STATE,
                                    new BlockItemStateProperties(Map.of()).with(BombPileBlock.BOMBS, BombPileBlock.MIN_BOMBS)
                            )
                            .component(
                                    DataComponents.USE_COOLDOWN,
                                    new UseCooldown(1.0f, Optional.of(GRENADE_COOLDOWN_ID))
                            )
                            .component(
                                    DataComponentRegistry.BOMB_TOSS_STRENGTH.get(),
                                    ThrowableBombItem.DEFAULT_PROJECTILE_SHOOT_POWER * 2
                            ),
                    () -> BlockRegistry.METAL_BOMB_PILE.get().defaultBlockState(),
                    ThrowableBombItem.DEFAULT_PROJECTILE_SHOOT_POWER * 2.0f,
                    ThrowableBombItem.DEFAULT_PROJECTILE_FUSE / 2,
                    ThrowableBombItem.DEFAULT_BLAST_DAMAGE_MULTIPLIER * 2.00f,
                    ThrowableBombItem.DEFAULT_PROJECTILE_BLAST_POWER
            )
    );

    private static <T extends Entity> RegistryObject<Item, SpawnEggItem> registerSpawnEgg(RegistryObject<EntityType<?>, EntityType<T>> type) {
        return register(
                type.getId().withSuffix("_spawn_egg").getPath(),
                properties -> new SpawnEggItem(properties.spawnEgg(type.get()))

        );
    }

    public static void init() {
    }

    public static <T extends Item> RegistryObject<Item, T> register(String name, Function<Item.Properties, T> supplier) {
        return ITEMS.register(name, () -> supplier.apply(properties(name)));
    }

    public static Item.Properties properties(String name) {
        return new Item.Properties().setId(ResourceKey.create(
                Registries.ITEM,
                Identifier.fromNamespaceAndPath(Constants.MOD_ID, name)
        ));
    }

    public static Equippable armorSpikes(Identifier location, ArmorType type) {
        return Equippable.builder(type.getSlot())
                .setAsset(ResourceKey.create(EquipmentAssets.ROOT_ID, location))
                .build();
    }

    protected static abstract class SmithingTemplateExtensions {
        private static final Component IMBUEMENT_UPGRADE_APPLIES_TO;
        private static final Component IMBUEMENT_UPGRADE_INGREDIENTS;
        private static final Component IMBUEMENT_UPGRADE_BASE_SLOT_DESCRIPTION;
        private static final Component IMBUEMENT_UPGRADE_ADDITIONS_SLOT_DESCRIPTION;
        private static final Component THORNS_UPGRADE_APPLIES_TO;
        private static final Component THORNS_UPGRADE_INGREDIENTS;
        private static final Component THORNS_UPGRADE_BASE_SLOT_DESCRIPTION;
        private static final Component THORNS_UPGRADE_ADDITIONS_SLOT_DESCRIPTION;
        private static final ChatFormatting DESCRIPTION_FORMAT;

        private static final Identifier EMPTY_SLOT_HELMET;
        private static final Identifier EMPTY_SLOT_CHESTPLATE;
        private static final Identifier EMPTY_SLOT_LEGGINGS;
        private static final Identifier EMPTY_SLOT_BOOTS;
        private static final Identifier EMPTY_SLOT_HOE;
        private static final Identifier EMPTY_SLOT_AXE;
        private static final Identifier EMPTY_SLOT_SWORD;
        private static final Identifier EMPTY_SLOT_SHOVEL;
        private static final Identifier EMPTY_SLOT_PICKAXE;
        private static final Identifier EMPTY_SLOT_HEART_FRAGMENT;
        private static final Identifier EMPTY_SLOT_SPIKES;

        static {

            DESCRIPTION_FORMAT = ChatFormatting.BLUE;

            IMBUEMENT_UPGRADE_APPLIES_TO = Component.translatable(Util.makeDescriptionId(
                    "item",
                    Identifier.withDefaultNamespace("smithing_template.imbuement_upgrade.applies_to")
            )).

                    withStyle(DESCRIPTION_FORMAT);

            IMBUEMENT_UPGRADE_INGREDIENTS = Component.translatable(Util.makeDescriptionId(
                    "item",
                    Identifier.withDefaultNamespace("smithing_template.imbuement_upgrade.ingredients")
            )).

                    withStyle(DESCRIPTION_FORMAT);

            IMBUEMENT_UPGRADE_BASE_SLOT_DESCRIPTION = Component.translatable(Util.makeDescriptionId(
                    "item",
                    Identifier.withDefaultNamespace("smithing_template.imbuement_upgrade.base_slot_description")
            ));
            IMBUEMENT_UPGRADE_ADDITIONS_SLOT_DESCRIPTION = Component.translatable(Util.makeDescriptionId(
                    "item",
                    Identifier.withDefaultNamespace("smithing_template.imbuement_upgrade.additions_slot_description")
            ));

            THORNS_UPGRADE_APPLIES_TO = Component.translatable(Util.makeDescriptionId(
                    "item",
                    Identifier.withDefaultNamespace("smithing_template.thorns_upgrade.applies_to")
            )).

                    withStyle(DESCRIPTION_FORMAT);

            THORNS_UPGRADE_INGREDIENTS = Component.translatable(Util.makeDescriptionId(
                    "item",
                    Identifier.withDefaultNamespace("smithing_template.thorns_upgrade.ingredients")
            )).

                    withStyle(DESCRIPTION_FORMAT);

            THORNS_UPGRADE_BASE_SLOT_DESCRIPTION = Component.translatable(Util.makeDescriptionId(
                    "item",
                    Identifier.withDefaultNamespace("smithing_template.thorns_upgrade.base_slot_description")
            ));
            THORNS_UPGRADE_ADDITIONS_SLOT_DESCRIPTION = Component.translatable(Util.makeDescriptionId(
                    "item",
                    Identifier.withDefaultNamespace("smithing_template.thorns_upgrade.additions_slot_description")
            ));

            EMPTY_SLOT_HELMET = Identifier.withDefaultNamespace("container/slot/helmet");
            EMPTY_SLOT_CHESTPLATE = Identifier.withDefaultNamespace("container/slot/chestplate");
            EMPTY_SLOT_LEGGINGS = Identifier.withDefaultNamespace("container/slot/leggings");
            EMPTY_SLOT_BOOTS = Identifier.withDefaultNamespace("container/slot/boots");
            EMPTY_SLOT_HOE = Identifier.withDefaultNamespace("container/slot/hoe");
            EMPTY_SLOT_AXE = Identifier.withDefaultNamespace("container/slot/axe");
            EMPTY_SLOT_SWORD = Identifier.withDefaultNamespace("container/slot/sword");
            EMPTY_SLOT_SHOVEL = Identifier.withDefaultNamespace("container/slot/shovel");
            EMPTY_SLOT_PICKAXE = Identifier.withDefaultNamespace("container/slot/pickaxe");

            EMPTY_SLOT_HEART_FRAGMENT = Identifier.fromNamespaceAndPath(
                    Constants.MOD_ID,
                    "container/slot/empty_slot_heart_fragment"
            );
            EMPTY_SLOT_SPIKES = Identifier.fromNamespaceAndPath(Constants.MOD_ID, "container/slot/empty_slot_spikes");
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

        public static SmithingTemplateItem createThornsUpgradeTemplate(Item.Properties properties) {
            return new SmithingTemplateItem(
                    THORNS_UPGRADE_APPLIES_TO,
                    THORNS_UPGRADE_INGREDIENTS,
                    THORNS_UPGRADE_BASE_SLOT_DESCRIPTION,
                    THORNS_UPGRADE_ADDITIONS_SLOT_DESCRIPTION,
                    createThornsUpgradeIconList(),
                    createThornsUpgradeMaterialList(),
                    properties
            );
        }

        private static List<Identifier> createImbuementUpgradeIconList() {
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

        private static List<Identifier> createImbuementUpgradeMaterialList() {
            return List.of(EMPTY_SLOT_HEART_FRAGMENT);
        }

        private static List<Identifier> createThornsUpgradeIconList() {
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

        private static List<Identifier> createThornsUpgradeMaterialList() {
            return List.of(EMPTY_SLOT_SPIKES);
        }

    }

}

