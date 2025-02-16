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

import com.startraveler.verdant.util.VerdantTags;
import net.minecraft.world.item.ToolMaterial;

public class ToolMaterialRegistry {

    public static ToolMaterial HEARTWOOD = new ToolMaterial(
            VerdantTags.Blocks.INCORRECT_FOR_HEARTWOOD_TOOL,
            59,
            6.0F,
            2.0F,
            5,
            VerdantTags.Items.HEARTWOOD_TOOL_MATERIALS
    );

    public static ToolMaterial IMBUED_HEARTWOOD = new ToolMaterial(
            VerdantTags.Blocks.INCORRECT_FOR_HEARTWOOD_TOOL,
            131,
            7.0F,
            2.5F,
            3,
            VerdantTags.Items.HEARTWOOD_TOOL_MATERIALS
    );

}

