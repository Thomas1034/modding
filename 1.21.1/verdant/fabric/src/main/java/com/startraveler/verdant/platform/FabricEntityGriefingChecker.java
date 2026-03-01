package com.startraveler.verdant.platform;

import com.startraveler.verdant.platform.services.IEntityGriefingChecker;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.gamerules.GameRules;

public class FabricEntityGriefingChecker implements IEntityGriefingChecker {

    @Override
    public boolean canEntityGrief(ServerLevel serverLevel, Entity entity) {
        return serverLevel.getGameRules().get(GameRules.MOB_GRIEFING);
    }
}
