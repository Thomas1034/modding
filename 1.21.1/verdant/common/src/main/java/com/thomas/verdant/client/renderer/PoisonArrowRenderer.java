package com.thomas.verdant.client.renderer;

import com.thomas.verdant.Constants;
import com.thomas.verdant.entity.custom.PoisonArrowEntity;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.ArrowRenderState;
import net.minecraft.resources.ResourceLocation;

public class PoisonArrowRenderer extends ArrowRenderer<PoisonArrowEntity, ArrowRenderState> {
    public static final ResourceLocation POISON_ARROW_LOCATION = ResourceLocation.fromNamespaceAndPath(
            Constants.MOD_ID,
            "textures/entity/projectiles/poison_arrow.png"
    );

    public PoisonArrowRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    protected ResourceLocation getTextureLocation(ArrowRenderState arrowRenderState) {
        return POISON_ARROW_LOCATION;
    }

    @Override
    public ArrowRenderState createRenderState() {
        return new ArrowRenderState();
    }
}
