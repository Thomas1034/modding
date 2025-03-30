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

import com.startraveler.verdant.registry.DataComponentRegistry;
import com.startraveler.verdant.registry.ItemRegistry;
import com.startraveler.verdant.registry.RecipeSerializerRegistry;
import com.startraveler.verdant.util.OKLabBlender;
import com.startraveler.verdant.util.VerdantTags;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.component.SuspiciousStewEffects;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SuspiciousEffectHolder;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.stream.Stream;

public class BlowdartTippingRecipe extends CustomRecipe {

    public static final float SUSPICIOUS_STEW_BONUS = 2;
    public static final float EFFECT_DURATION_BASE_MULTIPLIER = 2;
    public static final float EFFECT_DURATION_PER_BINDER_MULTIPLIER = 1;

    public BlowdartTippingRecipe(CraftingBookCategory category) {
        super(category);
    }

    @Override
    public boolean matches(CraftingInput input, Level level) {
        return assemble(input, level.registryAccess()) != ItemStack.EMPTY;
    }

    @Override
    public ItemStack assemble(CraftingInput input, HolderLookup.Provider registries) {
        ItemStack result = ItemStack.EMPTY;

        boolean isValid = true;
        int dartCount = 0;
        List<SuspiciousStewEffects> suspiciousEffectsFromFlowers = new ArrayList<>();
        List<SuspiciousStewEffects> suspiciousEffectsFromStew = new ArrayList<>();
        List<MobEffectInstance> directEffects = new ArrayList<>();
        int binderCount = 0;
        for (ItemStack stack : input.items()) {
            boolean anySucceeded = false;
            // TODO: allow item to fulfil multiple conditions?
            if (stack.is(ItemRegistry.DART.get())) {

                dartCount++;
                anySucceeded = true;

            }

            if (stack.has(DataComponentRegistry.BLOWDART_TIPPING_INGREDIENT.get())) {

                directEffects.addAll(Objects.requireNonNull(stack.get(DataComponentRegistry.BLOWDART_TIPPING_INGREDIENT.get()))
                        .effects());
                anySucceeded = true;

            }

            if (stack.has(DataComponents.SUSPICIOUS_STEW_EFFECTS)) {

                suspiciousEffectsFromStew.add(stack.get(DataComponents.SUSPICIOUS_STEW_EFFECTS));
                anySucceeded = true;

            }

            if (stack.getItem() instanceof BlockItem blockItem && blockItem.getBlock() instanceof SuspiciousEffectHolder seh) {

                suspiciousEffectsFromFlowers.add(seh.getSuspiciousEffects());
                anySucceeded = true;

            }

            if (stack.is(VerdantTags.Items.DART_EFFECT_BINDERS)) {

                binderCount++;
                anySucceeded = true;

            }

            if (stack.isEmpty()) {
                anySucceeded = true;
            }

            isValid &= anySucceeded;
        }


        isValid &= dartCount > 0;


        isValid &= (!suspiciousEffectsFromFlowers.isEmpty() || !suspiciousEffectsFromStew.isEmpty() || !directEffects.isEmpty());

        if (isValid) {
            int durationMultiplier = (int) (EFFECT_DURATION_BASE_MULTIPLIER + binderCount * EFFECT_DURATION_PER_BINDER_MULTIPLIER);
            List<MobEffectInstance> customEffects = Stream.concat(
                    Stream.concat(
                            suspiciousEffectsFromStew.stream()
                                    .flatMap(suspiciousStewEffects -> suspiciousStewEffects.effects()
                                            .stream()
                                            .map(SuspiciousStewEffects.Entry::createEffectInstance))
                                    .map(oldEffects -> new MobEffectInstance(
                                            oldEffects.getEffect(),
                                            (int) (oldEffects.getDuration() * SUSPICIOUS_STEW_BONUS),
                                            oldEffects.getAmplifier(),
                                            oldEffects.isAmbient(),
                                            oldEffects.isVisible(),
                                            oldEffects.showIcon()
                                    )),
                            suspiciousEffectsFromFlowers.stream()
                                    .flatMap(suspiciousStewEffects -> suspiciousStewEffects.effects()
                                            .stream()
                                            .map(SuspiciousStewEffects.Entry::createEffectInstance))
                    ), directEffects.stream()
            ).map(oldEffects -> new MobEffectInstance(
                    oldEffects.getEffect(),
                    oldEffects.getDuration() * durationMultiplier,
                    oldEffects.getAmplifier(),
                    oldEffects.isAmbient(),
                    oldEffects.isVisible(),
                    oldEffects.showIcon()
            )).toList();

            int color = OKLabBlender.blendColors(customEffects.stream()
                    .map(instance -> instance.getEffect().value().getColor())
                    .toList());
            result = new ItemStack(ItemRegistry.TIPPED_DART.get(), dartCount);
            result.set(
                    DataComponents.POTION_CONTENTS,
                    new PotionContents(Optional.empty(), Optional.of(color), customEffects, Optional.empty())
            );
        }

        return result;
    }

    @Override
    public RecipeSerializer<? extends CustomRecipe> getSerializer() {
        return RecipeSerializerRegistry.BLOWDART_TIPPING_SERIALIZER.get();
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

        @Override
        public Item getResult() {
            return ItemRegistry.TIPPED_DART.get();
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
                    new BlowdartTippingRecipe(this.category),
                    advancement.build(key.location().withPrefix("recipes/"))
            );
        }

        public Builder category(CraftingBookCategory category) {
            this.category = category;
            return this;
        }
    }
}

