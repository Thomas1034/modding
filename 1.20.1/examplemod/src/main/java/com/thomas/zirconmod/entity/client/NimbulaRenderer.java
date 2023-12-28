package com.thomas.zirconmod.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.thomas.zirconmod.ZirconMod;
import com.thomas.zirconmod.entity.custom.NimbulaEntity;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class NimbulaRenderer extends MobRenderer<NimbulaEntity, NimbulaModel<NimbulaEntity>> {

   public NimbulaRenderer(EntityRendererProvider.Context p_174391_) {
      super(p_174391_, new NimbulaModel<>(p_174391_.bakeLayer(ModModelLayers.NIMBULA_LAYER)), 0.25F);
   }

   public void render(NimbulaEntity pEntity, float p_115977_, float p_115978_, PoseStack pMatrixStack, MultiBufferSource p_115980_, int p_115981_) {
	   if(pEntity.isBaby()) {
           pMatrixStack.scale(0.5f, 0.5f, 0.5f);
       }
	   
	   super.render(pEntity, p_115977_, p_115978_, pMatrixStack, p_115980_, p_115981_);
   }

   public ResourceLocation getTextureLocation(NimbulaEntity p_115974_) {
       return new ResourceLocation(ZirconMod.MOD_ID, "textures/entity/nimbula.png");
   }
}
