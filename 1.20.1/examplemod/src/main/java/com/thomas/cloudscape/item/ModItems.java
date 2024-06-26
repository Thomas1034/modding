package com.thomas.cloudscape.item;

import com.thomas.cloudscape.Cloudscape;
import com.thomas.cloudscape.block.ModBlocks;
import com.thomas.cloudscape.entity.ModEntityType;
import com.thomas.cloudscape.entity.custom.ModBoatEntity;
import com.thomas.cloudscape.item.custom.CitrineArmorItem;
import com.thomas.cloudscape.item.custom.ClearWeatherTotemItem;
import com.thomas.cloudscape.item.custom.CopperArmorItem;
import com.thomas.cloudscape.item.custom.CopperAxeItem;
import com.thomas.cloudscape.item.custom.CopperHoeItem;
import com.thomas.cloudscape.item.custom.CopperPickaxeItem;
import com.thomas.cloudscape.item.custom.CopperShovelItem;
import com.thomas.cloudscape.item.custom.CopperSpearItem;
import com.thomas.cloudscape.item.custom.CopperSwordItem;
import com.thomas.cloudscape.item.custom.FlamingArrowItem;
import com.thomas.cloudscape.item.custom.FlamingPineconeItem;
import com.thomas.cloudscape.item.custom.HailstoneItem;
import com.thomas.cloudscape.item.custom.ModBoatItem;
import com.thomas.cloudscape.item.custom.ModSmithingTemplateItem;
import com.thomas.cloudscape.item.custom.PineconeItem;
import com.thomas.cloudscape.item.custom.ReturningTotemItem;
import com.thomas.cloudscape.item.custom.SearchEyeItem;
import com.thomas.cloudscape.item.custom.SpearItem;
import com.thomas.cloudscape.item.custom.SpeedometerItem;
import com.thomas.cloudscape.item.custom.WindBagItem;
import com.thomas.cloudscape.item.custom.wings.AncientFeatherWingsItem;
import com.thomas.cloudscape.item.custom.wings.ArmoredWingsItem;
import com.thomas.cloudscape.item.custom.wings.CitrineArmoredWingsItem;
import com.thomas.cloudscape.item.custom.wings.CopperArmoredWingsItem;
import com.thomas.cloudscape.item.custom.wings.DragonflyWingsItem;
import com.thomas.cloudscape.item.custom.wings.FeatherWingsItem;
import com.thomas.cloudscape.item.custom.wings.SeaWingsItem;
import com.thomas.cloudscape.util.ModTags;

