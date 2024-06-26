package com.thomas.quantumcircuit.item;

import java.util.function.Supplier;

import com.thomas.quantumcircuit.QuantumCircuit;
import com.thomas.quantumcircuit.item.custom.DebugItem;

import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS,
			QuantumCircuit.MOD_ID);
			
	public static final RegistryObject<Item> DEBUGGER = ITEMS.register("debugger", () -> new DebugItem(new Item.Properties()));
	
	// Boilerplate
	public static RegistryObject<Item> register(String name, Supplier<Item> supplier) {
		return ITEMS.register(name, supplier);
	}
	
	public static void register(IEventBus eventBus) {
		ITEMS.register(eventBus);
	}
}
