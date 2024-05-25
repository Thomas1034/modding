package com.thomas.verdant.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.thomas.verdant.Verdant;

import net.minecraft.client.model.DrownedModel;
import net.minecraft.client.model.ZombieModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.Zombie;

public class VerdantZombieOuterLayer extends RenderLayer<Zombie, ZombieModel<Zombie>> {
	private static final ResourceLocation ZOMBIE_OUTER_LAYER_LOCATION = new ResourceLocation(Verdant.MOD_ID,
			"textures/entity/verdant_zombie/verdant_zombie_outer_layer.png");
	private final DrownedModel<Zombie> model;

	public VerdantZombieOuterLayer(RenderLayerParent<Zombie, ZombieModel<Zombie>> layerParent,
			EntityModelSet modelSet) {
		super(layerParent);
		this.model = new DrownedModel<>(modelSet.bakeLayer(ModelLayers.DROWNED_OUTER_LAYER));
	}

	public void render(PoseStack p_116924_, MultiBufferSource p_116925_, int p_116926_, Zombie p_116927_,
			float p_116928_, float p_116929_, float p_116930_, float p_116931_, float p_116932_, float p_116933_) {
		coloredCutoutModelCopyLayerRender(this.getParentModel(), this.model, ZOMBIE_OUTER_LAYER_LOCATION, p_116924_,
				p_116925_, p_116926_, p_116927_, p_116928_, p_116929_, p_116931_, p_116932_, p_116933_, p_116930_, 1.0F,
				1.0F, 1.0F);
	}
}