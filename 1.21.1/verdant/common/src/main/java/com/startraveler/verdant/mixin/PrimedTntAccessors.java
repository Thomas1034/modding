package com.startraveler.verdant.mixin;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.level.ExplosionDamageCalculator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(PrimedTnt.class)
public interface PrimedTntAccessors {

    @Accessor("USED_PORTAL_DAMAGE_CALCULATOR")
    static ExplosionDamageCalculator getUsedPortalDamageCalculator() {
        throw new AssertionError();
    }

    @Accessor("owner")
    void setOwner(LivingEntity entity);

    @Accessor("usedPortal")
    boolean getUsedPortal();

    @Accessor("explosionPower")
    float getExplosionPower();

    @Accessor("explosionPower")
    void setExplosionPower(float power);
}
