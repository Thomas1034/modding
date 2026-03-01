package com.startraveler.verdant.client.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.startraveler.verdant.client.renderer.OozeRenderState;
import com.startraveler.verdant.client.renderer.OozeRenderer;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.monster.slime.SlimeModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import org.jetbrains.annotations.NotNull;

public class OozeOuterLayer extends RenderLayer<@NotNull OozeRenderState, @NotNull SlimeModel> {
    private final SlimeModel model;

    public OozeOuterLayer(RenderLayerParent<@NotNull OozeRenderState, @NotNull SlimeModel> renderer, EntityModelSet modelSet) {
        super(renderer);
        this.model = new SlimeModel(modelSet.bakeLayer(ModelLayers.SLIME_OUTER));
    }

    @Override
    public void submit(@NotNull PoseStack poseStack, @NotNull SubmitNodeCollector submitNodeCollector, int i, @NotNull OozeRenderState renderState, float v, float v1) {
        boolean flag = renderState.appearsGlowing() && renderState.isInvisible;
        if (!renderState.isInvisible || flag) {
            int overlayCoords = LivingEntityRenderer.getOverlayCoords(renderState, 0.0F);
            if (flag) {
                submitNodeCollector.order(1).submitModel(
                        this.model,
                        renderState,
                        poseStack,
                        RenderTypes.outline(OozeRenderer.OOZE_LOCATION),
                        i,
                        overlayCoords,
                        -1,
                        null,
                        renderState.outlineColor,
                        null
                );
            } else {
                submitNodeCollector.order(1).submitModel(
                        this.model,
                        renderState,
                        poseStack,
                        RenderTypes.entityTranslucent(OozeRenderer.OOZE_LOCATION),
                        i,
                        overlayCoords,
                        -1,
                        null,
                        renderState.outlineColor,
                        null
                );
            }
        }
    }
}

