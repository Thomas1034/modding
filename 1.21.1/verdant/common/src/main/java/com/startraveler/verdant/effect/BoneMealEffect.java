package com.startraveler.verdant.effect;


import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.BoneMealItem;
import net.minecraft.world.item.Items;

public class BoneMealEffect extends MobEffect {

    public BoneMealEffect(MobEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public boolean applyEffectTick(ServerLevel level, LivingEntity entity, int amplifier) {
        super.applyEffectTick(level, entity, amplifier);

        // Random chance.
        if (entity.canBeCollidedWith() && entity.getRandom().nextFloat() >= 0.05 * (amplifier + 1)) {


            // Applies bone meal below, at, and above the player.

            BoneMealItem.growCrop(Items.BONE_MEAL.getDefaultInstance(), entity.level(), entity.blockPosition());
            BoneMealItem.growCrop(Items.BONE_MEAL.getDefaultInstance(), entity.level(), entity.blockPosition().below());
            BoneMealItem.growCrop(Items.BONE_MEAL.getDefaultInstance(), entity.level(), entity.blockPosition().above());
        }
        return true;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return true;
    }
}
