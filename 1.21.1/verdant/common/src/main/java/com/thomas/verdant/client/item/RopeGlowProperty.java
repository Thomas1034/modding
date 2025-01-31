package com.thomas.verdant.client.item;

import com.mojang.serialization.MapCodec;
import com.thomas.verdant.item.component.RopeCoilData;
import com.thomas.verdant.registry.DataComponentRegistry;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.properties.numeric.RangeSelectItemModelProperty;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public record RopeGlowProperty() implements RangeSelectItemModelProperty {

    public static final MapCodec<RopeGlowProperty> MAP_CODEC = MapCodec.unit(new RopeGlowProperty());

    @Override
    public float get(ItemStack stack, ClientLevel level, LivingEntity entity, int seed) {
        RopeCoilData d = stack.get(DataComponentRegistry.ROPE_COIL.get());
        return d == null ? 0 : ((float) d.lightLevel() / 4);
    }

    @Override
    public MapCodec<RopeGlowProperty> type() {
        return MAP_CODEC;
    }
}
