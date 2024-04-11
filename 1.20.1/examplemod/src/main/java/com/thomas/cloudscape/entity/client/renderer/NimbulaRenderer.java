package com.thomas.cloudscape.entity.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.thomas.cloudscape.ZirconMod;
import com.thomas.cloudscape.entity.client.ModModelLayers;
import com.thomas.cloudscape.entity.client.model.NimbulaModel;
import com.thomas.cloudscape.entity.custom.NimbulaEntity;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class NimbulaRenderer extends MobRenderer<NimbulaEntity, NimbulaModel<NimbulaEntity>> {
	
	public NimbulaRenderer(EntityRendererProvider.Context p_174391_) {
		super(p_174391_, new NimbulaModel<>(p_174391_.bakeLayer(ModModelLayers.NIMBULA_LAYER)), 0.25F);
	}

	public void render(NimbulaEntity nimbula, float f1, float f2, PoseStack poseStack,
			MultiBufferSource bufferSource, int i1) {
		if (nimbula.isBaby()) {
			poseStack.scale(0.5f, 0.5f, 0.5f);
		}
		
		// Change color if it's raining.
		if (nimbula.isInWaterOrRain()) {
			this.model.multColor(0.8f, 0.9f, 1.0f);
		}
		
		if (nimbula.level().isRaining()) {
			this.model.multColor(0.8f, 0.8f, 0.8f);
		}
		
		if (nimbula.level().isThundering()) {
			this.model.multColor(0.5f, 0.5f, 0.5f);
		}
		
		super.render(nimbula, f1, f2, poseStack, bufferSource, i1);
		this.model.resetColor();
	}

	public ResourceLocation getTextureLocation(NimbulaEntity nimbula) {
		if (nimbula.level().isThundering()) {
			return new ResourceLocation(ZirconMod.MOD_ID, "textures/entity/nimbula/black.png");
		} else if (nimbula.level().isRaining()) {
			return new ResourceLocation(ZirconMod.MOD_ID, "textures/entity/nimbula/grey.png");
		} else {
			if (nimbula.level().dimension() == Level.OVERWORLD) {
				return new ResourceLocation(ZirconMod.MOD_ID, "textures/entity/nimbula/green.png");
			} else {
				return new ResourceLocation(ZirconMod.MOD_ID, "textures/entity/nimbula/blue.png");
			}
		}
	}
}
