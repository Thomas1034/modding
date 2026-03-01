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
import net.minecraft.resources.Identifier;

public class FeatureSetRegistry {

    public static final Identifier ABOVE_GROUND = set("above_ground");
    public static final Identifier HANGING = set("hanging");
    public static final Identifier WATER = set("water");
    public static final Identifier ALWAYS = set("always");
    public static final Identifier BELOW_LOG = set("below_log");
    public static final Identifier MULCH = set("mulch");
    public static final Identifier LARGE_MULCH = set("large_mulch");

    private static Identifier set(String name) {
        return Identifier.fromNamespaceAndPath(Constants.MOD_ID, name);
    }

}

