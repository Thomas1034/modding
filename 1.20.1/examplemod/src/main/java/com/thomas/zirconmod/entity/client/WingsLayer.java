package com.thomas.zirconmod.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.thomas.zirconmod.item.custom.AbstractWingsItem;
import com.thomas.zirconmod.util.TranslucentWingsItem;

import net.minecraft.client.model.ElytraModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.ElytraLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.PlayerModelPart;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

// Credit for this goes to LocusAzzurro
// Source at: https://github.com/LocusAzzurro/IcarusWings/blob/1.19.4/src/main/java/org/mineplugin/locusazzurro/icaruswings/event/ModClientRenderEventHandler.java
// Changes were made to adapt the code to this project, mainly by removing superfluous code.
public class WingsLayer<T extends LivingEntity, M extends EntityModel<T>> extends ElytraLayer<T, M> {

	private final ElytraModel<T> elytraModel;

	public WingsLayer(RenderLayerParent<T, M> renderIn, EntityModelSet root) {
		super(renderIn, root);
		elytraModel = new ElytraModel<>(root.bakeLayer(ModelLayers.ELYTRA));
	}

	@Override
	public boolean shouldRender(ItemStack stack, T entity) {
		return (stack.getItem() instanceof AbstractWingsItem);
	}

	@Override
	public ResourceLocation getElytraTexture(ItemStack stack, T entity) {
		Item item = stack.getItem();
		if (item instanceof AbstractWingsItem) {
			return ((AbstractWingsItem) item).getTexture();
		}
		// If the texture is not found, throw an exception.
		throw new IllegalArgumentException("Item is not an instance of AbstractWingsItem");
	}

	@Override
	public void render(PoseStack matrixStackIn, MultiBufferSource bufferIn, int packetLightIn, T entityLivingBaseIn,
			float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw,
			float headPitch) {
		ItemStack itemstack = entityLivingBaseIn.getItemBySlot(EquipmentSlot.CHEST);
		if (shouldRender(itemstack, entityLivingBaseIn)) {
			ResourceLocation resourceLocation;
			if (entityLivingBaseIn instanceof AbstractClientPlayer player) {
				if (player.isElytraLoaded() && player.getElytraTextureLocation() != null) {
					resourceLocation = player.getElytraTextureLocation();
				} else if (player.isCapeLoaded() && player.getCloakTextureLocation() != null
						&& player.isModelPartShown(PlayerModelPart.CAPE)) {
					resourceLocation = player.getCloakTextureLocation();
				} else {
					resourceLocation = getElytraTexture(itemstack, entityLivingBaseIn);
				}
			} else {
				resourceLocation = getElytraTexture(itemstack, entityLivingBaseIn);
			}
			matrixStackIn.pushPose();
			matrixStackIn.translate(0.0D, 0.0D, 0.125D);
			this.getParentModel().copyPropertiesTo(this.elytraModel);
			this.elytraModel.setupAnim(entityLivingBaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw,
					headPitch);
			boolean isWingsTranslucent = itemstack.getItem() instanceof TranslucentWingsItem;
			VertexConsumer vertexBuilder = ItemRenderer.getArmorFoilBuffer(bufferIn,
					isWingsTranslucent ? RenderType.entityTranslucent(resourceLocation)
							: RenderType.armorCutoutNoCull(resourceLocation),
					false, !isWingsTranslucent && itemstack.hasFoil());

			this.elytraModel.renderToBuffer(matrixStackIn, vertexBuilder, packetLightIn, OverlayTexture.NO_OVERLAY,
					1.0F, 1.0F, 1.0F, 1.0F);

			matrixStackIn.popPose();
		}
	}

}
