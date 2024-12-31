package com.thomas.verdant.item.component;

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

        NONE("none", Blocks.AIR.defaultBlockState(), 0, "empty_overlay"), LANTERN(
                "lantern",
                Blocks.LANTERN.defaultBlockState().setValue(BlockStateProperties.HANGING, true),
                0.2f,
                "lantern_overlay"
        ), SOUL_LANTERN(
                "soul_lantern",
                Blocks.SOUL_LANTERN.defaultBlockState().setValue(BlockStateProperties.HANGING, true),
                0.4f,
                "soul_lantern_overlay"
        ), BELL(
                "bell",
                Blocks.BELL.defaultBlockState().setValue(BlockStateProperties.BELL_ATTACHMENT, BellAttachType.CEILING),
                0.6f,
                "bell_overlay"
        );

        protected static final Map<String, LanternOptions> MAP = new HashMap<>();
        static StringRepresentableCodec<LanternOptions> CODEC = new StringRepresentableCodec<>(
                LanternOptions.values(),
                LanternOptions::bySerializedName,
                LanternOptions::ordinal
        );

        static {
            MAP.putAll(Arrays.stream(LanternOptions.values())
                    .collect(Collectors.toMap(LanternOptions::getSerializedName, Function.identity())));
        }

        public final float cutoff;
        public final String overlay;
        public final String typeName;
        public final BlockState state;

        LanternOptions(String typeName, BlockState state, float cutoff, String overlay) {
            this.cutoff = cutoff;
            this.state = state;
            this.overlay = overlay;
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
