package com.thomas.verdant.datagen;

import java.util.List;
import java.util.function.Consumer;

import com.thomas.verdant.Verdant;
import com.thomas.verdant.block.ModBlocks;
import com.thomas.verdant.item.ModItems;

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
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {

	public ModRecipeProvider(PackOutput pOutput) {
		super(pOutput);
	}

	@Override
	protected void buildRecipes(Consumer<FinishedRecipe> recipeWriter) {
		// Verdant furniture
		charcoalSmelting(recipeWriter, ModBlocks.VERDANT_LOG.get());
		charcoalSmelting(recipeWriter, ModBlocks.STRIPPED_VERDANT_LOG.get());
		charcoalSmelting(recipeWriter, ModBlocks.VERDANT_WOOD.get());
		charcoalSmelting(recipeWriter, ModBlocks.STRIPPED_VERDANT_WOOD.get());
		shapeless(recipeWriter, List.of(ModBlocks.VERDANT_LOG.get()), List.of(1), RecipeCategory.BUILDING_BLOCKS,
				ModBlocks.VERDANT_PLANKS.get(), 4);
		shapeless(recipeWriter, List.of(ModBlocks.STRIPPED_VERDANT_LOG.get()), List.of(1),
				RecipeCategory.BUILDING_BLOCKS, ModBlocks.VERDANT_PLANKS.get(), 4);
		shapeless(recipeWriter, List.of(ModBlocks.VERDANT_WOOD.get()), List.of(1), RecipeCategory.BUILDING_BLOCKS,
				ModBlocks.VERDANT_PLANKS.get(), 4);
		shapeless(recipeWriter, List.of(ModBlocks.STRIPPED_VERDANT_WOOD.get()), List.of(1),
				RecipeCategory.BUILDING_BLOCKS, ModBlocks.VERDANT_PLANKS.get(), 4);
		shaped(recipeWriter, List.of("PP", "PP"), List.of('P'), List.of(ModBlocks.VERDANT_LOG.get()),
				RecipeCategory.BUILDING_BLOCKS, ModBlocks.VERDANT_WOOD.get(), 3);
		shaped(recipeWriter, List.of("PP", "PP", "PP"), List.of('P'), List.of(ModBlocks.VERDANT_PLANKS.get()),
				RecipeCategory.BUILDING_BLOCKS, ModBlocks.VERDANT_DOOR.get(), 3);
		shaped(recipeWriter, List.of("PPP", "PPP"), List.of('P'), List.of(ModBlocks.VERDANT_PLANKS.get()),
				RecipeCategory.BUILDING_BLOCKS, ModBlocks.VERDANT_TRAPDOOR.get(), 2);
		shaped(recipeWriter, List.of("P  ", "PP ", "PPP"), List.of('P'), List.of(ModBlocks.VERDANT_PLANKS.get()),
				RecipeCategory.BUILDING_BLOCKS, ModBlocks.VERDANT_STAIRS.get(), 4);
		shaped(recipeWriter, List.of("PPP"), List.of('P'), List.of(ModBlocks.VERDANT_PLANKS.get()),
				RecipeCategory.BUILDING_BLOCKS, ModBlocks.VERDANT_SLAB.get(), 6);
		shapeless(recipeWriter, List.of(ModBlocks.VERDANT_PLANKS.get()), List.of(1), RecipeCategory.BUILDING_BLOCKS,
				ModBlocks.VERDANT_BUTTON.get(), 1);
		shaped(recipeWriter, List.of("PSP", "PSP"), List.of('P', 'S'),
				List.of(ModBlocks.VERDANT_PLANKS.get(), Items.STICK), RecipeCategory.BUILDING_BLOCKS,
				ModBlocks.VERDANT_FENCE.get(), 3);
		shaped(recipeWriter, List.of("SPS", "SPS"), List.of('P', 'S'),
				List.of(ModBlocks.VERDANT_PLANKS.get(), Items.STICK), RecipeCategory.BUILDING_BLOCKS,
				ModBlocks.VERDANT_FENCE_GATE.get(), 1);
		shaped(recipeWriter, List.of("PP"), List.of('P'), List.of(ModBlocks.VERDANT_PLANKS.get()),
				RecipeCategory.BUILDING_BLOCKS, ModBlocks.VERDANT_PRESSURE_PLATE.get(), 1);
		shaped(recipeWriter, List.of("PPP", "PPP", " S "), List.of('P', 'S'),
				List.of(ModBlocks.VERDANT_PLANKS.get(), Items.STICK), RecipeCategory.BUILDING_BLOCKS,
				ModItems.VERDANT_SIGN.get(), 3);
		shaped(recipeWriter, List.of("C C", "PPP", "PPP"), List.of('P', 'C'),
				List.of(ModBlocks.STRIPPED_VERDANT_LOG.get(), Items.CHAIN), RecipeCategory.BUILDING_BLOCKS,
				ModItems.VERDANT_HANGING_SIGN.get(), 2);

		// Verdant heartwood furniture
		charcoalSmelting(recipeWriter, ModBlocks.VERDANT_HEARTWOOD_LOG.get());
		charcoalSmelting(recipeWriter, ModBlocks.STRIPPED_VERDANT_HEARTWOOD_LOG.get());
		charcoalSmelting(recipeWriter, ModBlocks.VERDANT_HEARTWOOD_WOOD.get());
		charcoalSmelting(recipeWriter, ModBlocks.STRIPPED_VERDANT_HEARTWOOD_WOOD.get());
		shapeless(recipeWriter, List.of(ModBlocks.VERDANT_HEARTWOOD_LOG.get()), List.of(1),
				RecipeCategory.BUILDING_BLOCKS, ModBlocks.VERDANT_HEARTWOOD_PLANKS.get(), 4);
		shapeless(recipeWriter, List.of(ModBlocks.STRIPPED_VERDANT_HEARTWOOD_LOG.get()), List.of(1),
				RecipeCategory.BUILDING_BLOCKS, ModBlocks.VERDANT_HEARTWOOD_PLANKS.get(), 4);
		shapeless(recipeWriter, List.of(ModBlocks.VERDANT_HEARTWOOD_WOOD.get()), List.of(1),
				RecipeCategory.BUILDING_BLOCKS, ModBlocks.VERDANT_HEARTWOOD_PLANKS.get(), 4);
		shapeless(recipeWriter, List.of(ModBlocks.STRIPPED_VERDANT_HEARTWOOD_WOOD.get()), List.of(1),
				RecipeCategory.BUILDING_BLOCKS, ModBlocks.VERDANT_HEARTWOOD_PLANKS.get(), 4);
		shaped(recipeWriter, List.of("PP", "PP"), List.of('P'), List.of(ModBlocks.VERDANT_HEARTWOOD_LOG.get()),
				RecipeCategory.BUILDING_BLOCKS, ModBlocks.VERDANT_HEARTWOOD_WOOD.get(), 3);
		shaped(recipeWriter, List.of("PP", "PP", "PP"), List.of('P'), List.of(ModBlocks.VERDANT_HEARTWOOD_PLANKS.get()),
				RecipeCategory.BUILDING_BLOCKS, ModBlocks.VERDANT_HEARTWOOD_DOOR.get(), 3);
		shaped(recipeWriter, List.of("PPP", "PPP"), List.of('P'), List.of(ModBlocks.VERDANT_HEARTWOOD_PLANKS.get()),
				RecipeCategory.BUILDING_BLOCKS, ModBlocks.VERDANT_HEARTWOOD_TRAPDOOR.get(), 2);
		shaped(recipeWriter, List.of("P  ", "PP ", "PPP"), List.of('P'),
				List.of(ModBlocks.VERDANT_HEARTWOOD_PLANKS.get()), RecipeCategory.BUILDING_BLOCKS,
				ModBlocks.VERDANT_HEARTWOOD_STAIRS.get(), 4);
		shaped(recipeWriter, List.of("PPP"), List.of('P'), List.of(ModBlocks.VERDANT_HEARTWOOD_PLANKS.get()),
				RecipeCategory.BUILDING_BLOCKS, ModBlocks.VERDANT_HEARTWOOD_SLAB.get(), 6);
		shapeless(recipeWriter, List.of(ModBlocks.VERDANT_HEARTWOOD_PLANKS.get()), List.of(1),
				RecipeCategory.BUILDING_BLOCKS, ModBlocks.VERDANT_HEARTWOOD_BUTTON.get(), 1);
		shaped(recipeWriter, List.of("PSP", "PSP"), List.of('P', 'S'),
				List.of(ModBlocks.VERDANT_HEARTWOOD_PLANKS.get(), Items.STICK), RecipeCategory.BUILDING_BLOCKS,
				ModBlocks.VERDANT_HEARTWOOD_FENCE.get(), 3);
		shaped(recipeWriter, List.of("SPS", "SPS"), List.of('P', 'S'),
				List.of(ModBlocks.VERDANT_HEARTWOOD_PLANKS.get(), Items.STICK), RecipeCategory.BUILDING_BLOCKS,
				ModBlocks.VERDANT_HEARTWOOD_FENCE_GATE.get(), 1);
		shaped(recipeWriter, List.of("PP"), List.of('P'), List.of(ModBlocks.VERDANT_HEARTWOOD_PLANKS.get()),
				RecipeCategory.BUILDING_BLOCKS, ModBlocks.VERDANT_HEARTWOOD_PRESSURE_PLATE.get(), 1);
		shaped(recipeWriter, List.of("PPP", "PPP", " S "), List.of('P', 'S'),
				List.of(ModBlocks.VERDANT_HEARTWOOD_PLANKS.get(), Items.STICK), RecipeCategory.BUILDING_BLOCKS,
				ModItems.VERDANT_HEARTWOOD_SIGN.get(), 3);
		shaped(recipeWriter, List.of("C C", "PPP", "PPP"), List.of('P', 'C'),
				List.of(ModBlocks.STRIPPED_VERDANT_HEARTWOOD_LOG.get(), Items.CHAIN), RecipeCategory.BUILDING_BLOCKS,
				ModItems.VERDANT_HEARTWOOD_HANGING_SIGN.get(), 2);

		// Roasting coffee
		foodCooking(recipeWriter, List.of(ModItems.COFFEE_BERRIES.get()), RecipeCategory.FOOD,
				ModItems.ROASTED_COFFEE.get(), 0.1f, 400, "roasted_coffee");
		
		// Poison arrow
		shapeless(recipeWriter, List.of(ModBlocks.POISON_IVY.get(), Items.ARROW), List.of(1, 1), RecipeCategory.COMBAT,
				ModItems.POISON_ARROW.get(), 1);
		// Arrow from thorn
		shaped(recipeWriter, List.of(" P", "S "), List.of('P', 'S'), List.of(ModItems.THORN.get(), Items.STICK),
				RecipeCategory.COMBAT, Items.ARROW, 1);
		// Rope
		shaped(recipeWriter, List.of("T", "T", "T"), List.of('T'), List.of(ModBlocks.VERDANT_TENDRIL.get()),
				RecipeCategory.BUILDING_BLOCKS, ModBlocks.ROPE.get(), 2);
		// Poison ivy block
		shaped(recipeWriter, List.of("PPP", "PSP", "PPP"), List.of('P', 'S'),
				List.of(ModBlocks.POISON_IVY.get(), ModBlocks.FRAME_BLOCK.get()), RecipeCategory.BUILDING_BLOCKS,
				ModBlocks.POISON_IVY_BLOCK.get(), 1);
		// Toxic ash block
		smelting(recipeWriter, List.of(ModBlocks.POISON_IVY_BLOCK.get()), RecipeCategory.MISC,
				ModBlocks.TOXIC_ASH_BLOCK.get(), 0.1f, 200, "toxic_ash_block_from_poison_ivy_block");
		// Toxic ash item
		shapeless(recipeWriter, List.of(ModBlocks.TOXIC_ASH_BLOCK.get()), List.of(1), RecipeCategory.TOOLS,
				ModItems.TOXIC_ASH.get(), 3);
		// Toxic ash bucket item
		shapeless(recipeWriter, List.of(ModItems.TOXIC_ASH.get(), Items.BUCKET), List.of(8, 1), RecipeCategory.TOOLS,
				ModItems.TOXIC_ASH_BUCKET.get(), 1);
		// Packed mud from verdant tendrils
		shapeless(recipeWriter, List.of(ModBlocks.VERDANT_TENDRIL.get(), Blocks.MUD), List.of(2, 1),
				RecipeCategory.TOOLS, Blocks.PACKED_MUD, 1);

		// Bucket item
		shapeless(recipeWriter, List.of(ModItems.TOXIC_ASH_BUCKET.get()), List.of(1), RecipeCategory.TOOLS,
				Items.BUCKET, 1);
		// Toxic ash bucket item
		shapeless(recipeWriter, List.of(ModItems.TOXIC_ASH_BUCKET.get(), Items.WATER_BUCKET), List.of(1, 1),
				RecipeCategory.TOOLS, ModItems.TOXIC_SOLUTION_BUCKET.get(), 1);
		// Thorn spikes item
		shaped(recipeWriter, List.of("TTT", "TTT", "KRK"), List.of('T', 'K', 'R'),
				List.of(ModItems.THORN.get(), Items.STICK, Items.STRING), RecipeCategory.BUILDING_BLOCKS,
				ModBlocks.THORN_SPIKES.get(), 1);
		shaped(recipeWriter, List.of("TTT", "TTT", "KRK"), List.of('T', 'K', 'R'),
				List.of(ModItems.THORN.get(), Items.STICK, ModBlocks.ROPE.get()), RecipeCategory.BUILDING_BLOCKS,
				ModBlocks.THORN_SPIKES.get(), 1);
		// Frame block
		shaped(recipeWriter, List.of("TTT", "T T", "TTT"), List.of('T'), List.of(Items.STICK),
				RecipeCategory.BUILDING_BLOCKS, ModBlocks.FRAME_BLOCK.get(), 1);
		// Rope coil
		shaped(recipeWriter, List.of("TTT", "T T", "TTT"), List.of('T'), List.of(ModBlocks.ROPE.get()),
				RecipeCategory.BUILDING_BLOCKS, ModItems.SHORT_ROPE_COIL.get(), 1);
		shaped(recipeWriter, List.of("TTT", "TCT", "TTT"), List.of('T', 'C'),
				List.of(ModBlocks.ROPE.get(), ModItems.SHORT_ROPE_COIL.get()), RecipeCategory.BUILDING_BLOCKS,
				ModItems.ROPE_COIL.get(), 1);

		// Heartwood armor
		shaped(recipeWriter, List.of("PPP", "P P"), List.of('P'), List.of(ModBlocks.VERDANT_HEARTWOOD_LOG.get()),
				RecipeCategory.COMBAT, ModItems.VERDANT_HEARTWOOD_HELMET.get(), 1);
		shaped(recipeWriter, List.of("P P", "PPP", "PPP"), List.of('P'), List.of(ModBlocks.VERDANT_HEARTWOOD_LOG.get()),
				RecipeCategory.COMBAT, ModItems.VERDANT_HEARTWOOD_CHESTPLATE.get(), 1);
		shaped(recipeWriter, List.of("PPP", "P P", "P P"), List.of('P'), List.of(ModBlocks.VERDANT_HEARTWOOD_LOG.get()),
				RecipeCategory.COMBAT, ModItems.VERDANT_HEARTWOOD_LEGGINGS.get(), 1);
		shaped(recipeWriter, List.of("P P", "P P"), List.of('P'), List.of(ModBlocks.VERDANT_HEARTWOOD_LOG.get()),
				RecipeCategory.COMBAT, ModItems.VERDANT_HEARTWOOD_BOOTS.get(), 1);

		// Heartwood tools
		shaped(recipeWriter, List.of("P", "P", "S"), List.of('P', 'S'),
				List.of(ModBlocks.VERDANT_HEARTWOOD_LOG.get(), Items.STICK), RecipeCategory.COMBAT,
				ModItems.VERDANT_HEARTWOOD_SWORD.get(), 1);
		shaped(recipeWriter, List.of("PPP", " S ", " S "), List.of('P', 'S'),
				List.of(ModBlocks.VERDANT_HEARTWOOD_LOG.get(), Items.STICK), RecipeCategory.TOOLS,
				ModItems.VERDANT_HEARTWOOD_PICKAXE.get(), 1);
		shaped(recipeWriter, List.of("PP", "PS", " S"), List.of('P', 'S'),
				List.of(ModBlocks.VERDANT_HEARTWOOD_LOG.get(), Items.STICK), RecipeCategory.TOOLS,
				ModItems.VERDANT_HEARTWOOD_AXE.get(), 1);
		shaped(recipeWriter, List.of("PP", " S", " S"), List.of('P', 'S'),
				List.of(ModBlocks.VERDANT_HEARTWOOD_LOG.get(), Items.STICK), RecipeCategory.TOOLS,
				ModItems.VERDANT_HEARTWOOD_HOE.get(), 1);
		shaped(recipeWriter, List.of("P", "S", "S"), List.of('P', 'S'),
				List.of(ModBlocks.VERDANT_HEARTWOOD_LOG.get(), Items.STICK), RecipeCategory.TOOLS,
				ModItems.VERDANT_HEARTWOOD_SHOVEL.get(), 1);

		// Verdant conduit
		shaped(recipeWriter, List.of("SSS", "SPS", "SSS"), List.of('P', 'S'),
				List.of(ModItems.HEART_OF_THE_FOREST.get(), BlockTags.LOGS_THAT_BURN), RecipeCategory.MISC,
				ModBlocks.VERDANT_CONDUIT.get(), 1);

		// Roasting cassava
		cooking(recipeWriter, RecipeSerializer.SMELTING_RECIPE, List.of(ModItems.BITTER_CASSAVA.get()),
				RecipeCategory.FOOD, ModItems.CASSAVA.get(), 0.1f, 200, "cleaning_cassava",
				"_from_smelting_bitter_cassava");

	}

	// Stonecutting recipe.
	@SuppressWarnings("unchecked")
	protected static void brewing(Consumer<FinishedRecipe> finishedRecipeConsumer, Object ingredient,
			RecipeCategory recipeCategory, ItemLike result, int count) {

		// The name of the recipe.
		String recipeName = Verdant.MOD_ID + ":" + getItemName(result) + "_from_brewing_";

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

		// Prints out the result.
		System.out.println("Finished stonecutting recipe, saving with name: " + recipeName);
		recipe.save(finishedRecipeConsumer, recipeName);
	}

	// Stonecutting recipe.
	@SuppressWarnings("unchecked")
	protected static void stonecutting(Consumer<FinishedRecipe> finishedRecipeConsumer, Object ingredient,
			RecipeCategory recipeCategory, ItemLike result, int count) {

		// The name of the recipe.
		String recipeName = Verdant.MOD_ID + ":" + getItemName(result) + "_from_stonecutting_";

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

		// Prints out the result.
		System.out.println("Finished stonecutting recipe, saving with name: " + recipeName);
		recipe.save(finishedRecipeConsumer, recipeName);
	}

	// Shapeless recipe. Item at i must correspond to item count at i.
	@SuppressWarnings("unchecked")
	protected static void shapeless(Consumer<FinishedRecipe> finishedRecipeConsumer, List<Object> ingredients,
			List<Integer> counts, RecipeCategory recipeCategory, ItemLike result, int count) {

		ShapelessRecipeBuilder recipe = ShapelessRecipeBuilder.shapeless(recipeCategory, result, count);
		String recipeName = Verdant.MOD_ID + ":" + getItemName(result) + "_from";

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

		String recipeName = Verdant.MOD_ID + ":" + getItemName(result) + "_from_smithing_transform_of_"
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
		String recipeName = Verdant.MOD_ID + ":" + getItemName(result) + "_from";
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
			RecipeCategory category, ItemLike result, float experience, int cookingTime, String group) {
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
							Verdant.MOD_ID + ":" + getItemName(result) + recipeName + "_" + getItemName(itemlike));
		}
	}
}
