package com.thomas.cloudscape.worldgen.tree.custom;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.thomas.cloudscape.block.ModBlocks;
import com.thomas.cloudscape.block.custom.FrondBlock;
import com.thomas.cloudscape.worldgen.tree.ModFoliagePlacers;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;

public class PalmFoliagePlacer extends FoliagePlacer {
	public static final Codec<PalmFoliagePlacer> CODEC = RecordCodecBuilder
			.create(palmFoliagePlacerInstance -> foliagePlacerParts(palmFoliagePlacerInstance)
					.and(Codec.intRange(0, 16).fieldOf("height").forGetter(fp -> fp.height))
					.apply(palmFoliagePlacerInstance, PalmFoliagePlacer::new));
	private final int height;

	public PalmFoliagePlacer(IntProvider radius, IntProvider offset, int height) {
		super(radius, offset);
		this.height = height;
	}

	@Override
	protected FoliagePlacerType<?> type() {
		return ModFoliagePlacers.PALM_FOLIAGE_PLACER.get();
	}

	@Override
	protected void createFoliage(LevelSimulatedReader level, FoliageSetter blockSetter, RandomSource rand,
			TreeConfiguration config, int maxFreeTreeHeight, FoliageAttachment attachment, int foliageHeight,
			int foliageRadius, int offset) {

		blockSetter.set(attachment.pos().above(1), ModBlocks.PALM_FLOOR_FROND.get().defaultBlockState());
		// Place each row of fronds.
		int numRows = 1 + (rand.nextBoolean() && maxFreeTreeHeight > 1 ? 1 : 0);
		for (int i = 0; i < numRows; i++) {
			BlockPos trunk = attachment.pos().below(i);
			// Place fronds on each direction.
			for (Direction dir : Direction.values()) {
				// Skip the vertical axis.
				if (dir.getAxis() == Axis.Y)
					continue;
				// Get the location of the frond.
				BlockPos at = trunk.relative(dir);
				// Get the state of the frond.
				BlockState frond = ModBlocks.PALM_FROND.get().defaultBlockState()
						.setValue(FrondBlock.getFacingProperty(), dir)
						.setValue(FrondBlock.getCountProperty(), 3);
				// Place the frond.
				blockSetter.set(at, frond);
			}
		}
	}

	@Override
	public int foliageHeight(RandomSource random, int height, TreeConfiguration config) {
		return this.height;
	}

	@Override
	protected boolean shouldSkipLocation(RandomSource random, int localX, int localY, int localZ, int range,
			boolean large) {
		return false;
	}
}