package com.thomas.verdant.worldgen;

import java.util.function.Supplier;

import com.thomas.verdant.Verdant;

import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModFeature {
	public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES,
			Verdant.MOD_ID);

	private static <T extends Feature<?>> RegistryObject<T> register(String name, Supplier<T> feature) {
		return FEATURES.register(name, feature);
	}

	public static void register(IEventBus modEventBus) {
		FEATURES.register(modEventBus);
	}

}
