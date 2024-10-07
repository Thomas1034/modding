package com.thomas.verdant.network;

import com.thomas.verdant.Verdant;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry.ChannelBuilder;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public class ModPacketHandler {
	// Primarily from TurtyWurty's tutorial mod,
	// located here:
	// https://github.com/DaRealTurtyWurty/1.20-Tutorial-Mod/blob/main/src/main/java/dev/turtywurty/tutorialmod/network/PacketHandler.java

	private static String VERSION = Verdant.MOD_ID + ":1";

	private static final SimpleChannel INSTANCE = ChannelBuilder.named(new ResourceLocation(Verdant.MOD_ID, "main"))
			.serverAcceptedVersions(VERSION::equals).clientAcceptedVersions(VERSION::equals)
			.networkProtocolVersion(() -> VERSION).simpleChannel();

	public static void register() {
		INSTANCE.messageBuilder(PlayerAddVelocityPacket.class, NetworkDirection.PLAY_TO_CLIENT.ordinal())
				.encoder(PlayerAddVelocityPacket::encode).decoder(PlayerAddVelocityPacket::decode)
				.consumerNetworkThread(PlayerAddVelocityPacket::handle).add();
		INSTANCE.messageBuilder(PlayerSetVelocityPacket.class, NetworkDirection.PLAY_TO_CLIENT.ordinal())
				.encoder(PlayerSetVelocityPacket::encode).decoder(PlayerSetVelocityPacket::decode)
				.consumerNetworkThread(PlayerSetVelocityPacket::handle).add();
		INSTANCE.messageBuilder(SynchronizePlayerInfectionPacket.class, NetworkDirection.PLAY_TO_CLIENT.ordinal())
				.encoder(SynchronizePlayerInfectionPacket::encode).decoder(SynchronizePlayerInfectionPacket::decode)
				.consumerNetworkThread(SynchronizePlayerInfectionPacket::handle).add();
//		INSTANCE.messageBuilder(DestroyEffectsPacket.class, NetworkDirection.PLAY_TO_CLIENT.ordinal())
//				.encoder(DestroyEffectsPacket::encode).decoder(DestroyEffectsPacket::decode)
//				.consumerNetworkThread(DestroyEffectsPacket::handle).add();

	}

	public static void sendToServer(Object msg) {
		INSTANCE.send(PacketDistributor.SERVER.noArg(), msg);
	}

	public static void sendToPlayer(Object msg, ServerPlayer player) {
		INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), msg);
	}

	public static void sendToAllClients(Object msg) {
		INSTANCE.send(PacketDistributor.ALL.noArg(), msg);
	}
}
