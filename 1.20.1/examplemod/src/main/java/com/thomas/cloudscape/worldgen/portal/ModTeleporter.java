package com.thomas.cloudscape.worldgen.portal;

import java.util.function.Function;

import org.jetbrains.annotations.Nullable;

import com.thomas.cloudscape.network.ModPacketHandler;
import com.thomas.cloudscape.network.PlayerAddVelocityPacket;
import com.thomas.cloudscape.util.MotionHelper;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.portal.PortalInfo;
import net.minecraftforge.common.util.ITeleporter;

public class ModTeleporter implements ITeleporter {

	@Override
	public Entity placeEntity(Entity entity, ServerLevel currentWorld, ServerLevel destWorld, float yaw,
			Function<Boolean, Entity> repositionEntity) {
		
		Entity placed = repositionEntity.apply(false);
		
		if (entity instanceof ServerPlayer spOrig && placed instanceof ServerPlayer spPlaced) {
			// Update flying.
			if (spOrig.isFallFlying()) {
				spPlaced.startFallFlying();
			}
			// Update velocity.
			ModPacketHandler.sendToPlayer(new PlayerAddVelocityPacket(MotionHelper.getVelocity(spOrig)), spPlaced);
			
		}
		return placed;
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
