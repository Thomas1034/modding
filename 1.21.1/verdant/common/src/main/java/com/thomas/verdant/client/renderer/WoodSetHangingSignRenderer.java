package com.thomas.verdant.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.thomas.verdant.woodset.WoodSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.HangingSignRenderer;
import net.minecraft.world.level.block.SignBlock;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WoodType;

public class WoodSetHangingSignRenderer extends HangingSignRenderer {

    private final WoodSet woodSet;
    private final HangingSignRenderer.HangingSignModel model;

    public WoodSetHangingSignRenderer(BlockEntityRendererProvider.Context context, WoodSet woodSet) {
        super(context);
        this.woodSet = woodSet;
        this.model = new HangingSignRenderer.HangingSignModel(context.bakeLayer(ModelLayers.createHangingSignModelName(woodSet.getType())));
    }

    @Override
    public void render(SignBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        BlockState blockstate = blockEntity.getBlockState();
        SignBlock signblock = (SignBlock)blockstate.getBlock();
        WoodType woodtype = SignBlock.getWoodType(signblock);
        HangingSignRenderer.HangingSignModel hangingsignrenderer$hangingsignmodel = this.model;
        hangingsignrenderer$hangingsignmodel.evaluateVisibleParts(blockstate);
        this.renderSignWithText(blockEntity, poseStack, bufferSource, packedLight, packedOverlay, blockstate, signblock, woodtype, hangingsignrenderer$hangingsignmodel);
    }
}
