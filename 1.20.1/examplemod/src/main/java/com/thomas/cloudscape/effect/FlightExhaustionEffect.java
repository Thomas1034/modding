package com.thomas.cloudscape.effect;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class FlightExhaustionEffect extends MobEffect {
	public FlightExhaustionEffect(MobEffectCategory mobEffectCategory, int color) {
		super(mobEffectCategory, color);
	}

	// This effect does nothing directly; instead, it is used by wings to control
	// various aspects of flight.
	@Override
	public void applyEffectTick(LivingEntity entity, int amplifier) {
		super.applyEffectTick(entity, amplifier);
	}

	@Override
	public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
		return true;
	}

	// This effect cannot be cured.
	@Override
	public List<ItemStack> getCurativeItems() {
		ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
		return ret;
	}
}
