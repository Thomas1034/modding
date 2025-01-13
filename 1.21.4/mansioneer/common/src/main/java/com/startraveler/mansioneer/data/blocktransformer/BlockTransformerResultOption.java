package com.startraveler.mansioneer.data.blocktransformer;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;

public record BlockTransformerResultOption(ResourceLocation name, int weight) {

    // Codec for ResultItem (object with name and weight)
    public static final Codec<BlockTransformerResultOption> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    ResourceLocation.CODEC.fieldOf("name").forGetter(item -> item.name),
                    Codec.INT.optionalFieldOf("weight", 1).forGetter(item -> item.weight)
            ).apply(instance, BlockTransformerResultOption::new)
    );
}
