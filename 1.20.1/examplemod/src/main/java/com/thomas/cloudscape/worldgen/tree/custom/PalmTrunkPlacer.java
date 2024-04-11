package com.thomas.cloudscape.worldgen.tree.custom;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.thomas.cloudscape.worldgen.tree.ModTrunkPlacerTypes;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;

public class PalmTrunkPlacer extends TrunkPlacer {
	public static final Codec<PalmTrunkPlacer> CODEC = RecordCodecBuilder
			.create(pineTrunkPlacerInstance -> trunkPlacerParts(pineTrunkPlacerInstance).apply(pineTrunkPlacerInstance,
					PalmTrunkPlacer::new));

	public PalmTrunkPlacer(int baseHeight, int heightRandA, int heightRandB) {
		super(baseHeight, heightRandA, heightRandB);
	}

	@Override
	protected TrunkPlacerType<?> type() {
		return ModTrunkPlacerTypes.PALM_TRUNK_PLACER.get();
	}

	@Override
	public List<FoliagePlacer.FoliageAttachment> placeTrunk(LevelSimulatedReader level,
			BiConsumer<BlockPos, BlockState> blockSetter, RandomSource rand, int freeTreeHeight, BlockPos startPos,
			TreeConfiguration config) {
		// THIS IS WHERE BLOCK PLACING LOGIC GOES
		ArrayList<BlockPos> foliageLocations = new ArrayList<>();

		// Sets dirt if the growth site is invalid.
		if (!isValidGrowthSite(level, startPos.below()))
			setDirtAt(level, blockSetter, rand, startPos.below(), config);

		// Gets the height of the tree.
		int heightBonus = getHeightBonus(level, startPos.below());
		int height = heightBonus + this.baseHeight + rand.nextIntBetweenInclusive(heightRandA, heightRandA + 3)
				+ rand.nextIntBetweenInclusive(heightRandB - 1, heightRandB + 1);

		// Places the trunk.
		for (int i = 0; i <= height; i++) {
			placeLog(level, blockSetter, rand, startPos.above(i), config);
		}
		// Adds in the foliage location.
		foliageLocations.add(startPos.above(height));

		ArrayList<FoliagePlacer.FoliageAttachment> foliageAttachments = new ArrayList<>();
		for (BlockPos foliagePos : foliageLocations) {
			foliageAttachments.add(new FoliagePlacer.FoliageAttachment(foliagePos, 0, false));
		}

		return ImmutableList.copyOf(foliageAttachments);
	}

	private int getHeightBonus(LevelSimulatedReader level, BlockPos below) {

		if (level.isStateAtPosition(below, state -> state.is(Blocks.PODZOL)))
			return 6;
		else if (level.isStateAtPosition(below, state -> state.is(Blocks.GRASS_BLOCK)))
			return 3;
		else if (level.isStateAtPosition(below, state -> state.is(Blocks.COARSE_DIRT)))
			return -2;
		else
			return 0;
	}

	private boolean isValidGrowthSite(LevelSimulatedReader level, BlockPos below) {
		if (level.isStateAtPosition(below, state -> state.is(Blocks.PODZOL)))
			return true;
		else if (level.isStateAtPosition(below, state -> state.is(Blocks.GRASS_BLOCK)))
			return true;
		else if (level.isStateAtPosition(below, state -> state.is(Blocks.DIRT)))
			return true;
		else if (level.isStateAtPosition(below, state -> state.is(Blocks.ROOTED_DIRT)))
			return true;
		else if (level.isStateAtPosition(below, state -> state.is(Blocks.MOSS_BLOCK)))
			return true;
		else if (level.isStateAtPosition(below, state -> state.is(Blocks.SAND)))
			return true;
		else if (level.isStateAtPosition(below, state -> state.is(Blocks.RED_SAND)))
			return true;
		else if (level.isStateAtPosition(below, state -> state.is(Blocks.COARSE_DIRT)))
			return true;
		else
			return false;
	}

}