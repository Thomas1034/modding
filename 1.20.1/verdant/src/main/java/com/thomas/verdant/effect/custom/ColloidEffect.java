package com.thomas.verdant.effect.custom;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;

public class ColloidEffect extends MobEffect {

	public ColloidEffect(MobEffectCategory category, int color) {
		super(category, color);
		// TODO Auto-generated constructor stub
	}

	@SuppressWarnings("resource")
	@Override
	public void applyEffectTick(LivingEntity entity, int amplifier) {

		// Ensure only runs on the server.
		if (entity.level().isClientSide) {
			return;
		}
		int timeSinceHurt = entity.invulnerableDuration - entity.invulnerableTime;
		// System.out.println("t: " + timeSinceHurt + " & b: " + (timeSinceHurt < 5));
		if (timeSinceHurt < 5) {
			// Inflict stacked slowness debuffs.
			int baseTime = 5;
			int max = 5 * (amplifier + 2);
			for (int i = 0; i < max; i++) {
				// System.out.println("i: " + i);
				// System.out.println("d: " + (baseTime * (max - i)));
				// System.out.println("l: " + (2 * i));
				entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, baseTime * (max - i), 2 * i));
			}
		}

	}

	@Override
	public boolean isDurationEffectTick(int duration, int amplifier) {
		return true;
	}
}
