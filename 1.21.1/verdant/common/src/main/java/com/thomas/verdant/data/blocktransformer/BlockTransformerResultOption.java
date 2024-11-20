package com.thomas.verdant.data.blocktransformer;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;

public class BlockTransformerResultOption {
    public final ResourceLocation name;
    public final int weight;

    public BlockTransformerResultOption(ResourceLocation name, int weight) {
        this.name = name;
        this.weight = weight;
    }

    // Codec for ResultItem (object with name and weight)
    public static final Codec<BlockTransformerResultOption> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    ResourceLocation.CODEC.fieldOf("name").forGetter(item -> item.name),
                    Codec.INT.optionalFieldOf("weight", 1).forGetter(item -> item.weight)
            ).apply(instance, BlockTransformerResultOption::new)
    );
}
