package com.thomas.zirconmod.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.thomas.zirconmod.ZirconMod;
import com.thomas.zirconmod.entity.custom.WispEntity;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class WispRenderer extends MobRenderer<WispEntity, WispModel<WispEntity>> {

	public WispRenderer(EntityRendererProvider.Context context) {
		super(context, new WispModel<>(context.bakeLayer(ModModelLayers.WISP_LAYER)), 0.4F);
	}

	public void render(WispEntity pEntity, float p_115977_, float p_115978_, PoseStack pMatrixStack,
			MultiBufferSource p_115980_, int p_115981_) {
		if (pEntity.isBaby()) {
			pMatrixStack.scale(0.5f, 0.5f, 0.5f);
		}
		
		super.render(pEntity, p_115977_, p_115978_, pMatrixStack, p_115980_, p_115981_);
	}

	public ResourceLocation getTextureLocation(WispEntity wisp) {
		// System.out.println("Rendering " + wisp.getWispType() + " with: " +
		// wisp.getProfession());

		String profession = wisp.getProfession().toString();

		return new ResourceLocation(ZirconMod.MOD_ID, "textures/entity/wisp/" + profession + ".png");
	}


}