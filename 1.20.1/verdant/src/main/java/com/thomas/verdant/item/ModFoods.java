package com.thomas.verdant.item;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;

public class ModFoods {

	public static final FoodProperties LEMON_JUICE = (new FoodProperties.Builder()).nutrition(3).saturationMod(0.25F)
			.effect(() -> new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 1500), 0.5f).build();
}
