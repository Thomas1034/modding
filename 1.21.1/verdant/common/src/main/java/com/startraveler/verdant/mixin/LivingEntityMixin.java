package com.startraveler.verdant.mixin;

import com.startraveler.verdant.util.VerdantTags;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {

    @Inject(method = "hurtServer*", at = @At(value = "HEAD"))
    private void addInherentThornsEffect(ServerLevel level, DamageSource source, float damage, CallbackInfoReturnable<Boolean> cir) {
        LivingEntity thisEntity = ((LivingEntity) (Object) this);
        if (!source.is(DamageTypeTags.AVOIDS_GUARDIAN_THORNS) && !source.is(DamageTypes.THORNS)) {
            float damageToDeal = 0;
            for (EquipmentSlot slot : EquipmentSlot.values()) {
                if (thisEntity.canUseSlot(slot)) {

                    ItemStack equipment = thisEntity.getItemBySlot(slot);

                    if (equipment.is(VerdantTags.Items.HAS_THORNS)) {
                        damageToDeal += 1.0f;
                    }
                }
            }

            Entity attacker = source.getDirectEntity();
            if (damageToDeal > 0 && attacker instanceof LivingEntity livingAttacker) {
                livingAttacker.hurtServer(level, thisEntity.damageSources().thorns(thisEntity), damageToDeal);
            }
        }
    }

}
