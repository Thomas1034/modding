package com.startraveler.verdant.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.startraveler.verdant.entity.custom.BrambleEntity;
import com.startraveler.verdant.registry.BlockRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.TntMinecartRenderer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

public class BrambleRenderer extends EntityRenderer<BrambleEntity, BrambleEntityRenderState> {
    private final BlockRenderDispatcher blockRenderer;


    public BrambleRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.shadowRadius = 0.0F;
        this.blockRenderer = context.getBlockRenderDispatcher();
    }

    public void render(BrambleEntityRenderState brambleRenderState, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        poseStack.pushPose();

        poseStack.translate(0.0F, 0.5F, 0.0F);
        poseStack.mulPose(Axis.YP.rotationDegrees(-90.0F));
        poseStack.translate(-0.5F, -0.5F, 0.5F);
        poseStack.mulPose(Axis.YP.rotationDegrees(90.0F));

        if (brambleRenderState.headBlockState != null) {
            poseStack.pushPose();
            poseStack.translate(0, 0.5, 0);

            double bobRate = 100;
            double bobScale = 0.125;
            double bob = bobScale * (-1 + Math.sin(2 * Mth.PI * (brambleRenderState.age) / bobRate));

            poseStack.translate(0, bob, 0);
            Entity cameraEntity = Minecraft.getInstance().player;
            if (cameraEntity != null) {
                Vec3 relative =
                        cameraEntity.getEyePosition(brambleRenderState.partialTick)
                                .subtract(
                                        brambleRenderState.x,
                                        brambleRenderState.y + 0.5f + bob,
                                        brambleRenderState.z
                                );
                float yRot = (float) Mth.atan2(relative.z, relative.x);
                float xRot = (float) Mth.atan2(relative.y, relative.length());
                poseStack.rotateAround(Axis.YP.rotation(-yRot - (float)(Math.PI / 2)), 0.5f, 0, 0.5f);
                poseStack.rotateAround(Axis.XP.rotation(xRot), 0.0f, 0.0f - (float)bob, 0.0f);
            }
            TntMinecartRenderer.renderWhiteSolidBlock(
                    this.blockRenderer,
                    brambleRenderState.headBlockState,
                    poseStack,
                    bufferSource,
                    15728880,
                    false
            );
            poseStack.popPose();
        }
        if (brambleRenderState.blockState != null) {
            TntMinecartRenderer.renderWhiteSolidBlock(
                    this.blockRenderer,
                    brambleRenderState.blockState,
                    poseStack,
                    bufferSource,
                    packedLight,
                    false
            );
        }
        if (brambleRenderState.shellBlockState != null && brambleRenderState.isInvulnerable) {
            poseStack.pushPose();
            float halfScale = -(brambleRenderState.shellBlockScale - 1) / 2;
            poseStack.translate(halfScale, halfScale, halfScale);
            poseStack.scale(
                    brambleRenderState.shellBlockScale, brambleRenderState.shellBlockScale,
                    brambleRenderState.shellBlockScale
            );
            // Render with full sky and block light (LightTexture.pack()).
            TntMinecartRenderer.renderWhiteSolidBlock(
                    this.blockRenderer,
                    brambleRenderState.shellBlockState,
                    poseStack,
                    bufferSource,
                    15728880,
                    false
            );
            poseStack.popPose();
        }

        poseStack.popPose();
        super.render(brambleRenderState, poseStack, bufferSource, packedLight);
    }

    public BrambleEntityRenderState createRenderState() {
        return new BrambleEntityRenderState();
    }

    public void extractRenderState(BrambleEntity bramble, BrambleEntityRenderState renderState, float partialTick) {
        super.extractRenderState(bramble, renderState, partialTick);
        renderState.fuseRemainingInTicks = 1.0f;
        renderState.blockState = bramble.getModelForCurrentStage();
        renderState.shellBlockState = bramble.getShellModelForCurrentStage();
        renderState.headBlockState = BlockRegistry.BRAMBLE_HEAD.get().defaultBlockState();
        renderState.isInvulnerable = bramble.getInvulnerableTicks() > 0;
        renderState.shellBlockScale = 1.25f;
        renderState.age = bramble.tickCount + partialTick;
        renderState.yRot = bramble.getYRot(partialTick);
        renderState.xRot = bramble.getXRot(partialTick);
        renderState.partialTick = partialTick;
    }
}
