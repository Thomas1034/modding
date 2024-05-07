package com.thomas.verdant.network;

import java.util.function.Supplier;

import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkEvent.Context;

public class PlayerAddVelocityPacket {

	private double dx;
	private double dy;
	private double dz;

	public PlayerAddVelocityPacket(double dx, double dy, double dz) {
		this.dx = dx;
		this.dy = dy;
		this.dz = dz;
	}
	
	public PlayerAddVelocityPacket(Vec3 vel) {
		this.dx = vel.x;
		this.dy = vel.y;
		this.dz = vel.z;
	}

	// Encodes the message onto the buffer.
	public static void encode(PlayerAddVelocityPacket message, FriendlyByteBuf buffer) {
		// Three pieces of information need to be sent.
		buffer.writeDouble(message.getDx());
		buffer.writeDouble(message.getDy());
		buffer.writeDouble(message.getDz());
	}

	public static PlayerAddVelocityPacket decode(FriendlyByteBuf buffer) {
		// Read off that one piece of information.
		return new PlayerAddVelocityPacket(buffer.readDouble(), buffer.readDouble(), buffer.readDouble());
	}
	
	public static void handle(PlayerAddVelocityPacket msg, Supplier<NetworkEvent.Context> ctx) {
		ctx.get().enqueueWork(() -> {
			
			// Work that needs to be thread-safe (most work)			
			DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> PlayerAddVelocityPacket.handleClientPacket(msg, ctx));
			
		});
		ctx.get().setPacketHandled(true);
	}


	@SuppressWarnings("resource")
	private static void handleClientPacket(PlayerAddVelocityPacket msg, Supplier<Context> ctx) {
		Minecraft.getInstance().player.addDeltaMovement(new Vec3(msg.getDx(), msg.getDy(), msg.getDz()));
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
