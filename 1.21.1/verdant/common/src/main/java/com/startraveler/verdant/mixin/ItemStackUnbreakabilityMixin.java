package com.startraveler.verdant.mixin;

import com.startraveler.verdant.util.VerdantTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Coerce;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Consumer;

@Debug(export = true)
@Mixin(ItemStack.class)
public class ItemStackUnbreakabilityMixin {

    @Inject(method = "applyDamage*", at = @At(value = "HEAD"), cancellable = true)
    private void verdant$applyUnbreakabilityEffects(int damage, @Coerce LivingEntity player, Consumer<Item> onBreak, CallbackInfo ci) {
        if (player != null) {
            if (player.getActiveEffects()
                    .stream()
                    .anyMatch(instance -> instance.getEffect().is(VerdantTags.MobEffects.UNBREAKABLE))) {
                if (ci.isCancellable()) {
                    ci.cancel();
                }
            }
        }
    }
}
