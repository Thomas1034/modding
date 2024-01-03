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

	
	public static void bootstrap(BootstapContext<PlacedFeature> context) {
		HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);

		register(context, ZIRCON_ORE_PLACED_KEY,
				configuredFeatures.getOrThrow(ModConfiguredFeatures.OVERWORLD_ZIRCON_ORE_KEY),
				ModOrePlacement.commonOrePlacement(20,
						HeightRangePlacement.triangle(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(320))));
		
		register(context, CITRINE_GEODE_PLACED_KEY,
				configuredFeatures.getOrThrow(ModConfiguredFeatures.OVERWORLD_CITRINE_GEODE_KEY),
				ModOrePlacement.rareOrePlacement(64,
						HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(32))));

        register(context, PALM_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.PALM_KEY),
                VegetationPlacements.treePlacement(RarityFilter.onAverageOnceEvery(2),
                        ModBlocks.PALM_SAPLING.get()));
        
        register(context, PALM_PLACED_JUNGLE_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.PALM_JUNGLE_KEY),
                VegetationPlacements.treePlacement(PlacementUtils.countExtra(3, 0.1f, 2),
                        ModBlocks.PALM_SAPLING.get()));
	}

	private static ResourceKey<PlacedFeature> registerKey(String name) {
		return ResourceKey.create(Registries.PLACED_FEATURE, new ResourceLocation(ZirconMod.MOD_ID, name));
	}

	private static void register(BootstapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key,
			Holder<ConfiguredFeature<?, ?>> configuration, List<PlacementModifier> modifiers) {
		context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
	}
}