package com.startraveler.verdant.client.renderer;

import com.startraveler.verdant.Constants;
import com.startraveler.verdant.client.layer.GenericEyesLayer;
import com.startraveler.verdant.client.VerdantModelLayers;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.monster.spider.SpiderModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.monster.spider.Spider;
import org.jetbrains.annotations.NotNull;

public class SkullSpiderRenderer<T extends Spider> extends MobRenderer<T, @NotNull LivingEntityRenderState, @NotNull SpiderModel> {
    private static final Identifier SKULL_SPIDER_LOCATION = Constants.id("textures/entity/spider/skull_spider.png");
    private static final Identifier SKULL_SPIDER_EYES_LOCATION = Constants.id(
            "textures/entity/spider/skull_spider_eyes.png");

    public SkullSpiderRenderer(EntityRendererProvider.Context context) {
        this(context, VerdantModelLayers.SKULL_SPIDER);
    }

    public SkullSpiderRenderer(EntityRendererProvider.Context context, ModelLayerLocation layer) {
        super(context, new SpiderModel(context.bakeLayer(layer)), 0.8F);
        this.addLayer(new GenericEyesLayer<>(this, SKULL_SPIDER_EYES_LOCATION));
        this.shadowRadius = 0.56F;
    }

    public @NotNull Identifier getTextureLocation(@NotNull LivingEntityRenderState state) {
        return SKULL_SPIDER_LOCATION;
    }

    @Override
    protected float getFlipDegrees() {
        return 180.0F;
    }

    @Override
    public LivingEntityRenderState createRenderState() {
        return new LivingEntityRenderState();
    }
}
