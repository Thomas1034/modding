package com.thomas.zirconmod.worldgen.biome.surface;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.SurfaceRules;

public class ModSurfaceRules {
	private static final SurfaceRules.RuleSource DIRT = makeStateRule(Blocks.DIRT);
	private static final SurfaceRules.RuleSource GRASS_BLOCK = makeStateRule(Blocks.GRASS_BLOCK);

	public static SurfaceRules.RuleSource makeRules() {
		SurfaceRules.ConditionSource isAtOrAboveWaterLevel = SurfaceRules.waterBlockCheck(-1, 0);

		SurfaceRules.RuleSource grassSurface = SurfaceRules
				.sequence(SurfaceRules.ifTrue(isAtOrAboveWaterLevel, GRASS_BLOCK), DIRT);

		SurfaceRules.RuleSource normalRules = SurfaceRules
				.sequence(SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, SurfaceRules
						.sequence(SurfaceRules.ifTrue(SurfaceRules.not(SurfaceRules.UNDER_FLOOR), grassSurface))));

		return normalRules; // SurfaceRules.sequence(
		// Default to a grass and dirt surface
		// SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, grassSurface));

	}

	private static SurfaceRules.RuleSource makeStateRule(Block block) {
		return SurfaceRules.state(block.defaultBlockState());
	}
}