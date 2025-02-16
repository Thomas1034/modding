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

import com.startraveler.verdant.Constants;
import com.startraveler.verdant.item.component.DurabilityChanging;
import com.startraveler.verdant.item.component.RopeCoilData;
import com.startraveler.verdant.item.component.VerdantFriendliness;
import com.startraveler.verdant.registration.RegistrationProvider;
import com.startraveler.verdant.registration.RegistryObject;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;

public class DataComponentRegistry {

    public static final RegistrationProvider<DataComponentType<?>> COMPONENTS = RegistrationProvider.get(
            Registries.DATA_COMPONENT_TYPE,
            Constants.MOD_ID
    );

    public static final RegistryObject<DataComponentType<?>, DataComponentType<RopeCoilData>> ROPE_COIL = COMPONENTS.register("rope_coil",
            () -> DataComponentType.<RopeCoilData>builder().persistent(RopeCoilData.CODEC).build()
    );

    public static final RegistryObject<DataComponentType<?>, DataComponentType<DurabilityChanging>> DURABILITY_CHANGING = COMPONENTS.register(
            "durability_changing",
            () -> DataComponentType.<DurabilityChanging>builder().persistent(DurabilityChanging.CODEC).build()
    );

    public static final RegistryObject<DataComponentType<?>, DataComponentType<VerdantFriendliness>> VERDANT_FRIENDLINESS = COMPONENTS.register(
            "verdant_friendliness",
            () -> DataComponentType.<VerdantFriendliness>builder().persistent(VerdantFriendliness.CODEC).build()
    );


    public static void init() {
    }
}

