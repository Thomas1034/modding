package com.thomas.verdant;

import org.slf4j.Logger;

import com.mojang.logging.LogUtils;
import com.thomas.verdant.block.ModBlocks;
import com.thomas.verdant.block.entity.ModBlockEntities;
import com.thomas.verdant.effect.ModEffects;
import com.thomas.verdant.enchantment.ModEnchantments;
import com.thomas.verdant.entity.ModEntityType;
import com.thomas.verdant.entity.client.renderer.ModBoatRenderer;
import com.thomas.verdant.growth.VerdantEroder;
import com.thomas.verdant.growth.VerdantGrassGrower;
import com.thomas.verdant.growth.VerdantHydratable;
import com.thomas.verdant.growth.VerdantRootGrower;
import com.thomas.verdant.item.ModCreativeModeTabs;
import com.thomas.verdant.item.ModItems;
import com.thomas.verdant.network.ModPacketHandler;
import com.thomas.verdant.painting.ModPaintings;
import com.thomas.verdant.worldgen.ModFeature;
import com.thomas.verdant.worldgen.tree.ModFoliagePlacers;
import com.thomas.verdant.worldgen.tree.ModTrunkPlacerTypes;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

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
		ModEffects.register(modEventBus);

		ModBlockEntities.register(modEventBus);

		ModTrunkPlacerTypes.register(modEventBus);
		ModFoliagePlacers.register(modEventBus);

		ModEntityType.register(modEventBus);

		ModFeature.register(modEventBus);

		ModPacketHandler.register();

		// Register the commonSetup method for modloading
		modEventBus.addListener(this::commonSetup);

		// Register ourselves for server and other game events we are interested in
		MinecraftForge.EVENT_BUS.register(this);
	}

	private void commonSetup(final FMLCommonSetupEvent event) {
		// Some common setup code
		LOGGER.info("HELLO FROM COMMON SETUP");

		// Register verdant growth mechanics on setup.
		VerdantEroder.registerErosions();
		VerdantHydratable.registerHydratables();
		VerdantGrassGrower.registerGrasses();
		VerdantRootGrower.registerRoots();
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
		}
	}
}
