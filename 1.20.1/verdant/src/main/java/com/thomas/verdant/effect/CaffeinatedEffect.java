package com.thomas.verdant.effect;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.function.Function;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class CaffeinatedEffect extends MobEffect {

	public CaffeinatedEffect(MobEffectCategory mobEffectCategory, int color) {
		super(mobEffectCategory, color);
	}

	// This effect gives speed, haste, and slowness.
	// The slowness lasts longer.
	@Override
	public void applyEffectTick(LivingEntity entity, int amplifier) {
		super.applyEffectTick(entity, amplifier);

		// Ensure only runs on the client.
		if (entity.level().isClientSide) {
			return;
		}
		
		// Add the effects
		MobEffectInstance haste = new MobEffectInstance(MobEffects.DIG_SPEED, 5, amplifier);
		MobEffectInstance speed = new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 5, amplifier);
		MobEffectInstance slowness = new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 600 + 120 * amplifier, amplifier);
		
		
		entity.addEffect(haste);
		entity.addEffect(speed);
		entity.addEffect(slowness);
	}

	@Override
	public boolean isDurationEffectTick(int duration, int amplifier) {
		return true;
	}
}
