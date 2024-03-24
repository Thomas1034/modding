package com.thomas.zirconmod.network;

import java.util.function.Supplier;

import com.thomas.zirconmod.effect.ModEffects;
import com.thomas.zirconmod.item.custom.wings.AbstractFlappingWingsItem;
import com.thomas.zirconmod.util.Utilities;
import com.thomas.zirconmod.util.WingsItem;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

public class WingFlapPacket {

	private float exhaustionToAdd;

	public WingFlapPacket(float exhaustionToAdd) {
		this.setExhaustionToAdd(exhaustionToAdd);
	}

	// Encodes the message onto the buffer.
	public static void encode(WingFlapPacket message, FriendlyByteBuf buffer) {
		// Only one piece of information needs to be sent.
		buffer.writeFloat(message.getExhaustionToAdd());
	}

	public static WingFlapPacket decode(FriendlyByteBuf buffer) {
		// Read off that one piece of information.
		return new WingFlapPacket(buffer.readFloat());
	}

	public static void handle(WingFlapPacket msg, Supplier<NetworkEvent.Context> ctx) {
		ctx.get().enqueueWork(() -> {
			// Give the sender flight exhaustion.

			// Work that needs to be thread-safe (most work)
			ServerPlayer player = ctx.get().getSender(); // the client that sent this packet
			ServerLevel sl = player.serverLevel();
			ItemStack wings = player.getItemBySlot(EquipmentSlot.CHEST);

			// Check if the wings are actually wings.
			if (wings.getItem() instanceof AbstractFlappingWingsItem afwi) {

				// Applies flight exhaustion.
				player.addEffect(new MobEffectInstance(ModEffects.FLIGHT_EXHAUSTION.get(),
						(int) (msg.getExhaustionToAdd() * 20), 0, false, false));
				// Adds flapping particles.
				Utilities.addParticlesAroundPositionServer(sl, player.position(), ParticleTypes.CLOUD, 1.0, 5);
				Utilities.addParticlesAroundPositionServer(sl, player.position(), ParticleTypes.CLOUD, 1.0, 5);
				Utilities.addParticlesAroundPositionServer(sl, player.position(), ParticleTypes.CLOUD, 2.0, 5);
				Utilities.addParticlesAroundPositionServer(sl, player.position(), ParticleTypes.CLOUD, 2.0, 5);

				// Wears down the wings.
				WingsItem.decreaseDurabilityPublic(wings, player);
				// Plays a sound, assuming the chunk is loaded.
				sl.playSound(null, player.blockPosition(), SoundEvents.ENDER_DRAGON_FLAP, SoundSource.PLAYERS, 1.0f,
						1.0f);
			}

		});
		ctx.get().setPacketHandled(true);
	}

	public float getExhaustionToAdd() {
		return exhaustionToAdd;
	}

	public void setExhaustionToAdd(float exhaustionToAdd) {
		this.exhaustionToAdd = exhaustionToAdd;
	}

}
