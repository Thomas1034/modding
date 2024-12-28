package com.thomas.verdant.data;

import com.thomas.verdant.Constants;
import com.thomas.verdant.util.VerdantTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.effect.MobEffects;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class VerdantMobEffectTagProvider extends MobEffectTagProvider {
    public VerdantMobEffectTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, Constants.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.tag(VerdantTags.MobEffects.AIRLESS_BREATHING)
                .add(MobEffects.CONDUIT_POWER.value(), MobEffects.WATER_BREATHING.value());
    }
}
