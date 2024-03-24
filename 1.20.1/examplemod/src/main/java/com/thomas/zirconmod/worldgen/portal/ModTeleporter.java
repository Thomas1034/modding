package com.thomas.zirconmod.worldgen.portal;

import java.util.function.Function;

import org.jetbrains.annotations.Nullable;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.portal.PortalInfo;
import net.minecraftforge.common.util.ITeleporter;

public class ModTeleporter implements ITeleporter {

	@Override
	public Entity placeEntity(Entity entity, ServerLevel currentWorld, ServerLevel destWorld, float yaw,
			Function<Boolean, Entity> repositionEntity) {
		return repositionEntity.apply(false);
	}

	@Override
	@Nullable
	public PortalInfo getPortalInfo(Entity entity, ServerLevel destWorld,
			Function<ServerLevel, PortalInfo> defaultPortalInfo) {
		return this.isVanilla() ? defaultPortalInfo.apply(destWorld)
				: new PortalInfo(entity.position(), entity.getDeltaMovement(), entity.getYRot(), entity.getXRot());
	}
	
	@Override
	public boolean playTeleportSound(ServerPlayer player, ServerLevel sourceWorld, ServerLevel destWorld)
    {
        return false;
    }

}
