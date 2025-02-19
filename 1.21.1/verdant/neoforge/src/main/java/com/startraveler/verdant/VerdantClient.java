package com.startraveler.verdant;

import com.startraveler.verdant.client.item.RopeGlowProperty;
import com.startraveler.verdant.client.item.RopeHangingBlockProperty;
import com.startraveler.verdant.client.item.RopeHookProperty;
import com.startraveler.verdant.client.item.RopeLengthProperty;
import com.startraveler.verdant.client.renderer.*;
import com.startraveler.verdant.client.screen.FishTrapScreen;
import com.startraveler.verdant.registration.RegistryObject;
import com.startraveler.verdant.registry.BlockEntityTypeRegistry;
import com.startraveler.verdant.registry.EntityTypeRegistry;
import com.startraveler.verdant.registry.MenuRegistry;
import com.startraveler.verdant.registry.WoodSets;
import com.startraveler.verdant.woodset.WoodSet;
import net.minecraft.client.model.BoatModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.entity.vehicle.ChestBoat;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.*;

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
        modBus.addListener(VerdantClient::registerRangeProperties);
        modBus.addListener(VerdantClient::registerSelectProperties);
        modBus.addListener(VerdantClient::registerConditionalProperties);

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
            EntityRenderers.register(EntityTypeRegistry.TIMBERMITE.get(), TimbermiteRenderer::new);
            EntityRenderers.register(EntityTypeRegistry.POISON_ARROW.get(), PoisonArrowRenderer::new);
            EntityRenderers.register(EntityTypeRegistry.ROOTED.get(), RootedRenderer::new);
            EntityRenderers.register(EntityTypeRegistry.THROWN_SPEAR.get(), ThrownSpearRenderer::new);
            EntityRenderers.register(EntityTypeRegistry.DART.get(), TippableDartRenderer::new);
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

    public static void registerSelectProperties(RegisterSelectItemModelPropertyEvent event) {
        event.register(
                // The name to reference as the type
                ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "rope/hanging_block"),
                // The property type
                RopeHangingBlockProperty.TYPE
        );
    }

    public static void registerRangeProperties(RegisterRangeSelectItemModelPropertyEvent event) {
        event.register(
                // The name to reference as the type
                ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "rope/rope_length"),
                // The map codec
                RopeLengthProperty.MAP_CODEC
        );
        event.register(
                // The name to reference as the type
                ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "rope/glow_level"),
                // The map codec
                RopeGlowProperty.MAP_CODEC
        );
    }


    public static void registerConditionalProperties(RegisterConditionalItemModelPropertyEvent event) {
        event.register(
                // The name to reference as the type
                ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "rope/has_hook"),
                // The map codec
                RopeHookProperty.MAP_CODEC
        );
    }
}