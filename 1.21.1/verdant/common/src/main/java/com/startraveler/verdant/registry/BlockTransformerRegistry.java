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
import net.minecraft.resources.ResourceLocation;

public class BlockTransformerRegistry {

    public static final ResourceLocation EROSION = transformer("erosion");
    public static final ResourceLocation EROSION_WET = transformer("erosion_wet");
    public static final ResourceLocation HOEING = transformer("hoeing");
    public static final ResourceLocation VERDANT_ROOTS = transformer("verdant_roots");
    public static final ResourceLocation TOXIC_ASH = transformer("toxic_ash");

    private static ResourceLocation transformer(String name) {
        return ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, name);
    }


}

