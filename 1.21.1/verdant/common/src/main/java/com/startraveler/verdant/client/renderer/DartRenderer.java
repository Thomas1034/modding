package com.startraveler.verdant.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.startraveler.verdant.Constants;
import com.startraveler.verdant.entity.custom.DartEntity;
import com.startraveler.verdant.mixin.ArrowRendererModelAccessor;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public abstract class DartRenderer<T extends DartEntity, S extends DartRenderState> extends ArrowRenderer<T, S> {
    public static final ResourceLocation NORMAL_DART_OVERLAY_LOCATION = ResourceLocation.fromNamespaceAndPath(
            Constants.MOD_ID,
            "textures/entity/projectiles/dart_overlay.png"
    );
    public static final ResourceLocation TIPPED_DART_OVERLAY_LOCATION = ResourceLocation.fromNamespaceAndPath(
            Constants.MOD_ID,
            "textures/entity/projectiles/tipped_dart_overlay.png"
    );
    private static final double DART_OFFSET = -0.5;

    public DartRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    public void render(S state, PoseStack stack, MultiBufferSource bufferSource, int packedLight) {
        stack.pushPose();
        // wstack.translate(Vec3.directionFromRotation(state.xRot, state.yRot - 90.0F).scale(DART_OFFSET));

        stack.mulPose(Axis.YP.rotationDegrees(state.yRot - 90.0F));
        stack.mulPose(Axis.ZP.rotationDegrees(state.xRot));
        stack.translate(0.5, 0, 0);
        stack.mulPose(Axis.ZP.rotationDegrees(- state.xRot));
        stack.mulPose(Axis.YP.rotationDegrees(- state.yRot + 90.0F));

        // Render the fletching and tip
        stack.pushPose();
        stack.mulPose(Axis.YP.rotationDegrees(state.yRot - 90.0F));
        stack.mulPose(Axis.ZP.rotationDegrees(state.xRot));
        // stack.translate(0, 0, 0.5);
        VertexConsumer vertexConsumer = bufferSource.getBuffer(RenderType.entityCutout(this.getTippedTextureLocation(
                state)));
        ((ArrowRendererModelAccessor) this).verdant$getModel().setupAnim(state);

        if (state.isTipped) {
            ((ArrowRendererModelAccessor) this).verdant$getModel()
                    .renderToBuffer(stack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY, state.color);
        } else {
            ((ArrowRendererModelAccessor) this).verdant$getModel()
                    .renderToBuffer(stack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY);
        }

        stack.popPose();

        // Return to normal context and continue.
        super.render(state, stack, bufferSource, packedLight);
        stack.popPose();
    }

    private ResourceLocation getTippedTextureLocation(S state) {
        return state.isTipped ? TIPPED_DART_OVERLAY_LOCATION : NORMAL_DART_OVERLAY_LOCATION;
    }

}
