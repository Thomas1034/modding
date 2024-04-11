package com.thomas.cloudscape.entity.custom;

import java.util.EnumSet;

import com.thomas.cloudscape.effect.ModEffects;
import com.thomas.cloudscape.entity.ai.TempestShootLightningGoal;

import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.PowerableMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.AbstractGolem;
import net.minecraft.world.entity.monster.Ghast;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.SpectralArrow;
import net.minecraft.world.entity.projectile.ThrownTrident;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class TempestEntity extends Ghast implements PowerableMob {

	public final AnimationState idleAnimationState = new AnimationState();
	private int idleAnimationTimeout = 0;

	public TempestEntity(EntityType<? extends Ghast> p_32725_, Level p_32726_) {
		super(p_32725_, p_32726_);
		this.moveControl = new TempestEntity.TempestMoveControl(this);

	}

	protected void registerGoals() {
		this.goalSelector.addGoal(5, new TempestEntity.RandomFloatAroundGoal(this));
		this.goalSelector.addGoal(3, new TempestEntity.TempestLookGoal(this));
		this.goalSelector.addGoal(3, new TempestShootLightningGoal(this));
		this.targetSelector.addGoal(1,
				new NearestAttackableTargetGoal<>(this, Player.class, 10, true, false, (p_289460_) -> {
					return Math.abs(p_289460_.getY() - this.getY()) <= 4.0D;
				}));
		this.targetSelector.addGoal(3,
				new NearestAttackableTargetGoal<>(this, AbstractGolem.class, 10, true, false, (p_289460_) -> {
					return Math.abs(p_289460_.getY() - this.getY()) <= 4.0D;
				}));

	}

	public static AttributeSupplier.Builder createAttributes() {
		return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 160.0D).add(Attributes.FOLLOW_RANGE, 256.0D);
	}

	@Override
	public void tick() {
		super.tick();

		// Putting out fires.
		this.setRemainingFireTicks(0);

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

	// When the entity is hurt, check if it takes damage from that source. If not,
	// do nothing.
	public boolean hurt(DamageSource damageSource, float f) {

		// Check if it is invulnerable first.
		if (this.isInvulnerableTo(damageSource) || this.isInvulnerable())
			return false;

		Entity entity = damageSource.getDirectEntity();
		// Tempests are immune to projectiles, unless certain conditions are true.
		if (entity instanceof Projectile) {
			// Not immune to spectral arrows.
			if (entity instanceof SpectralArrow || entity instanceof ThrownTrident)
				return super.hurt(damageSource, f);
			// Not immune if it has citrine glow.
			else if (this.hasEffect(ModEffects.CITRINE_GLOW.get()))
				return super.hurt(damageSource, f);
			// Otherwise, it is immune
			else
				return false;
		}
		// If it isn't an arrow, this is susceptible.
		return super.hurt(damageSource, f);
	}

	@Override
	public boolean isInvulnerableTo(DamageSource d) {
		return super.isInvulnerableTo(d) || d.is(DamageTypeTags.IS_FALL) || d.is(DamageTypeTags.IS_LIGHTNING);
	}

	// Fireproof
	@Override
	public boolean fireImmune() {
		return true;
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

	public static class TempestMoveControl extends MoveControl {
		private final TempestEntity tempest;
		private int floatDuration;
		private static final double SPEED = 0.2;

		public TempestMoveControl(TempestEntity tempest) {
			super(tempest);
			this.tempest = tempest;
		}

		public void tick() {
			if (this.operation == MoveControl.Operation.MOVE_TO) {
				if (this.floatDuration-- <= 0) {
					this.floatDuration += this.tempest.getRandom().nextInt(5) + 2;
					Vec3 path = new Vec3(this.wantedX - this.tempest.getX(), this.wantedY - this.tempest.getY(),
							this.wantedZ - this.tempest.getZ());
					double distance = path.length();
					path = path.normalize();
					if (this.canReach(path, Mth.ceil(distance))) {
						this.tempest.setDeltaMovement(this.tempest.getDeltaMovement().add(path.scale(SPEED)));
					} else {
						this.operation = MoveControl.Operation.WAIT;
					}
				}

			}
		}

		private boolean canReach(Vec3 offset, int tries) {
			AABB boundingBox = this.tempest.getBoundingBox();

			for (int i = 1; i < tries; ++i) {
				boundingBox = boundingBox.move(offset);
				if (!this.tempest.level().noCollision(this.tempest, boundingBox)) {
					return false;
				}
			}

			return true;
		}
	}

	@Override
	public boolean isPowered() {
		return this.isCharging();
	}

}
