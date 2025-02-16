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
package com.startraveler.verdant.block.loot;

import com.startraveler.verdant.Constants;
import net.minecraft.resources.ResourceLocation;

public class LootLocations {

    public static final ResourceLocation CASSAVA_ROOTED_DIRT_POP = block("cassava_rooted_dirt_pop");
    public static final ResourceLocation BITTER_CASSAVA_ROOTED_DIRT_POP = block("bitter_cassava_rooted_dirt_pop");
    public static final ResourceLocation DIRT_COAL_ORE_POP = block("dirt_coal_ore_pop");
    public static final ResourceLocation DIRT_COPPER_ORE_POP = block("dirt_copper_ore_pop");
    public static final ResourceLocation DIRT_IRON_ORE_POP = block("dirt_iron_ore_pop");
    public static final ResourceLocation DIRT_GOLD_ORE_POP = block("dirt_gold_ore_pop");
    public static final ResourceLocation DIRT_LAPIS_ORE_POP = block("dirt_lapis_ore_pop");
    public static final ResourceLocation DIRT_REDSTONE_ORE_POP = block("dirt_redstone_ore_pop");
    public static final ResourceLocation DIRT_EMERALD_ORE_POP = block("dirt_emerald_ore_pop");
    public static final ResourceLocation DIRT_DIAMOND_ORE_POP = block("dirt_diamond_ore_pop");

    public static ResourceLocation block(String location) {
        return table("blocks/" + location);
    }

    public static ResourceLocation table(String location) {
        return ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, location);
    }

}

