package com.thomas.zirconmod.entity.client;

import com.thomas.zirconmod.ZirconMod;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;

public class ModModelLayers {
	public static final ModelLayerLocation MOLE_LAYER = new ModelLayerLocation(
			new ResourceLocation(ZirconMod.MOD_ID, "mole_layer"), "main");
	public static final ModelLayerLocation WOOD_GOLEM_LAYER = new ModelLayerLocation(
			new ResourceLocation(ZirconMod.MOD_ID, "wood_golem"), "main");
	public static final ModelLayerLocation NIMBULA_LAYER = new ModelLayerLocation(
			new ResourceLocation(ZirconMod.MOD_ID, "nimbula"), "main");
	public static final ModelLayerLocation WISP_LAYER = new ModelLayerLocation(
			new ResourceLocation(ZirconMod.MOD_ID, "wisp"), "main");
	public static final ModelLayerLocation TEMPEST_LAYER = new ModelLayerLocation(
			new ResourceLocation(ZirconMod.MOD_ID, "tempest"), "main");
	public static final ModelLayerLocation TEMPEST_POWER_LAYER = new ModelLayerLocation(
			new ResourceLocation(ZirconMod.MOD_ID, "tempest_power"), "main");
    public static final ModelLayerLocation PALM_BOAT_LAYER = new ModelLayerLocation(
            new ResourceLocation(ZirconMod.MOD_ID, "boat/palm"), "main");
    public static final ModelLayerLocation PALM_CHEST_BOAT_LAYER = new ModelLayerLocation(
            new ResourceLocation(ZirconMod.MOD_ID, "chest_boat/palm"), "main");
	public static final ModelLayerLocation GUST_LAYER = new ModelLayerLocation(
			new ResourceLocation(ZirconMod.MOD_ID, "gust"), "main");

}
