package com.thomas.verdant.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.thomas.verdant.effect.IntangibilityEffect;
import net.minecraft.client.renderer.ScreenEffectRenderer;
import net.minecraft.world.entity.player.Player;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ScreenEffectRenderer.class)
public class IntangibilityBlockVisionMixin {

    @ModifyExpressionValue(method = "renderScreenEffect", at = @At(value = "FIELD", target = "net/minecraft/world/entity/player/Player.noPhysics : Z", opcode = Opcodes.GETFIELD, ordinal = 0))
    private static boolean blockVisionWhenInsideBlocks(boolean original, @Local(ordinal = 0) Player player) {
        return original && !IntangibilityEffect.isIntangible(player);
    }

}
