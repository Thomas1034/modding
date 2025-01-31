package com.startraveler.verdant.platform;

import com.startraveler.verdant.block.custom.entity.FishTrapBlockEntity;
import com.startraveler.verdant.menu.FishTrapMenu;
import com.startraveler.verdant.platform.services.IFishTrapMenuOpener;
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
