package com.startraveler.verdant.data.definitions;

import com.startraveler.verdant.data.VerdantModelProvider;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.model.ModelTemplate;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

@SuppressWarnings("unused")
public enum VerdantPlantType {
    OVERLAID_TINTED(
            VerdantModelTemplates.OVERLAID_TINTED_CROSS,
            VerdantModelTemplates.OVERLAID_TINTED_FLOWER_POT_CROSS,
            false
    ),
    OVERLAID_NOT_TINTED(VerdantModelTemplates.OVERLAID_CROSS, VerdantModelTemplates.OVERLAID_FLOWER_POT_CROSS, false),
    OVERLAID_EMISSIVE_NOT_TINTED(
            VerdantModelTemplates.OVERLAID_CROSS_EMISSIVE,
            VerdantModelTemplates.OVERLAID_FLOWER_POT_CROSS_EMISSIVE,
            true
    );

    private final ModelTemplate blockTemplate;
    private final ModelTemplate flowerPotTemplate;
    private final boolean isEmissive;

    VerdantPlantType(ModelTemplate blockTemplate, ModelTemplate flowerPotTemplate, boolean isEmissive) {
        this.blockTemplate = blockTemplate;
        this.flowerPotTemplate = flowerPotTemplate;
        this.isEmissive = isEmissive;
    }

    public ModelTemplate getCross() {
        return this.blockTemplate;
    }

    public ModelTemplate getCrossPot() {
        return this.flowerPotTemplate;
    }

    public Identifier createItemModel(BlockModelGenerators generators, Block block) {
        Item item = block.asItem();
        return this.isEmissive ? VerdantModelProvider.createFlatItemModelWithBlockTextureAndOverlay(
                generators,
                item,
                block,
                "_emissive",
                "_emissive_overlay"
        ) : VerdantModelProvider.createFlatItemModelWithBlockTextureAndOverlay(generators, item, block, "", "_overlay");
    }

    public TextureMapping getTextureMapping(Block block) {
        return this.isEmissive ? VerdantTextureMapping.overlaidCrossEmissive(block) : VerdantTextureMapping.overlaidCross(
                TextureMapping.getBlockTexture(block));
    }

    public TextureMapping getPlantTextureMapping(Block block) {
        return this.isEmissive ? VerdantTextureMapping.overlaidPlantEmissive(block) : VerdantTextureMapping.overlaidPlant(
                block);
    }
}
