package com.thomas.verdant.network;

import java.util.UUID;
import java.util.function.Supplier;

import com.thomas.verdant.client.ClientInfectionData;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkEvent.Context;

public class SynchronizePlayerInfectionPacket {

	private int level;
	private UUID id;

	public SynchronizePlayerInfectionPacket(int level, UUID id) {
		this.level = level;
		this.id = id;
	}

	// Encodes the message onto the buffer.
	public static void encode(SynchronizePlayerInfectionPacket message, FriendlyByteBuf buffer) {
		// Two pieces of information need to be sent.
		buffer.writeInt(message.getLevel());
		buffer.writeUUID(message.getID());
	}

	public static SynchronizePlayerInfectionPacket decode(FriendlyByteBuf buffer) {
		// Read off that one piece of information.
		return new SynchronizePlayerInfectionPacket(buffer.readInt(), buffer.readUUID());
	}

	public static void handle(SynchronizePlayerInfectionPacket msg, Supplier<NetworkEvent.Context> ctx) {
		ctx.get().enqueueWork(() -> {

			// Work that needs to be thread-safe (most work)
			DistExecutor.unsafeRunWhenOn(Dist.CLIENT,
					() -> () -> SynchronizePlayerInfectionPacket.handleClientPacket(msg, ctx));

		});
		ctx.get().setPacketHandled(true);
	}

	private static void handleClientPacket(SynchronizePlayerInfectionPacket msg, Supplier<Context> ctx) {
		ClientInfectionData.set(msg.getID(), msg.getLevel());
	}

	public int getLevel() {
		return this.level;
	}

	public UUID getID() {
		return this.id;
	}
}
