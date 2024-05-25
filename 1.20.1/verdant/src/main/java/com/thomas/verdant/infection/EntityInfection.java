package com.thomas.verdant.infection;

import com.thomas.verdant.client.ClientInfectionData;

import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

public class EntityInfection {

	private int level;
	public static final int MAX_LEVEL = 800;
	public static final int MIN_LEVEL = 0;
	private static final String NAME = "infection";
	private static final EntityInfection ZERO = new EntityInfection();

	public int getLevel() {
		return this.level;
	}

	public void addLevel(int toAdd) {
		this.level = Math.min(this.level + toAdd, MAX_LEVEL);
	}

	public void subLevel(int toSub) {
		this.level = Math.max(this.level + toSub, MIN_LEVEL);
	}

	public void setLevel(int toSet) {
		this.level = Math.min(Math.max(toSet, MIN_LEVEL), MAX_LEVEL);
	}

	public void copyFrom(EntityInfection other) {
		this.level = other.level;
	}

	public void saveNBTData(CompoundTag nbt) {
		nbt.putInt(NAME, this.level);
	}

	public void loadNBTData(CompoundTag nbt) {
		this.level = nbt.getInt(NAME);
	}

	public static void updateLevel(LivingEntity le) {

	}

	public static int getLevel(Entity entity) {
		if (entity instanceof AbstractClientPlayer) {
			return ClientInfectionData.getPlayerInfection(entity.getUUID());
		}

		return entity.getCapability(EntityInfectionProvider.ENTITY_INFECTION).orElseGet(() -> ZERO).getLevel();
	}

	public static String getTextureSuffix(int progressionLevel) {
		return "_level_" + (progressionLevel / 200);
	}

}
