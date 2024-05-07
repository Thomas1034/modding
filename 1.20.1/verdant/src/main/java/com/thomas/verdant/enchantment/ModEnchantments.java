package com.thomas.verdant.enchantment;

import com.thomas.verdant.Verdant;

import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModEnchantments {
	public static final DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister
			.create(ForgeRegistries.ENCHANTMENTS, Verdant.MOD_ID);

	
	public static void register(IEventBus eventBus) {
		ENCHANTMENTS.register(eventBus);
	}
}
