package com.thomas.zirconmod.entity.client.renderer;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.ItemSupplier;

public class ModThrownItemRenderer<T extends Entity & ItemSupplier> extends ThrownItemRenderer {

	public ModThrownItemRenderer(EntityRendererProvider.Context context, float scale, boolean bright) {
		super(context, scale, bright);
	}

	public ModThrownItemRenderer(EntityRendererProvider.Context context) {
		this(context, 1.0F, false);
	}

	public static ModThrownItemRenderer BallLightningRenderer(EntityRendererProvider.Context context) {
		
		return new ModThrownItemRenderer(context, 4.0f, true);
		
	}
}
