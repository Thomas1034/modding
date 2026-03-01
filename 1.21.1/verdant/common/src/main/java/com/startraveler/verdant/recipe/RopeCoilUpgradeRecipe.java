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
package com.startraveler.verdant.recipe;

import com.mojang.datafixers.Products;
import com.mojang.datafixers.util.Function3;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.startraveler.verdant.Constants;
import com.startraveler.verdant.block.custom.RopeBlock;
import com.startraveler.verdant.item.component.RopeCoilData;
import com.startraveler.verdant.registry.DataComponentRegistry;
import com.startraveler.verdant.registry.ItemRegistry;
import com.startraveler.verdant.registry.RecipeSerializerRegistry;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.criterion.RecipeUnlockedTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

public class RopeCoilUpgradeRecipe extends CustomRecipe {

    protected final Item coil;
    protected final BlockItem rope;

    public RopeCoilUpgradeRecipe(CraftingBookCategory category, Item coil, Item rope) {
        super(category);
        this.coil = coil;
        if (rope instanceof BlockItem ropeBlockItem) {
            this.rope = ropeBlockItem;
        } else {
            throw new IllegalArgumentException("Passed non-block item as rope parameter to a rope coil upgrade recipe.");
        }
    }

    @Override
    public boolean matches(@NotNull CraftingInput input, Level level) {
        return assemble(input, level.registryAccess()) != ItemStack.EMPTY;
    }

