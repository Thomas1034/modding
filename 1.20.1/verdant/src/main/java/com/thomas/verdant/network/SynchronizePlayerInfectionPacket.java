package com.thomas.verdant.network;

import java.util.function.Supplier;

import com.thomas.verdant.client.ClientInfectionData;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkEvent.Context;

public class SynchronizePlayerInfectionPacket {

	private int level;

	public SynchronizePlayerInfectionPacket(int level) {
		this.level = level;
	}

	// Encodes the message onto the buffer.
	public static void encode(SynchronizePlayerInfectionPacket message, FriendlyByteBuf buffer) {
		// One piece of information need to be sent.
		buffer.writeInt(message.getLevel());
	}

	public static SynchronizePlayerInfectionPacket decode(FriendlyByteBuf buffer) {
		// Read off that one piece of information.
		return new SynchronizePlayerInfectionPacket(buffer.readInt());
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
		ClientInfectionData.set(msg.getLevel());
	}

	public int getLevel() {
		return this.level;
	}

}
