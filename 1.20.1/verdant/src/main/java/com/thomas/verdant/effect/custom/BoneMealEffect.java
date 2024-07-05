package com.thomas.verdant.effect.custom;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BoneMealItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class BoneMealEffect extends MobEffect {

	public BoneMealEffect(MobEffectCategory category, int color) {
		super(category, color);
	}

	@Override
	public void applyEffectTick(LivingEntity entity, int amplifier) {
		super.applyEffectTick(entity, amplifier);
		
		// Random chance.
		if (entity.getRandom().nextFloat() >= 0.05 * (amplifier+1)) {
			return;
		}

		
		// Applies bone meal below, at, and above the player.
		BoneMealItem.applyBonemeal(new ItemStack(Items.BONE_MEAL, 64), entity.level(), entity.blockPosition(),
				entity instanceof Player ? (Player) entity : null);
		BoneMealItem.applyBonemeal(new ItemStack(Items.BONE_MEAL, 64), entity.level(), entity.blockPosition().below(),
				entity instanceof Player ? (Player) entity : null);
		BoneMealItem.applyBonemeal(new ItemStack(Items.BONE_MEAL, 64), entity.level(), entity.blockPosition().above(),
				entity instanceof Player ? (Player) entity : null);
		
		// Small chance of applying around the player? Maybe add later.

	}

	@Override
	public boolean isDurationEffectTick(int duration, int amplifier) {
		return true;
	}
}
