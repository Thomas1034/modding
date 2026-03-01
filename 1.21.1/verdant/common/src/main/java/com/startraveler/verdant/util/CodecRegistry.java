package com.startraveler.verdant.util;

import com.google.common.collect.HashBiMap;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.minecraft.resources.Identifier;

public class CodecRegistry<T> {

    private final HashBiMap<Identifier, MapCodec<? extends T>> map = HashBiMap.create();

    public <S extends T> CodecRegistryEntry<S> register(Identifier location, MapCodec<S> type) {
        if (type != null) {
            this.map.put(location, type);
        } else {
            throw new IllegalArgumentException("Can only register one codec for " + location + "!");
        }

        return new CodecRegistryEntry<>(location, type);
    }

    public MapCodec<? extends T> byKey(Identifier location) {
        return this.map.get(location);
    }

    public Identifier byValue(MapCodec<?> type) {
        return this.map.inverse().get(type);
    }

    public Codec<MapCodec<? extends T>> forDispatch() {
        return Identifier.CODEC.xmap(
                this::byKey,
                this::byValue
        );
    }

    public record CodecRegistryEntry<S>(Identifier location, MapCodec<S> codec) {
    }

}

