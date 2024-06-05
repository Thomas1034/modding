package com.thomas.verdant.util.modfeature.placements;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import net.minecraft.resources.ResourceLocation;

public class ModPlacementTypeLookup {

	private static final Map<ResourceLocation, Supplier<AbstractPlacementType>> FEATURES = new HashMap<>();

	public static void add(ResourceLocation name, Supplier<AbstractPlacementType> feature) {
		FEATURES.put(name, feature);
	}

	public static AbstractPlacementType lookup(ResourceLocation name) {
		return FEATURES.get(name).get();
	}

}
