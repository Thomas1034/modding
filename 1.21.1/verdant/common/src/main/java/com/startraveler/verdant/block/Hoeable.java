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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public interface Hoeable {

    default BlockState hoe(BlockState state, ServerLevel level, BlockPos pos, ItemStack stack) {
        // Retrieves the registry for hoeing.
        Registry<BlockTransformer> transformers = level.registryAccess().lookupOrThrow(BlockTransformer.KEY);
        BlockTransformer hoeing = transformers.get(BlockTransformerRegistry.HOEING).orElseThrow().value();

        // Transforms the state and returns it
        return hoeing.get(state, level.registryAccess(), level.random);
    }

    @SuppressWarnings("unused")
    default boolean isHoeable(BlockState state, ServerLevel level, BlockPos pos, ItemStack stack) {
        return (this instanceof Block thisBlock) && state.is(thisBlock);
    }

}

