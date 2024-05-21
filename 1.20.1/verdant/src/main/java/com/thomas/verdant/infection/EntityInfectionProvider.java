package com.thomas.verdant.infection;

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

public class EntityInfectionProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
	public static final Capability<EntityInfection> ENTITY_INFECTION = CapabilityManager
			.get(new CapabilityToken<EntityInfection>() {
			});

	private EntityInfection thirst = null;
	private final LazyOptional<EntityInfection> optional = LazyOptional.of(this::createEntityInfection);

	private EntityInfection createEntityInfection() {
		if (this.thirst == null) {
			this.thirst = new EntityInfection();
		}

		return this.thirst;
	}

	@Override
	public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
		if (cap == ENTITY_INFECTION) {
			return optional.cast();
		}

		return LazyOptional.empty();
	}

	@Override
	public CompoundTag serializeNBT() {
		CompoundTag nbt = new CompoundTag();
		createEntityInfection().saveNBTData(nbt);
		return nbt;
	}

	@Override
	public void deserializeNBT(CompoundTag nbt) {
		createEntityInfection().loadNBTData(nbt);
	}

}
