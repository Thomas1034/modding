package com.startraveler.verdant.util.featureset;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.startraveler.verdant.Constants;
import com.startraveler.verdant.util.AliasBuilder;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class FeatureSet {

    public static final ResourceKey<Registry<FeatureSet>> KEY = ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "feature_set"));

    public static final Codec<List<FeatureSet.Entry>> ENTRY_LIST_CODEC =
            FeatureSet.Entry.CODEC.listOf();

    public static final Codec<FeatureSet> CODEC = RecordCodecBuilder.create(instance -> instance.group(ENTRY_LIST_CODEC.fieldOf("entries").forGetter(FeatureSet::asEntries), ResourceLocation.CODEC.fieldOf("name").forGetter(bt -> bt.name)).apply(instance, FeatureSet::new));

    private final Object2IntMap<Entry> entries;
    private final Function<RandomSource, Entry> aliasMap;
    private final ResourceLocation name;

    public FeatureSet(List<Entry> entries, ResourceLocation name) {
        this.entries = new Object2IntOpenHashMap<>(entries.stream().collect(Collectors.toUnmodifiableMap(Entry::self, Entry::getWeight)));

        this.aliasMap = AliasBuilder.build(this.entries);
        this.name = name;
    }

    public List<Entry> asEntries() {
        return this.entries.keySet().stream().toList();
    }

    public void place(ServerLevel level, BlockPos pos) {
        aliasMap.apply(level.random).place(level, pos);
    }

    public abstract static class Entry {
        public static final Codec<FeatureSet.Entry> CODEC = new Codec<>() {
            @Override
            public <T> DataResult<T> encode(FeatureSet.Entry input, DynamicOps<T> ops, T prefix) {
                if (input instanceof RecursiveFeatureSetEntry recursive) {
                    return RecursiveFeatureSetEntry.CODEC.encodeStart(ops, recursive)
                            .map(result -> ops.mapBuilder()
                                    .add("type", ops.createString("recursive"))
                                    .add("entry", result)
                                    .build(prefix)).getOrThrow();
                } else if (input instanceof ConfiguredFeatureSetEntry configured) {
                    return ConfiguredFeatureSetEntry.CODEC.encodeStart(ops, configured)
                            .map(result -> ops.mapBuilder()
                                    .add("type", ops.createString("configured"))
                                    .add("entry", result)
                                    .build(prefix)).getOrThrow();
                }
                return DataResult.error(() -> "Unknown FeatureSetEntry implementation: " + input);
            }

            @Override
            public <T> DataResult<Pair<FeatureSet.Entry, T>> decode(DynamicOps<T> ops, T input) {
                Dynamic<T> dynamic = new Dynamic<>(ops, input);

                // Extract the "type" field
                String type = dynamic.get("type").asString().result().orElse(null);
                if (type == null) {
                    return DataResult.error(() -> "Missing 'type' field in FeatureSet.Entry");
                }

                // Extract the "entry" field
                T entryData = dynamic.get("entry").result().orElseThrow().getValue();
                if (entryData == null) {
                    return DataResult.error(() -> "Missing 'entry' field for type: " + type);
                }

                // Decode based on type
                return switch (type) {
                    case "recursive" -> RecursiveFeatureSetEntry.CODEC.decode(ops, entryData)
                            .map(pair -> Pair.of(pair.getFirst(), dynamic.getValue()));
                    case "configured" -> ConfiguredFeatureSetEntry.CODEC.decode(ops, entryData)
                            .map(pair -> Pair.of(pair.getFirst(), dynamic.getValue()));
                    default -> DataResult.error(() -> "Unknown type in FeatureSet.Entry: " + type);
                };
            }
        };

        protected final int weight;

        protected Entry(int weight) {
            this.weight = weight;
        }

        public int getWeight() {
            return this.weight;
        }

        public Entry self() {
            return this;
        }

        public abstract ResourceLocation getType();

        public abstract void place(ServerLevel level, BlockPos pos);
    }
}
