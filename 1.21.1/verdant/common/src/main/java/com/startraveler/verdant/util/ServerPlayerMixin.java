package com.startraveler.verdant.util;

import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

public interface ServerPlayerMixin {
    void addInventoryChangeTrigger(AbstractContainerMenu menu, int slot, ItemStack stack, CallbackInfo ci);
}
