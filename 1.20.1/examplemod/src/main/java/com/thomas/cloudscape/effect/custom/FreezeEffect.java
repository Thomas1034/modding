package com.thomas.cloudscape.effect.custom;

import com.thomas.cloudscape.util.Utilities;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class FreezeEffect extends MobEffect {

	// Applies a freezing effect to the entity.
	public FreezeEffect(MobEffectCategory mobEffectCategory, int color) {
		super(mobEffectCategory, color);
	}

	@Override
	public void applyEffectTick(LivingEntity entity, int amplifier) {
		super.applyEffectTick(entity, amplifier);
		entity.setIsInPowderSnow(true);
		if (entity.getRandom().nextInt(10) < 2) {
			Utilities.addParticlesAroundEntity(entity, ParticleTypes.SNOWFLAKE);
		}
	}

	@Override
	public boolean isDurationEffectTick(int duration, int amplifier) {
		return true;
	}
}
