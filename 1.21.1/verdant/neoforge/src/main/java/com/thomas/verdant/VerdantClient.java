package com.thomas.verdant;

import com.thomas.verdant.registration.RegistryObject;
import com.thomas.verdant.registry.properties.WoodSets;
import com.thomas.verdant.woodset.WoodSet;
import net.minecraft.client.model.BoatModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.BoatRenderer;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.entity.vehicle.ChestBoat;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

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

        WOOD_SETS.add(WoodSets.STRANGLER);
        for (WoodSet set : WOOD_SETS) {
            registerLayersForWoodSet(set);
        }
        modBus.addListener(VerdantClient::onClientSetup);
        modBus.addListener(VerdantClient::onRegisterLayerDefinitions);
    }

    public static void onRegisterLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        for (WoodSet woodSet : WOOD_SETS) {
            Constants.LOG.warn("Registering layer definitions for wood set {}", woodSet.getName());
            event.registerLayerDefinition(locationForBoat.get(woodSet.getBoat()), BoatModel::createBoatModel);
            event.registerLayerDefinition(locationForChestBoat.get(woodSet.getChestBoat()), BoatModel::createChestBoatModel);
        }
    }

    public static void registerLayersForWoodSet(WoodSet woodSet) {
        Constants.LOG.warn("Registering layers for wood set {}", woodSet.getName());
        ModelLayerLocation boat = new ModelLayerLocation(ResourceLocation.withDefaultNamespace("boat/" + woodSet.getName()), "main");
        // ModelLayers.register("boat/" + woodSet.getName());
        ModelLayerLocation chestBoat = new ModelLayerLocation(ResourceLocation.withDefaultNamespace("chest_boat/" + woodSet.getName()), "main");
        // ModelLayers.register("chest_boat/" + woodSet.getName());
        locationForBoat.put(woodSet.getBoat(), boat);
        locationForChestBoat.put(woodSet.getChestBoat(), chestBoat);
    }

    public static void onClientSetup(FMLClientSetupEvent event) {
        for (WoodSet woodSet : WOOD_SETS) {
            EntityRenderers.register(woodSet.getBoat().get(), (context) -> new BoatRenderer(context, locationForBoat.get(woodSet.getBoat())));
            EntityRenderers.register(woodSet.getChestBoat().get(), (context) -> new BoatRenderer(context, locationForChestBoat.get(woodSet.getChestBoat())));
        }
    }
}