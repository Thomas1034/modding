package com.thomas.zirconmod.entity.ai;

import javax.annotation.Nullable;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomFlyingGoal;
import net.minecraft.world.phys.Vec3;

public class NimbulaStayWithinBoundsGoal extends WaterAvoidingRandomFlyingGoal {

	private Vec3 destination = null;
	private int lowerBound = 0;

	public NimbulaStayWithinBoundsGoal(PathfinderMob p_25981_, double p_25982_, int lowerBound) {
		super(p_25981_, p_25982_);
		this.lowerBound = lowerBound;
	}

	// Tells the mob to go toward the goal, come what may.
	@Override
	public void tick() {
		super.tick();

		// If there is a goal
		if (this.destination != null) {
			Vec3 v = this.mob.getDeltaMovement();
			// If the mob is too low, add levitation.
			if (this.mob.position().y < this.destination.y && this.mob.getDeltaMovement().y < 3) {
				this.mob.addEffect(new MobEffectInstance(MobEffects.LEVITATION, 10, 8));
			}
			// If the mob is too high, remove delta-v.
			if (this.mob.position().y > this.destination.y && this.mob.getDeltaMovement().y > -3) {
				this.mob.setDeltaMovement((new Vec3(v.x, -0.5, v.z)));
			}
		} else {
			// If there is not a goal, apply levitation if too low.
			if (this.mob.position().y < this.lowerBound) {
				this.mob.addEffect(new MobEffectInstance(MobEffects.LEVITATION, 10, 8));
			}
		}

	}

	@Override
	public boolean canUse() {
		return super.canUse() && (this.mob.position().y > this.mob.level().getMaxBuildHeight() - this.lowerBound || this.mob.position().y < this.lowerBound);
	}

	@Override
	public boolean canContinueToUse() {
		return super.canUse() && (this.mob.position().y > this.mob.level().getMaxBuildHeight() - this.lowerBound || this.mob.position().y < this.lowerBound);
	}

	@Nullable
	protected Vec3 getPosition() {
		Vec3 vec3 = this.mob.position();
		Vec3 dest = vec3;
		if (dest.y < this.lowerBound) {
			dest = new Vec3(dest.x, this.lowerBound, dest.z);
		} else if (dest.y > this.mob.level().getMaxBuildHeight() - this.lowerBound) {
			dest = new Vec3(dest.x, this.mob.level().getMaxBuildHeight() - this.lowerBound, dest.z);
		}
		this.destination = dest;
		return dest;
	}

}
