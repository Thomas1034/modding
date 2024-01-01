package com.thomas.zirconmod.item.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;

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
					BlockPos npos = getNearbyRespawn(dim, pos, 3);

					sp.teleportTo(dim, npos.getX() + 0.5, npos.getY(), npos.getZ() + 0.5, angle, 0);
				} catch (NullPointerException e) {
					// Send a chat message if the player does not have a valid respawn point.
				}
			}
			// Adds a cooldown to the item.
			player.getCooldowns().addCooldown(this, 40);
		}
		// Calls super.use, to handle other behaviors.
		return super.use(level, player, hand);
	}

	// Checks if a block position is a valid respawn point.
	private static boolean isValidRespawn(Level level, BlockPos pos) throws NullPointerException {

		BlockState above = level.getBlockState(pos.above());
		BlockState at = level.getBlockState(pos);
		BlockState below = level.getBlockState(pos.below());
		// System.out.println("Column is: " + below.getBlock() + " " + at.getBlock() + "
		// " + above.getBlock());

		boolean isBottomSolid = below.isFaceSturdy(level, pos, Direction.UP);
		boolean isEmpty = at.canBeReplaced() && above.canBeReplaced();

		// System.out.println("Is the bottom solid? " + isBottomSolid);
		// System.out.println("Is it empty? " + isEmpty);
		return isBottomSolid && isEmpty;
	}

	// Gets a nearby respawn point that will be valid.
	// If there are no valid respawn points, returns null.
	public static BlockPos getNearbyRespawn(Level level, BlockPos start, int maxDistance) {
		BlockPos npos = null;
		for (int i = -1 * maxDistance; i < maxDistance; i++) {
			for (int j = -1 * maxDistance; j < maxDistance; j++) {
				for (int k = -1 * maxDistance; k < maxDistance; k++) {
					npos = start.offset(j, i, k);
					if (isValidRespawn(level, npos)) {
						return npos;
					} else {
						npos = null;
					}
				}
			}
		}

		return null;
	}
}
