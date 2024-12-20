package com.thomas.verdant.registry;

import com.thomas.verdant.Constants;
import com.thomas.verdant.block.custom.entity.FishTrapBlockEntity;
import com.thomas.verdant.registration.RegistrationProvider;
import com.thomas.verdant.registration.RegistryObject;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.Set;

public class BlockEntityTypeRegistry {

    public static final RegistrationProvider<BlockEntityType<?>> BLOCK_ENTITIES = RegistrationProvider.get(
            Registries.BLOCK_ENTITY_TYPE,
            Constants.MOD_ID);

    public static void init() {
    }    public static final RegistryObject<BlockEntityType<?>, BlockEntityType<FishTrapBlockEntity>> FISH_TRAP_BLOCK_ENTITY = BLOCK_ENTITIES.register(
            "fish_trap",
            () -> new BlockEntityType<>(
                    FishTrapBlockEntity::new,
                    Set.of(BlockRegistry.FISH_TRAP_BLOCK.get())));


}
