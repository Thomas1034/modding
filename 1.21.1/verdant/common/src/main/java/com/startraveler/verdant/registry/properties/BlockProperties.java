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
import net.minecraft.world.level.material.MapColor;

public class BlockProperties {
    public static final BlockBehaviour.Properties VERDANT_ROOTS = BlockBehaviour.Properties.of()
            .mapColor(MapColor.GRASS).randomTicks().sound(SoundType.GRASS).strength(0.75F);
    public static final BlockBehaviour.Properties VERDANT_GRASS = BlockBehaviour.Properties.of()
            .mapColor(MapColor.GRASS).randomTicks().sound(SoundType.GRASS).strength(0.8F);

}