    @Override
    public @NotNull ItemStack assemble(CraftingInput input, HolderLookup.@NotNull Provider registries) {
        // There must be at least two items to perform the recipe.
        if (input.ingredientCount() < 2) {
            return ItemStack.EMPTY;
        }
        // First, check to see if there is a rope coil somewhere here.
        // This is done by checking for the component.
        boolean ropeCoilIsPresent = false;
        int ropeCoilIndex = -1;
        for (int i = 0; i < input.size(); i++) {
            ItemStack item = input.getItem(i);
            if (item.is(this.coil) && item.has(DataComponentRegistry.ROPE_COIL.get())) {
                ropeCoilIsPresent = true;
                ropeCoilIndex = i;
            }
        }
        // If no rope coil was found, return false.
        if (!ropeCoilIsPresent) {
            return ItemStack.EMPTY;
        }
        // If a rope coil was found, make sure that it has the right component.
        RopeCoilData component = input.getItem(ropeCoilIndex).get(DataComponentRegistry.ROPE_COIL.get());
        if (component == null) {
            
            return ItemStack.EMPTY;
        }
        // The maximum allowed length that can be added to the coil.
        int remainingAllowedLength = RopeCoilData.MAX_LENGTH_FROM_CRAFTING - component.length();
        // The maximum allowed glow ink that can be added to the coil.
        int remainingAllowedLightLevel = RopeBlock.GLOW_MAX - component.lightLevel();
        int resultLength = component.length();
        int resultLightLevel = component.lightLevel();
        // Whether adding a hook is allowed.
        boolean canAddHook = !component.hasHook();
        boolean resultHasHook = component.hasHook();
        RopeCoilData.HangingBlockOptions resultHangingBlock = component.hangingBlock();
        for (int i = 0; i < input.size(); i++) {
            // Skip the rope coil, it's allowed.
            
            if (i == ropeCoilIndex) {
                
                continue;
            }
            ItemStack stack = input.getItem(i);
            Item item = stack.getItem();
            if (stack.is(this.rope)) {
                // If it is rope, decrement the amount of rope that can be added further.
                if (remainingAllowedLength > 0) {
                    remainingAllowedLength--;
                    resultLength++;
                } else {
                    // If no more rope can be added, the recipe fails.
                    return ItemStack.EMPTY;
                }
            } else if (stack.is(Items.TRIPWIRE_HOOK)) {
                // If it is a hook, check if a hook can be added.
                // If not, fail. If so, disallow further hooks.
                if (canAddHook) {
                    canAddHook = false;
                    resultHasHook = true;
                } else {
                    
                    return ItemStack.EMPTY;
                }
            } else if (RopeCoilData.HangingBlockOptions.getOption(stack) instanceof RopeCoilData.HangingBlockOptions options) {
                // If it is a hanging block, check if a hanging block can be added.
                // If not, fail. If so, disallow further hanging blocks.
                if (resultHangingBlock == RopeCoilData.HangingBlockOptions.NONE) {
                    resultHangingBlock = options;
                } else if (options != RopeCoilData.HangingBlockOptions.NONE) {
                    Constants.LOG.warn(
                            "Already has a hanging block {}, trying to add hanging block {} from item {}, returning empty.",
                            resultHangingBlock,
                            options,
                            stack
                    );
                    return ItemStack.EMPTY;
                }
            } else if (item == Items.GLOW_INK_SAC) {
                // If it is glow ink, decrement the amount of glow ink that can be added further.
                if (remainingAllowedLightLevel > 0) {
                    remainingAllowedLightLevel--;
                    resultLightLevel++;
                } else {
                    // If no more glow ink can be added, the recipe fails.
                    Constants.LOG.warn(
                            "Light level is too high (original {} plus one item), returning empty.",
                            resultLightLevel
                    );
                    return ItemStack.EMPTY;
                }
            } else if (stack.has(DataComponentRegistry.ROPE_COIL.get()) && stack.is(this.coil)) {
                // Allow combining rope coils, if they're not too long and don't both have hooks.
                RopeCoilData data = stack.get(DataComponentRegistry.ROPE_COIL.get());
                if (data == null) {
                    // This shouldn't happen... if it does, we have a problem.
                    
                    return ItemStack.EMPTY;
                }
                if (data.length() <= remainingAllowedLength) {
                    // If the length to be added is less than the remaining allowed length,
                    // add it in!
                    remainingAllowedLength -= data.length();
                    resultLength += data.length();
                } else {
                    // It's too long to be combined.
                    Constants.LOG.warn(
                            "Other coil is too long to be combined (original {} plus other {}), returning empty.",
                            resultLength,
                            data.length()
                    );
                    return ItemStack.EMPTY;
                }
                if (data.hasHook()) {
                    if (canAddHook) {
                        // It has a hook and a hook can be added.
                        // No further hooks can be added.
                        canAddHook = false;
                        resultHasHook = true;
                    } else {
                        // It has a hook and a hook cannot be added.
                        // Fail.
                        
                        return ItemStack.EMPTY;
                    }
                }
                if (resultHangingBlock == RopeCoilData.HangingBlockOptions.NONE) {
                    resultHangingBlock = data.hangingBlock();
                } else {
                    if (data.hangingBlock() != RopeCoilData.HangingBlockOptions.NONE) {
                        Constants.LOG.warn(
                                "Already has a hanging block {}, trying to add hanging block {} from other coil, returning empty.",
                                resultHangingBlock,
                                data.hangingBlock()
                        );
                        return ItemStack.EMPTY;
                    }
                }
                if (data.lightLevel() <= remainingAllowedLightLevel) {
                    remainingAllowedLightLevel -= data.lightLevel();
                    resultLightLevel += data.lightLevel();
                } else {
                    // It's got too high a light level to be combined.
                    Constants.LOG.warn(
                            "Light level is too high (original {} plus other {}), returning empty.",
                            resultLightLevel,
                            data.lightLevel()
                    );
                    return ItemStack.EMPTY;
                }
            }
        }
        ItemStack result = new ItemStack(this.coil);
        result.set(
                DataComponentRegistry.ROPE_COIL.get(),
                new RopeCoilData(
                        resultLength,
                        resultHasHook,
                        resultLightLevel,
                        resultHangingBlock,
                        this.rope.getBlock()
                )
        );
        
        return result;
    }

    @Override
    public @NotNull RecipeSerializer<? extends CustomRecipe> getSerializer() {
        return RecipeSerializerRegistry.ROPE_COIL_SERIALIZER.get();
    }

    public Item getCoil() {
        return this.coil;
    }

    public BlockItem getRope() {
        return this.rope;
    }

    public static class Serializer<T extends RopeCoilUpgradeRecipe> implements RecipeSerializer<T> {
        private final MapCodec<T> codec;
        private final StreamCodec<RegistryFriendlyByteBuf, T> streamCodec; // RegistryFriendlyByteBuf

