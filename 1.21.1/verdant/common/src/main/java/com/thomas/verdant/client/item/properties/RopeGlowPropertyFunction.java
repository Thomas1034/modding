package com.thomas.verdant.client.item.properties;

import com.thomas.verdant.block.custom.RopeBlock;
import com.thomas.verdant.item.component.RopeCoilData;
import com.thomas.verdant.registry.DataComponentRegistry;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.ClampedItemPropertyFunction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class RopeGlowPropertyFunction implements ClampedItemPropertyFunction {
    @Override
    public float unclampedCall(ItemStack stack, @Nullable ClientLevel level, @Nullable LivingEntity entity, int seed) {
        RopeCoilData data = stack.get(DataComponentRegistry.ROPE_COIL.get());
        if (data == null) {
            return 0;
        }
        return ((float) data.lightLevel()) / ((float) RopeBlock.GLOW_MAX);
    }
}
