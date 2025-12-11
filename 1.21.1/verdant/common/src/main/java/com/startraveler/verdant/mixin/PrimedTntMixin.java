package com.startraveler.verdant.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.startraveler.verdant.util.PrimedTntMixinIndirection;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(PrimedTnt.class)
public abstract class PrimedTntMixin implements PrimedTntMixinIndirection {

    @Unique
    boolean verdant$startsFires = false;

    @WrapOperation(method = "explode", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;explode(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/damagesource/DamageSource;Lnet/minecraft/world/level/ExplosionDamageCalculator;DDDFZLnet/minecraft/world/level/Level$ExplosionInteraction;)V"))
    public void allowExplosionsToStartFires(Level instance, Entity source, DamageSource damageSource, ExplosionDamageCalculator damageCalculator, double x, double y, double z, float radius, boolean fire, Level.ExplosionInteraction explosionInteraction, Operation<Void> original) {
        original.call(
                instance,
                source,
                damageSource,
                damageCalculator,
                x,
                y,
                z,
                radius,
                fire || this.verdant$startsFires,
                explosionInteraction
        );
    }

    @Unique
    @Override
    public void verdant$setStartsFires() {
        this.verdant$startsFires = true;
    }
}
