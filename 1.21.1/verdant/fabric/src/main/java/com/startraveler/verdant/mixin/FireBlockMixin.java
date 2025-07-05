package com.startraveler.verdant.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.startraveler.verdant.block.custom.BombPileBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FireBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(FireBlock.class)
public class FireBlockMixin {


    @WrapOperation(method = "checkBurnOut", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;getBlock()Lnet/minecraft/world/level/block/Block;"))
    public Block alsoPrimeBombPileBlocks(BlockState instance, Operation<Block> original, @Local(argsOnly = true) Level level, @Local(argsOnly = true) BlockPos pos) {
        Block block = original.call(instance);
        if (block instanceof BombPileBlock bpb) {
            bpb.onCaughtFire(instance, level, pos, null, null);
        }
        return block;
    }
}
