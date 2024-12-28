package com.thomas.verdant.data;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.IntrinsicHolderTagsProvider;
import net.minecraft.world.effect.MobEffect;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public abstract class MobEffectTagProvider extends IntrinsicHolderTagsProvider<MobEffect> {

    public MobEffectTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, String modId, @Nullable ExistingFileHelper existingFileHelper) {
        super(
                output,
                Registries.MOB_EFFECT,
                lookupProvider,
                mobEffect -> BuiltInRegistries.MOB_EFFECT.getResourceKey(mobEffect).orElseThrow(),
                modId,
                existingFileHelper
        );
    }

}
