package com.thomas.cloudscape.effect.custom;

import java.util.ArrayList;
import java.util.List;

import com.thomas.cloudscape.util.MotionHelper;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

public class PropulsionEffect extends MobEffect {

	// Applies a freezing effect to the entity.
	public PropulsionEffect(MobEffectCategory mobEffectCategory, int color) {
		super(mobEffectCategory, color);
	}

	@Override
	public void applyEffectTick(LivingEntity entity, int amplifier) {
		super.applyEffectTick(entity, amplifier);

		double power = amplifier / 10.0;

		if (entity.isFallFlying()) {
			// Get the direction this entity is looking.
			Vec3 lookAngle = entity.getLookAngle().normalize();
			// Get this entity's velocity.
			Vec3 velocity = MotionHelper.getVelocity(entity);
			// Calculates the component of the velocity in the direction of the entity's
			// look angle.
			double lookComponent = lookAngle.dot(velocity);

			// If the component in the direction of the entity's look angle is less than the
			// strength of the item, boost accordingly.
			if (lookComponent < power) {
				MotionHelper.addVelocity(entity, lookAngle.scale(0.5 * (lookComponent > 0 ? (power - lookComponent) : power)));
				//System.out.println("Boosting. " + entity.tickCount);
			}
		}
	}

	@Override
	public boolean isDurationEffectTick(int duration, int amplifier) {
		return true;
	}

	// This effect cannot be cured.
	@Override
	public List<ItemStack> getCurativeItems() {
		ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
		return ret;
	}
}
