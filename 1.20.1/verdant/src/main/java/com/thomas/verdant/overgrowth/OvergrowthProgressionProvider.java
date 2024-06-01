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

public class OvergrowthProgressionProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
	public static final Capability<OvergrowthProgression> OVERGROWTH_PROGRESSION = CapabilityManager
			.get(new CapabilityToken<OvergrowthProgression>() {
			});

	private OvergrowthProgression spreadRules = null;
	private final LazyOptional<OvergrowthProgression> optional = LazyOptional.of(this::getSpreadAmount);

	private OvergrowthProgression getSpreadAmount() {
		if (this.spreadRules == null) {
			this.spreadRules = new OvergrowthProgression();
		}

		return this.spreadRules;
	}

	@Override
	public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
		if (cap == OVERGROWTH_PROGRESSION) {
			return optional.cast();
		}

		return LazyOptional.empty();
	}

	@Override
	public CompoundTag serializeNBT() {
		CompoundTag nbt = new CompoundTag();
		getSpreadAmount().saveNBTData(nbt);
		return nbt;
	}

	@Override
	public void deserializeNBT(CompoundTag nbt) {
		getSpreadAmount().loadNBTData(nbt);
	}

}
