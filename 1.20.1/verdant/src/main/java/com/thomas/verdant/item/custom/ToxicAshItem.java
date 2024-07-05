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

public class ToxicAshItem extends Item {

	public ToxicAshItem(Properties properties) {
		super(properties);
		// BoneMealItem
	}

	public InteractionResult useOn(UseOnContext context) {
		Level level = context.getLevel();
		BlockPos blockpos = context.getClickedPos();
		if (applyAsh(context.getItemInHand(), level, blockpos, context.getPlayer())) {

			if (level instanceof ServerLevel serverLevel) {
				// System.out.println("Effects.");
				addDeathParticles(serverLevel, blockpos, 30);
				level.playSound(null, blockpos, SoundEvents.BONE_MEAL_USE, SoundSource.PLAYERS);
			}
			return InteractionResult.sidedSuccess(level.isClientSide);
		}
		return InteractionResult.PASS;
	}

	public static boolean applyAsh(ItemStack stack, Level level, BlockPos pos, Player player) {
		BlockState state = level.getBlockState(pos);

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

		// Ensure the level is a server level.
		if (level.isClientSide) {
			return true;
		}
		// Otherwise, update and shrink.
		level.addDestroyBlockEffect(pos, state);
		level.setBlockAndUpdate(pos, next);
		stack.shrink(1);
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
