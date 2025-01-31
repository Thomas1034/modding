package com.startraveler.verdant.platform;

import com.startraveler.verdant.block.custom.entity.FishTrapBlockEntity;
import com.startraveler.verdant.menu.FishTrapMenu;
import com.startraveler.verdant.platform.services.IFishTrapMenuOpener;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import org.jetbrains.annotations.Nullable;

public class FabricFishTrapMenuOpener implements IFishTrapMenuOpener {


    @Override
    public void openMenu(ServerPlayer player, MenuProvider provider, FishTrapBlockEntity blockEntity) {
        ExtendedScreenHandlerFactory<FishTrapMenu.SyncedFishTrapMenuData> factory = new ExtendedScreenHandlerFactory<>() {
            @Override
            public @Nullable AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
                return provider.createMenu(i, inventory, player);
            }

            @Override
            public Component getDisplayName() {
                return provider.getDisplayName();
            }

            @Override
            public FishTrapMenu.SyncedFishTrapMenuData getScreenOpeningData(ServerPlayer player) {
                // TODO
                return new FishTrapMenu.SyncedFishTrapMenuData(
                        blockEntity.getBlockPos(),
                        blockEntity.getNumBaitSlots(),
                        blockEntity.getNumOutputSlots());
            }
        };
        player.openMenu(factory);
    }
}