        public Serializer(Factory<T> factory) {
            Objects.requireNonNull(factory);
            this.codec = RecordCodecBuilder.mapCodec((instance) -> {
                Products.P3<RecordCodecBuilder.Mu<T>, CraftingBookCategory, Item, Item> products = instance.group(
                        CraftingBookCategory.CODEC.fieldOf("category")
                                .orElse(CraftingBookCategory.MISC)
                                .forGetter(CraftingRecipe::category),
                        BuiltInRegistries.ITEM.byNameCodec()
                                .fieldOf("coil")
                                .orElse(Items.AIR)
                                .forGetter(RopeCoilUpgradeRecipe::getCoil),
                        BuiltInRegistries.ITEM.byNameCodec()
                                .fieldOf("rope")
                                .orElse(Items.AIR)
                                .forGetter(RopeCoilUpgradeRecipe::getRope)
                );
                Objects.requireNonNull(factory);
                return products.apply(instance, factory);
            });
            StreamCodec<? super RegistryFriendlyByteBuf, CraftingBookCategory> craftingBookCategoryStreamCodec = CraftingBookCategory.STREAM_CODEC;
            Function<T, CraftingBookCategory> categoryGetter = RopeCoilUpgradeRecipe::category;
            StreamCodec<? super RegistryFriendlyByteBuf, Item> itemStreamCodec = ByteBufCodecs.fromCodec(
                    BuiltInRegistries.ITEM.byNameCodec());
            Function<T, Item> coilGetter = RopeCoilUpgradeRecipe::getCoil;
            Function<T, Item> ropeGetter = RopeCoilUpgradeRecipe::getRope;
            this.streamCodec = StreamCodec.composite(
                    craftingBookCategoryStreamCodec,
                    categoryGetter,
                    itemStreamCodec,
                    coilGetter,
                    itemStreamCodec,
                    ropeGetter,
                    factory
            );
        }

        @Override
        public @NotNull MapCodec<T> codec() {
            return this.codec;
        }

        @Override
        public @NotNull StreamCodec<RegistryFriendlyByteBuf, T> streamCodec() {
            return this.streamCodec;
        }

        @FunctionalInterface
        public interface Factory<T extends RopeCoilUpgradeRecipe> extends Function3<CraftingBookCategory, Item, Item, T> {
        }
    }

    // Inspired by the implementation here: https://docs.neoforged.net/docs/resources/server/recipes/custom/#data-generation
    public static class Builder implements RecipeBuilder {
        protected final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();
        @Nullable
        protected String group;
        protected Item coil;
        protected BlockItem rope;
        private CraftingBookCategory category;

        public Builder() {
        }

        @Override
        public @NotNull Builder unlockedBy(@NotNull String name, @NotNull Criterion<?> criterion) {
            this.criteria.put(name, criterion);
            return this;
        }

        @Override
        public @NotNull Builder group(@Nullable String group) {
            this.group = group;
            return this;
        }

        @Override
        public @NotNull Item getResult() {
            return this.coil;
        }

        @Override
        public void save(RecipeOutput output, @NotNull ResourceKey<Recipe<?>> key) {
            this.fillDefaults();
            Advancement.Builder advancement = output.advancement()
                    .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(key))
                    .rewards(AdvancementRewards.Builder.recipe(key))
                    .requirements(AdvancementRequirements.Strategy.OR);
            this.criteria.forEach(advancement::addCriterion);
            output.accept(
                    key,
                    new RopeCoilUpgradeRecipe(this.category, this.coil, this.rope),
                    advancement.build(key.identifier().withPrefix("recipes/"))
            );
        }

        private void fillDefaults() {
            if (this.coil == null) {
                this.coil = ItemRegistry.ROPE_COIL.get();
            }
            if (this.rope == null) {
                this.rope = ItemRegistry.ROPE.get();
            }
        }

        public Builder coil(Item coil) {
            this.coil = coil;
            return this;
        }

        public Builder rope(BlockItem rope) {
            this.rope = rope;
            return this;
        }

        public Builder category(CraftingBookCategory category) {
            this.category = category;
            return this;
        }
    }
}

