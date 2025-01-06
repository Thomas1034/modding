package com.thomas.verdant.feature.custom;


import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.TallSeagrassBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.ProbabilityFeatureConfiguration;
import net.minecraft.world.level.material.Fluids;

public class FixedSeagrassFeature extends Feature<ProbabilityFeatureConfiguration> {
    public FixedSeagrassFeature(Codec<ProbabilityFeatureConfiguration> configurationCodec) {
        super(configurationCodec);
    }

    @Override
    public boolean place(FeaturePlaceContext<ProbabilityFeatureConfiguration> context) {
        boolean placementSucceeded = false;
        RandomSource random = context.random();
        WorldGenLevel level = context.level();
        BlockPos featureCenter = context.origin();
        ProbabilityFeatureConfiguration probabilityfeatureconfiguration = context.config();
        int xOffset = random.nextInt(8) - random.nextInt(8);
        int zOffset = random.nextInt(8) - random.nextInt(8);
        int yPosition = featureCenter.getY();
        BlockPos placementPosition = new BlockPos(
                featureCenter.getX() + xOffset,
                yPosition,
                featureCenter.getZ() + zOffset
        );
        BlockState atPlacementPosition = level.getBlockState(placementPosition);
        if (atPlacementPosition.is(Blocks.WATER) && atPlacementPosition.getFluidState().isSourceOfType(Fluids.WATER)) {
            boolean shouldBeTall = random.nextDouble() < (double) probabilityfeatureconfiguration.probability;
            BlockState seaGrassBottom = shouldBeTall ? Blocks.TALL_SEAGRASS.defaultBlockState() : Blocks.SEAGRASS.defaultBlockState();
            if (seaGrassBottom.canSurvive(level, placementPosition)) {
                if (shouldBeTall) {
                    BlockState seaGrassTop = seaGrassBottom.setValue(TallSeagrassBlock.HALF, DoubleBlockHalf.UPPER);
                    BlockPos topHalfPosition = placementPosition.above();
                    BlockState atTopHalfPosition = level.getBlockState(topHalfPosition);
                    if (atTopHalfPosition.is(Blocks.WATER) && atTopHalfPosition.getFluidState()
                            .isSourceOfType(Fluids.WATER)) {
                        level.setBlock(placementPosition, seaGrassBottom, 2);
                        level.setBlock(topHalfPosition, seaGrassTop, 2);
                    }
                } else {
                    level.setBlock(placementPosition, seaGrassBottom, 2);
                }

                placementSucceeded = true;
            }
        }

        return placementSucceeded;
    }
}

