package com.startraveler.verdant.data.definitions;

import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.data.models.model.TextureSlot;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;

public class VerdantTextureMapping {

    public static TextureMapping asterisk(ResourceLocation plus, ResourceLocation cross) {
        return new TextureMapping().put(VerdantTextureSlot.PLUS, plus).put(TextureSlot.CROSS, cross);
    }

    public static TextureMapping candleCake(Block cake, Block candle, boolean lit) {
        return new TextureMapping().put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(cake, "_side"))
                .put(TextureSlot.BOTTOM, TextureMapping.getBlockTexture(cake, "_bottom"))
                .put(TextureSlot.TOP, TextureMapping.getBlockTexture(cake, "_top"))
                .put(TextureSlot.SIDE, TextureMapping.getBlockTexture(cake, "_side"))
                .put(TextureSlot.CANDLE, TextureMapping.getBlockTexture(candle, lit ? "_lit" : ""));
    }

    public static TextureMapping fishTrap(Block block) {
        return new TextureMapping().put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(block, "_side"))
                .put(TextureSlot.SIDE, TextureMapping.getBlockTexture(block, "_side"))
                .put(TextureSlot.FRONT, TextureMapping.getBlockTexture(block, "_front"))
                .put(TextureSlot.TOP, TextureMapping.getBlockTexture(block, "_top"))
                .put(VerdantTextureSlot.INSET_LOW, TextureMapping.getBlockTexture(block, "_inset_low"))
                .put(VerdantTextureSlot.INSET_HIGH, TextureMapping.getBlockTexture(block, "_inset_high"));
    }

    public static TextureMapping trap(Block block) {
        return new TextureMapping().put(
                        VerdantTextureSlot.PARTICLE_BASE,
                        TextureMapping.getBlockTexture(block, "_base")
                )
                .put(VerdantTextureSlot.BASE, TextureMapping.getBlockTexture(block, "_base"))
                .put(VerdantTextureSlot.BAR, TextureMapping.getBlockTexture(block, "_bar"))
                .put(VerdantTextureSlot.SPIKES, TextureMapping.getBlockTexture(block, "_spikes"));
    }


    public static TextureMapping overlaidCubeBlock(Block block, Block base, ResourceLocation overlay) {
        return new TextureMapping().put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(base))
                .put(VerdantTextureSlot.BASE, TextureMapping.getBlockTexture(base))
                .put(VerdantTextureSlot.OVERLAY, overlay);
    }

    public static TextureMapping topOverlaidCubeBlock(Block block, Block base, ResourceLocation overlay, ResourceLocation topOverlay) {
        return new TextureMapping().put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(base))
                .put(VerdantTextureSlot.BASE, TextureMapping.getBlockTexture(base))
                .put(VerdantTextureSlot.OVERLAY, overlay)
                .put(TextureSlot.TOP, topOverlay.withSuffix("_top"))
                .put(TextureSlot.SIDE, topOverlay.withSuffix("_side"))
                .put(TextureSlot.BOTTOM, topOverlay.withSuffix("_bottom"));
    }
}
