package com.thomas.verdant.util.featureset;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.thomas.verdant.Constants;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

public class ConfiguredFeatureSetEntry extends FeatureSet.Entry {

    public static final Codec<ConfiguredFeatureSetEntry> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ResourceLocation.CODEC.fieldOf("configured_feature").forGetter(ConfiguredFeatureSetEntry::getConfiguredFeatureLocation),
            Codec.INT.fieldOf("weight").forGetter(ConfiguredFeatureSetEntry::getWeight)
    ).apply(instance, ConfiguredFeatureSetEntry::new));

    public static final ResourceLocation TYPE = ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "configured_feature");

    private final ResourceLocation configuredFeatureLocation;
    private ConfiguredFeature<?, ?> configuredFeature;

    public ConfiguredFeatureSetEntry(ResourceLocation configuredFeature, int weight) {
        super(weight);
        this.configuredFeatureLocation = configuredFeature;
    }

    public ResourceLocation getConfiguredFeatureLocation() {
        return this.configuredFeatureLocation;
    }

    @Override
    public ResourceLocation getType() {
        return TYPE;
    }

    @Override
    public void place(ServerLevel level, BlockPos pos) {
        if (this.configuredFeature == null) {
            this.configuredFeature = level.registryAccess().registryOrThrow(Registries.CONFIGURED_FEATURE).get(this.configuredFeatureLocation);
        }
        // If it's still null, throw.
        if (this.configuredFeature == null) {
            Constants.LOG.error("Unable to get feature {}", this.configuredFeatureLocation);
            Constants.LOG.error("Please report this to the mod developer, along with a list of the other mods or data packs you were using at the time");
            return;
        }
        this.configuredFeature.place(level, level.getChunkSource().getGenerator(), level.getRandom(), pos);
    }


}
