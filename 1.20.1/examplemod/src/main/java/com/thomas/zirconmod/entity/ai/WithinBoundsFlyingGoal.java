package com.thomas.zirconmod.entity.ai;

import javax.annotation.Nullable;

import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomFlyingGoal;
import net.minecraft.world.entity.ai.util.AirAndWaterRandomPos;
import net.minecraft.world.entity.ai.util.HoverRandomPos;
import net.minecraft.world.phys.Vec3;

public class WithinBoundsFlyingGoal extends WaterAvoidingRandomFlyingGoal {

	private int lowerBound = 0;
	public WithinBoundsFlyingGoal(PathfinderMob p_25981_, double p_25982_, int lowerBound) {
		super(p_25981_, p_25982_);
		this.lowerBound = lowerBound;
	}

	@Nullable
	protected Vec3 getPosition() {
		Vec3 vec3 = this.mob.getViewVector(0.0F);
		Vec3 vec31 = HoverRandomPos.getPos(this.mob, 8, 7, vec3.x, vec3.z, ((float) Math.PI / 2F), 3, 1);
		Vec3 dest = vec31 != null ? vec31
				: AirAndWaterRandomPos.getPos(this.mob, 8, 4, -2, vec3.x, vec3.z, (double) ((float) Math.PI / 2F));
		if (dest == null)
			return null;
		// If the destination is below y=0, increases the destination.
		if (dest.y < 0)
		{
			dest = new Vec3(dest.x, dest.y + 16, dest.z);
		}
		
		if (dest.y < this.mob.level().getMinBuildHeight() + this.lowerBound + 64)
			dest = new Vec3(dest.x, -32, dest.z);
		else if (dest.y > this.mob.level().getMaxBuildHeight() - this.lowerBound)
			dest = new Vec3(dest.x, 288, dest.z);
		
		return dest;
	}

}
