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
import com.startraveler.verdant.registration.RegistrationProvider;
import com.startraveler.verdant.registration.RegistryObject;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.alchemy.Potion;

public class PotionRegistry {

    public static final RegistrationProvider<Potion> POTIONS = RegistrationProvider.get(
            Registries.POTION,
            Constants.MOD_ID
    );

    public static final RegistryObject<Potion, Potion> FOOD_POISONING = POTIONS.register(
            "food_poisoning",
            () -> new Potion(
                    "food_poisoning",
                    new MobEffectInstance(MobEffectRegistry.FOOD_POISONING.asHolder(), 3600, 0)
            )
    );
    public static final RegistryObject<Potion, Potion> LONG_FOOD_POISONING = POTIONS.register(
            "long_food_poisoning",
            () -> new Potion(
                    "long_food_poisoning",
                    new MobEffectInstance(MobEffectRegistry.FOOD_POISONING.asHolder(), 9600, 0)
            )
    );
    public static final RegistryObject<Potion, Potion> STRONG_FOOD_POISONING = POTIONS.register(
            "strong_food_poisoning", () -> new Potion(
                    "strong_food_poisoning",
                    new MobEffectInstance(MobEffectRegistry.FOOD_POISONING.asHolder(), 1800, 1)
            )
    );

    public static final RegistryObject<Potion, Potion> ASPHYXIATING = POTIONS.register(
            "asphyxiating",
            () -> new Potion("asphyxiating", new MobEffectInstance(MobEffectRegistry.ASPHYXIATING.asHolder(), 3600, 0))
    );
    public static final RegistryObject<Potion, Potion> LONG_ASPHYXIATING = POTIONS.register(
            "long_asphyxiating",
            () -> new Potion(
                    "long_asphyxiating",
                    new MobEffectInstance(MobEffectRegistry.ASPHYXIATING.asHolder(), 9600, 0)
            )
    );
    public static final RegistryObject<Potion, Potion> STRONG_ASPHYXIATING = POTIONS.register(
            "strong_asphyxiating",
            () -> new Potion(
                    "strong_asphyxiating",
                    new MobEffectInstance(MobEffectRegistry.ASPHYXIATING.asHolder(), 1800, 1)
            )
    );

    public static final RegistryObject<Potion, Potion> CAFFEINE = POTIONS.register(
            "caffeine",
            () -> new Potion("caffeine", new MobEffectInstance(MobEffectRegistry.CAFFEINATED.asHolder(), 3600, 0))
    );
    public static final RegistryObject<Potion, Potion> LONG_CAFFEINE = POTIONS.register(
            "long_caffeine",
            () -> new Potion("long_caffeine", new MobEffectInstance(MobEffectRegistry.CAFFEINATED.asHolder(), 9600, 0))
    );
    public static final RegistryObject<Potion, Potion> STRONG_CAFFEINE = POTIONS.register(
            "strong_caffeine",
            () -> new Potion(
                    "strong_caffeine",
                    new MobEffectInstance(MobEffectRegistry.CAFFEINATED.asHolder(), 1800, 1)
            )
    );
    public static final RegistryObject<Potion, Potion> LONG_STRONG_CAFFEINE = POTIONS.register(
            "long_strong_caffeine",
            () -> new Potion(
                    "long_strong_caffeine",
                    new MobEffectInstance(MobEffectRegistry.CAFFEINATED.asHolder(), 3600, 1)
            )
    );

    public static final RegistryObject<Potion, Potion> COLLOID = POTIONS.register(
            "colloid",
            () -> new Potion("colloid", new MobEffectInstance(MobEffectRegistry.COLLOID.asHolder(), 3600, 0))
    );
    public static final RegistryObject<Potion, Potion> LONG_COLLOID = POTIONS.register(
            "long_colloid",
            () -> new Potion("long_colloid", new MobEffectInstance(MobEffectRegistry.COLLOID.asHolder(), 9600, 0))
    );
    public static final RegistryObject<Potion, Potion> STRONG_COLLOID = POTIONS.register(
            "strong_colloid",
            () -> new Potion("strong_colloid", new MobEffectInstance(MobEffectRegistry.COLLOID.asHolder(), 1800, 1))
    );

