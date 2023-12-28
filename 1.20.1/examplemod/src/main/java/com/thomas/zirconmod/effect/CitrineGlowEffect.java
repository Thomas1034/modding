package com.thomas.zirconmod.effect;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class CitrineGlowEffect extends MobEffect {
	public CitrineGlowEffect(MobEffectCategory mobEffectCategory, int color) {
		super(mobEffectCategory, color);
	}

	// This effect actually does nothing directly; instead, it impacts how other
	// blocks and entities react.
	@Override
	public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
		super.applyEffectTick(pLivingEntity, pAmplifier);
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
