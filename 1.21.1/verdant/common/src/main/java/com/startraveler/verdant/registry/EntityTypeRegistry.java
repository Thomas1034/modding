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
import com.startraveler.verdant.entity.custom.PoisonArrowEntity;
import com.startraveler.verdant.entity.custom.RootedEntity;
import com.startraveler.verdant.entity.custom.ThrownRopeEntity;
import com.startraveler.verdant.entity.custom.TimbermiteEntity;
import com.startraveler.verdant.registration.RegistrationProvider;
import com.startraveler.verdant.registration.RegistryObject;
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
    public static final RegistryObject<EntityType<?>, EntityType<RootedEntity>> ROOTED = ENTITY_TYPES.register(
            "rooted",
            () -> EntityType.Builder.<RootedEntity>of(RootedEntity::new, MobCategory.MONSTER)
                    .sized(0.6F, 1.95F)
                    .eyeHeight(1.74F)
                    .passengerAttachments(2.0125F)
                    .ridingOffset(-0.7F)
                    .clientTrackingRange(8)
                    .build(key("rooted"))
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

