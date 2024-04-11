package com.thomas.cloudscape.datagen;

import java.util.List;
import java.util.function.Consumer;

import com.thomas.cloudscape.ZirconMod;
import com.thomas.cloudscape.block.ModBlocks;
import com.thomas.cloudscape.item.ModItems;
import com.thomas.cloudscape.util.ModTags;

import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.data.recipes.SmithingTransformRecipeBuilder;
import net.minecraft.tags.ItemTags;
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
	private static final List<ItemLike> ZIRCON_SMELTABLES = List.of(ModBlocks.ZIRCON_ORE.get(),
			ModBlocks.DEEPSLATE_ZIRCON_ORE.get());

	public ModRecipeProvider(PackOutput pOutput) {
		super(pOutput);
	}

	@Override
	protected void buildRecipes(Consumer<FinishedRecipe> pWriter) {
		// Smelting zircon ores to zircon
		oreSmelting(pWriter, ZIRCON_SMELTABLES, RecipeCategory.MISC, ModItems.ZIRCON.get(), 0.25f, 200, "zircon");
		oreBlasting(pWriter, ZIRCON_SMELTABLES, RecipeCategory.MISC, ModItems.ZIRCON.get(), 0.25f, 100, "zircon");

		// Smelting zircon block to raw zirconium
		oreSmelting(pWriter, List.of(ModBlocks.ZIRCON_BLOCK.get()), RecipeCategory.MISC, ModItems.RAW_ZIRCONIUM.get(),
				0.25f, 200, "raw_zirconium");
		oreBlasting(pWriter, List.of(ModBlocks.ZIRCON_BLOCK.get()), RecipeCategory.MISC, ModItems.RAW_ZIRCONIUM.get(),
				0.25f, 100, "raw_zirconium");

		// Charcoal from palm loggish things.
		charcoalSmelting(pWriter, ModBlocks.PALM_LOG.get());
		charcoalSmelting(pWriter, ModBlocks.STRIPPED_PALM_LOG.get());
		charcoalSmelting(pWriter, ModBlocks.PALM_WOOD.get());
		charcoalSmelting(pWriter, ModBlocks.STRIPPED_PALM_WOOD.get());

		// Smelting raw zirconium to zirconium
		oreSmelting(pWriter, List.of(ModItems.RAW_ZIRCONIUM.get()), RecipeCategory.MISC, ModItems.ZIRCONIUM_INGOT.get(),
				0.25f, 200, "zirconium_ingot");
		oreBlasting(pWriter, List.of(ModItems.RAW_ZIRCONIUM.get()), RecipeCategory.MISC, ModItems.ZIRCONIUM_INGOT.get(),
				0.25f, 100, "zirconium_ingot");

		// Zircon block from zircons.
		shaped(pWriter, List.of("ZZZ", "ZZZ", "ZZZ"), List.of('Z'), List.of(ModItems.ZIRCON.get()),
				RecipeCategory.BUILDING_BLOCKS, ModBlocks.ZIRCON_BLOCK.get(), 1);

		// Zircon from zircon shard.
		shaped(pWriter, List.of("ZZ", "ZZ"), List.of('Z'), List.of(ModItems.ZIRCON_SHARD.get()),
				RecipeCategory.BUILDING_BLOCKS, ModItems.ZIRCON.get(), 1);

		// Red sand from sand and zircon shard.
		shaped(pWriter, List.of("ZS", "SZ"), List.of('Z', 'S'), List.of(ModItems.ZIRCON_SHARD.get(), Items.SAND),
				RecipeCategory.BUILDING_BLOCKS, Items.RED_SAND, 2);

		// Blast furnace from furnace and zirconium.
		shaped(pWriter, List.of(" Z ", "SFS", " S "), List.of('Z', 'F', 'S'),
				List.of(ModItems.ZIRCONIUM_INGOT.get(), Items.FURNACE, Items.SMOOTH_STONE),
				RecipeCategory.BUILDING_BLOCKS, Items.BLAST_FURNACE, 2);

		// Zircon lamp.
		shaped(pWriter, List.of("SSS", "R#R", "SSS"), List.of('S', 'R', '#'),
				List.of(Items.STICK, Items.REDSTONE, ModBlocks.ZIRCON_BLOCK.get()), RecipeCategory.BUILDING_BLOCKS,
				ModBlocks.ZIRCON_LAMP.get(), 1);

		// Zircon from zircon block
		shapeless(pWriter, List.of(ModBlocks.ZIRCON_BLOCK.get()), List.of(1), RecipeCategory.MISC,
				ModItems.ZIRCON.get(), 9);

		// Flaming pine cones.
		shaped(pWriter, List.of("PPP", "PBP", "PPP"), List.of('P', 'B'),
				List.of(ModItems.PINE_CONE.get(), Items.BLAZE_POWDER), RecipeCategory.MISC,
				ModItems.FLAMING_PINE_CONE.get(), 1);

		// Flaming arrows.
		shaped(pWriter, List.of("FAF", "ASA", "FAF"), List.of('F', 'A', 'S'),
				List.of(ModItems.FLAMING_PINE_CONE.get(), Items.ARROW, Items.STRING), RecipeCategory.COMBAT,
				ModItems.FLAMING_ARROW.get(), 1);

		// Hailstones from ice (4x)
		shapeless(pWriter, List.of(Blocks.ICE), List.of(1), RecipeCategory.COMBAT, ModItems.HAILSTONE.get(), 4);

		// Hailstones from snowballs and ice (8x)
		shaped(pWriter, List.of("SSS", "SIS", "SSS"), List.of('S', 'I'), List.of(Items.SNOWBALL, Blocks.ICE),
				RecipeCategory.COMBAT, ModItems.HAILSTONE.get(), 1);

		// String from wool
		shapeless(pWriter, List.of(Blocks.WHITE_WOOL), List.of(1), RecipeCategory.MISC, Items.STRING, 3);

		// Spruce sapling from pine cones.
		shaped(pWriter, List.of("PP", "PP"), List.of('P'), List.of(ModItems.PINE_CONE.get()), RecipeCategory.MISC,
				Items.SPRUCE_SAPLING, 1);

		// Palm sapling from palm seeds.
		shaped(pWriter, List.of("PP", "PP"), List.of('P'), List.of(ModItems.PALM_SEEDS.get()), RecipeCategory.MISC,
				ModBlocks.PALM_SAPLING.get(), 1);

		// Sculk catalyst.
		shaped(pWriter, List.of("111", "333", "242"), List.of('1', '2', '3', '4'),
				List.of(Items.ECHO_SHARD, Items.BONE_BLOCK, Blocks.SCULK, Blocks.SOUL_SOIL), RecipeCategory.DECORATIONS,
				Blocks.SCULK_CATALYST, 1);

		// Sticks from saplings.
		shaped(pWriter, List.of("S", "S"), List.of('S'), List.of(ItemTags.SAPLINGS), RecipeCategory.MISC, Items.STICK,
				1);

		// Rooted dirt from roots and dirt.
		shaped(pWriter, List.of("DR", "RD"), List.of('R', 'D'), List.of(Items.HANGING_ROOTS, Items.DIRT),
				RecipeCategory.BUILDING_BLOCKS, Blocks.ROOTED_DIRT, 1);

		// Elytra
		shaped(pWriter, List.of("MHM", "MDM", "M M"), List.of('M', 'D', 'H'),
				List.of(Items.PHANTOM_MEMBRANE, Items.DIAMOND, ModItems.HEART_OF_THE_SKY.get()),
				RecipeCategory.TRANSPORTATION, Items.ELYTRA, 1);

		// Trident
		shaped(pWriter, List.of(" SD", " HS", "T  "), List.of('S', 'D', 'H', 'T'),
				List.of(Items.PRISMARINE_SHARD, Items.DIAMOND, Items.HEART_OF_THE_SEA, Items.STICK),
				RecipeCategory.COMBAT, Items.TRIDENT, 1);

		// Raw zirconium from raw zirconium block
		shapeless(pWriter, List.of(ModBlocks.ZIRCONIUM_BLOCK.get()), List.of(1), RecipeCategory.MISC,
				ModItems.RAW_ZIRCONIUM.get(), 9);

		// Raw zirconium block from raw zirconium.
		shaped(pWriter, List.of("ZZZ", "ZZZ", "ZZZ"), List.of('Z'), List.of(ModItems.RAW_ZIRCONIUM.get()),
				RecipeCategory.BUILDING_BLOCKS, ModBlocks.RAW_ZIRCONIUM_BLOCK.get(), 1);

		// Moss carpet from mossy cobblestone.
		shaped(pWriter, List.of("MM", "MM"), List.of('M'), List.of(Blocks.MOSSY_COBBLESTONE),
				RecipeCategory.BUILDING_BLOCKS, Blocks.MOSS_CARPET, 2);

		// Moss from moss carpet.
		shaped(pWriter, List.of("C", "C", "C"), List.of('C'), List.of(Blocks.MOSS_CARPET),
				RecipeCategory.BUILDING_BLOCKS, Blocks.MOSS_BLOCK, 1);

		// Zirconium ingot from zirconium block
		shapeless(pWriter, List.of(ModBlocks.ZIRCONIUM_BLOCK.get()), List.of(1), RecipeCategory.MISC,
				ModItems.ZIRCONIUM_INGOT.get(), 9);

		// Zirconium block from zirconium ingot.
		shaped(pWriter, List.of("ZZZ", "ZZZ", "ZZZ"), List.of('Z'), List.of(ModItems.ZIRCONIUM_INGOT.get()),
				RecipeCategory.BUILDING_BLOCKS, ModBlocks.ZIRCONIUM_BLOCK.get(), 1);

		// Deepslate to polished deepslate.
		shaped(pWriter, List.of("DD", "DD"), List.of('D'), List.of(Blocks.DEEPSLATE), RecipeCategory.BUILDING_BLOCKS,
				Blocks.POLISHED_DEEPSLATE, 4);

		// Berry pie
		shapeless(pWriter, List.of(ModTags.Items.BERRIES, Items.SUGAR, Items.EGG), List.of(3, 1, 1),
				RecipeCategory.MISC, ModItems.BERRY_PIE.get(), 1);

		// Feather bed.
		shaped(pWriter, List.of("FFF", "FFF", "PPP"), List.of('F', 'P'), List.of(Items.FEATHER, ItemTags.PLANKS),
				RecipeCategory.BUILDING_BLOCKS, Blocks.WHITE_BED, 1);

		// Campfire from pine cones.
		shaped(pWriter, List.of("CCC", "CCC", "PPP"), List.of('C', 'P'),
				List.of(ModItems.PINE_CONE.get(), ItemTags.LOGS_THAT_BURN), RecipeCategory.BUILDING_BLOCKS,
				Blocks.CAMPFIRE, 1);

		// Cobweb from honey.
		shaped(pWriter, List.of("SSS", "SHS", "SSS"), List.of('S', 'H'), List.of(Items.STRING, Items.HONEY_BOTTLE),
				RecipeCategory.BUILDING_BLOCKS, Blocks.COBWEB, 1);

		// Cobweb from slime.
		shaped(pWriter, List.of("SSS", "SHS", "SSS"), List.of('S', 'H'), List.of(Items.STRING, Items.SLIME_BALL),
				RecipeCategory.BUILDING_BLOCKS, Blocks.COBWEB, 1);

		// Zirconium equipment
		smithingTransform(pWriter, ModItems.ZIRCONIUM_UPGRADE_SMITHING_TEMPLATE.get(), Items.IRON_AXE,
				ModItems.ZIRCONIUM_INGOT.get(), RecipeCategory.TOOLS, ModItems.ZIRCONIUM_AXE.get());
		smithingTransform(pWriter, ModItems.ZIRCONIUM_UPGRADE_SMITHING_TEMPLATE.get(), Items.IRON_HOE,
				ModItems.ZIRCONIUM_INGOT.get(), RecipeCategory.TOOLS, ModItems.ZIRCONIUM_HOE.get());
		smithingTransform(pWriter, ModItems.ZIRCONIUM_UPGRADE_SMITHING_TEMPLATE.get(), Items.IRON_PICKAXE,
				ModItems.ZIRCONIUM_INGOT.get(), RecipeCategory.TOOLS, ModItems.ZIRCONIUM_PICKAXE.get());
		smithingTransform(pWriter, ModItems.ZIRCONIUM_UPGRADE_SMITHING_TEMPLATE.get(), Items.IRON_SHOVEL,
				ModItems.ZIRCONIUM_INGOT.get(), RecipeCategory.TOOLS, ModItems.ZIRCONIUM_SHOVEL.get());
		smithingTransform(pWriter, ModItems.ZIRCONIUM_UPGRADE_SMITHING_TEMPLATE.get(), Items.IRON_SWORD,
				ModItems.ZIRCONIUM_INGOT.get(), RecipeCategory.COMBAT, ModItems.ZIRCONIUM_SWORD.get());
		smithingTransform(pWriter, ModItems.ZIRCONIUM_UPGRADE_SMITHING_TEMPLATE.get(), Items.IRON_HELMET,
				ModItems.ZIRCONIUM_INGOT.get(), RecipeCategory.COMBAT, ModItems.ZIRCONIUM_HELMET.get());
		smithingTransform(pWriter, ModItems.ZIRCONIUM_UPGRADE_SMITHING_TEMPLATE.get(), Items.IRON_CHESTPLATE,
				ModItems.ZIRCONIUM_INGOT.get(), RecipeCategory.COMBAT, ModItems.ZIRCONIUM_CHESTPLATE.get());
		smithingTransform(pWriter, ModItems.ZIRCONIUM_UPGRADE_SMITHING_TEMPLATE.get(), Items.IRON_LEGGINGS,
				ModItems.ZIRCONIUM_INGOT.get(), RecipeCategory.COMBAT, ModItems.ZIRCONIUM_LEGGINGS.get());
		smithingTransform(pWriter, ModItems.ZIRCONIUM_UPGRADE_SMITHING_TEMPLATE.get(), Items.IRON_BOOTS,
				ModItems.ZIRCONIUM_INGOT.get(), RecipeCategory.COMBAT, ModItems.ZIRCONIUM_BOOTS.get());

		// Copper equipment
		shaped(pWriter, List.of("CC", "CS", " S"), List.of('C', 'S'), List.of(Items.COPPER_INGOT, Items.STICK),
				RecipeCategory.TOOLS, ModItems.COPPER_AXE.get(), 1);
		shaped(pWriter, List.of("CC", " S", " S"), List.of('C', 'S'), List.of(Items.COPPER_INGOT, Items.STICK),
				RecipeCategory.TOOLS, ModItems.COPPER_HOE.get(), 1);
		shaped(pWriter, List.of("CCC", " S ", " S "), List.of('C', 'S'), List.of(Items.COPPER_INGOT, Items.STICK),
				RecipeCategory.TOOLS, ModItems.COPPER_PICKAXE.get(), 1);
		shaped(pWriter, List.of("C", "S", "S"), List.of('C', 'S'), List.of(Items.COPPER_INGOT, Items.STICK),
				RecipeCategory.TOOLS, ModItems.COPPER_SHOVEL.get(), 1);
		shaped(pWriter, List.of("C", "C", "S"), List.of('C', 'S'), List.of(Items.COPPER_INGOT, Items.STICK),
				RecipeCategory.COMBAT, ModItems.COPPER_SWORD.get(), 1);
		shaped(pWriter, List.of("CCC", "C C"), List.of('C'), List.of(Items.COPPER_INGOT), RecipeCategory.COMBAT,
				ModItems.COPPER_HELMET.get(), 1);
		shaped(pWriter, List.of("C C", "CCC", "CCC"), List.of('C'), List.of(Items.COPPER_INGOT), RecipeCategory.COMBAT,
				ModItems.COPPER_CHESTPLATE.get(), 1);
		shaped(pWriter, List.of("CCC", "C C", "C C"), List.of('C'), List.of(Items.COPPER_INGOT), RecipeCategory.COMBAT,
				ModItems.COPPER_LEGGINGS.get(), 1);
		shaped(pWriter, List.of("C C", "C C"), List.of('C'), List.of(Items.COPPER_INGOT), RecipeCategory.COMBAT,
				ModItems.COPPER_BOOTS.get(), 1);

		// Netherite Anvil
		smithingTransform(pWriter, Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE, Items.ANVIL, Items.NETHERITE_INGOT,
				RecipeCategory.TOOLS, ModBlocks.NETHERITE_ANVIL.get().asItem());
		// Repairing Anvil
		smithingTransform(pWriter, Items.AIR, Items.DAMAGED_ANVIL, Items.IRON_BLOCK, RecipeCategory.TOOLS,
				Items.CHIPPED_ANVIL);
		smithingTransform(pWriter, Items.AIR, Items.CHIPPED_ANVIL, Items.IRON_BLOCK, RecipeCategory.TOOLS, Items.ANVIL);

		// Horse armor
		shaped(pWriter, List.of("B  ", "BBB", "B B"), List.of('B'), List.of(Items.IRON_BLOCK), RecipeCategory.COMBAT,
				Items.IRON_HORSE_ARMOR, 1);
		shaped(pWriter, List.of("B  ", "BBB", "B B"), List.of('B'), List.of(Items.GOLD_BLOCK), RecipeCategory.COMBAT,
				Items.GOLDEN_HORSE_ARMOR, 1);
		shaped(pWriter, List.of("B  ", "BBB", "B B"), List.of('B'), List.of(Items.DIAMOND_BLOCK), RecipeCategory.COMBAT,
				Items.DIAMOND_HORSE_ARMOR, 1);

		// Zirconium smithing template.
		shaped(pWriter, List.of("#S#", "#L#", "###"), List.of('#', 'S', 'L'),
				List.of(Items.IRON_INGOT, Items.LAPIS_LAZULI, Items.STONE), RecipeCategory.MISC,
				ModItems.ZIRCONIUM_UPGRADE_SMITHING_TEMPLATE.get(), 2);

		// Illuminated torchflower.
		shapeless(pWriter, List.of(Blocks.TORCHFLOWER, Items.GLOW_INK_SAC), List.of(1, 1), RecipeCategory.DECORATIONS,
				ModBlocks.ILLUMINATED_TORCHFLOWER.get(), 1);

		// Obsidian.
		shapeless(pWriter, List.of(Items.WATER_BUCKET, Items.LAVA_BUCKET), List.of(1, 1), RecipeCategory.DECORATIONS,
				Blocks.OBSIDIAN, 1);

		// Carpentry table
		shaped(pWriter, List.of(" C ", "L#L", "LIL"), List.of('I', 'L', '#', 'C'),
				List.of(Items.IRON_INGOT, ItemTags.LOGS, Items.CRAFTING_TABLE, Items.COPPER_INGOT),
				RecipeCategory.BUILDING_BLOCKS, ModBlocks.CARPENTRY_TABLE.get(), 1);

		// Feather wings
		shaped(pWriter, List.of("FBF", "PEP", "FBF"), List.of('F', 'B', 'P', 'E'),
				List.of(Items.FEATHER, Items.HONEYCOMB, ItemTags.PLANKS, Items.ELYTRA), RecipeCategory.TRANSPORTATION,
				ModItems.FEATHER_WINGS.get(), 1);

		// Tempest bottle
		shapeless(pWriter, List.of(ModItems.GUST_BOTTLE.get(), ModItems.HEART_OF_THE_SKY.get()), List.of(2, 1),
				RecipeCategory.TOOLS, ModItems.TEMPEST_BOTTLE.get(), 1);

		// Empty wind bag
		shaped(pWriter, List.of("GRG", "R R", "RRR"), List.of('G', 'R'), List.of(Items.GOLD_INGOT, Items.RABBIT_HIDE),
				RecipeCategory.TOOLS, ModItems.EMPTY_WIND_BAG.get(), 1);

		// Filled wind bag
		shaped(pWriter, List.of("GGG", "GBG", "GGG"), List.of('G', 'B'),
				List.of(ModItems.GUST_BOTTLE.get(), ModItems.EMPTY_WIND_BAG.get()), RecipeCategory.TOOLS,
				ModItems.WIND_BAG.get(), 1);

		// Palm furniture
		shaped(pWriter, List.of("PP", "PP", "PP"), List.of('P'), List.of(ModBlocks.PALM_PLANKS.get()),
				RecipeCategory.BUILDING_BLOCKS, ModBlocks.PALM_DOOR.get(), 3);
		shaped(pWriter, List.of("PPP", "PPP"), List.of('P'), List.of(ModBlocks.PALM_PLANKS.get()),
				RecipeCategory.BUILDING_BLOCKS, ModBlocks.PALM_TRAPDOOR.get(), 2);
		shaped(pWriter, List.of("P  ", "PP ", "PPP"), List.of('P'), List.of(ModBlocks.PALM_PLANKS.get()),
				RecipeCategory.BUILDING_BLOCKS, ModBlocks.PALM_STAIRS.get(), 4);
		shapeless(pWriter, List.of(ModBlocks.PALM_PLANKS.get()), List.of(1), RecipeCategory.BUILDING_BLOCKS,
				ModBlocks.PALM_BUTTON.get(), 1);
		shaped(pWriter, List.of("PSP", "PSP"), List.of('P', 'S'), List.of(ModBlocks.PALM_PLANKS.get(), Items.STICK),
				RecipeCategory.BUILDING_BLOCKS, ModBlocks.PALM_FENCE.get(), 3);
		shaped(pWriter, List.of("SPS", "SPS"), List.of('P', 'S'), List.of(ModBlocks.PALM_PLANKS.get(), Items.STICK),
				RecipeCategory.BUILDING_BLOCKS, ModBlocks.PALM_FENCE_GATE.get(), 1);
		shaped(pWriter, List.of("PP"), List.of('P'), List.of(ModBlocks.PALM_PLANKS.get()),
				RecipeCategory.BUILDING_BLOCKS, ModBlocks.PALM_PRESSURE_PLATE.get(), 1);
		shaped(pWriter, List.of("PPP", "PPP", " S "), List.of('P', 'S'),
				List.of(ModBlocks.PALM_PLANKS.get(), Items.STICK), RecipeCategory.BUILDING_BLOCKS,
				ModItems.PALM_SIGN.get(), 3);
		shaped(pWriter, List.of("C C", "PPP", "PPP"), List.of('P', 'C'),
				List.of(ModBlocks.STRIPPED_PALM_LOG.get(), Items.CHAIN), RecipeCategory.BUILDING_BLOCKS,
				ModItems.PALM_HANGING_SIGN.get(), 2);

		// Cloud brick furniture
		shaped(pWriter, List.of("CCC", "CTC", "CCC"), List.of('C', 'T'),
				List.of(ModBlocks.CLOUD.get(), ModItems.CITRINE_SHARD.get()), RecipeCategory.BUILDING_BLOCKS,
				ModBlocks.CLOUD_BRICKS.get(), 4);
		shaped(pWriter, List.of("CCC", "CTC", "CCC"), List.of('C', 'T'),
				List.of(ModBlocks.THUNDER_CLOUD.get(), ModItems.CITRINE_SHARD.get()), RecipeCategory.BUILDING_BLOCKS,
				ModBlocks.THUNDER_CLOUD_BRICKS.get(), 4);
		shaped(pWriter, List.of("P  ", "PP ", "PPP"), List.of('P'), List.of(ModBlocks.CLOUD_BRICKS.get()),
				RecipeCategory.BUILDING_BLOCKS, ModBlocks.CLOUD_BRICK_STAIRS.get(), 4);
		shaped(pWriter, List.of("PPP"), List.of('P'), List.of(ModBlocks.CLOUD_BRICKS.get()),
				RecipeCategory.BUILDING_BLOCKS, ModBlocks.CLOUD_BRICK_SLAB.get(), 6);
		shaped(pWriter, List.of("PPP", "PPP"), List.of('P'), List.of(ModBlocks.CLOUD_BRICKS.get()),
				RecipeCategory.BUILDING_BLOCKS, ModBlocks.CLOUD_BRICK_WALL.get(), 3);

		shaped(pWriter, List.of("P  ", "PP ", "PPP"), List.of('P'), List.of(ModBlocks.THUNDER_CLOUD_BRICKS.get()),
				RecipeCategory.BUILDING_BLOCKS, ModBlocks.THUNDER_CLOUD_BRICK_STAIRS.get(), 4);
		shaped(pWriter, List.of("PPP"), List.of('P'), List.of(ModBlocks.THUNDER_CLOUD_BRICKS.get()),
				RecipeCategory.BUILDING_BLOCKS, ModBlocks.THUNDER_CLOUD_BRICK_SLAB.get(), 6);
		shaped(pWriter, List.of("PPP", "PPP"), List.of('P'), List.of(ModBlocks.THUNDER_CLOUD_BRICKS.get()),
				RecipeCategory.BUILDING_BLOCKS, ModBlocks.THUNDER_CLOUD_BRICK_WALL.get(), 3);

		// Cloud converter
		shaped(pWriter, List.of("RCR", "PTP", "RCR"), List.of('C', 'T', 'R', 'P'),
				List.of(ModBlocks.CLOUD_BRICKS.get(), ModItems.CUT_CITRINE.get(), Items.REDSTONE, Items.COPPER_BLOCK),
				RecipeCategory.BUILDING_BLOCKS, ModBlocks.CLOUD_CONVERTER.get(), 2);

		// Cloud inverter
		shaped(pWriter, List.of("PRC", "RTR", "CRP"), List.of('C', 'T', 'R', 'P'),
				List.of(ModBlocks.CLOUD_BRICKS.get(), ModItems.CUT_CITRINE.get(), Items.REDSTONE, Items.COPPER_BLOCK),
				RecipeCategory.BUILDING_BLOCKS, ModBlocks.CLOUD_INVERTER.get(), 2);

		// Cloud detector
		shaped(pWriter, List.of("PCR", "QTQ", "RCP"), List.of('C', 'T', 'R', 'Q', 'P'),
				List.of(ModBlocks.CLOUD_BRICKS.get(), ModBlocks.THUNDER_CLOUD.get(), Items.REDSTONE, Items.QUARTZ, Items.COPPER_BLOCK),
				RecipeCategory.BUILDING_BLOCKS, ModBlocks.CLOUD_DETECTOR.get(), 1);

		// Smelting amethyst blocks to citrine
		oreSmelting(pWriter, List.of(Blocks.AMETHYST_BLOCK), RecipeCategory.BUILDING_BLOCKS,
				ModBlocks.CITRINE_BLOCK.get(), 0.25f, 200, "citrine_block");
		oreSmelting(pWriter, List.of(Blocks.AMETHYST_CLUSTER), RecipeCategory.BUILDING_BLOCKS,
				ModBlocks.CITRINE_CLUSTER.get(), 0.25f, 200, "citrine_cluster");
		oreSmelting(pWriter, List.of(Blocks.LARGE_AMETHYST_BUD), RecipeCategory.BUILDING_BLOCKS,
				ModBlocks.LARGE_CITRINE_BUD.get(), 0.25f, 200, "large_citrine_bud");
		oreSmelting(pWriter, List.of(Blocks.MEDIUM_AMETHYST_BUD), RecipeCategory.BUILDING_BLOCKS,
				ModBlocks.MEDIUM_CITRINE_BUD.get(), 0.25f, 200, "medium_citrine_bud");
		oreSmelting(pWriter, List.of(Blocks.SMALL_AMETHYST_BUD), RecipeCategory.BUILDING_BLOCKS,
				ModBlocks.SMALL_CITRINE_BUD.get(), 0.25f, 200, "small_citrine_bud");
		oreSmelting(pWriter, List.of(Items.AMETHYST_SHARD), RecipeCategory.BUILDING_BLOCKS,
				ModItems.CITRINE_SHARD.get(), 0.25f, 200, "citrine_shard");
		oreSmelting(pWriter, List.of(Items.BUDDING_AMETHYST), RecipeCategory.BUILDING_BLOCKS,
				ModBlocks.BUDDING_CITRINE.get(), 0.25f, 200, "budding_citrine");

		// Resonator
		shaped(pWriter, List.of("SSS", "QER", "SSS"), List.of('S', 'E', 'Q', 'R'),
				List.of(Items.DEEPSLATE_BRICKS, ModBlocks.ECHO_BLOCK.get(), Items.QUARTZ, Items.REDSTONE),
				RecipeCategory.MISC, ModBlocks.RESONATOR_BLOCK.get(), 1);

		// Ore and material stairs
		shaped(pWriter, List.of("P  ", "PP ", "PPP"), List.of('P'), List.of(Items.COAL), RecipeCategory.BUILDING_BLOCKS,
				ModBlocks.COAL_STAIRS.get(), 1);
		shaped(pWriter, List.of("P  ", "PP ", "PPP"), List.of('P'), List.of(Items.CHARCOAL),
				RecipeCategory.BUILDING_BLOCKS, ModBlocks.CHARCOAL_STAIRS.get(), 1);
		shaped(pWriter, List.of("P  ", "PP ", "PPP"), List.of('P'), List.of(Blocks.AMETHYST_BLOCK),
				RecipeCategory.BUILDING_BLOCKS, ModBlocks.AMETHYST_STAIRS.get(), 4);
		shaped(pWriter, List.of("P  ", "PP ", "PPP"), List.of('P'), List.of(Items.RAW_COPPER),
				RecipeCategory.BUILDING_BLOCKS, ModBlocks.RAW_COPPER_STAIRS.get(), 1);
		shaped(pWriter, List.of("P  ", "PP ", "PPP"), List.of('P'), List.of(Items.RAW_IRON),
				RecipeCategory.BUILDING_BLOCKS, ModBlocks.RAW_IRON_STAIRS.get(), 1);
		shaped(pWriter, List.of("P  ", "PP ", "PPP"), List.of('P'), List.of(Items.IRON_INGOT),
				RecipeCategory.BUILDING_BLOCKS, ModBlocks.IRON_STAIRS.get(), 1);
		shaped(pWriter, List.of("P  ", "PP ", "PPP"), List.of('P'), List.of(Items.RAW_GOLD),
				RecipeCategory.BUILDING_BLOCKS, ModBlocks.RAW_GOLD_STAIRS.get(), 1);
		shaped(pWriter, List.of("P  ", "PP ", "PPP"), List.of('P'), List.of(Items.GOLD_INGOT),
				RecipeCategory.BUILDING_BLOCKS, ModBlocks.GOLD_STAIRS.get(), 1);
		shaped(pWriter, List.of("P  ", "PP ", "PPP"), List.of('P'), List.of(Items.REDSTONE),
				RecipeCategory.BUILDING_BLOCKS, ModBlocks.REDSTONE_STAIRS.get(), 1);
		shaped(pWriter, List.of("P  ", "PP ", "PPP"), List.of('P'), List.of(Items.LAPIS_LAZULI),
				RecipeCategory.BUILDING_BLOCKS, ModBlocks.LAPIS_STAIRS.get(), 1);
		shaped(pWriter, List.of("P  ", "PP ", "PPP"), List.of('P'), List.of(Items.EMERALD),
				RecipeCategory.BUILDING_BLOCKS, ModBlocks.EMERALD_STAIRS.get(), 1);
		shaped(pWriter, List.of("P  ", "PP ", "PPP"), List.of('P'), List.of(Items.DIAMOND),
				RecipeCategory.BUILDING_BLOCKS, ModBlocks.DIAMOND_STAIRS.get(), 1);
		shaped(pWriter, List.of("P  ", "PP ", "PPP"), List.of('P'), List.of(Items.NETHERITE_INGOT),
				RecipeCategory.BUILDING_BLOCKS, ModBlocks.NETHERITE_STAIRS.get(), 1);
		shaped(pWriter, List.of("P  ", "PP ", "PPP"), List.of('P'), List.of(Blocks.OBSIDIAN),
				RecipeCategory.BUILDING_BLOCKS, ModBlocks.OBSIDIAN_STAIRS.get(), 4);
		shaped(pWriter, List.of("P  ", "PP ", "PPP"), List.of('P'), List.of(Blocks.CRYING_OBSIDIAN),
				RecipeCategory.BUILDING_BLOCKS, ModBlocks.CRYING_OBSIDIAN_STAIRS.get(), 4);

		shaped(pWriter, List.of("P  ", "PP ", "PPP"), List.of('P'), List.of(ModItems.RAW_ZIRCONIUM.get()),
				RecipeCategory.BUILDING_BLOCKS, ModBlocks.RAW_ZIRCONIUM_STAIRS.get(), 1);
		shaped(pWriter, List.of("P  ", "PP ", "PPP"), List.of('P'), List.of(ModItems.ZIRCONIUM_INGOT.get()),
				RecipeCategory.BUILDING_BLOCKS, ModBlocks.ZIRCONIUM_STAIRS.get(), 1);
		shaped(pWriter, List.of("P  ", "PP ", "PPP"), List.of('P'), List.of(ModItems.ZIRCON.get()),
				RecipeCategory.BUILDING_BLOCKS, ModBlocks.ZIRCON_STAIRS.get(), 1);
		shaped(pWriter, List.of("P  ", "PP ", "PPP"), List.of('P'), List.of(ModBlocks.CITRINE_BLOCK.get()),
				RecipeCategory.BUILDING_BLOCKS, ModBlocks.CITRINE_STAIRS.get(), 4);

		// Uncrafting ore and material stairs
		shapeless(pWriter, List.of(ModBlocks.COAL_STAIRS.get()), List.of(1), RecipeCategory.BUILDING_BLOCKS, Items.COAL,
				6);
		shapeless(pWriter, List.of(ModBlocks.CHARCOAL_STAIRS.get()), List.of(1), RecipeCategory.BUILDING_BLOCKS,
				Items.CHARCOAL, 6);
		shapeless(pWriter, List.of(ModBlocks.RAW_COPPER_STAIRS.get()), List.of(1), RecipeCategory.BUILDING_BLOCKS,
				Items.RAW_COPPER, 6);
		shapeless(pWriter, List.of(ModBlocks.RAW_IRON_STAIRS.get()), List.of(1), RecipeCategory.BUILDING_BLOCKS,
				Items.RAW_IRON, 6);
		shapeless(pWriter, List.of(ModBlocks.IRON_STAIRS.get()), List.of(1), RecipeCategory.BUILDING_BLOCKS,
				Items.IRON_INGOT, 6);
		shapeless(pWriter, List.of(ModBlocks.RAW_GOLD_STAIRS.get()), List.of(1), RecipeCategory.BUILDING_BLOCKS,
				Items.RAW_GOLD, 6);
		shapeless(pWriter, List.of(ModBlocks.GOLD_STAIRS.get()), List.of(1), RecipeCategory.BUILDING_BLOCKS,
				Items.GOLD_INGOT, 6);
		shapeless(pWriter, List.of(ModBlocks.LAPIS_STAIRS.get()), List.of(1), RecipeCategory.BUILDING_BLOCKS,
				Items.LAPIS_LAZULI, 6);
		shapeless(pWriter, List.of(ModBlocks.REDSTONE_STAIRS.get()), List.of(1), RecipeCategory.BUILDING_BLOCKS,
				Items.REDSTONE, 6);
		shapeless(pWriter, List.of(ModBlocks.EMERALD_STAIRS.get()), List.of(1), RecipeCategory.BUILDING_BLOCKS,
				Items.EMERALD, 6);
		shapeless(pWriter, List.of(ModBlocks.DIAMOND_STAIRS.get()), List.of(1), RecipeCategory.BUILDING_BLOCKS,
				Items.DIAMOND, 6);
		shapeless(pWriter, List.of(ModBlocks.NETHERITE_STAIRS.get()), List.of(1), RecipeCategory.BUILDING_BLOCKS,
				Items.NETHERITE_INGOT, 6);
		shapeless(pWriter, List.of(ModBlocks.RAW_ZIRCONIUM_STAIRS.get()), List.of(1), RecipeCategory.BUILDING_BLOCKS,
				ModItems.RAW_ZIRCONIUM.get(), 6);
		shapeless(pWriter, List.of(ModBlocks.ZIRCONIUM_STAIRS.get()), List.of(1), RecipeCategory.BUILDING_BLOCKS,
				ModItems.ZIRCONIUM_INGOT.get(), 6);
		shapeless(pWriter, List.of(ModBlocks.ZIRCON_STAIRS.get()), List.of(1), RecipeCategory.BUILDING_BLOCKS,
				ModItems.ZIRCON.get(), 6);

		// Ore and material slab
		shaped(pWriter, List.of("PPP"), List.of('P'), List.of(Items.COAL), RecipeCategory.BUILDING_BLOCKS,
				ModBlocks.COAL_SLAB.get(), 1);
		shaped(pWriter, List.of("PPP"), List.of('P'), List.of(Items.CHARCOAL), RecipeCategory.BUILDING_BLOCKS,
				ModBlocks.CHARCOAL_SLAB.get(), 1);
		shaped(pWriter, List.of("PPP"), List.of('P'), List.of(Blocks.AMETHYST_BLOCK), RecipeCategory.BUILDING_BLOCKS,
				ModBlocks.AMETHYST_SLAB.get(), 6);
		shaped(pWriter, List.of("PPP"), List.of('P'), List.of(Items.RAW_COPPER), RecipeCategory.BUILDING_BLOCKS,
				ModBlocks.RAW_COPPER_SLAB.get(), 1);
		shaped(pWriter, List.of("PPP"), List.of('P'), List.of(Items.RAW_IRON), RecipeCategory.BUILDING_BLOCKS,
				ModBlocks.RAW_IRON_SLAB.get(), 1);
		shaped(pWriter, List.of("PPP"), List.of('P'), List.of(Items.IRON_INGOT), RecipeCategory.BUILDING_BLOCKS,
				ModBlocks.IRON_SLAB.get(), 1);
		shaped(pWriter, List.of("PPP"), List.of('P'), List.of(Items.RAW_GOLD), RecipeCategory.BUILDING_BLOCKS,
				ModBlocks.RAW_GOLD_SLAB.get(), 1);
		shaped(pWriter, List.of("PPP"), List.of('P'), List.of(Items.GOLD_INGOT), RecipeCategory.BUILDING_BLOCKS,
				ModBlocks.GOLD_SLAB.get(), 1);
		shaped(pWriter, List.of("PPP"), List.of('P'), List.of(Items.REDSTONE), RecipeCategory.BUILDING_BLOCKS,
				ModBlocks.REDSTONE_SLAB.get(), 1);
		shaped(pWriter, List.of("PPP"), List.of('P'), List.of(Items.LAPIS_LAZULI), RecipeCategory.BUILDING_BLOCKS,
				ModBlocks.LAPIS_SLAB.get(), 1);
		shaped(pWriter, List.of("PPP"), List.of('P'), List.of(Items.EMERALD), RecipeCategory.BUILDING_BLOCKS,
				ModBlocks.EMERALD_SLAB.get(), 1);
		shaped(pWriter, List.of("PPP"), List.of('P'), List.of(Items.DIAMOND), RecipeCategory.BUILDING_BLOCKS,
				ModBlocks.DIAMOND_SLAB.get(), 1);
		shaped(pWriter, List.of("PPP"), List.of('P'), List.of(Items.NETHERITE_INGOT), RecipeCategory.BUILDING_BLOCKS,
				ModBlocks.NETHERITE_SLAB.get(), 1);
		shaped(pWriter, List.of("PPP"), List.of('P'), List.of(Blocks.OBSIDIAN), RecipeCategory.BUILDING_BLOCKS,
				ModBlocks.OBSIDIAN_SLAB.get(), 6);
		shaped(pWriter, List.of("PPP"), List.of('P'), List.of(Blocks.CRYING_OBSIDIAN), RecipeCategory.BUILDING_BLOCKS,
				ModBlocks.CRYING_OBSIDIAN_SLAB.get(), 6);

		shaped(pWriter, List.of("PPP"), List.of('P'), List.of(ModItems.RAW_ZIRCONIUM.get()),
				RecipeCategory.BUILDING_BLOCKS, ModBlocks.RAW_ZIRCONIUM_SLAB.get(), 1);
		shaped(pWriter, List.of("PPP"), List.of('P'), List.of(ModItems.ZIRCONIUM_INGOT.get()),
				RecipeCategory.BUILDING_BLOCKS, ModBlocks.ZIRCONIUM_SLAB.get(), 1);
		shaped(pWriter, List.of("PPP"), List.of('P'), List.of(ModItems.ZIRCON.get()), RecipeCategory.BUILDING_BLOCKS,
				ModBlocks.ZIRCON_SLAB.get(), 1);
		shaped(pWriter, List.of("PPP"), List.of('P'), List.of(ModBlocks.CITRINE_BLOCK.get()),
				RecipeCategory.BUILDING_BLOCKS, ModBlocks.CITRINE_SLAB.get(), 6);

		// Uncrafting ore and material slab
		shapeless(pWriter, List.of(ModBlocks.COAL_SLAB.get()), List.of(1), RecipeCategory.BUILDING_BLOCKS, Items.COAL,
				3);
		shapeless(pWriter, List.of(ModBlocks.CHARCOAL_SLAB.get()), List.of(1), RecipeCategory.BUILDING_BLOCKS,
				Items.CHARCOAL, 3);
		shapeless(pWriter, List.of(ModBlocks.RAW_COPPER_SLAB.get()), List.of(1), RecipeCategory.BUILDING_BLOCKS,
				Items.RAW_COPPER, 3);
		shapeless(pWriter, List.of(ModBlocks.RAW_IRON_SLAB.get()), List.of(1), RecipeCategory.BUILDING_BLOCKS,
				Items.RAW_IRON, 3);
		shapeless(pWriter, List.of(ModBlocks.IRON_SLAB.get()), List.of(1), RecipeCategory.BUILDING_BLOCKS,
				Items.IRON_INGOT, 3);
		shapeless(pWriter, List.of(ModBlocks.RAW_GOLD_SLAB.get()), List.of(1), RecipeCategory.BUILDING_BLOCKS,
				Items.RAW_GOLD, 3);
		shapeless(pWriter, List.of(ModBlocks.GOLD_SLAB.get()), List.of(1), RecipeCategory.BUILDING_BLOCKS,
				Items.GOLD_INGOT, 3);
		shapeless(pWriter, List.of(ModBlocks.LAPIS_SLAB.get()), List.of(1), RecipeCategory.BUILDING_BLOCKS,
				Items.LAPIS_LAZULI, 3);
		shapeless(pWriter, List.of(ModBlocks.REDSTONE_SLAB.get()), List.of(1), RecipeCategory.BUILDING_BLOCKS,
				Items.REDSTONE, 3);
		shapeless(pWriter, List.of(ModBlocks.EMERALD_SLAB.get()), List.of(1), RecipeCategory.BUILDING_BLOCKS,
				Items.EMERALD, 3);
		shapeless(pWriter, List.of(ModBlocks.DIAMOND_SLAB.get()), List.of(1), RecipeCategory.BUILDING_BLOCKS,
				Items.DIAMOND, 3);
		shapeless(pWriter, List.of(ModBlocks.NETHERITE_SLAB.get()), List.of(1), RecipeCategory.BUILDING_BLOCKS,
				Items.NETHERITE_INGOT, 3);
		shapeless(pWriter, List.of(ModBlocks.RAW_ZIRCONIUM_SLAB.get()), List.of(1), RecipeCategory.BUILDING_BLOCKS,
				ModItems.RAW_ZIRCONIUM.get(), 3);
		shapeless(pWriter, List.of(ModBlocks.ZIRCONIUM_SLAB.get()), List.of(1), RecipeCategory.BUILDING_BLOCKS,
				ModItems.ZIRCONIUM_INGOT.get(), 3);
		shapeless(pWriter, List.of(ModBlocks.ZIRCON_SLAB.get()), List.of(1), RecipeCategory.BUILDING_BLOCKS,
				ModItems.ZIRCON.get(), 3);

		// Armor Wings
		shapeless(pWriter, List.of(Items.LEATHER_CHESTPLATE, Items.ELYTRA), List.of(1, 1), RecipeCategory.COMBAT,
				ModItems.LEATHER_WINGS.get(), 1);
		shapeless(pWriter, List.of(Items.CHAINMAIL_CHESTPLATE, Items.ELYTRA), List.of(1, 1), RecipeCategory.COMBAT,
				ModItems.CHAINMAIL_WINGS.get(), 1);
		shapeless(pWriter, List.of(Items.GOLDEN_CHESTPLATE, Items.ELYTRA), List.of(1, 1), RecipeCategory.COMBAT,
				ModItems.GOLDEN_WINGS.get(), 1);
		shapeless(pWriter, List.of(Items.IRON_CHESTPLATE, Items.ELYTRA), List.of(1, 1), RecipeCategory.COMBAT,
				ModItems.IRON_WINGS.get(), 1);
		shapeless(pWriter, List.of(Items.DIAMOND_CHESTPLATE, Items.ELYTRA), List.of(1, 1), RecipeCategory.COMBAT,
				ModItems.DIAMOND_WINGS.get(), 1);
		shapeless(pWriter, List.of(Items.NETHERITE_CHESTPLATE, Items.ELYTRA), List.of(1, 1), RecipeCategory.COMBAT,
				ModItems.NETHERITE_WINGS.get(), 1);
		shapeless(pWriter, List.of(ModItems.COPPER_CHESTPLATE.get(), Items.ELYTRA), List.of(1, 1),
				RecipeCategory.COMBAT, ModItems.COPPER_WINGS.get(), 1);
		shapeless(pWriter, List.of(ModItems.ZIRCONIUM_CHESTPLATE.get(), Items.ELYTRA), List.of(1, 1),
				RecipeCategory.COMBAT, ModItems.ZIRCONIUM_WINGS.get(), 1);
		shapeless(pWriter, List.of(ModItems.CITRINE_CHESTPLATE.get(), Items.ELYTRA), List.of(1, 1),
				RecipeCategory.COMBAT, ModItems.CITRINE_WINGS.get(), 1);

		// Copper buttons
		shapeless(pWriter, List.of(Blocks.CUT_COPPER), List.of(1), RecipeCategory.REDSTONE,
				ModBlocks.COPPER_BUTTON.get(), 1);
		shapeless(pWriter, List.of(Blocks.EXPOSED_CUT_COPPER), List.of(1), RecipeCategory.REDSTONE,
				ModBlocks.EXPOSED_COPPER_BUTTON.get(), 1);
		shapeless(pWriter, List.of(Blocks.WEATHERED_CUT_COPPER), List.of(1), RecipeCategory.REDSTONE,
				ModBlocks.WEATHERED_COPPER_BUTTON.get(), 1);
		shapeless(pWriter, List.of(Blocks.OXIDIZED_CUT_COPPER), List.of(1), RecipeCategory.REDSTONE,
				ModBlocks.OXIDIZED_COPPER_BUTTON.get(), 1);
		shapeless(pWriter, List.of(Blocks.WAXED_CUT_COPPER), List.of(1), RecipeCategory.REDSTONE,
				ModBlocks.WAXED_COPPER_BUTTON.get(), 1);
		shapeless(pWriter, List.of(Blocks.WAXED_EXPOSED_CUT_COPPER), List.of(1), RecipeCategory.REDSTONE,
				ModBlocks.WAXED_EXPOSED_COPPER_BUTTON.get(), 1);
		shapeless(pWriter, List.of(Blocks.WAXED_WEATHERED_CUT_COPPER), List.of(1), RecipeCategory.REDSTONE,
				ModBlocks.WAXED_WEATHERED_COPPER_BUTTON.get(), 1);
		shapeless(pWriter, List.of(Blocks.WAXED_OXIDIZED_CUT_COPPER), List.of(1), RecipeCategory.REDSTONE,
				ModBlocks.WAXED_OXIDIZED_COPPER_BUTTON.get(), 1);

		// Thick mist
		shapeless(pWriter, List.of(ModBlocks.CLOUD.get(), ModBlocks.MIST.get()), List.of(1, 1),
				RecipeCategory.BUILDING_BLOCKS, ModBlocks.THICK_MIST.get(), 2);

		// Spears
		shaped(pWriter, List.of("T  ", " S ", "  S"), List.of('S', 'T'), List.of(Items.STICK, ItemTags.PLANKS),
				RecipeCategory.COMBAT, ModItems.WOODEN_SPEAR.get(), 1);
		shaped(pWriter, List.of("T  ", " S ", "  S"), List.of('S', 'T'), List.of(Items.STICK, ItemTags.STONE_TOOL_MATERIALS),
				RecipeCategory.COMBAT, ModItems.STONE_SPEAR.get(), 1);
		shaped(pWriter, List.of("T  ", " S ", "  S"), List.of('S', 'T'), List.of(Items.STICK, Items.IRON_INGOT),
				RecipeCategory.COMBAT, ModItems.IRON_SPEAR.get(), 1);
		shaped(pWriter, List.of("T  ", " S ", "  S"), List.of('S', 'T'), List.of(Items.STICK, Items.COPPER_INGOT),
				RecipeCategory.COMBAT, ModItems.COPPER_SPEAR.get(), 1);
		shaped(pWriter, List.of("T  ", " S ", "  S"), List.of('S', 'T'), List.of(Items.STICK, Items.GOLD_INGOT),
				RecipeCategory.COMBAT, ModItems.GOLDEN_SPEAR.get(), 1);
		shaped(pWriter, List.of("T  ", " S ", "  S"), List.of('S', 'T'), List.of(Items.STICK, Items.DIAMOND),
				RecipeCategory.COMBAT, ModItems.DIAMOND_SPEAR.get(), 1);
		smithingTransform(pWriter, ModItems.ZIRCONIUM_UPGRADE_SMITHING_TEMPLATE.get(), ModItems.IRON_SPEAR.get(),
				ModItems.ZIRCONIUM_INGOT.get(), RecipeCategory.COMBAT, ModItems.ZIRCONIUM_SPEAR.get());
		smithingTransform(pWriter, Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE, ModItems.DIAMOND_SPEAR.get(),
				Items.NETHERITE_INGOT, RecipeCategory.COMBAT, ModItems.NETHERITE_SPEAR.get());

	}

	// Shapeless recipe. Item at i must correspond to item count at i.
	@SuppressWarnings("unchecked")
	protected static void shapeless(Consumer<FinishedRecipe> pFinishedRecipeConsumer, List<Object> ingredients,
			List<Integer> counts, RecipeCategory recipeCategory, ItemLike result, int count) {

		ShapelessRecipeBuilder recipe = ShapelessRecipeBuilder.shapeless(recipeCategory, result, count);
		String recipeName = ZirconMod.MOD_ID + ":" + getItemName(result) + "_from";

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
				recipeName += "_tag_" + ((TagKey<Item>) ingredients.get(i)).registry().registry().toDebugFileName();
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
		recipe.save(pFinishedRecipeConsumer, recipeName);

	}

	protected static void smithingTransform(Consumer<FinishedRecipe> pFinishedRecipeConsumer, ItemLike template,
			ItemLike base, ItemLike addition, RecipeCategory recipeCategory, Item result) {
		SmithingTransformRecipeBuilder recipe = SmithingTransformRecipeBuilder.smithing(Ingredient.of(template),
				Ingredient.of(base), Ingredient.of(addition), recipeCategory, result);

		// Recipe gift triggers
		recipe = recipe.unlocks(getHasName(template), has(template));
		recipe = recipe.unlocks(getHasName(base), has(base));
		recipe = recipe.unlocks(getHasName(addition), has(addition));

		String recipeName = ZirconMod.MOD_ID + ":" + getItemName(result) + "_from_smithing_transform_of_"
				+ getItemName(base);
		System.out.println("Finished smithing recipe, saving with name: " + recipeName);
		recipe.save(pFinishedRecipeConsumer, recipeName);
	}

	// Shaped recipe. Token at i must correspond to ingredient at i.
	@SuppressWarnings("unchecked")
	protected static void shaped(Consumer<FinishedRecipe> pFinishedRecipeConsumer, List<String> pattern,
			List<Character> tokens, List<Object> ingredients, RecipeCategory recipeCategory, ItemLike result,
			int count) {

		ShapedRecipeBuilder recipe = ShapedRecipeBuilder.shaped(recipeCategory, result, count);
		String recipeName = ZirconMod.MOD_ID + ":" + getItemName(result) + "_from";
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
				recipeName += "_tag_" + ((TagKey<Item>) ingredients.get(i)).registry().registry().toDebugFileName();
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
		recipe.save(pFinishedRecipeConsumer, recipeName);
	}

	protected static void oreSmelting(Consumer<FinishedRecipe> pFinishedRecipeConsumer, List<ItemLike> pIngredients,
			RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTime, String pGroup) {
		oreCooking(pFinishedRecipeConsumer, RecipeSerializer.SMELTING_RECIPE, pIngredients, pCategory, pResult,
				pExperience, pCookingTime, pGroup, "_from_smelting");
	}

	protected static void oreBlasting(Consumer<FinishedRecipe> pFinishedRecipeConsumer, List<ItemLike> pIngredients,
			RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTime, String pGroup) {
		oreCooking(pFinishedRecipeConsumer, RecipeSerializer.BLASTING_RECIPE, pIngredients, pCategory, pResult,
				pExperience, pCookingTime, pGroup, "_from_blasting");
	}

	protected static void charcoalSmelting(Consumer<FinishedRecipe> pFinishedRecipeConsumer, ItemLike pIngredient) {
		oreCooking(pFinishedRecipeConsumer, RecipeSerializer.SMELTING_RECIPE, List.of(pIngredient), RecipeCategory.MISC,
				Items.CHARCOAL, 0.1f, 200, "charcoal", "_from_smelting_" + getItemName(pIngredient));
	}

	protected static void oreCooking(Consumer<FinishedRecipe> pFinishedRecipeConsumer,
			RecipeSerializer<? extends AbstractCookingRecipe> pCookingSerializer, List<ItemLike> pIngredients,
			RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTime, String pGroup,
			String pRecipeName) {
		for (ItemLike itemlike : pIngredients) {
			SimpleCookingRecipeBuilder
					.generic(Ingredient.of(itemlike), pCategory, pResult, pExperience, pCookingTime, pCookingSerializer)
					.group(pGroup).unlockedBy(getHasName(itemlike), has(itemlike)).save(pFinishedRecipeConsumer,
							ZirconMod.MOD_ID + ":" + getItemName(pResult) + pRecipeName + "_" + getItemName(itemlike));
		}
	}
}
