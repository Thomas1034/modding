package com.thomas.cloudscape.enchantment;

import com.thomas.cloudscape.ZirconMod;
import com.thomas.cloudscape.enchantment.custom.UpdraftEnchantment;

import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEnchantments {
	public static final DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister
			.create(ForgeRegistries.ENCHANTMENTS, ZirconMod.MOD_ID);

	public static final RegistryObject<Enchantment> UPDRAFT = ENCHANTMENTS.register("updraft", UpdraftEnchantment::new);

	public static void register(IEventBus eventBus) {
		ENCHANTMENTS.register(eventBus);
	}
}
