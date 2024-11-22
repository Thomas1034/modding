package com.thomas.verdant.util.blocktransformer;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.thomas.verdant.Constants;
import com.thomas.verdant.data.blocktransformer.BlockTransformerData;
import com.thomas.verdant.data.blocktransformer.BlockTransformerResultOption;
import com.thomas.verdant.util.AliasBuilder;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.Function;

// NOT THREAD-SAFE
public class BlockTransformer {

    public static final ResourceKey<Registry<BlockTransformer>> KEY = ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "block_transformer"));

    public static final Codec<List<BlockTransformerData>> DATA_LIST_CODEC = Codec.list(BlockTransformerData.CODEC).xmap(list -> {
        // Validation: Ensure that all objects in the list pass the required field check
        list.forEach(data -> {
            if (!BlockTransformerData.validateRequiredFields(data)) {
                throw new IllegalStateException("Block Transformer Data failed to validate");
            }
        });
        return list;
    }, list -> list);
    public static final Codec<BlockTransformer> CODEC = RecordCodecBuilder.create(instance -> instance.group(DATA_LIST_CODEC.fieldOf("values").forGetter(BlockTransformer::asData), ResourceLocation.CODEC.fieldOf("name").forGetter(bt->bt.name)).apply(instance, BlockTransformer::new));
    private final Map<TagKey<Block>, Function<RandomSource, Block>> tagMap;
    private final Object2IntMap<TagKey<Block>> tagPriorityMap;
    private final Map<Block, Function<RandomSource, Block>> directMap;
    private final List<ResourceLocation> fallbacks;
    private final List<BlockTransformerData> rawData;
    private final Map<ResourceLocation, BlockTransformer> cachedFallbacks;
    private int numTagsAdded;
    public final ResourceLocation name;

    public BlockTransformer(List<BlockTransformerData> values, ResourceLocation name) {
        this.tagMap = new HashMap<>();
        this.tagPriorityMap = new Object2IntOpenHashMap<>();
        this.tagPriorityMap.defaultReturnValue(-1);
        this.numTagsAdded = 0;
        this.directMap = new HashMap<>();
        this.fallbacks = new ArrayList<>();
        this.cachedFallbacks = new HashMap<>();
        this.rawData = values;
        this.name = name;

        this.fillData(this.rawData);

        // Reverse the list of fallbacks; this makes ones added last have higher priority.
        List<ResourceLocation> reversedCallbacks = new ArrayList<>(this.fallbacks.reversed());
        this.fallbacks.clear();
        this.fallbacks.addAll(reversedCallbacks);
    }

    private static Block getBlock(ResourceLocation location) {
        Block block = BuiltInRegistries.BLOCK.get(location);
        Objects.requireNonNull(block, "Unrecognized block " + location + "in BlockTransformer");
        return BuiltInRegistries.BLOCK.get(location);
    }

    private void fillData(List<BlockTransformerData> values) {
        for (BlockTransformerData toLoad : values) {
            // First, check if it's giving a transformer override.
            if (toLoad.transformer != null) {
                this.fallbacks.add(toLoad.transformer);
            }
            // If it's adding a block as the key, it could either be a direct mapping or a random chance.
            else if (toLoad.block != null) {
                if (toLoad.result != null) {
                    // It is a direct mapping.
                    this.addDirectMapping(getBlock(toLoad.block), getBlock(toLoad.result));
                } else if (toLoad.results != null) {
                    // It is not a direct mapping, which means this will be harder.
                    Object2IntMap<Block> temporaryMap = new Object2IntOpenHashMap<>();
                    // Load all the options into a map.
                    for (BlockTransformerResultOption option : toLoad.results) {
                        temporaryMap.put(getBlock(option.name), option.weight);
                    }
                    // Add the map.
                    this.addProbabilisticMapping(getBlock(toLoad.block), temporaryMap);
                }
            }
            // If it's adding a tag as the key, it could either be a direct mapping or a random chance.
            else if (toLoad.tag != null) {
                if (toLoad.result != null) {
                    // It is a direct mapping.
                    this.addDirectMapping(toLoad.tag, getBlock(toLoad.result));
                } else if (toLoad.results != null) {
                    // It is not a direct mapping, which means this will be harder.
                    Object2IntMap<Block> temporaryMap = new Object2IntOpenHashMap<>();
                    // Load all the options into a map.
                    for (BlockTransformerResultOption option : toLoad.results) {
                        temporaryMap.put(getBlock(option.name), option.weight);
                    }
                    // Add the map.
                    this.addProbabilisticMapping(toLoad.tag, temporaryMap);
                }
            }
            // Otherwise, we have a problem.
            else {
                throw new IllegalStateException("Unable to parse BlockTransformerData");
            }
        }
    }

    private void addDirectMapping(Block input, Block output) {
        this.directMap.put(input, (randomSource) -> output);
    }

    private void addDirectMapping(TagKey<Block> input, Block output) {
        this.tagMap.put(input, (randomSource) -> output);
        this.tagPriorityMap.put(input, this.numTagsAdded++);
    }

    private void addProbabilisticMapping(Block input, Map<Block, Integer> probabilities) {
        this.directMap.put(input, AliasBuilder.build(probabilities));
    }

    private void addProbabilisticMapping(TagKey<Block> input, Map<Block, Integer> probabilities) {
        this.tagMap.put(input, AliasBuilder.build(probabilities));
    }

    private Function<RandomSource, Block> getRaw(Block input, LevelAccessor level) {

        Function<RandomSource, Block> result = this.directMap.get(input);

        if (result == null) {
            result = this.getHighestPriorityTagMapping(input);
        }
        // If no result was found yet,
        if (result == null) {
            for (ResourceLocation fallback : this.fallbacks) {
                // Iterate until a valid option is found; then stop.
                result = this.getFallback(level, fallback).getRaw(input, level);
            }
        }
        return result;
    }

    // Storing the set of previously visited block transformers prevents infinite loops.
    public Block get(Block input, ServerLevelAccessor level) {
        Function<RandomSource, Block> raw = this.getRaw(input, level);
        return raw == null ? null : raw.apply(level.getRandom());
    }

    public BlockState get(BlockState input, ServerLevelAccessor level) {
        return copyProperties(input, this.get(input.getBlock(), level));
    }

    private Function<RandomSource, Block> getHighestPriorityTagMapping(Block block) {
        int highestPriority = -1;
        Function<RandomSource, Block> result = null;

        for (Object2IntMap.Entry<TagKey<Block>> entry : this.tagPriorityMap.object2IntEntrySet()) {
            int tagPriority = entry.getIntValue();
            TagKey<Block> tag = entry.getKey();
            if (tagPriority > highestPriority && block.builtInRegistryHolder().is(tag)) {
                result = this.tagMap.get(tag);
                highestPriority = tagPriority;
            }
        }

        return result;
    }


    public boolean isValidInput(LevelAccessor level, @NotNull BlockState input) {
        return this.isValidInput(level, input.getBlock());
    }

    public boolean isValidInput(LevelAccessor level, Block input) {
        return this.directMap.containsKey(input) || this.hasValidTagMapping(input) || this.hasValidFallbackMapping(level, input);
    }

    private boolean hasValidTagMapping(@NotNull Block input) {
        Set<TagKey<Block>> tags = this.tagMap.keySet();
        for (TagKey<Block> tag : tags) {
            if (input.builtInRegistryHolder().is(tag)) {
                return true;
            }
        }

        return false;
    }

    private boolean hasValidFallbackMapping(LevelAccessor level, @NotNull Block input) {
        for (ResourceLocation location : this.fallbacks) {
            if (this.getFallback(level, location).isValidInput(level, input)) {
                return true;
            }
        }
        return false;
    }

    // Note: this could cause an infinite loop if a fallback of this registry at any point lists this as a fallback.
    private BlockTransformer getFallback(LevelAccessor level, ResourceLocation location) {
        BlockTransformer cached = this.cachedFallbacks.get(location);
        if (cached != null) {
            return cached;
        }

        Registry<BlockTransformer> transformers = level.registryAccess().registryOrThrow(BlockTransformer.KEY);
        BlockTransformer transformer = transformers.get(location);
        if (transformer != null) {
            this.cachedFallbacks.put(location, transformer);
        } else {
            Constants.LOG.error("Unable to get Block Transformer: {}\nPlease report this error to the developer, including what other mods and data packs (if any) you were using.", location);
        }
        return transformer;
    }

    public List<BlockTransformerData> asData() {
        return this.rawData;
    }

    // Copies the properties of one block state onto the default state of another block, to whatever degree is possible.
    @SuppressWarnings("unchecked")
    public static BlockState copyProperties(BlockState input, Block to) {
        // Don't do unnecessary work.
        if (to == null || input.is(to)) {
            return input;
        }
        // Get the default state to copy properties onto.
        BlockState output = to.defaultBlockState();
        // Copy every property, if applicable.
        // Properties have type-parameters, but that sufficiently confuses the compiler here such that
        // raw types must be used.
        for (@SuppressWarnings("rawtypes") Property property : input.getProperties()) {
            // Skip if the output state does not have the desired property.
            if (output.hasProperty(property)) {
                // If it does, set it.
                output = output.trySetValue(property, input.getValue(property));
            }
        }

        return output;
    }

}
