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

import com.google.common.base.Suppliers;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.startraveler.verdant.registry.BlockRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BellAttachType;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public record RopeCoilData(int length,
        boolean hasHook,
        int lightLevel,
        HangingBlockOptions hangingBlock,
        Supplier<Block> ropeBlockSupplier) {
    public static final int MAX_LENGTH_FROM_CRAFTING = 32;
    public static final Codec<RopeCoilData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.INT.fieldOf("length").forGetter(RopeCoilData::length),
            Codec.BOOL.fieldOf("hasHook").forGetter(RopeCoilData::hasHook),
            Codec.INT.fieldOf("lightLevel").forGetter(RopeCoilData::lightLevel),
            HangingBlockOptions.CODEC.fieldOf("hangingBlock").forGetter(RopeCoilData::hangingBlock),
            BuiltInRegistries.BLOCK.byNameCodec().fieldOf("rope").forGetter(RopeCoilData::ropeBlock)
    ).apply(instance, RopeCoilData::new));
    @SuppressWarnings({"Convert2MethodRef", "FunctionalExpressionCanBeFolded"})
    public static final RopeCoilData DEFAULT = new RopeCoilData(
            4,
            false,
            0,
            HangingBlockOptions.NONE,
            () -> BlockRegistry.ROPE.get()
    );
    @SuppressWarnings({"Convert2MethodRef", "FunctionalExpressionCanBeFolded"})
    public static final RopeCoilData DEFAULT_TWISTED = new RopeCoilData(
            4,
            false,
            0,
            HangingBlockOptions.NONE,
            () -> BlockRegistry.TWISTED_ROPE.get()
    );

    public RopeCoilData(int length,
                        boolean hasHook,
                        int lightLevel,
                        HangingBlockOptions hangingBlock,
                        Block ropeBlockSupplier) {
        this(length, hasHook, lightLevel, hangingBlock, () -> ropeBlockSupplier);
    }

    public Block ropeBlock() {
        return this.ropeBlockSupplier.get();
    }

    public enum HangingBlockOptions implements StringRepresentable {

        NONE("none", Items.AIR, Blocks.AIR.defaultBlockState()),
        LANTERN(
                "lantern",
                Items.LANTERN,
                Blocks.LANTERN.defaultBlockState().setValue(BlockStateProperties.HANGING, true)
        ),
        SOUL_LANTERN(
                "soul_lantern",
                Items.SOUL_LANTERN,
                Blocks.SOUL_LANTERN.defaultBlockState().setValue(BlockStateProperties.HANGING, true)
        ),
        @SuppressWarnings({"Convert2MethodRef"})
        SAP_LANTERN(
                "sap_lantern",
                () -> BlockRegistry.SAP_LANTERN.get(),
                () -> BlockRegistry.SAP_LANTERN.get().defaultBlockState().setValue(BlockStateProperties.HANGING, true)
        ),
        BELL(
                "bell",
                Items.BELL,
                Blocks.BELL.defaultBlockState().setValue(BlockStateProperties.BELL_ATTACHMENT, BellAttachType.CEILING)
        ),
        SHROOMLIGHT(
                "shroomlight",
                Items.SHROOMLIGHT,
                Blocks.SHROOMLIGHT.defaultBlockState()
        );

        public static final StringRepresentableCodec<@NotNull HangingBlockOptions> CODEC = StringRepresentable.fromEnum(
                HangingBlockOptions::values);

        public final String typeName;
        public final Supplier<BlockState> state;
        public final Supplier<ItemLike> item;

        HangingBlockOptions(String typeName, Supplier<@NotNull ItemLike> item, Supplier<BlockState> state) {
            this.state = state;
            this.item = item;
            this.typeName = typeName;
        }

        HangingBlockOptions(String typeName, Item item, BlockState state) {
            this.state = Suppliers.ofInstance(state);
            this.item = Suppliers.ofInstance(item);
            this.typeName = typeName;
        }

        public static HangingBlockOptions getOption(ItemStack stack) {
            for (HangingBlockOptions option : HangingBlockOptions.values()) {
                if (stack.is(option.getItem())) {
                    return option;
                }
            }
            return null;
        }

        public Item getItem() {
            return this.item.get().asItem();
        }

        @Override
        public @NotNull String getSerializedName() {
            return this.typeName;
        }

        public BlockState getState() {
            return this.state.get();
        }
    }
}

