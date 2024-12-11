package com.thomas.verdant.util.fauxmap;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Map;
import java.util.Objects;

/**
 * A codec that facilitates serialization and deserialization of polymorphic data structures.
 * The type of data is determined by a string field, which maps to a specific codec
 * from a provided registry of codecs. This enables dynamic typing within the serialized structure.
 *
 * @author ChatGPT
 */
public class PolymorphicCodec<T> {
    private final Map<ResourceLocation, Codec<? extends T>> codecMap;

    public PolymorphicCodec(Map<ResourceLocation, Codec<? extends T>> codecMap) {
        this.codecMap = codecMap;
    }

    public Codec<Pair<ResourceLocation, T>> createCodec() {
        return RecordCodecBuilder.create(instance -> instance.group(
                ResourceLocation.CODEC.fieldOf("type").forGetter(Pair::getLeft), // Field for the type string
                Codec.PASSTHROUGH.flatXmap(
                        this::decodeDynamic,
                        this::encodeDynamic
                ).fieldOf("data").forGetter(p -> p) // Field for dynamically typed data
        ).apply(instance, (location, t) -> Pair.of(t.getLeft(), t.getRight())));
    }

    private DataResult<Dynamic<?>> encodeDynamic(Pair<ResourceLocation, T> input) {
        ResourceLocation type = input.getLeft();
        T value = input.getRight();

        Codec<? super T> specificCodec = (Codec<? super T>) codecMap.get(type);
        if (specificCodec == null) {
            return DataResult.error(() -> "Unknown type: " + type);
        }
        return specificCodec.encodeStart(JsonOps.INSTANCE, value)
                .map(encoded -> new Dynamic<>(JsonOps.INSTANCE, encoded));
    }

    private DataResult<Pair<ResourceLocation, T>> decodeDynamic(Dynamic<?> input) {
        ResourceLocation type = Objects.requireNonNull(input.get("type").decode(ResourceLocation.CODEC).result().orElse(null)).getFirst();
        if (type == null) {
            return DataResult.error(() -> "Missing or invalid type field");
        }

        Codec<? extends T> specificCodec = codecMap.get(type);
        if (specificCodec == null) {
            return DataResult.error(() -> "Unknown type: " + type);
        }

        return specificCodec.parse(input.get("data").result().orElseThrow())
                .map(decoded -> Pair.of(type, decoded));
    }
}