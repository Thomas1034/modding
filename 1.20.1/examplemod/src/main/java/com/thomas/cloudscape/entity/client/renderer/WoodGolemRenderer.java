package com.thomas.cloudscape.entity.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.thomas.cloudscape.Cloudscape;
import com.thomas.cloudscape.entity.client.ModModelLayers;
import com.thomas.cloudscape.entity.client.model.WoodGolemModel;
import com.thomas.cloudscape.entity.custom.WoodGolemEntity;
import com.thomas.cloudscape.entity.variant.WoodGolemVariant;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class WoodGolemRenderer extends MobRenderer<WoodGolemEntity, WoodGolemModel<WoodGolemEntity>> {
	public WoodGolemRenderer(EntityRendererProvider.Context pContext) {
		super(pContext, new WoodGolemModel<>(pContext.bakeLayer(ModModelLayers.WOOD_GOLEM_LAYER)), 0.5f);
	}

	@Override
	public ResourceLocation getTextureLocation(WoodGolemEntity pEntity) {
		// Do get different versions here:
		if (pEntity.getTypeVariant() == WoodGolemVariant.ACACIA.getId()) {
			return new ResourceLocation(Cloudscape.MOD_ID, "textures/entity/wood_golem/acacia.png");
		} else if (pEntity.getTypeVariant() == WoodGolemVariant.BIRCH.getId()) {
			return new ResourceLocation(Cloudscape.MOD_ID, "textures/entity/wood_golem/birch.png");
		} else if (pEntity.getTypeVariant() == WoodGolemVariant.CHERRY.getId()) {
			return new ResourceLocation(Cloudscape.MOD_ID, "textures/entity/wood_golem/cherry.png");
		} else if (pEntity.getTypeVariant() == WoodGolemVariant.DARK_OAK.getId()) {
			return new ResourceLocation(Cloudscape.MOD_ID, "textures/entity/wood_golem/dark_oak.png");
		} else if (pEntity.getTypeVariant() == WoodGolemVariant.JUNGLE.getId()) {
			return new ResourceLocation(Cloudscape.MOD_ID, "textures/entity/wood_golem/jungle.png");
		} else if (pEntity.getTypeVariant() == WoodGolemVariant.MANGROVE.getId()) {
			return new ResourceLocation(Cloudscape.MOD_ID, "textures/entity/wood_golem/mangrove.png");
		} else if (pEntity.getTypeVariant() == WoodGolemVariant.OAK.getId()) {
			return new ResourceLocation(Cloudscape.MOD_ID, "textures/entity/wood_golem/oak.png");
		} else if (pEntity.getTypeVariant() == WoodGolemVariant.PALM.getId()) {
			return new ResourceLocation(Cloudscape.MOD_ID, "textures/entity/wood_golem/palm.png");
		} else if (pEntity.getTypeVariant() == WoodGolemVariant.SPRUCE.getId()) {
			return new ResourceLocation(Cloudscape.MOD_ID, "textures/entity/wood_golem/spruce.png");
		}
		return new ResourceLocation(Cloudscape.MOD_ID, "textures/entity/wood_golem/oak.png");

	}

	@Override
	public void render(WoodGolemEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack,
			MultiBufferSource pBuffer, int pPackedLight) {
		
		super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
	}
}
