package com.thomas.verdant.entity.client.renderer;

import com.thomas.verdant.Verdant;
import com.thomas.verdant.entity.custom.PoisonIvyArrowEntity;

import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class PoisonIvyArrowRenderer extends ArrowRenderer<PoisonIvyArrowEntity> {

	private static final ResourceLocation TEXTURE = new ResourceLocation(Verdant.MOD_ID,
			"textures/entity/projectiles/poison_arrow.png");

	public PoisonIvyArrowRenderer(EntityRendererProvider.Context context) {
		super(context);
	}

	@Override
	public ResourceLocation getTextureLocation(PoisonIvyArrowEntity arrow) {
		return TEXTURE;
	}

}
