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
package com.startraveler.verdant.client.item;

import com.mojang.serialization.MapCodec;
import com.startraveler.verdant.item.component.RopeCoilData;
import com.startraveler.verdant.registry.DataComponentRegistry;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.properties.numeric.RangeSelectItemModelProperty;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public record RopeLengthProperty() implements RangeSelectItemModelProperty {

    public static final MapCodec<RopeLengthProperty> MAP_CODEC = MapCodec.unit(new RopeLengthProperty());

    @Override
    public float get(ItemStack stack, ClientLevel level, LivingEntity entity, int seed) {
        RopeCoilData d = stack.get(DataComponentRegistry.ROPE_COIL.get());
        return d == null ? 0 : ((float) d.length() / RopeCoilData.MAX_LENGTH_FROM_CRAFTING);
    }

    @Override
    public MapCodec<RopeLengthProperty> type() {
        return MAP_CODEC;
    }
}

