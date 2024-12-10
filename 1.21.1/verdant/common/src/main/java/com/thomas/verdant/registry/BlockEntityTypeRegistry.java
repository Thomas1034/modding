package com.thomas.verdant.registry;

import com.thomas.verdant.Constants;
import com.thomas.verdant.block.custom.entity.FishTrapBlockEntity;
import com.thomas.verdant.registration.RegistrationProvider;
import com.thomas.verdant.registration.RegistryObject;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class BlockEntityTypeRegistry {

    public static final RegistrationProvider<BlockEntityType<?>> BLOCK_ENTITIES = RegistrationProvider.get(Registries.BLOCK_ENTITY_TYPE, Constants.MOD_ID);

    public static final RegistryObject<BlockEntityType<FishTrapBlockEntity>, BlockEntityType<FishTrapBlockEntity>> FISH_TRAP_BLOCK_ENTITY = BLOCK_ENTITIES
            .register("fish_trap", () -> new BlockEntityType<>(FishTrapBlockEntity::new, BlockRegistry.FISH_TRAP_BLOCK.get()).build(null));

}
