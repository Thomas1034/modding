package com.thomas.verdant.entity.client.renderer;

import com.thomas.verdant.Verdant;
import com.thomas.verdant.entity.custom.HallucinatedCreeperEntity;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.CreeperRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;

public class HallucinatedCreeperRenderer extends CreeperRenderer {
	private static final ResourceLocation CREEPER_LOCATION = new ResourceLocation(
			"textures/entity/creeper/creeper.png");
	private static final ResourceLocation EMPTY = new ResourceLocation(Verdant.MOD_ID,
			"textures/entity/hallucinated_creeper/empty.png");

	public HallucinatedCreeperRenderer(Context context) {
		super(context);
	}

	@SuppressWarnings("resource")
	public ResourceLocation getTextureLocation(HallucinatedCreeperEntity creeper) {
		if (creeper.matchesPlayer(Minecraft.getInstance().player)) {
			return CREEPER_LOCATION;
		}
		return EMPTY;
	}


}
