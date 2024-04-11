package com.thomas.cloudscape.worldgen;

import java.util.List;

import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.heightproviders.BiasedToBottomHeight;
import net.minecraft.world.level.levelgen.heightproviders.ConstantHeight;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.RarityFilter;

public class ModPlacement {
	public static List<PlacementModifier> orePlacement(PlacementModifier p_195347_, PlacementModifier p_195348_) {
		return List.of(p_195347_, InSquarePlacement.spread(), p_195348_, BiomeFilter.biome());
	}

	public static List<PlacementModifier> commonOrePlacement(int pCount, PlacementModifier pHeightRange) {
		return orePlacement(CountPlacement.of(pCount), pHeightRange);
	}

	public static List<PlacementModifier> rareOrePlacement(int pChance, PlacementModifier pHeightRange) {
		return orePlacement(RarityFilter.onAverageOnceEvery(pChance), pHeightRange);
	}

	public static List<PlacementModifier> rareSurfacePlacement(int onceEvery) {
		return List.of(PlacementUtils.HEIGHTMAP_WORLD_SURFACE, RarityFilter.onAverageOnceEvery(onceEvery), BiomeFilter.biome());
	}
	
	public static List<PlacementModifier> commonSurfacePlacement(int howMany) {
		return List.of(PlacementUtils.HEIGHTMAP_WORLD_SURFACE, CountPlacement.of(howMany), BiomeFilter.biome());
	}
	
	public static List<PlacementModifier> rareSurfaceIncludingCloudPlacement(int onceEvery) {
		return List.of(PlacementUtils.HEIGHTMAP, RarityFilter.onAverageOnceEvery(onceEvery), BiomeFilter.biome());
	}
	
	public static List<PlacementModifier> commonSurfaceIncludingCloudPlacement(int howMany) {
		return List.of(PlacementUtils.HEIGHTMAP, CountPlacement.of(howMany), BiomeFilter.biome());
	}
	
	public static List<PlacementModifier> surfacePlacement(int howMany) {
		return List.of(PlacementUtils.HEIGHTMAP_WORLD_SURFACE, CountPlacement.of(howMany), BiomeFilter.biome());
	}
	
	public static List<PlacementModifier> cavePlacement(int howMany) {
		return List.of(CountPlacement.of(ConstantInt.of(256)), InSquarePlacement.spread(), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, BiomeFilter.biome(), CountPlacement.of(howMany));
	}
	
	public static List<PlacementModifier> rareCavePlacement(int onceEvery) {
		return List.of(CountPlacement.of(ConstantInt.of(256)), InSquarePlacement.spread(), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, BiomeFilter.biome(), RarityFilter.onAverageOnceEvery(onceEvery));
	}
	
	public static List<PlacementModifier> aboveBottomPlacement(int y, PlacementModifier otherModifier) {
		return List.of(otherModifier, HeightRangePlacement.of(ConstantHeight.of(VerticalAnchor.aboveBottom(y))), BiomeFilter.biome());
	}
	
	public static List<PlacementModifier> aboveBottomRangePlacement(int y1, int y2, PlacementModifier otherModifier) {
		return List.of(otherModifier, HeightRangePlacement.of(BiasedToBottomHeight.of(VerticalAnchor.aboveBottom(y1), VerticalAnchor.aboveBottom(y2), (y2 - y1)/4)), BiomeFilter.biome());
	}
	
	
	
	public static List<PlacementModifier> commonAboveBottomPlacement(int y, int count) {
		return aboveBottomPlacement(y, CountPlacement.of(count));
	}
	
	public static List<PlacementModifier> rareAboveBottomPlacement(int y, int onceEvery) {
		return aboveBottomPlacement(y, RarityFilter.onAverageOnceEvery(onceEvery));
	}
	
	public static List<PlacementModifier> commonAboveBottomRangePlacement(int y1, int y2, int count) {
		return aboveBottomRangePlacement(y1, y2, CountPlacement.of(count));
	}
	
	public static List<PlacementModifier> rareAboveBottomRangePlacement(int y1, int y2, int onceEvery) {
		return aboveBottomRangePlacement(y1, y2, RarityFilter.onAverageOnceEvery(onceEvery));
	}
}
