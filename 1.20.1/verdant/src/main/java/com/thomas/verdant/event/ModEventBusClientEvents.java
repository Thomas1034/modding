package com.thomas.verdant.event;

import com.thomas.verdant.Verdant;
import com.thomas.verdant.block.entity.ModBlockEntities;
import com.thomas.verdant.entity.client.ModModelLayers;

import net.minecraft.client.model.BoatModel;
import net.minecraft.client.model.ChestBoatModel;
import net.minecraft.client.renderer.blockentity.HangingSignRenderer;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Verdant.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModEventBusClientEvents {
	@SubscribeEvent
	public static void registerLayer(EntityRenderersEvent.RegisterLayerDefinitions event) {

		event.registerLayerDefinition(ModModelLayers.DEMO_BOAT_LAYER, BoatModel::createBodyModel);
		event.registerLayerDefinition(ModModelLayers.DEMO_CHEST_BOAT_LAYER, ChestBoatModel::createBodyModel);

	}

	@SubscribeEvent
	public static void registerBER(EntityRenderersEvent.RegisterRenderers event) {
		event.registerBlockEntityRenderer(ModBlockEntities.MOD_SIGN.get(), SignRenderer::new);
		event.registerBlockEntityRenderer(ModBlockEntities.MOD_HANGING_SIGN.get(), HangingSignRenderer::new);

	}

	@SubscribeEvent
	public static void registerBlockColors(RegisterColorHandlersEvent.Block event) {
		// event.register(DynamicColorBlock::skyColors, ModBlocks.SKY_BLOCK.get());
	}

}
