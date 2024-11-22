package com.thomas.verdant.util.featureset;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.thomas.verdant.Constants;
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
            this.featureSet = level.registryAccess().registryOrThrow(FeatureSet.KEY).get(this.featureSetLocation);
        }
        // If it's still null, throw.
        if (this.featureSet == null) {
            Constants.LOG.error("Unable to get feature set {}", this.featureSetLocation);
            Constants.LOG.error("Please report this to the mod developer, along with a list of the other mods or data packs you were using at the time");
            return;
        }
        this.featureSet.place(level, pos);
    }
}
