package com.thomas.verdant.platform.services;

import com.thomas.verdant.block.custom.entity.FishTrapBlockEntity;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;

public interface IFishTrapMenuOpener {

    public void openMenu(ServerPlayer player, MenuProvider provider, FishTrapBlockEntity blockEntity);
}
