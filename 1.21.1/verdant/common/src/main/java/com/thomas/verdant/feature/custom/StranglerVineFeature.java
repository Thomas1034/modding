package com.thomas.verdant.feature.custom;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class StranglerVineFeature extends Feature<NoneFeatureConfiguration> {

    public StranglerVineFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext featurePlaceContext) {

        BlockPos origin = featurePlaceContext.origin();
        WorldGenLevel level = featurePlaceContext.level();

        // TODO ((StranglerVineBlock) BlockRegistry.STRANGLER_VINE.get()).spread(level, origin);

        return false;
    }
}
