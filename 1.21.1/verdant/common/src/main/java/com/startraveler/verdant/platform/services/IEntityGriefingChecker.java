package com.startraveler.verdant.platform.services;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;

public interface IEntityGriefingChecker {
    boolean canEntityGrief(ServerLevel serverLevel, Entity entity);
}
