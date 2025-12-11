package com.startraveler.verdant.data;

import com.startraveler.verdant.Constants;
import com.startraveler.verdant.registry.DamageSourceRegistry;
import com.startraveler.verdant.util.VerdantTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.DamageTypeTagsProvider;

import java.util.concurrent.CompletableFuture;

public class VerdantDamageSourceTagProvider extends DamageTypeTagsProvider {
    public VerdantDamageSourceTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, Constants.MOD_ID);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.tag(VerdantTags.DamageSources.TOXIC_ASH).addOptional(
                DamageSourceRegistry.TOXIC_ASH
        );
    }
}
