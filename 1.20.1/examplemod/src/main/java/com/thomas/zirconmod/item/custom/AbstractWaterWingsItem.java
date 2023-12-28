package com.thomas.zirconmod.item.custom;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

public abstract class AbstractWaterWingsItem extends AbstractWingsItem {

	private float push;
	private float maxV;

	// Push is the amount added to the lateral velocity.
	public AbstractWaterWingsItem(Properties properties, float push, float maxV) {
		super(properties);
		this.push = push;
		this.maxV = maxV;
	}

	@Override
	public boolean elytraFlightTick(ItemStack stack, LivingEntity entity, int flightTicks) {
		super.elytraFlightTick(stack, entity, flightTicks);
		
		// Check if the entity is trying to flap and can.
		if (entity.isShiftKeyDown() && canFlap(entity)) {
			// Boosts the entity's lateral velocity.
			Vec3 velocity = entity.getDeltaMovement();
			double x = entity.getLookAngle().x * this.getPushMultiplier(entity) + velocity.x;
			double y = entity.getLookAngle().y * this.getPushMultiplier(entity) + velocity.y;
			double z = entity.getLookAngle().z * this.getPushMultiplier(entity) + velocity.z;
			velocity = new Vec3(x, y, z);

			if (velocity.length() > maxV) {
				velocity = velocity.normalize().multiply(maxV, maxV, maxV);
			}

			entity.setDeltaMovement(velocity);
		}
		return true;
	}

	private double getPushMultiplier(LivingEntity entity) {
		return this.push * (entity.isInWater() ? 10 : 1);
	}

	public int limit(int value, int min, int max) {
		return Math.max(min, Math.min(value, max));
	}

	private boolean canFlap(LivingEntity entity) {
		return entity.isInWaterOrRain();
	}
}