import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.food.Foods;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorItem.Type;
import net.minecraft.world.item.ArmorMaterials;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.HangingSignItem;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.SignItem;
import net.minecraft.world.item.SolidBucketItem;
import net.minecraft.world.item.StandingAndWallBlockItem;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tiers;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Cloudscape.MOD_ID);

	// Zircon and zircon shard
	public static final RegistryObject<Item> ZIRCON = ITEMS.register("zircon", () -> new Item(new Item.Properties()));

	public static final RegistryObject<Item> ZIRCON_SHARD = ITEMS.register("zircon_shard",
			() -> new Item(new Item.Properties()));

	// Zirconium items, tools, armor, and weapons
	// Zirconium items and armor
	public static final RegistryObject<Item> ZIRCONIUM_INGOT = ITEMS.register("zirconium_ingot",
			() -> new Item(new Item.Properties().stacksTo(64)));

	public static final RegistryObject<Item> RAW_ZIRCONIUM = ITEMS.register("raw_zirconium",
			() -> new Item(new Item.Properties().stacksTo(64).fireResistant()));

	public static final RegistryObject<Item> ZIRCONIUM_UPGRADE_SMITHING_TEMPLATE = ITEMS.register(
			"zirconium_upgrade_smithing_template", () -> ModSmithingTemplateItem.createZirconiumUpgradeTemplate());

	public static final RegistryObject<Item> ZIRCONIUM_HELMET = ITEMS.register("zirconium_helmet",
			() -> new ArmorItem(ModArmorMaterials.ZIRCONIUM, ArmorItem.Type.HELMET,
					new Item.Properties().fireResistant()));

	public static final RegistryObject<Item> ZIRCONIUM_CHESTPLATE = ITEMS.register("zirconium_chestplate",
			() -> new ArmorItem(ModArmorMaterials.ZIRCONIUM, ArmorItem.Type.CHESTPLATE,
					new Item.Properties().fireResistant()));

	public static final RegistryObject<Item> ZIRCONIUM_LEGGINGS = ITEMS.register("zirconium_leggings",
			() -> new ArmorItem(ModArmorMaterials.ZIRCONIUM, ArmorItem.Type.LEGGINGS,
					new Item.Properties().fireResistant()));

	public static final RegistryObject<Item> ZIRCONIUM_BOOTS = ITEMS.register("zirconium_boots",
			() -> new ArmorItem(ModArmorMaterials.ZIRCONIUM, ArmorItem.Type.BOOTS,
					new Item.Properties().fireResistant()));

	public static final RegistryObject<Item> ZIRCONIUM_SWORD = ITEMS.register("zirconium_sword",
			() -> new SwordItem(ModToolTiers.ZIRCONIUM, 3, -2.4F, new Item.Properties().fireResistant()));

	public static final RegistryObject<Item> ZIRCONIUM_PICKAXE = ITEMS.register("zirconium_pickaxe",
			() -> new PickaxeItem(ModToolTiers.ZIRCONIUM, 1, -1.0F, new Item.Properties().fireResistant()));

	public static final RegistryObject<Item> ZIRCONIUM_AXE = ITEMS.register("zirconium_axe",
			() -> new AxeItem(ModToolTiers.ZIRCONIUM, 6, -3.4F, new Item.Properties().fireResistant()));

	public static final RegistryObject<Item> ZIRCONIUM_SHOVEL = ITEMS.register("zirconium_shovel",
			() -> new ShovelItem(ModToolTiers.ZIRCONIUM, 1, -1.0F, new Item.Properties().fireResistant()));

	public static final RegistryObject<Item> ZIRCONIUM_HOE = ITEMS.register("zirconium_hoe",
			() -> new HoeItem(ModToolTiers.ZIRCONIUM, 0, -1.0F, new Item.Properties().fireResistant()));

	// Sculk items
	public static final RegistryObject<Item> VIGIL_EYE = ITEMS.register("vigil_eye",
			() -> new SearchEyeItem(new Item.Properties().stacksTo(16), ModTags.Structures.VIGIL_EYE_LOCATED));

	public static final RegistryObject<Item> ECHO_POWDER = ITEMS.register("echo_powder",
			() -> new Item(new Item.Properties()));

	// Pine cone items
	public static final RegistryObject<Item> PINE_CONE = ITEMS.register("pine_cone",
			() -> new PineconeItem(new Item.Properties(), 50));

	public static final RegistryObject<Item> FLAMING_PINE_CONE = ITEMS.register("flaming_pine_cone",
			() -> new FlamingPineconeItem(new Item.Properties(), 50));

	public static final RegistryObject<Item> FLAMING_ARROW = ITEMS.register("flaming_arrow",
			() -> new FlamingArrowItem(new Item.Properties()));

	// Totems
	public static final RegistryObject<Item> TOTEM_OF_RETURNING = ITEMS.register("totem_of_returning",
			() -> new ReturningTotemItem(new Item.Properties().stacksTo(1)));

	public static final RegistryObject<Item> CLEAR_WEATHER_TOTEM = ITEMS.register("clear_weather_totem",
			() -> new ClearWeatherTotemItem(new Item.Properties()));

	// Copper tools, armor, and weapons
	public static final RegistryObject<Item> COPPER_SWORD = ITEMS.register("copper_sword",
			() -> new CopperSwordItem(ModToolTiers.COPPER, 3, -2.4F, new Item.Properties()));

	public static final RegistryObject<Item> COPPER_SHOVEL = ITEMS.register("copper_shovel",
			() -> new CopperShovelItem(ModToolTiers.COPPER, 1.2F, -3.0F, new Item.Properties()));

	public static final RegistryObject<Item> COPPER_PICKAXE = ITEMS.register("copper_pickaxe",
			() -> new CopperPickaxeItem(ModToolTiers.COPPER, 1, -2.8F, new Item.Properties()));

	public static final RegistryObject<Item> COPPER_AXE = ITEMS.register("copper_axe",
			() -> new CopperAxeItem(ModToolTiers.COPPER, 5.0F, -3.0F, new Item.Properties()));

	public static final RegistryObject<Item> COPPER_HOE = ITEMS.register("copper_hoe",
			() -> new CopperHoeItem(ModToolTiers.COPPER, -2, -1.0F, new Item.Properties()));

	public static final RegistryObject<Item> COPPER_HELMET = ITEMS.register("copper_helmet",
			() -> new CopperArmorItem(ModArmorMaterials.COPPER, ArmorItem.Type.HELMET, new Item.Properties()));

	public static final RegistryObject<Item> COPPER_CHESTPLATE = ITEMS.register("copper_chestplate",
			() -> new CopperArmorItem(ModArmorMaterials.COPPER, ArmorItem.Type.CHESTPLATE, new Item.Properties()));

	public static final RegistryObject<Item> COPPER_LEGGINGS = ITEMS.register("copper_leggings",
			() -> new CopperArmorItem(ModArmorMaterials.COPPER, ArmorItem.Type.LEGGINGS, new Item.Properties()));

	public static final RegistryObject<Item> COPPER_BOOTS = ITEMS.register("copper_boots",
			() -> new CopperArmorItem(ModArmorMaterials.COPPER, ArmorItem.Type.BOOTS, new Item.Properties()));

	public static final RegistryObject<Item> CITRINE_HELMET = ITEMS.register("citrine_helmet",
			() -> new CitrineArmorItem(ModArmorMaterials.CITRINE, ArmorItem.Type.HELMET, new Item.Properties()));

	public static final RegistryObject<Item> CITRINE_CHESTPLATE = ITEMS.register("citrine_chestplate",
			() -> new CitrineArmorItem(ModArmorMaterials.CITRINE, ArmorItem.Type.CHESTPLATE, new Item.Properties()));

	public static final RegistryObject<Item> CITRINE_LEGGINGS = ITEMS.register("citrine_leggings",
			() -> new CitrineArmorItem(ModArmorMaterials.CITRINE, ArmorItem.Type.LEGGINGS, new Item.Properties()));

	public static final RegistryObject<Item> CITRINE_BOOTS = ITEMS.register("citrine_boots",
			() -> new CitrineArmorItem(ModArmorMaterials.CITRINE, ArmorItem.Type.BOOTS, new Item.Properties()));

	public static final RegistryObject<Item> CITRINE_SWORD = ITEMS.register("citrine_sword",
			() -> new SwordItem(Tiers.DIAMOND, 3, -2.4F, new Item.Properties()));

	public static final RegistryObject<Item> CITRINE_PICKAXE = ITEMS.register("citrine_pickaxe",
			() -> new PickaxeItem(Tiers.DIAMOND, 1, -2.8F, new Item.Properties()));

	public static final RegistryObject<Item> CITRINE_AXE = ITEMS.register("citrine_axe",
			() -> new AxeItem(Tiers.DIAMOND, 5.0F, -3.0F, new Item.Properties()));

	public static final RegistryObject<Item> CITRINE_SHOVEL = ITEMS.register("citrine_shovel",
			() -> new ShovelItem(Tiers.DIAMOND, 1.5F, -3.0F, new Item.Properties()));

	public static final RegistryObject<Item> CITRINE_HOE = ITEMS.register("citrine_hoe",
			() -> new HoeItem(Tiers.DIAMOND, -3, 0.0F, new Item.Properties()));

	// Misc
	public static final RegistryObject<Item> QUICKSAND_BUCKET = ITEMS.register("quicksand_bucket",
			() -> new SolidBucketItem(ModBlocks.QUICKSAND.get(), SoundEvents.BUCKET_EMPTY_POWDER_SNOW,
					(new Item.Properties()).stacksTo(1)));

	public static final RegistryObject<Item> BLUEBERRY_SEEDS = ITEMS.register("blueberry_seeds",
			() -> new ItemNameBlockItem(ModBlocks.BLUEBERRY_CROP.get(), new Item.Properties().stacksTo(64)));

	public static final RegistryObject<Item> BUBBLEFRUIT = ITEMS.register("bubblefruit",
			() -> new ItemNameBlockItem(ModBlocks.BUBBLEFRUIT_CROP.get(),
					new Item.Properties().stacksTo(64)
							.food(new FoodProperties.Builder().nutrition(2).saturationMod(0.1f).fast()
									.effect(() -> new MobEffectInstance(MobEffects.SLOW_FALLING, 100, 0), 1.0F)
									.build())));

	public static final RegistryObject<Item> BLUEBERRY = ITEMS.register("blueberry",
			() -> new Item(new Item.Properties().stacksTo(64).food(Foods.SWEET_BERRIES)));

	public static final RegistryObject<Item> BERRY_PIE = ITEMS.register("berry_pie",
			() -> new Item(new Item.Properties().stacksTo(64).food(Foods.PUMPKIN_PIE)));

	public static final RegistryObject<Item> PALM_SEEDS = ITEMS.register("palm_seeds",
			() -> new Item(new Item.Properties().stacksTo(64)));

	public static final RegistryObject<Item> NIMBULA_GEL = ITEMS.register("nimbula_gel",
			() -> new Item(new Item.Properties().stacksTo(64)
					.food((new FoodProperties.Builder()).nutrition(4).saturationMod(0.1F)
							.effect(() -> new MobEffectInstance(MobEffects.SLOW_FALLING, 100, 0), 0.8F).meat()
							.build())));

	public static final RegistryObject<Item> MOLE_SPAWN_EGG = ITEMS.register("mole_spawn_egg",
			() -> new ForgeSpawnEggItem(ModEntityType.MOLE_ENTITY, 0x062e37, 0xa2af86, new Item.Properties()));

	public static final RegistryObject<Item> NIMBULA_SPAWN_EGG = ITEMS.register("nimbula_spawn_egg",
			() -> new ForgeSpawnEggItem(ModEntityType.NIMBULA_ENTITY, 0xffffff, 0x0092d6, new Item.Properties()));

	public static final RegistryObject<Item> TEMPEST_SPAWN_EGG = ITEMS.register("tempest_spawn_egg",
			() -> new ForgeSpawnEggItem(ModEntityType.TEMPEST_ENTITY, 0x292C33, 0x82eefd, new Item.Properties()));

	public static final RegistryObject<Item> WISP_SPAWN_EGG = ITEMS.register("wisp_spawn_egg",
			() -> new ForgeSpawnEggItem(ModEntityType.WISP_ENTITY, 0xffffff, 0x3333cc, new Item.Properties()));

	public static final RegistryObject<Item> GUST_SPAWN_EGG = ITEMS.register("gust_spawn_egg",
			() -> new ForgeSpawnEggItem(ModEntityType.GUST_ENTITY, 0xb4fffc, 0xf4f4f4, new Item.Properties()));

	public static final RegistryObject<Item> WRAITH_SPAWN_EGG = ITEMS.register("wraith_spawn_egg",
			() -> new ForgeSpawnEggItem(ModEntityType.WRAITH_ENTITY, 0xafb3b3, 0x88ff00, new Item.Properties()));

	public static final RegistryObject<Item> CUT_CITRINE = ITEMS.register("cut_citrine",
			() -> new Item(new Item.Properties()));

	public static final RegistryObject<Item> CITRINE_SHARD = ITEMS.register("citrine_shard",
			() -> new Item(new Item.Properties()));

	public static final RegistryObject<Item> CITRINE_BRACKET = ITEMS.register("citrine_bracket",
			() -> new StandingAndWallBlockItem(ModBlocks.CITRINE_BRACKET.get(), ModBlocks.CITRINE_WALL_BRACKET.get(),
					new Item.Properties(), Direction.DOWN));

	public static final RegistryObject<Item> FEATHER_WINGS = ITEMS.register("feather_wings",
			() -> new FeatherWingsItem(new Item.Properties().durability(108).rarity(Rarity.UNCOMMON)));
	public static final RegistryObject<Item> SEA_WINGS = ITEMS.register("sea_wings",
			() -> new SeaWingsItem(new Item.Properties().durability(1000)));
	public static final RegistryObject<Item> DRAGONFLY_WINGS = ITEMS.register("dragonfly_wings",
			() -> new DragonflyWingsItem(new Item.Properties().durability(1000)));

	public static final RegistryObject<Item> PALM_BOAT = ITEMS.register("palm_boat",
			() -> new ModBoatItem(false, ModBoatEntity.Type.PALM, new Item.Properties()));
	public static final RegistryObject<Item> PALM_CHEST_BOAT = ITEMS.register("palm_chest_boat",
			() -> new ModBoatItem(true, ModBoatEntity.Type.PALM, new Item.Properties()));

	public static final RegistryObject<Item> SPEEDOMETER = ITEMS.register("speedometer",
			() -> new SpeedometerItem(new Item.Properties()));

	public static final RegistryObject<Item> PALM_SIGN = ITEMS.register("palm_sign",
			() -> new SignItem(new Item.Properties().stacksTo(16), ModBlocks.PALM_SIGN.get(),
					ModBlocks.PALM_WALL_SIGN.get()));
	public static final RegistryObject<Item> PALM_HANGING_SIGN = ITEMS.register("palm_hanging_sign",
			() -> new HangingSignItem(ModBlocks.PALM_HANGING_SIGN.get(), ModBlocks.PALM_WALL_HANGING_SIGN.get(),
					new Item.Properties().stacksTo(16)));

	public static final RegistryObject<Item> HAILSTONE = ITEMS.register("hailstone",
			() -> new HailstoneItem(new Item.Properties()));

	public static final RegistryObject<Item> HEART_OF_THE_SKY = ITEMS.register("heart_of_the_sky",
			() -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON)));

	public static final RegistryObject<Item> GUST_BOTTLE = ITEMS.register("gust_bottle",
			() -> new WindBagItem(new Item.Properties().defaultDurability(8), 1.0, 50, Items.GLASS_BOTTLE));

	public static final RegistryObject<Item> TEMPEST_BOTTLE = ITEMS.register("tempest_bottle",
			() -> new WindBagItem(new Item.Properties().defaultDurability(32), 2.0, 50, Items.GLASS_BOTTLE));

	public static final RegistryObject<Item> EMPTY_WIND_BAG = ITEMS.register("empty_wind_bag",
			() -> new Item(new Item.Properties().stacksTo(64)));

	public static final RegistryObject<Item> WIND_BAG = ITEMS.register("wind_bag",
			() -> new WindBagItem(new Item.Properties().defaultDurability(1), 10, 5, ModItems.EMPTY_WIND_BAG.get()));

	// Various wing armor tiers
	public static final RegistryObject<Item> LEATHER_WINGS = ITEMS.register("leather_wings",
			() -> new ArmoredWingsItem(ArmorMaterials.LEATHER, Type.CHESTPLATE,
					new Item.Properties().durability(512).rarity(Rarity.UNCOMMON)));
	public static final RegistryObject<Item> GOLDEN_WINGS = ITEMS.register("golden_wings",
			() -> new ArmoredWingsItem(ArmorMaterials.GOLD, Type.CHESTPLATE,
					new Item.Properties().durability(544).rarity(Rarity.UNCOMMON)));
	public static final RegistryObject<Item> CHAINMAIL_WINGS = ITEMS.register("chainmail_wings",
			() -> new ArmoredWingsItem(ArmorMaterials.CHAIN, Type.CHESTPLATE,
					new Item.Properties().durability(672).rarity(Rarity.UNCOMMON)));
	public static final RegistryObject<Item> IRON_WINGS = ITEMS.register("iron_wings",
			() -> new ArmoredWingsItem(ArmorMaterials.IRON, Type.CHESTPLATE,
					new Item.Properties().durability(672).rarity(Rarity.UNCOMMON)));
	public static final RegistryObject<Item> DIAMOND_WINGS = ITEMS.register("diamond_wings",
			() -> new ArmoredWingsItem(ArmorMaterials.DIAMOND, Type.CHESTPLATE,
					new Item.Properties().durability(960).rarity(Rarity.UNCOMMON)));
	public static final RegistryObject<Item> NETHERITE_WINGS = ITEMS.register("netherite_wings",
			() -> new ArmoredWingsItem(ArmorMaterials.NETHERITE, Type.CHESTPLATE,
					new Item.Properties().durability(1024).fireResistant().rarity(Rarity.UNCOMMON)));
	public static final RegistryObject<Item> ZIRCONIUM_WINGS = ITEMS.register("zirconium_wings",
			() -> new ArmoredWingsItem(ModArmorMaterials.ZIRCONIUM, Type.CHESTPLATE,
					new Item.Properties().durability(784).fireResistant().rarity(Rarity.UNCOMMON)));
	public static final RegistryObject<Item> CITRINE_WINGS = ITEMS.register("citrine_wings",
			() -> new CitrineArmoredWingsItem(ModArmorMaterials.CITRINE, Type.CHESTPLATE,
					new Item.Properties().durability(960).rarity(Rarity.UNCOMMON)));
	public static final RegistryObject<Item> COPPER_WINGS = ITEMS.register("copper_wings",
			() -> new CopperArmoredWingsItem(ModArmorMaterials.COPPER, Type.CHESTPLATE,
					new Item.Properties().durability(672).rarity(Rarity.UNCOMMON)));

	// Spears
	private static final int SPEAR_DAMAGE = 0;
	
	public static final RegistryObject<Item> WOODEN_SPEAR = ITEMS.register("wooden_spear",
			() -> new SpearItem(Tiers.WOOD, SPEAR_DAMAGE, -3.0F, new Item.Properties()));
	public static final RegistryObject<Item> STONE_SPEAR = ITEMS.register("stone_spear",
			() -> new SpearItem(Tiers.STONE, SPEAR_DAMAGE, -3.0F, new Item.Properties()));
	public static final RegistryObject<Item> ZIRCONIUM_SPEAR = ITEMS.register("zirconium_spear",
			() -> new SpearItem(ModToolTiers.ZIRCONIUM, 0, -3.0F, new Item.Properties().fireResistant()));
	public static final RegistryObject<Item> COPPER_SPEAR = ITEMS.register("copper_spear",
			() -> new CopperSpearItem(ModToolTiers.COPPER, 0, -3.0F, new Item.Properties()));
	public static final RegistryObject<Item> IRON_SPEAR = ITEMS.register("iron_spear",
			() -> new SpearItem(Tiers.IRON, SPEAR_DAMAGE, -3.0F, new Item.Properties()));
	public static final RegistryObject<Item> GOLDEN_SPEAR = ITEMS.register("golden_spear",
			() -> new SpearItem(Tiers.GOLD, SPEAR_DAMAGE, -3.0F, new Item.Properties()));
	public static final RegistryObject<Item> DIAMOND_SPEAR = ITEMS.register("diamond_spear",
			() -> new SpearItem(Tiers.DIAMOND, SPEAR_DAMAGE, -3.0F, new Item.Properties()));
	public static final RegistryObject<Item> CITRINE_SPEAR = ITEMS.register("citrine_spear",
			() -> new SpearItem(Tiers.DIAMOND, SPEAR_DAMAGE, -3.0F, new Item.Properties()));
	public static final RegistryObject<Item> NETHERITE_SPEAR = ITEMS.register("netherite_spear",
			() -> new SpearItem(Tiers.NETHERITE, SPEAR_DAMAGE, -3.0F, new Item.Properties().fireResistant()));


	public static final RegistryObject<Item> ANCIENT_FEATHER_WINGS = ITEMS.register("ancient_feather_wings",
			() -> new AncientFeatherWingsItem(new Item.Properties().durability(108).rarity(Rarity.UNCOMMON)));

	// Boilerplate
	public static void register(IEventBus eventBus) {
		ITEMS.register(eventBus);
	}
}