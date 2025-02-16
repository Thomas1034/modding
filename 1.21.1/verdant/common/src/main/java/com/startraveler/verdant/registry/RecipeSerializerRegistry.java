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
import com.startraveler.verdant.recipe.RopeCoilUpgradeRecipe;
import com.startraveler.verdant.registration.RegistrationProvider;
import com.startraveler.verdant.registration.RegistryObject;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class RecipeSerializerRegistry {

    public static final RegistrationProvider<RecipeSerializer<?>> SERIALIZERS = RegistrationProvider.get(
            Registries.RECIPE_SERIALIZER,
            Constants.MOD_ID
    );

    public static final RegistryObject<RecipeSerializer<?>, CustomRecipe.Serializer<RopeCoilUpgradeRecipe>> ROPE_COIL_SERIALIZER = SERIALIZERS.register("rope_coil_upgrade",
            () -> new CustomRecipe.Serializer<>(RopeCoilUpgradeRecipe::new)
    );

    public static void init() {

    }

}

