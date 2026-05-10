/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * If you modify this file, please include a notice stating the changes:
 * Example: "Modified by [Your Name] on [Date] - [Short Description of Changes]"
 */
package com.startraveler.verdant.registry;

import com.startraveler.verdant.Constants;
import com.startraveler.verdant.block.custom.entity.FishTrapBlockEntity;
import com.startraveler.verdant.block.custom.entity.OozingHeartBlockEntity;
import com.startraveler.verdant.block.custom.entity.OvergrownSpawnerBlockEntity;
import com.startraveler.verdant.block.custom.entity.VerdantConduitBlockEntity;
import com.startraveler.verdant.registration.RegistrationProvider;
import com.startraveler.verdant.registration.RegistryObject;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class BlockEntityTypeRegistry {

    public static final RegistrationProvider<BlockEntityType<?>> BLOCK_ENTITIES = RegistrationProvider.get(
            Registries.BLOCK_ENTITY_TYPE,
            Constants.MOD_ID
    );

    public static void init() {
    }

    public static final RegistryObject<BlockEntityType<?>, BlockEntityType<@NotNull OvergrownSpawnerBlockEntity>> OVERGROWN_SPAWNER = BLOCK_ENTITIES.register(
            "overgrown_spawner",
            () -> new BlockEntityType<>(OvergrownSpawnerBlockEntity::new, Set.of(BlockRegistry.OVERGROWN_SPAWNER.get()))
    );


    public static final RegistryObject<BlockEntityType<?>, BlockEntityType<@NotNull FishTrapBlockEntity>> FISH_TRAP_BLOCK_ENTITY = BLOCK_ENTITIES.register(
            "fish_trap",
            () -> new BlockEntityType<>(FishTrapBlockEntity::new, Set.of(BlockRegistry.FISH_TRAP.get()))
    );

    public static final RegistryObject<BlockEntityType<?>, BlockEntityType<@NotNull VerdantConduitBlockEntity>> VERDANT_CONDUIT_BLOCK_ENTITY = BLOCK_ENTITIES.register(
            "verdant_conduit",
            () -> new BlockEntityType<>(VerdantConduitBlockEntity::new, Set.of(BlockRegistry.VERDANT_CONDUIT.get()))
    );

    public static final RegistryObject<BlockEntityType<?>, BlockEntityType<@NotNull OozingHeartBlockEntity>> OOZING_HEART_BLOCK_ENTITY = BLOCK_ENTITIES.register(
            "oozing_heart",
            () -> new BlockEntityType<>(OozingHeartBlockEntity::new, Set.of(BlockRegistry.OOZING_HEART.get()))
    );


}

