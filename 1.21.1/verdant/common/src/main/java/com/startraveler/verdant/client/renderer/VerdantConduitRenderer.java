/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * If you modify this file, please include a notice stating the changes:
 * Example: "Modified by [Your Name] on [Date] - [Short Description of Changes]"
 */
package com.startraveler.verdant.client.renderer;


import com.mojang.blaze3d.vertex.PoseStack;
import com.startraveler.verdant.Constants;
import com.startraveler.verdant.block.custom.entity.VerdantConduitBlockEntity;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.SpriteMapper;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.state.ConduitRenderState;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.sprite.SpriteGetter;
import net.minecraft.client.resources.model.sprite.SpriteId;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.jspecify.annotations.Nullable;

public class VerdantConduitRenderer implements BlockEntityRenderer<@NotNull VerdantConduitBlockEntity, @NotNull ConduitRenderState> {

    @SuppressWarnings("deprecation")
    public static final SpriteMapper MAPPER = new SpriteMapper(TextureAtlas.LOCATION_BLOCKS, "entity/conduit");
    public static final SpriteId SHELL_TEXTURE = MAPPER.apply(Constants.id("base"));
    public static final SpriteId ACTIVE_SHELL_TEXTURE = MAPPER.apply(Constants.id("cage"));
    public static final SpriteId WIND_TEXTURE = MAPPER.apply(Constants.id("wind"));
    public static final SpriteId VERTICAL_WIND_TEXTURE = MAPPER.apply(Constants.id("wind_vertical"));
    public static final SpriteId OPEN_EYE_TEXTURE = MAPPER.apply(Constants.id("open_eye"));
    public static final SpriteId CLOSED_EYE_TEXTURE = MAPPER.apply(Constants.id("closed_eye"));

    private final SpriteGetter sprites;
    private final ModelPart eye;
    private final ModelPart wind;
    private final ModelPart shell;
    private final ModelPart cage;

    public VerdantConduitRenderer(BlockEntityRendererProvider.Context context) {
        this.sprites = context.sprites();
        this.eye = context.bakeLayer(ModelLayers.CONDUIT_EYE);
        this.wind = context.bakeLayer(ModelLayers.CONDUIT_WIND);
        this.shell = context.bakeLayer(ModelLayers.CONDUIT_SHELL);
        this.cage = context.bakeLayer(ModelLayers.CONDUIT_CAGE);
    }

    public ConduitRenderState createRenderState() {
        return new ConduitRenderState();
    }


    public void extractRenderState(VerdantConduitBlockEntity blockEntity, ConduitRenderState state, float partialTicks, @NotNull Vec3 cameraPosition, ModelFeatureRenderer.@Nullable CrumblingOverlay breakProgress) {
        BlockEntityRenderer.super.extractRenderState(blockEntity, state, partialTicks, cameraPosition, breakProgress);
        state.isActive = blockEntity.isActive();
        state.activeRotation = blockEntity.getActiveRotation(blockEntity.isActive() ? partialTicks : 0.0F);
        state.animTime = (float) blockEntity.tickCount + partialTicks;
        state.animationPhase = blockEntity.tickCount / 66 % 3;
        state.isHunting = false;
    }

    public void submit(ConduitRenderState state, @NotNull PoseStack poseStack, @NotNull SubmitNodeCollector submitNodeCollector, @NotNull CameraRenderState camera) {
        if (!state.isActive) {
            poseStack.pushPose();
            poseStack.translate(0.5F, 0.5F, 0.5F);
            poseStack.mulPose((new Quaternionf()).rotationY(state.activeRotation * ((float) Math.PI / 180F)));
            submitNodeCollector.submitModelPart(
                    this.shell,
                    poseStack,
                    SHELL_TEXTURE.renderType(RenderTypes::entitySolid),
                    state.lightCoords,
                    OverlayTexture.NO_OVERLAY,
                    this.sprites.get(SHELL_TEXTURE),
                    -1,
                    state.breakProgress
            );
            poseStack.popPose();
        } else {
            float rotation = state.activeRotation * (180F / (float) Math.PI);
            float hh = Mth.sin(state.animTime * 0.1F) / 2.0F + 0.5F;
            hh = hh * hh + hh;
            poseStack.pushPose();
            poseStack.translate(0.5F, 0.3F + hh * 0.2F, 0.5F);
            Vector3f axis = (new Vector3f(0.5F, 1.0F, 0.5F)).normalize();
            poseStack.mulPose((new Quaternionf()).rotationAxis(rotation * ((float) Math.PI / 180F), axis));
            submitNodeCollector.submitModelPart(
                    this.cage,
                    poseStack,
                    ACTIVE_SHELL_TEXTURE.renderType(RenderTypes::entityCutout),
                    state.lightCoords,
                    OverlayTexture.NO_OVERLAY,
                    this.sprites.get(ACTIVE_SHELL_TEXTURE),
                    -1,
                    state.breakProgress
            );
            poseStack.popPose();
            poseStack.pushPose();
            poseStack.translate(0.5F, 0.5F, 0.5F);
            if (state.animationPhase == 1) {
                poseStack.mulPose((new Quaternionf()).rotationX(((float) Math.PI / 2F)));
            } else if (state.animationPhase == 2) {
                poseStack.mulPose((new Quaternionf()).rotationZ(((float) Math.PI / 2F)));
            }

            SpriteId windSpriteId = state.animationPhase == 1 ? VERTICAL_WIND_TEXTURE : WIND_TEXTURE;
            RenderType windRenderType = windSpriteId.renderType(RenderTypes::entityCutout);
            TextureAtlasSprite windSprite = this.sprites.get(windSpriteId);
            submitNodeCollector.submitModelPart(
                    this.wind,
                    poseStack,
                    windRenderType,
                    state.lightCoords,
                    OverlayTexture.NO_OVERLAY,
                    windSprite
            );
            poseStack.popPose();
            poseStack.pushPose();
            poseStack.translate(0.5F, 0.5F, 0.5F);
            poseStack.scale(0.875F, 0.875F, 0.875F);
            poseStack.mulPose((new Quaternionf()).rotationXYZ((float) Math.PI, 0.0F, (float) Math.PI));
            submitNodeCollector.submitModelPart(
                    this.wind,
                    poseStack,
                    windRenderType,
                    state.lightCoords,
                    OverlayTexture.NO_OVERLAY,
                    windSprite
            );
            poseStack.popPose();
            poseStack.pushPose();
            poseStack.translate(0.5F, 0.3F + hh * 0.2F, 0.5F);
            poseStack.scale(0.5F, 0.5F, 0.5F);
            poseStack.mulPose(camera.orientation);
            poseStack.mulPose((new Quaternionf()).rotationZ((float) Math.PI).rotateY((float) Math.PI));
            float scale = 1.3333334F;
            poseStack.scale(scale, scale, scale);
            SpriteId eyeSprite = state.isHunting ? OPEN_EYE_TEXTURE : CLOSED_EYE_TEXTURE;
            submitNodeCollector.submitModelPart(
                    this.eye,
                    poseStack,
                    eyeSprite.renderType(RenderTypes::entityCutout),
                    state.lightCoords,
                    OverlayTexture.NO_OVERLAY,
                    this.sprites.get(eyeSprite)
            );
            poseStack.popPose();
        }

    }
}

