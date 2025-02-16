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

import com.startraveler.verdant.util.blocktransformer.BlockTransformer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public interface Converter {

    // Performs block conversion.
    // Must be called server-side.
    // Returns true if a change was made to the world (i.e. conversion succeeded)
    default boolean convert(ServerLevel level, BlockPos pos) {
        return this.convert(level.getBlockState(pos), level, pos);
    }

    // Returns true if block conversion is possible, false otherwise.
    default boolean canConvert(BlockState state, Level level) {
        RegistryAccess access = level.registryAccess();
        // Retrieves the registry for block transformers.
        Registry<BlockTransformer> transformers = access.lookupOrThrow(BlockTransformer.KEY);
        BlockTransformer converter = transformers.get(this.getTransformer()).orElseThrow().value();
        return converter.isValidInput(access, state);
    }

    default boolean convert(BlockState state, ServerLevel level, BlockPos pos) {
        RegistryAccess access = level.registryAccess();
        // Retrieves the registry for block transformers.
        Registry<BlockTransformer> transformers = access.lookupOrThrow(BlockTransformer.KEY);
        BlockTransformer converter = transformers.get(this.getTransformer()).orElseThrow().value();
        // Gets the result of conversion. This could be null.
        BlockState newState = converter.get(state, access, level.random);
        // Check if the result is either unchanged or null.
        // Block states are cached, allowing slight efficiency to avoid setting a redundant state.
        if (state != newState && newState != null) {
            // Set the block iff it changed and is not null.
            level.setBlockAndUpdate(pos, newState);
            // Schedule a tick.
            level.scheduleTick(pos, newState.getBlock(), 1);
            // Return true since conversion succeeded.
            return true;
        }
        // Return false since no change was made to the world.
        return false;
    }

    abstract ResourceLocation getTransformer();

}

