package com.startraveler.mansioneer.util.biomemapping;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.startraveler.mansioneer.Constants;
import com.startraveler.mansioneer.util.blocktransformer.BlockTransformer;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;

import java.util.stream.StreamSupport;

// Stores a relationship between a biome (or biome tag) and a block transformer
public class BiomeMapping {
    public static final ResourceKey<Registry<BiomeMapping>> KEY = ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID,
            "biomemapping"
    ));
    public static final Codec<BiomeMapping> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ResourceLocation.CODEC.fieldOf("biome").forGetter(BiomeMapping::getBiomeLocation),
            Codec.BOOL.fieldOf("tag").forGetter(BiomeMapping::isTag),
            ResourceLocation.CODEC.fieldOf("transformer").forGetter(BiomeMapping::getBlockTransformerLocation)
    ).apply(instance, BiomeMapping::new));

    protected final TagKey<Biome> biomeTagKey;
    protected final ResourceLocation biomeLocation;
    protected final boolean isTag;
    protected final ResourceLocation blockTransformerLocation;
    protected int size = -1;

    public BiomeMapping(ResourceLocation biomeLocation, boolean isTag, ResourceLocation blockTransformerLocation) {
        this.biomeLocation = biomeLocation;
        this.biomeTagKey = TagKey.create(Registries.BIOME, biomeLocation);
        this.isTag = isTag;
        this.blockTransformerLocation = blockTransformerLocation;
    }

    public boolean matches(Holder<Biome> biomeToTest) {
        if (this.isTag) {
            return biomeToTest.is(this.biomeTagKey);
        } else {
            return biomeToTest.is(this.biomeLocation);
        }
    }

    public int getSize(RegistryAccess access) {
        if (this.size >= 0) {
            return this.size;
        }
        Registry<Biome> mappings = access.lookupOrThrow(Registries.BIOME);
        if (this.isTag) {
            this.size = (int) StreamSupport.stream(mappings.getTagOrEmpty(this.biomeTagKey).spliterator(), false).count();
        } else {
            this.size = mappings.get(this.biomeLocation).isPresent() ? 1 : 0;
        }
        return this.size;
    }

    public BlockTransformer getTransformer(RegistryAccess access) {

        return access.lookupOrThrow(BlockTransformer.KEY).get(this.blockTransformerLocation).orElseThrow().value();
    }

    private boolean isTag() {
        return this.isTag;
    }

    private ResourceLocation getBiomeLocation() {
        return this.biomeLocation;
    }

    private ResourceLocation getBlockTransformerLocation() {
        return this.blockTransformerLocation;
    }

}
