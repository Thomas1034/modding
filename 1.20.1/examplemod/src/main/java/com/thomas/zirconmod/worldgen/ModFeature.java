package com.thomas.zirconmod.worldgen;

import java.util.function.Supplier;

import com.thomas.zirconmod.ZirconMod;
import com.thomas.zirconmod.worldgen.custom.OasisFeature;
import com.thomas.zirconmod.worldgen.custom.PatchFeature;
import com.thomas.zirconmod.worldgen.custom.PetrifiedTreeFeature;
import com.thomas.zirconmod.worldgen.custom.SculkHostilesFeature;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModFeature {
	public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES,
			ZirconMod.MOD_ID);

	public static final RegistryObject<Feature<?>> OASIS = register("oasis",
			() -> new OasisFeature(NoneFeatureConfiguration.CODEC));
	public static final RegistryObject<Feature<?>> PETRIFIED_TREE = register("petrified_tree",
			() -> new PetrifiedTreeFeature(NoneFeatureConfiguration.CODEC));
	public static final RegistryObject<Feature<?>> STONE_PATCH = register("stone_patch",
			() -> new PatchFeature(NoneFeatureConfiguration.CODEC, Blocks.STONE, Blocks.GRAVEL, 16));
	public static final RegistryObject<Feature<?>> COARSE_DIRT_PATCH = register("coarse_dirt_patch",
			() -> new PatchFeature(NoneFeatureConfiguration.CODEC, Blocks.COARSE_DIRT, Blocks.COARSE_DIRT, 16));
	public static final RegistryObject<Feature<?>> SCULK_DECORATION = register("sculk_decoration",
			() -> new SculkHostilesFeature(NoneFeatureConfiguration.CODEC));

	
	private static <T extends Feature<?>> RegistryObject<T> register(String name, Supplier<T> feature) {
		return FEATURES.register(name, feature);
	}

	public static void register(IEventBus modEventBus) {
		FEATURES.register(modEventBus);
	}

}
