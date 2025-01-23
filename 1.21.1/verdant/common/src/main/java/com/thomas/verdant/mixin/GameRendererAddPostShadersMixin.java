package com.thomas.verdant.mixin;

import com.mojang.blaze3d.resource.CrossFrameResourcePool;
import com.thomas.verdant.registry.MobEffectRegistry;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.LevelTargetBundle;
import net.minecraft.client.renderer.PostChain;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public class GameRendererAddPostShadersMixin {

    @Shadow
    @Final
    private static ResourceLocation BLUR_POST_CHAIN_ID;
    @Final
    @Shadow
    private Minecraft minecraft;
    @Final
    @Shadow
    private CrossFrameResourcePool resourcePool;

    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/LevelRenderer;doEntityOutline()V", shift = At.Shift.AFTER))
    private void verdant$addBlurOverlay(DeltaTracker deltaTracker, boolean renderLevel, CallbackInfo ci) {

        Player player = this.minecraft.player;
        if (player != null) {
            MobEffectInstance instance = player.getEffect(MobEffectRegistry.BLURRED.asHolder());

            if (instance != null) {
                PostChain postchain = this.minecraft.getShaderManager()
                        .getPostChain(BLUR_POST_CHAIN_ID, LevelTargetBundle.MAIN_TARGETS);
                if (postchain != null) {
                    float screenEffectScale = this.minecraft.options.screenEffectScale().get().floatValue();

                    postchain.setUniform("Radius", (1 + instance.getAmplifier()) * 2 * screenEffectScale);
                    postchain.process(this.minecraft.getMainRenderTarget(), this.resourcePool);
                }
            }
        }
    }
}
