package com.thomas.cloudscape.entity.client.renderer;

import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;

import com.thomas.cloudscape.Cloudscape;

import net.minecraft.client.renderer.entity.PhantomRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.Phantom;

public class WraithRenderer extends PhantomRenderer {

	public WraithRenderer(Context context) {
		super(context);
	}

	public ResourceLocation getTextureLocation(Phantom p_115679_) {
		return new ResourceLocation(Cloudscape.MOD_ID, "textures/entity/wraith.png");
	}

}
