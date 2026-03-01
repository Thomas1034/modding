package com.startraveler.verdant.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.Holder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionBrewing;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Optional;

@Mixin(PotionBrewing.class)
public class FabricPotionBrewingMixin {

    @ModifyReturnValue(method = "mix", at = @At(value = "RETURN", ordinal = 1))
    private ItemStack a(ItemStack original, @Local(argsOnly = true, ordinal = 0) ItemStack potion, @Local(argsOnly = true, ordinal = 1) ItemStack potionItem, @Local Optional<Holder<Potion>> optional) {
        if (original == potionItem) {
            for (PotionBrewing.Mix<Item> mix : ((PotionBrewing) (Object) this).containerMixes) {
                if (potionItem.is(mix.from) && mix.ingredient.test(potion)) {
                    return new ItemStack(mix.to.value());
                }
            }
        }
        return original;
    }
}
