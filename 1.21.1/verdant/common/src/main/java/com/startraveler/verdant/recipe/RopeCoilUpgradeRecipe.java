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

import com.startraveler.verdant.block.custom.RopeBlock;
import com.startraveler.verdant.item.component.RopeCoilData;
import com.startraveler.verdant.registry.DataComponentRegistry;
import com.startraveler.verdant.registry.ItemRegistry;
import com.startraveler.verdant.registry.RecipeSerializerRegistry;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedHashMap;
import java.util.Map;

public class RopeCoilUpgradeRecipe extends CustomRecipe {

    public RopeCoilUpgradeRecipe(CraftingBookCategory category) {
        super(category);
    }

    @Override
    public boolean matches(CraftingInput input, Level level) {
        return assemble(input, level.registryAccess()) != ItemStack.EMPTY;
    }

    @Override
    public ItemStack assemble(CraftingInput input, HolderLookup.Provider registries) {
        // There must be at least two items to perform the recipe.
        if (input.ingredientCount() < 2) {
            return ItemStack.EMPTY;
        }
        // First, check to see if there is a rope coil somewhere here.
        // This is done by checking for the component.
        boolean ropeCoilIsPresent = false;
        int ropeIndex = -1;
        for (int i = 0; i < input.size(); i++) {
            if (input.getItem(i).has(DataComponentRegistry.ROPE_COIL.get())) {
                ropeCoilIsPresent = true;
                ropeIndex = i;
            }
        }
        // If no rope coil was found, return false.
        if (!ropeCoilIsPresent) {
            return ItemStack.EMPTY;
        }
        // Otherwise, check for illegal items.
        // This varies based on the component, so get the component!
        RopeCoilData component = input.getItem(ropeIndex).get(DataComponentRegistry.ROPE_COIL.get());
        if (component == null) {
            return ItemStack.EMPTY;
        }
        // The maximum allowed length that can be added to the coil.
        int remainingAllowedLength = RopeCoilData.MAX_LENGTH_FROM_CRAFTING - component.length();
        int remainingAllowedLightLevel = RopeBlock.GLOW_MAX - component.lightLevel();
        int resultLength = component.length();
        int resultLightLevel = component.lightLevel();
        // Whether adding a hook is allowed.
        boolean canAddHook = !component.hasHook();
        boolean resultHasHook = component.hasHook();
        RopeCoilData.LanternOptions resultLantern = component.lantern();
        for (int i = 0; i < input.size(); i++) {
            // Skip the rope coil, it's allowed.
            if (i == ropeIndex) {
                continue;
            }
            ItemStack stack = input.getItem(i);
            Item item = stack.getItem();
            if (item == ItemRegistry.ROPE.get()) {
                // If it is rope, decrement the amount of rope that can be added further.
                if (remainingAllowedLength > 0) {
                    remainingAllowedLength--;
                    resultLength++;
                } else {
                    // If no more rope can be added, the recipe fails.
                    return ItemStack.EMPTY;
                }
            } else if (item == Items.TRIPWIRE_HOOK) {
                // If it is a hook, check if a hook can be added.
                // If not, fail. If so, disallow further hooks.
                if (canAddHook) {
                    canAddHook = false;
                    resultHasHook = true;
                } else {
                    return ItemStack.EMPTY;
                }
            } else if (item == Items.LANTERN) {
                // If it is a hook, check if a hook can be added.
                // If not, fail. If so, disallow further hooks.
                if (resultLantern == RopeCoilData.LanternOptions.NONE) {
                    resultLantern = RopeCoilData.LanternOptions.LANTERN;
                } else {
                    return ItemStack.EMPTY;
                }
            } else if (item == Items.SOUL_LANTERN) {
                // If it is a hook, check if a hook can be added.
                // If not, fail. If so, disallow further hooks.
                if (resultLantern == RopeCoilData.LanternOptions.NONE) {
                    resultLantern = RopeCoilData.LanternOptions.SOUL_LANTERN;
                } else {
                    return ItemStack.EMPTY;
                }
            } else if (item == Items.BELL) {
                // If it is a hook, check if a hook can be added.
                // If not, fail. If so, disallow further hooks.
                if (resultLantern == RopeCoilData.LanternOptions.NONE) {
                    resultLantern = RopeCoilData.LanternOptions.BELL;
                } else {
                    return ItemStack.EMPTY;
                }
            } else if (item == Items.GLOW_INK_SAC) {
                // If it is rope, decrement the amount of rope that can be added further.
                if (remainingAllowedLightLevel > 0) {
                    remainingAllowedLightLevel--;
                    resultLightLevel++;
                } else {
                    // If no more rope can be added, the recipe fails.
                    return ItemStack.EMPTY;
                }
            } else if (stack.has(DataComponentRegistry.ROPE_COIL.get())) {
                // Allow combining rope coils, if they're not too long and don't both have hooks.
                RopeCoilData data = stack.get(DataComponentRegistry.ROPE_COIL.get());
                if (data == null) {
                    // This shouldn't happen... if it does, we have a problem.
                    return ItemStack.EMPTY;
                }
                if (data.length() <= remainingAllowedLength) {
                    remainingAllowedLength -= data.length();
                    resultLength += data.length();
                } else {
                    // It's too long to be combined.
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
                if (resultLantern == RopeCoilData.LanternOptions.NONE) {
                    resultLantern = data.lantern();
                } else {
                    if (data.lantern() != RopeCoilData.LanternOptions.NONE) {
                        return ItemStack.EMPTY;
                    }
                }
                if (data.lightLevel() <= remainingAllowedLightLevel) {
                    remainingAllowedLightLevel -= data.lightLevel();
                    resultLightLevel += data.lightLevel();
                } else {
                    // It's too long to be combined.
                    return ItemStack.EMPTY;
                }
            }
        }
        ItemStack result = new ItemStack(ItemRegistry.ROPE_COIL.get());
        result.set(
                DataComponentRegistry.ROPE_COIL.get(),
                new RopeCoilData(resultLength, resultHasHook, resultLightLevel, resultLantern)
        );
        return result;
    }

    @Override
    public RecipeSerializer<? extends CustomRecipe> getSerializer() {
        return RecipeSerializerRegistry.ROPE_COIL_SERIALIZER.get();
    }


    // Inspired by the implementation here: https://docs.neoforged.net/docs/resources/server/recipes/custom/#data-generation
    public static class Builder implements RecipeBuilder {
        protected final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();
        @Nullable
        protected String group;
        private CraftingBookCategory category;

        public Builder() {
        }

        @Override
        public Builder unlockedBy(String name, Criterion<?> criterion) {
            this.criteria.put(name, criterion);
            return this;
        }

        @Override
        public Builder group(@Nullable String group) {
            this.group = group;
            return this;
        }

        public Builder category(CraftingBookCategory category) {
            this.category = category;
            return this;
        }

        @Override
        public Item getResult() {
            return ItemRegistry.ROPE_COIL.get();
        }

        @Override
        public void save(RecipeOutput output, ResourceKey<Recipe<?>> key) {
            Advancement.Builder advancement = output.advancement()
                    .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(key))
                    .rewards(AdvancementRewards.Builder.recipe(key))
                    .requirements(AdvancementRequirements.Strategy.OR);
            this.criteria.forEach(advancement::addCriterion);
            output.accept(
                    key,
                    new RopeCoilUpgradeRecipe(this.category),
                    advancement.build(key.location().withPrefix("recipes/"))
            );
        }
    }
}

