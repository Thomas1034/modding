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

import com.startraveler.rootbound.woodset.WoodSet;
import com.startraveler.verdant.Constants;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;

import java.util.HashSet;
import java.util.Set;

public class WoodSets {

    public static Set<WoodSet> WOOD_SETS = new HashSet<>();

    public static final WoodSet STRANGLER = register(new WoodSet(
            Constants.MOD_ID,
            "strangler",
            () -> BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_LIGHT_GRAY)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F, 3.0F)
                    .sound(SoundType.WOOD)
                    .ignitedByLava(),
            1,
            true
    ));

    public static final WoodSet DEAD = register(new WoodSet(
            Constants.MOD_ID,
            "dead",
            () -> BlockBehaviour.Properties.of()
                    .mapColor(MapColor.STONE)
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .strength(1.0F, 1.5F)
                    .sound(SoundType.WOOD)
                    .ignitedByLava(),
            1,
            true
    ));

    public static final WoodSet HEARTWOOD = register(new WoodSet(
            Constants.MOD_ID,
            "heartwood",
            () -> BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_GREEN)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(4.0F, 6.0F)
                    .sound(SoundType.WOOD)
                    .ignitedByLava(),
            2,
            true
    ));

    public static WoodSet register(WoodSet woodSet) {
        WOOD_SETS.add(woodSet);
        return woodSet;
    }

    public static void init() {

    }
}

