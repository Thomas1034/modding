package com.startraveler.verdant.client.item;

import com.mojang.serialization.MapCodec;
import com.startraveler.verdant.item.component.RopeCoilData;
import com.startraveler.verdant.registry.DataComponentRegistry;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.properties.numeric.RangeSelectItemModelProperty;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public record RopeLengthProperty() implements RangeSelectItemModelProperty {

    public static final MapCodec<RopeLengthProperty> MAP_CODEC = MapCodec.unit(new RopeLengthProperty());

    @Override
    public float get(ItemStack stack, ClientLevel level, LivingEntity entity, int seed) {
        RopeCoilData d = stack.get(DataComponentRegistry.ROPE_COIL.get());
        return d == null ? 0 : ((float) d.length() / RopeCoilData.MAX_LENGTH_FROM_CRAFTING);
    }

    @Override
    public MapCodec<RopeLengthProperty> type() {
        return MAP_CODEC;
    }
}
