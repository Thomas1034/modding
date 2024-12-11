package com.thomas.verdant.util.fauxmap;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

public record RegistryReference<T>(ResourceLocation location, ResourceKey<Registry<T>> registryKey) {

    public static final Codec<RegistryReference<?>> CODEC = RecordCodecBuilder.create(instance -> instance.group(ResourceLocation.CODEC.fieldOf("key").forGetter(RegistryReference::location), ResourceLocation.CODEC.fieldOf("key").forGetter((o) -> o.registryKey().location())).apply(instance, (location, registryKey) -> new RegistryReference<>(location, ResourceKey.createRegistryKey(registryKey))));

    public T resolve(RegistryAccess registryAccess) {
        Registry<T> registry = registryAccess.lookupOrThrow(registryKey);
        return registry.getValue(location);
    }
}