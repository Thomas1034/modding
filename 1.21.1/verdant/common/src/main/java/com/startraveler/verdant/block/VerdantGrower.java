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
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.state.BlockState;

public interface VerdantGrower extends Converter, Eroder {

    default boolean erodeOrGrow(ServerLevel level, BlockPos pos, boolean isWet) {
        if (level.isLoaded(pos)) {
            return this.erodeOrGrow(level.getBlockState(pos), level, pos, isWet);
        }
        return false;
    }

    default boolean erodeOrGrow(BlockState state, ServerLevel level, BlockPos pos, boolean isWet) {
        if (level.isLoaded(pos)) {
            return this.erode(state, level, pos, isWet) || this.convert(state, level, pos);
        }
        return false;
    }

    @Override
    default ResourceLocation getTransformer() {
        return BlockTransformerRegistry.VERDANT_ROOTS;
    }
}

