package com.thomas.zirconmod.entity.client.renderer;

import com.thomas.zirconmod.ZirconMod;

import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.PhantomRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.Phantom;

public class WraithRenderer extends PhantomRenderer {

	public WraithRenderer(Context context) {
		super(context);
	}

	public ResourceLocation getTextureLocation(Phantom p_115679_) {
		return new ResourceLocation(ZirconMod.MOD_ID, "textures/entity/wraith.png");
	}

}
