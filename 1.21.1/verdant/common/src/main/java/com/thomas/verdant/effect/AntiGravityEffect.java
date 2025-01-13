package com.thomas.verdant.effect;

import com.thomas.verdant.Constants;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class AntiGravityEffect extends MobEffect {
    public AntiGravityEffect(MobEffectCategory category, int color) {
        super(category, color);
        this.addAttributeModifier(
                Attributes.GRAVITY,
                ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "antigravity/no_gravity"),
                -1.0,
                AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
        );
    }
    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return true;
    }
}