package com.thomas.verdant.util.modfeature.checkers;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import com.thomas.verdant.Verdant;
import com.thomas.verdant.util.modfeature.checkers.builtin.SingleBlockChecker;
import com.thomas.verdant.util.modfeature.checkers.builtin.SingleBlockStateChecker;

import net.minecraft.resources.ResourceLocation;

public class ModCheckTypeLookup {

	private static final Map<ResourceLocation, Supplier<AbstractCheckerType>> FEATURES = new HashMap<>();

	public static void add(ResourceLocation name, Supplier<AbstractCheckerType> feature) {
		FEATURES.put(name, feature);
	}

	public static Supplier<AbstractCheckerType> lookup(ResourceLocation name) {
		return FEATURES.get(name);
	}

	public static void registerBuiltins() {
		add(new ResourceLocation(Verdant.MOD_ID, "single_block"), SingleBlockChecker::new);
		add(new ResourceLocation(Verdant.MOD_ID, "single_blockstate"), SingleBlockStateChecker::new);

	}

}
