package com.startraveler.verdant.data;

import com.startraveler.verdant.Constants;
import com.startraveler.verdant.registry.MobEffectRegistry;
import com.startraveler.verdant.util.VerdantTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.effect.MobEffects;

import java.util.concurrent.CompletableFuture;

public class VerdantMobEffectTagProvider extends MobEffectTagProvider {
    public VerdantMobEffectTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, Constants.MOD_ID);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.tag(VerdantTags.MobEffects.AIRLESS_BREATHING)
                .add(MobEffects.CONDUIT_POWER.value(), MobEffects.WATER_BREATHING.value());
        this.tag(VerdantTags.MobEffects.WEAK_VERDANT_FRIENDLINESS).add(MobEffectRegistry.ANTIDOTE.get());
        this.tag(VerdantTags.MobEffects.VERDANT_FRIEND).add(MobEffectRegistry.VERDANT_ENERGY.get());
        this.tag(VerdantTags.MobEffects.UNBREAKABLE)
                .add(MobEffectRegistry.UNBREAKABLE.get());
    }
}
