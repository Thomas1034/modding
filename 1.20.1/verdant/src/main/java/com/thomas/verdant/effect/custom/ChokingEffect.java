package com.thomas.verdant.effect.custom;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

public class ChokingEffect extends MobEffect {

	public ChokingEffect(MobEffectCategory category, int color) {
		super(category, color);
	}

	@Override
	public void applyEffectTick(LivingEntity entity, int amplifier) {
		super.applyEffectTick(entity, amplifier);

		int air = entity.getAirSupply();
		int respiration = EnchantmentHelper.getRespiration(entity);
		MobEffectInstance waterBreathing = entity.getEffect(MobEffects.WATER_BREATHING);
		if (waterBreathing != null) {
			amplifier -= waterBreathing.getAmplifier();
		}
		amplifier -= respiration;
		if (amplifier >= 0) {
			entity.setAirSupply(air - (amplifier + 4));
		}
	}

	@Override
	public boolean isDurationEffectTick(int duration, int amplifier) {
		return true;
	}
}
