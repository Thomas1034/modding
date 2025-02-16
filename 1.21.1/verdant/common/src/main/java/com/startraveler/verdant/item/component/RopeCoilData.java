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
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BellAttachType;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public record RopeCoilData(int length, boolean hasHook, int lightLevel, LanternOptions lantern) {
    public static final int MAX_LENGTH_FROM_CRAFTING = 32;

    public static final Codec<RopeCoilData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.INT.fieldOf("length").forGetter(RopeCoilData::length),
            Codec.BOOL.fieldOf("hasHook").forGetter(RopeCoilData::hasHook),
            Codec.INT.fieldOf("lightLevel").forGetter(RopeCoilData::lightLevel),
            LanternOptions.CODEC.fieldOf("lantern").forGetter(RopeCoilData::lantern)
    ).apply(instance, RopeCoilData::new));


    public enum LanternOptions implements StringRepresentable {

        NONE("none", Blocks.AIR.defaultBlockState()),
        LANTERN("lantern", Blocks.LANTERN.defaultBlockState().setValue(BlockStateProperties.HANGING, true)),
        SOUL_LANTERN(
                "soul_lantern",
                Blocks.SOUL_LANTERN.defaultBlockState().setValue(BlockStateProperties.HANGING, true)
        ),
        BELL(
                "bell",
                Blocks.BELL.defaultBlockState().setValue(BlockStateProperties.BELL_ATTACHMENT, BellAttachType.CEILING)
        );

        public static final StringRepresentableCodec<LanternOptions> CODEC = StringRepresentable.fromEnum(LanternOptions::values);
        private static final Map<String, LanternOptions> MAP = new HashMap<>();

        static {
            MAP.putAll(Arrays.stream(LanternOptions.values())
                    .collect(Collectors.toMap(LanternOptions::getSerializedName, Function.identity())));
        }

        public final String typeName;
        public final BlockState state;

        LanternOptions(String typeName, BlockState state) {
            this.state = state;
            this.typeName = typeName;
        }

        public static LanternOptions bySerializedName(String name) {
            return MAP.get(name);
        }

        @Override
        public String getSerializedName() {
            return this.typeName;
        }
    }
}

