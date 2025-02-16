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

import com.startraveler.verdant.woodset.WoodSet;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;

import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class FuelsRegistry {

    private final BiConsumer<ItemLike, Integer> registrar;

    private FuelsRegistry(BiConsumer<ItemLike, Integer> registrar) {
        this.registrar = registrar;
    }

    public static void init(BiConsumer<ItemLike, Integer> registrar) {
        // FireBlock
        FuelsRegistry fuels = new FuelsRegistry(registrar);

        fuels.registerFuel(BlockRegistry.FISH_TRAP_BLOCK, WoodSet.BurnTimes.PLANKS);
        fuels.registerFuel(BlockRegistry.BUSH, WoodSet.BurnTimes.STICK);
        fuels.registerFuel(BlockRegistry.THORN_BUSH, WoodSet.BurnTimes.STICK);
        fuels.registerFuel(BlockRegistry.STRANGLER_LEAVES, WoodSet.BurnTimes.STICK);
        fuels.registerFuel(BlockRegistry.WILTED_STRANGLER_LEAVES, WoodSet.BurnTimes.STICK);
        fuels.registerFuel(BlockRegistry.THORNY_STRANGLER_LEAVES, WoodSet.BurnTimes.STICK);
        fuels.registerFuel(BlockRegistry.STRANGLER_VINE, WoodSet.BurnTimes.SIGN);
        fuels.registerFuel(BlockRegistry.LEAFY_STRANGLER_VINE, WoodSet.BurnTimes.SIGN);
        fuels.registerFuel(BlockRegistry.FRAME_BLOCK, WoodSet.BurnTimes.DOOR);

    }

    public void registerFuel(ItemLike block, int burnTime) {
        // Constants.LOG.warn("Setting itemLike {} to be fuel with {}", block, burnTime);
        this.registrar.accept(block, burnTime);
    }

    public void registerFuel(Supplier<Block> block, int burnTime) {
        this.registerFuel(block.get(), burnTime);
    }

}

