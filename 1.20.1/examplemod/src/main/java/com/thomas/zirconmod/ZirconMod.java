package com.thomas.zirconmod;

import org.slf4j.Logger;

import com.mojang.logging.LogUtils;
import com.thomas.zirconmod.block.ModBlocks;
import com.thomas.zirconmod.block.entity.ModBlockEntities;
import com.thomas.zirconmod.effect.ModEffects;
import com.thomas.zirconmod.enchantment.ModEnchantments;
import com.thomas.zirconmod.entity.ModEntityType;
import com.thomas.zirconmod.entity.client.renderer.GustRenderer;
import com.thomas.zirconmod.entity.client.renderer.ModBoatRenderer;
import com.thomas.zirconmod.entity.client.renderer.ModThrownItemRenderer;
import com.thomas.zirconmod.entity.client.renderer.MoleRenderer;
import com.thomas.zirconmod.entity.client.renderer.NimbulaRenderer;
import com.thomas.zirconmod.entity.client.renderer.TempestRenderer;
import com.thomas.zirconmod.entity.client.renderer.WispRenderer;
import com.thomas.zirconmod.entity.client.renderer.WoodGolemRenderer;
import com.thomas.zirconmod.entity.client.renderer.WraithRenderer;
import com.thomas.zirconmod.item.ModCreativeModeTabs;
import com.thomas.zirconmod.item.ModItems;
import com.thomas.zirconmod.loot.ModLootModifiers;
import com.thomas.zirconmod.network.ModPacketHandler;
import com.thomas.zirconmod.painting.ModPaintings;
import com.thomas.zirconmod.sound.ModSounds;
import com.thomas.zirconmod.util.WoodGolemPlacer;
import com.thomas.zirconmod.villager.ModVillagers;
import com.thomas.zirconmod.worldgen.ModFeature;
import com.thomas.zirconmod.worldgen.biome.ModTerraBlender;
import com.thomas.zirconmod.worldgen.biome.surface.ModSurfaceRules;
import com.thomas.zirconmod.worldgen.tree.ModFoliagePlacers;
import com.thomas.zirconmod.worldgen.tree.ModTrunkPlacerTypes;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
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
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import terrablender.api.SurfaceRuleManager;

// To do:
// Add painting titles in lang file.
// Add sounds for all the entities.
// Add JEI compatibility
// 
// 
// 

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

		ModBlockEntities.register(modEventBus);

		ModLootModifiers.register(modEventBus);
		ModVillagers.register(modEventBus);

		ModTrunkPlacerTypes.register(modEventBus);
		ModFoliagePlacers.register(modEventBus);

		ModSounds.register(modEventBus);
		ModEntityType.register(modEventBus);

		ModFeature.register(modEventBus);
		
		ModTerraBlender.registerBiomes();
		
		ModPacketHandler.register();

		// Register the commonSetup method for modloading
		modEventBus.addListener(this::commonSetup);

		// Register ourselves for server and other game events we are interested in
		MinecraftForge.EVENT_BUS.register(this);
	}

	private void commonSetup(final FMLCommonSetupEvent event) {
		event.enqueueWork(() -> {
			// Add the right click to add to flowerpot event.
			((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ModBlocks.ILLUMINATED_TORCHFLOWER.getId(),
					ModBlocks.POTTED_ILLUMINATED_TORCHFLOWER);
			((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ModBlocks.PALM_SAPLING.getId(),
					ModBlocks.POTTED_PALM_SAPLING);
			((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ModBlocks.WHITE_ORCHID.getId(),
					ModBlocks.POTTED_WHITE_ORCHID);

			SurfaceRuleManager.addSurfaceRules(SurfaceRuleManager.RuleCategory.OVERWORLD, MOD_ID,
					ModSurfaceRules.makeRules());

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
			EntityRenderers.register(ModEntityType.MOLE_ENTITY.get(), MoleRenderer::new);
			EntityRenderers.register(ModEntityType.WOOD_GOLEM_ENTITY.get(), WoodGolemRenderer::new);
			EntityRenderers.register(ModEntityType.NIMBULA_ENTITY.get(), NimbulaRenderer::new);
			EntityRenderers.register(ModEntityType.WISP_ENTITY.get(), WispRenderer::new);
			EntityRenderers.register(ModEntityType.MOD_BOAT.get(), context -> new ModBoatRenderer(context, false));
			EntityRenderers.register(ModEntityType.MOD_CHEST_BOAT.get(), context -> new ModBoatRenderer(context, true));
			EntityRenderers.register(ModEntityType.TEMPEST_ENTITY.get(), TempestRenderer::new);
			EntityRenderers.register(ModEntityType.HAILSTONE_ENTITY.get(), ThrownItemRenderer::new);
			EntityRenderers.register(ModEntityType.BALL_LIGHTNING_ENTITY.get(), ModThrownItemRenderer::BallLightningRenderer);
			EntityRenderers.register(ModEntityType.GUST_ENTITY.get(), GustRenderer::new);
			EntityRenderers.register(ModEntityType.WRAITH_ENTITY.get(), WraithRenderer::new);
		}
	}

}
