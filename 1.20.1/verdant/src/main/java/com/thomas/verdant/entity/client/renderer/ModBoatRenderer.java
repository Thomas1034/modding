package com.thomas.verdant.entity.client.renderer;

import java.util.Map;
import java.util.stream.Stream;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.util.Pair;
import com.thomas.verdant.Verdant;
import com.thomas.verdant.entity.custom.ModBoatEntity;
import com.thomas.verdant.entity.custom.ModChestBoatEntity;

import net.minecraft.client.model.BoatModel;
import net.minecraft.client.model.ChestBoatModel;
import net.minecraft.client.model.ListModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.BoatRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.vehicle.Boat;

public class ModBoatRenderer extends BoatRenderer {
	private final Map<ModBoatEntity.Type, Pair<ResourceLocation, ListModel<Boat>>> boatResources;

	public ModBoatRenderer(EntityRendererProvider.Context context, boolean isChestBoat) {
		super(context, isChestBoat);
		this.boatResources = Stream.of(ModBoatEntity.Type.values())
				.collect(ImmutableMap.toImmutableMap(type -> type,
						type -> Pair.of(new ResourceLocation(Verdant.MOD_ID, getTextureLocation(type, isChestBoat)),
								this.createBoatModel(context, type, isChestBoat))));
	}

	private static String getTextureLocation(ModBoatEntity.Type pType, boolean pChestBoat) {
		return pChestBoat ? "textures/entity/chest_boat/" + pType.getName() + ".png"
				: "textures/entity/boat/" + pType.getName() + ".png";
	}

	private ListModel<Boat> createBoatModel(EntityRendererProvider.Context context, ModBoatEntity.Type type,
			boolean isChestBoat) {
		ModelLayerLocation modellayerlocation = isChestBoat ? ModBoatRenderer.createChestBoatModelName(type)
				: ModBoatRenderer.createBoatModelName(type);
		ModelPart modelpart = context.bakeLayer(modellayerlocation);
		return isChestBoat ? new ChestBoatModel(modelpart) : new BoatModel(modelpart);
	}

	public static ModelLayerLocation createBoatModelName(ModBoatEntity.Type pType) {
		return createLocation("boat/" + pType.getName(), "main");
	}

	public static ModelLayerLocation createChestBoatModelName(ModBoatEntity.Type pType) {
		return createLocation("chest_boat/" + pType.getName(), "main");
	}

	private static ModelLayerLocation createLocation(String pPath, String pModel) {
		return new ModelLayerLocation(new ResourceLocation(Verdant.MOD_ID, pPath), pModel);
	}

	public Pair<ResourceLocation, ListModel<Boat>> getModelWithLocation(Boat boat) {
		if (boat instanceof ModBoatEntity modBoat) {
			return this.boatResources.get(modBoat.getModVariant());
		} else if (boat instanceof ModChestBoatEntity modChestBoatEntity) {
			return this.boatResources.get(modChestBoatEntity.getModVariant());
		} else {
			return null;
		}
	}
}