package com.startraveler.verdant.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.startraveler.verdant.block.custom.SapFireBlock;
import com.startraveler.verdant.registry.BlockRegistry;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(BaseFireBlock.class)
public class BaseFireBlockMixin {

    @ModifyReturnValue(method = "getState", at = @At("RETURN"))
    private static BlockState addSapFire(BlockState original, @Local BlockState blockstate) {
        return SapFireBlock.canSurviveOnBlock(blockstate) ? BlockRegistry.SAP_FIRE.get()
                .defaultBlockState() : original;
    }
}
