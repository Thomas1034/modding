package com.thomas.verdant;

import org.slf4j.Logger;

import com.mojang.logging.LogUtils;
import com.thomas.verdant.block.ModBlocks;
import com.thomas.verdant.block.entity.ModBlockEntities;
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
import com.thomas.verdant.util.data.DataRegistries;

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

// Additions
// Cassava (bitter, normal, golden) hoe and double high
// Shrub
// Water hemlock

// Changes
// Verdant heartwood blocks are now harder to mine.
// Thorn bushes now do more damage when you are jumping or falling, and less while walking through them. Don't fall on them.
// Block transforms no longer print when registered.
// Verdant wood is now far more flammable.
// Verdant Energy now correctly applies more frequently on higher levels.
// Verdant Vines now no longer drop sticks.
// Verdant roots now grow slightly differently and are slightly less "smart" about where they grow to. This is more than made up by a ~25% performance improvement
// Frame blocks are now half as expensive.
// Leaves no longer schedule an extra tick after updating; this cuts off 8 ms of lag per tick on a heavily overgrown world. This _may_ cause some odd behavior when the leaves are affected by non-block-updating block changes from other mods, but this is unlikely. 
// Verdant ground blocks can now be hoed back to their ordinary variants.


// Bugfixes
// General stability increases
// Toxic Ash / Toxic Solution now have a proper use animations
// Verdant wood/heartwood items have now been standardized into a common constructor.
// This is primarily a backend change, but it has fixed some bugs. This might change the mining time
// and flammability of some blocks to a minor degree
// Fixed hanging sign edit screen
// Fixed inaccurate /verdant command text
// Heart of the Forest no longer grows vines on players who hold it - that was meant to be experimental. Pretend you never saw it.
// Decreased lag! Hooray!

// Cassava -> 4 Cassava Flour 
// 6 Cassava Flour -> 4 Bread
// Cassava -> Cooked Cassava (like chicken)

// To add: water hemlock (deadly, inflicts wither on contact)
// To add: wild parsnip (inflicts photosensitive, causes some damage in sky access in the day, effect strength determines how often damage occurs)
// Make verdant dirt hoeable!
// Maybe add monkshood? Causes paralysis.
// Think about giant hogweed.
// Levels of infection: sprouting, leafing, rooting, blooming.
// sprouting: speed, regen, haste
// leafing:   regen, haste, resistance, phototrophic (restores saturation based on light level)
// rooting:   slowness, regen, haste, resistance 2, phototrophic 2
// blooming: slowness 3, mining fatigue, wither, phototrophic 3

// To add: poison ivy bundle block, inflicts poison, ages to dead poison ivy items which is compostable at a value of 1.
// Crafted with four sticks and five ivy.
// To add: thorns block, harms monsters that step on it. Cross-model like grass.

//{
//    "name": "verdant:grass",
//    "type": "verdant:custom",
//    "tries": 1,
//    "parameters": {
//        "should_place": {
//            "type": "verdant:single_block",
//            "parameters": {
//                "block": "minecraft:air",
//                "y": "1" 
//            }
//        },
//        "can_place": {
//            "type": "verdant:single_block",
//            "parameters": {
//                "block": "minecraft:air"
//            }
//        },
//        "placement": {
//            "type": "verdant:offset",
//            "parameters": {
//                "y": "1"
//            }
//        },
//        "placer": {
//            "type": "verdant:single_block",
//            "parameters": {
//                "block": "minecraft:grass"
//            }
//        }
//    }
//}

// The value here should match an entry in the META-INF/mods.toml file
@Mod(Verdant.MOD_ID)
public class Verdant {
	// Define mod id in a common place for everything to reference
	public static final String MOD_ID = "verdant";
	// Directly reference a slf4j logger
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
		LOGGER.info("HELLO FROM COMMON SETUP");
		// Add the right click to add to flowerpot event.
		((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ModBlocks.BLEEDING_HEART.getId(),
				ModBlocks.POTTED_BLEEDING_HEART);
		((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ModBlocks.WILD_COFFEE.getId(), ModBlocks.POTTED_WILD_COFFEE);
		((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ModBlocks.THORN_BUSH.getId(), ModBlocks.POTTED_THORN_BUSH);
		/// ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ModBlocks..getId(),
		// ModBlocks.POTTED_BLEEDING_HEART);
		// ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ModBlocks.PALM_SAPLING.getId(),
		/// ModBlocks.POTTED_PALM_SAPLING);
		// ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ModBlocks.WHITE_ORCHID.getId(),
		/// ModBlocks.POTTED_WHITE_ORCHID);

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

		// Register verdant growth mechanics on setup.
		// Nope! Data driven now.
		// VerdantTransformationHandler.registerDehydration();
		// VerdantTransformationHandler.registerErosion();
		// VerdantTransformationHandler.registerErosionWet();
		// VerdantTransformationHandler.registerGrowGrasses();
		// VerdantTransformationHandler.registerHydration();
		// VerdantTransformationHandler.registerRemoveGrasses();
		// VerdantTransformationHandler.registerRoots();
		FoodPoisoningEffect.registerEffects();
		FeaturePlacer.registerFeatures();
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
		}
	}
}
