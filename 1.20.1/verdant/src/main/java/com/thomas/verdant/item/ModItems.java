package com.thomas.verdant.item;

import com.thomas.verdant.Verdant;

import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModItems {
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Verdant.MOD_ID);

	// Boilerplate
	public static void register(IEventBus eventBus) {
		ITEMS.register(eventBus);
	}
}