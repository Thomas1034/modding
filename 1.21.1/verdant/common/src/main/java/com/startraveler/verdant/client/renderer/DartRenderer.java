package com.startraveler.verdant.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.startraveler.verdant.Constants;
import com.startraveler.verdant.entity.custom.DartEntity;
import com.startraveler.verdant.mixin.ArrowRendererModelAccessor;
import net.minecraft.client.model.object.projectile.ArrowModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.NotNull;

public abstract class DartRenderer<T extends DartEntity, S extends DartRenderState> extends ArrowRenderer<T, S> {
    public static final Identifier NORMAL_DART_OVERLAY_LOCATION = Identifier.fromNamespaceAndPath(
            Constants.MOD_ID,
            "textures/entity/projectiles/dart_overlay.png"
    );
    public static final Identifier TIPPED_DART_OVERLAY_LOCATION = Identifier.fromNamespaceAndPath(
            Constants.MOD_ID,
            "textures/entity/projectiles/tipped_dart_overlay.png"
    );

    public DartRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    public void submit(S renderState, PoseStack poseStack, @NotNull SubmitNodeCollector submitNodeCollector, @NotNull CameraRenderState state) {
        // Save current position
        poseStack.pushPose();

        // Perform all the operations the `super` call does.
        // This makes sure it renders over top of the super call.
        poseStack.mulPose(Axis.YP.rotationDegrees(renderState.yRot - 90.0F));
        poseStack.mulPose(Axis.ZP.rotationDegrees(renderState.xRot));
        poseStack.translate(0.5, 0, 0);
        // Undo the operations, except for the translation "forward".
        poseStack.mulPose(Axis.ZP.rotationDegrees(-renderState.xRot));
        poseStack.mulPose(Axis.YP.rotationDegrees(-renderState.yRot + 90.0F));

        poseStack.pushPose();
        poseStack.mulPose(Axis.YP.rotationDegrees(renderState.yRot - 90.0F));
        poseStack.mulPose(Axis.ZP.rotationDegrees(renderState.xRot));
        ArrowModel thisModel = ((ArrowRendererModelAccessor) this).verdant$getModel();
        thisModel.setupAnim(renderState);

        submitNodeCollector.submitModel(
                thisModel,
                renderState,
                poseStack,
                RenderTypes.entityCutout(this.getTippedTextureLocation(renderState)),
                renderState.lightCoords,
                OverlayTexture.NO_OVERLAY,
                renderState.color,
                null,
                renderState.outlineColor,
                null
        );
        poseStack.popPose();
        super.submit(renderState, poseStack, submitNodeCollector, state);

        poseStack.popPose();
    }

    private Identifier getTippedTextureLocation(S state) {
        return state.isTipped ? TIPPED_DART_OVERLAY_LOCATION : NORMAL_DART_OVERLAY_LOCATION;
    }

}
