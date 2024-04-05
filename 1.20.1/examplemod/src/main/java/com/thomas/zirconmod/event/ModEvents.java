package com.thomas.zirconmod.event;

import java.util.List;

import com.thomas.zirconmod.ZirconMod;
import com.thomas.zirconmod.block.entity.menu.NetheriteAnvilMenu;
import com.thomas.zirconmod.entity.ModEntityType;
import com.thomas.zirconmod.entity.custom.GustEntity;
import com.thomas.zirconmod.entity.custom.TempestEntity;
import com.thomas.zirconmod.entity.custom.WraithEntity;
import com.thomas.zirconmod.item.ModItems;
import com.thomas.zirconmod.util.ModWeatheringCopper;
import com.thomas.zirconmod.util.Reflection;
import com.thomas.zirconmod.util.Utilities;
import com.thomas.zirconmod.util.Waxable;
import com.thomas.zirconmod.villager.ModVillagerTrades;
import com.thomas.zirconmod.villager.ModVillagers;
import com.thomas.zirconmod.worldgen.dimension.ModDimensions;
import com.thomas.zirconmod.worldgen.portal.ModTeleporter;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrownTrident;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.HoneycombItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;
import net.minecraftforge.event.entity.EntityEvent.EnteringSection;
import net.minecraftforge.event.entity.living.MobSpawnEvent.SpawnPlacementCheck;
import net.minecraftforge.event.entity.player.AnvilRepairEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.EntityInteract;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.minecraftforge.event.level.BlockEvent.BlockToolModificationEvent;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.event.village.WandererTradesEvent;
import net.minecraftforge.eventbus.api.Event.Result;
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

		else if (event.getType() == VillagerProfession.BUTCHER) {
			Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();
			ModVillagerTrades.addButcherTrades(trades);
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

	@SubscribeEvent
	public static void catchGustEvent(EntityInteract event) {

		Player player = event.getEntity();
		Entity entity = event.getTarget();
		InteractionHand hand = event.getHand();
		ItemStack stack = event.getItemStack();

		if (entity instanceof GustEntity gust && event.getSide().isServer()) {
			gust.discard();
			Utilities.addParticlesAroundPositionServer((ServerLevel) event.getLevel(), gust.position(),
					ParticleTypes.CLOUD, 1.0, 10);

			if (stack.is(Items.GLASS_BOTTLE)) {
				stack.setCount(stack.getCount() - 1);
				player.setItemInHand(hand, stack);
				player.addItem(new ItemStack(ModItems.GUST_BOTTLE.get()));
			}

		}
	}

	@SubscribeEvent
	public static void wraithSpawnEvent(SpawnPlacementCheck event) {
		// If a wraith spawns naturally in bright light...
		// cancel. Unless it's thundering.
		if (event.getEntityType().equals(ModEntityType.WRAITH_ENTITY.get())) {
			// System.out.println("Attempting to spawn wraith.");
			// System.out.println("Brightness is " +
			// event.getLevel().getRawBrightness(event.getPos(), 0));
			if (event.getLevel().getLevel().isThundering()
					|| event.getLevel().getRawBrightness(event.getPos(), 0) > WraithEntity.getMaxLightLevel()) {
				// System.out.println("Canceling spawn in " +
				// event.getLevel().getRawBrightness(event.getPos(), 0));
				event.setResult(Result.DENY);
			}
		}
	}

	@SubscribeEvent
	public static void tempestSpawnEvent(SpawnPlacementCheck event) {
		// If a tempest spawns naturally...
		// cancel. Unless it's thundering.
		if (event.getEntityType().equals(ModEntityType.TEMPEST_ENTITY.get())) {
			// System.out.println("Attempting to spawn tempest.");
			// System.out.println("Brightness is " +
			// event.getLevel().getRawBrightness(event.getPos(), 0));
			ServerLevelAccessor level = event.getLevel();

			// Ensure that it is thundering.
			if (!level.getLevel().isThundering()) {
				// System.out.println("Canceling spawn in " +
				// event.getLevel().getRawBrightness(event.getPos(), 0));
				event.setResult(Result.DENY);
			}
			// Now check to see if there are more tempests than players. If so, cancel.
			// This is to prevent being overwhelmed by tempests.
			List<TempestEntity> entities = level.getEntitiesOfClass(TempestEntity.class,
					AABB.ofSize(event.getPos().getCenter(), 256, 256, 256));
			if (entities != null && entities.size() != 0) {
				event.setResult(Result.DENY);
			}
		}
	}

	@SubscribeEvent
	public static void dontBreakNetheriteAnvilEvent(AnvilRepairEvent event) {

		Player player = event.getEntity();

		AbstractContainerMenu menu = player.containerMenu;
		if (menu instanceof NetheriteAnvilMenu) {
			event.setBreakChance(0);
		}
	}

	/*
	 * @SubscribeEvent public static void travelToAndFromSkyEvent(LivingTickEvent
	 * event) {
	 * 
	 * LivingEntity entity = event.getEntity(); Level level = entity.level(); if
	 * (level instanceof ServerLevel sl) {
	 * 
	 * // If going to the sky. if (entity.level().dimension() == Level.OVERWORLD) {
	 * if (entity.position().y > 512) {
	 * 
	 * // Store some data. Vec3 pos = entity.position();
	 * 
	 * MinecraftServer server = sl.getServer(); ServerLevel sky_dim =
	 * server.getLevel(ModDimensions.SKY_DIM_LEVEL_KEY); // Move to sky
	 * entity.changeDimension(sky_dim, new ModTeleporter()); // Set the data again.
	 * entity.teleportTo(pos.x, 0, pos.z); } }
	 * 
	 * // If going to the overworld. if (entity.level().dimension() ==
	 * ModDimensions.SKY_DIM_LEVEL_KEY) { if (entity.position().y < -32) {
	 * 
	 * // Store some data. Vec3 pos = entity.position();
	 * 
	 * MinecraftServer server = sl.getServer(); ServerLevel overworld =
	 * server.getLevel(Level.OVERWORLD); // Move to overworld
	 * entity.changeDimension(overworld, new ModTeleporter()); // Set the data
	 * again. entity.teleportTo(pos.x, 480, pos.z); } } } }
	 */
	@SuppressWarnings("unchecked")
	@SubscribeEvent
	public static void travelToAndFromSkyEvent(EnteringSection event) {

		Entity entity = event.getEntity();
		Level level = entity.level();
		if (level instanceof ServerLevel sl) {

			// If going to the sky.
			if (entity.level().dimension() == Level.OVERWORLD) {
				if (entity.position().y > 512) {

					// Store some data.
					Vec3 pos = entity.position();

					MinecraftServer server = sl.getServer();
					ServerLevel sky_dim = server.getLevel(ModDimensions.SKY_DIM_LEVEL_KEY);
					// Move to sky
					entity.changeDimension(sky_dim, new ModTeleporter());
					// Set the data again.
					entity.teleportTo(pos.x, 0, pos.z);
				}
			}

			// If going to the overworld.
			if (entity.level().dimension() == ModDimensions.SKY_DIM_LEVEL_KEY) {
				if (entity.position().y < -32) {

					// Check if it's a trident.
					if (entity instanceof ThrownTrident trident) {
						Object rawLoyaltyAccessor = null;
						EntityDataAccessor<Byte> loyaltyAccessor;
						try {
							rawLoyaltyAccessor = Reflection.getFromStaticFinal(ThrownTrident.class, "ID_LOYALTY");

							if (!(rawLoyaltyAccessor instanceof EntityDataAccessor)) {
								throw new NoSuchFieldException("Cannot access ID_LOYALTY in ThrownTrident.");
							} else {
								loyaltyAccessor = (EntityDataAccessor<Byte>) rawLoyaltyAccessor;
								if (trident.getEntityData().get(loyaltyAccessor) != 0) {
									// If so, trigger returning.
									trident.setNoPhysics(true);
									trident.setDeltaMovement(0, 1, 0);
									return;
								}
							}
						} catch (NoSuchFieldException e) {
							e.printStackTrace();
						} catch (SecurityException e) {
							e.printStackTrace();
						} catch (IllegalArgumentException e) {
							e.printStackTrace();
						} catch (IllegalAccessException e) {
							e.printStackTrace();
						}
					}

					// Store some data.
					Vec3 pos = entity.position();

					MinecraftServer server = sl.getServer();
					ServerLevel overworld = server.getLevel(Level.OVERWORLD);
					// Move to overworld
					entity.changeDimension(overworld, new ModTeleporter());
					// Set the data again.
					entity.teleportTo(pos.x, 480, pos.z);
				}
			}
		}
	}

	@SubscribeEvent
	public static void removeWaxOrScrapeEvent(BlockToolModificationEvent event) {

		BlockState originalState = event.getState();

		ToolAction action = event.getToolAction();

		// UseOnContext context = event.getContext();

		// BlockPos pos = context.getClickedPos();

		boolean isSimulated = event.isSimulated();

		if (!isSimulated) {
			// Check if the action is correct.
			// Now wax can be applied or removed.
			if (action.equals(ToolActions.AXE_WAX_OFF)) {
				// Removing wax!
				event.setFinalState(Waxable.removeWax(originalState));
				event.setResult(Result.ALLOW);
			}
			// Or oxidation can be removed.
			else if (action.equals(ToolActions.AXE_SCRAPE)) {
				event.setFinalState(ModWeatheringCopper.getPrevious(originalState).orElse(originalState));
				event.setResult(Result.ALLOW);
			}

			// System.out.println("Performing " + action + " on " + originalState + " at " +
			// pos + ".");
		}
	}

	@SubscribeEvent
	public static void applyWaxEvent(RightClickBlock event) {

		boolean isServerSide = event.getSide().isServer();

		Level level = event.getLevel();

		Player player = event.getEntity();

		BlockPos pos = event.getPos();

		ItemStack itemStack = event.getItemStack();

		BlockState state = level.getBlockState(pos);

		// Check if the level is server side, and if the item extends Honeycomb.
		if (itemStack.getItem() instanceof HoneycombItem) {
			// Check if the block is waxable.
			if (Waxable.canApplyWax(state)) {
				// If so update the state.
				BlockState waxed = Waxable.applyWax(state);
				level.setBlock(pos, waxed, 11);
				// Grant achievements and particles and stuff.
				// Copied from honeycomb
				// I have honestly no idea what all of this does.
				if (isServerSide) {
					CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer) player, pos, itemStack);
				}
				itemStack.shrink(1);
				level.levelEvent(player, 3003, pos, 0);
				level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(player, waxed));
				event.setUseItem(Result.DENY);
				event.setUseBlock(Result.DENY);
			}
		}
	}

}
