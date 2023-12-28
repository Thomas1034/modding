package com.thomas.zirconmod.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.thomas.zirconmod.ZirconMod;
import com.thomas.zirconmod.entity.custom.MoleEntity;

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
        return new ResourceLocation(ZirconMod.MOD_ID, "textures/entity/mole.png");
    }

    @Override
    public void render(MoleEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack,
                       MultiBufferSource pBuffer, int pPackedLight) {
        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
    }
}
