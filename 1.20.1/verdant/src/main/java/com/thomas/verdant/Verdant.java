package com.thomas.verdant;

import org.slf4j.Logger;

import com.mojang.logging.LogUtils;
import com.thomas.verdant.block.ModBlocks;
import com.thomas.verdant.block.custom.extensible.ExtensibleCakeBlock;
import com.thomas.verdant.block.entity.ModBlockEntities;
import com.thomas.verdant.datagen.ModLootModifiers;
import com.thomas.verdant.effect.ModMobEffects;
import com.thomas.verdant.effect.custom.FoodPoisoningEffect;
import com.thomas.verdant.enchantment.ModEnchantments;
import com.thomas.verdant.entity.ModEntityTypes;
import com.thomas.verdant.entity.client.renderer.HallucinatedCreeperRenderer;
import com.thomas.verdant.entity.client.renderer.OvergrownSkeletonRenderer;
import com.thomas.verdant.entity.client.renderer.OvergrownZombieRenderer;
import com.thomas.verdant.entity.client.renderer.PoisonIvyArrowRenderer;
import com.thomas.verdant.entity.custom.PoisonIvyArrowEntity;
import com.thomas.verdant.item.ModCreativeModeTabs;
import com.thomas.verdant.item.ModItems;
import com.thomas.verdant.item.custom.RopeCoilItem;
import com.thomas.verdant.modfeature.FeaturePlacer;
import com.thomas.verdant.network.ModPacketHandler;
import com.thomas.verdant.painting.ModPaintings;
import com.thomas.verdant.potion.BetterBrewingRecipe;
import com.thomas.verdant.potion.ModPotions;
import com.thomas.verdant.screen.ModMenuTypes;
import com.thomas.verdant.screen.screen.FishTrapScreen;
import com.thomas.verdant.util.data.DataRegistries;

import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.core.Position;
import net.minecraft.core.dispenser.AbstractProjectileDispenseBehavior;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

// Remember 1:33

// bugfixes:
// added lang string for verdant heartwood pickaxe

// to do: 
// make data add and override properly



// add recipes!

// 
// ready to em-bark achievement - get heartwood armor
// tree chugger - drink an antidote
// shattered heart - collect a heart fragment, full of mysterious energy
// 

// List for next update:
// Blowpipe - consumes air meter to fire darts; very short cooldown, mostly limited by suffocation
// Blowdarts (maybe automatically register potions, like with arrows?)
// Special blowdarts that aren't made with potions - specialized effects for different situations and monsters
// 
// Two new spider types.
// Web-shooting spiders?
// Trapdoor spiders?

// wild parsnip (inflicts photosensitive, causes some damage in sky access in the day, effect strength determines how often damage occurs)
// monkshood (Causes paralysis, i.e. slowness, weakness, and mining fatigue).

// Scented effect; attracts animals. Higher levels cause them to spontaneously fall in love.
// Made with a fermented spider eye into stench potion

// Think about giant hogweed.

// Levels of infection: sprouting, leafing, rooting, blooming.
// sprouting: speed, regen, haste
// leafing:   regen, haste, resistance, phototrophic (restores saturation based on light level)
// rooting:   slowness, regen, haste, resistance 2, phototrophic 2
// blooming: slowness 3, mining fatigue, wither, phototrophic 3

// The value here should match an entry in the META-INF/mods.toml file
@Mod(Verdant.MOD_ID)
public class Verdant {
	// Define mod id in a common place for everything to reference
	public static final String MOD_ID = "verdant";
	// Directly reference a slf4j logger
	@SuppressWarnings("unused")
	private static final Logger LOGGER = LogUtils.getLogger();

