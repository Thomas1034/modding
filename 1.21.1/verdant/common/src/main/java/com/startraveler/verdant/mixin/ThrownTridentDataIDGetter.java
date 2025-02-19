package com.startraveler.verdant.mixin;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.world.entity.projectile.ThrownTrident;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ThrownTrident.class)
public interface ThrownTridentDataIDGetter {
    @Accessor("ID_LOYALTY")
    static EntityDataAccessor<Byte> getLoyaltyID() {
        throw new AssertionError();
    }

    @Accessor("ID_FOIL")
    static EntityDataAccessor<Boolean> getFoilID() {
        throw new AssertionError();
    }
}
