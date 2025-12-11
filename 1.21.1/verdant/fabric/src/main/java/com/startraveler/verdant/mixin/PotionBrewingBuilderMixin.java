package com.startraveler.verdant.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.world.item.PotionItem;
import net.minecraft.world.item.alchemy.PotionBrewing;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;

@Mixin(PotionBrewing.Builder.class)
public class PotionBrewingBuilderMixin {

    @WrapOperation(method="expectPotion", constant=@Constant(classValue = PotionItem.class))
    private static boolean neuterPotionChecker(Object object, Operation<Boolean> original) {
        return true;
    }
}
