package com.startraveler.verdant.mixin;

import net.minecraft.client.model.ArrowModel;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ArrowRenderer.class)
public interface ArrowRendererModelAccessor {

    @Accessor("model")
    ArrowModel verdant$getModel();
}
