package com.thomas.verdant.item.custom;

import com.thomas.verdant.growth.VerdantBlockTransformer;
import com.thomas.verdant.util.Utilities;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class ToxicBucketItem extends Item {

	private final ItemStack empty;

	public ToxicBucketItem(Properties properties, ItemStack empty) {
		super(properties);
		this.empty = empty;
	}

	public InteractionResult useOn(UseOnContext context) {
		Level level = context.getLevel();
		BlockPos pos = context.getClickedPos();

		boolean anyPassed = false;
		// Iterate over a 3x3 area on the x and z axis.
		for (int i = -1; i <= 1; i++) {
			for (int k = -1; k <= 1; k++) {
				// Choose a random depth.
				// Subtract i and k from the upper bound
				int depth = level.random.nextInt(2, 4);
				for (int j = -depth; j <= 1; j++) {
					BlockPos localPos = pos.offset(i, j, k);
					if (applyBucketAsh(context.getItemInHand(), level, localPos, context.getPlayer())) {

						if (level instanceof ServerLevel serverLevel) {
							// System.out.println("Effects.");
							addDeathParticles(serverLevel, localPos, 30);
							serverLevel.playSound(null, pos, SoundEvents.BONE_MEAL_USE, SoundSource.PLAYERS, 1.0f,
									1.0f);
						}
						anyPassed = true;
					}
				}
			}
		}

		if (anyPassed) {
			if (!context.getPlayer().getAbilities().instabuild) {
				context.getPlayer().setItemInHand(context.getHand(), this.empty);
			}
			return InteractionResult.sidedSuccess(level.isClientSide);
		}

		return InteractionResult.PASS;
	}

	public static boolean applyBucketAsh(ItemStack stack, Level level, BlockPos pos, Player player) {
		BlockState state = level.getBlockState(pos);

		// Ensure the level is a server level.
		if (level.isClientSide) {
			return false;
		}

		// See if the player can break the block.
		// Returns true if the player can.
		boolean hook = net.minecraftforge.event.ForgeEventFactory.onEntityDestroyBlock(player, pos, state);

		// If not, return false.
		if (!hook)
			return false;
		// Get the next state from the transformer.
		BlockState next = VerdantBlockTransformer.TOXIC_ASH.get().next(state);
		// If nothing happens, do nothing.
		if (next.equals(state)) {
			return false;
		}

		// Otherwise, update and shrink.
		level.setBlockAndUpdate(pos, next);
		return true;

	}

	public static void addDeathParticles(ServerLevel level, BlockPos pos, int count) {
		if (count == 0) {
			count = 15;
		}
		// System.out.println("Particling.");
		BlockState blockstate = level.getBlockState(pos);
		double d1 = 0.0D;
		if (blockstate.isAir()) {
			count *= 3;
			d1 = 0.5D;
		} else if (blockstate.is(Blocks.WATER)) {
			count *= 3;
			d1 = 1.0D;
		} else if (blockstate.isSolidRender(level, pos)) {
			pos = pos.above();
			count *= 3;
			d1 = 1.0D;
		} else {
			d1 = blockstate.getShape(level, pos).max(Direction.Axis.Y);
			// Limit so it won't go beneath 0, if the block is empty.
			d1 = d1 > 0 ? d1 : 0;
		}
		// System.out.println("Really Particling; " + d1);
		Utilities.addParticlesAroundPositionServer(level, pos.getCenter().add(0, d1 - 0.5, 0), ParticleTypes.SMOKE, 0.2,
				count);
	}

}
