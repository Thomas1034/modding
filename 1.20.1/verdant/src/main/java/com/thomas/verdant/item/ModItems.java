package com.thomas.verdant.item;

import java.util.function.Supplier;

import com.thomas.verdant.Verdant;
import com.thomas.verdant.block.ModBlocks;
import com.thomas.verdant.entity.custom.ModBoatEntity;
import com.thomas.verdant.item.custom.ModBoatItem;
import com.thomas.verdant.item.custom.VerdantVineItem;

import net.minecraft.world.item.HangingSignItem;
import net.minecraft.world.item.HoneyBottleItem;
import net.minecraft.world.item.Item;
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

	public static final RegistryObject<Item> VERDANT_SIGN = registerItem("verdant_sign",
			() -> new SignItem(new Item.Properties().stacksTo(16), ModBlocks.VERDANT_SIGN.get(),
					ModBlocks.VERDANT_WALL_SIGN.get()));
	public static final RegistryObject<Item> VERDANT_HANGING_SIGN = registerItem("verdant_hanging_sign",
			() -> new HangingSignItem(ModBlocks.VERDANT_HANGING_SIGN.get(), ModBlocks.VERDANT_WALL_HANGING_SIGN.get(),
					new Item.Properties().stacksTo(16)));

	public static final RegistryObject<Item> LEMON_JUICE_BOTTLE = registerItem("lemon_juice_bottle",
			() -> new HoneyBottleItem(new Item.Properties().stacksTo(1)
					.food(ModFoods.LEMON_JUICE)));

	//public static final RegistryObject<Item> VERDANT_VINE = registerItem("verdant_vine",
	//		() -> new VerdantVineItem(new Item.Properties()));
	
	// Boilerplate
	private static RegistryObject<Item> registerItem(String name, Supplier<Item> item) {
		return ModItems.ITEMS.register(name, item);
	}
	
	public static void register(IEventBus eventBus) {
		ITEMS.register(eventBus);
	}
}