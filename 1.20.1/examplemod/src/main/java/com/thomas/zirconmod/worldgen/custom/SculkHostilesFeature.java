package com.thomas.zirconmod.worldgen.custom;

import com.mojang.serialization.Codec;
import com.thomas.zirconmod.block.ModBlocks;
import com.thomas.zirconmod.util.SurfaceNotFoundException;
import com.thomas.zirconmod.util.Utilities;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class SculkHostilesFeature extends Feature<NoneFeatureConfiguration> {

	public static final BlockState JAW = ModBlocks.SCULK_JAW.get().defaultBlockState();
	public static final BlockState ROOT = ModBlocks.SCULK_ROOTS.get().defaultBlockState();
	public static final BlockState SCULK = Blocks.SCULK.defaultBlockState();

	public SculkHostilesFeature(Codec<NoneFeatureConfiguration> codec) {
		super(codec);
	}

	@Override
	public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
		WorldGenLevel level = context.level();
		BlockPos pos = context.origin();

		placeHostiles(level, pos);

		return true;
	}

	public static void placeHostiles(LevelAccessor level, BlockPos pos) {

		// Check what kind of thing to place.
		int whichType = level.getRandom().nextInt(10);

		// 20% bloom
		// 30% jaw
		// 50% roots
		if (whichType < 2) {
			placeSculkPatch(level, pos);
		} else if (whichType < 5) {
			// Place a jaw, surrounded by sculk.
			placeSculkPatch(level, pos);
			placeJaw(level, pos);
		} else {
			// Place roots.
			placeRoots(level, pos);
		}
	}

	// Places a sculk jaw.
	public static boolean placeJaw(LevelAccessor level, BlockPos pos) {
		try {
			return Utilities.setSculkSafe(level, Utilities.findSurface(level, pos, 5).below(), JAW);
		} catch (SurfaceNotFoundException e) {
			// If a surface is not found, do nothing.
			return false;
		}
	}

	// Places a patch of sculk roots.
	// Also places sculk underneath them.
	public static void placeRoots(LevelAccessor level, BlockPos pos) {
		PatchFeature.placeSurfacePatch(level, pos, SculkHostilesFeature::placeDecoratedRoot, 7);
	}

	// Places a single root.
	public static boolean placeRoot(LevelAccessor level, BlockPos pos) {
		try {
			return Utilities.setSafeNoFluid(level, Utilities.findSurface(level, pos, 5), ROOT);
		} catch (SurfaceNotFoundException e) {
			// If a surface is not found, do nothing.
			return false;
		}
	}

	// Places a single sculk block below the given position.
	public static boolean placeSculk(LevelAccessor level, BlockPos pos) {
		try {
			return Utilities.setSculk(level, Utilities.findSurface(level, pos, 5).below());
		} catch (SurfaceNotFoundException e) {
			// If a surface is not found, do nothing.
			return false;
		}
	}

	// Places a single sculk block below the given position, if there isn't one
	// already.
	public static boolean placeSculkIfNotAlready(LevelAccessor level, BlockPos pos) {
		BlockPos placePos;
		try {
			placePos = Utilities.findSurface(level, pos, 5).below();
		} catch (SurfaceNotFoundException e) {
			// If a surface is not found, do nothing.
			return false;
		}
		if (level.getBlockState(placePos).is(SCULK.getBlock())) {
			return true;
		}
		return Utilities.setSculk(level, placePos);
	}

	// Places a small patch of sculk blocks.
	public static void placeSculkPatch(LevelAccessor level, BlockPos pos) {
		PatchFeature.placeSurfacePatch(level, pos, (levelParam, posParam) -> placeSculkIfNotAlready(levelParam, posParam), 7);
	}

	// Places a sculk root on a sculk block.
	public static boolean placeDecoratedRoot(LevelAccessor level, BlockPos pos) {
		return placeSculkIfNotAlready(level, pos) && placeRoot(level, pos);
	}

}