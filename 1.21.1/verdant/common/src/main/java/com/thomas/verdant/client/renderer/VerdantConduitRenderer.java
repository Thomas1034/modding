package com.thomas.verdant.client.renderer;


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.thomas.verdant.Constants;
import com.thomas.verdant.block.custom.entity.VerdantConduitBlockEntity;
import net.minecraft.client.Camera;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.Material;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class VerdantConduitRenderer implements BlockEntityRenderer<VerdantConduitBlockEntity> {
    public static final Material SHELL_TEXTURE;
    public static final Material ACTIVE_SHELL_TEXTURE;
    public static final Material WIND_TEXTURE;
    public static final Material VERTICAL_WIND_TEXTURE;
    public static final Material CLOSED_EYE_TEXTURE;

    static {

        SHELL_TEXTURE = new Material(
                TextureAtlas.LOCATION_BLOCKS,
                ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "entity/conduit/base")
        );
        ACTIVE_SHELL_TEXTURE = new Material(
                TextureAtlas.LOCATION_BLOCKS,
                ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "entity/conduit/cage")
        );
        WIND_TEXTURE = new Material(
                TextureAtlas.LOCATION_BLOCKS,
                ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "entity/conduit/wind")
        );
        VERTICAL_WIND_TEXTURE = new Material(
                TextureAtlas.LOCATION_BLOCKS,
                ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "entity/conduit/wind_vertical")
        );
        CLOSED_EYE_TEXTURE = new Material(
                TextureAtlas.LOCATION_BLOCKS,
                ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "entity/conduit/closed_eye")
        );

    }

    private final ModelPart eye;
    private final ModelPart wind;
    private final ModelPart shell;
    private final ModelPart cage;
    private final BlockEntityRenderDispatcher renderer;

    public VerdantConduitRenderer(BlockEntityRendererProvider.Context context) {
        this.renderer = context.getBlockEntityRenderDispatcher();
        this.eye = context.bakeLayer(ModelLayers.CONDUIT_EYE);
        this.wind = context.bakeLayer(ModelLayers.CONDUIT_WIND);
        this.shell = context.bakeLayer(ModelLayers.CONDUIT_SHELL);
        this.cage = context.bakeLayer(ModelLayers.CONDUIT_CAGE);
    }

    public void render(VerdantConduitBlockEntity blockEntity, float partialTicks, PoseStack poseStack, MultiBufferSource multiBufferSource, int unknown1, int unknown2) {
        float tickCount = (float) blockEntity.tickCount + partialTicks;
        if (!blockEntity.isActive()) {
            // float inactiveRotation = 0; //blockEntity.getActiveRotation(0.0F);
            VertexConsumer vertexConsumer = SHELL_TEXTURE.buffer(multiBufferSource, RenderType::entitySolid);
            poseStack.pushPose();
            poseStack.translate(0.5F, 0.5F, 0.5F);
            // poseStack.mulPose((new Quaternionf()).rotationY(inactiveRotation * ((float) Math.PI / 180F)));
            this.shell.render(poseStack, vertexConsumer, unknown1, unknown2);
            poseStack.popPose();
        } else {
            float rotation = blockEntity.getActiveRotation(partialTicks) * (180F / (float) Math.PI);
            float bounceAmount = Mth.sin(tickCount * 0.1F) / 2.0F + 0.5F;
            bounceAmount = bounceAmount * bounceAmount + bounceAmount;
            poseStack.pushPose();
            poseStack.translate(0.5F, 0.3F + bounceAmount * 0.2F, 0.5F);
            Vector3f vector3f = (new Vector3f(0.5F, 1.0F, 0.5F)).normalize();
            poseStack.mulPose((new Quaternionf()).rotationAxis(rotation * ((float) Math.PI / 180F), vector3f));
            this.cage.render(
                    poseStack,
                    ACTIVE_SHELL_TEXTURE.buffer(multiBufferSource, RenderType::entityCutoutNoCull),
                    unknown1,
                    unknown2
            );
            poseStack.popPose();
            int whichWindTexture = blockEntity.tickCount / 66 % 3;
            poseStack.pushPose();
            poseStack.translate(0.5F, 0.5F, 0.5F);
            if (whichWindTexture == 1) {
                poseStack.mulPose((new Quaternionf()).rotationX(((float) Math.PI / 2F)));
            } else if (whichWindTexture == 2) {
                poseStack.mulPose((new Quaternionf()).rotationZ(((float) Math.PI / 2F)));
            }

            VertexConsumer vertexconsumer = (whichWindTexture == 1 ? VERTICAL_WIND_TEXTURE : WIND_TEXTURE).buffer(multiBufferSource,
                    RenderType::entityCutoutNoCull
            );
            this.wind.render(poseStack, vertexconsumer, unknown1, unknown2);
            poseStack.popPose();
            poseStack.pushPose();
            poseStack.translate(0.5F, 0.5F, 0.5F);
            poseStack.scale(0.875F, 0.875F, 0.875F);
            poseStack.mulPose((new Quaternionf()).rotationXYZ((float) Math.PI, 0.0F, (float) Math.PI));
            this.wind.render(poseStack, vertexconsumer, unknown1, unknown2);
            poseStack.popPose();
            Camera camera = this.renderer.camera;
            poseStack.pushPose();
            poseStack.translate(0.5F, 0.3F + bounceAmount * 0.2F, 0.5F);
            poseStack.scale(0.5F, 0.5F, 0.5F);
            float f3 = -camera.getYRot();
            poseStack.mulPose((new Quaternionf()).rotationYXZ(
                    f3 * ((float) Math.PI / 180F),
                    camera.getXRot() * ((float) Math.PI / 180F),
                    (float) Math.PI
            ));
            float f4 = 1.3333334F;
            poseStack.scale(1.3333334F, 1.3333334F, 1.3333334F);
            this.eye.render(
                    poseStack,
                    CLOSED_EYE_TEXTURE.buffer(multiBufferSource, RenderType::entityCutoutNoCull),
                    unknown1,
                    unknown2
            );
            poseStack.popPose();
        }

    }
}
