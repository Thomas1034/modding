/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * If you modify this file, please include a notice stating the changes:
 * Example: "Modified by [Your Name] on [Date] - [Short Description of Changes]"
 */
package com.startraveler.verdant.block;

import com.startraveler.verdant.registry.BlockTransformerRegistry;
import com.startraveler.verdant.util.blocktransformer.BlockTransformer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.state.BlockState;

// Represents something capable of performing erosion.
// Erosion operates using a block transformer, and uses a different block transformer
// depending on if the erosion is happening with access to water.
// Implementers can decide what qualifies as access to water.
// When erosion occurs, the block at the given position is set to the value retrieved by the appropriate block transformer.
// If the block transformer returns null, no changes are made.
public interface Eroder {


    // Performs erosion.
    // Must be called server-side.
    // Returns true if a change was made to the world (i.e. erosion succeeded)
    default boolean erode(ServerLevel level, BlockPos pos, boolean isWet) {
        return this.erode(level.getBlockState(pos), level, pos, isWet);
    }

    default boolean erode(BlockState state, ServerLevel level, BlockPos pos, boolean isWet) {
        // Retrieves the registry for eroders.
        Registry<BlockTransformer> transformers = level.registryAccess().lookupOrThrow(BlockTransformer.KEY);
        // Selects which eroder to use, depending on whether there is access to water.
        BlockTransformer eroder = (transformers.get(isWet ? BlockTransformerRegistry.EROSION_WET : BlockTransformerRegistry.EROSION)).orElseThrow()
                .value();
        // Gets the result of erosion. This could be null.
        BlockState newState = eroder.get(state, level.registryAccess(), level.random);
        // Check if the result is either unchanged or null.
        // Block states are cached, allowing slight efficiency to avoid setting a redundant state.
        if (state != newState && newState != null) {
            // Set the block iff it changed and is not null.
            level.setBlockAndUpdate(pos, newState);
            // Schedule a tick.
            level.scheduleTick(pos, newState.getBlock(), 1);
            // Return true since erosion succeeded.
            return true;
        }
        // Return false since no change was made to the world.
        return false;
    }

}

