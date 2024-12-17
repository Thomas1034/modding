package com.thomas.verdant.platform.services;

import com.thomas.verdant.menu.FishTrapMenu;
import net.minecraft.world.inventory.MenuType;

public interface IFishTrapMenuCreator {

    MenuType<FishTrapMenu> createMenuType();
}
