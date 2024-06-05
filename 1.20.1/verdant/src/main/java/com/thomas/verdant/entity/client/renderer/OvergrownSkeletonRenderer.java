package com.thomas.verdant.entity.client.renderer;

import com.thomas.verdant.Verdant;

import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.SkeletonRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.AbstractSkeleton;

public class OvergrownSkeletonRenderer extends SkeletonRenderer {
	private static final ResourceLocation SKELETON_LOCATION = new ResourceLocation(Verdant.MOD_ID,
			"textures/entity/overgrown_skeleton/overgrown_skeleton.png");

	public OvergrownSkeletonRenderer(Context context) {
		super(context);
	}

	@Override
	public ResourceLocation getTextureLocation(AbstractSkeleton p_115941_) {
		return SKELETON_LOCATION;
	}

}
