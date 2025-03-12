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

import net.minecraft.core.Holder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import org.apache.commons.lang3.function.TriConsumer;

import java.util.Set;

public class PotionRecipeRegistry {

    // MobEffects.DARKNESS


    private final TriConsumer<Holder<Potion>, Item, Holder<Potion>> potionRegistrar;
    private final TriConsumer<Item, Ingredient, Item> genericRegistrar;

    private PotionRecipeRegistry(TriConsumer<Holder<Potion>, Item, Holder<Potion>> potionRegistrar, TriConsumer<Item, Ingredient, Item> genericRegistrar) {
        this.potionRegistrar = potionRegistrar;
        this.genericRegistrar = genericRegistrar;
    }

    public static void init(TriConsumer<Holder<Potion>, Item, Holder<Potion>> potionRegistrar, TriConsumer<Item, Ingredient, Item> genericRegistrar) {

        PotionRecipeRegistry recipes = new PotionRecipeRegistry(potionRegistrar, genericRegistrar);

        recipes.register(Potions.WATER, ItemRegistry.ROASTED_COFFEE.get(), PotionRegistry.CAFFEINE.asHolder());
        recipes.register(
                PotionRegistry.CAFFEINE.asHolder(),
                ItemRegistry.ROASTED_COFFEE.get(),
                PotionRegistry.LONG_CAFFEINE.asHolder()
        );
        recipes.register(PotionRegistry.CAFFEINE.asHolder(), Items.SUGAR, PotionRegistry.STRONG_CAFFEINE.asHolder());
        recipes.register(
                PotionRegistry.STRONG_CAFFEINE.asHolder(),
                ItemRegistry.ROASTED_COFFEE.get(),
                PotionRegistry.LONG_STRONG_CAFFEINE.asHolder()
        );
        recipes.register(
                PotionRegistry.LONG_CAFFEINE.asHolder(),
                Items.SUGAR,
                PotionRegistry.LONG_STRONG_CAFFEINE.asHolder()
        );
        recipes.register(Potions.THICK, ItemRegistry.SPARKLING_STARCH.get(), PotionRegistry.COLLOID.asHolder());
        recipes.register(PotionRegistry.COLLOID.asHolder(), Items.REDSTONE, PotionRegistry.LONG_COLLOID.asHolder());
        recipes.register(
                PotionRegistry.COLLOID.asHolder(),
                Items.GLOWSTONE_DUST,
                PotionRegistry.STRONG_COLLOID.asHolder()
        );

        recipes.register(Potions.THICK, BlockRegistry.STINKING_BLOSSOM.get(), PotionRegistry.STENCH.asHolder());
        recipes.register(PotionRegistry.STENCH.asHolder(), Items.REDSTONE, PotionRegistry.LONG_STENCH.asHolder());
        recipes.register(
                PotionRegistry.STENCH.asHolder(),
                Items.GLOWSTONE_DUST,
                PotionRegistry.STRONG_STENCH.asHolder()
        );

        recipes.register(Potions.THICK, BlockRegistry.DROWNED_HEMLOCK.get(), PotionRegistry.ASPHYXIATING.asHolder());
        recipes.register(
                PotionRegistry.ASPHYXIATING.asHolder(),
                Items.REDSTONE,
                PotionRegistry.LONG_ASPHYXIATING.asHolder()
        );
        recipes.register(
                PotionRegistry.ASPHYXIATING.asHolder(),
                Items.GLOWSTONE_DUST,
                PotionRegistry.STRONG_ASPHYXIATING.asHolder()
        );

        recipes.register(Potions.THICK, ItemRegistry.HEART_FRAGMENT.get(), PotionRegistry.ANTIDOTE.asHolder());
        recipes.register(PotionRegistry.ANTIDOTE.asHolder(), Items.REDSTONE, PotionRegistry.LONG_ANTIDOTE.asHolder());
        recipes.register(
                PotionRegistry.ANTIDOTE.asHolder(),
                Items.GLOWSTONE_DUST,
                PotionRegistry.STRONG_ANTIDOTE.asHolder()
        );

        recipes.register(Potions.THICK, BlockRegistry.RUE.get(), PotionRegistry.BLURRED.asHolder());
        recipes.register(PotionRegistry.BLURRED.asHolder(), Items.REDSTONE, PotionRegistry.LONG_BLURRED.asHolder());
        recipes.register(
                PotionRegistry.BLURRED.asHolder(),
                Items.GLOWSTONE_DUST,
                PotionRegistry.STRONG_BLURRED.asHolder()
        );

        recipes.register(Potions.THICK, ItemRegistry.ROTTEN_COMPOST.get(), PotionRegistry.FOOD_POISONING.asHolder());
        recipes.register(Potions.THICK, ItemRegistry.RANCID_SLIME.get(), PotionRegistry.FOOD_POISONING.asHolder());
        recipes.register(
                PotionRegistry.FOOD_POISONING.asHolder(),
                Items.REDSTONE,
                PotionRegistry.LONG_FOOD_POISONING.asHolder()
        );
        recipes.register(
                PotionRegistry.FOOD_POISONING.asHolder(),
                Items.GLOWSTONE_DUST,
                PotionRegistry.STRONG_FOOD_POISONING.asHolder()
        );

        recipes.register(
                PotionRegistry.COLLOID.asHolder(),
                Items.FERMENTED_SPIDER_EYE,
                PotionRegistry.ADRENALINE.asHolder()
        );
        recipes.register(
                PotionRegistry.ADRENALINE.asHolder(),
                Items.REDSTONE,
                PotionRegistry.LONG_ADRENALINE.asHolder()
        );
        recipes.register(
                PotionRegistry.ADRENALINE.asHolder(),
                Items.GLOWSTONE_DUST,
                PotionRegistry.STRONG_ADRENALINE.asHolder()
        );

        recipes.register(
                PotionRegistry.BLURRED.asHolder(),
                Items.FERMENTED_SPIDER_EYE,
                PotionRegistry.CLUMSINESS.asHolder()
        );
        recipes.register(
                PotionRegistry.CLUMSINESS.asHolder(),
                Items.REDSTONE,
                PotionRegistry.LONG_CLUMSINESS.asHolder()
        );
        recipes.register(
                PotionRegistry.CLUMSINESS.asHolder(),
                Items.GLOWSTONE_DUST,
                PotionRegistry.STRONG_CLUMSINESS.asHolder()
        );

    }

    public void register(ItemLike ingredient, Holder<Potion> normal, Holder<Potion> extended, Holder<Potion> strong) {
        this.register(Potions.AWKWARD, ingredient, normal);
        this.register(normal, Items.REDSTONE, extended);
        this.register(normal, Items.GLOWSTONE_DUST, strong);
    }

    public void register(Item input, Set<ItemLike> ingredient, Item output) {
        this.register(input, Ingredient.of(ingredient.toArray(ItemLike[]::new)), output);
    }

    public void register(ItemLike input, ItemLike ingredient, ItemLike output) {
        this.register(input, Ingredient.of(ingredient), output);
    }

    public void register(ItemLike input, Ingredient ingredient, ItemLike output) {
        this.genericRegistrar.accept(input.asItem(), ingredient, output.asItem());
    }

    public void register(Holder<Potion> input, ItemLike ingredient, Holder<Potion> output) {
        this.potionRegistrar.accept(input, ingredient.asItem(), output);
    }


}

