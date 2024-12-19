package com.thomas.verdant.item.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record RopeCoilData(int length, boolean hasHook) {
    public static final int MAX_LENGTH_FROM_CRAFTING = 32;

    public static final Codec<RopeCoilData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.INT.fieldOf("length").forGetter(RopeCoilData::length),
            Codec.BOOL.fieldOf("hasHook").forGetter(RopeCoilData::hasHook)
    ).apply(instance, RopeCoilData::new));

}
