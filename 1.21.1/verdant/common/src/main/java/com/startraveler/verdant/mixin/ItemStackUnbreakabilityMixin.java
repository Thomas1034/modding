package com.startraveler.verdant.mixin;

import com.startraveler.verdant.util.VerdantTags;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.function.Consumer;

@Debug(export=true)
@Mixin(ItemStack.class)
public class ItemStackUnbreakabilityMixin {

    @Inject(method = "applyDamage", at = @At(value = "HEAD"), cancellable = true)
    private void verdant$applyUnbreakabilityEffects(int damage, ServerPlayer player, Consumer<Item> onBreak, CallbackInfo ci) {
        System.out.println("Running! Player is " + player + " and damage is " + damage);
        if (player != null) {
            System.out.println("The player has " + List.copyOf(player.getActiveEffects()));
            if (player.getActiveEffects()
                    .stream()
                    .anyMatch(instance -> instance.getEffect().is(VerdantTags.MobEffects.UNBREAKABLE))) {
                System.out.println("Should cancel!");
                if (ci.isCancellable()) {
                    System.out.println("Canceling!");
                    ci.cancel();
                }
            }
        }
    }

}
