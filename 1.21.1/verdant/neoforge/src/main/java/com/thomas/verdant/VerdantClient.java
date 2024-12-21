package com.thomas.verdant;

import com.thomas.verdant.client.item.VerdantItemProperties;
import com.thomas.verdant.client.item.properties.RopeHasHookPropertyFunction;
import com.thomas.verdant.client.item.properties.RopeLengthPropertyFunction;
import com.thomas.verdant.client.screen.FishTrapScreen;
import com.thomas.verdant.registration.RegistryObject;
import com.thomas.verdant.registry.EntityTypeRegistry;
import com.thomas.verdant.registry.ItemRegistry;
import com.thomas.verdant.registry.MenuRegistry;
import com.thomas.verdant.registry.WoodSets;
import com.thomas.verdant.woodset.WoodSet;
import net.minecraft.client.model.BoatModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.BoatRenderer;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.client.renderer.item.ItemProperties;
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

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Mod(value = "verdant", dist = Dist.CLIENT)
public class VerdantClient {

    protected static final Map<RegistryObject<EntityType<?>, EntityType<? extends Boat>>, ModelLayerLocation> locationForBoat = new HashMap<>();
    protected static final Map<RegistryObject<EntityType<?>, EntityType<? extends ChestBoat>>, ModelLayerLocation> locationForChestBoat = new HashMap<>();
    protected static final Set<WoodSet> WOOD_SETS = new HashSet<>();

    public VerdantClient(IEventBus modBus) {

        // Add boat renderers
        for (WoodSet woodSet : WoodSets.WOOD_SETS) {
            registerBoatRenderers(woodSet);
        }
        modBus.addListener(VerdantClient::onClientSetup);
        modBus.addListener(VerdantClient::onRegisterLayerDefinitions);
        modBus.addListener(VerdantClient::registerScreens);

    }

    public static void onRegisterLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        for (WoodSet woodSet : WOOD_SETS) {
            event.registerLayerDefinition(locationForBoat.get(woodSet.getBoat()), BoatModel::createBoatModel);
            event.registerLayerDefinition(
                    locationForChestBoat.get(woodSet.getChestBoat()),
                    BoatModel::createChestBoatModel
            );
        }
    }

    public static void registerLayersForWoodSet(WoodSet woodSet) {
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
        locationForBoat.put(woodSet.getBoat(), boat);
        locationForChestBoat.put(woodSet.getChestBoat(), chestBoat);
    }

    public static void onClientSetup(FMLClientSetupEvent event) {
        for (WoodSet woodSet : WOOD_SETS) {
            EntityRenderers.register(
                    woodSet.getBoat().get(),
                    (context) -> new BoatRenderer(context, locationForBoat.get(woodSet.getBoat()))
            );
            EntityRenderers.register(
                    woodSet.getChestBoat().get(),
                    (context) -> new BoatRenderer(context, locationForChestBoat.get(woodSet.getChestBoat()))
            );
        }

        EntityRenderers.register(EntityTypeRegistry.THROWN_ROPE.get(), ThrownItemRenderer::new);

        registerItemProperties();
    }

    private static void registerScreens(RegisterMenuScreensEvent event) {

        event.register(MenuRegistry.FISH_TRAP_MENU.get(), FishTrapScreen::new);
    }

    protected static void registerItemProperties() {
        ItemProperties.register(
                ItemRegistry.ROPE_COIL.get(),
                VerdantItemProperties.ROPE_LENGTH,
                new RopeLengthPropertyFunction()
        );
        ItemProperties.register(
                ItemRegistry.ROPE_COIL.get(),
                VerdantItemProperties.HAS_HOOK,
                new RopeHasHookPropertyFunction()
        );
    }

    protected void registerBoatRenderers(WoodSet woodSet) {
        WOOD_SETS.add(woodSet);
        registerLayersForWoodSet(woodSet);
    }
}