package com.thomas.zirconmod.network;

import java.util.function.Supplier;

import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkEvent.Context;

public class PlayerVelocityRequestPacket {

	public PlayerVelocityRequestPacket() {

	}

	// Encodes the message onto the buffer.
	public static void encode(PlayerVelocityRequestPacket message, FriendlyByteBuf buffer) {
		// No information needs to be sent.
	}

	public static PlayerVelocityRequestPacket decode(FriendlyByteBuf buffer) {
		// No information needs to be read.
		return new PlayerVelocityRequestPacket();
	}

	public static void handle(PlayerVelocityRequestPacket msg, Supplier<NetworkEvent.Context> ctx) {
		ctx.get().enqueueWork(() -> {

			// Work that needs to be thread-safe (most work)
			DistExecutor.unsafeRunWhenOn(Dist.CLIENT,
					() -> () -> PlayerVelocityRequestPacket.handleClientPacket(msg, ctx));

		});
		ctx.get().setPacketHandled(true);
	}

	@SuppressWarnings("resource")
	private static void handleClientPacket(PlayerVelocityRequestPacket msg, Supplier<Context> ctx) {
		ModPacketHandler.sendToServer(new PlayerVelocityReplyPacket(Minecraft.getInstance().player.getDeltaMovement()));
	}

}
