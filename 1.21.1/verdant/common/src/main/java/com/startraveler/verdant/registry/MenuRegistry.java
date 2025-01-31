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
