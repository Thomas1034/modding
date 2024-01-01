package com.thomas.zirconmod.event;

import java.util.List;

import com.thomas.zirconmod.ZirconMod;
import com.thomas.zirconmod.item.ModItems;
import com.thomas.zirconmod.villager.ModVillagerTrades;
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
			ModVillagerTrades.addFarmerTrades(trades);
		}

		else if (event.getType() == ModVillagers.FORESTER.get()) {
			// Gets the list of trades.
			Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();
			ModVillagerTrades.addForesterTrades(trades);
		}
		
		else if (event.getType() == ModVillagers.ARCHITECT.get()) {
			// Gets the list of trades.
			Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();
			ModVillagerTrades.addArchitectTrades(trades);
		}

		else if (event.getType() == ModVillagers.GEMSMITH.get()) {
			// Gets the list of trades.
			Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();
			ModVillagerTrades.addGemsmithTrades(trades);
		}
		
		else if (event.getType() == ModVillagers.SCHOLAR.get()) {
			// Gets the list of trades.
			Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();
			ModVillagerTrades.addScholarTrades(trades);
		}
		
		else if (event.getType() == ModVillagers.TINKERER.get()) {
			// Gets the list of trades.
			Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();
			ModVillagerTrades.addTinkererTrades(trades);
		}
		
		else if (event.getType() == ModVillagers.CHIEF.get()) {
			// Gets the list of trades.
			Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();
			ModVillagerTrades.addChiefTrades(trades);
		}
		

		else if (event.getType() == ModVillagers.BOTANIST.get()) {
			// Gets the list of trades.
			Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();
			ModVillagerTrades.addBotanistTrades(trades);
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