    public static final RegistryObject<Potion, Potion> ADRENALINE = POTIONS.register(
            "adrenaline",
            () -> new Potion("adrenaline", new MobEffectInstance(MobEffectRegistry.ADRENALINE.asHolder(), 3600, 0))
    );
    public static final RegistryObject<Potion, Potion> LONG_ADRENALINE = POTIONS.register(
            "long_adrenaline",
            () -> new Potion("long_adrenaline", new MobEffectInstance(MobEffectRegistry.ADRENALINE.asHolder(), 9600, 0))
    );
    public static final RegistryObject<Potion, Potion> STRONG_ADRENALINE = POTIONS.register(
            "strong_adrenaline",
            () -> new Potion(
                    "strong_adrenaline",
                    new MobEffectInstance(MobEffectRegistry.ADRENALINE.asHolder(), 1800, 1)
            )
    );

    public static final RegistryObject<Potion, Potion> ANTIDOTE = POTIONS.register(
            "antidote",
            () -> new Potion("antidote", new MobEffectInstance(MobEffectRegistry.ANTIDOTE.asHolder(), 1800, 0))
    );
    public static final RegistryObject<Potion, Potion> LONG_ANTIDOTE = POTIONS.register(
            "long_antidote",
            () -> new Potion("long_antidote", new MobEffectInstance(MobEffectRegistry.ANTIDOTE.asHolder(), 4800, 0))
    );
    public static final RegistryObject<Potion, Potion> STRONG_ANTIDOTE = POTIONS.register(
            "strong_antidote",
            () -> new Potion("strong_antidote", new MobEffectInstance(MobEffectRegistry.ANTIDOTE.asHolder(), 900, 1))
    );

    public static final RegistryObject<Potion, Potion> STENCH = POTIONS.register(
            "stench",
            () -> new Potion("stench", new MobEffectInstance(MobEffectRegistry.STENCH.asHolder(), 3600, 0))
    );
    public static final RegistryObject<Potion, Potion> LONG_STENCH = POTIONS.register(
            "long_stench",
            () -> new Potion("long_stench", new MobEffectInstance(MobEffectRegistry.STENCH.asHolder(), 9600, 0))
    );
    public static final RegistryObject<Potion, Potion> STRONG_STENCH = POTIONS.register(
            "strong_stench",
            () -> new Potion("strong_stench", new MobEffectInstance(MobEffectRegistry.STENCH.asHolder(), 1800, 1))
    );

    public static final RegistryObject<Potion, Potion> BLURRED = POTIONS.register(
            "blurred",
            () -> new Potion("blurred", new MobEffectInstance(MobEffectRegistry.BLURRING.asHolder(), 3600, 0))
    );
    public static final RegistryObject<Potion, Potion> LONG_BLURRED = POTIONS.register(
            "long_blurred",
            () -> new Potion("long_blurred", new MobEffectInstance(MobEffectRegistry.BLURRING.asHolder(), 9600, 0))
    );
    public static final RegistryObject<Potion, Potion> STRONG_BLURRED = POTIONS.register(
            "strong_blurred",
            () -> new Potion("strong_blurred", new MobEffectInstance(MobEffectRegistry.BLURRING.asHolder(), 1800, 1))
    );

    public static final RegistryObject<Potion, Potion> COLORBLIND = POTIONS.register(
            "colorblind",
            () -> new Potion("colorblind", new MobEffectInstance(MobEffectRegistry.RED_GREEN.asHolder(), 3600, 0))
    );
    public static final RegistryObject<Potion, Potion> LONG_COLORBLIND = POTIONS.register(
            "long_colorblind",
            () -> new Potion("long_colorblind", new MobEffectInstance(MobEffectRegistry.RED_GREEN.asHolder(), 9600, 0))
    );

    public static final RegistryObject<Potion, Potion> FADING = POTIONS.register(
            "fading",
            () -> new Potion("fading", new MobEffectInstance(MobEffectRegistry.SEPIA.asHolder(), 3600, 0))
    );
    public static final RegistryObject<Potion, Potion> LONG_FADING = POTIONS.register(
            "long_fading",
            () -> new Potion("long_fading", new MobEffectInstance(MobEffectRegistry.SEPIA.asHolder(), 9600, 0))
    );

    public static final RegistryObject<Potion, Potion> CLUMSINESS = POTIONS.register(
            "clumsiness",
            () -> new Potion("clumsiness", new MobEffectInstance(MobEffectRegistry.CLUMSINESS.asHolder(), 3600, 0))
    );
    public static final RegistryObject<Potion, Potion> LONG_CLUMSINESS = POTIONS.register(
            "long_clumsiness",
            () -> new Potion("long_clumsiness", new MobEffectInstance(MobEffectRegistry.CLUMSINESS.asHolder(), 9600, 0))
    );
    public static final RegistryObject<Potion, Potion> STRONG_CLUMSINESS = POTIONS.register(
            "strong_clumsiness",
            () -> new Potion(
                    "strong_clumsiness",
                    new MobEffectInstance(MobEffectRegistry.CLUMSINESS.asHolder(), 1800, 1)
            )
    );

