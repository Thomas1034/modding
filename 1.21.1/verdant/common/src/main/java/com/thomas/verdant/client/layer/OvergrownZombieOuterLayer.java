package com.thomas.verdant.client.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.thomas.verdant.Constants;
import net.minecraft.client.model.ZombieModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.state.ZombieRenderState;
import net.minecraft.resources.ResourceLocation;

public class OvergrownZombieOuterLayer extends RenderLayer<ZombieRenderState, ZombieModel<ZombieRenderState>> {
    private static final ResourceLocation OVERGROWN_ZOMBIE_OUTER_LAYER_LOCATION = ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID,
            "textures/entity/overgrown_zombie/overgrown_zombie_outer_layer.png"
    );
    private final ZombieModel<ZombieRenderState> model;
    private final ZombieModel<ZombieRenderState> babyModel;

    public OvergrownZombieOuterLayer(RenderLayerParent<ZombieRenderState, ZombieModel<ZombieRenderState>> renderer, EntityModelSet modelSet) {
        super(renderer);
        this.model = new ZombieModel<>(modelSet.bakeLayer(ModelLayers.DROWNED_OUTER_LAYER));
        this.babyModel = new ZombieModel<>(modelSet.bakeLayer(ModelLayers.DROWNED_BABY_OUTER_LAYER));
    }

    public void render(PoseStack stack, MultiBufferSource source, int packedLight, ZombieRenderState renderState, float f, float g) {
        ZombieModel<ZombieRenderState> drownedModel = renderState.isBaby ? this.babyModel : this.model;
        coloredCutoutModelCopyLayerRender(
                drownedModel,
                OVERGROWN_ZOMBIE_OUTER_LAYER_LOCATION,
                stack,
                source,
                packedLight,
                renderState,
                -1
        );
    }
}
