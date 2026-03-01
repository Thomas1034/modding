package com.startraveler.verdant.platform;

import com.startraveler.verdant.platform.services.IEntityGriefingChecker;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.neoforged.neoforge.event.EventHooks;

public class NeoForgeEntityGriefingChecker implements IEntityGriefingChecker {
    @Override
    public boolean canEntityGrief(ServerLevel serverLevel, Entity entity) {
        return EventHooks.canEntityGrief(serverLevel, entity);
    }
}
