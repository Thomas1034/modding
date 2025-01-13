package com.thomas.verdant;

import com.thomas.verdant.client.renderer.VerdantConduitRenderer;
import com.thomas.verdant.client.renderer.VerdantConduitSpecialRenderer;
import com.thomas.verdant.client.screen.FishTrapScreen;
import com.thomas.verdant.registration.RegistryObject;
import com.thomas.verdant.registry.BlockEntityTypeRegistry;
import com.thomas.verdant.registry.EntityTypeRegistry;
import com.thomas.verdant.registry.MenuRegistry;
import com.thomas.verdant.registry.WoodSets;
import com.thomas.verdant.woodset.WoodSet;
import net.minecraft.client.model.BoatModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.BoatRenderer;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.entity.vehicle.ChestBoat;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.event.RegisterSpecialModelRendererEvent;

import java.util.HashMap;
import java.util.Map;

@Mod(value = "verdant", dist = Dist.CLIENT)
public class VerdantClient {

    protected static final Map<RegistryObject<EntityType<?>, EntityType<? extends Boat>>, ModelLayerLocation> LOCATION_FOR_BOAT = new HashMap<>();
    protected static final Map<RegistryObject<EntityType<?>, EntityType<? extends ChestBoat>>, ModelLayerLocation> LOCATION_FOR_CHEST_BOAT = new HashMap<>();

    public VerdantClient(IEventBus modBus) {
        modBus.addListener(VerdantClient::onClientSetup);
        modBus.addListener(VerdantClient::onRegisterLayerDefinitions);
        modBus.addListener(VerdantClient::registerScreens);
        modBus.addListener(VerdantClient::registerBlockEntityRenderers);
        modBus.addListener(VerdantClient::registerSpecialModels);

        for (WoodSet woodSet : WoodSets.WOOD_SETS) {
            initialSetupBeforeRenderEvents(woodSet);
        }
    }

    public static void initialSetupBeforeRenderEvents(WoodSet woodSet) {
        ModelLayerLocation boat = new ModelLayerLocation(
                ResourceLocation.withDefaultNamespace("boat/" + woodSet.getName()),
                "main"
        );
        // ModelLayers.register("boat/" + woodSet.getName());
        ModelLayerLocation chestBoat = new ModelLayerLocation(
                ResourceLocation.withDefaultNamespace("chest_boat/" + woodSet.getName()),
                "main"
        );
        // ModelLayers.register("chest_boat/" + woodSet.getName());

        LOCATION_FOR_BOAT.put(woodSet.getBoat(), boat);
        LOCATION_FOR_CHEST_BOAT.put(woodSet.getChestBoat(), chestBoat);
    }

    public static void onRegisterLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        for (WoodSet woodSet : WoodSets.WOOD_SETS) {
            event.registerLayerDefinition(LOCATION_FOR_BOAT.get(woodSet.getBoat()), BoatModel::createBoatModel);
            event.registerLayerDefinition(
                    LOCATION_FOR_CHEST_BOAT.get(woodSet.getChestBoat()),
                    BoatModel::createChestBoatModel
            );
        }
    }

    public static void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            setupWoodSets();

            EntityRenderers.register(EntityTypeRegistry.THROWN_ROPE.get(), ThrownItemRenderer::new);
        });
    }

    public static void setupWoodSets() {
        for (WoodSet woodSet : WoodSets.WOOD_SETS) {
            setupWoodSet(woodSet);
        }
    }

    public static void setupWoodSet(WoodSet woodSet) {
        EntityRenderers.register(
                woodSet.getBoat().get(),
                (context) -> new BoatRenderer(context, LOCATION_FOR_BOAT.get(woodSet.getBoat()))
        );
        EntityRenderers.register(
                woodSet.getChestBoat().get(),
                (context) -> new BoatRenderer(context, LOCATION_FOR_CHEST_BOAT.get(woodSet.getChestBoat()))
        );

        registerRenderTypes(woodSet);
    }

    public static void registerScreens(RegisterMenuScreensEvent event) {
        event.register(MenuRegistry.FISH_TRAP_MENU.get(), FishTrapScreen::new);
    }

    @SuppressWarnings("deprecation")
    public static void registerRenderTypes(WoodSet woodSet) {
        ItemBlockRenderTypes.setRenderLayer(woodSet.getTrapdoor().get(), RenderType.CUTOUT);
        ItemBlockRenderTypes.setRenderLayer(woodSet.getDoor().get(), RenderType.CUTOUT);
    }

    public static void registerBlockEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(
                // The block entity type to register the renderer for.
                BlockEntityTypeRegistry.VERDANT_CONDUIT_BLOCK_ENTITY.get(),
                // A function of BlockEntityRendererProvider.Context to BlockEntityRenderer.
                VerdantConduitRenderer::new
        );
    }

    public static void registerSpecialModels(RegisterSpecialModelRendererEvent event) {
        event.register(VerdantConduitSpecialRenderer.Unbaked.LOCATION, VerdantConduitSpecialRenderer.Unbaked.MAP_CODEC);
    }
}