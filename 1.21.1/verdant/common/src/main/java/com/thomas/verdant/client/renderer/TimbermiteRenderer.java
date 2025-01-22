package com.thomas.verdant.client.renderer;

import com.thomas.verdant.Constants;
import com.thomas.verdant.entity.custom.TimbermiteEntity;
import net.minecraft.client.model.EndermiteModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.resources.ResourceLocation;

public class TimbermiteRenderer extends MobRenderer<TimbermiteEntity, LivingEntityRenderState, EndermiteModel> {
    private static final ResourceLocation TIMBERMITE_LOCATION = ResourceLocation.fromNamespaceAndPath(
            Constants.MOD_ID, "textures/entity/timbermite.png");

    public TimbermiteRenderer(EntityRendererProvider.Context context) {
        super(context, new EndermiteModel(context.bakeLayer(ModelLayers.ENDERMITE)), 0.3F);
    }

    public ResourceLocation getTextureLocation(LivingEntityRenderState p_363663_) {
        return TIMBERMITE_LOCATION;
    }

    protected float getFlipDegrees() {
        return 180.0F;
    }

    public LivingEntityRenderState createRenderState() {
        return new LivingEntityRenderState();
    }
}