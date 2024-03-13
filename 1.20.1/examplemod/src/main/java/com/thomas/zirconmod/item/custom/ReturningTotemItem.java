package com.thomas.zirconmod.item.custom;

import com.thomas.zirconmod.util.Utilities;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
import net.minecraft.world.phys.Vec3;

public class ReturningTotemItem extends Item {

	public ReturningTotemItem(Properties properties) {
		super(properties);
	}

	// Implements custom behavior.
	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
		if (!level.isClientSide() && hand == InteractionHand.MAIN_HAND) {

			if (player instanceof ServerPlayer sp && level instanceof ServerLevel sl) {
				// In a try-catch in case any of the following are null.
				try {
					// Gets all requisite information.
					BlockPos pos = sp.getRespawnPosition();
					float angle = sp.getRespawnAngle();
					BlockPos alt = Utilities.getNearbyRespawn(sl, pos, 5);
					Vec3 respawnPos = Player.findRespawnPositionAndUseSpawnBlock(sl, pos, angle, false, false)
							.orElseGet(() -> alt.getCenter());
					ResourceKey<Level> dimkey = sp.getRespawnDimension();
					ServerLevel dim = sp.server.getLevel(dimkey);
					// Disables if the player is in the end and there is a live dragon.
					boolean isInEnd = sl.dimensionTypeRegistration().is(BuiltinDimensionTypes.END_EFFECTS);
					boolean isLiveDragon = sl.getDragons().size() == 0;
					if (isInEnd && isLiveDragon) {
						return super.use(level, player, hand);
					}

					// Respawns the player.
					// Chooses a location near the respawn point that is a valid
					// spawn point.

					sp.teleportTo(dim, respawnPos.x, respawnPos.y, respawnPos.z, angle, 0);
					// Adds a cooldown to the item.
					player.getCooldowns().addCooldown(this, 40);
					
				} catch (NullPointerException e) {
					// Send a message if the player does not have a valid respawn point.
					sp.displayClientMessage(Component.translatable("item.zirconmod.no_respawn"), true);
				}

			}
		}
		// Calls super.use, to handle other behaviors.
		return super.use(level, player, hand);
	}

}
