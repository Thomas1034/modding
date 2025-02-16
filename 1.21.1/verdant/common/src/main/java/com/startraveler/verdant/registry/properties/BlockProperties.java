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
package com.startraveler.verdant.registry.properties;


import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

public class BlockProperties {
    public static final BlockBehaviour.Properties VERDANT_ROOTS = BlockBehaviour.Properties.of()
            .mapColor(MapColor.GRASS).randomTicks().sound(SoundType.GRASS).strength(0.75F);
    public static final BlockBehaviour.Properties VERDANT_GRASS = BlockBehaviour.Properties.of()
            .mapColor(MapColor.GRASS).randomTicks().sound(SoundType.GRASS).strength(0.8F);
    public static final BlockBehaviour.Properties VERDANT_HEARTWOOD_PLANKS = verdantHeartwoodBase();
    public static final BlockBehaviour.Properties VERDANT_HEARTWOOD_SLAB = verdantHeartwoodBase();
    public static final BlockBehaviour.Properties VERDANT_HEARTWOOD_SIGN = verdantHeartwoodBase().forceSolidOn()
            .noCollission().strength(2.0F);
    public static final BlockBehaviour.Properties VERDANT_HEARTWOOD_LOGS = verdantHeartwoodBase();
    public static final BlockBehaviour.Properties VERDANT_HEARTWOOD_PRESSURE_PLATE = verdantHeartwoodBase()
            .forceSolidOn().noCollission().strength(2.0F).pushReaction(PushReaction.DESTROY);
    public static final BlockBehaviour.Properties VERDANT_HEARTWOOD_STAIRS = verdantHeartwoodBase();
    public static final BlockBehaviour.Properties VERDANT_HEARTWOOD_FENCE = verdantHeartwoodBase().forceSolidOn();
    public static final BlockBehaviour.Properties VERDANT_HEARTWOOD_FENCE_GATE = verdantHeartwoodBase().forceSolidOn();
    public static final BlockBehaviour.Properties VERDANT_HEARTWOOD_TRAPDOOR = verdantHeartwoodBase().noOcclusion();
    public static final BlockBehaviour.Properties VERDANT_HEARTWOOD_DOOR = verdantHeartwoodBase().strength(4.5F)
            .noOcclusion().pushReaction(PushReaction.DESTROY);
    public static final BlockBehaviour.Properties VERDANT_HEARTWOOD_WALL_HANGING_SIGN = verdantHeartwoodBase()
            .forceSolidOn().noCollission().strength(1.0F);
    public static final BlockBehaviour.Properties VERDANT_HEARTWOOD_WALL_SIGN = verdantHeartwoodBase().forceSolidOn()
            .noCollission().strength(1.0F);
    public static final BlockBehaviour.Properties VERDANT_HEARTWOOD_HANGING_SIGN = verdantHeartwoodBase().forceSolidOn()
            .noCollission().strength(1.0F);

    private static BlockBehaviour.Properties verdantHeartwoodBase() {
        return BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GREEN).instrument(NoteBlockInstrument.BASS)
                .strength(4.0F, 6.0F).sound(SoundType.WOOD).ignitedByLava();
    }
}
