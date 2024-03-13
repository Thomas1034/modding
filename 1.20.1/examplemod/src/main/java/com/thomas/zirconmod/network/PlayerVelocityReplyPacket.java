package com.thomas.zirconmod.network;

import java.util.function.Supplier;

import com.thomas.zirconmod.util.MotionHelper;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkEvent;

public class PlayerVelocityReplyPacket {

	private double dx;
	private double dy;
	private double dz;

	public PlayerVelocityReplyPacket(double dx, double dy, double dz) {
		this.dx = dx;
		this.dy = dy;
		this.dz = dz;
	}

	public PlayerVelocityReplyPacket(Vec3 vel) {
		this.dx = vel.x;
		this.dy = vel.y;
		this.dz = vel.z;
	}

	// Encodes the message onto the buffer.
	public static void encode(PlayerVelocityReplyPacket message, FriendlyByteBuf buffer) {
		// Three pieces of information need to be sent.
		buffer.writeDouble(message.getDx());
		buffer.writeDouble(message.getDy());
		buffer.writeDouble(message.getDz());
	}

	public static PlayerVelocityReplyPacket decode(FriendlyByteBuf buffer) {
		// Read off that one piece of information.
		return new PlayerVelocityReplyPacket(buffer.readDouble(), buffer.readDouble(), buffer.readDouble());
	}

	public static void handle(PlayerVelocityReplyPacket msg, Supplier<NetworkEvent.Context> ctx) {
		ctx.get().enqueueWork(() -> {
			// Give the sender flight exhaustion.

			// Work that needs to be thread-safe (most work)
			ServerPlayer player = ctx.get().getSender(); // the client that sent this packet
			
			// Update that player's information.
			MotionHelper.setVelocity(player, msg.getVec3());

		});
		ctx.get().setPacketHandled(true);
	}

	private Vec3 getVec3() {
		return new Vec3(this.dx, this.dy, this.dz);
	}

	public double getDx() {
		return dx;
	}

	public double getDy() {
		return dy;
	}

	public double getDz() {
		return dz;
	}

}
