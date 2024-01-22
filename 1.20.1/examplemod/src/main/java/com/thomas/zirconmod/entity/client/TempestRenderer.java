package com.thomas.zirconmod.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.thomas.zirconmod.ZirconMod;
import com.thomas.zirconmod.entity.custom.TempestEntity;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.Ghast;

public class TempestRenderer extends MobRenderer<TempestEntity, TempestModel<TempestEntity>> {
    public TempestRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new TempestModel<>(pContext.bakeLayer(ModModelLayers.TEMPEST_LAYER)), 0.5f);
    }

    @Override
    public ResourceLocation getTextureLocation(TempestEntity pEntity) {
        return new ResourceLocation(ZirconMod.MOD_ID, "textures/entity/nimbula.png");
    }

    @Override
    public void render(TempestEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack,
                       MultiBufferSource pBuffer, int pPackedLight) {
    	super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
    }
    
    @Override
    protected void scale(TempestEntity p_114757_, PoseStack p_114758_, float p_114759_) {
        p_114758_.scale(4.5F, 4.5F, 4.5F);
     }
}
