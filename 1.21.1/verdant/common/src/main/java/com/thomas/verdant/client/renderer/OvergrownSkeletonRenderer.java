package com.thomas.verdant.client.renderer;

import com.thomas.verdant.Constants;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.SkeletonRenderer;
import net.minecraft.client.renderer.entity.state.SkeletonRenderState;
import net.minecraft.resources.ResourceLocation;

public class OvergrownSkeletonRenderer extends SkeletonRenderer {
    private static final ResourceLocation SKELETON_LOCATION = ResourceLocation.fromNamespaceAndPath(
            Constants.MOD_ID,
            "textures/entity/overgrown_skeleton/overgrown_skeleton.png"
    );

    public OvergrownSkeletonRenderer(Context context) {
        super(context);
    }

    public ResourceLocation getTextureLocation(SkeletonRenderState state) {
        return SKELETON_LOCATION;
    }
}
