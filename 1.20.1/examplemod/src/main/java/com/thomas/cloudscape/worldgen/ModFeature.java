package com.thomas.cloudscape.worldgen;

import java.util.function.Supplier;

import com.thomas.cloudscape.ZirconMod;
import com.thomas.cloudscape.block.ModBlocks;
import com.thomas.cloudscape.worldgen.biome.ModBiomes;
import com.thomas.cloudscape.worldgen.custom.CirrusFeature;
import com.thomas.cloudscape.worldgen.custom.CloudFloorFeature;
import com.thomas.cloudscape.worldgen.custom.OasisFeature;
import com.thomas.cloudscape.worldgen.custom.PatchFeature;
import com.thomas.cloudscape.worldgen.custom.PetrifiedTreeFeature;
import com.thomas.cloudscape.worldgen.custom.SculkHostilesFeature;
import com.thomas.cloudscape.worldgen.custom.SimplexCloudFeature;

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
			() -> new PatchFeature(NoneFeatureConfiguration.CODEC, Blocks.STONE, 8));
	public static final RegistryObject<Feature<?>> COARSE_DIRT_PATCH = register("coarse_dirt_patch",
			() -> new PatchFeature(NoneFeatureConfiguration.CODEC, Blocks.COARSE_DIRT, 8));
	public static final RegistryObject<Feature<?>> SCULK_DECORATION = register("sculk_decoration",
			() -> new SculkHostilesFeature(NoneFeatureConfiguration.CODEC));
	public static final RegistryObject<Feature<?>> LARGE_CLOUD = register("large_cloud",
			() -> new SimplexCloudFeature(NoneFeatureConfiguration.CODEC, 0.7, 9223372036854775807L));
	public static final RegistryObject<Feature<?>> THICK_CLOUD_CEILING_FOR_CLOUDY_SKY = register("thick_cloud_ceiling",
			() -> new CloudFloorFeature(NoneFeatureConfiguration.CODEC, ModBiomes.CLOUDY_SKY_BIOME,
					ModBlocks.CLOUD.get().defaultBlockState()));
	public static final RegistryObject<Feature<?>> CIRRUS_CLOUD_FOR_CIRRUS = register("cirrus_cloud",
			() -> new CirrusFeature(NoneFeatureConfiguration.CODEC, ModBiomes.CIRRUS_BIOME,
					ModBlocks.CLOUD.get().defaultBlockState(), ModBlocks.MIST.get().defaultBlockState()));

	private static <T extends Feature<?>> RegistryObject<T> register(String name, Supplier<T> feature) {
		return FEATURES.register(name, feature);
	}

	public static void register(IEventBus modEventBus) {
		FEATURES.register(modEventBus);
	}

}
