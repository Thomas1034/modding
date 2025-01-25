package com.thomas.verdant.item.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record DurabilityChanging(int perTick, int tickEvery, boolean randomize) {
    public static final DurabilityChanging HEARTWOOD_ARMOR = new DurabilityChanging(1, 60, false);
    public static final DurabilityChanging HEARTWOOD_TOOLS = new DurabilityChanging(3, 600, false);

    public static final Codec<DurabilityChanging> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.INT.fieldOf("perTick").forGetter(DurabilityChanging::perTick),
            Codec.INT.fieldOf("tickEvery").forGetter(DurabilityChanging::tickEvery),
            Codec.BOOL.fieldOf("randomize").forGetter(DurabilityChanging::randomize)
    ).apply(instance, DurabilityChanging::new));
}
