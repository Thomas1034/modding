package com.thomas.verdant.item;

import java.util.function.Supplier;

import com.thomas.verdant.Verdant;
import com.thomas.verdant.block.ModBlocks;
import com.thomas.verdant.effect.ModMobEffects;
import com.thomas.verdant.entity.custom.ModBoatEntity;
import com.thomas.verdant.item.custom.ModBoatItem;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.HangingSignItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.item.SignItem;
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

	public static final RegistryObject<Item> VERDANT_HEARTWOOD_SIGN = registerItem("verdant_heartwood_sign",
			() -> new SignItem(new Item.Properties().stacksTo(16), ModBlocks.VERDANT_HEARTWOOD_SIGN.get(),
					ModBlocks.VERDANT_HEARTWOOD_WALL_SIGN.get()));
	public static final RegistryObject<Item> VERDANT_HEARTWOOD_HANGING_SIGN = registerItem(
			"verdant_heartwood_hanging_sign", () -> new HangingSignItem(ModBlocks.VERDANT_HEARTWOOD_HANGING_SIGN.get(),
					ModBlocks.VERDANT_HEARTWOOD_WALL_HANGING_SIGN.get(), new Item.Properties().stacksTo(16)));
	
	// Feed to goats?
	public static final RegistryObject<Item> COFFEE_BERRIES = ITEMS
			.register("coffee_berries",
					() -> new ItemNameBlockItem(ModBlocks.COFFEE_CROP.get(), new Item.Properties().stacksTo(64)
							.food(new FoodProperties.Builder().nutrition(2).saturationMod(0.1f).fast()
									.effect(() -> new MobEffectInstance(ModMobEffects.CAFFEINATED.get(), 300, 0), 1.0F)
									.build())));

	public static final RegistryObject<Item> ROASTED_COFFEE = ITEMS
			.register("roasted_coffee",
					() -> new Item(new Item.Properties()));
	
	// Heartwood armor
	
	
	// Boilerplate
	private static RegistryObject<Item> registerItem(String name, Supplier<Item> item) {
		return ModItems.ITEMS.register(name, item);
	}

	public static void register(IEventBus eventBus) {
		ITEMS.register(eventBus);
	}
}