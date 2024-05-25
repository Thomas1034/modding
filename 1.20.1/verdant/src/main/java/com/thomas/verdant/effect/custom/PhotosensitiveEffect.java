package com.thomas.verdant.effect.custom;

import com.thomas.verdant.damage.ModDamageSources;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class PhotosensitiveEffect extends MobEffect {

	public PhotosensitiveEffect(MobEffectCategory category, int color) {
		super(category, color);
	}

	@Override
	public void applyEffectTick(LivingEntity entity, int amplifier) {
		super.applyEffectTick(entity, amplifier);

		// Check tick.
		if (entity.tickCount % (40 / amplifier) > 0) {
			return;
		}
		// Check for sky access.
		if (!entity.level().canSeeSky(entity.blockPosition())) {
			return;
		}
		// Do some damage.
		DamageSource source = ModDamageSources.get(entity.level(), ModDamageSources.THORN_BUSH);
		entity.hurt(source, amplifier);
	}

	@Override
	public boolean isDurationEffectTick(int duration, int amplifier) {
		return true;
	}
}