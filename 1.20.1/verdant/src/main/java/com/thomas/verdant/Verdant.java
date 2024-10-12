package com.thomas.verdant;

import org.slf4j.Logger;

import com.mojang.logging.LogUtils;
import com.thomas.verdant.block.ModBlocks;
import com.thomas.verdant.block.entity.ModBlockEntities;
import com.thomas.verdant.datagen.ModLootModifiers;
import com.thomas.verdant.effect.ModMobEffects;
import com.thomas.verdant.effect.custom.FoodPoisoningEffect;
import com.thomas.verdant.enchantment.ModEnchantments;
import com.thomas.verdant.entity.ModEntityTypes;
import com.thomas.verdant.entity.client.renderer.OvergrownSkeletonRenderer;
import com.thomas.verdant.entity.client.renderer.OvergrownZombieRenderer;
import com.thomas.verdant.entity.client.renderer.PoisonIvyArrowRenderer;
import com.thomas.verdant.item.ModCreativeModeTabs;
import com.thomas.verdant.item.ModItems;
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
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ComposterBlock;
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

// register spawn eggs!
// add recipes!
// fix block picking on yams giving seeds

// Additions
// Rope ladders
// Traps
// Iron Spikes
// Heart fragment + heart recipe
// Imbued armor
// Bush
// Charred frame block
// Lilypads will now grow on water that has verdant ground underneath.
// Many texture updates!

// Changes
// Entities should try to avoid walking through spikes and thorn bushes
// Significant changes to how verdant rooted dirt spreads, in an attempt to decrease lag.
// Verdant rooted dirt will no longer collapse from cave ceilings or smooth out pillars.
// Rooted dirt will erode cobblestone into "dense gravel", which does not fall, but can drop up to three gravel items.
// Dense Gravel will break when pushed by a piston.
// Updated texture for poison ivy

// Verdant rooted dirt will now immediately convert to mud (or vice versa) when placed, if appropriate.
// Visual rework of verdant rooted dirt
// The added recipe for arrows now requires a vine as fletching, in addition to the stick and the thorn.
// - This is made in anticipation of a possible new weapon in the future that will include the original crafting recipe.

// Bugfixes
// Removed debug text when the Cassava crop is bonemealed.
// Fixed possible desync when a poison ivy arrow hits an entity.
// Spikes can no longer be instantly replaced by other blocks.
// Frame blocks are in the creative menu
// Reduced lag considerably
// Fixed a bug where verdant rooted dirt wouldn't always erode the entire area around it.
// Frame blocks now have the proper block support shape (i.e. they can't support blocks like torches at all)

// To add: yams. three growth stages, 75% chance to spread instead of growing from the second to the third stage.
// Changed icon for food poisoning
// Restoration potion, provides immunity to poison and wither

// To add: wild parsnip (inflicts photosensitive, causes some damage in sky access in the day, effect strength determines how often damage occurs)
// Maybe add monkshood? Causes paralysis.
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

		// Register potions
		BrewingRecipeRegistry.addRecipe(
				new BetterBrewingRecipe(Potions.WATER, ModItems.ROASTED_COFFEE.get(), ModPotions.CAFFEINE.get()));
		BrewingRecipeRegistry.addRecipe(new BetterBrewingRecipe(ModPotions.CAFFEINE.get(),
				ModItems.ROASTED_COFFEE.get(), ModPotions.LONG_CAFFEINE.get()));
		BrewingRecipeRegistry.addRecipe(
				new BetterBrewingRecipe(ModPotions.CAFFEINE.get(), Items.SUGAR, ModPotions.STRONG_CAFFEINE.get()));

		BrewingRecipeRegistry.addRecipe(
				new BetterBrewingRecipe(Potions.AWKWARD, ModItems.SPARKLING_STARCH.get(), ModPotions.COLLOID.get()));
		BrewingRecipeRegistry.addRecipe(
				new BetterBrewingRecipe(ModPotions.COLLOID.get(), Items.REDSTONE, ModPotions.LONG_COLLOID.get()));
		BrewingRecipeRegistry.addRecipe(
				new BetterBrewingRecipe(ModPotions.COLLOID.get(), Items.GLOWSTONE, ModPotions.STRONG_COLLOID.get()));

		BrewingRecipeRegistry.addRecipe(
				new BetterBrewingRecipe(Potions.AWKWARD, ModItems.HEART_FRAGMENT.get(), ModPotions.ANTIDOTE.get()));
		BrewingRecipeRegistry.addRecipe(
				new BetterBrewingRecipe(ModPotions.ANTIDOTE.get(), Items.REDSTONE, ModPotions.LONG_ANTIDOTE.get()));

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

		// FishingRodItem
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

			MenuScreens.register(ModMenuTypes.FISH_TRAP_MENU.get(), FishTrapScreen::new);
		}
	}
}
