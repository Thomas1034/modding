package com.thomas.zirconmod.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class ResonatorBlockEntity extends BlockEntity {

	private int cooldown = 0;
	private boolean powered = false;
	private static final String COOLDOWN_NAME = "Cooldown";

	public ResonatorBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
		super(type, pos, state);
	}

	public ResonatorBlockEntity(BlockPos pos, BlockState state) {
		super(ModBlockEntities.RESONATOR.get(), pos, state);
	}

	// Add the cooldown time.
	@Override
	protected void saveAdditional(CompoundTag tag) {
		super.saveAdditional(tag);
		tag.putInt(COOLDOWN_NAME, this.cooldown);
	}

	@Override
	public void load(CompoundTag tag) {
		super.load(tag);
		if (tag.contains(COOLDOWN_NAME)) {

			this.cooldown = Math.max(tag.getInt(COOLDOWN_NAME), 0);
		} else {
			this.cooldown = 0;
		}
	}

	public void updateCooldown(int cooldown) {
		this.cooldown = cooldown;
	}

	public void decreaseCooldown() {
		this.cooldown = Math.max(this.cooldown - 1, 0);
	}

	public void setPowered(boolean powered) {
		this.powered = powered;
	}
	
	public boolean isPowered() {
		return this.powered;
	}

	public int getCooldown() {
		return cooldown;
	}

}
