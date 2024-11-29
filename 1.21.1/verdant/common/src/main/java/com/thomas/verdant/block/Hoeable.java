package com.thomas.verdant.block;

import com.thomas.verdant.registry.BlockTransformerRegistry;
import com.thomas.verdant.util.blocktransformer.BlockTransformer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public interface Hoeable {

    default BlockState hoe(BlockState state, ServerLevel level, BlockPos pos, ItemStack stack) {
        // Retrieves the registry for hoeing.
        Registry<BlockTransformer> transformers = level.registryAccess().lookupOrThrow(BlockTransformer.KEY);
        BlockTransformer hoeing = transformers.get(BlockTransformerRegistry.HOEING).orElseThrow().value();

        // Transforms the state and returns it
        return hoeing.get(state, level);
    }

    @SuppressWarnings("unused")
    default boolean isHoeable(BlockState state, ServerLevel level, BlockPos pos, ItemStack stack) {
        return (this instanceof Block thisBlock) && state.is(thisBlock);
    }

}
