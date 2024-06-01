package com.thomas.verdant.overgrowth;

import com.thomas.verdant.client.ClientOvergrowthData;

import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

public class EntityOvergrowth {

	private int level;
	public static final int STAGES = 8;
	private static final int MINUTES_TO_MAX = 2;
	public static final int MAX_LEVEL = 1200 * MINUTES_TO_MAX;
	public static final int MIN_LEVEL = 0;
	public static final int FRIEND = MIN_LEVEL + (MAX_LEVEL - MIN_LEVEL) / (STAGES / 2);
	private static final String NAME = "overgrowth";
	private static final EntityOvergrowth ZERO = new EntityOvergrowth();

	public int getLevel() {
		return this.level;
	}

	public void constrain() {
		this.level = Math.min(Math.max(this.level, MIN_LEVEL), MAX_LEVEL);
	}

	public void addLevel(int toAdd) {
		this.level = Math.min(this.level + toAdd, MAX_LEVEL);
		this.constrain();
	}

	public void subLevel(int toSub) {
		this.level = Math.max(this.level - toSub, MIN_LEVEL);
	}

	public void setLevel(int toSet) {
		this.level = Math.min(Math.max(toSet, MIN_LEVEL), MAX_LEVEL);
	}

	public void copyFrom(EntityOvergrowth other) {
		this.level = other.level;
	}

	public void saveNBTData(CompoundTag nbt) {
		nbt.putInt(NAME, this.level);
	}

	public void loadNBTData(CompoundTag nbt) {
		this.level = nbt.getInt(NAME);
	}

	public static int getLevel(Entity entity) {
		if (entity instanceof AbstractClientPlayer) {
			return ClientOvergrowthData.getPlayerOvergrowth(entity.getUUID());
		}

		return entity.getCapability(EntityOvergrowthProvider.ENTITY_OVERGROWTH).orElseGet(() -> ZERO).getLevel();
	}

	public static void addLevel(LivingEntity entity, int level) {
		entity.getCapability(EntityOvergrowthProvider.ENTITY_OVERGROWTH)
				.ifPresent(overgrowth -> overgrowth.addLevel(level));
	}

	public static void setLevel(LivingEntity entity, int level) {
		entity.getCapability(EntityOvergrowthProvider.ENTITY_OVERGROWTH)
				.ifPresent(overgrowth -> overgrowth.setLevel(level));
	}

	public static String getTextureSuffix(int progressionLevel) {
		return "_level_" + (progressionLevel / (MAX_LEVEL / 4));
	}

}
