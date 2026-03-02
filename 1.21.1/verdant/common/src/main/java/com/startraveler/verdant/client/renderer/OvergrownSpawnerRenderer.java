package com.startraveler.verdant.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.startraveler.verdant.block.custom.entity.OvergrownSpawnerBlockEntity;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.state.SpawnerRenderState;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BaseSpawner;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.Nullable;

// TODO
public class OvergrownSpawnerRenderer implements BlockEntityRenderer<@NotNull OvergrownSpawnerBlockEntity, @NotNull SpawnerRenderState> {
    private final EntityRenderDispatcher entityRenderer;

    public OvergrownSpawnerRenderer(BlockEntityRendererProvider.Context context) {
        this.entityRenderer = context.entityRenderer();
    }

    public static void submitEntityInSpawner(PoseStack poseStack, SubmitNodeCollector nodeCollector, EntityRenderState displayEntity, EntityRenderDispatcher entityRenderer, float spin, float scale, CameraRenderState cameraRenderState) {
        poseStack.pushPose();
        poseStack.translate(0.5F, 0.4F, 0.5F);
        poseStack.mulPose(Axis.YP.rotationDegrees(spin));
        poseStack.translate(0.0F, -0.2F, 0.0F);
        poseStack.mulPose(Axis.XP.rotationDegrees(-30.0F));
        poseStack.scale(scale, scale, scale);
        entityRenderer.submit(
                displayEntity,
                cameraRenderState,
                0.0F,
                0.0F,
                0.0F,
                poseStack,
                nodeCollector
        );
        poseStack.popPose();
    }

    static void extractSpawnerData(SpawnerRenderState renderState, float partialTick, @Nullable Entity entity, EntityRenderDispatcher entityRenderer, double oSpin, double spin) {
        if (entity != null) {
            renderState.displayEntity = entityRenderer.extractEntity(entity, partialTick);
            renderState.displayEntity.lightCoords = renderState.lightCoords;
            renderState.spin = (float) Mth.lerp(partialTick, oSpin, spin) * 10.0F;
            renderState.scale = 0.53125F;
            float f = Math.max(entity.getBbWidth(), entity.getBbHeight());
            if ((double) f > (double) 1.0F) {
                renderState.scale /= f;
            }
        }

    }

    @Override
    public SpawnerRenderState createRenderState() {
        return new SpawnerRenderState();
    }

    @Override
    public void extractRenderState(OvergrownSpawnerBlockEntity p_446072_, SpawnerRenderState p_447179_, float p_446518_, @NotNull Vec3 p_446190_, ModelFeatureRenderer.@Nullable CrumblingOverlay p_445699_) {
        BlockEntityRenderer.super.extractRenderState(p_446072_, p_447179_, p_446518_, p_446190_, p_445699_);
        if (p_446072_.getLevel() != null) {
            BaseSpawner basespawner = p_446072_.getSpawner();
            Entity entity = basespawner.getOrCreateDisplayEntity(p_446072_.getLevel(), p_446072_.getBlockPos());
            extractSpawnerData(
                    p_447179_,
                    p_446518_,
                    entity,
                    this.entityRenderer,
                    basespawner.getOSpin(),
                    basespawner.getSpin()
            );
        }

    }

    @Override
    public void submit(SpawnerRenderState p_446520_, @NotNull PoseStack p_440479_, @NotNull SubmitNodeCollector p_439725_, @NotNull CameraRenderState p_451046_) {
        if (p_446520_.displayEntity != null) {
            submitEntityInSpawner(
                    p_440479_,
                    p_439725_,
                    p_446520_.displayEntity,
                    this.entityRenderer,
                    p_446520_.spin,
                    p_446520_.scale,
                    p_451046_
            );
        }

    }
}