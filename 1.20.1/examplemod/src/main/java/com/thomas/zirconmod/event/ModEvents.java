package com.thomas.zirconmod.event;

import java.util.List;

import com.thomas.zirconmod.ZirconMod;
import com.thomas.zirconmod.item.ModItems;
import com.thomas.zirconmod.villager.ModVillagers;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.event.village.WandererTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ZirconMod.MOD_ID)
public class ModEvents {

	// Adds custom trades to villagers.
	@SubscribeEvent
	public static void addCustomTrades(VillagerTradesEvent event) {

		// Adds blueberry trades to farmer villagers.
		if (event.getType() == VillagerProfession.FARMER) {
			Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();
			// Level 1
			// Adds blueberry trades
			trades.get(1).add((pTrader, pRandom) -> new MerchantOffer(new ItemStack(ModItems.BLUEBERRY.get(), 8),
					new ItemStack(Items.EMERALD, 2), 16, 2, 0.02f));
			trades.get(1).add((pTrader, pRandom) -> new MerchantOffer(new ItemStack(Items.EMERALD, 4),
					new ItemStack(ModItems.BLUEBERRY_SEEDS.get(), 3), 16, 2, 0.02f));
		}

		else if (event.getType() == ModVillagers.FORESTER.get()) {
			// Gets the list of trades.
			Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();

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

		else if (event.getType() == ModVillagers.GEMSMITH.get()) {
			// Gets the list of trades.
			Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();

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

			// Amethyst conversion trades
			trades.get(villagerLevel)
					.add((trader, rand) -> new MerchantOffer(new ItemStack(Items.AMETHYST_SHARD, rand.nextInt(8) + 16),
							new ItemStack(ModItems.CUT_CITRINE.get(), 1), 8, 3, 0.02f));
			trades.get(villagerLevel)
					.add((trader, rand) -> new MerchantOffer(new ItemStack(Items.AMETHYST_BLOCK, rand.nextInt(2) + 3),
							new ItemStack(Items.AMETHYST_SHARD, rand.nextInt(7) + 1),
							new ItemStack(ModItems.CUT_CITRINE.get(), 1), 16, 3, 0.02f));

			// Level 2 trades start here.
			villagerLevel = 2;
			// Fancy amethyst trades, for clusters and copper
			trades.get(villagerLevel)
					.add((trader, rand) -> new MerchantOffer(new ItemStack(Items.AMETHYST_CLUSTER, rand.nextInt(2) + 2),
							new ItemStack(ModItems.CUT_CITRINE.get(), 1), 16, 6, 0.04f));
			trades.get(villagerLevel)
					.add((trader, rand) -> new MerchantOffer(new ItemStack(Items.COPPER_INGOT, rand.nextInt(8) + 16),
							new ItemStack(ModItems.CUT_CITRINE.get(), 1), 8, 6, 0.04f));
			trades.get(villagerLevel)
					.add((trader, rand) -> new MerchantOffer(new ItemStack(Items.STICK, rand.nextInt(8) + 24),
							new ItemStack(ModItems.CUT_CITRINE.get(), 1), 8, 6, 0.04f));

			// Level 3 trades start here.
			villagerLevel = 3;
			// Use amethysts to buy various gem-like items
			trades.get(villagerLevel)
					.add((trader, rand) -> new MerchantOffer(new ItemStack(ModItems.CUT_CITRINE.get(), 2),
							new ItemStack(Items.OBSIDIAN, 3), new ItemStack(Items.CRYING_OBSIDIAN, 3), 16, 6, 0.04f));
			trades.get(villagerLevel)
					.add((trader, rand) -> new MerchantOffer(new ItemStack(ModItems.CUT_CITRINE.get(), 1),
							new ItemStack(Items.CRYING_OBSIDIAN, 2), new ItemStack(Items.OBSIDIAN, 2), 16, 6, 0.04f));
			trades.get(villagerLevel)
					.add((trader, rand) -> new MerchantOffer(new ItemStack(ModItems.CUT_CITRINE.get(), 1 + rand.nextInt(2)),
							new ItemStack(ModItems.ZIRCON_SHARD.get(), 4 + rand.nextInt(2)), 16, 6, 0.04f));
			trades.get(villagerLevel)
					.add((trader, rand) -> new MerchantOffer(new ItemStack(ModItems.CUT_CITRINE.get(), 1),
							new ItemStack(Items.AMETHYST_SHARD, 10 + rand.nextInt(6)), 16, 6, 0.04f));
			trades.get(villagerLevel)
					.add((trader, rand) -> new MerchantOffer(new ItemStack(ModItems.CUT_CITRINE.get(), 1),
							new ItemStack(Items.GLASS, 3 + rand.nextInt(4)), 16, 6, 0.04f));
			trades.get(villagerLevel)
			.add((trader, rand) -> new MerchantOffer(new ItemStack(ModItems.CUT_CITRINE.get(), 1),
					new ItemStack(Items.PRISMARINE_CRYSTALS, 3 + rand.nextInt(3)), 16, 6, 0.04f));
			// Use amethysts to buy amethyst furniture items.
			
			
			// Level 4 trades start here.
			villagerLevel = 4;
			trades.get(villagerLevel)
					.add((trader, rand) -> new MerchantOffer(
							new ItemStack(ModItems.CUT_CITRINE.get(), rand.nextInt(2) + 4),
							new ItemStack(ModItems.CITRINE_BOOTS.get(), 1), 2, 12, 0.08f));
			trades.get(villagerLevel)
					.add((trader, rand) -> new MerchantOffer(
							new ItemStack(ModItems.CUT_CITRINE.get(), rand.nextInt(2) + 8),
							new ItemStack(ModItems.CITRINE_CHESTPLATE.get(), 1), 2, 12, 0.08f));
			trades.get(villagerLevel)
					.add((trader, rand) -> new MerchantOffer(
							new ItemStack(ModItems.CUT_CITRINE.get(), rand.nextInt(2) + 5),
							new ItemStack(ModItems.CITRINE_HELMET.get(), 1), 2, 12, 0.08f));
			trades.get(villagerLevel)
					.add((trader, rand) -> new MerchantOffer(
							new ItemStack(ModItems.CUT_CITRINE.get(), rand.nextInt(2) + 7),
							new ItemStack(ModItems.CITRINE_LEGGINGS.get(), 1), 2, 12, 0.08f));

			// Level 5 trades start here.
			villagerLevel = 5;
			// Enchanted diamond armor trades.
			trades.get(villagerLevel)
					.add((trader, rand) -> new MerchantOffer(
							new ItemStack(ModItems.CUT_CITRINE.get(), rand.nextInt(4) + 6), EnchantmentHelper
									.enchantItem(rand, new ItemStack(Items.DIAMOND_BOOTS, 1), rand.nextInt(2, 4), true),
							1, 3, 0.08f));
			trades.get(villagerLevel)
					.add((trader, rand) -> new MerchantOffer(
							new ItemStack(ModItems.CUT_CITRINE.get(), rand.nextInt(4) + 14),
							EnchantmentHelper.enchantItem(rand, new ItemStack(Items.DIAMOND_CHESTPLATE, 1),
									rand.nextInt(2, 4), true),
							1, 3, 0.08f));
			trades.get(villagerLevel)
					.add((trader, rand) -> new MerchantOffer(
							new ItemStack(ModItems.CUT_CITRINE.get(), rand.nextInt(4) + 8),
							EnchantmentHelper.enchantItem(rand, new ItemStack(Items.DIAMOND_LEGGINGS, 1),
									rand.nextInt(2, 4), true),
							1, 3, 0.08f));
			trades.get(villagerLevel)
					.add((trader, rand) -> new MerchantOffer(
							new ItemStack(ModItems.CUT_CITRINE.get(), rand.nextInt(4) + 12),
							EnchantmentHelper.enchantItem(rand, new ItemStack(Items.DIAMOND_HELMET, 1),
									rand.nextInt(2, 4), true),
							1, 3, 0.08f));
		}
	}

	@SubscribeEvent
	public static void addCustomWanderingTrades(WandererTradesEvent event) {
		List<VillagerTrades.ItemListing> genericTrades = event.getGenericTrades();
		List<VillagerTrades.ItemListing> rareTrades = event.getRareTrades();

		// Sell blueberry seeds
		genericTrades.add((pTrader, pRandom) -> new MerchantOffer(new ItemStack(Items.EMERALD, 4),
				new ItemStack(ModItems.BLUEBERRY_SEEDS.get(), 2), 3, 2, 0.2f));

		// Buying common items
		genericTrades.add((pTrader, pRandom) -> new MerchantOffer(new ItemStack(Items.ACACIA_SAPLING, 2),
				new ItemStack(Items.EMERALD, 2), 3, 2, 0.2f));
		genericTrades.add((pTrader, pRandom) -> new MerchantOffer(new ItemStack(Items.OAK_SAPLING, 2),
				new ItemStack(Items.EMERALD, 1), 3, 2, 0.2f));
		genericTrades.add((pTrader, pRandom) -> new MerchantOffer(new ItemStack(Items.AMETHYST_SHARD, 16),
				new ItemStack(Items.EMERALD, 1), 3, 2, 0.2f));
		genericTrades.add((pTrader, pRandom) -> new MerchantOffer(new ItemStack(Items.SUGAR, 8),
				new ItemStack(Items.EMERALD, 1), 3, 2, 0.2f));
		genericTrades.add((pTrader, pRandom) -> new MerchantOffer(new ItemStack(Items.BAKED_POTATO, 4),
				new ItemStack(Items.EMERALD, 1), 3, 2, 0.2f));

		// Selling rare items:
		rareTrades.add((pTrader, pRandom) -> new MerchantOffer(new ItemStack(Items.EMERALD, 8),
				new ItemStack(ModItems.PINE_CONE.get(), 8), 2, 4, 0.2f));
		// Buying rare items:
		rareTrades.add((pTrader, pRandom) -> new MerchantOffer(new ItemStack(Items.FERMENTED_SPIDER_EYE, 1),
				new ItemStack(Items.EMERALD, 3), 3, 2, 0.2f));
		rareTrades.add((pTrader, pRandom) -> new MerchantOffer(new ItemStack(Items.GLASS_BOTTLE, 3),
				new ItemStack(Items.EMERALD, 1), 3, 2, 0.2f));
		rareTrades.add((pTrader, pRandom) -> new MerchantOffer(new ItemStack(Items.MILK_BUCKET, 1),
				new ItemStack(Items.EMERALD, 3), 3, 2, 0.2f));
		rareTrades.add((pTrader, pRandom) -> new MerchantOffer(new ItemStack(Items.GOLDEN_CARROT, 1),
				new ItemStack(Items.EMERALD, 3), 3, 2, 0.2f));
	}
}
