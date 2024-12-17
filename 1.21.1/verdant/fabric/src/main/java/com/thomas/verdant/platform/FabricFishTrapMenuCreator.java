package com.thomas.verdant.platform;

import com.thomas.verdant.menu.FishTrapMenu;
import com.thomas.verdant.platform.services.IFishTrapMenuCreator;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.inventory.MenuType;

public class FabricFishTrapMenuCreator implements IFishTrapMenuCreator {

    public static final StreamCodec<RegistryFriendlyByteBuf, FishTrapMenu.SyncedFishTrapMenuData> PACKET_CODEC = StreamCodec.composite(
            ByteBufCodecs.fromCodec(BlockPos.CODEC),
            FishTrapMenu.SyncedFishTrapMenuData::pos,
            ByteBufCodecs.INT,
            FishTrapMenu.SyncedFishTrapMenuData::numBaitSlots,
            ByteBufCodecs.INT,
            FishTrapMenu.SyncedFishTrapMenuData::numOutputSlots,
            FishTrapMenu.SyncedFishTrapMenuData::new);

    @Override
    public MenuType<FishTrapMenu> createMenuType() {
        return new ExtendedScreenHandlerType<>(FishTrapMenu::new, PACKET_CODEC);
    }
}
