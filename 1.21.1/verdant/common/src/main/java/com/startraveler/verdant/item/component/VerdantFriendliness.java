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
package com.startraveler.verdant.item.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

// Data Component for storing how much a worn/held item contributes to verdant friendliness.
public record VerdantFriendliness(float sway) {
    public static final VerdantFriendliness HEARTWOOD_ARMOR = new VerdantFriendliness(0.1f);
    public static final VerdantFriendliness HEARTWOOD_HORSE_ARMOR = new VerdantFriendliness(0.7f);
    public static final VerdantFriendliness IMBUED_HEARTWOOD_ARMOR = new VerdantFriendliness(0.3f);
    public static final VerdantFriendliness IMBUED_HEARTWOOD_HORSE_ARMOR = new VerdantFriendliness(1.0f);


    public static final Codec<VerdantFriendliness> CODEC = RecordCodecBuilder.create(instance -> instance.group(Codec.FLOAT.fieldOf(
            "sway").forGetter(VerdantFriendliness::sway)).apply(instance, VerdantFriendliness::new));
}

