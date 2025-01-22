package com.thomas.verdant.registry;

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

        recipes.register(
                ItemRegistry.HEART_FRAGMENT.get(),
                PotionRegistry.ANTIDOTE.asHolder(),
                PotionRegistry.LONG_ANTIDOTE.asHolder(),
                PotionRegistry.STRONG_ANTIDOTE.asHolder()
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
