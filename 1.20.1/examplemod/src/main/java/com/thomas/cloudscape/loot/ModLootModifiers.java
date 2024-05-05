package com.thomas.cloudscape.loot;

import com.mojang.serialization.Codec;
import com.thomas.cloudscape.Cloudscape;

import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

//From Kaupenjoe
public class ModLootModifiers {
	public static final DeferredRegister<Codec<? extends IGlobalLootModifier>> LOOT_MODIFIER_SERIALIZERS = DeferredRegister
			.create(ForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, Cloudscape.MOD_ID);

	public static final RegistryObject<Codec<? extends IGlobalLootModifier>> ADD_ITEM = LOOT_MODIFIER_SERIALIZERS
			.register("add_item", AddSingleItemModifier.CODEC);
	public static final RegistryObject<Codec<? extends IGlobalLootModifier>> ADD_MULTI_ITEM = LOOT_MODIFIER_SERIALIZERS
			.register("add_multi_item", AddMultiItemModifier.CODEC);
	
	public static final RegistryObject<Codec<? extends IGlobalLootModifier>> ADD_SUS_SAND_ITEM = LOOT_MODIFIER_SERIALIZERS
			.register("add_sus_sand_item", AddSuspiciousSandItemModifier.CODEC);

	public static void register(IEventBus eventBus) {
		LOOT_MODIFIER_SERIALIZERS.register(eventBus);
	}
}