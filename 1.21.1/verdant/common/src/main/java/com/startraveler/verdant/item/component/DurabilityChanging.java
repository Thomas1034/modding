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

public record DurabilityChanging(int perTick, int tickEvery, boolean randomize) {
    public static final DurabilityChanging HEARTWOOD_ARMOR = new DurabilityChanging(1, 200, false);
    public static final DurabilityChanging HEARTWOOD_TOOLS = new DurabilityChanging(1, 600, false);
    public static final DurabilityChanging IMBUED_HEARTWOOD_ARMOR = new DurabilityChanging(1, 40, false);
    public static final DurabilityChanging IMBUED_HEARTWOOD_TOOLS = new DurabilityChanging(1, 50, false);

    public static final Codec<DurabilityChanging> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.INT.fieldOf("perTick").forGetter(DurabilityChanging::perTick),
            Codec.INT.fieldOf("tickEvery").forGetter(DurabilityChanging::tickEvery),
            Codec.BOOL.fieldOf("randomize").forGetter(DurabilityChanging::randomize)
    ).apply(instance, DurabilityChanging::new));
}

