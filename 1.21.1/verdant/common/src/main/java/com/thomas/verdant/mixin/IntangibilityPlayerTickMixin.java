package com.thomas.verdant.mixin;

import com.thomas.verdant.effect.IntangibilityEffect;
import net.minecraft.world.entity.player.Player;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public class IntangibilityPlayerTickMixin {
    @Inject(method = "tick", at = @At(value = "FIELD", target = "net/minecraft/world/entity/player/Player.takeXpDelay : I", opcode = Opcodes.GETFIELD, ordinal = 0))
    public void verdant$setNoPhysicsForIntangibility(CallbackInfo ci) {

        if (IntangibilityEffect.isIntangible(((Player) (Object) this))) {
            boolean shouldBeIntangible = IntangibilityEffect.canGoThroughBlockBeneath((Player) (Object) (this));
            ((Player) (Object) this).noPhysics = shouldBeIntangible;
            ((Player) (Object) this).setOnGround(!shouldBeIntangible);
        }

    }
}
