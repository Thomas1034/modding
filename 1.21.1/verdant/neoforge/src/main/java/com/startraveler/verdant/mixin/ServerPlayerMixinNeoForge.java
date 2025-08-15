package com.startraveler.verdant.mixin;

import com.startraveler.verdant.registry.TriggerRegistry;
import com.startraveler.verdant.util.ServerPlayerMixin;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Debug(export = true)
@Mixin(targets = "net/minecraft/server/level/ServerPlayer$2")
public abstract class ServerPlayerMixinNeoForge implements ServerPlayerMixin {

    @Final
    @Shadow
    ServerPlayer this$0;

    @Override
    @Inject(method = "slotChanged", at = @At(value = "INVOKE", target = "Lnet/minecraft/advancements/critereon/InventoryChangeTrigger;trigger(Lnet/minecraft/server/level/ServerPlayer;Lnet/minecraft/world/entity/player/Inventory;Lnet/minecraft/world/item/ItemStack;)V", shift = At.Shift.AFTER))
    public void addInventoryChangeTrigger(AbstractContainerMenu menu, int slot, ItemStack stack, CallbackInfo ci) {
        TriggerRegistry.INVENTORY_CHANGE_ITEM_COUNT_TRIGGER.get()
                .trigger(this.this$0, this.this$0.getInventory(), stack);
    }
}
