package com.startraveler.verdant.data.definitions;

import com.startraveler.verdant.block.custom.BombFlowerCropBlock;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.data.models.model.TextureSlot;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.world.level.block.Block;

public class VerdantTextureMapping {

    public static TextureMapping fruitingLeaves(Material block) {
        return TextureMapping.cube(block)
                .put(VerdantTextureSlot.OVERLAY, new Material(block.sprite().withSuffix("_overlay")));
    }

    public static TextureMapping skull(Block block) {
        return (new TextureMapping()).put(TextureSlot.UP, TextureMapping.getBlockTexture(block, "_up"))
                .put(TextureSlot.DOWN, TextureMapping.getBlockTexture(block, "_down"))
                .put(TextureSlot.NORTH, TextureMapping.getBlockTexture(block, "_north"))
                .put(TextureSlot.EAST, TextureMapping.getBlockTexture(block, "_east"))
                .put(TextureSlot.SOUTH, TextureMapping.getBlockTexture(block, "_south"))
                .put(TextureSlot.WEST, TextureMapping.getBlockTexture(block, "_west"));
    }

    public static TextureMapping columnAlt(Block block) {
        return (new TextureMapping()).put(TextureSlot.SIDE, TextureMapping.getBlockTexture(block, "_side_alt"))
                .put(TextureSlot.END, TextureMapping.getBlockTexture(block, "_top"));
    }

    public static TextureMapping asterisk(Material plus, Material cross) {
        return new TextureMapping().put(VerdantTextureSlot.PLUS, plus).put(TextureSlot.CROSS, cross);
    }


    public static TextureMapping bombFlower(Block block, int age) {
        TextureMapping mapping = new TextureMapping().put(
                        VerdantTextureSlot.BASE,
                        TextureMapping.getBlockTexture(block, "_base")
                )
                .put(VerdantTextureSlot.FLOWER, TextureMapping.getBlockTexture(block, "_stage" + age))
                .put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(block, "_stage" + age));
        if (age == BombFlowerCropBlock.MAX_AGE) {
            mapping = mapping.put(TextureSlot.SIDE, TextureMapping.getBlockTexture(block, "_stage" + age + "_side"));
        }
        return mapping;
    }

    @SuppressWarnings("unused")
    public static TextureMapping bombPile(Block block, int bombs) {
        return new TextureMapping().put(VerdantTextureSlot.FLOWER, TextureMapping.getBlockTexture(block))
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


    public static TextureMapping tintedTrap(Block block) {
        return new TextureMapping().put(
                        VerdantTextureSlot.PARTICLE_BASE,
                        TextureMapping.getBlockTexture(block, "_base")
                )
                .put(VerdantTextureSlot.BASE, TextureMapping.getBlockTexture(block, "_base"))
                .put(VerdantTextureSlot.BAR, TextureMapping.getBlockTexture(block, "_bar"))
                .put(VerdantTextureSlot.SPIKES, TextureMapping.getBlockTexture(block, "_spikes"))
                .put(VerdantTextureSlot.TINTED_BASE, TextureMapping.getBlockTexture(block, "_base_tinted"))
                .put(VerdantTextureSlot.TINTED_BAR, TextureMapping.getBlockTexture(block, "_bar_tinted"))
                .put(VerdantTextureSlot.TINTED_SPIKES, TextureMapping.getBlockTexture(block, "_spikes_tinted"));
    }


    @SuppressWarnings("unused")
    public static TextureMapping overlaidCubeBlock(Block block, Block base, Material overlay) {
        return new TextureMapping().put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(base))
                .put(VerdantTextureSlot.BASE, TextureMapping.getBlockTexture(base))
                .put(VerdantTextureSlot.OVERLAY, overlay);
    }

    @SuppressWarnings("unused")
    public static TextureMapping topOverlaidCubeBlock(Block block, Block base, Material overlay, Material topOverlay) {
        return new TextureMapping().put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(base))
                .put(VerdantTextureSlot.BASE, TextureMapping.getBlockTexture(base))
                .put(VerdantTextureSlot.OVERLAY, overlay)
                .put(TextureSlot.TOP, new Material(topOverlay.sprite().withSuffix("_top")))
                .put(TextureSlot.SIDE, new Material(topOverlay.sprite().withSuffix("_side")));
    }

    public static TextureMapping overlaidCross(Material base) {
        return new TextureMapping().put(TextureSlot.CROSS, base)
                .put(VerdantTextureSlot.OVERLAY, new Material(base.sprite().withSuffix("_overlay")));
    }


    public static TextureMapping overlaidCrossEmissive(Block block) {
        return new TextureMapping().put(TextureSlot.CROSS, TextureMapping.getBlockTexture(block))
                .put(TextureSlot.CROSS_EMISSIVE, TextureMapping.getBlockTexture(block, "_emissive"))
                .put(VerdantTextureSlot.OVERLAY, TextureMapping.getBlockTexture(block, "_overlay"));
    }


    public static TextureMapping overlaidPlant(Block block) {
        return new TextureMapping().put(TextureSlot.PLANT, TextureMapping.getBlockTexture(block))
                .put(VerdantTextureSlot.OVERLAY, TextureMapping.getBlockTexture(block, "_overlay"));
    }

    public static TextureMapping overlaidPlantEmissive(Block block) {
        return new TextureMapping().put(TextureSlot.PLANT, TextureMapping.getBlockTexture(block))
                .put(TextureSlot.CROSS_EMISSIVE, TextureMapping.getBlockTexture(block, "_emissive"))
                .put(VerdantTextureSlot.OVERLAY, TextureMapping.getBlockTexture(block, "_overlay"));
    }

}
