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
package com.startraveler.verdant.registry;

import com.startraveler.verdant.client.block.VerdantBlockTintSources;
import net.minecraft.client.color.block.BlockTintSource;
import net.minecraft.client.color.block.BlockTintSources;
import net.minecraft.world.level.block.Block;

import java.util.List;
import java.util.function.BiConsumer;

public class VerdantBlockColorRegistry {

    private final BiConsumer<List<BlockTintSource>, Block[]> registrar;

    private VerdantBlockColorRegistry(BiConsumer<List<BlockTintSource>, Block[]> registrar) {
        this.registrar = registrar;
    }

    public static void init(BiConsumer<List<BlockTintSource>, Block[]> registrar) {
        // FireBlock
        VerdantBlockColorRegistry registry = new VerdantBlockColorRegistry(registrar);

        registry.register(List.of(BlockTintSources.foliage()), BlockRegistry.MANGO_LEAVES.get());
        registry.register(
                List.of(BlockTintSources.foliage()),
                BlockRegistry.STRANGLER_LEAVES.get(),
                BlockRegistry.WILTED_STRANGLER_LEAVES.get(),
                BlockRegistry.THORNY_STRANGLER_LEAVES.get(),
                BlockRegistry.POISON_STRANGLER_LEAVES.get(),
                BlockRegistry.LEAFY_STRANGLER_VINE.get()
        );
        registry.register(
                List.of(BlockTintSources.doubleTallGrass()),
                BlockRegistry.TALL_BUSH.get(),
                BlockRegistry.TALL_THORN_BUSH.get()
        );
        registry.register(
                List.of(VerdantBlockTintSources.snowyGrassBlock()),
                BlockRegistry.VERDANT_GRASS_MUD.get(),
                BlockRegistry.VERDANT_GRASS_CLAY.get(),
                BlockRegistry.VERDANT_GRASS_DIRT.get(),
                BlockRegistry.VERDANT_GRASS_GRUS.get()
        );
        registry.register(
                List.of(BlockTintSources.grass()),
                BlockRegistry.BUSH.get(),
                BlockRegistry.POTTED_BUSH.get(),
                BlockRegistry.THORN_BUSH.get(),
                BlockRegistry.POTTED_THORN_BUSH.get(),
                BlockRegistry.SNAPLEAF.get()
        );
    }

    public void register(List<BlockTintSource> tintSources, Block... blocks) {
        this.registrar.accept(tintSources, blocks);
    }

}

