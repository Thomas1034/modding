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
import net.minecraft.core.dispenser.BoatDispenseItemBehavior;
import net.minecraft.core.dispenser.ProjectileDispenseBehavior;
import net.minecraft.world.level.block.DispenserBlock;

public class DispenserBehaviors {

    public static void init() {

        DispenserBlock.registerBehavior(
                ItemRegistry.ROPE_COIL.get(),
                new ProjectileDispenseBehavior(ItemRegistry.ROPE_COIL.get())
        );
    }

    public static void woodSet(WoodSet woodSet) {
        DispenserBlock.registerBehavior(
                woodSet.getBoatItem().get(),
                new BoatDispenseItemBehavior(woodSet.getBoat().get())
        );
        DispenserBlock.registerBehavior(
                woodSet.getChestBoatItem().get(),
                new BoatDispenseItemBehavior(woodSet.getChestBoat().get())
        );
    }

}

