package com.thomas.verdant.effect.custom;

import com.thomas.verdant.effect.ModMobEffects;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;

public class CassavaPoisoningEffect extends MobEffect {

	public CassavaPoisoningEffect(MobEffectCategory category, int color) {
		super(category, color);
	}

	// 0 ->
	// 1 ->
	// 2 ->
	// 3 ->
	// 4 ->
	// 5 -> slowness 1
	// 6 -> slowness 1
	// 7 -> slowness 1 weakness 1
	// 8 -> slowness 1 weakness 1
	// 9 -> slowness 2 weakness 1
	// 10 -> slowness 2 weakness 1
	// 11 -> slowness 2 weakness 2
	// 12 -> slowness 2 weakness 2
	// 13 -> slowness 2 weakness 2 food poisoning 1
	// 14 -> slowness 2 weakness 2 food poisoning 1
	// 15 -> slowness 2 weakness 2 food poisoning 2
	// 15 -> slowness 2 weakness 2 food poisoning 2

	// 0 -> slowness 1
	// 1 -> slowness 1 weakness 1
	// 2 -> slowness 2 weakness 1
	// 3 -> slowness 2 weakness 2
	// 4 -> slowness 3 weakness 2 food poisoning 1
	// 5 -> slowness 3 weakness 3 food poisoning 1
	// 6 -> slowness 4 weakness 3 food poisoning 2

	// This effect inflicts other harmful effects depending on the level.
	@SuppressWarnings("resource")
	@Override
	public void applyEffectTick(LivingEntity entity, int amplifier) {
		super.applyEffectTick(entity, amplifier);

		// Ensure only runs on the server.
		if (entity.level().isClientSide) {
			return;
		}
		int level = (amplifier - 5) / 2;

		// System.out.println(amplifier + " -> " + level);

		int slownessLevel = Math.max(-1, level / 2);
		int weaknessLevel = Math.max(-1, (level) / 2 - 1);
		int miningFatigueLevel = Math.max(-1, (level) / 2 - 2);
		int foodPoisoningLevel = Math.max(-1, (level) / 2 - 3);
		int witherLevel = Math.max(-1, (level) / 2 - 5);

		if (level >= 0) {
			if (slownessLevel >= 0) {
				entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 600, slownessLevel));
			}
			if (weaknessLevel >= 0) {
				entity.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 600, weaknessLevel));
			}
			if (foodPoisoningLevel >= 0) {
				entity.addEffect(new MobEffectInstance(ModMobEffects.FOOD_POISONING.get(), 600, foodPoisoningLevel));
			}
			if (miningFatigueLevel >= 0) {
				entity.addEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN, 600, miningFatigueLevel));
			}
			if (witherLevel >= 0) {
				entity.addEffect(new MobEffectInstance(MobEffects.WITHER, 600, witherLevel));
			}
		}

	}

	@Override
	public boolean isDurationEffectTick(int duration, int amplifier) {
		return true;
	}

}
