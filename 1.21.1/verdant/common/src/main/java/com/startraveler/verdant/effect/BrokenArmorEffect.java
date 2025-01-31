package com.startraveler.verdant.effect;

import com.startraveler.verdant.Constants;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class BrokenArmorEffect extends MobEffect {
    public BrokenArmorEffect(MobEffectCategory category, float percentage, int color) {
        super(category, color);

        this.addAttributeModifier(
                Attributes.ARMOR,
                ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "broken_armor/decrease_armor"),
                -percentage,
                AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
        );
    }
    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return true;
    }
}