package com.thomas.verdant.effect.custom;

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

public class FoodPoisoningEffect extends MobEffect {

	private static final HashSet<WeightedEffectHolder> EFFECTS = new HashSet<>();

	public FoodPoisoningEffect(MobEffectCategory mobEffectCategory, int color) {
		super(mobEffectCategory, color);
	}

	// This effect inflicts other harmful effects with a low chance every tick.
	// This should be interesting.
	// The inflicted level is the same as that of this effect.
	// An inflicted effect is more likely if the amplifier is higher.
	@Override
	public void applyEffectTick(LivingEntity entity, int amplifier) {
		super.applyEffectTick(entity, amplifier);

		// Ensure only runs on the client.
		if (entity.level().isClientSide) {
			return;
		}
		
		// Check if should continue.
		// Once every twenty ticks is one second.
		// Once every two seconds is when it should inflict.
		float effectChance = 0.05f * 0.5f * (amplifier + 1);
		if (entity.getRandom().nextFloat() >= effectChance) {
			return;
		}

		// Get a random effect holder.
		WeightedEffectHolder holder = null;
		int totalWeight = EFFECTS.stream().mapToInt((el) -> el.weight()).reduce((a, b) -> a + b).orElseGet(() -> 1);
		int r = entity.getRandom().nextInt(totalWeight);
		for (WeightedEffectHolder el : EFFECTS) {
			r -= el.weight();
			if (r <= 0) {
				holder = el;
				break;
			}
		}

		// If the holder is still null, there has been an error.
		// Print out a warning message.
		if (holder == null) {
			System.out.println("Warning! Failed to inflict an effect in applyEffectTick for FoodPoisoningEffect.");
			System.out.println("There was " + r + " weight remaining. ");
			System.out.println("Amplifier: " + amplifier);
			System.out.println("Effects to choose from: ");
			for (WeightedEffectHolder el : EFFECTS) {
				System.out.println("Effect: " + el.effect().getDisplayName().getString());
				System.out.println("\tDuration : " + el.duration());
				System.out.println("\tWeight   : " + el.weight());
			}
			return;
		}

		// Otherwise, add the effect!
		// Step back, and watch the drama unfold.
		entity.addEffect(holder.get(amplifier));
	}

	@Override
	public boolean isDurationEffectTick(int duration, int amplifier) {
		return true;
	}

	public static void register(int weight, int duration, MobEffect effect) {
		EFFECTS.add(new WeightedEffectHolder(weight, duration, effect));
	}

	public static void registerEffects() {
		// Hunger
		register(20, 200, MobEffects.HUNGER);
		// Slowness
		register(10, 200, MobEffects.MOVEMENT_SLOWDOWN);
		// Weakness
		register(10, 200, MobEffects.WEAKNESS);
		// Nausea
		// They'll hate me.
		register(10, 100, MobEffects.CONFUSION);
		// Mining Fatigue
		register(5, 200, MobEffects.DIG_SLOWDOWN);
		// Poison
		register(5, 200, MobEffects.POISON);
		System.out.println("Registered " + EFFECTS.size() + " food poisoning effects.");


	}

	public static class WeightedEffectHolder {
		private int weight = 1;
		private int duration = 0;
		private MobEffect effect = MobEffects.SLOW_FALLING;
		private final Function<Integer, MobEffectInstance> provider = (amplifier) -> new MobEffectInstance(this.effect,
				this.duration, amplifier);

		public WeightedEffectHolder(int weight, int duration, MobEffect effect) {
			this.weight = weight;
			this.duration = duration;
			this.effect = effect;
		}

		public MobEffectInstance get(int amplifier) {
			return this.provider.apply(amplifier);
		}

		public int weight() {
			return this.weight;
		}

		public int duration() {
			return this.duration;
		}

		public MobEffect effect() {
			return this.effect;
		}

	}
}
