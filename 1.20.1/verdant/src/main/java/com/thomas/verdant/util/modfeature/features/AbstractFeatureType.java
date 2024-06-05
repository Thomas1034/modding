package com.thomas.verdant.util.modfeature.features;

import com.thomas.verdant.util.data.DataParseable;
import com.thomas.verdant.util.modfeature.checkers.AbstractCheckerType;
import com.thomas.verdant.util.modfeature.placements.AbstractPlacementType;
import com.thomas.verdant.util.modfeature.placers.AbstractPlacerType;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;

public abstract class AbstractFeatureType implements DataParseable<AbstractFeatureType> {

	private final ResourceLocation name;
	private int maxAttempts;
	private AbstractCheckerType shouldPlace;
	private AbstractCheckerType canPlace;
	private AbstractPlacementType placement;
	private AbstractPlacerType placer;

	public AbstractFeatureType(ResourceLocation name) {
		this.name = name;
	}

	public boolean shouldPlace(ServerLevel level, BlockPos pos) {
		return this.shouldPlace.check(level, pos);
	}

	public void place(ServerLevel level, BlockPos pos) {
		BlockPos placementPos;
		int attempts = 0;
		do {
			placementPos = this.placement.get(level, pos);
			attempts++;
		} while (attempts < this.maxAttempts && !this.canPlace.check(level, placementPos));
		this.placer.place(level, placementPos);
	}

	public ResourceLocation getLocation() {
		return this.name;
	}
}
