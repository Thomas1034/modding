package com.thomas.verdant.event;

import com.thomas.verdant.Verdant;
import com.thomas.verdant.block.entity.ModBlockEntities;
import com.thomas.verdant.block.entity.renderer.VerdantConduitRenderer;
import com.thomas.verdant.entity.VinesLayer;
import com.thomas.verdant.entity.client.ModModelLayers;
import com.thomas.verdant.util.function.Reflection;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.BoatModel;
import net.minecraft.client.model.ChestBoatModel;
import net.minecraft.client.model.HumanoidArmorModel;
import net.minecraft.client.model.geom.ModelLayers;
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

@Mod.EventBusSubscriber(modid = Verdant.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModEventBusClientEvents {
	@SubscribeEvent
	public static void registerLayer(EntityRenderersEvent.RegisterLayerDefinitions event) {

		event.registerLayerDefinition(ModModelLayers.VERDANT_BOAT_LAYER, BoatModel::createBodyModel);
		event.registerLayerDefinition(ModModelLayers.VERDANT_CHEST_BOAT_LAYER, ChestBoatModel::createBodyModel);
		event.registerLayerDefinition(ModModelLayers.VERDANT_HEARTWOOD_BOAT_LAYER, BoatModel::createBodyModel);
		event.registerLayerDefinition(ModModelLayers.VERDANT_HEARTWOOD_CHEST_BOAT_LAYER,
				ChestBoatModel::createBodyModel);

	}

	@SubscribeEvent
	public static void registerBER(EntityRenderersEvent.RegisterRenderers event) {
		event.registerBlockEntityRenderer(ModBlockEntities.MOD_SIGN.get(), SignRenderer::new);
		event.registerBlockEntityRenderer(ModBlockEntities.MOD_HANGING_SIGN.get(), HangingSignRenderer::new);
		event.registerBlockEntityRenderer(ModBlockEntities.VERDANT_HEART_BLOCK_ENTITY.get(),
				VerdantConduitRenderer::new);

	}

	@SubscribeEvent
	public static void registerBlockColors(RegisterColorHandlersEvent.Block event) {
		// event.register(DynamicColorBlock::skyColors, ModBlocks.SKY_BLOCK.get());
	}

	@SuppressWarnings({ "rawtypes", "unchecked"})
	@SubscribeEvent
	public static void addVinesLayer(EntityRenderersEvent.AddLayers event) {
		//System.out.println("Adding vines layer!!! ---------------------------------------------------------------------------------------");
		event.getSkins().forEach(skin -> {
			var renderer = event.getSkin(skin);
			if (renderer != null) {
				try {
					HumanoidArmorModel innerModel = new HumanoidArmorModel(Minecraft.getInstance().getEntityModels()
							.bakeLayer((Boolean) Reflection.getFromFinal(renderer.getModel(), "slim")
									? ModelLayers.PLAYER_SLIM_INNER_ARMOR
									: ModelLayers.PLAYER_INNER_ARMOR));
					HumanoidArmorModel outerModel = new HumanoidArmorModel(Minecraft.getInstance().getEntityModels()
							.bakeLayer((Boolean) Reflection.getFromFinal(renderer.getModel(), "slim")
									? ModelLayers.PLAYER_SLIM_INNER_ARMOR
									: ModelLayers.PLAYER_INNER_ARMOR));

					renderer.addLayer(new VinesLayer<>((RenderLayerParent) renderer, innerModel, outerModel,
							Minecraft.getInstance().getModelManager()));
				} catch (NoSuchFieldException e) {
					e.printStackTrace();
				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}

			}
		});

		// Minecraft.getInstance().getEntityRenderDispatcher().renderers.forEach((entityType,
		// entityRenderer) -> {
		// if (entityRenderer instanceof LivingEntityRenderer<?, ?> livingEntityRenderer
		// && !(livingEntityRenderer instanceof PlayerRenderer)) {
		// livingEntityRenderer.addLayer(new VinesLayer<>((RenderLayerParent)
		// livingEntityRenderer,
		// Minecraft.getInstance().getEntityModels()));
		// }
		// });
	}

}
