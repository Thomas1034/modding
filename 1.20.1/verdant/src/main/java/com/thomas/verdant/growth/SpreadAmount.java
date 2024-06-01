package com.thomas.verdant.growth;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;

public class SpreadAmount {

	private float amount = 1;
	private static final String NAME = "amount";
	private static final SpreadAmount ZERO = new SpreadAmount();

	public float getAmount() {
		return this.amount;
	}

	public void constrain() {
		this.amount = Math.max(this.amount, 0);
	}

	public void setAmount(float toSet) {
		this.amount = Math.max(toSet, 0);
	}

	public void copyFrom(SpreadAmount other) {
		this.amount = other.amount;
	}

	public void saveNBTData(CompoundTag nbt) {
		nbt.putFloat(NAME, this.amount);
	}

	public void loadNBTData(CompoundTag nbt) {
		this.amount = nbt.getFloat(NAME);
	}

	public static float getAmount(Level level) {
		return level.getCapability(SpreadAmountProvider.SPREAD_AMOUNT).orElseGet(() -> ZERO).getAmount();
	}

	public static void setAmount(Level level, float amount) {
		level.getCapability(SpreadAmountProvider.SPREAD_AMOUNT).ifPresent(spread -> spread.setAmount(amount));
	}
}
