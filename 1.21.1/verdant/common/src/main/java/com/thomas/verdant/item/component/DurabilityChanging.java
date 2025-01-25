package com.thomas.verdant.item.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record DurabilityChanging(int perTick, int tickEvery, boolean randomize) {
    public static final int MAX_LENGTH_FROM_CRAFTING = 32;

    public static final Codec<DurabilityChanging> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.INT.fieldOf("perTick").forGetter(DurabilityChanging::perTick),
            Codec.INT.fieldOf("tickEvery").forGetter(DurabilityChanging::tickEvery),
            Codec.BOOL.fieldOf("randomize").forGetter(DurabilityChanging::randomize)
    ).apply(instance, DurabilityChanging::new));
}
