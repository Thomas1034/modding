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
import com.startraveler.verdant.menu.FishTrapMenu;
import com.startraveler.verdant.platform.Services;
import com.startraveler.verdant.registration.RegistrationProvider;
import com.startraveler.verdant.registration.RegistryObject;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.MenuType;

public class MenuRegistry {
    public static final RegistrationProvider<MenuType<?>> MENUS = RegistrationProvider.get(
            Registries.MENU,
            Constants.MOD_ID);

    public static final RegistryObject<MenuType<?>, MenuType<FishTrapMenu>> FISH_TRAP_MENU = MENUS.register(
            "fish_trap",
            Services.FISH_TRAP_MENU_CREATOR::createMenuType);

    public static void init() {
    }
}

