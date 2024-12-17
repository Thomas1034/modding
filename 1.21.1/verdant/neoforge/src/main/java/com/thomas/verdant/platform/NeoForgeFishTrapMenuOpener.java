package com.thomas.verdant.platform;

import com.thomas.verdant.block.custom.entity.FishTrapBlockEntity;
import com.thomas.verdant.menu.FishTrapMenu;
import com.thomas.verdant.platform.services.IFishTrapMenuOpener;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;

public class NeoForgeFishTrapMenuOpener implements IFishTrapMenuOpener {
    @Override
    public void openMenu(ServerPlayer player, MenuProvider provider, FishTrapBlockEntity blockEntity) {
        FishTrapMenu.SyncedFishTrapMenuData data = new FishTrapMenu.SyncedFishTrapMenuData(
                blockEntity.getBlockPos(),
                blockEntity.getNumBaitSlots(),
                blockEntity.getNumOutputSlots());
        player.openMenu(provider, data::to);
    }
}
