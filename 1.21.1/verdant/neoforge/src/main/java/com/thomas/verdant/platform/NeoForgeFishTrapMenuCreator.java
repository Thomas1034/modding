package com.thomas.verdant.platform;

import com.thomas.verdant.menu.FishTrapMenu;
import com.thomas.verdant.platform.services.IFishTrapMenuCreator;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;

public class NeoForgeFishTrapMenuCreator implements IFishTrapMenuCreator {
    @Override
    public MenuType<FishTrapMenu> createMenuType() {
        return IMenuTypeExtension.create(FishTrapMenu::new);
    }
}
