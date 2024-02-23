package com.thomas.zirconmod.worldgen.custom;

import com.mojang.serialization.Codec;
import com.thomas.zirconmod.block.ModBlocks;
import com.thomas.zirconmod.util.Utilities;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class CloudFloorFeature extends Feature<NoneFeatureConfiguration> {

	private static final BlockState CLOUD = ModBlocks.CLOUD.get().defaultBlockState();
	private static final BlockState THUNDER_CLOUD = ModBlocks.THUNDER_CLOUD.get().defaultBlockState();
	private final ResourceKey<Biome> filter;
	private final BlockState state;

	public CloudFloorFeature(Codec<NoneFeatureConfiguration> codec, ResourceKey<Biome> filter, BlockState state) {
		super(codec);
		this.filter = filter;
		this.state = state;
	}

	// Places a small cloud.
	@Override
	public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
		WorldGenLevel level = context.level();
		BlockPos pos = context.origin();
		ChunkGenerator chunks = context.chunkGenerator();
		placeMultiLayer(level, pos, 8, this.state, this.filter);
		
		return true;
		
	}

	// Places a solid layer of cloud, if the biome at that position has the specified feature.
	public static void placeLayer(LevelAccessor level, BlockPos determinator, BlockState state, ResourceKey biome) {
		
		// If there is no chunk here, skip it.
		if (!level.hasChunkAt(determinator)) {
			System.out.println("Skipping " + determinator.getX() + " " + determinator.getY() + " " + determinator.getZ() + " ");
			return;
		}
		
		ChunkPos thisChunk = level.getChunk(determinator).getPos();
		BlockPos corner = new BlockPos(thisChunk.x, determinator.getY(), thisChunk.z);
		
		// Iterate over the chunk, checking the biome and placing each time.
		for (int i = 0; i < 16; i++) {
			for (int j = 0; j < 16; j++) {
				BlockPos here = thisChunk.getBlockAt(i, determinator.getY(), j);
				// Check the biome.
				boolean isCorrectBiome = level.getBiome(here).is(biome);
				if (isCorrectBiome) {
					
					Utilities.setSafe(level, here, state);
				}
			}
		}
		
	
	}

	// Creates a layer of clouds covering the entire chunk containing the BlockPos, with the BlockPos as the highest point.
	// Will only place a block at a position if the biome there supports the specified feature.
	public static void placeMultiLayer(LevelAccessor level, BlockPos determinator, int thickness, BlockState state, ResourceKey<Biome> biome) {
		int halfThick = thickness >> 1;
		for (int i = -halfThick; i < halfThick; i++) {
			placeLayer(level, determinator, state, biome);
		}
		
	}

}
