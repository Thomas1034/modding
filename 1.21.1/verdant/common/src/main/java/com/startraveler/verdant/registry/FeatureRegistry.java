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
import com.startraveler.verdant.feature.custom.FixedSeagrassFeature;
import com.startraveler.verdant.feature.custom.StranglerVineFeature;
import com.startraveler.verdant.registration.RegistrationProvider;
import com.startraveler.verdant.registration.RegistryObject;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.ProbabilityFeatureConfiguration;

public class FeatureRegistry {

    public static final RegistrationProvider<Feature<?>> FEATURES = RegistrationProvider.get(
            Registries.FEATURE,
            Constants.MOD_ID
    );

    public static final RegistryObject<Feature<?>, Feature<?>> FIXED_SEAGRASS = FEATURES.register(
            "fixed_seagrass",
            () -> new FixedSeagrassFeature(ProbabilityFeatureConfiguration.CODEC)
    );
    public static final RegistryObject<Feature<?>, Feature<?>> STRANGLER_VINES = FEATURES.register(
            "strangler_vines",
            () -> new StranglerVineFeature(NoneFeatureConfiguration.CODEC)
    );


    public static void init() {

    }

}


