package com.thomas.verdant.util.modfeature.placers;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import net.minecraft.resources.ResourceLocation;

public class ModPlacerTypeLookup {

	private static final Map<ResourceLocation, Supplier<AbstractPlacerType>> FEATURES = new HashMap<>();

	public static void add(ResourceLocation name, Supplier<AbstractPlacerType> feature) {
		FEATURES.put(name, feature);
	}

	public static Supplier<AbstractPlacerType> lookup(ResourceLocation name) {
		return FEATURES.get(name);
	}

}
