package com.thomas.zirconmod.util;

import java.util.HashMap;

import com.thomas.zirconmod.network.ModPacketHandler;
import com.thomas.zirconmod.network.PlayerVelocityRequestPacket;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.Vec3;

public class MotionHelper {

	private static final HashMap<ServerPlayer, Vec3> VELOCITIES = new HashMap<>();

	public static void setVelocity(ServerPlayer player, Vec3 vel) {
		VELOCITIES.put(player, vel);
	}
	
	public static Vec3 getVelocity(ServerPlayer player) {
		return VELOCITIES.get(player);
	}
	
	public static void update(ServerPlayer player) {
		ModPacketHandler.sendToPlayer(new PlayerVelocityRequestPacket(), player);
	}
	
}
