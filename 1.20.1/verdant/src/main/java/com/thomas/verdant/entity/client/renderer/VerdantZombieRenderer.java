package com.thomas.verdant.entity.client.renderer;

import com.thomas.verdant.Verdant;
import com.thomas.verdant.entity.client.VerdantZombieOuterLayer;

import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.ZombieRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.Zombie;

public class VerdantZombieRenderer extends ZombieRenderer {

	private static final ResourceLocation ZOMBIE_LOCATION = new ResourceLocation(Verdant.MOD_ID,
			"textures/entity/verdant_zombie/verdant_zombie.png");

	public VerdantZombieRenderer(Context context) {
		super(context);
		this.addLayer(new VerdantZombieOuterLayer(this, context.getModelSet()));
	}

	public ResourceLocation getTextureLocation(Zombie zombie) {
		return ZOMBIE_LOCATION;
	}

}
