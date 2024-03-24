package com.thomas.zirconmod.item.custom.wings;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

public abstract class AbstractHoverWingsItem extends AbstractWingsItem {
	
	// Lift is the amount added to the vertical velocity.
	// Push is the amount added to the lateral velocity.
	// Exhaustion is the number of seconds between flaps.
	public AbstractHoverWingsItem(Properties properties) {
		super(properties);
	}
	
	@Override
	public boolean elytraFlightTick(ItemStack stack, LivingEntity entity, int flightTicks) {
		super.elytraFlightTick(stack, entity, flightTicks);

		// Check if the entity is trying to flap and can.
		if (entity.isShiftKeyDown() && canFlap(entity)) {
			// Slows/halts the entity's velocity.
			Vec3 velocity = entity.getDeltaMovement();
			if (velocity.length() <= 0.1)
			{
				velocity = new Vec3(0, 0.025, 0);
				velocity.add(0, Math.abs(entity.getLookAngle().normalize().y) * 0.05, 0);
			}
			else
			{
				velocity = velocity.multiply(0.90, 0.50, 0.90);
			}
			entity.setDeltaMovement(velocity);
			// Wears down the wings.
			decreaseDurability(stack, entity);
		}
		return true;
	}

	private boolean canFlap(LivingEntity entity) {
		return true;
	}
}
