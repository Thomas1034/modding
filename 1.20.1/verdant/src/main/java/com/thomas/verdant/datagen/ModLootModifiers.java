package com.thomas.verdant.datagen;

import com.mojang.serialization.Codec;
import com.thomas.verdant.Verdant;
import com.thomas.verdant.datagen.lootmodifiers.AddItemModifier;
import com.thomas.verdant.datagen.lootmodifiers.AddTableModifier;

import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModLootModifiers {
	public static final DeferredRegister<Codec<? extends IGlobalLootModifier>> LOOT_MODIFIER_SERIALIZERS = DeferredRegister
			.create(ForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, Verdant.MOD_ID);

	public static final RegistryObject<Codec<? extends IGlobalLootModifier>> ADD_ITEM = LOOT_MODIFIER_SERIALIZERS
			.register("add_item", AddItemModifier.CODEC);
	public static final RegistryObject<Codec<? extends IGlobalLootModifier>> ADD_TABLE = LOOT_MODIFIER_SERIALIZERS
			.register("add_table", AddTableModifier.CODEC);

	public static void register(IEventBus eventBus) {
		LOOT_MODIFIER_SERIALIZERS.register(eventBus);
	}
}