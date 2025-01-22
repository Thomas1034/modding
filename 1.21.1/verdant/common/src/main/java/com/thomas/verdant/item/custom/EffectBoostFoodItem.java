package com.thomas.verdant.item.custom;

import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.function.Function;
import java.util.function.Supplier;

public class EffectBoostFoodItem extends Item {

    private final Supplier<Holder<MobEffect>> effect;
    private final Function<Integer, MobEffectInstance> getEffect;

    public EffectBoostFoodItem(Properties properties, Supplier<Holder<MobEffect>> effect, Function<Integer, MobEffectInstance> getEffect) {
        super(properties);
        this.effect = effect;
        this.getEffect = getEffect;

    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity eater) {
        FoodProperties food = stack.get(DataComponents.FOOD);

        if (food != null && !level.isClientSide) {
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
        return super.finishUsingItem(stack, level, eater);
    }

}