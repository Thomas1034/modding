package com.thomas.verdant.data;

import com.thomas.verdant.Constants;
import com.thomas.verdant.registry.EntityTypeRegistry;
import com.thomas.verdant.util.VerdantTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.entity.EntityType;

import java.util.concurrent.CompletableFuture;

public class VerdantEntityTypeTagProvider extends EntityTypeTagsProvider {
    public VerdantEntityTypeTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, Constants.MOD_ID);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {

        this.tag(EntityTypeTags.IMPACT_PROJECTILES).add(EntityTypeRegistry.THROWN_ROPE.get());
        this.tag(EntityTypeTags.ARROWS).add(EntityTypeRegistry.POISON_ARROW.get());
        this.tag(EntityTypeTags.ARTHROPOD).add(EntityTypeRegistry.TIMBERMITE.get());
        this.tag(VerdantTags.EntityTypes.VERDANT_FRIENDLY_ENTITIES)
                .add(EntityTypeRegistry.TIMBERMITE.get(), EntityTypeRegistry.ROOTED.get(), EntityType.BOGGED);
        this.tag(VerdantTags.EntityTypes.VERDANT_FRIENDLY_ENTITIES).addTag(EntityTypeTags.IMPACT_PROJECTILES);

    }
}
