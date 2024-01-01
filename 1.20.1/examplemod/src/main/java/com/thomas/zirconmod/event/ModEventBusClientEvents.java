package com.thomas.zirconmod.event;

import com.thomas.zirconmod.ZirconMod;
import com.thomas.zirconmod.entity.client.ModModelLayers;
import com.thomas.zirconmod.entity.client.MoleModel;
import com.thomas.zirconmod.entity.client.NimbulaModel;
import com.thomas.zirconmod.entity.client.WingsLayer;
import com.thomas.zirconmod.entity.client.WispModel;
import com.thomas.zirconmod.entity.client.WoodGolemModel;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.BoatModel;
import net.minecraft.client.model.ChestBoatModel;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ZirconMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModEventBusClientEvents {
	@SubscribeEvent
	public static void registerLayer(EntityRenderersEvent.RegisterLayerDefinitions event) {
		event.registerLayerDefinition(ModModelLayers.MOLE_LAYER, MoleModel::createBodyLayer);
		event.registerLayerDefinition(ModModelLayers.WOOD_GOLEM_LAYER, WoodGolemModel::createBodyLayer);
		event.registerLayerDefinition(ModModelLayers.NIMBULA_LAYER, NimbulaModel::createBodyLayer);
		event.registerLayerDefinition(ModModelLayers.WISP_LAYER, WispModel::createBodyLayer);
		event.registerLayerDefinition(ModModelLayers.PALM_BOAT_LAYER, BoatModel::createBodyModel);
		event.registerLayerDefinition(ModModelLayers.PALM_CHEST_BOAT_LAYER, ChestBoatModel::createBodyModel);

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

}
