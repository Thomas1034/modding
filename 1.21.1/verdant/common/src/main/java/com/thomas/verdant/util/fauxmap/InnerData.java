package com.thomas.verdant.util.fauxmap;

import com.mojang.serialization.Codec;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

public interface InnerData {

    Map<String, Codec<?>> CODEC_MAP = new HashMap<>();

    static <T> void register(ResourceLocation name, Codec<T> codec) {
        CODEC_MAP.put(name, codec);
    }

}
