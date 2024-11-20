package com.thomas.verdant.data.blocktransformer;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

import java.util.List;
import java.util.Optional;

public class BlockTransformerData {
    public static final Codec<TagKey<Block>> TAG_CODEC = RecordCodecBuilder.create(instance -> instance.group(ResourceLocation.CODEC.fieldOf("location").forGetter(TagKey::location)).apply(instance, location -> TagKey.create(Registries.BLOCK, location)));
    // Codec for TransformerData
    public static final Codec<BlockTransformerData> CODEC = RecordCodecBuilder.create(instance -> instance.group(ResourceLocation.CODEC.optionalFieldOf("transformer").forGetter(data -> data.transformer == null ? Optional.<ResourceLocation>empty() : Optional.of(data.transformer)), ResourceLocation.CODEC.optionalFieldOf("result").forGetter(data -> data.result == null ? Optional.<ResourceLocation>empty() : Optional.of(data.result)), Codec.list(BlockTransformerResultOption.CODEC).optionalFieldOf("results").forGetter(data -> data.results == null ? Optional.<List<BlockTransformerResultOption>>empty() : Optional.of(data.results)), TAG_CODEC.optionalFieldOf("tag").forGetter(data -> data.tag == null ? Optional.<TagKey<Block>>empty() : Optional.of(data.tag)), ResourceLocation.CODEC.optionalFieldOf("block").forGetter(data -> data.block == null ? Optional.<ResourceLocation>empty() : Optional.of(data.block))).apply(instance, BlockTransformerData::new));
    public final ResourceLocation transformer;
    public final ResourceLocation result;
    public final List<BlockTransformerResultOption> results;
    public final TagKey<Block> tag;
    public final ResourceLocation block;


    public BlockTransformerData(Optional<ResourceLocation> transformer, Optional<ResourceLocation> result, Optional<List<BlockTransformerResultOption>> results, Optional<TagKey<Block>> tag, Optional<ResourceLocation> block) {
        this(transformer.orElse(null), result.orElse(null), results.orElse(null), tag.orElse(null), block.orElse(null));
    }

    public BlockTransformerData(ResourceLocation transformer, ResourceLocation result, List<BlockTransformerResultOption> results, TagKey<Block> tag, ResourceLocation block) {
        this.transformer = transformer;
        this.result = result;
        this.results = results;
        this.tag = tag;
        this.block = block;

        if (!validateRequiredFields(this)) {
            throw new IllegalArgumentException("Failed to validate required fields for BlockTransformerData!");
        }
    }

    // Method to validate that at least one of transformer, result, or results is present
    public static boolean validateRequiredFields(BlockTransformerData data) {
        boolean defersToParent = (data.transformer != null) && (data.result == null && data.results == null) && (data.tag == null && data.block == null);
        boolean specifiesResult = (data.transformer == null) && (data.result != null || data.results != null) && (data.tag != null || data.block != null) && !(data.tag != null && data.block != null);
        return defersToParent || specifiesResult;
    }


}