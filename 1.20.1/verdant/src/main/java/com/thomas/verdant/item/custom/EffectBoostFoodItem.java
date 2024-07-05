package com.thomas.verdant.item.custom;

import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class EffectBoostFoodItem extends Item {

	private final Supplier<MobEffect> effect;
	private final Function<Integer, MobEffectInstance> getEffect;

	public EffectBoostFoodItem(Properties properties, Supplier<MobEffect> effect,
			Function<Integer, MobEffectInstance> getEffect) {
		super(properties);
		this.effect = effect;
		this.getEffect = getEffect;
	}

	@Override
	public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity eater) {
		if (this.isEdible() && !level.isClientSide) {
			// Find the mob effect instance that the eater has.
			MobEffectInstance instance = eater.getEffect(this.effect.get());

			int amplifier = -1;
			// Check if it exists.
			if (instance != null) {
				amplifier = instance.getAmplifier();
			}
			MobEffectInstance newEffect = this.getEffect.apply(amplifier);
			eater.addEffect(newEffect);
		}
		return this.isEdible() ? eater.eat(level, stack) : stack;
	}

}
