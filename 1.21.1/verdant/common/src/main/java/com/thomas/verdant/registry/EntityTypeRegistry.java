package com.thomas.verdant.registry;

import com.thomas.verdant.Constants;
import com.thomas.verdant.registration.RegistrationProvider;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;

public class EntityTypeRegistry {

    public static final RegistrationProvider<EntityType<?>> ENTITY_TYPES = RegistrationProvider.get(Registries.ENTITY_TYPE, Constants.MOD_ID);

}
