package com.thomas.zirconmod.entity.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.thomas.zirconmod.ZirconMod;
import com.thomas.zirconmod.entity.client.ModModelLayers;
import com.thomas.zirconmod.entity.client.model.GustModel;
import com.thomas.zirconmod.entity.custom.GustEntity;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;

public class GustRenderer extends MobRenderer<GustEntity, GustModel<GustEntity>> {

	public GustRenderer(EntityRendererProvider.Context context) {
		super(context, new GustModel<>(context.bakeLayer(ModModelLayers.GUST_LAYER)), 0.4F);
	}

	public void render(GustEntity pEntity, float p_115977_, float p_115978_, PoseStack pMatrixStack,
			MultiBufferSource bufferSource, int p_115981_) {
		if (pEntity.isBaby()) {
			pMatrixStack.scale(0.5f, 0.5f, 0.5f);
		}
		
		super.render(pEntity, p_115977_, p_115978_, pMatrixStack, bufferSource, p_115981_);
	}

	public ResourceLocation getTextureLocation(GustEntity gust) {

		return new ResourceLocation(ZirconMod.MOD_ID, "textures/entity/gust.png");
	}
	
	@Override
	protected int getBlockLightLevel(GustEntity entity, BlockPos pos)
	{
		return 2;
	}


}