package com.thomas.zirconmod;

import org.slf4j.Logger;

import com.mojang.logging.LogUtils;
import com.thomas.zirconmod.block.ModBlocks;
import com.thomas.zirconmod.datagen.loot.ModLootModifiers;
import com.thomas.zirconmod.effect.ModEffects;
import com.thomas.zirconmod.enchantment.ModEnchantments;
import com.thomas.zirconmod.entity.ModEntities;
import com.thomas.zirconmod.entity.client.ModBoatRenderer;
import com.thomas.zirconmod.entity.client.MoleRenderer;
import com.thomas.zirconmod.entity.client.NimbulaRenderer;
import com.thomas.zirconmod.entity.client.WoodGolemRenderer;
import com.thomas.zirconmod.item.ModCreativeModeTabs;
import com.thomas.zirconmod.item.ModItems;
import com.thomas.zirconmod.painting.ModPaintings;
import com.thomas.zirconmod.sound.ModSounds;
import com.thomas.zirconmod.util.WoodGolemPlacer;
import com.thomas.zirconmod.villager.ModVillagers;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.level.BlockEvent;
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
@Mod(ZirconMod.MOD_ID)
public class ZirconMod {
	// Define mod id in a common place for everything to reference
	public static final String MOD_ID = "zirconmod";
	// Directly reference a slf4j logger
	private static final Logger LOGGER = LogUtils.getLogger();

	public ZirconMod() {
		IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

		ModCreativeModeTabs.register(modEventBus);

		ModItems.register(modEventBus);
		ModBlocks.register(modEventBus);
		ModPaintings.register(modEventBus);
		ModEnchantments.register(modEventBus);
		ModEffects.register(modEventBus);

		ModLootModifiers.register(modEventBus);
		ModVillagers.register(modEventBus);

		ModSounds.register(modEventBus);
		ModEntities.register(modEventBus);

		// Register the commonSetup method for modloading
		modEventBus.addListener(this::commonSetup);

		// Register ourselves for server and other game events we are interested in
		MinecraftForge.EVENT_BUS.register(this);


		// Register our mod's ForgeConfigSpec so that Forge can create and load the
		// config file for us
		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);
	}

	private void commonSetup(final FMLCommonSetupEvent event) {
		// Add the right click to add to flowerpot event.
		event.enqueueWork(() -> {
			((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ModBlocks.ILLUMINATED_TORCHFLOWER.getId(),
					ModBlocks.POTTED_ILLUMINATED_TORCHFLOWER);
		});
	}

	// Runs block placement code.
	@SubscribeEvent
	public void onBlockPlaced(BlockEvent.EntityPlaceEvent event) {
		LevelAccessor level = event.getLevel();
		BlockPos pos = event.getPos();
		BlockState state = event.getState();
		WoodGolemPlacer.checkAndPlaceWoodGolem(level, pos, state);
	}

	// Add the example block item to the building blocks tab
	@SuppressWarnings("unused")
	private void addCreative(BuildCreativeModeTabContentsEvent event) {
		if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS)
			;
		// event.accept(EXAMPLE_BLOCK_ITEM);
	}

	// You can use SubscribeEvent and let the Event Bus discover methods to call
	@SubscribeEvent
	public void onServerStarting(ServerStartingEvent event) {
		// Do something when the server starts
		LOGGER.info("Server is starting...");
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
			EntityRenderers.register(ModEntities.MOLE_ENTITY.get(), MoleRenderer::new);
			EntityRenderers.register(ModEntities.WOOD_GOLEM_ENTITY.get(), WoodGolemRenderer::new);
			EntityRenderers.register(ModEntities.NIMBULA_ENTITY.get(), NimbulaRenderer::new);
			EntityRenderers.register(ModEntities.MOD_BOAT.get(), context -> getModBoatRenderer(context));
			EntityRenderers.register(ModEntities.MOD_CHEST_BOAT.get(), context -> getModChestBoatRenderer(context));

		}
	}
	
	private static ModBoatRenderer getModBoatRenderer(net.minecraft.client.renderer.entity.EntityRendererProvider.Context context) {
		ModBoatRenderer renderer = new ModBoatRenderer(context, false);
		System.out.println("		The normal boat renderer is: " + renderer);
		return renderer;
	}
	private static ModBoatRenderer getModChestBoatRenderer(net.minecraft.client.renderer.entity.EntityRendererProvider.Context context) {
		ModBoatRenderer renderer = new ModBoatRenderer(context, true);
		System.out.println("		The chest boat renderer is: " + renderer);
		return renderer;
	}
}
