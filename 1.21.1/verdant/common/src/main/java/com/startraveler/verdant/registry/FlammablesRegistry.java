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

import net.minecraft.world.level.block.Block;
import org.apache.commons.lang3.function.TriConsumer;

import java.util.function.Supplier;

public class FlammablesRegistry {

    private final TriConsumer<Block, Integer, Integer> registrar;

    private FlammablesRegistry(TriConsumer<Block, Integer, Integer> registrar) {
        this.registrar = registrar;
    }

    public static void init(TriConsumer<Block, Integer, Integer> registrar) {
        // FireBlock
        FlammablesRegistry flammables = new FlammablesRegistry(registrar);

        flammables.registerFlammability(BlockRegistry.WILTED_STRANGLER_LEAVES, 60, 60);
        flammables.registerFlammability(BlockRegistry.THORNY_STRANGLER_LEAVES, 60, 60);
        flammables.registerFlammability(BlockRegistry.POISON_STRANGLER_LEAVES, 60, 60);
        flammables.registerFlammability(BlockRegistry.STRANGLER_LEAVES, 60, 60);
        flammables.registerFlammability(BlockRegistry.STRANGLER_VINE, 60, 60);
        flammables.registerFlammability(BlockRegistry.LEAFY_STRANGLER_VINE, 60, 60);
        flammables.registerFlammability(BlockRegistry.STRANGLER_TENDRIL, 60, 60);
        flammables.registerFlammability(BlockRegistry.ROPE, 60, 60);
        flammables.registerFlammability(BlockRegistry.ROTTEN_WOOD, 60, 60);
        flammables.registerFlammability(BlockRegistry.STINKING_BLOSSOM, 60, 100);
        flammables.registerFlammability(BlockRegistry.BLEEDING_HEART, 60, 100);
        flammables.registerFlammability(BlockRegistry.TIGER_LILY, 60, 100);
        flammables.registerFlammability(BlockRegistry.RUE, 60, 100);
        flammables.registerFlammability(BlockRegistry.THORN_BUSH, 60, 100);
        flammables.registerFlammability(BlockRegistry.BUSH, 60, 100);
        flammables.registerFlammability(BlockRegistry.WILD_COFFEE, 60, 100);
        flammables.registerFlammability(BlockRegistry.COFFEE_CROP, 60, 100);
        flammables.registerFlammability(BlockRegistry.FRAME_BLOCK, 60, 100);
        flammables.registerFlammability(BlockRegistry.CHARRED_FRAME_BLOCK, 60, 100);
        flammables.registerFlammability(BlockRegistry.PAPER_FRAME, 60, 100);
    }

    public void registerFlammability(Block block, int flammability, int spreadSpeed) {
        // Constants.LOG.warn("Setting block {} to be flammable with {} and {}", block, flammability, spreadSpeed);
        this.registrar.accept(block, flammability, spreadSpeed);
    }

    public void registerFlammability(Supplier<Block> block, int flammability, int spreadSpeed) {
        this.registerFlammability(block.get(), flammability, spreadSpeed);
    }

}

