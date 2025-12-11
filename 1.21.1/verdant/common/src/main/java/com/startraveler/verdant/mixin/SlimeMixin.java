package com.startraveler.verdant.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.startraveler.verdant.entity.custom.OozeEntity;
import com.startraveler.verdant.registry.EntityTypeRegistry;
import net.minecraft.core.HolderSet;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.monster.Slime;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Slime.class)
public class SlimeMixin {

    @WrapOperation(method = "dealDamage", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;hurtServer(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/damagesource/DamageSource;F)Z"))
    public boolean addEffectsToOozeAttacks(LivingEntity instance, ServerLevel projectile, DamageSource damageSource, float amount, Operation<Boolean> original) {
        boolean originalResult = original.call(instance, projectile, damageSource, amount);
        Slime slimeThis = (Slime) (Object) (this);
        if (!instance.isInvulnerable() && slimeThis instanceof OozeEntity oozeEntity) {
            oozeEntity.applyHitPotionEffect(instance);
        }
        return originalResult;
    }


    @WrapOperation(method = "remove", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/monster/Slime;convertTo(Lnet/minecraft/world/entity/EntityType;Lnet/minecraft/world/entity/ConversionParams;Lnet/minecraft/world/entity/EntitySpawnReason;Lnet/minecraft/world/entity/ConversionParams$AfterConversion;)Lnet/minecraft/world/entity/Mob;"))
    public Mob makeOozesPickUpFlowers(Slime instance, EntityType<?> entityType, ConversionParams conversionParams, EntitySpawnReason entitySpawnReason, ConversionParams.AfterConversion<?> afterConversion, Operation<Mob> original) {

        return original.call(
                instance, entityType, new ConversionParams(
                        conversionParams.type(),
                        conversionParams.keepEquipment(),
                        conversionParams.preserveCanPickUpLoot() || (entityType.is(HolderSet.direct(EntityTypeRegistry.OOZE.asHolder()))),
                        conversionParams.team()
                ), entitySpawnReason, afterConversion
        );
    }
}
