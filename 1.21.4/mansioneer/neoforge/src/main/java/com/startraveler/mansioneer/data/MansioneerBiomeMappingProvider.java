package com.startraveler.mansioneer.data;


import com.startraveler.mansioneer.Constants;
import com.startraveler.mansioneer.util.biomemapping.BiomeMapping;
import com.startraveler.mansioneer.util.blocktransformer.BuiltInTransformers;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.neoforged.neoforge.common.Tags;

public class MansioneerBiomeMappingProvider {

    public static void register(BootstrapContext<BiomeMapping> bootstrap) {
        register(bootstrap, Tags.Biomes.IS_SWAMP, BuiltInTransformers.MANSION_TO_SWAMP);
        register(bootstrap, Tags.Biomes.IS_SAVANNA, BuiltInTransformers.MANSION_TO_SAVANNA);
        register(bootstrap, Tags.Biomes.IS_TAIGA, BuiltInTransformers.MANSION_TO_TAIGA);
        register(bootstrap, Biomes.PALE_GARDEN, BuiltInTransformers.MANSION_TO_PALE_GARDEN);
        register(bootstrap, Tags.Biomes.IS_JUNGLE, BuiltInTransformers.MANSION_TO_JUNGLE);
        register(bootstrap, Tags.Biomes.IS_DESERT, BuiltInTransformers.MANSION_TO_DESERT);
    }

    public static ResourceKey<BiomeMapping> key(ResourceLocation location) {
        return ResourceKey.create(BiomeMapping.KEY, location);
    }

    protected static void register(BootstrapContext<BiomeMapping> bootstrap, ResourceKey<Biome> biome, ResourceLocation transformer) {
        bootstrap.register(
                key(ResourceLocation.fromNamespaceAndPath(
                        Constants.MOD_ID,
                        biome.location().getNamespace() + "_" + biome.location().getPath()
                )),
                new BiomeMapping(biome.location(), false, transformer)
        );
    }

    protected static void register(BootstrapContext<BiomeMapping> bootstrap, TagKey<Biome> biome, ResourceLocation transformer) {
        bootstrap.register(
                key(ResourceLocation.fromNamespaceAndPath(
                        Constants.MOD_ID,
                        biome.location().getNamespace() + "_" + biome.location().getPath()
                )),
                new BiomeMapping(biome.location(), true, transformer)
        );
    }
}
