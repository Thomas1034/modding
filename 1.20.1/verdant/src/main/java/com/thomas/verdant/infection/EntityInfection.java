package com.thomas.verdant.infection;

import net.minecraft.nbt.CompoundTag;

public class EntityInfection {

	private int level;
	public static final int MAX_LEVEL = 800;
	public static final int MIN_LEVEL = 0;
	private static final String NAME = "infection";

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

}
