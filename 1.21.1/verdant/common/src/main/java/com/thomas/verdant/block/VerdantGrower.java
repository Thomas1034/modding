package com.thomas.verdant.block;

import com.thomas.verdant.registry.BlockTransformerRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.state.BlockState;

public interface VerdantGrower extends Converter, Eroder {

    default boolean erodeOrGrow(ServerLevel level, BlockPos pos, boolean isWet) {
        return this.erodeOrGrow(level.getBlockState(pos), level, pos, isWet);
    }

    default boolean erodeOrGrow(BlockState state, ServerLevel level, BlockPos pos, boolean isWet) {
        return this.erode(state, level, pos, isWet) || this.convert(state, level, pos);
    }

    @Override
    default ResourceLocation getTransformer() {
        return BlockTransformerRegistry.VERDANT_ROOTS;
    }
}
