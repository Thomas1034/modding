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
package com.startraveler.verdant.util;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Direction;
import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum OptionalDirection implements StringRepresentable {

    EMPTY("empty"), UP("up", Direction.UP), DOWN("down", Direction.DOWN), NORTH("north", Direction.NORTH), EAST("east", Direction.EAST), SOUTH("south", Direction.SOUTH), WEST("west", Direction.WEST);

    public static final Codec<OptionalDirection> CODEC = RecordCodecBuilder.create(instance -> instance.group(Codec.STRING.fieldOf("direction").forGetter(OptionalDirection::getSerializedName)).apply(instance, OptionalDirection::valueOf));
    private static final BiMap<Direction, OptionalDirection> MAP = HashBiMap.create();

    static {
        MAP.putAll(Arrays.stream(OptionalDirection.values()).collect(Collectors.toMap(OptionalDirection::direction, Function.identity())));
    }

    private final Direction direction;
    private final String name;

    OptionalDirection(String name, Direction d) {
        this.name = name;
        this.direction = d;
    }

    OptionalDirection(String name) {
        this.name = name;
        this.direction = null;
    }

    public static OptionalDirection of(Direction d) {
        return MAP.get(d);
    }

    public Direction direction() {
        return this.direction;
    }

    @Override
    public @NotNull String getSerializedName() {
        return this.name;
    }
}

