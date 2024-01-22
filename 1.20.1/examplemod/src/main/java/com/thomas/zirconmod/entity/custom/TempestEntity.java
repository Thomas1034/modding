package com.thomas.zirconmod.entity.custom;

import java.util.EnumSet;

import com.thomas.zirconmod.block.ModBlocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Ghast;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.LargeFireball;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class TempestEntity extends Ghast {

	public final AnimationState idleAnimationState = new AnimationState();
	private int idleAnimationTimeout = 0;

	public TempestEntity(EntityType<? extends Ghast> p_32725_, Level p_32726_) {
		super(p_32725_, p_32726_);
	}

	protected void registerGoals() {
		this.goalSelector.addGoal(5, new TempestEntity.RandomFloatAroundGoal(this));
		this.goalSelector.addGoal(7, new TempestEntity.TempestLookGoal(this));
		this.goalSelector.addGoal(7, new TempestEntity.TempestShootLightningGoal(this));
		this.targetSelector.addGoal(1,
				new NearestAttackableTargetGoal<>(this, Player.class, 10, true, false, (p_289460_) -> {
					return Math.abs(p_289460_.getY() - this.getY()) <= 4.0D;
				}));
	}

	@Override
	public void tick() {
		super.tick();

		// If below the world limit, add levitation!
		if (this.getEyePosition().y < this.level().getMinBuildHeight() + 8) {
			this.addEffect(new MobEffectInstance(MobEffects.LEVITATION, 5, 15));
		}
		if (this.level().isClientSide()) {
			setupAnimationStates();
		}
	}

	private void setupAnimationStates() {
		if (this.idleAnimationTimeout <= 0) {
			this.idleAnimationTimeout = 80;
			this.idleAnimationState.start(this.tickCount);
		} else {
			--this.idleAnimationTimeout;
		}
	}

	@Override
	protected void updateWalkAnimation(float pPartialTick) {
		float f;
		if (this.getPose() == Pose.STANDING) {
			f = Math.min(pPartialTick * 6F, 1f);
		} else {
			f = 0f;
		}

		this.walkAnimation.update(f, 0.2f);
	}

	static class TempestLookGoal extends Goal {
		private final TempestEntity tempest;

		public TempestLookGoal(TempestEntity p_32762_) {
			this.tempest = p_32762_;
			this.setFlags(EnumSet.of(Goal.Flag.LOOK));
		}

		public boolean canUse() {
			return true;
		}

		public boolean requiresUpdateEveryTick() {
			return true;
		}

		public void tick() {
			if (this.tempest.getTarget() == null) {
				Vec3 vec3 = this.tempest.getDeltaMovement();
				this.tempest.setYRot(-((float) Mth.atan2(vec3.x, vec3.z)) * (180F / (float) Math.PI));
				this.tempest.yBodyRot = this.tempest.getYRot();
			} else {
				LivingEntity livingentity = this.tempest.getTarget();
				if (livingentity.distanceToSqr(this.tempest) < 4096.0D) {
					double d1 = livingentity.getX() - this.tempest.getX();
					double d2 = livingentity.getZ() - this.tempest.getZ();
					this.tempest.setYRot(-((float) Mth.atan2(d1, d2)) * (180F / (float) Math.PI));
					this.tempest.yBodyRot = this.tempest.getYRot();
				}
			}

		}
	}

	static class TempestShootLightningGoal extends Goal {
		private final TempestEntity tempest;
		public int chargeTime;
		private Vec3 targetPos = null;

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

		public void tick() {
			LivingEntity target = this.tempest.getTarget();
			if (target != null && target.level() instanceof ServerLevel) {
				if (target.distanceToSqr(this.tempest) < 4096.0D && this.tempest.hasLineOfSight(target)) {
					// Increment the charge time.
					Level level = this.tempest.level();
					++this.chargeTime;

					// If the charge time is zero, get the target's position.
					if (this.chargeTime == 0 && !this.tempest.isSilent()) {
						// This makes the ghast firing sound. Replace later?
						// level.levelEvent((Player) null, 1015, this.tempest.blockPosition(), 0);
						// First, play a sound at the target's position.
						level.playSound(null, target.blockPosition(), SoundEvents.TRIDENT_THUNDER, SoundSource.HOSTILE,
								1.0f, 1.0f);
						this.targetPos = target.position();
					}

					// If the charge time is between 7 and 13, fire hailstone barrage
					if (this.chargeTime > 7 && this.chargeTime < 13 && this.chargeTime % 2 == 0){
						double velocityScale = 1.0D;
						Vec3 viewVector = this.tempest.getViewVector(1.0F);
						double d2 = target.getX() - (this.tempest.getX() + viewVector.x * velocityScale);
						double d3 = target.getY(0.5D) - (0.5D + this.tempest.getY(0.5D));
						double d4 = target.getZ() - (this.tempest.getZ() + viewVector.z * velocityScale);
						if (!this.tempest.isSilent()) {
							// TODO edit this sound later
							level.playSound(null, target.blockPosition(), SoundEvents.SNOW_GOLEM_SHOOT,
									SoundSource.HOSTILE, 1.0f, 1.0f);
						}

						LargeFireball largefireball = new LargeFireball(level, this.tempest, d2, d3, d4, 1);
						largefireball.setPos(this.tempest.getX() + viewVector.x * 4.0D, this.tempest.getY(0.5D) + 0.5D,
								largefireball.getZ() + viewVector.z * 4.0D);
						level.addFreshEntity(largefireball);

					}

					// If the charge time is 20, place lightning.
					if (this.chargeTime == 20) {
						// Creates lightning blocks in the direction the target is moving.
						if (this.targetPos != null) {
							BlockPos placePos = new BlockPos((int) this.targetPos.x - 1, (int) this.targetPos.y + 1,
									(int) this.targetPos.z - 1);
							// Place lightning blocks at and around that position.
							if (level.getBlockState(placePos).isAir()) {
								level.setBlockAndUpdate(placePos,
										ModBlocks.UNSTABLE_LIGHTNING_BLOCK.get().defaultBlockState());
							}
							int numToPlace = this.tempest.random.nextInt(2) + 1;
							for (int i = 0; i < numToPlace; i++) {
								Axis offsetAxis = Direction.Axis.getRandom(this.tempest.random);
								BlockPos offsetPos = placePos.relative(offsetAxis, this.tempest.random.nextInt(3) - 1);
								if (level.getBlockState(offsetPos).isAir()) {
									level.setBlockAndUpdate(offsetPos,
											ModBlocks.UNSTABLE_LIGHTNING_BLOCK.get().defaultBlockState());
								}
							}

							this.targetPos = null;
						}

						this.chargeTime = -40;
					}
				} else if (this.chargeTime > 0) {
					--this.chargeTime;
				}

				this.tempest.setCharging(this.chargeTime > 10);
			}
		}
	}

	static class RandomFloatAroundGoal extends Goal {
		private final TempestEntity tempest;

		public RandomFloatAroundGoal(TempestEntity p_32783_) {
			this.tempest = p_32783_;
			this.setFlags(EnumSet.of(Goal.Flag.MOVE));
		}

		public boolean canUse() {
			MoveControl movecontrol = this.tempest.getMoveControl();
			if (!movecontrol.hasWanted()) {
				return true;
			} else {
				double d0 = movecontrol.getWantedX() - this.tempest.getX();
				double d1 = movecontrol.getWantedY() - this.tempest.getY();
				double d2 = movecontrol.getWantedZ() - this.tempest.getZ();
				double d3 = d0 * d0 + d1 * d1 + d2 * d2;
				return d3 < 1.0D || d3 > 3600.0D;
			}
		}

		public boolean canContinueToUse() {
			return false;
		}

		public void start() {
			RandomSource randomsource = this.tempest.getRandom();
			double d0 = this.tempest.getX() + (double) ((randomsource.nextFloat() * 2.0F - 1.0F) * 16.0F);
			double d1 = this.tempest.getY() + (double) ((randomsource.nextFloat() * 2.0F - 1.0F) * 16.0F);
			double d2 = this.tempest.getZ() + (double) ((randomsource.nextFloat() * 2.0F - 1.0F) * 16.0F);
			this.tempest.getMoveControl().setWantedPosition(d0, d1, d2, 1.0D);
		}
	}

}
