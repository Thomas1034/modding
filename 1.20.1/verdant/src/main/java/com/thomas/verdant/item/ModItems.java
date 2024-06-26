package com.thomas.verdant.item;

import java.util.function.Supplier;

import com.thomas.verdant.Verdant;
import com.thomas.verdant.block.ModBlocks;
import com.thomas.verdant.effect.ModMobEffects;
import com.thomas.verdant.entity.custom.ModBoatEntity;
import com.thomas.verdant.item.custom.HeartOfTheForestItem;
import com.thomas.verdant.item.custom.ModBoatItem;
import com.thomas.verdant.item.custom.PoisonIvyArrowItem;
import com.thomas.verdant.item.custom.RopeCoilItem;
import com.thomas.verdant.item.custom.ToxicAshItem;
import com.thomas.verdant.item.custom.ToxicBucketItem;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.HangingSignItem;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.SignItem;
import net.minecraft.world.item.SwordItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Verdant.MOD_ID);

	public static final RegistryObject<Item> VERDANT_BOAT = registerItem("verdant_boat",
			() -> new ModBoatItem(false, ModBoatEntity.Type.VERDANT, new Item.Properties()));
	public static final RegistryObject<Item> VERDANT_CHEST_BOAT = registerItem("verdant_chest_boat",
			() -> new ModBoatItem(true, ModBoatEntity.Type.VERDANT, new Item.Properties()));

	public static final RegistryObject<Item> VERDANT_HEARTWOOD_BOAT = registerItem("verdant_heartwood_boat",
			() -> new ModBoatItem(false, ModBoatEntity.Type.VERDANT_HEARTWOOD, new Item.Properties()));
	public static final RegistryObject<Item> VERDANT_HEARTWOOD_CHEST_BOAT = registerItem("verdant_heartwood_chest_boat",
			() -> new ModBoatItem(true, ModBoatEntity.Type.VERDANT_HEARTWOOD, new Item.Properties()));

	public static final RegistryObject<Item> VERDANT_SIGN = registerItem("verdant_sign",
			() -> new SignItem(new Item.Properties().stacksTo(16), ModBlocks.VERDANT_SIGN.get(),
					ModBlocks.VERDANT_WALL_SIGN.get()));
	public static final RegistryObject<Item> VERDANT_HANGING_SIGN = registerItem("verdant_hanging_sign",
			() -> new HangingSignItem(ModBlocks.VERDANT_HANGING_SIGN.get(), ModBlocks.VERDANT_WALL_HANGING_SIGN.get(),
					new Item.Properties().stacksTo(16)));

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
			() -> new ArmorItem(ModArmorMaterials.VERDANT_HEARTWOOD, ArmorItem.Type.HELMET,
					new Item.Properties().fireResistant()));

	public static final RegistryObject<Item> VERDANT_HEARTWOOD_CHESTPLATE = ITEMS
			.register("verdant_heartwood_chestplate", () -> new ArmorItem(ModArmorMaterials.VERDANT_HEARTWOOD,
					ArmorItem.Type.CHESTPLATE, new Item.Properties().fireResistant()));

	public static final RegistryObject<Item> VERDANT_HEARTWOOD_LEGGINGS = ITEMS.register("verdant_heartwood_leggings",
			() -> new ArmorItem(ModArmorMaterials.VERDANT_HEARTWOOD, ArmorItem.Type.LEGGINGS,
					new Item.Properties().fireResistant()));

	public static final RegistryObject<Item> VERDANT_HEARTWOOD_BOOTS = ITEMS.register("verdant_heartwood_boots",
			() -> new ArmorItem(ModArmorMaterials.VERDANT_HEARTWOOD, ArmorItem.Type.BOOTS,
					new Item.Properties().fireResistant()));

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
			() -> new ToxicBucketItem(new Item.Properties().stacksTo(1), new ItemStack(Items.BUCKET)));

	public static final RegistryObject<Item> TOXIC_ASH_BUCKET = ITEMS.register("toxic_ash_bucket",
			() -> new Item(new Item.Properties().stacksTo(1)));

	public static final RegistryObject<Item> THORN = ITEMS.register("thorn", () -> new Item(new Item.Properties()));

	public static final RegistryObject<Item> HEART_OF_THE_FOREST = ITEMS.register("heart_of_the_forest",
			() -> new HeartOfTheForestItem(new Item.Properties().rarity(Rarity.UNCOMMON)));

	public static final RegistryObject<Item> SHORT_ROPE_COIL = ITEMS.register("short_rope_coil",
			() -> new RopeCoilItem(new Item.Properties().stacksTo(8), 8));
	public static final RegistryObject<Item> ROPE_COIL = ITEMS.register("rope_coil",
			() -> new RopeCoilItem(new Item.Properties().stacksTo(4), 16));

	// Cassava
	public static final RegistryObject<Item> CASSAVA_CUTTINGS = ITEMS.register("cassava_cuttings",
			() -> new ItemNameBlockItem(ModBlocks.CASSAVA_CROP.get(), new Item.Properties().stacksTo(64)));
	public static final RegistryObject<Item> CASSAVA = ITEMS.register("cassava",
			() -> new Item(new Item.Properties().stacksTo(64)));
	public static final RegistryObject<Item> BITTER_CASSAVA = ITEMS.register("bitter_cassava",
			() -> new Item(new Item.Properties().stacksTo(64)));

	// Boilerplate
	private static RegistryObject<Item> registerItem(String name, Supplier<Item> item) {
		return ModItems.ITEMS.register(name, item);
	}

	public static void register(IEventBus eventBus) {
		ITEMS.register(eventBus);
	}
}