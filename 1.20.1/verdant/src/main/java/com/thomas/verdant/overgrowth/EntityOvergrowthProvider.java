package com.thomas.verdant.overgrowth;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;

public class EntityOvergrowthProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
	public static final Capability<EntityOvergrowth> ENTITY_OVERGROWTH = CapabilityManager
			.get(new CapabilityToken<EntityOvergrowth>() {
			});

	private EntityOvergrowth overgrowth = null;
	private final LazyOptional<EntityOvergrowth> optional = LazyOptional.of(this::getEntityInfection);

	private EntityOvergrowth getEntityInfection() {
		if (this.overgrowth == null) {
			this.overgrowth = new EntityOvergrowth();
		}

		return this.overgrowth;
	}

	@Override
	public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
		if (cap == ENTITY_OVERGROWTH) {
			return optional.cast();
		}

		return LazyOptional.empty();
	}

	@Override
	public CompoundTag serializeNBT() {
		CompoundTag nbt = new CompoundTag();
		getEntityInfection().saveNBTData(nbt);
		return nbt;
	}

	@Override
	public void deserializeNBT(CompoundTag nbt) {
		getEntityInfection().loadNBTData(nbt);
	}

}
