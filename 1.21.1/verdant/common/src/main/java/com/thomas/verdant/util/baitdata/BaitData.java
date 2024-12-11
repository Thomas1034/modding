package com.thomas.verdant.util.baitdata;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;

public abstract class BaitData<T> {

    public static Codec<BaitData<?>> CODEC = RecordCodecBuilder.create(instance -> instance.group(DefaultType.CODEC.fieldOf("type").forGetter(BaitData::getType), ResourceLocation.CODEC.fieldOf("key").forGetter(BaitData::getKeyLocation), InnerData.CODEC.fieldOf("data").forGetter(BaitData::getData)).apply(instance, BaitData::Factory));

    protected final T key;
    protected final ResourceLocation keyLocation;
    protected final InnerData innerData;

    public BaitData(T key, ResourceLocation keyLocation, InnerData innerData) {
        this.keyLocation = keyLocation;
        this.innerData = innerData;
        this.key = this.calculateKey();
    }

    protected abstract T calculateKey();

    protected static <T> BaitData<T> Factory(Type type, ResourceLocation key, InnerData innerData) {
        return null;
    }

    public InnerData getData() {
        return this.innerData;
    }

    public ResourceLocation getKeyLocation() {
        return this.keyLocation;
    }

    public abstract Type getType();

    public boolean matches(T t) {
        return this.key.equals(t);
    }

    public record InnerData(float catchChance, float consumeChance) {
        public static final Codec<InnerData> CODEC = RecordCodecBuilder.create(instance -> instance.group(Codec.FLOAT.fieldOf("catch_chance").forGetter(InnerData::catchChance), Codec.FLOAT.fieldOf("consume_chance").forGetter(InnerData::consumeChance)).apply(instance, InnerData::new));
    }
}
