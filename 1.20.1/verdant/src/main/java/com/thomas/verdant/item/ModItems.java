package com.thomas.verdant.item;

import java.util.function.Supplier;

import com.thomas.verdant.Verdant;
import com.thomas.verdant.block.ModBlocks;
import com.thomas.verdant.effect.ModMobEffects;
import com.thomas.verdant.item.custom.EffectBoostFoodItem;
import com.thomas.verdant.item.custom.HeartOfTheForestItem;
import com.thomas.verdant.item.custom.PoisonIvyArrowItem;
import com.thomas.verdant.item.custom.RopeCoilItem;
import com.thomas.verdant.item.custom.ToxicAshItem;
import com.thomas.verdant.item.custom.ToxicBucketItem;
import com.thomas.verdant.item.custom.VerdantHeartwoodArmorItem;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.food.Foods;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.DoubleHighBlockItem;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.SwordItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Verdant.MOD_ID);

	// TODO SWAP TO WoodSet
	public static final RegistryObject<Item> VERDANT_BOAT = ModBlocks.VERDANT.getBoatItem();

	public static final RegistryObject<Item> VERDANT_CHEST_BOAT = ModBlocks.VERDANT.getChestBoatItem();

	public static final RegistryObject<Item> VERDANT_HEARTWOOD_BOAT = ModBlocks.VERDANT_HEARTWOOD.getBoatItem();

	public static final RegistryObject<Item> VERDANT_HEARTWOOD_CHEST_BOAT = ModBlocks.VERDANT_HEARTWOOD
			.getChestBoatItem();

	public static final RegistryObject<Item> VERDANT_SIGN = ModBlocks.VERDANT.getSignItem();

	public static final RegistryObject<Item> VERDANT_HANGING_SIGN = ModBlocks.VERDANT.getHangingSignItem();

	public static final RegistryObject<Item> VERDANT_HEARTWOOD_SIGN = ModBlocks.VERDANT_HEARTWOOD.getSignItem();

	public static final RegistryObject<Item> VERDANT_HEARTWOOD_HANGING_SIGN = ModBlocks.VERDANT_HEARTWOOD
			.getHangingSignItem();

	// Feed to goats?
	public static final RegistryObject<Item> COFFEE_BERRIES = ITEMS
			.register("coffee_berries",
					() -> new ItemNameBlockItem(ModBlocks.COFFEE_CROP.get(), new Item.Properties().stacksTo(64)
							.food(new FoodProperties.Builder().nutrition(2).saturationMod(0.1f).fast()
									.effect(() -> new MobEffectInstance(ModMobEffects.CAFFEINATED.get(), 300, 0), 1.0F)
									.build())));

	public static final RegistryObject<Item> ROASTED_COFFEE = ITEMS.register("roasted_coffee",
			() -> new Item(new Item.Properties()));

	// Heartwood armor
	public static final RegistryObject<Item> VERDANT_HEARTWOOD_HELMET = ITEMS.register("verdant_heartwood_helmet",
			() -> new VerdantHeartwoodArmorItem(ModArmorMaterials.VERDANT_HEARTWOOD, ArmorItem.Type.HELMET,
					new Item.Properties().fireResistant(), 400));

	public static final RegistryObject<Item> VERDANT_HEARTWOOD_CHESTPLATE = ITEMS
			.register("verdant_heartwood_chestplate", () -> new VerdantHeartwoodArmorItem(ModArmorMaterials.VERDANT_HEARTWOOD,
					ArmorItem.Type.CHESTPLATE, new Item.Properties().fireResistant(), 400));

	public static final RegistryObject<Item> VERDANT_HEARTWOOD_LEGGINGS = ITEMS.register("verdant_heartwood_leggings",
			() -> new VerdantHeartwoodArmorItem(ModArmorMaterials.VERDANT_HEARTWOOD, ArmorItem.Type.LEGGINGS,
					new Item.Properties().fireResistant(), 400));

	public static final RegistryObject<Item> VERDANT_HEARTWOOD_BOOTS = ITEMS.register("verdant_heartwood_boots",
			() -> new VerdantHeartwoodArmorItem(ModArmorMaterials.VERDANT_HEARTWOOD, ArmorItem.Type.BOOTS,
					new Item.Properties().fireResistant(), 400));

	// Heartwood armor
	public static final RegistryObject<Item> IMBUED_VERDANT_HEARTWOOD_HELMET = ITEMS
			.register("imbued_verdant_heartwood_helmet", () -> new VerdantHeartwoodArmorItem(ModArmorMaterials.IMBUED_VERDANT_HEARTWOOD,
					ArmorItem.Type.HELMET, new Item.Properties().fireResistant(), 100));

	public static final RegistryObject<Item> IMBUED_VERDANT_HEARTWOOD_CHESTPLATE = ITEMS
			.register("imbued_verdant_heartwood_chestplate", () -> new VerdantHeartwoodArmorItem(ModArmorMaterials.IMBUED_VERDANT_HEARTWOOD,
					ArmorItem.Type.CHESTPLATE, new Item.Properties().fireResistant(), 100));

	public static final RegistryObject<Item> IMBUED_VERDANT_HEARTWOOD_LEGGINGS = ITEMS
			.register("imbued_verdant_heartwood_leggings", () -> new VerdantHeartwoodArmorItem(ModArmorMaterials.IMBUED_VERDANT_HEARTWOOD,
					ArmorItem.Type.LEGGINGS, new Item.Properties().fireResistant(), 100));

	public static final RegistryObject<Item> IMBUED_VERDANT_HEARTWOOD_BOOTS = ITEMS
			.register("imbued_verdant_heartwood_boots", () -> new VerdantHeartwoodArmorItem(ModArmorMaterials.IMBUED_VERDANT_HEARTWOOD,
					ArmorItem.Type.BOOTS, new Item.Properties().fireResistant(), 100));

	// Heartwood weapons
	public static final RegistryObject<Item> VERDANT_HEARTWOOD_SWORD = ITEMS.register("verdant_heartwood_sword",
			() -> new SwordItem(ModToolTiers.VERDANT_HEARTWOOD, 3, -2.4F, new Item.Properties()));

	public static final RegistryObject<Item> VERDANT_HEARTWOOD_SHOVEL = ITEMS.register("verdant_heartwood_shovel",
			() -> new ShovelItem(ModToolTiers.VERDANT_HEARTWOOD, 1.2F, -3.0F, new Item.Properties()));

	public static final RegistryObject<Item> VERDANT_HEARTWOOD_PICKAXE = ITEMS.register("verdant_heartwood_pickaxe",
			() -> new PickaxeItem(ModToolTiers.VERDANT_HEARTWOOD, 1, -2.8F, new Item.Properties()));

	public static final RegistryObject<Item> VERDANT_HEARTWOOD_AXE = ITEMS.register("verdant_heartwood_axe",
			() -> new AxeItem(ModToolTiers.VERDANT_HEARTWOOD, 5.0F, -3.0F, new Item.Properties()));

	public static final RegistryObject<Item> VERDANT_HEARTWOOD_HOE = ITEMS.register("verdant_heartwood_hoe",
			() -> new HoeItem(ModToolTiers.VERDANT_HEARTWOOD, -2, -1.0F, new Item.Properties()));

	public static final RegistryObject<Item> POISON_ARROW = ITEMS.register("poison_arrow",
			() -> new PoisonIvyArrowItem(new Item.Properties()));

	public static final RegistryObject<Item> TOXIC_ASH = ITEMS.register("toxic_ash",
			() -> new ToxicAshItem(new Item.Properties()));

	public static final RegistryObject<Item> TOXIC_SOLUTION_BUCKET = ITEMS.register("toxic_solution_bucket",
			() -> new ToxicBucketItem(new Item.Properties().stacksTo(1), () -> new ItemStack(Items.BUCKET)));

	public static final RegistryObject<Item> TOXIC_ASH_BUCKET = ITEMS.register("toxic_ash_bucket",
			() -> new Item(new Item.Properties().stacksTo(1)));

	public static final RegistryObject<Item> THORN = ITEMS.register("thorn", () -> new Item(new Item.Properties()));

	public static final RegistryObject<Item> HEART_FRAGMENT = ITEMS.register("heart_fragment",
			() -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON)));
	
	public static final RegistryObject<Item> HEART_OF_THE_FOREST = ITEMS.register("heart_of_the_forest",
			() -> new HeartOfTheForestItem(new Item.Properties().rarity(Rarity.UNCOMMON)));

	public static final RegistryObject<Item> SHORT_ROPE_COIL = ITEMS.register("short_rope_coil",
			() -> new RopeCoilItem(new Item.Properties().stacksTo(8), 8));
	public static final RegistryObject<Item> ROPE_COIL = ITEMS.register("rope_coil",
			() -> new RopeCoilItem(new Item.Properties().stacksTo(4), 16));

	// Cassava
	public static final RegistryObject<Item> CASSAVA_CUTTINGS = ITEMS.register("cassava_cuttings",
			() -> new ItemNameBlockItem(ModBlocks.CASSAVA_CROP.get(), new Item.Properties().stacksTo(64)));
	public static final RegistryObject<Item> BITTER_CASSAVA_CUTTINGS = ITEMS.register("bitter_cassava_cuttings",
			() -> new ItemNameBlockItem(ModBlocks.BITTER_CASSAVA_CROP.get(), new Item.Properties().stacksTo(64)));
	public static final RegistryObject<Item> CASSAVA = ITEMS.register("cassava",
			() -> new Item(new Item.Properties().stacksTo(64)));
	public static final RegistryObject<Item> BITTER_CASSAVA = ITEMS.register("bitter_cassava",
			() -> new Item(new Item.Properties().stacksTo(64)));
	public static final RegistryObject<Item> STARCH = ITEMS.register("starch",
			() -> new Item(new Item.Properties().stacksTo(64)));
	public static final RegistryObject<Item> BITTER_STARCH = ITEMS.register("bitter_starch",
			() -> new Item(new Item.Properties().stacksTo(64)));
	public static final RegistryObject<Item> BITTER_BREAD = ITEMS.register("bitter_bread",
			() -> new EffectBoostFoodItem(new Item.Properties().stacksTo(64).food(Foods.BREAD),
					ModMobEffects.CASSAVA_POISONING,
					(i) -> new MobEffectInstance(ModMobEffects.CASSAVA_POISONING.get(), (i + 2) * 20 * 60, i + 1)));
	public static final RegistryObject<Item> COOKED_CASSAVA = ITEMS.register("cooked_cassava",
			() -> new Item(new Item.Properties().stacksTo(64).food(Foods.COOKED_CHICKEN)));
	public static final RegistryObject<Item> GOLDEN_CASSAVA = ITEMS.register("golden_cassava",
			() -> new Item(new Item.Properties().stacksTo(64)));
	public static final RegistryObject<Item> COOKED_GOLDEN_CASSAVA = ITEMS.register("cooked_golden_cassava",
			() -> new Item(new Item.Properties().stacksTo(64).food((new FoodProperties.Builder()).nutrition(8)
					.saturationMod(1.2F).effect(() -> new MobEffectInstance(MobEffects.DIG_SPEED, 2400, 0), 1.0F)
					.effect(() -> new MobEffectInstance(MobEffects.ABSORPTION, 2400, 0), 1.0F).alwaysEat().build())));
	public static final RegistryObject<Item> SPARKLING_STARCH = ITEMS.register("sparkling_starch",
			() -> new Item(new Item.Properties().stacksTo(64)));
	
	// Yam stuff
	public static final RegistryObject<Item> YAM = ITEMS.register("yam",
			() -> new Item(new Item.Properties().stacksTo(64)));
	

	
	// Water hemlock
	public static final RegistryObject<Item> WATER_HEMLOCK = ITEMS.register("water_hemlock",
			() -> new DoubleHighBlockItem(ModBlocks.WATER_HEMLOCK.get(), new Item.Properties().stacksTo(64)));

	// Boilerplate
	@SuppressWarnings("unused")
	private static RegistryObject<Item> registerItem(String name, Supplier<Item> item) {
		return ModItems.ITEMS.register(name, item);
	}

	public static void register(IEventBus eventBus) {
		ITEMS.register(eventBus);
	}
}