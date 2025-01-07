package com.thomas.verdant.feature.custom;

import com.mojang.serialization.Codec;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

public class StranglerVineFeature extends Feature {
    public StranglerVineFeature(Codec codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext featurePlaceContext) {
        return false;
    }
}