	public Verdant() {

		IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

		ModCreativeModeTabs.register(modEventBus);

		ModItems.register(modEventBus);
		ModBlocks.register(modEventBus);
		ModPaintings.register(modEventBus);
		ModEnchantments.register(modEventBus);
		ModMobEffects.register(modEventBus);
		ModPotions.register(modEventBus);

		ModBlockEntities.register(modEventBus);
		ModMenuTypes.register(modEventBus);
		ModLootModifiers.register(modEventBus);

		ModEntityTypes.register(modEventBus);

		ModPacketHandler.register();

		// Register the commonSetup method for modloading
		modEventBus.addListener(this::commonSetup);

		// Accessed to ensure that the class is loaded early in the mod's lifecycle.
		DataRegistries.ensureAlive();

		// Register ourselves for server and other game events we are interested in
		MinecraftForge.EVENT_BUS.register(this);
	}

	private void commonSetup(final FMLCommonSetupEvent event) {
		// Some common setup code
		// LOGGER.info("HELLO FROM COMMON SETUP");
		// Add the right click to add to flowerpot event.
		((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ModBlocks.BLEEDING_HEART.getId(),
				ModBlocks.POTTED_BLEEDING_HEART);
		((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ModBlocks.WILD_COFFEE.getId(), ModBlocks.POTTED_WILD_COFFEE);
		((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ModBlocks.THORN_BUSH.getId(), ModBlocks.POTTED_THORN_BUSH);
		((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ModBlocks.BUSH.getId(), ModBlocks.POTTED_BUSH);
		((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ModBlocks.WILD_UBE.getId(), ModBlocks.POTTED_WILD_UBE);
		((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ModBlocks.WILD_CASSAVA.getId(), ModBlocks.POTTED_WILD_CASSAVA);

		// Register potions
		BrewingRecipeRegistry.addRecipe(
				new BetterBrewingRecipe(Potions.WATER, ModItems.ROASTED_COFFEE.get(), ModPotions.CAFFEINE.get()));
		BrewingRecipeRegistry.addRecipe(new BetterBrewingRecipe(ModPotions.CAFFEINE.get(),
				ModItems.ROASTED_COFFEE.get(), ModPotions.LONG_CAFFEINE.get()));
		BrewingRecipeRegistry.addRecipe(
				new BetterBrewingRecipe(ModPotions.CAFFEINE.get(), Items.SUGAR, ModPotions.STRONG_CAFFEINE.get()));

		BrewingRecipeRegistry.addRecipe(
				new BetterBrewingRecipe(Potions.THICK, ModItems.SPARKLING_STARCH.get(), ModPotions.COLLOID.get()));
		BrewingRecipeRegistry.addRecipe(
				new BetterBrewingRecipe(ModPotions.COLLOID.get(), Items.REDSTONE, ModPotions.LONG_COLLOID.get()));
		BrewingRecipeRegistry.addRecipe(
				new BetterBrewingRecipe(ModPotions.COLLOID.get(), Items.GLOWSTONE, ModPotions.STRONG_COLLOID.get()));

		BrewingRecipeRegistry.addRecipe(new BetterBrewingRecipe(Potions.THICK,
				ModBlocks.STINKING_BLOSSOM.get().asItem(), ModPotions.STENCH.get()));
		BrewingRecipeRegistry.addRecipe(
				new BetterBrewingRecipe(ModPotions.STENCH.get(), Items.REDSTONE, ModPotions.LONG_STENCH.get()));
		BrewingRecipeRegistry.addRecipe(
				new BetterBrewingRecipe(ModPotions.STENCH.get(), Items.GLOWSTONE_DUST, ModPotions.STRONG_STENCH.get()));

		BrewingRecipeRegistry.addRecipe(
				new BetterBrewingRecipe(Potions.AWKWARD, ModItems.HEART_FRAGMENT.get(), ModPotions.ANTIDOTE.get()));
		BrewingRecipeRegistry.addRecipe(
				new BetterBrewingRecipe(ModPotions.ANTIDOTE.get(), Items.REDSTONE, ModPotions.LONG_ANTIDOTE.get()));

		BrewingRecipeRegistry.addRecipe(
				new BetterBrewingRecipe(Potions.AWKWARD, ModItems.WATER_HEMLOCK.get(), ModPotions.ASPHYXIATING.get()));
		BrewingRecipeRegistry.addRecipe(new BetterBrewingRecipe(ModPotions.ASPHYXIATING.get(), Items.REDSTONE,
				ModPotions.LONG_ASPHYXIATING.get()));
		BrewingRecipeRegistry.addRecipe(new BetterBrewingRecipe(ModPotions.ASPHYXIATING.get(), Items.GLOWSTONE_DUST,
				ModPotions.STRONG_ASPHYXIATING.get()));

		// Register compostables.
		ComposterBlock.COMPOSTABLES.put(ModBlocks.POISON_IVY_VERDANT_LEAVES.get().asItem(), 0.3f);
		ComposterBlock.COMPOSTABLES.put(ModBlocks.THORNY_VERDANT_LEAVES.get().asItem(), 0.3f);
		ComposterBlock.COMPOSTABLES.put(ModBlocks.VERDANT_LEAVES.get().asItem(), 0.3f);
		ComposterBlock.COMPOSTABLES.put(ModBlocks.WILTED_VERDANT_LEAVES.get().asItem(), 0.3f);
		ComposterBlock.COMPOSTABLES.put(ModBlocks.THORN_BUSH.get().asItem(), 0.5f);
		ComposterBlock.COMPOSTABLES.put(ModBlocks.BLEEDING_HEART.get().asItem(), 0.65f);
		ComposterBlock.COMPOSTABLES.put(ModBlocks.STINKING_BLOSSOM.get().asItem(), 1.0f);
		ComposterBlock.COMPOSTABLES.put(ModBlocks.VERDANT_TENDRIL.get().asItem(), 0.5f);
		ComposterBlock.COMPOSTABLES.put(ModBlocks.POISON_IVY_BLOCK.get().asItem(), 1.0f);
		ComposterBlock.COMPOSTABLES.put(ModItems.COOKED_CASSAVA.get(), 0.85f);
		ComposterBlock.COMPOSTABLES.put(ModItems.BITTER_BREAD.get(), 0.85f);
		ComposterBlock.COMPOSTABLES.put(ModItems.CASSAVA.get(), 0.65f);
		ComposterBlock.COMPOSTABLES.put(ModItems.CASSAVA_CUTTINGS.get(), 0.30f);
		ComposterBlock.COMPOSTABLES.put(ModItems.BITTER_CASSAVA.get(), 0.65f);
		ComposterBlock.COMPOSTABLES.put(ModItems.BITTER_CASSAVA_CUTTINGS.get(), 0.30f);
		ComposterBlock.COMPOSTABLES.put(ModItems.BITTER_STARCH.get(), 0.30f);
		ComposterBlock.COMPOSTABLES.put(ModItems.STARCH.get(), 0.30f);

		FoodPoisoningEffect.registerEffects();
		FeaturePlacer.registerFeatures();

		DispenserBlock.registerBehavior(ModItems.POISON_ARROW.get(), new AbstractProjectileDispenseBehavior() {
			protected Projectile getProjectile(Level level, Position position, ItemStack stack) {
				PoisonIvyArrowEntity arrow = new PoisonIvyArrowEntity(level, position.x(), position.y(), position.z());
				arrow.pickup = AbstractArrow.Pickup.ALLOWED;
				return arrow;
			}
		});
		DispenserBlock.registerBehavior(ModItems.SHORT_ROPE_COIL.get(), new AbstractProjectileDispenseBehavior() {
			protected Projectile getProjectile(Level level, Position position, ItemStack stack) {
				Item item = stack.getItem();
				if (item instanceof RopeCoilItem coilItem) {
					return coilItem.createThrownRope(level, null, stack);
				}

				return null;
			}
		});
		DispenserBlock.registerBehavior(ModItems.ROPE_COIL.get(), new AbstractProjectileDispenseBehavior() {
			protected Projectile getProjectile(Level level, Position position, ItemStack stack) {
				Item item = stack.getItem();
				if (item instanceof RopeCoilItem coilItem) {
					return coilItem.createThrownRope(level, null, stack);
				}

				return null;
			}
		});

		// Register candled cakes.
		ExtensibleCakeBlock ubeCake = (ExtensibleCakeBlock) ModBlocks.UBE_CAKE.get();
		ubeCake.addCandleCake(Blocks.CANDLE, ModBlocks.CANDLE_UBE_CAKE.get());
		ubeCake.addCandleCake(Blocks.BLACK_CANDLE, ModBlocks.BLACK_CANDLE_UBE_CAKE.get());
		ubeCake.addCandleCake(Blocks.BLUE_CANDLE, ModBlocks.BLUE_CANDLE_UBE_CAKE.get());
		ubeCake.addCandleCake(Blocks.BROWN_CANDLE, ModBlocks.BROWN_CANDLE_UBE_CAKE.get());
		ubeCake.addCandleCake(Blocks.CYAN_CANDLE, ModBlocks.CYAN_CANDLE_UBE_CAKE.get());
		ubeCake.addCandleCake(Blocks.GRAY_CANDLE, ModBlocks.GRAY_CANDLE_UBE_CAKE.get());
		ubeCake.addCandleCake(Blocks.GREEN_CANDLE, ModBlocks.GREEN_CANDLE_UBE_CAKE.get());
		ubeCake.addCandleCake(Blocks.LIGHT_BLUE_CANDLE, ModBlocks.LIGHT_BLUE_CANDLE_UBE_CAKE.get());
		ubeCake.addCandleCake(Blocks.LIGHT_GRAY_CANDLE, ModBlocks.LIGHT_GRAY_CANDLE_UBE_CAKE.get());
		ubeCake.addCandleCake(Blocks.LIME_CANDLE, ModBlocks.LIME_CANDLE_UBE_CAKE.get());
		ubeCake.addCandleCake(Blocks.MAGENTA_CANDLE, ModBlocks.MAGENTA_CANDLE_UBE_CAKE.get());
		ubeCake.addCandleCake(Blocks.ORANGE_CANDLE, ModBlocks.ORANGE_CANDLE_UBE_CAKE.get());
		ubeCake.addCandleCake(Blocks.PINK_CANDLE, ModBlocks.PINK_CANDLE_UBE_CAKE.get());
		ubeCake.addCandleCake(Blocks.PURPLE_CANDLE, ModBlocks.PURPLE_CANDLE_UBE_CAKE.get());
		ubeCake.addCandleCake(Blocks.RED_CANDLE, ModBlocks.RED_CANDLE_UBE_CAKE.get());
		ubeCake.addCandleCake(Blocks.WHITE_CANDLE, ModBlocks.WHITE_CANDLE_UBE_CAKE.get());
		ubeCake.addCandleCake(Blocks.YELLOW_CANDLE, ModBlocks.YELLOW_CANDLE_UBE_CAKE.get());

	}

	// You can use SubscribeEvent and let the Event Bus discover methods to call
	@SubscribeEvent
	public void onServerStarting(ServerStartingEvent event) {
		// Don't something when the server starts
	}

	// You can use EventBusSubscriber to automatically register all static methods
	// in the class annotated with @SubscribeEvent
	@Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
	public static class ClientModEvents {
		@SubscribeEvent
		public static void onClientSetup(FMLClientSetupEvent event) {
			// Register entity renderers
			EntityRenderers.register(ModEntityTypes.POISON_IVY_ARROW.get(), PoisonIvyArrowRenderer::new);
			EntityRenderers.register(ModEntityTypes.OVERGROWN_ZOMBIE.get(), OvergrownZombieRenderer::new);
			EntityRenderers.register(ModEntityTypes.OVERGROWN_SKELETON.get(), OvergrownSkeletonRenderer::new);
			EntityRenderers.register(ModEntityTypes.THROWN_ROPE.get(), ThrownItemRenderer::new);
			EntityRenderers.register(ModEntityTypes.HALLUCINATED_CREEPER.get(), HallucinatedCreeperRenderer::new);

			MenuScreens.register(ModMenuTypes.FISH_TRAP_MENU.get(), FishTrapScreen::new);
		}
	}
}
