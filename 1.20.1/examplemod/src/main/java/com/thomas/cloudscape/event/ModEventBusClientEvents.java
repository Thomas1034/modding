package com.thomas.cloudscape.event;

import com.thomas.cloudscape.Cloudscape;
import com.thomas.cloudscape.block.ModBlocks;
import com.thomas.cloudscape.block.custom.DynamicColorBlock;
import com.thomas.cloudscape.block.entity.ModBlockEntities;
import com.thomas.cloudscape.entity.client.ModModelLayers;
import com.thomas.cloudscape.entity.client.WingsLayer;
import com.thomas.cloudscape.entity.client.model.GustModel;
import com.thomas.cloudscape.entity.client.model.MoleModel;
import com.thomas.cloudscape.entity.client.model.NimbulaModel;
import com.thomas.cloudscape.entity.client.model.TempestModel;
import com.thomas.cloudscape.entity.client.model.WispModel;
import com.thomas.cloudscape.entity.client.model.WoodGolemModel;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.BoatModel;
import net.minecraft.client.model.ChestBoatModel;
import net.minecraft.client.renderer.blockentity.HangingSignRenderer;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Cloudscape.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModEventBusClientEvents {
	@SubscribeEvent
	public static void registerLayer(EntityRenderersEvent.RegisterLayerDefinitions event) {
		event.registerLayerDefinition(ModModelLayers.MOLE_LAYER, MoleModel::createBodyLayer);
		event.registerLayerDefinition(ModModelLayers.WOOD_GOLEM_LAYER, WoodGolemModel::createBodyLayer);
		event.registerLayerDefinition(ModModelLayers.NIMBULA_LAYER, NimbulaModel::createBodyLayer);
		event.registerLayerDefinition(ModModelLayers.TEMPEST_LAYER, TempestModel::createBodyLayer);
		event.registerLayerDefinition(ModModelLayers.TEMPEST_POWER_LAYER, TempestModel::createPowerLayer);
		
		event.registerLayerDefinition(ModModelLayers.WISP_LAYER, WispModel::createBodyLayer);

		event.registerLayerDefinition(ModModelLayers.GUST_LAYER, GustModel::createBodyLayer);
		
		event.registerLayerDefinition(ModModelLayers.PALM_BOAT_LAYER, BoatModel::createBodyModel);
		event.registerLayerDefinition(ModModelLayers.PALM_CHEST_BOAT_LAYER, ChestBoatModel::createBodyModel);

	}

	@SubscribeEvent
	public static void registerBER(EntityRenderersEvent.RegisterRenderers event) {
		event.registerBlockEntityRenderer(ModBlockEntities.MOD_SIGN.get(), SignRenderer::new);
		event.registerBlockEntityRenderer(ModBlockEntities.MOD_HANGING_SIGN.get(), HangingSignRenderer::new);
		
	}

	// Credit for this goes to LocusAzzurro
	// Source at:
	// https://github.com/LocusAzzurro/IcarusWings/blob/1.19.4/src/main/java/org/mineplugin/locusazzurro/icaruswings/event/ModClientRenderEventHandler.java
	// Copied almost directly with changes to some class names.
	@SuppressWarnings({ "rawtypes", "unchecked", "resource" })
	@SubscribeEvent
	public static void addWingsLayer(EntityRenderersEvent.AddLayers event) {

		event.getSkins().forEach(skin -> {
			var renderer = event.getSkin(skin);
			if (renderer != null)
				renderer.addLayer(
						new WingsLayer<>((RenderLayerParent) renderer, Minecraft.getInstance().getEntityModels()));
		});

		Minecraft.getInstance().getEntityRenderDispatcher().renderers.forEach((entityType, entityRenderer) -> {
			if (entityRenderer instanceof LivingEntityRenderer<?, ?> livingEntityRenderer
					&& !(livingEntityRenderer instanceof PlayerRenderer)) {
				livingEntityRenderer.addLayer(new WingsLayer<>((RenderLayerParent) livingEntityRenderer,
						Minecraft.getInstance().getEntityModels()));
			}
		});
	}
	
	
	@SubscribeEvent
	public static void registerBlockColors(RegisterColorHandlersEvent.Block event) {
		//event.register(DynamicColorBlock::skyColors, ModBlocks.SKY_BLOCK.get());
	}

}
