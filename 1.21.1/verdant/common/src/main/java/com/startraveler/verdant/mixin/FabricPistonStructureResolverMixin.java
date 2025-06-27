package com.startraveler.verdant.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.startraveler.verdant.registry.BlockRegistry;
import com.startraveler.verdant.util.VerdantTags;
import net.minecraft.world.level.block.piston.PistonStructureResolver;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(PistonStructureResolver.class)
public class FabricPistonStructureResolverMixin {

    @ModifyReturnValue(method = "isSticky(Lnet/minecraft/world/level/block/state/BlockState;)Z", at = @At("RETURN"))
    private static boolean addNewStickyBlocks(boolean original, BlockState state) {
        return original || state.is(BlockRegistry.SAP_BLOCK.get()) || state.is(BlockRegistry.VERDANT_RESIN_BLOCK.get()) || state.is(
                VerdantTags.Blocks.VERDANT_RESIN_BLOCKS);
    }

    @ModifyReturnValue(method = "canStickToEachOther(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/block/state/BlockState;)Z", at = @At("RETURN"))
    private static boolean controlNewStickinessBehavior(boolean original, BlockState state1, BlockState state2) {
        if (state1.is(BlockRegistry.SAP_BLOCK.get())) {
            return true;
        }
        if (state2.is(BlockRegistry.SAP_BLOCK.get())) {
            return true;
        }
        if (state1.is(VerdantTags.Blocks.VERDANT_RESIN_BLOCKS) && isSticky(state2)) {
            return original;
        }
        if (state2.is(VerdantTags.Blocks.VERDANT_RESIN_BLOCKS) && isSticky(state1)) {
            return original;
        }

        return original;
    }

    @Shadow
    private static boolean isSticky(BlockState state2) {
        throw new AssertionError("Shadowed method");
    }
}
