package com.thomas.verdant.util.baitdata;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;

public interface Type {
    BiMap<ResourceLocation, Type> MAP = HashBiMap.create();

    public static Codec<Type> CODEC = RecordCodecBuilder.create(instance -> instance.group(ResourceLocation.CODEC.fieldOf("key").forGetter(Type::location)).apply(instance, Type::of));


    static Type of(ResourceLocation location) {
        return MAP.get(location);
    }

    public abstract ResourceLocation location();

}
