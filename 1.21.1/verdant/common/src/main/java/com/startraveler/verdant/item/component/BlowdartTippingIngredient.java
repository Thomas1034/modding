package com.startraveler.verdant.item.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.effect.MobEffectInstance;

import java.util.List;

public record BlowdartTippingIngredient(List<MobEffectInstance> effects) {

    public static final Codec<BlowdartTippingIngredient> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                    MobEffectInstance.CODEC.listOf().fieldOf("effects").forGetter(BlowdartTippingIngredient::effects))
            .apply(instance, BlowdartTippingIngredient::new));

}
