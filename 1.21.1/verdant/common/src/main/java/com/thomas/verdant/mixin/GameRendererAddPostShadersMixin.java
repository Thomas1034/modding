package com.thomas.verdant.mixin;

import com.mojang.blaze3d.resource.CrossFrameResourcePool;
import com.thomas.verdant.Constants;
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
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public class GameRendererAddPostShadersMixin {

    @Unique
    private static final ResourceLocation COLORBLIND_POST_CHAIN_ID = ResourceLocation.fromNamespaceAndPath(
            Constants.MOD_ID,
            "desaturate"
    );
    @Unique
    private static final ResourceLocation SEPIA_POST_CHAIN_ID = ResourceLocation.fromNamespaceAndPath(
            Constants.MOD_ID,
            "sepia"
    );
    @Unique
    private static final ResourceLocation RED_GREEN_POST_CHAIN_ID = ResourceLocation.fromNamespaceAndPath(
            Constants.MOD_ID,
            "red_green"
    );
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

                    postchain.setUniform("Radius", (2 + instance.getAmplifier()) * 4 * screenEffectScale);
                    postchain.process(this.minecraft.getMainRenderTarget(), this.resourcePool);
                }
            }
        }
    }

    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/LevelRenderer;doEntityOutline()V", shift = At.Shift.AFTER))
    private void verdant$addColorblindFilter(DeltaTracker deltaTracker, boolean renderLevel, CallbackInfo ci) {

        Player player = this.minecraft.player;
        if (player != null) {
            MobEffectInstance instance = player.getEffect(MobEffectRegistry.COLORBLIND.asHolder());

            if (instance != null) {
                PostChain postchain = this.minecraft.getShaderManager()
                        .getPostChain(COLORBLIND_POST_CHAIN_ID, LevelTargetBundle.MAIN_TARGETS);
                if (postchain != null) {
                    postchain.process(this.minecraft.getMainRenderTarget(), this.resourcePool);
                }
            }
        }
    }

    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/LevelRenderer;doEntityOutline()V", shift = At.Shift.AFTER))
    private void verdant$addSepiaFilter(DeltaTracker deltaTracker, boolean renderLevel, CallbackInfo ci) {

        Player player = this.minecraft.player;
        if (player != null) {
            MobEffectInstance instance = player.getEffect(MobEffectRegistry.SEPIA.asHolder());

            if (instance != null) {
                PostChain postchain = this.minecraft.getShaderManager()
                        .getPostChain(SEPIA_POST_CHAIN_ID, LevelTargetBundle.MAIN_TARGETS);
                if (postchain != null) {
                    postchain.process(this.minecraft.getMainRenderTarget(), this.resourcePool);
                }
            }
        }
    }

    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/LevelRenderer;doEntityOutline()V", shift = At.Shift.AFTER))
    private void verdant$addRedGreenFilter(DeltaTracker deltaTracker, boolean renderLevel, CallbackInfo ci) {

        Player player = this.minecraft.player;
        if (player != null) {
            MobEffectInstance instance = player.getEffect(MobEffectRegistry.RED_GREEN.asHolder());

            if (instance != null) {
                PostChain postchain = this.minecraft.getShaderManager()
                        .getPostChain(RED_GREEN_POST_CHAIN_ID, LevelTargetBundle.MAIN_TARGETS);
                if (postchain != null) {
                    postchain.process(this.minecraft.getMainRenderTarget(), this.resourcePool);
                }
            }
        }
    }
}
