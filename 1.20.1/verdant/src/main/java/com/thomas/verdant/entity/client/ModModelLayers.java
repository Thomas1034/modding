package com.thomas.verdant.entity.client;

import com.thomas.verdant.Verdant;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;

public class ModModelLayers {
    public static final ModelLayerLocation DEMO_BOAT_LAYER = new ModelLayerLocation(
            new ResourceLocation(Verdant.MOD_ID, "boat/demo"), "main");
    public static final ModelLayerLocation DEMO_CHEST_BOAT_LAYER = new ModelLayerLocation(
            new ResourceLocation(Verdant.MOD_ID, "chest_boat/demo"), "main");

}
