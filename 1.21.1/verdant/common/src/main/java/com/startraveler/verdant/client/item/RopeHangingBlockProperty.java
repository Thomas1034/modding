package com.startraveler.verdant.client.item;

import com.mojang.serialization.MapCodec;
import com.startraveler.verdant.item.component.RopeCoilData;
import com.startraveler.verdant.registry.DataComponentRegistry;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.properties.select.SelectItemModelProperty;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public record RopeHangingBlockProperty() implements SelectItemModelProperty<RopeCoilData.LanternOptions> {

    // The object to register that contains the relevant codecs
    public static final SelectItemModelProperty.Type<RopeHangingBlockProperty, RopeCoilData.LanternOptions> TYPE = SelectItemModelProperty.Type.create(
            // The map codec for this property
            MapCodec.unit(new RopeHangingBlockProperty()),
            // The codec for the object being selected
            // Used to serialize the case entries ("when": <property value>)
            RopeCoilData.LanternOptions.CODEC
    );

    @Override
    public RopeCoilData.LanternOptions get(ItemStack itemStack, ClientLevel clientLevel, LivingEntity livingEntity, int i, ItemDisplayContext itemDisplayContext) {
        RopeCoilData d = itemStack.get(DataComponentRegistry.ROPE_COIL.get());
        return d == null ? RopeCoilData.LanternOptions.NONE : d.lantern();
    }

    @Override
    public Type<? extends SelectItemModelProperty<RopeCoilData.LanternOptions>, RopeCoilData.LanternOptions> type() {
        return TYPE;
    }
}
