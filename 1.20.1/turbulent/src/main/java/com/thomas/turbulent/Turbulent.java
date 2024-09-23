package com.thomas.turbulent;

import org.slf4j.Logger;

import com.mojang.logging.LogUtils;
import com.thomas.turbulent.block.ModBlocks;
import com.thomas.turbulent.block.entity.ModBlockEntities;
import com.thomas.turbulent.effect.ModMobEffects;
import com.thomas.turbulent.enchantment.ModEnchantments;
import com.thomas.turbulent.entity.ModEntityTypes;
import com.thomas.turbulent.item.ModCreativeModeTabs;
import com.thomas.turbulent.item.ModItems;
import com.thomas.turbulent.network.ModPacketHandler;
import com.thomas.turbulent.painting.ModPaintings;
import com.thomas.turbulent.potion.ModPotions;
import com.thomas.turbulent.util.data.DataRegistries;

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
@Mod(Turbulent.MOD_ID)
public class Turbulent {
	// Define mod id in a common place for everything to reference
	public static final String MOD_ID = "turbulent";
	// Directly reference a slf4j logger
	private static final Logger LOGGER = LogUtils.getLogger();

	public Turbulent() {
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
		LOGGER.info("We're having some turbulence.");
		
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

		}
	}
}
