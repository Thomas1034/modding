package com.thomas.cloudscape.villager;

import java.util.List;

import com.thomas.cloudscape.block.ModBlocks;
import com.thomas.cloudscape.item.ModItems;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class ModVillagerTrades {

	public static final Block[] STAINED_GLASS_TYPES = { Blocks.BLACK_STAINED_GLASS, Blocks.BLUE_STAINED_GLASS,
			Blocks.BROWN_STAINED_GLASS, Blocks.CYAN_STAINED_GLASS, Blocks.GRAY_STAINED_GLASS,
			Blocks.GREEN_STAINED_GLASS, Blocks.LIGHT_BLUE_STAINED_GLASS, Blocks.LIGHT_GRAY_STAINED_GLASS,
			Blocks.LIME_STAINED_GLASS, Blocks.MAGENTA_STAINED_GLASS, Blocks.ORANGE_STAINED_GLASS,
			Blocks.PINK_STAINED_GLASS, Blocks.PURPLE_STAINED_GLASS, Blocks.RED_STAINED_GLASS,
			Blocks.WHITE_STAINED_GLASS, Blocks.YELLOW_STAINED_GLASS };

	public static final Item[] TRIM_SMITHING_TEMPLATE_ITEMS = { Items.COAST_ARMOR_TRIM_SMITHING_TEMPLATE,
			Items.DUNE_ARMOR_TRIM_SMITHING_TEMPLATE, Items.EYE_ARMOR_TRIM_SMITHING_TEMPLATE,
			Items.HOST_ARMOR_TRIM_SMITHING_TEMPLATE, Items.RAISER_ARMOR_TRIM_SMITHING_TEMPLATE,
			Items.RAISER_ARMOR_TRIM_SMITHING_TEMPLATE, Items.RIB_ARMOR_TRIM_SMITHING_TEMPLATE,
			Items.SENTRY_ARMOR_TRIM_SMITHING_TEMPLATE, Items.SHAPER_ARMOR_TRIM_SMITHING_TEMPLATE,
			Items.SILENCE_ARMOR_TRIM_SMITHING_TEMPLATE, Items.SNOUT_ARMOR_TRIM_SMITHING_TEMPLATE,
			Items.SPIRE_ARMOR_TRIM_SMITHING_TEMPLATE, Items.TIDE_ARMOR_TRIM_SMITHING_TEMPLATE,
			Items.VEX_ARMOR_TRIM_SMITHING_TEMPLATE, Items.WARD_ARMOR_TRIM_SMITHING_TEMPLATE,
			Items.WAYFINDER_ARMOR_TRIM_SMITHING_TEMPLATE, Items.WILD_ARMOR_TRIM_SMITHING_TEMPLATE };

	public static void addFarmerTrades(Int2ObjectMap<List<VillagerTrades.ItemListing>> trades) {
		// Level 1
		// Adds blueberry trades
		trades.get(1).add((pTrader, pRandom) -> new MerchantOffer(new ItemStack(ModItems.BLUEBERRY.get(), 8),
				new ItemStack(Items.EMERALD, 2), 16, 2, 0.02f));
		trades.get(1).add((pTrader, pRandom) -> new MerchantOffer(new ItemStack(Items.EMERALD, 4),
				new ItemStack(ModItems.BLUEBERRY_SEEDS.get(), 3), 16, 2, 0.02f));
	}

	public static void addButcherTrades(Int2ObjectMap<List<VillagerTrades.ItemListing>> trades) {
		// Level 1
		// Adds meat buying trades
		trades.get(2).add((pTrader, pRandom) -> new MerchantOffer(new ItemStack(Items.CHICKEN, 8 + pRandom.nextInt(12)),
				new ItemStack(Items.EMERALD, 1), 16, 2, 0.02f));
		trades.get(2).add((pTrader, pRandom) -> new MerchantOffer(new ItemStack(Items.BEEF, 8 + pRandom.nextInt(12)),
				new ItemStack(Items.EMERALD, 1), 16, 2, 0.02f));
		trades.get(2).add((pTrader, pRandom) -> new MerchantOffer(new ItemStack(Items.MUTTON, 8 + pRandom.nextInt(12)),
				new ItemStack(Items.EMERALD, 1), 16, 2, 0.02f));
		trades.get(2)
				.add((pTrader, pRandom) -> new MerchantOffer(new ItemStack(Items.PORKCHOP, 8 + pRandom.nextInt(12)),
						new ItemStack(Items.EMERALD, 1), 16, 2, 0.02f));
	}

	public static void addForesterTrades(Int2ObjectMap<List<VillagerTrades.ItemListing>> trades) {

		// The level of the villager, will be updated as the function goes on.
		int villagerLevel = 0;

		// Adds trades to the villager's level 1.
		// Syntax:
		// cost
		// result
		// maxUses
		// villagerXP
		// priceMultiplier
		villagerLevel = 1;

		// Berry selling trades
		trades.get(villagerLevel).add((trader, rand) -> new MerchantOffer(new ItemStack(Items.EMERALD, 3),
				new ItemStack(ModItems.BLUEBERRY.get(), rand.nextInt(3) + 6), 3, 3, 0.02f));
		trades.get(villagerLevel).add((trader, rand) -> new MerchantOffer(new ItemStack(Items.EMERALD, 3),
				new ItemStack(Items.SWEET_BERRIES, rand.nextInt(2) + 10), 3, 3, 0.02f));

		// Log buying trades:
		trades.get(villagerLevel)
				.add((trader, rand) -> new MerchantOffer(new ItemStack(Items.ACACIA_LOG, rand.nextInt(2) + 15),
						new ItemStack(Items.EMERALD, 1), 10, 2, 0.02f));
		trades.get(villagerLevel)
				.add((trader, rand) -> new MerchantOffer(new ItemStack(Items.BIRCH_LOG, rand.nextInt(2) + 15),
						new ItemStack(Items.EMERALD, 1), 10, 2, 0.02f));
		trades.get(villagerLevel)
				.add((trader, rand) -> new MerchantOffer(new ItemStack(Items.DARK_OAK_LOG, rand.nextInt(2) + 15),
						new ItemStack(Items.EMERALD, 1), 10, 2, 0.02f));
		trades.get(villagerLevel)
				.add((trader, rand) -> new MerchantOffer(new ItemStack(Items.DARK_OAK_LOG, rand.nextInt(2) + 15),
						new ItemStack(Items.EMERALD, 1), 10, 2, 0.02f));
		trades.get(villagerLevel)
				.add((trader, rand) -> new MerchantOffer(new ItemStack(Items.JUNGLE_LOG, rand.nextInt(2) + 15),
						new ItemStack(Items.EMERALD, 1), 10, 2, 0.02f));
		trades.get(villagerLevel)
				.add((trader, rand) -> new MerchantOffer(new ItemStack(Items.MANGROVE_LOG, rand.nextInt(2) + 15),
						new ItemStack(Items.EMERALD, 1), 10, 2, 0.02f));
		trades.get(villagerLevel)
				.add((trader, rand) -> new MerchantOffer(new ItemStack(Items.OAK_LOG, rand.nextInt(2) + 15),
						new ItemStack(Items.EMERALD, 1), 10, 2, 0.02f));
		trades.get(villagerLevel)
				.add((trader, rand) -> new MerchantOffer(new ItemStack(Items.SPRUCE_LOG, rand.nextInt(2) + 15),
						new ItemStack(Items.EMERALD, 1), 10, 3, 0.02f));

		// Plank selling trades
		trades.get(villagerLevel).add((trader, rand) -> new MerchantOffer(new ItemStack(Items.EMERALD, 2),
				new ItemStack(Items.OAK_PLANKS, rand.nextInt(4) + 8), 10, 4, 0.02f));
		trades.get(villagerLevel).add((trader, rand) -> new MerchantOffer(new ItemStack(Items.EMERALD, 2),
				new ItemStack(Items.BIRCH_PLANKS, rand.nextInt(4) + 8), 10, 4, 0.02f));

		// Sapling buying trades
		trades.get(villagerLevel)
				.add((trader, rand) -> new MerchantOffer(new ItemStack(Items.OAK_SAPLING, rand.nextInt(2) + 2),
						new ItemStack(Items.EMERALD, 1), 10, 3, 0.02f));
		trades.get(villagerLevel)
				.add((trader, rand) -> new MerchantOffer(new ItemStack(Items.BIRCH_SAPLING, rand.nextInt(2) + 2),
						new ItemStack(Items.EMERALD, 1), 10, 3, 0.02f));

		// Level 2 trades start here.
		villagerLevel = 2;
		// Axe selling trades
		trades.get(villagerLevel).add((trader, rand) -> new MerchantOffer(new ItemStack(Items.EMERALD, 3),
				new ItemStack(Items.IRON_AXE, 1), 4, 10, 0.04f));
		trades.get(villagerLevel).add((trader, rand) -> new MerchantOffer(new ItemStack(Items.EMERALD, 4),
				new ItemStack(Items.GOLDEN_AXE, 1), 4, 15, 0.04f));

		// Apple buying trade
		trades.get(villagerLevel)
				.add((trader, rand) -> new MerchantOffer(new ItemStack(Items.APPLE, rand.nextInt(2) + 4),
						new ItemStack(Items.EMERALD, 1), 10, 10, 0.04f));

		// Rabbit parts buying trade
		trades.get(villagerLevel)
				.add((trader, rand) -> new MerchantOffer(new ItemStack(Items.RABBIT, rand.nextInt(2) + 8),
						new ItemStack(Items.EMERALD, 1), 10, 10, 0.04f));
		trades.get(villagerLevel)
				.add((trader, rand) -> new MerchantOffer(new ItemStack(Items.RABBIT_HIDE, rand.nextInt(2) + 8),
						new ItemStack(Items.EMERALD, 1), 10, 8, 0.04f));

		// Charcoal selling trade
		trades.get(villagerLevel).add((trader, rand) -> new MerchantOffer(new ItemStack(Items.EMERALD, 3),
				new ItemStack(Items.CHARCOAL, 5), 10, 8, 0.04f));

		// Level 3 trades start here.
		villagerLevel = 3;
		trades.get(villagerLevel).add((trader, rand) -> new MerchantOffer(new ItemStack(Items.EMERALD, 4),
				new ItemStack(Items.MAP, 1), 6, 12, 0.08f));

		// Level 4 trades start here.
		villagerLevel = 4;
		// Decorative plants selling trades
		trades.get(villagerLevel).add((trader, rand) -> new MerchantOffer(new ItemStack(Items.EMERALD, 2),
				new ItemStack(Items.LARGE_FERN, 3), 8, 16, 0.02f));
		trades.get(villagerLevel).add((trader, rand) -> new MerchantOffer(new ItemStack(Items.EMERALD, 1),
				new ItemStack(Items.FERN, 3), 8, 16, 0.02f));
		trades.get(villagerLevel).add((trader, rand) -> new MerchantOffer(new ItemStack(Items.EMERALD, 2),
				new ItemStack(Items.DEAD_BUSH, 3), 8, 16, 0.02f));
		trades.get(villagerLevel).add((trader, rand) -> new MerchantOffer(new ItemStack(Items.EMERALD, 1),
				new ItemStack(Items.VINE, 3), 8, 16, 0.02f));
		trades.get(villagerLevel).add((trader, rand) -> new MerchantOffer(new ItemStack(Items.EMERALD, 2),
				new ItemStack(Items.HANGING_ROOTS, 3), 4, 16, 0.02f));
		trades.get(villagerLevel).add((trader, rand) -> new MerchantOffer(new ItemStack(Items.EMERALD, 2),
				new ItemStack(Items.LILY_PAD, 3), 4, 16, 0.02f));

		// Level 5 trades start here.
		villagerLevel = 5;
		trades.get(villagerLevel).add((trader, rand) -> new MerchantOffer(new ItemStack(Items.EMERALD, 6),
				new ItemStack(Items.ACACIA_SAPLING, 1), 4, 16, 0.02f));
		trades.get(villagerLevel).add((trader, rand) -> new MerchantOffer(new ItemStack(Items.EMERALD, 6),
				new ItemStack(Items.JUNGLE_SAPLING, 1), 4, 16, 0.02f));
		trades.get(villagerLevel).add((trader, rand) -> new MerchantOffer(new ItemStack(Items.EMERALD, 6),
				new ItemStack(Items.AZALEA, 1), 4, 16, 0.02f));
		trades.get(villagerLevel).add((trader, rand) -> new MerchantOffer(new ItemStack(Items.EMERALD, 6),
				new ItemStack(Items.FLOWERING_AZALEA, 1), 4, 16, 0.02f));
		trades.get(villagerLevel).add((trader, rand) -> new MerchantOffer(new ItemStack(Items.EMERALD, 6),
				new ItemStack(Items.SPRUCE_SAPLING, 1), 4, 16, 0.02f));
		trades.get(villagerLevel).add((trader, rand) -> new MerchantOffer(new ItemStack(Items.EMERALD, 6),
				new ItemStack(Items.DARK_OAK_SAPLING, 1), 4, 16, 0.02f));
		trades.get(villagerLevel).add((trader, rand) -> new MerchantOffer(new ItemStack(Items.EMERALD, 3),
				new ItemStack(Items.BAMBOO, 1), 4, 16, 0.02f));
		trades.get(villagerLevel).add((trader, rand) -> new MerchantOffer(new ItemStack(Items.EMERALD, 8),
				new ItemStack(Items.CRIMSON_STEM, rand.nextInt(5) + 8), 3, 20, 0.02f));
		trades.get(villagerLevel).add((trader, rand) -> new MerchantOffer(new ItemStack(Items.EMERALD, 8),
				new ItemStack(Items.WARPED_STEM, rand.nextInt(5) + 8), 3, 20, 0.02f));
	}

	public static void addArchitectTrades(Int2ObjectMap<List<VillagerTrades.ItemListing>> trades) {

		// Trades:
		// Sells:
		// Citrine Tools
		// Citrine Lantern / Bracket
		// Buys:
		// Mud Bricks
		// Terracotta
		// Deepslate
		// End Stone
		// Nether Bricks
		// Copper blocks

		// The level of the villager, will be updated as the function goes on.
		int villagerLevel = 0;

		// Adds trades to the villager's level 1.
		// Syntax:
		// cost
		// result
		// maxUses
		// villagerXP
		// priceMultiplier
		villagerLevel = 1;

		// Cloud brick conversion trades
		trades.get(villagerLevel)
				.add((trader, rand) -> new MerchantOffer(new ItemStack(ModBlocks.CLOUD.get(), rand.nextInt(2) + 12),
						new ItemStack(ModItems.CUT_CITRINE.get(), 1), 4, 3, 0.02f));
		trades.get(villagerLevel).add((trader, rand) -> new MerchantOffer(new ItemStack(ModItems.CUT_CITRINE.get(), 1),
				new ItemStack(ModBlocks.CLOUD_BRICKS.get(), rand.nextInt(2) + 4), 4, 3, 0.02f));
		trades.get(villagerLevel)
				.add((trader, rand) -> new MerchantOffer(
						new ItemStack(ModBlocks.THUNDER_CLOUD.get(), rand.nextInt(2) + 6),
						new ItemStack(ModItems.CUT_CITRINE.get(), 1), 4, 3, 0.02f));
		trades.get(villagerLevel).add((trader, rand) -> new MerchantOffer(new ItemStack(ModItems.CUT_CITRINE.get(), 2),
				new ItemStack(ModBlocks.THUNDER_CLOUD_BRICKS.get(), rand.nextInt(2) + 4), 4, 3, 0.02f));

		// Level 2 trades start here.
		villagerLevel = 2;
		// Buying foreign stones
		trades.get(villagerLevel).add((trader, rand) -> new MerchantOffer(new ItemStack(Blocks.DIORITE, 12),
				new ItemStack(ModItems.CUT_CITRINE.get(), 1), 6, 6, 0.04f));
		trades.get(villagerLevel).add((trader, rand) -> new MerchantOffer(new ItemStack(Blocks.ANDESITE, 12),
				new ItemStack(ModItems.CUT_CITRINE.get(), 1), 6, 6, 0.04f));
		trades.get(villagerLevel).add((trader, rand) -> new MerchantOffer(new ItemStack(Blocks.GRANITE, 12),
				new ItemStack(ModItems.CUT_CITRINE.get(), 1), 6, 6, 0.04f));
		trades.get(villagerLevel).add((trader, rand) -> new MerchantOffer(new ItemStack(Blocks.STONE, 12),
				new ItemStack(ModItems.CUT_CITRINE.get(), 1), 6, 6, 0.04f));
		trades.get(villagerLevel).add((trader, rand) -> new MerchantOffer(new ItemStack(Blocks.TUFF, 12),
				new ItemStack(ModItems.CUT_CITRINE.get(), 1), 6, 6, 0.04f));
		trades.get(villagerLevel).add((trader, rand) -> new MerchantOffer(new ItemStack(Blocks.COBBLED_DEEPSLATE, 12),
				new ItemStack(ModItems.CUT_CITRINE.get(), 1), 6, 6, 0.04f));
		trades.get(villagerLevel).add((trader, rand) -> new MerchantOffer(new ItemStack(Blocks.NETHER_BRICKS, 6),
				new ItemStack(ModItems.CUT_CITRINE.get(), 1), 6, 6, 0.04f));
		trades.get(villagerLevel).add((trader, rand) -> new MerchantOffer(new ItemStack(Blocks.END_STONE, 12),
				new ItemStack(ModItems.CUT_CITRINE.get(), 1), 6, 6, 0.04f));
		trades.get(villagerLevel).add((trader, rand) -> new MerchantOffer(new ItemStack(Blocks.OBSIDIAN, 4),
				new ItemStack(ModItems.CUT_CITRINE.get(), 1), 6, 6, 0.04f));
		trades.get(villagerLevel).add((trader, rand) -> new MerchantOffer(new ItemStack(Blocks.DRIPSTONE_BLOCK, 6),
				new ItemStack(ModItems.CUT_CITRINE.get(), 1), 6, 6, 0.04f));
		trades.get(villagerLevel).add((trader, rand) -> new MerchantOffer(new ItemStack(Blocks.TERRACOTTA, 6),
				new ItemStack(ModItems.CUT_CITRINE.get(), 1), 6, 6, 0.04f));

		// Level 3 trades start here.
		villagerLevel = 3;
		// Selling citrine decorations
		trades.get(villagerLevel).add((trader, rand) -> new MerchantOffer(new ItemStack(ModItems.CUT_CITRINE.get(), 1),
				new ItemStack(ModItems.CITRINE_BRACKET.get(), 3 + rand.nextInt(3)), 3, 6, 0.04f));
		trades.get(villagerLevel).add((trader, rand) -> new MerchantOffer(new ItemStack(ModItems.CUT_CITRINE.get(), 1),
				new ItemStack(ModBlocks.CITRINE_LANTERN.get(), 2 + rand.nextInt(2)), 3, 6, 0.04f));
		// Buying stained glass
		trades.get(villagerLevel).add((trader, rand) -> new MerchantOffer(new ItemStack(getStainedGlassColor(rand), 6),
				new ItemStack(ModItems.CUT_CITRINE.get(), 1), 6, 6, 0.04f));
		// Selling scaffolding
		trades.get(villagerLevel).add((trader, rand) -> new MerchantOffer(new ItemStack(ModItems.CUT_CITRINE.get(), 1),
				new ItemStack(Blocks.SCAFFOLDING, 8), 3, 6, 0.04f));

		// Level 4 trades start here.
		villagerLevel = 4;
		trades.get(villagerLevel)
				.add((trader, rand) -> new MerchantOffer(new ItemStack(ModItems.CUT_CITRINE.get(), rand.nextInt(2) + 3),
						new ItemStack(ModItems.CITRINE_PICKAXE.get(), 1), 2, 12, 0.08f));
		trades.get(villagerLevel)
				.add((trader, rand) -> new MerchantOffer(new ItemStack(ModItems.CUT_CITRINE.get(), rand.nextInt(2) + 3),
						new ItemStack(ModItems.CITRINE_AXE.get(), 1), 2, 12, 0.08f));
		trades.get(villagerLevel)
				.add((trader, rand) -> new MerchantOffer(new ItemStack(ModItems.CUT_CITRINE.get(), rand.nextInt(2) + 1),
						new ItemStack(ModItems.CITRINE_SHOVEL.get(), 1), 2, 12, 0.08f));

		// Level 5 trades start here.
		villagerLevel = 5;
		trades.get(villagerLevel).add((trader, rand) -> new MerchantOffer(new ItemStack(ModItems.CUT_CITRINE.get(), 2),
				new ItemStack(Items.OBSIDIAN, 4), new ItemStack(Items.CRYING_OBSIDIAN, 4), 3, 6, 0.04f));
		trades.get(villagerLevel).add((trader, rand) -> new MerchantOffer(new ItemStack(ModItems.CUT_CITRINE.get(), 2),
				new ItemStack(ModBlocks.CLOUD_BRICK_PILLAR.get(), 4 + rand.nextInt(2)), 6, 6, 0.04f));
		trades.get(villagerLevel).add((trader, rand) -> new MerchantOffer(new ItemStack(ModItems.CUT_CITRINE.get(), 2),
				new ItemStack(ModBlocks.THUNDER_CLOUD_BRICK_PILLAR.get(), 4 + rand.nextInt(2)), 6, 6, 0.04f));
		trades.get(villagerLevel).add((trader, rand) -> new MerchantOffer(new ItemStack(ModItems.CUT_CITRINE.get(), 2),
				new ItemStack(ModBlocks.CHISELED_CLOUD_BRICKS.get(), 4 + rand.nextInt(2)), 6, 6, 0.04f));
		trades.get(villagerLevel).add((trader, rand) -> new MerchantOffer(new ItemStack(ModItems.CUT_CITRINE.get(), 2),
				new ItemStack(ModBlocks.CHISELED_THUNDER_CLOUD_BRICKS.get(), 4 + rand.nextInt(2)), 6, 6, 0.04f));

	}

	public static void addBotanistTrades(Int2ObjectMap<List<VillagerTrades.ItemListing>> trades) {

		// The level of the villager, will be updated as the function goes on.
		int villagerLevel = 0;

		// Adds trades to the villager's level 1.
		// Syntax:
		// cost
		// result
		// maxUses
		// villagerXP
		// priceMultiplier
		villagerLevel = 1;

		// Sell

		// Buys common plants
		trades.get(villagerLevel)
				.add((trader, rand) -> new MerchantOffer(new ItemStack(Blocks.FERN, rand.nextInt(2) + 8),
						new ItemStack(ModItems.CUT_CITRINE.get(), 1), 4, 3, 0.02f));
		trades.get(villagerLevel).add((trader, rand) -> new MerchantOffer(new ItemStack(Blocks.FLOWER_POT, 5),
				new ItemStack(ModItems.CUT_CITRINE.get(), 1), 4, 3, 0.02f));
		trades.get(villagerLevel)
				.add((trader, rand) -> new MerchantOffer(new ItemStack(Blocks.GLOW_LICHEN, rand.nextInt(2) + 4),
						new ItemStack(ModItems.CUT_CITRINE.get(), 1), 4, 3, 0.02f));
		trades.get(villagerLevel)
				.add((trader, rand) -> new MerchantOffer(new ItemStack(Blocks.GRASS_BLOCK, rand.nextInt(4) + 16),
						new ItemStack(ModItems.CUT_CITRINE.get(), 1), 4, 3, 0.02f));
		trades.get(villagerLevel)
				.add((trader, rand) -> new MerchantOffer(new ItemStack(Blocks.PODZOL, rand.nextInt(2) + 16),
						new ItemStack(ModItems.CUT_CITRINE.get(), 1), 4, 3, 0.02f));
		trades.get(villagerLevel)
				.add((trader, rand) -> new MerchantOffer(new ItemStack(Blocks.SEAGRASS, rand.nextInt(2) + 12),
						new ItemStack(ModItems.CUT_CITRINE.get(), 1), 4, 3, 0.02f));
		trades.get(villagerLevel)
				.add((trader, rand) -> new MerchantOffer(new ItemStack(Items.BROWN_MUSHROOM, rand.nextInt(2) + 8),
						new ItemStack(ModItems.CUT_CITRINE.get(), 1), 4, 3, 0.02f));
		trades.get(villagerLevel)
				.add((trader, rand) -> new MerchantOffer(new ItemStack(Items.RED_MUSHROOM, rand.nextInt(2) + 8),
						new ItemStack(ModItems.CUT_CITRINE.get(), 1), 4, 3, 0.02f));

		// Level 2 trades start here.
		villagerLevel = 2;
		// Sells vines and moss and bamboo - things that could grow in the clouds.
		trades.get(villagerLevel).add((trader, rand) -> new MerchantOffer(new ItemStack(ModItems.CUT_CITRINE.get(), 2),
				new ItemStack(Blocks.MOSS_CARPET, 4), 3, 3, 0.02f));
		trades.get(villagerLevel).add((trader, rand) -> new MerchantOffer(new ItemStack(ModItems.CUT_CITRINE.get(), 2),
				new ItemStack(Blocks.VINE, 6), 3, 3, 0.02f));
		trades.get(villagerLevel).add((trader, rand) -> new MerchantOffer(new ItemStack(ModItems.CUT_CITRINE.get(), 2),
				new ItemStack(Items.BAMBOO, 6), 3, 3, 0.02f));
		trades.get(villagerLevel).add((trader, rand) -> new MerchantOffer(new ItemStack(ModItems.CUT_CITRINE.get(), 2),
				new ItemStack(Items.SPORE_BLOSSOM, 1), 3, 3, 0.02f));
		trades.get(villagerLevel).add((trader, rand) -> new MerchantOffer(new ItemStack(ModItems.CUT_CITRINE.get(), 2),
				new ItemStack(Items.SMALL_DRIPLEAF, 1), 3, 3, 0.02f));

		// Level 3 trades start here.
		villagerLevel = 3;
		// Buys nether "plants"
		trades.get(villagerLevel)
				.add((trader, rand) -> new MerchantOffer(new ItemStack(Blocks.NETHER_SPROUTS, rand.nextInt(2) + 12),
						new ItemStack(ModItems.CUT_CITRINE.get(), 1), 4, 3, 0.02f));
		trades.get(villagerLevel)
				.add((trader, rand) -> new MerchantOffer(new ItemStack(Blocks.CRIMSON_ROOTS, rand.nextInt(2) + 12),
						new ItemStack(ModItems.CUT_CITRINE.get(), 1), 4, 3, 0.02f));
		trades.get(villagerLevel)
				.add((trader, rand) -> new MerchantOffer(new ItemStack(Blocks.WARPED_ROOTS, rand.nextInt(2) + 12),
						new ItemStack(ModItems.CUT_CITRINE.get(), 1), 4, 3, 0.02f));
		trades.get(villagerLevel)
				.add((trader, rand) -> new MerchantOffer(new ItemStack(Blocks.CRIMSON_FUNGUS, rand.nextInt(2) + 6),
						new ItemStack(ModItems.CUT_CITRINE.get(), 1), 4, 3, 0.02f));
		trades.get(villagerLevel)
				.add((trader, rand) -> new MerchantOffer(new ItemStack(Blocks.WARPED_FUNGUS, rand.nextInt(2) + 6),
						new ItemStack(ModItems.CUT_CITRINE.get(), 1), 4, 3, 0.02f));
		trades.get(villagerLevel)
				.add((trader, rand) -> new MerchantOffer(new ItemStack(Items.TWISTING_VINES, rand.nextInt(4) + 16),
						new ItemStack(ModItems.CUT_CITRINE.get(), 1), 4, 3, 0.02f));
		trades.get(villagerLevel)
				.add((trader, rand) -> new MerchantOffer(new ItemStack(Items.WEEPING_VINES, rand.nextInt(4) + 16),
						new ItemStack(ModItems.CUT_CITRINE.get(), 1), 4, 3, 0.02f));
		trades.get(villagerLevel)
				.add((trader, rand) -> new MerchantOffer(new ItemStack(Blocks.SHROOMLIGHT, rand.nextInt(2) + 4),
						new ItemStack(ModItems.CUT_CITRINE.get(), 1), 4, 3, 0.02f));

		// Level 4 trades start here.
		villagerLevel = 4;
		// Buys and sells rarer plants
		trades.get(villagerLevel).add((trader, rand) -> new MerchantOffer(new ItemStack(ModItems.CUT_CITRINE.get(), 1),
				new ItemStack(Items.LILY_PAD, 2), 3, 3, 0.02f));
		trades.get(villagerLevel).add((trader, rand) -> new MerchantOffer(new ItemStack(ModItems.CUT_CITRINE.get(), 4),
				new ItemStack(Items.GOLDEN_APPLE, 1), 3, 3, 0.02f));
		trades.get(villagerLevel)
				.add((trader, rand) -> new MerchantOffer(new ItemStack(Items.CHORUS_FLOWER, rand.nextInt(2) + 3),
						new ItemStack(ModItems.CUT_CITRINE.get(), 1), 4, 3, 0.02f));
		trades.get(villagerLevel)
				.add((trader, rand) -> new MerchantOffer(new ItemStack(Items.POISONOUS_POTATO, rand.nextInt(3) + 12),
						new ItemStack(ModItems.CUT_CITRINE.get(), 2), 4, 3, 0.02f));
		trades.get(villagerLevel)
				.add((trader, rand) -> new MerchantOffer(new ItemStack(Items.HANGING_ROOTS, rand.nextInt(2) + 3),
						new ItemStack(ModItems.CUT_CITRINE.get(), 1), 4, 3, 0.02f));
		trades.get(villagerLevel)
				.add((trader, rand) -> new MerchantOffer(new ItemStack(Items.SEA_PICKLE, rand.nextInt(2) + 3),
						new ItemStack(ModItems.CUT_CITRINE.get(), 1), 4, 3, 0.02f));

		// Level 5 trades start here.
		villagerLevel = 5;
		// Extremely rare trades
		trades.get(villagerLevel).add((trader, rand) -> new MerchantOffer(new ItemStack(Items.WITHER_ROSE, 1),
				new ItemStack(ModItems.CUT_CITRINE.get(), 8), 4, 3, 0.02f));
		trades.get(villagerLevel).add((trader, rand) -> new MerchantOffer(new ItemStack(Items.TORCHFLOWER, 1),
				new ItemStack(ModItems.CUT_CITRINE.get(), 2), 4, 3, 0.02f));
		trades.get(villagerLevel).add((trader, rand) -> new MerchantOffer(new ItemStack(Items.PITCHER_PLANT, 1),
				new ItemStack(ModItems.CUT_CITRINE.get(), 2), 4, 3, 0.02f));
		trades.get(villagerLevel).add((trader, rand) -> new MerchantOffer(new ItemStack(ModItems.CUT_CITRINE.get(), 32),
				new ItemStack(Items.ENCHANTED_GOLDEN_APPLE, 1), 3, 3, 0.02f));
		trades.get(villagerLevel)
				.add((trader, rand) -> new MerchantOffer(new ItemStack(ModItems.CUT_CITRINE.get(), rand.nextInt(2) + 2),
						new ItemStack(ModItems.CITRINE_HOE.get(), 1), 2, 12, 0.08f));

	}

	public static void addChiefTrades(Int2ObjectMap<List<VillagerTrades.ItemListing>> trades) {
		// TODO

		// The level of the villager, will be updated as the function goes on.
		int villagerLevel = 0;

		// Adds trades to the villager's level 1.
		// Syntax:
		// cost
		// result
		// maxUses
		// villagerXP
		// priceMultiplier
		villagerLevel = 1;

		// No trades for now.
	}

	public static void addGemsmithTrades(Int2ObjectMap<List<VillagerTrades.ItemListing>> trades) {

		// The level of the villager, will be updated as the function goes on.
		int villagerLevel = 0;

		// Adds trades to the villager's level 1.
		// Syntax:
		// cost
		// result
		// maxUses
		// villagerXP
		// priceMultiplier
		villagerLevel = 1;

		// Citrine conversion trades
		trades.get(villagerLevel)
				.add((trader, rand) -> new MerchantOffer(
						new ItemStack(ModItems.CITRINE_SHARD.get(), rand.nextInt(4) + 8),
						new ItemStack(ModItems.CUT_CITRINE.get(), 1), 4, 3, 0.02f));
		trades.get(villagerLevel)
				.add((trader, rand) -> new MerchantOffer(
						new ItemStack(ModBlocks.CITRINE_BLOCK.get(), rand.nextInt(2) + 2),
						new ItemStack(ModItems.CITRINE_SHARD.get(), rand.nextInt(3) + 1),
						new ItemStack(ModItems.CUT_CITRINE.get(), 1), 4, 3, 0.02f));
		trades.get(villagerLevel)
				.add((trader, rand) -> new MerchantOffer(new ItemStack(Items.AMETHYST_SHARD, rand.nextInt(8) + 16),
						new ItemStack(ModItems.CUT_CITRINE.get(), 1), 4, 3, 0.02f));
		trades.get(villagerLevel)
				.add((trader, rand) -> new MerchantOffer(new ItemStack(Blocks.AMETHYST_BLOCK, rand.nextInt(2) + 4),
						new ItemStack(Items.AMETHYST_SHARD, rand.nextInt(7) + 1),
						new ItemStack(ModItems.CUT_CITRINE.get(), 1), 4, 3, 0.02f));

		// Level 2 trades start here.
		villagerLevel = 2;
		// Fancy citrine trades, for clusters and copper
		trades.get(villagerLevel)
				.add((trader, rand) -> new MerchantOffer(
						new ItemStack(ModBlocks.CITRINE_CLUSTER.get(), rand.nextInt(2) + 2),
						new ItemStack(ModItems.CUT_CITRINE.get(), 1), 3, 6, 0.04f));
		trades.get(villagerLevel)
				.add((trader, rand) -> new MerchantOffer(new ItemStack(Items.COPPER_INGOT, rand.nextInt(4) + 16),
						new ItemStack(ModItems.CUT_CITRINE.get(), 1), 2, 6, 0.04f));
		trades.get(villagerLevel)
				.add((trader, rand) -> new MerchantOffer(new ItemStack(Items.STICK, rand.nextInt(8) + 24),
						new ItemStack(ModItems.CUT_CITRINE.get(), 1), 3, 6, 0.04f));

		// Level 3 trades start here.
		villagerLevel = 3;
		// Use citrine to buy various gem-like items
		trades.get(villagerLevel)
				.add((trader, rand) -> new MerchantOffer(new ItemStack(ModItems.CUT_CITRINE.get(), 1 + rand.nextInt(3)),
						new ItemStack(Items.BLUE_ICE, 1 + rand.nextInt(2)), 4, 6, 0.04f));
		trades.get(villagerLevel)
				.add((trader, rand) -> new MerchantOffer(new ItemStack(ModItems.CUT_CITRINE.get(), 1 + rand.nextInt(2)),
						new ItemStack(ModItems.CITRINE_SHARD.get(), 6 + rand.nextInt(3)), 6, 6, 0.04f));
		trades.get(villagerLevel).add((trader, rand) -> new MerchantOffer(new ItemStack(ModItems.CUT_CITRINE.get(), 1),
				new ItemStack(Items.PACKED_ICE, 2 + rand.nextInt(2)), 8, 6, 0.04f));

		// Use citrine to buy citrine furniture items.
		trades.get(villagerLevel)
				.add((trader, rand) -> new MerchantOffer(new ItemStack(ModItems.CUT_CITRINE.get(), 2 + rand.nextInt(1)),
						new ItemStack(ModBlocks.LARGE_CITRINE_BUD.get(), 2 + rand.nextInt(1)), 2, 6, 0.04f));
		trades.get(villagerLevel)
				.add((trader, rand) -> new MerchantOffer(new ItemStack(ModItems.CUT_CITRINE.get(), 2 + rand.nextInt(1)),
						new ItemStack(ModBlocks.MEDIUM_CITRINE_BUD.get(), 3 + rand.nextInt(2)), 3, 6, 0.04f));
		trades.get(villagerLevel)
				.add((trader, rand) -> new MerchantOffer(new ItemStack(ModItems.CUT_CITRINE.get(), 2 + rand.nextInt(1)),
						new ItemStack(ModBlocks.SMALL_CITRINE_BUD.get(), 4 + rand.nextInt(3)), 4, 6, 0.04f));
		trades.get(villagerLevel)
				.add((trader, rand) -> new MerchantOffer(new ItemStack(ModItems.CUT_CITRINE.get(), 2 + rand.nextInt(1)),
						new ItemStack(ModBlocks.SMALL_CITRINE_BUD.get(), 4 + rand.nextInt(3)), 5, 6, 0.04f));

		// Level 4 trades start here.
		villagerLevel = 4;
		trades.get(villagerLevel)
				.add((trader, rand) -> new MerchantOffer(new ItemStack(ModItems.CUT_CITRINE.get(), rand.nextInt(2) + 4),
						new ItemStack(ModItems.CITRINE_BOOTS.get(), 1), 2, 12, 0.08f));
		trades.get(villagerLevel)
				.add((trader, rand) -> new MerchantOffer(new ItemStack(ModItems.CUT_CITRINE.get(), rand.nextInt(2) + 8),
						new ItemStack(ModItems.CITRINE_CHESTPLATE.get(), 1), 2, 12, 0.08f));
		trades.get(villagerLevel)
				.add((trader, rand) -> new MerchantOffer(new ItemStack(ModItems.CUT_CITRINE.get(), rand.nextInt(2) + 5),
						new ItemStack(ModItems.CITRINE_HELMET.get(), 1), 2, 12, 0.08f));
		trades.get(villagerLevel)
				.add((trader, rand) -> new MerchantOffer(new ItemStack(ModItems.CUT_CITRINE.get(), rand.nextInt(2) + 7),
						new ItemStack(ModItems.CITRINE_LEGGINGS.get(), 1), 2, 12, 0.08f));

		// Level 5 trades start here.
		villagerLevel = 5;
		// Enchanted diamond armor trades.
		List<Integer> diamondArmorEnchantRange = List.of(20, 25);
		trades.get(villagerLevel)
				.add((trader, rand) -> new MerchantOffer(new ItemStack(ModItems.CUT_CITRINE.get(), rand.nextInt(4) + 6),
						EnchantmentHelper.enchantItem(rand, new ItemStack(Items.DIAMOND_BOOTS, 1),
								rand.nextInt(diamondArmorEnchantRange.get(0), diamondArmorEnchantRange.get(1)), true),
						1, 3, 0.08f));
		trades.get(villagerLevel)
				.add((trader, rand) -> new MerchantOffer(
						new ItemStack(ModItems.CUT_CITRINE.get(), rand.nextInt(4) + 14),
						EnchantmentHelper.enchantItem(rand, new ItemStack(Items.DIAMOND_CHESTPLATE, 1),
								rand.nextInt(diamondArmorEnchantRange.get(0), diamondArmorEnchantRange.get(1)), true),
						1, 3, 0.08f));
		trades.get(villagerLevel)
				.add((trader, rand) -> new MerchantOffer(new ItemStack(ModItems.CUT_CITRINE.get(), rand.nextInt(4) + 8),
						EnchantmentHelper.enchantItem(rand, new ItemStack(Items.DIAMOND_LEGGINGS, 1),
								rand.nextInt(diamondArmorEnchantRange.get(0), diamondArmorEnchantRange.get(1)), true),
						1, 3, 0.08f));
		trades.get(villagerLevel)
				.add((trader, rand) -> new MerchantOffer(
						new ItemStack(ModItems.CUT_CITRINE.get(), rand.nextInt(4) + 12),
						EnchantmentHelper.enchantItem(rand, new ItemStack(Items.DIAMOND_HELMET, 1),
								rand.nextInt(diamondArmorEnchantRange.get(0), diamondArmorEnchantRange.get(1)), true),
						1, 3, 0.08f));
		trades.get(villagerLevel)
				.add((trader, rand) -> new MerchantOffer(new ItemStack(ModItems.CUT_CITRINE.get(), rand.nextInt(2) + 4),
						new ItemStack(ModItems.CITRINE_SWORD.get(), 1), 2, 12, 0.08f));
		trades.get(villagerLevel)
				.add((trader, rand) -> new MerchantOffer(new ItemStack(ModItems.CUT_CITRINE.get(), rand.nextInt(2) + 8),
						new ItemStack(ModItems.CITRINE_SPEAR.get(), 1), 2, 12, 0.08f));
		trades.get(villagerLevel)
				.add((trader, rand) -> new MerchantOffer(new ItemStack(ModItems.CUT_CITRINE.get(), rand.nextInt(2) + 5),
						new ItemStack(ModItems.CITRINE_AXE.get(), 1), 2, 12, 0.08f));

	}

	public static void addScholarTrades(Int2ObjectMap<List<VillagerTrades.ItemListing>> trades) {

		// The level of the villager, will be updated as the function goes on.
		int villagerLevel = 0;

		// Adds trades to the villager's level 1.
		// Syntax:
		// cost
		// result
		// maxUses
		// villagerXP
		// priceMultiplier
		villagerLevel = 1;

		// Buys books and leather.
		trades.get(villagerLevel)
				.add((trader, rand) -> new MerchantOffer(new ItemStack(Items.BAKED_POTATO, 8 + rand.nextInt(4)),
						new ItemStack(ModItems.CUT_CITRINE.get(), 1), 4, 3, 0.02f));
		trades.get(villagerLevel)
				.add((trader, rand) -> new MerchantOffer(new ItemStack(Items.BREAD, 8 + rand.nextInt(4)),
						new ItemStack(ModItems.CUT_CITRINE.get(), 1), 4, 3, 0.02f));
		trades.get(villagerLevel)
				.add((trader, rand) -> new MerchantOffer(new ItemStack(Items.COOKED_CHICKEN, 8 + rand.nextInt(4)),
						new ItemStack(ModItems.CUT_CITRINE.get(), 1), 4, 3, 0.02f));
		trades.get(villagerLevel).add((trader, rand) -> new MerchantOffer(new ItemStack(Items.LEATHER, 8),
				new ItemStack(ModItems.CUT_CITRINE.get(), 1), 4, 3, 0.02f));
		trades.get(villagerLevel).add((trader, rand) -> new MerchantOffer(new ItemStack(Items.BOOK, 7),
				new ItemStack(ModItems.CUT_CITRINE.get(), 1), 4, 3, 0.02f));

		// Level 2 trades start here.
		villagerLevel = 2;
		trades.get(villagerLevel)
				.add((trader, rand) -> new MerchantOffer(new ItemStack(Items.INK_SAC, 6 + rand.nextInt(3)),
						new ItemStack(ModItems.CUT_CITRINE.get(), 1), 4, 3, 0.02f));
		trades.get(villagerLevel)
				.add((trader, rand) -> new MerchantOffer(new ItemStack(Items.GLOW_INK_SAC, 6 + rand.nextInt(3)),
						new ItemStack(ModItems.CUT_CITRINE.get(), 1), 4, 3, 0.02f));
		trades.get(villagerLevel)
				.add((trader, rand) -> new MerchantOffer(new ItemStack(Items.FEATHER, 24 + rand.nextInt(8)),
						new ItemStack(ModItems.CUT_CITRINE.get(), 1), 6, 3, 0.02f));
		trades.get(villagerLevel)
				.add((trader, rand) -> new MerchantOffer(new ItemStack(Items.GLASS_BOTTLE, 6 + rand.nextInt(2)),
						new ItemStack(ModItems.CUT_CITRINE.get(), 1), 6, 3, 0.02f));
		trades.get(villagerLevel)
				.add((trader, rand) -> new MerchantOffer(new ItemStack(Items.WRITABLE_BOOK, 4 + rand.nextInt(2)),
						new ItemStack(ModItems.CUT_CITRINE.get(), 1), 6, 3, 0.02f));
		trades.get(villagerLevel)
				.add((trader, rand) -> new MerchantOffer(new ItemStack(Items.RABBIT_HIDE, 2 + rand.nextInt(4)),
						new ItemStack(ModItems.CUT_CITRINE.get(), 2), 6, 3, 0.02f));

		// Level 3 trades start here.
		villagerLevel = 3;
		// Sells enchanted books.
		final List<Integer> l3BookEnchantRange = List.of(10, 20);
		trades.get(villagerLevel)
				.add((trader, rand) -> new MerchantOffer(new ItemStack(ModItems.CUT_CITRINE.get(), rand.nextInt(2) + 8),
						EnchantmentHelper.enchantItem(rand, new ItemStack(Items.BOOK, 1),
								rand.nextInt(l3BookEnchantRange.get(0), l3BookEnchantRange.get(1)), true),
						2, 3, 0.08f));
		trades.get(villagerLevel)
				.add((trader, rand) -> new MerchantOffer(new ItemStack(ModItems.CUT_CITRINE.get(), rand.nextInt(2) + 8),
						EnchantmentHelper.enchantItem(rand, new ItemStack(Items.BOOK, 1),
								rand.nextInt(l3BookEnchantRange.get(0), l3BookEnchantRange.get(1)), true),
						2, 3, 0.08f));

		// Level 4 trades start here.
		villagerLevel = 4;
		// Sells smithing templates.
		trades.get(villagerLevel).add((trader, rand) -> new MerchantOffer(new ItemStack(ModItems.CUT_CITRINE.get(), 12),
				new ItemStack(getTrimSmithingTemplate(rand), 1), 2, 6, 0.04f));
		trades.get(villagerLevel).add((trader, rand) -> new MerchantOffer(new ItemStack(ModItems.CUT_CITRINE.get(), 12),
				new ItemStack(getTrimSmithingTemplate(rand), 1), 2, 6, 0.04f));
		trades.get(villagerLevel).add((trader, rand) -> new MerchantOffer(new ItemStack(ModItems.CUT_CITRINE.get(), 24),
				new ItemStack(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE, 1), 1, 6, 0.04f));

		// Level 5 trades start here.
		villagerLevel = 5;
		// Sells enchanted books.
		final List<Integer> l5BookEnchantRange = List.of(25, 30);

		trades.get(villagerLevel)
				.add((trader, rand) -> new MerchantOffer(
						new ItemStack(ModItems.CUT_CITRINE.get(), rand.nextInt(6) + 14),
						EnchantmentHelper.enchantItem(rand, new ItemStack(Items.BOOK, 1),
								rand.nextInt(l5BookEnchantRange.get(0), l5BookEnchantRange.get(1)), true),
						2, 3, 0.08f));
		trades.get(villagerLevel)
				.add((trader, rand) -> new MerchantOffer(
						new ItemStack(ModItems.CUT_CITRINE.get(), rand.nextInt(6) + 14),
						EnchantmentHelper.enchantItem(rand, new ItemStack(Items.BOOK, 1),
								rand.nextInt(l5BookEnchantRange.get(0), l5BookEnchantRange.get(1)), true),
						2, 3, 0.08f));

	}

	public static void addTinkererTrades(Int2ObjectMap<List<VillagerTrades.ItemListing>> trades) {

		// The level of the villager, will be updated as the function goes on.
		int villagerLevel = 0;

		// Adds trades to the villager's level 1.
		// Syntax:
		// cost
		// result
		// maxUses
		// villagerXP
		// priceMultiplier
		villagerLevel = 1;

		// Buys redstone
		trades.get(villagerLevel)
				.add((trader, rand) -> new MerchantOffer(new ItemStack(Items.REDSTONE, rand.nextInt(4) + 8),
						new ItemStack(ModItems.CUT_CITRINE.get(), 1), 4, 3, 0.02f));
		trades.get(villagerLevel)
				.add((trader, rand) -> new MerchantOffer(new ItemStack(Items.STONE, rand.nextInt(6) + 12),
						new ItemStack(ModItems.CUT_CITRINE.get(), 1), 4, 3, 0.02f));
		trades.get(villagerLevel)
				.add((trader, rand) -> new MerchantOffer(new ItemStack(Items.QUARTZ, rand.nextInt(8) + 24),
						new ItemStack(ModItems.CUT_CITRINE.get(), 1), 4, 3, 0.02f));
		trades.get(villagerLevel)
				.add((trader, rand) -> new MerchantOffer(new ItemStack(Items.REDSTONE_TORCH, rand.nextInt(2) + 4),
						new ItemStack(ModItems.CUT_CITRINE.get(), 1), 4, 3, 0.02f));

		// Level 2 trades start here.
		villagerLevel = 2;
		trades.get(villagerLevel).add((trader, rand) -> new MerchantOffer(new ItemStack(ModItems.CUT_CITRINE.get(), 1),
				new ItemStack(Blocks.REPEATER, 3), 4, 3, 0.02f));
		trades.get(villagerLevel).add((trader, rand) -> new MerchantOffer(new ItemStack(ModItems.CUT_CITRINE.get(), 1),
				new ItemStack(Blocks.COMPARATOR, 3), 4, 3, 0.02f));
		trades.get(villagerLevel).add((trader, rand) -> new MerchantOffer(new ItemStack(ModItems.CUT_CITRINE.get(), 1),
				new ItemStack(Blocks.OBSERVER, 2), 4, 3, 0.02f));
		trades.get(villagerLevel).add((trader, rand) -> new MerchantOffer(new ItemStack(ModItems.CUT_CITRINE.get(), 1),
				new ItemStack(Blocks.DAYLIGHT_DETECTOR, 2), 4, 3, 0.02f));
		trades.get(villagerLevel).add((trader, rand) -> new MerchantOffer(new ItemStack(ModItems.CUT_CITRINE.get(), 1),
				new ItemStack(Blocks.DISPENSER, 2), 4, 3, 0.02f));

		// Level 3 trades start here.
		villagerLevel = 3;
		trades.get(villagerLevel)
				.add((trader, rand) -> new MerchantOffer(new ItemStack(Items.COPPER_INGOT, rand.nextInt(8) + 20),
						new ItemStack(ModItems.CUT_CITRINE.get(), 1), 4, 3, 0.02f));
		trades.get(villagerLevel).add((trader, rand) -> new MerchantOffer(new ItemStack(ModItems.CUT_CITRINE.get(), 1),
				new ItemStack(Blocks.LIGHTNING_ROD, 4), 4, 3, 0.02f));
		trades.get(villagerLevel).add((trader, rand) -> new MerchantOffer(new ItemStack(ModItems.CUT_CITRINE.get(), 1),
				new ItemStack(ModBlocks.CLOUD_CONVERTER.get(), 2), 4, 3, 0.02f));
		trades.get(villagerLevel).add((trader, rand) -> new MerchantOffer(new ItemStack(ModItems.CUT_CITRINE.get(), 1),
				new ItemStack(ModBlocks.CLOUD_INVERTER.get(), 2), 4, 3, 0.02f));
		trades.get(villagerLevel).add((trader, rand) -> new MerchantOffer(new ItemStack(ModItems.CUT_CITRINE.get(), 1),
				new ItemStack(ModBlocks.CLOUD_DETECTOR.get(), 2), 4, 3, 0.02f));
		
		// Level 4 trades start here.
		villagerLevel = 4;
		trades.get(villagerLevel).add((trader, rand) -> new MerchantOffer(new ItemStack(Blocks.REDSTONE_ORE, 3),
				new ItemStack(ModItems.CUT_CITRINE.get(), 1), 4, 3, 0.02f));
		trades.get(villagerLevel).add((trader, rand) -> new MerchantOffer(new ItemStack(ModItems.CUT_CITRINE.get(), 2),
				new ItemStack(Blocks.REDSTONE_LAMP, 4), 4, 3, 0.02f));
		trades.get(villagerLevel).add((trader, rand) -> new MerchantOffer(new ItemStack(ModItems.CUT_CITRINE.get(), 1),
				new ItemStack(ModBlocks.ZIRCON_LAMP.get(), 4), 4, 3, 0.02f));
		trades.get(villagerLevel).add((trader, rand) -> new MerchantOffer(new ItemStack(ModItems.CUT_CITRINE.get(), 1),
				new ItemStack(Blocks.TNT, 3), 4, 3, 0.02f));

		// Level 5 trades start here.
		villagerLevel = 5;
		trades.get(villagerLevel).add((trader, rand) -> new MerchantOffer(new ItemStack(ModItems.CUT_CITRINE.get(), 1),
				new ItemStack(Items.RAIL, 12 + rand.nextInt(4)), 4, 3, 0.02f));
		trades.get(villagerLevel).add((trader, rand) -> new MerchantOffer(new ItemStack(ModItems.CUT_CITRINE.get(), 1),
				new ItemStack(Items.ACTIVATOR_RAIL, 4 + rand.nextInt(2)), 4, 3, 0.02f));
		trades.get(villagerLevel).add((trader, rand) -> new MerchantOffer(new ItemStack(ModItems.CUT_CITRINE.get(), 1),
				new ItemStack(Items.POWERED_RAIL, 4 + rand.nextInt(2)), 4, 3, 0.02f));
		trades.get(villagerLevel).add((trader, rand) -> new MerchantOffer(new ItemStack(ModItems.CUT_CITRINE.get(), 1),
				new ItemStack(Items.DETECTOR_RAIL, 4 + rand.nextInt(2)), 4, 3, 0.02f));

	}

	// Returns a random type of glass
	public static Block getStainedGlassColor(RandomSource rand) {
		return STAINED_GLASS_TYPES[rand.nextInt(STAINED_GLASS_TYPES.length)];
	}

	// Returns a random armor template
	public static Item getTrimSmithingTemplate(RandomSource rand) {
		return TRIM_SMITHING_TEMPLATE_ITEMS[rand.nextInt(TRIM_SMITHING_TEMPLATE_ITEMS.length)];
	}
}
