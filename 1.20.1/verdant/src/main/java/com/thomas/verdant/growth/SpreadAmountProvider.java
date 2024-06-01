package com.thomas.verdant.growth;

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

public class SpreadAmountProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
	public static final Capability<SpreadAmount> SPREAD_AMOUNT = CapabilityManager
			.get(new CapabilityToken<SpreadAmount>() {
			});

	private SpreadAmount spreadAmount = null;
	private final LazyOptional<SpreadAmount> optional = LazyOptional.of(this::getSpreadAmount);

	private SpreadAmount getSpreadAmount() {
		if (this.spreadAmount == null) {
			this.spreadAmount = new SpreadAmount();
		}

		return this.spreadAmount;
	}

	@Override
	public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
		if (cap == SPREAD_AMOUNT) {
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
