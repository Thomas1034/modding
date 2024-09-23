package com.thomas.turbulent.item;

import java.util.function.Supplier;

import com.thomas.turbulent.Turbulent;

import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Turbulent.MOD_ID);

	// Boilerplate
	@SuppressWarnings("unused")
	private static RegistryObject<Item> registerItem(String name, Supplier<Item> item) {
		return ModItems.ITEMS.register(name, item);
	}

	public static void register(IEventBus eventBus) {
		ITEMS.register(eventBus);
	}
}