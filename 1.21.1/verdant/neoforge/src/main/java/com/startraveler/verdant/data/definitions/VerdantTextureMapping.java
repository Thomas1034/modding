package com.startraveler.verdant.data.definitions;

import com.startraveler.verdant.block.custom.BombFlowerCropBlock;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.data.models.model.TextureSlot;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;

public class VerdantTextureMapping {

    public static TextureMapping columnAlt(Block block) {
        return (new TextureMapping()).put(TextureSlot.SIDE, TextureMapping.getBlockTexture(block, "_side_alt"))
                .put(TextureSlot.END, TextureMapping.getBlockTexture(block, "_top"));
    }

    public static TextureMapping asterisk(ResourceLocation plus, ResourceLocation cross) {
        return new TextureMapping().put(VerdantTextureSlot.PLUS, plus).put(TextureSlot.CROSS, cross);
    }

    public static TextureMapping asterisk(Block block) {
        return new TextureMapping().put(VerdantTextureSlot.PLUS, TextureMapping.getBlockTexture(block, "_plus"))
                .put(TextureSlot.CROSS, TextureMapping.getBlockTexture(block, "_cross"));
    }

    public static TextureMapping bombFlower(Block block, int age) {
        TextureMapping mapping = new TextureMapping().put(VerdantTextureSlot.BASE, TextureMapping.getBlockTexture(block, "_base"))
                .put(VerdantTextureSlot.FLOWER, TextureMapping.getBlockTexture(block, "_stage" + age))
                .put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(block, "_stage" + age));
        if (age == BombFlowerCropBlock.MAX_AGE) {
            mapping = mapping.put(TextureSlot.SIDE, TextureMapping.getBlockTexture(block, "_stage" + age + "_side"));
        }
        return mapping;
    }

    public static TextureMapping bombPile(Block block, int bombs) {
        return new TextureMapping().put(
                        VerdantTextureSlot.FLOWER,
                        TextureMapping.getBlockTexture(block)
                )
                .put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(block))
                .put(TextureSlot.SIDE, TextureMapping.getBlockTexture(block, "_side"));
    }

    public static TextureMapping asteriskForAloe(int age, int height, Block block) {
        return new TextureMapping().put(
                VerdantTextureSlot.PLUS,
                TextureMapping.getBlockTexture(
                        block,
                        (height == 0 ? "_base" : height == 1 ? "_middle" : "_top") + "_stage" + age + "_plus"
                )
        ).put(
                TextureSlot.CROSS,
                TextureMapping.getBlockTexture(
                        block,
                        (height == 0 ? "_base" : height == 1 ? "_middle" : "_top") + "_stage" + age + "_cross"
                )
        );
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
