package com.thomas.verdant.registry;

import com.thomas.verdant.Constants;
import com.thomas.verdant.entity.custom.OvergrownZombieEntity;
import com.thomas.verdant.entity.custom.PoisonArrowEntity;
import com.thomas.verdant.entity.custom.ThrownRopeEntity;
import com.thomas.verdant.entity.custom.TimbermiteEntity;
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
    public static final RegistryObject<EntityType<?>, EntityType<TimbermiteEntity>> TIMBERMITE = ENTITY_TYPES.register(
            "timbermite",
            () -> EntityType.Builder.of(TimbermiteEntity::new, MobCategory.MONSTER)
                    .sized(0.4f, 0.3f)
                    .build(key("timbermite"))
    );
    public static final RegistryObject<EntityType<?>, EntityType<PoisonArrowEntity>> POISON_ARROW = ENTITY_TYPES.register(
            "poison_arrow",
            () -> EntityType.Builder.<PoisonArrowEntity>of(PoisonArrowEntity::new, MobCategory.MISC)
                    .noLootTable()
                    .sized(0.5F, 0.5F)
                    .eyeHeight(0.13F)
                    .clientTrackingRange(4)
                    .updateInterval(20)
                    .build(key("poison_arrow"))
    );
    public static final RegistryObject<EntityType<?>, EntityType<OvergrownZombieEntity>> OVERGROWN_ZOMBIE = ENTITY_TYPES.register(
            "overgrown_zombie",
            () -> EntityType.Builder.<OvergrownZombieEntity>of(OvergrownZombieEntity::new, MobCategory.MONSTER)
                    .sized(0.6F, 1.95F)
                    .eyeHeight(1.74F)
                    .passengerAttachments(2.0125F)
                    .ridingOffset(-0.7F)
                    .clientTrackingRange(8)
                    .build(key("overgrown_zombie"))
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