    public static final RegistryObject<Potion, Potion> BROKEN_ARMOR = POTIONS.register(
            "broken_armor",
            () -> new Potion("broken_armor", new MobEffectInstance(MobEffectRegistry.BROKEN_ARMOR.asHolder(), 3600, 0))
    );
    public static final RegistryObject<Potion, Potion> LONG_BROKEN_ARMOR = POTIONS.register(
            "long_broken_armor",
            () -> new Potion(
                    "long_broken_armor",
                    new MobEffectInstance(MobEffectRegistry.BROKEN_ARMOR.asHolder(), 9600, 0)
            )
    );
    public static final RegistryObject<Potion, Potion> STRONG_BROKEN_ARMOR = POTIONS.register(
            "strong_broken_armor",
            () -> new Potion(
                    "strong_broken_armor",
                    new MobEffectInstance(MobEffectRegistry.BROKEN_ARMOR.asHolder(), 1800, 1)
            )
    );

    public static final RegistryObject<Potion, Potion> UNBREAKABLE = POTIONS.register(
            "unbreakable",
            () -> new Potion("unbreakable", new MobEffectInstance(MobEffectRegistry.UNBREAKABLE.asHolder(), 3600, 0))
    );
    public static final RegistryObject<Potion, Potion> LONG_UNBREAKABLE = POTIONS.register(
            "long_unbreakable",
            () -> new Potion(
                    "long_unbreakable",
                    new MobEffectInstance(MobEffectRegistry.UNBREAKABLE.asHolder(), 9600, 0)
            )
    );


    public static final RegistryObject<Potion, Potion> INNER_FIRE = POTIONS.register(
            "inner_fire",
            () -> new Potion("inner_fire", new MobEffectInstance(MobEffectRegistry.INNER_FIRE.asHolder(), 1800, 0))
    );
    public static final RegistryObject<Potion, Potion> LONG_INNER_FIRE = POTIONS.register(
            "long_inner_fire",
            () -> new Potion("long_inner_fire", new MobEffectInstance(MobEffectRegistry.INNER_FIRE.asHolder(), 4800, 0))
    );
    public static final RegistryObject<Potion, Potion> STRONG_INNER_FIRE = POTIONS.register(
            "strong_inner_fire",
            () -> new Potion(
                    "strong_inner_fire",
                    new MobEffectInstance(MobEffectRegistry.INNER_FIRE.asHolder(), 900, 1)
            )
    );

    public static final RegistryObject<Potion, Potion> FLICKERING = POTIONS.register(
            "flickering",
            () -> new Potion("flickering", new MobEffectInstance(MobEffectRegistry.FLICKERING.asHolder(), 3600, 0))
    );
    public static final RegistryObject<Potion, Potion> LONG_FLICKERING = POTIONS.register(
            "long_flickering",
            () -> new Potion("long_flickering", new MobEffectInstance(MobEffectRegistry.FLICKERING.asHolder(), 9600, 0))
    );
    public static final RegistryObject<Potion, Potion> STRONG_FLICKERING = POTIONS.register(
            "strong_flickering",
            () -> new Potion(
                    "strong_flickering",
                    new MobEffectInstance(MobEffectRegistry.FLICKERING.asHolder(), 1800, 1)
            )
    );

    public static final RegistryObject<Potion, Potion> RECALL = POTIONS.register(
            "recall",
            () -> new Potion("recall", new MobEffectInstance(MobEffectRegistry.RECALL.asHolder(), 60, 0))
    );


    public static final RegistryObject<Potion, Potion> PHOTOSENSITIVITY = POTIONS.register(
            "photosensitivity",
            () -> new Potion("photosensitivity", new MobEffectInstance(MobEffectRegistry.PHOTOSENSITIVITY.asHolder(), 3600, 0))
    );
    public static final RegistryObject<Potion, Potion> LONG_PHOTOSENSITIVITY = POTIONS.register(
            "long_photosensitivity",
            () -> new Potion(
                    "long_photosensitivity",
                    new MobEffectInstance(MobEffectRegistry.PHOTOSENSITIVITY.asHolder(), 9600, 0)
            )
    );
    public static final RegistryObject<Potion, Potion> STRONG_PHOTOSENSITIVITY = POTIONS.register(
            "strong_photosensitivity",
            () -> new Potion(
                    "strong_photosensitivity",
                    new MobEffectInstance(MobEffectRegistry.PHOTOSENSITIVITY.asHolder(), 1800, 1)
            )
    );

    public static void init() {
    }

}

