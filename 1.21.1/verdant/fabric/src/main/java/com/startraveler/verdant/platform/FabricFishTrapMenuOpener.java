package com.startraveler.verdant.platform;

import com.startraveler.verdant.block.custom.entity.FishTrapBlockEntity;
import com.startraveler.verdant.menu.FishTrapMenu;
import com.startraveler.verdant.platform.services.IFishTrapMenuOpener;
import net.fabricmc.fabric.api.menu.v1.ExtendedMenuProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class FabricFishTrapMenuOpener implements IFishTrapMenuOpener {


    @Override
    public void openMenu(ServerPlayer player, MenuProvider provider, FishTrapBlockEntity blockEntity) {
        ExtendedMenuProvider<FishTrapMenu.SyncedFishTrapMenuData> factory = new ExtendedMenuProvider<>() {
            @Override
            public @Nullable AbstractContainerMenu createMenu(int containerId, @NotNull Inventory inventory, @NotNull Player player) {
                return provider.createMenu(containerId, inventory, player);
            }

            @Override
            public @NotNull Component getDisplayName() {
                return provider.getDisplayName();
            }

            @Override
            public FishTrapMenu.@NotNull SyncedFishTrapMenuData getScreenOpeningData(@NotNull ServerPlayer player) {
                return new FishTrapMenu.SyncedFishTrapMenuData(
                        blockEntity.getBlockPos(),
                        blockEntity.getNumBaitSlots(),
                        blockEntity.getNumOutputSlots());
            }
        };
        player.openMenu(factory);
    }
}
