package com.thomas.verdant.util.baitdata;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.thomas.verdant.Constants;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.NotNull;

import java.util.Comparator;
import java.util.Optional;

public record BaitData(ResourceLocation location, BaitData.InnerData data, boolean isTag) {

    public static final ResourceKey<Registry<BaitData>> KEY = ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "bait_data"));


    public static Codec<BaitData> CODEC = RecordCodecBuilder.create(instance -> instance.group(ResourceLocation.CODEC.optionalFieldOf("item").forGetter(data -> !data.isTag() ? Optional.of(data.location()) : Optional.empty()), ResourceLocation.CODEC.optionalFieldOf("tag").forGetter(data -> data.isTag() ? Optional.of(data.location()) : Optional.empty()), InnerData.CODEC.fieldOf("data").forGetter(BaitData::data)).apply(instance, BaitData::create));

    public static BaitData create(Optional<ResourceLocation> item, Optional<ResourceLocation> tag, InnerData data) {
        if (item.isPresent() && tag.isPresent()) {
            throw new IllegalArgumentException("Cannot construct a BaitData instance that corresponds to both an item and a tag.");
        } else if (item.isEmpty() && tag.isEmpty()) {
            throw new IllegalArgumentException("Cannot construct a BaitData instance that is not bound to either an item or a tag.");
        }

        return item.map(resourceLocation -> new BaitData(resourceLocation, data, false)).orElseGet(() -> new BaitData(tag.get(), data, true));
    }

    public boolean matches(Item item) {
        if (this.isTag()) return false;
        return this.location.equals(BuiltInRegistries.ITEM.getKey(item));
    }

    public boolean matches(TagKey<Item> tag) {
        if (!this.isTag()) return false;
        return this.location.equals(tag.location());
    }

    public static int compare(BaitData a, BaitData b) {
        return a.data.compareTo(b.data);
    }

    public record InnerData(float catchChance, float consumeChance) implements Comparable<InnerData>{
        public static Codec<InnerData> CODEC = RecordCodecBuilder.create(instance -> instance.group(Codec.FLOAT.fieldOf("catch_chance").forGetter(InnerData::catchChance), Codec.FLOAT.fieldOf("consume_chance").forGetter(InnerData::consumeChance)).apply(instance, InnerData::new));

        public static Comparator<InnerData> COMPARATOR = Comparator.comparing(InnerData::catchChance).thenComparing(InnerData::consumeChance);;

        @Override
        public int compareTo(@NotNull BaitData.InnerData o) {
            return COMPARATOR.compare(this, o);
        }
    }

}
