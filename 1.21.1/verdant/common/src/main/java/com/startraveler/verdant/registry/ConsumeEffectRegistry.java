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
import com.startraveler.verdant.item.custom.DiminishEffectConsumeEffect;
import com.startraveler.verdant.registration.RegistrationProvider;
import com.startraveler.verdant.registration.RegistryObject;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.consume_effects.ConsumeEffect;

public class ConsumeEffectRegistry {
    public static final RegistrationProvider<ConsumeEffect.Type<?>> EFFECTS = RegistrationProvider.get(
            Registries.CONSUME_EFFECT_TYPE,
            Constants.MOD_ID
    );

    public static final RegistryObject<ConsumeEffect.Type<?>, ConsumeEffect.Type<DiminishEffectConsumeEffect>> DIMINISH_EFFECT = EFFECTS.register(
            "diminish_effect",
            () -> new ConsumeEffect.Type<>(DiminishEffectConsumeEffect.CODEC, DiminishEffectConsumeEffect.STREAM_CODEC)
    );

    public static void init() {
    }
}

