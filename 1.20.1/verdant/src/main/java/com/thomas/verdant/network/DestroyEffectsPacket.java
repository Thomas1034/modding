package com.thomas.verdant.network;

import java.util.function.Supplier;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkEvent.Context;

public class DestroyEffectsPacket {

	private BlockPos pos;
	private BlockState state;
	private int repetitions;

	public DestroyEffectsPacket(BlockPos pos, BlockState state, int repetitions) {
		this.pos = pos;
		this.state = state;
		this.repetitions = repetitions;
	}

	public DestroyEffectsPacket(BlockPos pos, BlockState state) {
		this(pos, state, 1);
	}

	// Encodes the message onto the buffer.
	public static void encode(DestroyEffectsPacket message, FriendlyByteBuf buffer) {
		// Three pieces of information need to be sent.
		buffer.writeBlockPos(message.getPosition());
		buffer.writeJsonWithCodec(BlockState.CODEC, message.getBlockState());
		buffer.writeInt(message.getRepetitions());
	}

	public static DestroyEffectsPacket decode(FriendlyByteBuf buffer) {
		// Read off that one piece of information.
		return new DestroyEffectsPacket(buffer.readBlockPos(), buffer.readJsonWithCodec(BlockState.CODEC),
				buffer.readInt());
	}

	public static void handle(DestroyEffectsPacket msg, Supplier<NetworkEvent.Context> ctx) {
		ctx.get().enqueueWork(() -> {

			// Work that needs to be thread-safe (most work)
			DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> DestroyEffectsPacket.handleClientPacket(msg, ctx));

		});
		ctx.get().setPacketHandled(true);
	}

	@SuppressWarnings("resource")
	private static void handleClientPacket(DestroyEffectsPacket msg, Supplier<Context> ctx) {
		ClientLevel level = Minecraft.getInstance().level;
		int repetitions = msg.getRepetitions();
		for (int i = 0; i < repetitions; i++) {
			level.addDestroyBlockEffect(msg.getPosition(), msg.getBlockState());
		}
	}

	public BlockPos getPosition() {
		return this.pos;
	}

	public BlockState getBlockState() {
		return this.state;
	}

	public int getRepetitions() {
		return this.repetitions;
	}

}