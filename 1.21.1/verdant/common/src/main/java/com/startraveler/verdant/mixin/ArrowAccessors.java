package com.startraveler.verdant.mixin;

import net.minecraft.world.entity.projectile.Arrow;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Arrow.class)
public interface ArrowAccessors {
    @Invoker("updateColor")
    void verdant$updateColor();
}
