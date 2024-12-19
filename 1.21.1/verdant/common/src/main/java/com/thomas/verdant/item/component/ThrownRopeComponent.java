package com.thomas.verdant.item.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record ThrownRopeComponent(int length, boolean hasHook) {

    public static final Codec<ThrownRopeComponent> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.INT.fieldOf("length").forGetter(ThrownRopeComponent::length),
            Codec.BOOL.fieldOf("hasHook").forGetter(ThrownRopeComponent::hasHook)
    ).apply(instance, ThrownRopeComponent::new));

}
