package com.thomas.verdant.client.item;

import com.mojang.serialization.MapCodec;
import com.thomas.verdant.item.custom.RopeCoilItem;
import com.thomas.verdant.registry.DataComponentRegistry;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.properties.numeric.BundleFullness;
import net.minecraft.client.renderer.item.properties.numeric.RangeSelectItemModelProperty;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public record RopeLengthProperty() implements RangeSelectItemModelProperty {
    public static final MapCodec<BundleFullness> MAP_CODEC = MapCodec.unit(new BundleFullness());

    @Override
    public float get(ItemStack itemStack, @Nullable ClientLevel clientLevel, @Nullable LivingEntity livingEntity, int i) {
        return ((float) itemStack.getOrDefault(DataComponentRegistry.ROPE_COIL.get(), RopeCoilItem.DEFAULT_DATA_COMPONENT)
                .length());
    }

    @Override
    public MapCodec<? extends RangeSelectItemModelProperty> type() {
        return MAP_CODEC;
    }
}
