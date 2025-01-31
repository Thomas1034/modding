package com.startraveler.verdant.client.item;

import com.mojang.serialization.MapCodec;
import com.startraveler.verdant.item.component.RopeCoilData;
import com.startraveler.verdant.registry.DataComponentRegistry;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.properties.conditional.ConditionalItemModelProperty;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public record RopeHookProperty() implements ConditionalItemModelProperty {

    public static final MapCodec<RopeHookProperty> MAP_CODEC = MapCodec.unit(new RopeHookProperty());

    @Override
    public boolean get(ItemStack itemStack, @Nullable ClientLevel clientLevel, @Nullable LivingEntity livingEntity, int i, ItemDisplayContext itemDisplayContext) {
        RopeCoilData d = itemStack.get(DataComponentRegistry.ROPE_COIL.get());
        return d != null && d.hasHook();
    }

    @Override
    public MapCodec<RopeHookProperty> type() {
        return MAP_CODEC;
    }
}
