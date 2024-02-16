package com.thomas.zirconmod.entity.ai;

import com.thomas.zirconmod.block.custom.LightningBlock;
import com.thomas.zirconmod.block.custom.UnstableLightningBlock;
import com.thomas.zirconmod.entity.custom.BallLightningEntity;
import com.thomas.zirconmod.entity.custom.HailstoneEntity;
import com.thomas.zirconmod.entity.custom.TempestEntity;
import com.thomas.zirconmod.util.Utilities;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;

public class TempestShootLightningGoal extends Goal {
	private final TempestEntity tempest;
	public int chargeTime;
	private Vec3 targetPos = null;
	private int attackType = 0;

	public TempestShootLightningGoal(TempestEntity p_32776_) {
		this.tempest = p_32776_;
	}

	public boolean canUse() {
		return this.tempest.getTarget() != null;
	}

	public void start() {
		this.chargeTime = 0;

	}

	public void stop() {
		this.tempest.setCharging(false);
	}

	public boolean requiresUpdateEveryTick() {
		return true;
	}

	private int getChargeTimeForAttack() {

		if (this.attackType == 0) {
			return 40;

		}

		else if (this.attackType == 1) {
			return 20;
		}

		else if (this.attackType == 2) {
			return 1;
		}

		return 60;

	}

	public void tick() {
		LivingEntity target = this.tempest.getTarget();
		if (target != null && target.level() instanceof ServerLevel level) {
			// Increased range to 256, from 64
			//
			int range = 128;
			if (target.distanceToSqr(this.tempest) < (range * range) && this.tempest.hasLineOfSight(target)) {
				// Increment the charge time.
				++this.chargeTime;

				// If the charge time is zero, get the target's position.
				if (this.chargeTime == 0) {

					// Choose the attack type.
					this.attackType = Utilities.pickNumberWithProbability(this.tempest.getRandom(),
							new int[] { 1, 1, 8 });

					if (this.attackType == 0) {
						this.tempest.setCharging(true);
						// Do a lightning attack later.
						// Warn and get the target now.
						// First, play a sound at the target's position.
						level.playSound(null, target.blockPosition(), SoundEvents.TRIDENT_THUNDER, SoundSource.HOSTILE,
								1.0f, 1.0f);
						// Then, get the target's position.
						this.targetPos = target.position();
					}
				}

				// If the charge time is partial and it's a hailstone attack, launch every other tick.
				if (this.attackType == 2 && this.chargeTime % 2 == 0) {

					// Fire a hailstone.

					if (!this.tempest.isSilent()) {
						// TODO edit this sound later
						this.tempest.playSound(SoundEvents.SNOW_GOLEM_SHOOT, 1.0F,
								0.4F / (this.tempest.getRandom().nextFloat() * 0.4F + 0.8F));

					}
					
					this.createHailstone(level, target, 6f);
				}
				
				// If the charge time is full, fire a primary attack.
				if (this.chargeTime == this.getChargeTimeForAttack()) {

					if (this.attackType == 0 && this.targetPos != null) {

						// Lightning block attack.
						this.tempest.setCharging(false);

						this.createLightningCluster(level);
						
						this.targetPos = null;
					}

					else if (this.attackType == 1) {

						// Fire a ball lightning.
						this.tempest.setCharging(false);

						this.createBallLightning(level, target);

					}

					else if (this.attackType == 2) {

						// Fire a hailstone.
						this.tempest.setCharging(false);

						if (!this.tempest.isSilent()) {
							// TODO edit this sound later
							this.tempest.playSound(SoundEvents.SNOW_GOLEM_SHOOT, 1.0F,
									0.4F / (this.tempest.getRandom().nextFloat() * 0.4F + 0.8F));

						}
						
						// Creates a cluster of hailstones.
						this.createHailstone(level, target, 0.1f);
						this.createHailstone(level, target, 6f);
						this.createHailstone(level, target, 6f);
						this.createHailstone(level, target, 6f);
						this.createHailstone(level, target, 6f);
						this.createHailstone(level, target, 6f);
						this.createHailstone(level, target, 6f);
						this.createHailstone(level, target, 6f);
						this.createHailstone(level, target, 6f);
						this.createHailstone(level, target, 6f);

						

					}

					this.chargeTime = -40;
				}
			} else if (this.chargeTime > 0) {
				--this.chargeTime;
			}

			this.tempest.setCharging(this.chargeTime > 10);
		}
	}

	private void createLightningCluster(ServerLevel level) {

		// Creates lightning blocks
		BlockPos placePos = new BlockPos((int) this.targetPos.x - 1, (int) this.targetPos.y + 1,
				(int) this.targetPos.z - 1);

		UnstableLightningBlock.placeCluster(level, placePos, 3, this.tempest.getRandom());
		LightningBlock.addLightningBoltAtChecked(level, placePos);

	}

	private void createBallLightning(ServerLevel level, LivingEntity target) {
		double velocityScale = 3.0D;
		Vec3 viewVector = this.tempest.getViewVector(1.0F);
		double d2 = target.getX() - (this.tempest.getX() + viewVector.x * velocityScale);
		double d3 = target.getY(0.5D) - (0.5D + this.tempest.getY(0.5D));
		double d4 = target.getZ() - (this.tempest.getZ() + viewVector.z * velocityScale);
		if (!this.tempest.isSilent()) {
			// TODO edit this sound later
			level.playSound(null, target.blockPosition(), SoundEvents.ENDER_DRAGON_SHOOT, SoundSource.HOSTILE, 1.0f,
					1.0f);
		}

		BallLightningEntity ballLightning = new BallLightningEntity(level, this.tempest, d2, d3, d4, 3);
		ballLightning.setPos(this.tempest.getX() + viewVector.x * 4.0D, this.tempest.getY(0.5D) + 0.5D,
				ballLightning.getZ() + viewVector.z * 4.0D);
		level.addFreshEntity(ballLightning);

	}
	
	private void createHailstone(ServerLevel level, LivingEntity target, float imprecision) {
		float speed = 1.0F;
		
		HailstoneEntity hailstone = new HailstoneEntity(this.tempest.level(), this.tempest);
		hailstone.setNoGravity(true);
		double targetY = target.getEyeY() + (target.isFallFlying() ? -1.5 : 0);
		double dx = target.getX() - this.tempest.getX();
		double dy = targetY - hailstone.getY();
		double dz = target.getZ() - this.tempest.getZ();
		//double d4 = Math.sqrt(d1 * d1 + d3 * d3) * (double) 0.2F;
		// Second to last is the speed.
		hailstone.shoot(dx, dy/* + d4*/, dz, 1.6F * speed, imprecision);
		//hailstone.setGlowingTag(true);
		this.tempest.level().addFreshEntity(hailstone);

	}

}