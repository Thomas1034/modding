package com.thomas.verdant.registry;

import com.thomas.verdant.Constants;
import com.thomas.verdant.entity.custom.ThrownRopeEntity;
import com.thomas.verdant.registration.RegistrationProvider;
import com.thomas.verdant.registration.RegistryObject;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public class EntityTypeRegistry {

    public static final RegistrationProvider<EntityType<?>> ENTITY_TYPES = RegistrationProvider.get(
            Registries.ENTITY_TYPE,
            Constants.MOD_ID
    );

    public static final RegistryObject<EntityType<?>, EntityType<ThrownRopeEntity>> THROWN_ROPE = ENTITY_TYPES.register(
            "thrown_rope",
            () -> EntityType.Builder.<ThrownRopeEntity>of(ThrownRopeEntity::new, MobCategory.MISC)
                    .sized(0.8f, 0.8f)
                    .build(key("thrown_rope"))
    );

    private static ResourceKey<EntityType<?>> key(String name) {
        return ResourceKey.create(
                Registries.ENTITY_TYPE,
                ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, name)
        );
    }

    public static void init() {

    }

}
