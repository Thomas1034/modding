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
import net.minecraft.client.renderer.item.properties.select.SelectItemModelProperty;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public record RopeHangingBlockProperty() implements SelectItemModelProperty<RopeCoilData.LanternOptions> {

    // The object to register that contains the relevant codecs
    public static final SelectItemModelProperty.Type<RopeHangingBlockProperty, RopeCoilData.LanternOptions> TYPE = SelectItemModelProperty.Type.create(
            // The map codec for this property
            MapCodec.unit(new RopeHangingBlockProperty()),
            // The codec for the object being selected
            // Used to serialize the case entries ("when": <property value>)
            RopeCoilData.LanternOptions.CODEC
    );

    @Override
    public RopeCoilData.LanternOptions get(ItemStack itemStack, ClientLevel clientLevel, LivingEntity livingEntity, int i, ItemDisplayContext itemDisplayContext) {
        RopeCoilData d = itemStack.get(DataComponentRegistry.ROPE_COIL.get());
        return d == null ? RopeCoilData.LanternOptions.NONE : d.lantern();
    }

    @Override
    public Type<? extends SelectItemModelProperty<RopeCoilData.LanternOptions>, RopeCoilData.LanternOptions> type() {
        return TYPE;
    }
}

