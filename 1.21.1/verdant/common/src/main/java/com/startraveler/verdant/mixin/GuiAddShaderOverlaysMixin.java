/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * If you modify this file, please include a notice stating the changes:
 * Example: "Modified by [Your Name] on [Date] - [Short Description of Changes]"
 */
package com.startraveler.verdant.mixin;

import com.startraveler.verdant.Constants;
import com.startraveler.verdant.registry.MobEffectRegistry;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;
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

// TODO
@Mixin(Gui.class)
public class GuiAddShaderOverlaysMixin {
    @Unique
    private static final Identifier SAPPY_LOCATION = Identifier.fromNamespaceAndPath(
            Constants.MOD_ID,
            "textures/misc/sappy_outline.png"
    );
    @Shadow
    @Final
    public static Identifier NAUSEA_LOCATION;
    @Shadow
    @Final
    private Minecraft minecraft;

    @Inject(method = "extractCameraOverlays", at = @At(value = "TAIL"))
    private void addOverlays(GuiGraphicsExtractor guiGraphics, DeltaTracker deltaTracker, CallbackInfo ci) {
        float screenEffectScale = this.minecraft.options.screenEffectScale().get().floatValue();
        Player player = this.minecraft.player;
        if (player != null) {
            MobEffectInstance blurringInstance = player.getEffect(MobEffectRegistry.BLURRING.asHolder());
            if (blurringInstance != null) {
                this.verdant$renderBlurredOverlay(guiGraphics, Mth.map(1.25f - screenEffectScale, 0, 1.2f, 0, 1));
            }
            MobEffectInstance sappyInstance = player.getEffect(MobEffectRegistry.SAPPY_VISION.asHolder());
            if (sappyInstance != null) {
                this.verdant$renderSappyOverlay(
                        guiGraphics,
                        1f
                );
            }
        }
    }

    @Unique
    @SuppressWarnings("SameParameterValue")
    private void verdant$renderSappyOverlay(GuiGraphicsExtractor guiGraphics, float intensity) {
        int guiWidth = guiGraphics.guiWidth();
        int guiHeight = guiGraphics.guiHeight();
        float r = 1 * intensity;
        float g = 1 * intensity;
        float b = 1 * intensity;
        guiGraphics.blit(
                RenderPipelines.GUI_NAUSEA_OVERLAY,
                SAPPY_LOCATION,
                0,
                0,
                0.0F,
                0.0F,
                guiWidth,
                guiHeight,
                guiWidth,
                guiHeight,
                ARGB.colorFromFloat(intensity, r, g, b)
        );
    }

    @Unique
    private void verdant$renderBlurredOverlay(GuiGraphicsExtractor guiGraphics, float intensity) {
        int guiWidth = guiGraphics.guiWidth();
        int guiHeight = guiGraphics.guiHeight();
        float r = 0.4F * intensity;
        float g = 0.4F * intensity;
        float b = 0.0F * intensity;
        guiGraphics.blit(
                RenderPipelines.GUI_NAUSEA_OVERLAY,
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

