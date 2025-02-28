package com.thomas.verdant.effect.custom;

import com.thomas.verdant.overgrowth.EntityOvergrowthEffects;

import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;

public class CaffeinatedEffect extends AddictiveEffect {

	public CaffeinatedEffect(MobEffectCategory mobEffectCategory, int color) {
		super(mobEffectCategory, color, 100);
		this.effects.add(MobEffects.DIG_SPEED);
		this.effects.add(MobEffects.MOVEMENT_SPEED);
	}

	// This effect gives speed, haste, and slowness.
	// The slowness lasts longer.
	@SuppressWarnings("resource")
	@Override
	public void applyEffectTick(LivingEntity entity, int amplifier) {
		super.applyEffectTick(entity, amplifier);

		// Ensure only runs on the server.
		if (entity.level().isClientSide) {
			return;
		}

		// Add the effects
		MobEffectInstance haste = new MobEffectInstance(MobEffects.DIG_SPEED, 100, amplifier);
		MobEffectInstance speed = new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 100, 1 + amplifier * 2);
		MobEffectInstance slowness = new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 600 + 1200 * (amplifier + 1),
				amplifier);

		entity.addEffect(haste);
		entity.addEffect(speed);
		if (!EntityOvergrowthEffects.isFriend(entity)) {
			entity.addEffect(slowness);
		}
	}

	@Override
	public boolean isDurationEffectTick(int duration, int amplifier) {
		return true;
	}
}
