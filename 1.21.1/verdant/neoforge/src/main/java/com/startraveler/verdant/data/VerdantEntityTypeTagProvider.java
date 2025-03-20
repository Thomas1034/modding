package com.startraveler.verdant.data;

import com.startraveler.verdant.Constants;
import com.startraveler.verdant.registry.EntityTypeRegistry;
import com.startraveler.verdant.util.VerdantTags;
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
        this.tag(EntityTypeTags.UNDEAD).add(EntityTypeRegistry.ROOTED.get());
        this.tag(VerdantTags.EntityTypes.VERDANT_FRIENDLY_ENTITIES).add(
                EntityTypeRegistry.TIMBERMITE.get(),
                EntityTypeRegistry.ROOTED.get(),
                EntityTypeRegistry.POISONER.get(),
                EntityType.BOGGED,
                EntityType.CREEPER,
                EntityType.SPIDER,
                EntityType.CAVE_SPIDER,
                EntityType.CREAKING
        );
        this.tag(VerdantTags.EntityTypes.VERDANT_FRIENDLY_ENTITIES).addTag(EntityTypeTags.IMPACT_PROJECTILES);
        this.tag(VerdantTags.EntityTypes.TOXIC_ASH_DAMAGES).add(
                EntityTypeRegistry.TIMBERMITE.get(),
                EntityTypeRegistry.ROOTED.get(),
                EntityType.BOGGED,
                EntityTypeRegistry.POISONER.get(),
                EntityType.CREAKING,
                EntityType.CREEPER
        );
        this.tag(VerdantTags.EntityTypes.TOXIC_ASH_DAMAGES).addTag(EntityTypeTags.AQUATIC);

    }
}
