package com.thomas.verdant.entity;

import javax.annotation.Nullable;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.thomas.verdant.Verdant;
import com.thomas.verdant.overgrowth.EntityOvergrowth;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

// This is an example of a layer; I simply copied it from HumanoidArmorLayer in my case. 
public class VinesLayer<T extends LivingEntity, M extends HumanoidModel<T>, A extends HumanoidModel<T>>
		extends HumanoidArmorLayer<T, M, A> {
	private final A innerModel;
	private final A outerModel;
	private static final Item AIR_ITEM = Items.AIR;
	private static final ItemStack AIR = new ItemStack(AIR_ITEM);

	public VinesLayer(RenderLayerParent<T, M> parent, A inner, A outer, ModelManager manager) {
		super(parent, inner, outer, manager);
		this.innerModel = inner;
		this.outerModel = outer;
	}

	// Copied here because of course it's private.
	private boolean usesInnerModel(EquipmentSlot slot) {
		return slot == EquipmentSlot.LEGS;
	}

	public void render(PoseStack stack, MultiBufferSource bufferSource, int p_117098_, T entity, float p_117100_,
			float p_117101_, float p_117102_, float p_117103_, float p_117104_, float p_117105_) {
		this.renderArmorPiece(stack, bufferSource, entity, EquipmentSlot.CHEST, p_117098_,
				this.getArmorModel(EquipmentSlot.CHEST));
		this.renderArmorPiece(stack, bufferSource, entity, EquipmentSlot.LEGS, p_117098_,
				this.getArmorModel(EquipmentSlot.LEGS));
		this.renderArmorPiece(stack, bufferSource, entity, EquipmentSlot.FEET, p_117098_,
				this.getArmorModel(EquipmentSlot.FEET));
		this.renderArmorPiece(stack, bufferSource, entity, EquipmentSlot.HEAD, p_117098_,
				this.getArmorModel(EquipmentSlot.HEAD));
	}

	private A getArmorModel(EquipmentSlot slot) {
		return (A) (this.usesInnerModel(slot) ? this.innerModel : this.outerModel);
	}

	// Overrode so it will always render.
	private void renderArmorPiece(PoseStack stack, MultiBufferSource bufferSource, T entity, EquipmentSlot slot,
			int p_117123_, A modelToRenderOn) {
		this.getParentModel().copyPropertiesTo(modelToRenderOn);
		this.setPartVisibility(modelToRenderOn, slot);
		net.minecraft.client.model.Model model = getArmorModelHook(entity, AIR, slot, modelToRenderOn);
		boolean flag = this.usesInnerModel(slot);

		this.renderModel(stack, bufferSource, p_117123_, null, model, flag, 1.0F, 1.0F, 1.0F,
				this.getArmorResource(entity, AIR, slot, null));
	}

	// This was private too.
	private void renderModel(PoseStack stack, MultiBufferSource bufferSource, int p_289681_, ArmorItem armorItem,
			net.minecraft.client.model.Model model, boolean p_289668_, float p_289678_, float p_289674_,
			float p_289693_, ResourceLocation armorResource) {
		VertexConsumer vertexconsumer = bufferSource.getBuffer(RenderType.armorCutoutNoCull(armorResource));
		model.renderToBuffer(stack, vertexconsumer, p_289681_, OverlayTexture.NO_OVERLAY, p_289678_, p_289674_,
				p_289693_, 1.0F);
	}

	// Overrode here too.
	@Override
	public ResourceLocation getArmorResource(Entity entity, ItemStack stack, EquipmentSlot slot,
			@Nullable String type) {

		int progressionLevel = EntityOvergrowth.getLevel(entity);
		String textureSuffix = EntityOvergrowth.getTextureSuffix(progressionLevel);
		String texture = "vines" + textureSuffix;

		String s1 = String.format(java.util.Locale.ROOT, "textures/models/armor/%s_layer_%d%s.png", texture,
				(usesInnerModel(slot) ? 2 : 1), type == null ? "" : String.format(java.util.Locale.ROOT, "_%s", type));

		return new ResourceLocation(Verdant.MOD_ID, s1);
	}

}
