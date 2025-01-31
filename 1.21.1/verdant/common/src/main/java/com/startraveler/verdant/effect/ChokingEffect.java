package com.startraveler.verdant.effect;

import com.startraveler.verdant.util.VerdantTags;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class ChokingEffect extends MobEffect {

    protected static final int ROUNDING_TO = 20;

    public ChokingEffect(MobEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public boolean applyEffectTick(ServerLevel level, LivingEntity entity, int amplifier) {
        super.applyEffectTick(level, entity, amplifier);

        float amountOfAirToTake = 2 * amplifier + 5.5f;

        for (MobEffectInstance effectInstance : entity.getActiveEffects()) {
            if (effectInstance.getEffect().is(VerdantTags.MobEffects.AIRLESS_BREATHING)) {
                amountOfAirToTake -= 2 * (1 + effectInstance.getAmplifier());
            }
        }

        amountOfAirToTake -= 0.5f * (float) entity.getAttributeValue(Attributes.OXYGEN_BONUS);

        int finalAmountOfAirToTake = (int) amountOfAirToTake;
        amountOfAirToTake -= finalAmountOfAirToTake;
        if (amountOfAirToTake * ROUNDING_TO < level.getGameTime() % ROUNDING_TO) {
            finalAmountOfAirToTake++;
        }

        if (finalAmountOfAirToTake > 0) {
            int newAirSupply = entity.getAirSupply() - finalAmountOfAirToTake;
            if (newAirSupply <= 0) {
                entity.hurt(entity.damageSources().drown(), 2.0f);
            } else {
                entity.setAirSupply(entity.getAirSupply() - finalAmountOfAirToTake);

            }
        }

        return true;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return true;
    }
}
