package com.thomas.cloudscape.entity.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.thomas.cloudscape.Cloudscape;
import com.thomas.cloudscape.entity.client.ModModelLayers;
import com.thomas.cloudscape.entity.client.model.MoleModel;
import com.thomas.cloudscape.entity.custom.MoleEntity;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class MoleRenderer extends MobRenderer<MoleEntity, MoleModel<MoleEntity>> {
    public MoleRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new MoleModel<>(pContext.bakeLayer(ModModelLayers.MOLE_LAYER)), 0.5f);
    }

    @Override
    public ResourceLocation getTextureLocation(MoleEntity pEntity) {
        return new ResourceLocation(Cloudscape.MOD_ID, "textures/entity/mole.png");
    }

    @Override
    public void render(MoleEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack,
                       MultiBufferSource pBuffer, int pPackedLight) {
        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
    }
}
