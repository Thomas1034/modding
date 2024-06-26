package com.thomas.verdant;

import org.slf4j.Logger;

import com.mojang.logging.LogUtils;
import com.thomas.verdant.block.ModBlocks;
import com.thomas.verdant.block.entity.ModBlockEntities;
import com.thomas.verdant.effect.ModMobEffects;
import com.thomas.verdant.effect.custom.FoodPoisoningEffect;
import com.thomas.verdant.enchantment.ModEnchantments;
import com.thomas.verdant.entity.ModEntityType;
import com.thomas.verdant.entity.client.renderer.ModBoatRenderer;
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
import com.thomas.verdant.worldgen.ModFeature;
import com.thomas.verdant.worldgen.tree.ModFoliagePlacers;
import com.thomas.verdant.worldgen.tree.ModTrunkPlacerTypes;

import net.minecraft.client.Minecraft;
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

		ModTrunkPlacerTypes.register(modEventBus);
		ModFoliagePlacers.register(modEventBus);

		ModEntityType.register(modEventBus);

		ModFeature.register(modEventBus);

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
		// Do something when the server starts
		LOGGER.info("HELLO from server starting");
	}

	// You can use EventBusSubscriber to automatically register all static methods
	// in the class annotated with @SubscribeEvent
	@Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
	public static class ClientModEvents {
		@SubscribeEvent
		public static void onClientSetup(FMLClientSetupEvent event) {
			// Some client setup code
			LOGGER.info("CLIENT SETUP:");
			LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
			EntityRenderers.register(ModEntityType.VERDANT_BOAT.get(), context -> new ModBoatRenderer(context, false));
			EntityRenderers.register(ModEntityType.VERDANT_CHEST_BOAT.get(),
					context -> new ModBoatRenderer(context, true));
			EntityRenderers.register(ModEntityType.POISON_IVY_ARROW.get(), PoisonIvyArrowRenderer::new);
			EntityRenderers.register(ModEntityType.OVERGROWN_ZOMBIE.get(), OvergrownZombieRenderer::new);
			EntityRenderers.register(ModEntityType.OVERGROWN_SKELETON.get(), OvergrownSkeletonRenderer::new);
			EntityRenderers.register(ModEntityType.THROWN_ROPE.get(), ThrownItemRenderer::new);

		}
	}
}
