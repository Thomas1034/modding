package com.thomas.verdant.effect.custom;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffects;

public abstract class AddictiveEffect extends MobEffect{
	
	protected AddictiveEffect(MobEffectCategory category, int color) {
		super(category, color);
	}

	public static final Map<MobEffect, MobEffect> OPPOSITES = new HashMap<>();
	
	public static void register(MobEffect e1, MobEffect e2) {
		OPPOSITES.put(e1, e2);
		OPPOSITES.put(e2, e1);
	}
	
	public static void registerEffects() {
		register(MobEffects.DARKNESS, MobEffects.NIGHT_VISION);
		register(MobEffects.GLOWING, MobEffects.INVISIBILITY);
		register(MobEffects.DAMAGE_BOOST, MobEffects.WEAKNESS);
		register(MobEffects.DIG_SLOWDOWN, MobEffects.DIG_SPEED);
		register(MobEffects.HARM, MobEffects.HEAL);
		register(MobEffects.HUNGER, MobEffects.SATURATION);
		register(MobEffects.REGENERATION, MobEffects.POISON);
	}
	
	
	
}
