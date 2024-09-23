package com.thomas.turbulent.datagen;

import java.util.List;
import java.util.function.Consumer;

import com.thomas.turbulent.Turbulent;

import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.data.recipes.SingleItemRecipeBuilder;
import net.minecraft.data.recipes.SmithingTransformRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import net.minecraftforge.registries.ForgeRegistries;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {

	public ModRecipeProvider(PackOutput pOutput) {
		super(pOutput);
	}

	@Override
	protected void buildRecipes(Consumer<FinishedRecipe> recipeWriter) {

	}

	// Stonecutting recipe.
	@SuppressWarnings("unchecked")
	protected static void stonecutting(Consumer<FinishedRecipe> finishedRecipeConsumer, Object ingredient,
			RecipeCategory recipeCategory, ItemLike result, int count) {

		// The name of the recipe.
		String recipeName = Turbulent.MOD_ID + ":" + getItemName(result) + "_from_stonecutting_";

		Ingredient toAdd = null;

		// Determines what type of ingredient is there.
		if (ingredient instanceof ItemLike item) {
			toAdd = Ingredient.of(item);
			recipeName += getItemName((ItemLike) ingredient);

		} else if (ingredient instanceof TagKey<?> tag) {
			toAdd = Ingredient.of((TagKey<Item>) tag);
			recipeName += dePath((tag).location());

		} else {
			throw new IllegalArgumentException("Cannot create a stonecutting recipe with a non-Item, non-Tag object.");
		}

		// Creates the recipe.
		SingleItemRecipeBuilder recipe = SingleItemRecipeBuilder.stonecutting(toAdd, recipeCategory, result, count);

		// Adds in the unlock trigger for the ingredient.
		if (ingredient instanceof ItemLike)
			recipe = recipe.unlockedBy(getHasName((ItemLike) ingredient), has((ItemLike) ingredient));
		else if (ingredient instanceof TagKey) {
			String name = "has" + ((TagKey<Item>) ingredient).registry().registry().toDebugFileName();
			recipe = recipe.unlockedBy(name, has((TagKey<Item>) ingredient));
		} else {
			throw new IllegalArgumentException("Unrecognized item or tag type: " + ingredient);
		}

		// Adds in the unlock trigger for the result.
		recipe = recipe.unlockedBy(getHasName(result), has(result));

		recipe.group(getItemNamespace(result) + ":" + getItemName(result));

		// Prints out the result.
		System.out.println("Finished stonecutting recipe, saving with name: " + recipeName);
		recipe.save(finishedRecipeConsumer, recipeName);
	}

	private static String getItemNamespace(ItemLike result) {
		return ForgeRegistries.ITEMS.getKey(result.asItem()).getNamespace();
	}

	// Shapeless recipe. Item at i must correspond to item count at i.
	@SuppressWarnings("unchecked")
	protected static void shapeless(Consumer<FinishedRecipe> finishedRecipeConsumer, List<Object> ingredients,
			List<Integer> counts, RecipeCategory recipeCategory, ItemLike result, int count) {

		ShapelessRecipeBuilder recipe = ShapelessRecipeBuilder.shapeless(recipeCategory, result, count);
		String recipeName = Turbulent.MOD_ID + ":" + getItemName(result) + "_from";

		// Check if the ingredients match the count length.
		if (counts.size() != ingredients.size()) {
			throw new IllegalArgumentException("Token count does not match ingredient count.");
		}

		// Adds in the ingredients.
		for (int i = 0; i < counts.size(); i++) {
			if (ingredients.get(i) instanceof ItemLike) {
				recipeName += "_" + getItemName((ItemLike) ingredients.get(i));
				recipe = recipe.requires((ItemLike) ingredients.get(i), counts.get(i));
			} else if (ingredients.get(i) instanceof TagKey) {
				recipeName += "_tag_" + dePath(((TagKey<Item>) ingredients.get(i)).location());
				recipe = recipe.requires(Ingredient.of((TagKey<Item>) ingredients.get(i)), counts.get(i));
			} else {
				throw new IllegalArgumentException("Unrecognized item or tag type: " + ingredients.get(i));
			}
		}

		// Adds in the unlock triggers for the ingredients.
		for (int i = 0; i < ingredients.size(); i++) {
			if (ingredients.get(i) instanceof ItemLike)
				recipe = recipe.unlockedBy(getHasName((ItemLike) ingredients.get(i)),
						has((ItemLike) ingredients.get(i)));
			else if (ingredients.get(i) instanceof TagKey) {
				String name = "has" + ((TagKey<Item>) ingredients.get(i)).registry().registry().toDebugFileName();
				recipe = recipe.unlockedBy(name, has((TagKey<Item>) ingredients.get(i)));
			} else {
				throw new IllegalArgumentException("Unrecognized item or tag type: " + ingredients.get(i));
			}
		}
		// Adds in the unlock trigger for the result.
		recipe = recipe.unlockedBy(getHasName(result), has(result));

		recipe.group(getItemNamespace(result) + ":" + getItemName(result));

		// Saves the recipe.
		System.out.println("Finished shapeless recipe, saving with name: " + recipeName);
		recipe.save(finishedRecipeConsumer, recipeName);

	}

	private static String dePath(ResourceLocation location) {
		char[] chars = location.toString().toCharArray();

		for (int i = 0; i < chars.length; i++) {
			if (chars[i] == '/' || chars[i] == '\\' || chars[i] == ':') {
				chars[i] = '_';
			}
		}

		return null;
	}

	protected static void smithingTransform(Consumer<FinishedRecipe> finishedRecipeConsumer, ItemLike template,
			ItemLike base, ItemLike addition, RecipeCategory recipeCategory, Item result) {
		SmithingTransformRecipeBuilder recipe = SmithingTransformRecipeBuilder.smithing(Ingredient.of(template),
				Ingredient.of(base), Ingredient.of(addition), recipeCategory, result);

		// Recipe gift triggers
		recipe = recipe.unlocks(getHasName(template), has(template));
		recipe = recipe.unlocks(getHasName(base), has(base));
		recipe = recipe.unlocks(getHasName(addition), has(addition));

		String recipeName = Turbulent.MOD_ID + ":" + getItemName(result) + "_from_smithing_transform_of_"
				+ getItemName(base);
		System.out.println("Finished smithing recipe, saving with name: " + recipeName);

		recipe.save(finishedRecipeConsumer, recipeName);
	}

	// Shaped recipe. Token at i must correspond to ingredient at i.
	@SuppressWarnings("unchecked")
	protected static void shaped(Consumer<FinishedRecipe> finishedRecipeConsumer, List<String> pattern,
			List<Character> tokens, List<Object> ingredients, RecipeCategory recipeCategory, ItemLike result,
			int count) {

		ShapedRecipeBuilder recipe = ShapedRecipeBuilder.shaped(recipeCategory, result, count);
		String recipeName = Turbulent.MOD_ID + ":" + getItemName(result) + "_from";
		// System.out.println(recipeName);

		// Adds in the pattern.
		for (String row : pattern) {
			recipe = recipe.pattern(row);
		}

		// Check if the ingredients match the tokens.
		if (tokens.size() != ingredients.size()) {
			throw new IllegalArgumentException("Token count does not match ingredient count.");
		}
		// System.out.println("There are " + tokens.size() + " tokens. They are: ");
		// Defines the tokens.
		for (int i = 0; i < tokens.size(); i++) {
			if (ingredients.get(i) instanceof ItemLike) {
				recipeName += "_" + getItemName((ItemLike) ingredients.get(i));
				recipe = recipe.define(tokens.get(i), (ItemLike) ingredients.get(i));
			} else if (ingredients.get(i) instanceof TagKey) {
				recipeName += "_tag_" + dePath(((TagKey<Item>) ingredients.get(i)).location());
				recipe = recipe.define(tokens.get(i), Ingredient.of((TagKey<Item>) ingredients.get(i)));
			} else {
				throw new IllegalArgumentException("Unrecognized item or tag type: " + ingredients.get(i));
			}
			// System.out.println(recipeName);

		}

		// Adds in the unlock trigger for the ingredients.
		for (int i = 0; i < ingredients.size(); i++) {
			if (ingredients.get(i) instanceof ItemLike) {
				String name = getHasName((ItemLike) ingredients.get(i));
				// System.out.println("Adding criterion with name: " + name);
				recipe = recipe.unlockedBy(name, has((ItemLike) ingredients.get(i)));
			} else if (ingredients.get(i) instanceof TagKey) {
				String name = "has_" + ((TagKey<Item>) ingredients.get(i)).registry().registry().toDebugFileName();
				// System.out.println("Adding criterion with name: " + name);
				recipe = recipe.unlockedBy(name, has((TagKey<Item>) ingredients.get(i)));
			} else {
				throw new IllegalArgumentException("Unrecognized item or tag type: " + ingredients.get(i));
			}
		}
		// Adds in the unlock trigger for the result.

		// System.out.println("Adding criterion with name: " + getHasName(result));
		recipe = recipe.unlockedBy(getHasName(result), has(result));

		recipe.group(getItemNamespace(result) + ":" + getItemName(result));

		// Saves the recipe.
		System.out.println("Finished shaped recipe, saving with name: " + recipeName);
		recipe.save(finishedRecipeConsumer, recipeName);
	}

	protected static void oreCooking(Consumer<FinishedRecipe> finishedRecipeConsumer, List<ItemLike> ingredients,
			RecipeCategory category, ItemLike result, float experience, int cookingTime, String group) {
		smelting(finishedRecipeConsumer, ingredients, category, result, experience, cookingTime, group);
		blasting(finishedRecipeConsumer, ingredients, category, result, experience, cookingTime / 2, group);
	}

	protected static void foodCooking(Consumer<FinishedRecipe> finishedRecipeConsumer, List<ItemLike> ingredients,
			RecipeCategory category, ItemLike result, float experience, int cookingTime) {

		String group = (getItemNamespace(result) + ":" + getItemName(result));
		campfire(finishedRecipeConsumer, ingredients, category, result, experience, 2 * cookingTime, group);
		smelting(finishedRecipeConsumer, ingredients, category, result, experience, cookingTime, group);
		smoking(finishedRecipeConsumer, ingredients, category, result, experience, cookingTime / 2, group);
	}

	protected static void smoking(Consumer<FinishedRecipe> finishedRecipeConsumer, List<ItemLike> ingredients,
			RecipeCategory category, ItemLike result, float experience, int cookingTime, String group) {
		cooking(finishedRecipeConsumer, RecipeSerializer.SMOKING_RECIPE, ingredients, category, result, experience,
				cookingTime, group, "_from_smoking");
	}

	protected static void smelting(Consumer<FinishedRecipe> finishedRecipeConsumer, List<ItemLike> ingredients,
			RecipeCategory category, ItemLike result, float experience, int cookingTime, String group) {
		cooking(finishedRecipeConsumer, RecipeSerializer.SMELTING_RECIPE, ingredients, category, result, experience,
				cookingTime, group, "_from_smelting");
	}

	protected static void campfire(Consumer<FinishedRecipe> finishedRecipeConsumer, List<ItemLike> ingredients,
			RecipeCategory category, ItemLike result, float experience, int cookingTime, String group) {
		cooking(finishedRecipeConsumer, RecipeSerializer.CAMPFIRE_COOKING_RECIPE, ingredients, category, result,
				experience, cookingTime, group, "_from_campfire");
	}

	protected static void blasting(Consumer<FinishedRecipe> finishedRecipeConsumer, List<ItemLike> ingredients,
			RecipeCategory category, ItemLike result, float experience, int cookingTime, String group) {
		cooking(finishedRecipeConsumer, RecipeSerializer.BLASTING_RECIPE, ingredients, category, result, experience,
				cookingTime, group, "_from_blasting");
	}

	protected static void charcoalSmelting(Consumer<FinishedRecipe> finishedRecipeConsumer, ItemLike pIngredient) {
		cooking(finishedRecipeConsumer, RecipeSerializer.SMELTING_RECIPE, List.of(pIngredient), RecipeCategory.MISC,
				Items.CHARCOAL, 0.1f, 200, "charcoal", "_from_smelting_" + getItemName(pIngredient));
	}

	protected static void cooking(Consumer<FinishedRecipe> finishedRecipeConsumer,
			RecipeSerializer<? extends AbstractCookingRecipe> cookingSerializer, List<ItemLike> ingredients,
			RecipeCategory category, ItemLike result, float experience, int cookingTime, String group,
			String recipeName) {
		for (ItemLike itemlike : ingredients) {
			SimpleCookingRecipeBuilder
					.generic(Ingredient.of(itemlike), category, result, experience, cookingTime, cookingSerializer)
					.group(group).unlockedBy(getHasName(itemlike), has(itemlike)).save(finishedRecipeConsumer,
							Turbulent.MOD_ID + ":" + getItemName(result) + recipeName + "_" + getItemName(itemlike));
		}
	}
}
