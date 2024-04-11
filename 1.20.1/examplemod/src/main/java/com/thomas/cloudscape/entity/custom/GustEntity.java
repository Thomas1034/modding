package com.thomas.cloudscape.entity.custom;

import java.util.EnumSet;
import java.util.List;

import com.thomas.cloudscape.network.ModPacketHandler;
import com.thomas.cloudscape.network.PlayerAddVelocityPacket;
import com.thomas.cloudscape.util.Utilities;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Vex;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class GustEntity extends Vex {

	public GustEntity(EntityType<? extends Vex> p_33984_, Level p_33985_) {
		super(p_33984_, p_33985_);
	}

	public final AnimationState idleAnimationState = new AnimationState();
	private int idleAnimationTimeout = 0;

	private void setupAnimationStates() {

		if (this.idleAnimationTimeout <= 0) {
			this.idleAnimationTimeout = 40;
			this.idleAnimationState.start(this.tickCount);
		} else {
			--this.idleAnimationTimeout;
		}
	}

	public void tick() {
		super.tick();

		// Handles premature detonations
		List<Entity> entities = this.level().getEntities(this, this.getBoundingBox());
		// If something was hit, splash and die.
		if (entities != null && !entities.isEmpty()) {
			this.setIsCharging(false);
			List<Entity> targets = this.level().getEntities(this, this.getBoundingBox().inflate(4));

			for (Entity entity : targets) {
				this.doHurtTarget(entity);
			}
			this.dissipate();
		}
		
		// Despawn if it's not raining if it's natural.
		if (!this.level().isRaining() && this.getSpawnType() == MobSpawnType.NATURAL) {
			this.discard();
		}

		// Handles animations
		if (this.level().isClientSide()) {
			setupAnimationStates();
		}
	}

	protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(0, new FloatGoal(this));
		this.goalSelector.addGoal(4, new GustChargeAttackGoal(this));
		this.goalSelector.addGoal(8, new GustRandomMoveGoal(this));
		this.goalSelector.addGoal(9, new LookAtPlayerGoal(this, Player.class, 3.0F, 1.0F));
		this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Mob.class, 8.0F));
		this.targetSelector.addGoal(1, (new HurtByTargetGoal(this, Raider.class)).setAlertOthers());
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Player.class, true));
	}

	public static AttributeSupplier.Builder createAttributes() {
		return Monster.createMonsterAttributes().add(Attributes.MAX_HEALTH, 14.0D).add(Attributes.ATTACK_DAMAGE, 0.0D);
	}

	private void doKnockback(Entity entity) {
		double factor = 2.0;
		Vec3 dir = entity.getEyePosition().subtract(this.getEyePosition()).normalize();
		Vec3 knockbackVector = dir.scale(factor);
		knockbackVector = new Vec3(dir.x * factor, Math.abs(dir.y) * factor / 2, dir.z * factor);
		if (entity instanceof ServerPlayer player) {
			// TODO Copy knockback code.
			ModPacketHandler.sendToPlayer(
					new PlayerAddVelocityPacket(knockbackVector.x, knockbackVector.y, knockbackVector.z), player);
			player.knockback(knockbackVector.x, knockbackVector.y, knockbackVector.z);
		} else if (entity instanceof LivingEntity le){
			le.knockback(knockbackVector.x, knockbackVector.y, knockbackVector.z);
		}
	}

	@Override
	public boolean doHurtTarget(Entity target) {
		// Only does knockback.
		// That's plenty enough.
		this.doKnockback(target);
		return false;
	}

	@Override
	protected void onInsideBlock(BlockState state) {
		// Dissipate when inside solid blocks.
		if (state.isCollisionShapeFullBlock(this.level(), this.blockPosition())) {
			this.dissipate();
		}
	}

	public void dissipate() {
		if (this.level() instanceof ServerLevel sl) {
			// Summon particles
			Utilities.addParticlesAroundPositionServer(sl, this.position(), ParticleTypes.EXPLOSION_EMITTER, 0.5, 1);
			// Discard
			this.discard();
		}
	}

	class GustChargeAttackGoal extends Goal {

		private final GustEntity gust;
		private int chargeTicks;

		public GustChargeAttackGoal(GustEntity gust) {
			this.setFlags(EnumSet.of(Goal.Flag.MOVE));
			this.gust = gust;
			this.chargeTicks = 300;
		}

		public boolean canUse() {

			LivingEntity target = this.gust.getTarget();
			if (target != null && target.isAlive() && !this.gust.getMoveControl().hasWanted()
					&& this.gust.hasLineOfSight(target)) {
				double dist = this.gust.distanceToSqr(target);
				return dist > 4.0D;
			} else {
				return false;
			}
		}

		public boolean canContinueToUse() {
			return this.gust.getMoveControl().hasWanted() && this.gust.isCharging() && this.gust.getTarget() != null
					&& this.gust.getTarget().isAlive();
		}

		public void start() {
			LivingEntity livingentity = this.gust.getTarget();
			if (livingentity != null) {
				Vec3 vec3 = livingentity.getEyePosition();
				this.gust.moveControl.setWantedPosition(vec3.x, vec3.y, vec3.z, 1.0D);
			}

			this.gust.setIsCharging(true);
			this.gust.playSound(SoundEvents.VEX_CHARGE, 1.0F, 1.0F);
		}

		public void stop() {
			this.gust.setIsCharging(false);
		}

		public boolean requiresUpdateEveryTick() {
			return true;

		}

		public void tick() {
			LivingEntity target = this.gust.getTarget();
			List<Entity> entities = this.gust.level().getEntities(this.gust, this.gust.getBoundingBox());
			if (!this.gust.level().isClientSide()) {
				if (target != null) {

					if (this.chargeTicks < 0 || (entities != null && !entities.isEmpty())
							|| this.gust.getBoundingBox().inflate(1).intersects(target.getBoundingBox())) {
						this.gust.setIsCharging(false);
						List<Entity> targets = this.gust.level().getEntities(this.gust,
								this.gust.getBoundingBox().inflate(4));

						for (Entity entity : targets) {
							this.gust.doHurtTarget(entity);
						}
						this.gust.dissipate();

					} else if (this.gust.hasLineOfSight(target)) {
						double d0 = this.gust.distanceToSqr(target);
						if (d0 < 9.0D) {
							Vec3 vec3 = target.getEyePosition();
							this.gust.moveControl.setWantedPosition(vec3.x, vec3.y, vec3.z, 1.0D);
						}
					}
					this.chargeTicks--;
				}
			}
		}
	}

	class GustRandomMoveGoal extends Goal {

		private final GustEntity gust;

		public GustRandomMoveGoal(GustEntity gust) {
			this.setFlags(EnumSet.of(Goal.Flag.MOVE));
			this.gust = gust;
		}

		public boolean canUse() {
			return !this.gust.getMoveControl().hasWanted() && this.gust.random.nextInt(reducedTickDelay(7)) == 0;
		}

		public boolean canContinueToUse() {
			return false;
		}

		public void tick() {
			BlockPos blockpos = this.gust.getBoundOrigin();
			if (blockpos == null) {
				blockpos = this.gust.blockPosition();
			}

			for (int i = 0; i < 3; ++i) {
				BlockPos target = blockpos.offset(this.gust.random.nextInt(15) - 7, this.gust.random.nextInt(11) - 5,
						this.gust.random.nextInt(15) - 7);
				if (this.gust.level().isEmptyBlock(target)) {
					this.gust.moveControl.setWantedPosition((double) target.getX() + 0.5D,
							(double) target.getY() + 0.5D, (double) target.getZ() + 0.5D, 0.25D);
					if (this.gust.getTarget() == null) {
						this.gust.getLookControl().setLookAt((double) target.getX() + 0.5D,
								(double) target.getY() + 0.5D, (double) target.getZ() + 0.5D, 180.0F, 20.0F);
					}
					break;
				}
			}

		}
	}

}
