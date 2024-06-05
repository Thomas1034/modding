package com.thomas.verdant.util.modfeature.features;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import net.minecraft.resources.ResourceLocation;

public class ModFeatureTypeLookup {

	private static final Map<ResourceLocation, Supplier<AbstractFeatureType>> FEATURES = new HashMap<>();

	public static void add(ResourceLocation name, Supplier<AbstractFeatureType> feature) {
		FEATURES.put(name, feature);
	}

	public static Supplier<AbstractFeatureType> lookup(ResourceLocation name) {
		return FEATURES.get(name);
	}

}
