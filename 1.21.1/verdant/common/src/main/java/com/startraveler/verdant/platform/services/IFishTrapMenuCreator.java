package com.startraveler.verdant.platform.services;

import com.startraveler.verdant.menu.FishTrapMenu;
import net.minecraft.world.inventory.MenuType;

public interface IFishTrapMenuCreator {

    MenuType<FishTrapMenu> createMenuType();
}
