package com.thomas.verdant.block;

import com.thomas.verdant.util.blocktransformer.BlockTransformer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.state.BlockState;

public interface Converter {

    // Performs block conversion.
    // Must be called server-side.
    // Returns true if a change was made to the world (i.e. conversion succeeded)
    default boolean convert(ServerLevel level, BlockPos pos) {
        return this.convert(level.getBlockState(pos), level, pos);
    }

    default boolean convert(BlockState state, ServerLevel level, BlockPos pos) {
        // Retrieves the registry for block transformers.
        Registry<BlockTransformer> transformers = level.registryAccess().registryOrThrow(BlockTransformer.KEY);
        // Selects which converter to use, depending on whether there is access to water.
        BlockTransformer converter = transformers.get(this.getTransformer());
        // Gets the result of conversion. This could be null.
        BlockState newState = converter != null ? converter.get(state, level) : null;
        // Check if the result is either unchanged or null.
        // Block states are cached, allowing slight efficiency to avoid setting a redundant state.
        if (state != newState && newState != null) {
            // Set the block iff it changed and is not null.
            level.setBlockAndUpdate(pos, newState);
            // Return true since conversion succeeded.
            return true;
        }
        // Return false since no change was made to the world.
        return false;
    }

    abstract ResourceLocation getTransformer();

}
