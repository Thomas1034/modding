package com.startraveler.verdant.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.startraveler.verdant.entity.custom.OozeEntity;
import com.startraveler.verdant.registry.EntityTypeRegistry;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Slime.class)
public abstract class SlimeMixin extends Mob {

    private SlimeMixin(EntityType<? extends Mob> type, Level level) {
        super(type, level);
    }

    @WrapOperation(method = "dealDamage", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;hurtServer(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/damagesource/DamageSource;F)Z"))
    private boolean addEffectsToOozeAttacks(LivingEntity target, ServerLevel serverLevel, DamageSource damageSource, float amount, Operation<Boolean> original) {
        boolean originalResult = original.call(target, serverLevel, damageSource, amount);
        if (!target.isInvulnerable() && this.is(EntityTypeRegistry.OOZE.get())) {
            OozeEntity.applyHitPotionEffect(this, target);
        }
        return originalResult;
    }

    @WrapOperation(method = "remove", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/monster/Slime;convertTo(Lnet/minecraft/world/entity/EntityType;Lnet/minecraft/world/entity/ConversionParams;Lnet/minecraft/world/entity/EntitySpawnReason;Lnet/minecraft/world/entity/ConversionParams$AfterConversion;)Lnet/minecraft/world/entity/Mob;"))
    private Mob makeOozesPickUpFlowers(Slime instance, EntityType<?> entityType, ConversionParams conversionParams, EntitySpawnReason entitySpawnReason, ConversionParams.AfterConversion<?> afterConversion, Operation<Mob> original) {
        return original.call(
                instance, entityType, new ConversionParams(
                        conversionParams.type(),
                        conversionParams.keepEquipment(),
                        conversionParams.preserveCanPickUpLoot() || instance.is(EntityTypeRegistry.OOZE.asHolder()),
                        conversionParams.team()
                ), entitySpawnReason, afterConversion
        );
    }
}
