package com.startraveler.mansioneer.data;

import com.startraveler.mansioneer.Constants;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biomes;
import net.neoforged.neoforge.common.Tags;

import java.util.concurrent.CompletableFuture;

public class MansioneerBiomeTagProvider extends BiomeTagsProvider {

    public MansioneerBiomeTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> provider) {
        super(output, provider, Constants.MOD_ID);
    }

    @Override
    protected void addTags(HolderLookup.Provider lookupProvider) {

        this.tag(BiomeTags.HAS_WOODLAND_MANSION).addTags(
                Tags.Biomes.IS_SWAMP,
                Tags.Biomes.IS_SAVANNA,
                Tags.Biomes.IS_TAIGA,
                Tags.Biomes.IS_JUNGLE,
                Tags.Biomes.IS_DESERT,
                Tags.Biomes.IS_BIRCH_FOREST,
                Tags.Biomes.IS_MOUNTAIN_SLOPE,
                Tags.Biomes.IS_ICY,
                Tags.Biomes.IS_SNOWY,
                Tags.Biomes.IS_FOREST,
                Tags.Biomes.IS_PLAINS
        );
        this.tag(BiomeTags.HAS_WOODLAND_MANSION).add(Biomes.PALE_GARDEN, Biomes.CHERRY_GROVE);

    }

}
