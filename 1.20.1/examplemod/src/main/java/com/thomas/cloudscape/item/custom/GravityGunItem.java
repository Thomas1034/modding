package com.thomas.cloudscape.item.custom;

import com.thomas.cloudscape.util.Utilities;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class GravityGunItem extends Item {

	private double range = 32;
	private static final double MINIMUM_DRAW_DISTANCE = 4;

	public GravityGunItem(Properties properties) {
		super(properties);
		this.range = 10;
	}

	public GravityGunItem(Properties properties, float range) {
		super(properties);
		this.range = range;
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {

		if (!level.isClientSide() && hand == InteractionHand.MAIN_HAND) {
			// The player is right clicking to "pull".

			// Get the closest entity within view.
			Entity target = Utilities.getLookedAt(player, 10);
			// System.out.println(target);
			if (target != null) {
				double distance = target.distanceTo(player);

				// Pull the entity if it's far away.
				if (distance > MINIMUM_DRAW_DISTANCE + 0.5) {
					Vec3 pull = player.getLookAngle().reverse().normalize().scale(getPullStrength(distance));
					target.addDeltaMovement(pull);
				} else {
					// Snap to player view vector.
					Vec3 newPos = player.getLookAngle().normalize().scale(MINIMUM_DRAW_DISTANCE)
							.add(player.getEyePosition()).subtract(0, target.getEyeHeight(), 0);
					target.setPos(newPos);
					target.setDeltaMovement(0, 0, 0);
					target.resetFallDistance();
					
				}

			} else {
				// If no entity was found, get the targeted block.
				
			}

		}

		return super.use(level, player, hand);
	}

	@Override
	public boolean canAttackBlock(BlockState p_41441_, Level p_41442_, BlockPos p_41443_, Player p_41444_) {
		return true;
	}

	private static double getPullStrength(double distance) {

		if (distance < MINIMUM_DRAW_DISTANCE)
			return 0;

		return 1;
	}

}
