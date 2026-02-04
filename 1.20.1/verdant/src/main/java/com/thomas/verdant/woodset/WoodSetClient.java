package com.thomas.verdant.woodset;

import com.mojang.datafixers.util.Pair;

import net.minecraft.client.model.BoatModel;
import net.minecraft.client.model.ChestBoatModel;
import net.minecraft.client.model.ListModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.blockentity.HangingSignRenderer;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraft.client.renderer.entity.BoatRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

// Handles the client-side things for a wood set.
public class WoodSetClient {

	protected final WoodSet woodSet;
	
	
	public WoodSetClient(WoodSet woodSet) {
		this.woodSet = woodSet;
		
		FMLJavaModLoadingContext.get().getModEventBus().register(this);
		MinecraftForge.EVENT_BUS.register(this);
	}

	@SubscribeEvent
	protected void registerER(FMLClientSetupEvent event) {
		EntityRenderers.register(this.woodSet.boatEntity.get(), context -> this.new WoodSetBoatRenderer(context, false));
		EntityRenderers.register(this.woodSet.chestBoatEntity.get(), context -> this.new WoodSetBoatRenderer(context, true));
	}


	@SubscribeEvent
	protected void registerBER(EntityRenderersEvent.RegisterRenderers event) {
		event.registerBlockEntityRenderer(this.woodSet.signBlockEntity.get(), SignRenderer::new);
		event.registerBlockEntityRenderer(this.woodSet.hangingSignBlockEntity.get(), HangingSignRenderer::new);
	}
	

	public class WoodSetBoatRenderer extends BoatRenderer {

		private final Pair<ResourceLocation, ListModel<Boat>> modelWithLocation;

		public WoodSetBoatRenderer(EntityRendererProvider.Context context, boolean isChestBoat) {
			super(context, isChestBoat);
			this.modelWithLocation = Pair.of(this.getTextureLocation(isChestBoat),
					this.createBoatModel(context, isChestBoat));
		}

		private ResourceLocation getTextureLocation(boolean chestBoat) {
			return new ResourceLocation(WoodSetClient.this.woodSet.modid,
					chestBoat ? "textures/entity/chest_boat/" + WoodSetClient.this.woodSet.baseName + ".png"
							: "textures/entity/boat/" + WoodSetClient.this.woodSet.baseName + ".png");
		}

		private ListModel<Boat> createBoatModel(EntityRendererProvider.Context context, boolean isChestBoat) {
			ModelLayerLocation modellayerlocation = isChestBoat ? this.createChestBoatModelName()
					: this.createBoatModelName();
			ModelPart modelpart = context.bakeLayer(modellayerlocation);
			return isChestBoat ? new ChestBoatModel(modelpart) : new BoatModel(modelpart);
		}

		public ModelLayerLocation createBoatModelName() {
			return createLocation("boat/" + WoodSetClient.this.woodSet.baseName, "main");
		}

		public ModelLayerLocation createChestBoatModelName() {
			return createLocation("chest_boat/" + WoodSetClient.this.woodSet.baseName, "main");
		}

		private ModelLayerLocation createLocation(String path, String model) {
			return new ModelLayerLocation(new ResourceLocation(WoodSetClient.this.woodSet.modid, path), model);
		}

		public Pair<ResourceLocation, ListModel<Boat>> getModelWithLocation(Boat boat) {
			return this.modelWithLocation;
		}
	}
	
}
