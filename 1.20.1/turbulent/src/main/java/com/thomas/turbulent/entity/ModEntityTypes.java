package com.thomas.turbulent.entity;

import com.thomas.turbulent.Turbulent;

import net.minecraft.world.entity.EntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModEntityTypes {
	public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister
			.create(ForgeRegistries.ENTITY_TYPES, Turbulent.MOD_ID);

	public static void register(IEventBus eventBus) {
		ENTITY_TYPES.register(eventBus);
	}
}