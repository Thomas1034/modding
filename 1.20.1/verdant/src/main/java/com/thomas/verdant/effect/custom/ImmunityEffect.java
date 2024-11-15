package com.thomas.verdant.effect.custom;

import java.util.List;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class ImmunityEffect extends MobEffect {

	private final List<MobEffect> immuneTo;

	public ImmunityEffect(MobEffectCategory category, int color, List<MobEffect> immuneTo) {
		super(category, color);
		this.immuneTo = immuneTo;
	}

	@Override
	public void applyEffectTick(LivingEntity entity, int amplifier) {
		super.applyEffectTick(entity, amplifier);
		boolean removedAny = false;
		// Remove listed effects
		for (MobEffect effect : this.immuneTo) {
			removedAny |= entity.removeEffect(effect);
		}
		if (removedAny && amplifier > 0) {
			// entity.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 60,
			// amplifier));
		}

	}

	@Override
	public boolean isDurationEffectTick(int duration, int amplifier) {
		return true;
	}
}
