package com.thomas.verdant.mixin;

import com.thomas.verdant.registry.MobEffectRegistry;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.util.ARGB;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.minecraft.client.gui.Gui.NAUSEA_LOCATION;

@Mixin(Gui.class)
public class GuiAddShaderOverlaysMixin {
    @Shadow
    @Final
    private Minecraft minecraft;

    @Inject(method = "renderCameraOverlays", at = @At(value = "TAIL"))
    private void addOverlays(GuiGraphics guiGraphics, DeltaTracker deltaTracker, CallbackInfo ci) {
        float screenEffectScale = this.minecraft.options.screenEffectScale().get().floatValue();
        Player player = this.minecraft.player;
        if (player != null) {
            MobEffectInstance instance = player.getEffect(MobEffectRegistry.BLURRED.asHolder());
            if (instance != null) {
                this.verdant$renderBlurredOverlay(guiGraphics, Mth.map(1.25f - screenEffectScale, 0, 1.2f, 0, 1));
            }
        }
    }


    @Unique
    private void verdant$renderBlurredOverlay(GuiGraphics guiGraphics, float intensity) {
        int guiWidth = guiGraphics.guiWidth();
        int guiHeight = guiGraphics.guiHeight();
        float r = 0.4F * intensity;
        float g = 0.4F * intensity;
        float b = 0.0F * intensity;
        guiGraphics.blit(
                (location) -> RenderType.guiNauseaOverlay(),
                NAUSEA_LOCATION,
                0,
                0,
                0.0F,
                0.0F,
                guiWidth,
                guiHeight,
                guiWidth,
                guiHeight,
                ARGB.colorFromFloat(1.0F, r, g, b)
        );
    }
}
