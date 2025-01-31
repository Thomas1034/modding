package com.startraveler.verdant.util.featureset;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.startraveler.verdant.Constants;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;

public class RecursiveFeatureSetEntry extends FeatureSet.Entry{

    public static final Codec<RecursiveFeatureSetEntry> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ResourceLocation.CODEC.fieldOf("configured_feature").forGetter(RecursiveFeatureSetEntry::getFeatureSetLocation),
            Codec.INT.fieldOf("weight").forGetter(RecursiveFeatureSetEntry::getWeight)
    ).apply(instance, RecursiveFeatureSetEntry::new));

    public static final ResourceLocation TYPE = ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "recursive");

    private final ResourceLocation featureSetLocation;
    private FeatureSet featureSet;

    public RecursiveFeatureSetEntry(ResourceLocation location, int weight) {
        super(weight);
        this.featureSetLocation = location;
    }

    public ResourceLocation getFeatureSetLocation() {
        return this.featureSetLocation;
    }

    @Override
    public ResourceLocation getType() {
        return TYPE;
    }

    @Override
    public void place(ServerLevel level, BlockPos pos) {
        if (this.featureSet == null) {
            this.featureSet = level.registryAccess().lookupOrThrow(FeatureSet.KEY).get(this.featureSetLocation).orElseThrow().value();
        }
        this.featureSet.place(level, pos);
    }
}
