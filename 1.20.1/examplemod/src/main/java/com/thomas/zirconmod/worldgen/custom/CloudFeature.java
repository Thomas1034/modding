package com.thomas.zirconmod.worldgen.custom;

import java.util.ArrayList;

import com.mojang.serialization.Codec;
import com.thomas.zirconmod.block.ModBlocks;
import com.thomas.zirconmod.util.Utilities;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class CloudFeature extends Feature<NoneFeatureConfiguration> {

	private static final BlockState CLOUD = ModBlocks.CLOUD.get().defaultBlockState();
	private static final BlockState THUNDER_CLOUD = ModBlocks.THUNDER_CLOUD.get().defaultBlockState();
	private int size;

	public CloudFeature(Codec<NoneFeatureConfiguration> codec, int size) {
		super(codec);
		this.size = size;
	}

	// Places a small cloud.
	@Override
	public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
		WorldGenLevel level = context.level();
		BlockPos pos = context.origin();
		ChunkGenerator chunks = context.chunkGenerator();

		// Find an offset point within 16 blocks of the position, horizontally.
		pos = pos.offset(level.getRandom().nextInt(16), 0, level.getRandom().nextInt(16));

		placeMultiLayer(level, pos, size, level.getRandom().nextDouble() < 0.0078125 ? THUNDER_CLOUD : CLOUD);

		return true;
		
	}

	// Places a layer of a small cloud around the specified anchor points.
	public static void placeLayer(LevelAccessor level, BlockPos anchor1, BlockPos anchor2, BlockPos anchor3, BlockState state) {

		ArrayList<BlockPos> locations = Utilities.getEnclosedGridPoints(anchor1, anchor2, anchor3);
		for (BlockPos location : locations) {
			// Fill the block.
			if (level.getBlockState(location).isAir()) level.setBlock(location, state, 3);
		}

	}

	// Creates multiple layers of a small cloud around the specified anchor points
	// for the center (largest) layer.
	public static void placeMultiLayer(LevelAccessor level, BlockPos center, int size, BlockState state) {

		// Get angles of the points.
		double th1 = level.getRandom().nextDouble() * 2 * Math.PI;
		double th2 = th1 + 2 * Math.PI / 3;
		double th3 = th2 + 2 * Math.PI / 3;

		// Get center radii
		double r1 = level.getRandom().nextIntBetweenInclusive(size - 1, size + 1);
		double r2 = level.getRandom().nextIntBetweenInclusive(size - 1, size + 1);
		double r3 = level.getRandom().nextIntBetweenInclusive(size - 1, size + 1);

		// Above center.
		int alt = 0;

		while (r1 > 3 && r2 > 3 && r3 > 3) {

			BlockPos anchor1 = center.offset((int) (r1 * Math.cos(th1)), 0, (int) (r1 * Math.sin(th1)));
			BlockPos anchor2 = center.offset((int) (r2 * Math.cos(th2)), 0, (int) (r2 * Math.sin(th2)));
			BlockPos anchor3 = center.offset((int) (r3 * Math.cos(th3)), 0, (int) (r3 * Math.sin(th3)));

			placeLayer(level, anchor1.above(alt), anchor2.above(alt), anchor3.above(alt), state);
			placeLayer(level, anchor1.below(alt / 2), anchor2.below(alt / 2), anchor3.below(alt / 2), state);
			r1 -= level.getRandom().nextInt(alt + 1);
			r2 -= level.getRandom().nextInt(alt + 1);
			r3 -= level.getRandom().nextInt(alt + 1);
			alt++;
		}

	}

}
