package com.thomas.verdant.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.thomas.verdant.woodset.WoodSet;
import net.minecraft.client.model.Model;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraft.world.level.block.SignBlock;
import net.minecraft.world.level.block.StandingSignBlock;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WoodType;

public class WoodSetSignRenderer extends SignRenderer {

    private final WoodSet woodSet;
    private final Model standing;
    private final Model wall;

    public WoodSetSignRenderer(BlockEntityRendererProvider.Context context, WoodSet woodSet) {
        super(context);
        this.woodSet = woodSet;
        this.standing = createSignModel(context.getModelSet(), this.woodSet.getType(), true);
        this.wall = createSignModel(context.getModelSet(), this.woodSet.getType(), false);
    }

    @Override
    public void render(SignBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        BlockState blockstate = blockEntity.getBlockState();
        SignBlock signblock = (SignBlock) blockstate.getBlock();
        WoodType woodtype = SignBlock.getWoodType(signblock);
        Model model = blockstate.getBlock() instanceof StandingSignBlock ? this.standing : this.wall;
        this.renderSignWithText(blockEntity, poseStack, bufferSource, packedLight, packedOverlay, blockstate, signblock, woodtype, model);
    }
}
