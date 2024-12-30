package com.thomas.verdant.data.definitions;

import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.data.models.model.TextureSlot;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;

public class VerdantTextureMapping {


    public static TextureMapping fishTrap(Block block) {
        return new TextureMapping().put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(block, "_side"))
                .put(TextureSlot.SIDE, TextureMapping.getBlockTexture(block, "_side"))
                .put(TextureSlot.FRONT, TextureMapping.getBlockTexture(block, "_front"))
                .put(TextureSlot.TOP, TextureMapping.getBlockTexture(block, "_top"))
                .put(VerdantTextureSlot.INSET_LOW, TextureMapping.getBlockTexture(block, "_inset_low"))
                .put(VerdantTextureSlot.INSET_HIGH, TextureMapping.getBlockTexture(block, "_inset_high"));
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
