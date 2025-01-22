package com.thomas.verdant.client.renderer;

import com.thomas.verdant.Constants;
import com.thomas.verdant.client.layer.OvergrownZombieOuterLayer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.ZombieRenderer;
import net.minecraft.client.renderer.entity.state.ZombieRenderState;
import net.minecraft.resources.ResourceLocation;

public class OvergrownZombieRenderer extends ZombieRenderer {

    private static final ResourceLocation ZOMBIE_LOCATION = ResourceLocation.fromNamespaceAndPath(
            Constants.MOD_ID,
            "textures/entity/overgrown_zombie/overgrown_zombie.png"
    );
    private static final ResourceLocation DROWNED_OUTER_LAYER_LOCATION = ResourceLocation.withDefaultNamespace(
            "textures/entity/zombie/drowned_outer_layer.png");

    public OvergrownZombieRenderer(Context context) {
        super(context);
        this.addLayer(new OvergrownZombieOuterLayer(this, context.getModelSet()));

    }

    public ResourceLocation getTextureLocation(ZombieRenderState state) {
        return ZOMBIE_LOCATION;
    }

}
