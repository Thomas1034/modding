package com.thomas.zirconmod.worldgen;

import java.util.List;

import com.thomas.zirconmod.ZirconMod;
import com.thomas.zirconmod.block.ModBlocks;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.RarityFilter;

public class ModPlacedFeatures {
		
	public static final ResourceKey<PlacedFeature> ZIRCON_ORE_PLACED_KEY = registerKey("zircon_ore_placed");
	
	public static final ResourceKey<PlacedFeature> CITRINE_GEODE_PLACED_KEY = registerKey("citrine_geode_placed");

	public static final ResourceKey<PlacedFeature> PALM_PLACED_KEY = registerKey("palm_placed");
	public static final ResourceKey<PlacedFeature> PALM_PLACED_JUNGLE_KEY = registerKey("palm_placed_jungle");

	public static final ResourceKey<PlacedFeature> OASIS_PLACED_KEY = registerKey("oasis_placed");

	public static final ResourceKey<PlacedFeature> PETRIFIED_TREE_PLACED_KEY = registerKey("petrified_tree_placed");

	public static final ResourceKey<PlacedFeature> STONE_PATCH_PLACED_KEY = registerKey("stone_patch_placed");
	public static final ResourceKey<PlacedFeature> COARSE_DIRT_PATCH_PLACED_KEY = registerKey(
			"coarse_dirt_patch_placed");

	public static final ResourceKey<PlacedFeature> SCULK_HOSTILES_PLACED_KEY = registerKey("sculk_hostiles_placed");

	public static final ResourceKey<PlacedFeature> SMALL_CLOUD_PLACED_KEY = registerKey("small_cloud_placed");

	public static final ResourceKey<PlacedFeature> LARGE_CLOUD_PLACED_KEY = registerKey("large_cloud_placed");

	public static final ResourceKey<PlacedFeature> THICK_CLOUD_CEILING_FOR_CLOUDY_SKY_BIOME_PLACED_KEY = registerKey("thick_cloud_ceiling_placed");

	
	public static void bootstrap(BootstapContext<PlacedFeature> context) {
		HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);

		register(context, ZIRCON_ORE_PLACED_KEY,
				configuredFeatures.getOrThrow(ModConfiguredFeatures.OVERWORLD_ZIRCON_ORE_KEY),
				ModPlacement.commonOrePlacement(20,
						HeightRangePlacement.triangle(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(320))));

		register(context, CITRINE_GEODE_PLACED_KEY,
				configuredFeatures.getOrThrow(ModConfiguredFeatures.OVERWORLD_CITRINE_GEODE_KEY),
				ModPlacement.rareOrePlacement(64,
						HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(32))));

		register(context, PALM_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.PALM_KEY),
				VegetationPlacements.treePlacement(RarityFilter.onAverageOnceEvery(2), ModBlocks.PALM_SAPLING.get()));

		register(context, PALM_PLACED_JUNGLE_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.PALM_JUNGLE_KEY),
				VegetationPlacements.treePlacement(PlacementUtils.countExtra(3, 0.1f, 2),
						ModBlocks.PALM_SAPLING.get()));

		register(context, OASIS_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.OASIS_KEY),
				ModPlacement.rareSurfacePlacement(75));

		register(context, PETRIFIED_TREE_PLACED_KEY,
				configuredFeatures.getOrThrow(ModConfiguredFeatures.PETRIFIED_TREE_KEY),
				ModPlacement.rareSurfacePlacement(2));

		register(context, STONE_PATCH_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.STONE_PATCH_KEY),
				ModPlacement.rareSurfacePlacement(4));

		register(context, COARSE_DIRT_PATCH_PLACED_KEY,
				configuredFeatures.getOrThrow(ModConfiguredFeatures.COARSE_DIRT_PATCH_KEY),
				ModPlacement.rareSurfacePlacement(4));

		register(context, SCULK_HOSTILES_PLACED_KEY,
				configuredFeatures.getOrThrow(ModConfiguredFeatures.SCULK_HOSTILES_KEY), ModPlacement.rareCavePlacement(4));

		register(context, SMALL_CLOUD_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.SMALL_CLOUD_KEY),
				ModPlacement.rareAboveBottomRangePlacement(0, 128, 8));

		register(context, LARGE_CLOUD_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.LARGE_CLOUD_KEY),
				ModPlacement.rareAboveBottomRangePlacement(0, 128, 64));
		
		register(context, THICK_CLOUD_CEILING_FOR_CLOUDY_SKY_BIOME_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.THICK_CLOUD_CEILING_FOR_CLOUDY_SKY_KEY),
				ModPlacement.commonAboveBottomPlacement(192, 1));

	}

	private static ResourceKey<PlacedFeature> registerKey(String name) {
		return ResourceKey.create(Registries.PLACED_FEATURE, new ResourceLocation(ZirconMod.MOD_ID, name));
	}

	private static void register(BootstapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key,
			Holder<ConfiguredFeature<?, ?>> configuration, List<PlacementModifier> modifiers) {
		
		PlacedFeature feature = new PlacedFeature(configuration, List.copyOf(modifiers));
		context.register(key, feature);
	}
}