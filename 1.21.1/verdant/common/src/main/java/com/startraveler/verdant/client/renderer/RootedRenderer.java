package com.startraveler.verdant.client.renderer;

import com.startraveler.verdant.Constants;
import com.startraveler.verdant.client.layer.RootedOuterLayer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.ZombieRenderer;
import net.minecraft.client.renderer.entity.state.ZombieRenderState;
import net.minecraft.resources.ResourceLocation;

public class RootedRenderer extends ZombieRenderer {

    private static final ResourceLocation ZOMBIE_LOCATION = ResourceLocation.fromNamespaceAndPath(
            Constants.MOD_ID,
            "textures/entity/zombie/rooted.png"
    );

    public RootedRenderer(Context context) {
        super(context);
        this.addLayer(new RootedOuterLayer(this, context.getModelSet()));

    }

    public ResourceLocation getTextureLocation(ZombieRenderState state) {
        return ZOMBIE_LOCATION;
    }

}
