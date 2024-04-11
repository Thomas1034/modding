package com.thomas.cloudscape.entity.client;

import com.thomas.cloudscape.ZirconMod;
import com.thomas.cloudscape.entity.client.model.TempestModel;
import com.thomas.cloudscape.entity.custom.TempestEntity;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EnergySwirlLayer;
import net.minecraft.resources.ResourceLocation;

public class TempestPowerLayer extends EnergySwirlLayer<TempestEntity, TempestModel<TempestEntity>> {
	   private static final ResourceLocation POWER_LOCATION = new ResourceLocation(ZirconMod.MOD_ID, "textures/entity/tempest_charging.png");
	   private final TempestModel<TempestEntity> model;

	   public TempestPowerLayer(RenderLayerParent<TempestEntity, TempestModel<TempestEntity>> model, EntityModelSet modelSet) {
	      super(model);
	      this.model = new TempestModel<TempestEntity>(modelSet.bakeLayer(ModModelLayers.TEMPEST_POWER_LAYER));
	   }

	   protected float xOffset(float p_116683_) {
	      return p_116683_ * 0.01F;
	   }

	   protected ResourceLocation getTextureLocation() {
	      return POWER_LOCATION;
	   }

	   protected EntityModel<TempestEntity> model() {
	      return this.model;
	   }
	}
