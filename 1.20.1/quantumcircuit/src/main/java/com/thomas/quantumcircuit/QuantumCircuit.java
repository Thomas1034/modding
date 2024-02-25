package com.thomas.quantumcircuit;

import org.slf4j.Logger;

import com.mojang.logging.LogUtils;
import com.thomas.quantumcircuit.block.ModBlocks;
import com.thomas.quantumcircuit.item.ModCreativeModeTabs;
import com.thomas.quantumcircuit.item.ModItems;
import com.thomas.quantumcircuit.qsim.test.QuantumCircuitTests;

import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(QuantumCircuit.MOD_ID)
public class QuantumCircuit {
	// Define mod id in a common place for everything to reference
	public static final String MOD_ID = "quantumcircuit";
	// Directly reference a slf4j logger
	private static final Logger LOGGER = LogUtils.getLogger();

	public QuantumCircuit() {
		IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

		// Register the commonSetup method for modloading
		modEventBus.addListener(this::commonSetup);
		

		ModCreativeModeTabs.register(modEventBus);
		ModItems.register(modEventBus);
		ModBlocks.register(modEventBus);

		// Register ourselves for server and other game events we are interested in
		MinecraftForge.EVENT_BUS.register(this);

		// Register our mod's ForgeConfigSpec so that Forge can create and load the
		// config file for us
		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);
	}

	private void commonSetup(final FMLCommonSetupEvent event) {
		// Used for testing currently.
		QuantumCircuitTests.run();
	}

	// You can use SubscribeEvent and let the Event Bus discover methods to call
	@SubscribeEvent
	public void onServerStarting(ServerStartingEvent event) {
		
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
		}
	}
}
