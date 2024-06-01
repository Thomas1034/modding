package com.thomas.verdant.entity.client.renderer;

import com.thomas.verdant.Verdant;
import com.thomas.verdant.entity.client.OvergrownZombieOuterLayer;

import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.ZombieRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.Zombie;

public class OvergrownZombieRenderer extends ZombieRenderer {

	private static final ResourceLocation ZOMBIE_LOCATION = new ResourceLocation(Verdant.MOD_ID,
			"textures/entity/overgrown_zombie/overgrown_zombie.png");

	public OvergrownZombieRenderer(Context context) {
		super(context);
		this.addLayer(new OvergrownZombieOuterLayer(this, context.getModelSet()));
	}

	public ResourceLocation getTextureLocation(Zombie zombie) {
		return ZOMBIE_LOCATION;
	}

}
