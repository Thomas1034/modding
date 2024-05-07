package com.thomas.verdant.util;

import java.util.HashMap;
import java.util.UUID;

import com.thomas.verdant.network.ModPacketHandler;
import com.thomas.verdant.network.PlayerAddVelocityPacket;
import com.thomas.verdant.network.PlayerSetVelocityPacket;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

public class MotionHelper {
	// Stores when the maps were last updated.
	private static final HashMap<UUID, Long> LAST_UPDATE = new HashMap<>();
	// Stores the old position.
	private static final HashMap<UUID, Vec3> OLD_POS = new HashMap<>();
	// Stores the current saved velocities.
	private static final HashMap<UUID, Vec3> VELOCITIES = new HashMap<>();

	// Gets the velocity of the entity.
	public static Vec3 getVelocity(Entity entity) {
		// Two scenarios: server player or not.
		if (entity instanceof ServerPlayer serverPlayer) {
			update(serverPlayer);
			return VELOCITIES.get(serverPlayer.getUUID());
		} else {
			return entity.getDeltaMovement();
		}
	}

	// Sets the velocity of the entity.
	public static void setVelocity(Entity entity, Vec3 velocity) {
		// Two scenarios: server player or not.
		if (entity instanceof ServerPlayer serverPlayer) {
			ModPacketHandler.sendToPlayer(new PlayerSetVelocityPacket(velocity), serverPlayer);
		} else {
			entity.setDeltaMovement(velocity);
		}
	}

	// Adds to the velocity of the entity.
	public static void addVelocity(Entity entity, Vec3 velocity) {
		// Two scenarios: server player or not.
		if (entity instanceof ServerPlayer serverPlayer) {
			ModPacketHandler.sendToPlayer(new PlayerAddVelocityPacket(velocity), serverPlayer);
		} else {
			entity.addDeltaMovement(velocity);
		}
	}

	public static void update(ServerPlayer serverPlayer) {
		UUID id = serverPlayer.getUUID();
		Vec3 oldPos = OLD_POS.get(id);
		Long oldTime = LAST_UPDATE.get(id);
		Vec3 newPos = serverPlayer.position();
		Long newTime = serverPlayer.serverLevel().getGameTime();
		Vec3 oldVel = VELOCITIES.get(id);
		Vec3 newVel = null;

		// System.out.println("==================================");
		// Set old velocity to zero if it's null.
		if (oldVel == null) {
			// System.out.println("Error: old value for velocity is null, setting it to
			// 0.");
			oldVel = new Vec3(0, 0, 0);
		}
		// Set old time to the current time if it's null.
		if (oldTime == null) {
			// System.out.println("Error: old value for time is null, setting to current
			// time.");
			oldTime = newTime;
		}
		if (oldPos != null && (oldTime.longValue() != newTime.longValue())) {
			newVel = newPos.subtract(oldPos).scale(1 / (newTime - oldTime));
		} else {
			// System.out.println("Error: retaining old value for velocity.");
			newVel = oldVel;
		}

		// System.out.println("Last position: " + oldPos);
		// System.out.println("Last update: " + oldTime);
		// System.out.println("Current position: " + newPos);
		// System.out.println("Current update: " + newTime);
		// System.out.println("Old velocity: " + oldVel);
		// System.out.println("Current velocity: " + newVel);
		// System.out.println("==================================");

		OLD_POS.put(id, newPos);
		LAST_UPDATE.put(id, newTime);
		VELOCITIES.put(id, newVel);

	}

}
