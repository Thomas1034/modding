package com.startraveler.verdant.client.layer;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.NotNull;

public class GenericEyesLayer<S extends EntityRenderState, M extends EntityModel<@NotNull S>> extends EyesLayer<@NotNull S, M> {
    private final RenderType renderType;

    public GenericEyesLayer(RenderLayerParent<@NotNull S, M> parent, Identifier texture) {
        super(parent);
        this.renderType = RenderTypes.eyes(texture);
    }

    @Override
    public @NotNull RenderType renderType() {
        return this.renderType;
    }
}
