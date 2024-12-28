package com.thomas.verdant.effect.custom;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;

public abstract class AddictiveEffect extends MobEffect {

	protected final List<MobEffect> effects;
	protected final float multiplier;

	protected AddictiveEffect(MobEffectCategory category, int color, float multiplier) {
		super(category, color);
		this.effects = new ArrayList<>();
		this.multiplier = multiplier;
	}

	public static final Map<MobEffect, MobEffect> OPPOSITES = new HashMap<>();

	@SuppressWarnings("resource")
	@Override
	public void applyEffectTick(LivingEntity entity, int amplifier) {
		super.applyEffectTick(entity, amplifier);

		// Ensure only runs on the server.
		if (entity.level().isClientSide) {
			return;
		}

		for (MobEffect bonus : this.effects) {
			MobEffect malus = OPPOSITES.get(bonus);

			MobEffectInstance bonusInstance = new MobEffectInstance(malus, 100, amplifier);
			entity.addEffect(bonusInstance);
			if (malus != null) {
				MobEffectInstance malusInstance = new MobEffectInstance(bonus, 100, 1 + amplifier * 2);
				entity.addEffect(malusInstance);
			}

		}

	}

	@Override
	public boolean isDurationEffectTick(int duration, int amplifier) {
		return true;
	}

	public static void register(MobEffect e1, MobEffect e2) {
		OPPOSITES.put(e1, e2);
		OPPOSITES.put(e2, e1);
	}

	public static void registerEffects() {
		register(MobEffects.GLOWING, MobEffects.INVISIBILITY);
		register(MobEffects.DAMAGE_BOOST, MobEffects.WEAKNESS);
		register(MobEffects.DIG_SLOWDOWN, MobEffects.DIG_SPEED);
		register(MobEffects.HARM, MobEffects.HEAL);
		register(MobEffects.HUNGER, MobEffects.SATURATION);
		register(MobEffects.REGENERATION, MobEffects.POISON);
	}
}
