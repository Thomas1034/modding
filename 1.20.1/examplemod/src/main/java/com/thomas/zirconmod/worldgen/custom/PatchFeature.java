package com.thomas.zirconmod.worldgen.custom;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

import com.mojang.serialization.Codec;
import com.thomas.zirconmod.util.SurfaceNotFoundException;
import com.thomas.zirconmod.util.Utilities;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.LakeFeature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public class PatchFeature extends Feature<NoneFeatureConfiguration> {

	private final Block block;
	private final Block border;
	private final int radius;

	public PatchFeature(Codec<NoneFeatureConfiguration> codec, Block block, Block border) {
		super(codec);
		this.block = block;
		this.border = border;
		this.radius = 8;
	}
	
	public PatchFeature(Codec<NoneFeatureConfiguration> codec, Block block, Block border, int radius) {
		super(codec);
		this.block = block;
		this.border = border;
		this.radius = radius;
	}

	@Override
	public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
		WorldGenLevel level = context.level();
		BlockPos pos = Utilities.findSurface(level, context.origin());
		ChunkGenerator chunks = context.chunkGenerator();
		
		placePatch(level, pos, this.block, this.radius);

		return true;
	}

	public static void placeLakePatch(WorldGenLevel level, ChunkGenerator chunks, BlockPos pos, Block block,
			Block border) {

		LakeFeature.Configuration config = new LakeFeature.Configuration(BlockStateProvider.simple(block),
				BlockStateProvider.simple(border));

		(new LakeFeature(config.CODEC)).place(config, level, chunks, level.getRandom(), pos);

	}

	public static void placePatch(LevelAccessor level, BlockPos pos, Block block, int radius) {
		// Picks three points around the given radius.
		BlockPos p1 = (Utilities.withinCircle(level.getRandom(), radius - 3, radius + 2));
		BlockPos p2 = (Utilities.withinCircle(level.getRandom(), radius - 3, radius + 2));
		BlockPos p3 = (Utilities.withinCircle(level.getRandom(), radius - 3, radius + 2));
		
		// Iterates over a blob enclosing these points.
		ArrayList<BlockPos> locations = Utilities.getEnclosedGridPoints(p1, p2, p3);
		
		for (BlockPos location : locations) {

			// Find the ground surface at that location.
			BlockPos placePos = Utilities.findSurface(level, pos.offset(location)).below();

			// Fill the top block of ground.
			Utilities.setSafe(level, placePos, block.defaultBlockState());
		}
	}
	
	public static void placePatch(LevelAccessor level, BlockPos pos, Block block, int radius, List<Integer> heights) {
		// Picks three points around the given radius.
		BlockPos p1 = (Utilities.withinCircle(level.getRandom(), radius - 3, radius + 2));
		BlockPos p2 = (Utilities.withinCircle(level.getRandom(), radius - 3, radius + 2));
		BlockPos p3 = (Utilities.withinCircle(level.getRandom(), radius - 3, radius + 2));
		
		// Iterates over a circle with the given radius.
		ArrayList<BlockPos> locations = Utilities.getEnclosedGridPoints(p1, p2, p3);
		
		for (BlockPos location : locations) {
			for (Integer height : heights)
			{
				// Find the ground surface at that location.
				BlockPos placePos = Utilities.findSurface(level, pos.offset(location).offset(0, height, 0)).below();

				// Fill the block of ground.
				Utilities.setSafe(level, placePos, block.defaultBlockState());
			}
		}
	}
	
	// Calls the function on each surface block.
	public static void placeSurfacePatch(LevelAccessor level, BlockPos pos, BiFunction<LevelAccessor, BlockPos, ?> function, int radius) {
		// Picks three points around the given radius.
		BlockPos p1 = (Utilities.withinCircle(level.getRandom(), radius - 3, radius + 2));
		BlockPos p2 = (Utilities.withinCircle(level.getRandom(), radius - 3, radius + 2));
		BlockPos p3 = (Utilities.withinCircle(level.getRandom(), radius - 3, radius + 2));
		
		// Iterates over a circle with the given radius.
		ArrayList<BlockPos> locations = Utilities.getEnclosedGridPoints(p1, p2, p3);
		
		for (BlockPos location : locations) {

			// Find the ground surface at that location.
			BlockPos placePos;
			try {
				placePos = Utilities.findSurfaceForWorldgen(level, pos.offset(location), 5).below();
			} catch (SurfaceNotFoundException e) {
				continue;
			}
			
			function.apply(level, placePos);
			
		}
	}
}
