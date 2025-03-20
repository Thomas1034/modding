package com.startraveler.verdant.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.startraveler.verdant.entity.custom.PoisonerEntity;
import com.startraveler.verdant.registry.MobEffectRegistry;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.monster.Witch;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.Potions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Witch.class)
public class WitchMixin {

    @ModifyExpressionValue(method = "aiStep", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;getUseDuration(Lnet/minecraft/world/entity/LivingEntity;)I", ordinal = 0))
    private int verdant$increaseDrinkSpeed(int original) {
        return ((Object) this) instanceof PoisonerEntity ? original / 4 : original;
    }

    @ModifyExpressionValue(method = "aiStep", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/monster/Witch;isEyeInFluid(Lnet/minecraft/tags/TagKey;)Z", ordinal = 0))
    private boolean verdant$waterBreathingIfChoking(boolean original) {
        return original || ((Witch) (Object) (this)).hasEffect(MobEffectRegistry.ASPHYXIATING.asHolder());
    }

    @ModifyExpressionValue(method = "aiStep", at = @At(value = "FIELD", target = "Lnet/minecraft/world/item/alchemy/Potions;HEALING:Lnet/minecraft/core/Holder;", ordinal = 0))
    private Holder<Potion> verdant$setHealingPotion(Holder<Potion> original) {
        return ((Object) this) instanceof PoisonerEntity ? Potions.STRONG_HEALING : original;
    }

    @ModifyExpressionValue(method = "aiStep", at = @At(value = "FIELD", target = "Lnet/minecraft/world/item/alchemy/Potions;SWIFTNESS:Lnet/minecraft/core/Holder;", ordinal = 0))
    private Holder<Potion> verdant$setSpeedPotion(Holder<Potion> original) {
        return ((Object) this) instanceof PoisonerEntity ? Potions.STRONG_SWIFTNESS : original;
    }
}
