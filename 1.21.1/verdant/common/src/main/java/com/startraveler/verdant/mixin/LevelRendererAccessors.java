package com.startraveler.verdant.mixin;

import net.minecraft.client.renderer.LevelRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(LevelRenderer.class)
public interface LevelRendererAccessors {

    @Invoker("setSectionDirty")
    void verdant$setSectionDirty(int sectionX, int sectionY, int sectionZ, boolean reRenderOnMainThread);
}
