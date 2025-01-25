package com.thomas.verdant.mixin;

import com.thomas.verdant.item.component.DurabilityChanging;
import com.thomas.verdant.registry.DataComponentRegistry;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemStack.class)
public class ItemStackDurabilityChangingMixin {

    @Inject(method = "inventoryTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/Item;inventoryTick(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/level/Level;Lnet/minecraft/world/entity/Entity;IZ)V", shift = At.Shift.AFTER))
    public void verdant$changeDurabilityOnTick(Level level, Entity entity, int inventorySlot, boolean isCurrentItem, CallbackInfo ci) {
        this.verdant$updateDurability(((ItemStack) (Object) this), level, entity, inventorySlot, isCurrentItem);
    }


    @Unique
    public void verdant$updateDurability(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        DurabilityChanging delta = stack.get(DataComponentRegistry.DURABILITY_CHANGING.get());
        if (delta != null && stack.isDamageableItem() && entity instanceof ServerPlayer serverPlayer && level instanceof ServerLevel serverLevel) {
            int tickEvery = delta.tickEvery();

            long time = level.getGameTime();

            if (delta.randomize()) {
                time = level.random.nextInt(tickEvery);
            }

            if (time % tickEvery == 0) {

                stack.hurtAndBreak(
                        delta.perTick(), serverLevel, serverPlayer, (x) -> {
                        }
                );
            }
        }


    }
}
