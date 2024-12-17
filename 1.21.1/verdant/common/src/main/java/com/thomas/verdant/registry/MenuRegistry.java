package com.thomas.verdant.registry;

import com.thomas.verdant.Constants;
import com.thomas.verdant.menu.FishTrapMenu;
import com.thomas.verdant.platform.Services;
import com.thomas.verdant.registration.RegistrationProvider;
import com.thomas.verdant.registration.RegistryObject;
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
