package com.startraveler.verdant.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.startraveler.verdant.entity.custom.ThrownSpearEntity;
import net.minecraft.client.model.TridentModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

public class ThrownSpearRenderer extends EntityRenderer<ThrownSpearEntity, ThrownSpearRenderState> {

    public static final ResourceLocation SPEAR_LOCATION = ResourceLocation.withDefaultNamespace(
            "textures/entity/trident.png");
    private final TridentModel model;


    public ThrownSpearRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new TridentModel(context.bakeLayer(ModelLayers.TRIDENT));
    }

    @Override
    public void render(ThrownSpearRenderState renderState, PoseStack stack, MultiBufferSource bufferSource, int tint) {
        stack.pushPose();
        if (!renderState.isStuck) {
            stack.mulPose(Axis.YP.rotationDegrees(renderState.yRot - 90.0F));
            stack.mulPose(Axis.ZP.rotationDegrees(renderState.xRot + 90.0F));

        } else {
            Vec3 offsetToTarget = new Vec3(
                    renderState.targetX - renderState.x,
                    renderState.targetY - renderState.y,
                    renderState.targetZ - renderState.z
            );
            stack.translate(offsetToTarget.x, offsetToTarget.y, offsetToTarget.z);
            Vec3 offsetFromTarget = new Vec3(renderState.relativeX, renderState.relativeY, renderState.relativeZ);


            stack.translate(offsetFromTarget.x, offsetFromTarget.y, offsetFromTarget.z);

            stack.mulPose(Axis.YP.rotationDegrees(renderState.yRotRelative - 90.0F));
            stack.mulPose(Axis.ZP.rotationDegrees(renderState.xRotRelative + 90.0F));
        }
        VertexConsumer vertexconsumer = ItemRenderer.getFoilBuffer(
                bufferSource,
                this.model.renderType(SPEAR_LOCATION),
                false,
                renderState.isFoil
        );
        this.model.renderToBuffer(stack, vertexconsumer, tint, OverlayTexture.NO_OVERLAY);
        stack.popPose();
        super.render(renderState, stack, bufferSource, tint);
    }

    @Override
    public ThrownSpearRenderState createRenderState() {
        return new ThrownSpearRenderState();
    }

    @Override
    public void extractRenderState(ThrownSpearEntity thrownSpear, ThrownSpearRenderState thrownSpearRenderState, float partialTick) {
        super.extractRenderState(thrownSpear, thrownSpearRenderState, partialTick);
        thrownSpearRenderState.yRot = thrownSpear.getYRot(partialTick);
        thrownSpearRenderState.xRot = thrownSpear.getXRot(partialTick);
        Entity entity = thrownSpear.getTargetEntity();
        if (entity == null) {
            thrownSpearRenderState.isStuck = false;
        } else {
            thrownSpearRenderState.isStuck = true;
            thrownSpearRenderState.xRotRelative = thrownSpear.lockedXRot + entity.getVisualRotationYInDegrees();
            thrownSpearRenderState.yRotRelative = thrownSpear.lockedYRot;
            thrownSpearRenderState.xRotTarget = entity.getXRot(partialTick);
            thrownSpearRenderState.yRotTarget = ThrownSpearEntity.getVisualYRot(entity, partialTick);
            Vec3 targetPos = entity.getPosition(partialTick);
            thrownSpearRenderState.targetX = targetPos.x;
            thrownSpearRenderState.targetY = targetPos.y;
            thrownSpearRenderState.targetZ = targetPos.z;
            thrownSpearRenderState.relativeX = thrownSpear.relativePosition.x;
            thrownSpearRenderState.relativeY = thrownSpear.relativePosition.y;
            thrownSpearRenderState.relativeZ = thrownSpear.relativePosition.z;
        }
        thrownSpearRenderState.isFoil = thrownSpear.isFoil();
    }
}
