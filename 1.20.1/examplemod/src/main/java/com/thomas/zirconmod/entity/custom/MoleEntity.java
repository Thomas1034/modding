package com.thomas.zirconmod.entity.custom;

import java.util.function.Predicate;

import com.thomas.zirconmod.entity.ai.MoleAttackGoal;

import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.ClimbOnTopOfPowderSnowGoal;
import net.minecraft.world.entity.ai.goal.FleeSunGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;

public class MoleEntity extends Monster {
	public static final EntityDataAccessor<Boolean> ATTACKING = SynchedEntityData.defineId(MoleEntity.class,
			EntityDataSerializers.BOOLEAN);

	public final AnimationState idleAnimationState = new AnimationState();
	public final AnimationState sniffAnimationState = new AnimationState();
	private int idleAnimationTimeout = 0;

	public final AnimationState attackAnimationState = new AnimationState();
	public int attackAnimationTimeout = 0;

	private static final Predicate<LivingEntity> LOUD_ENTITY_SELECTOR = (entity) -> {
		return (!entity.dampensVibrations()) && entity.attackable();
	};

	public MoleEntity(EntityType<? extends Monster> pEntityType, Level level) {
		super(pEntityType, level);
		this.getNavigation().setCanFloat(true);
		this.setPathfindingMalus(BlockPathTypes.UNPASSABLE_RAIL, 0.0F);
		this.setPathfindingMalus(BlockPathTypes.DAMAGE_OTHER, 8.0F);
		this.setPathfindingMalus(BlockPathTypes.POWDER_SNOW, 8.0F);
		this.setPathfindingMalus(BlockPathTypes.LAVA, 8.0F);
		this.setPathfindingMalus(BlockPathTypes.DAMAGE_FIRE, 0.0F);
		this.setPathfindingMalus(BlockPathTypes.DANGER_FIRE, 0.0F);
	}

	@Override
	public void tick() {
		super.tick();

		if (this.level().isClientSide()) {
			setupAnimationStates();
		}
	}

	private void setupAnimationStates() {
		if (this.idleAnimationTimeout <= 0) {
			this.idleAnimationTimeout = this.random.nextInt(40) + 80;
			// Choose to either sniff or idle.
			if (this.random.nextInt(4) == 1) {
				this.sniffAnimationState.start(this.tickCount);
			} else {
				this.idleAnimationState.start(this.tickCount);
			}
		} else {
			--this.idleAnimationTimeout;
		}

		if (this.isAttacking() && attackAnimationTimeout <= 0) {
			// Stop sniffing or idling.
			if (this.idleAnimationState.isStarted())
				this.idleAnimationState.stop();
			if (this.sniffAnimationState.isStarted())
				this.sniffAnimationState.stop();
			// Start attacking.
			this.attackAnimationTimeout = 20;
			attackAnimationState.start(this.tickCount);
		} else {
			--attackAnimationTimeout;
		}

		if (!this.isAttacking()) {
			this.attackAnimationState.stop();
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
		// Stops the idle animations
		if (this.getDeltaMovement().length() > 0.2) {
			if (this.idleAnimationState.isStarted())
				this.idleAnimationState.stop();
		}

		this.walkAnimation.update(f, 0.2f);
	}

	public static AttributeSupplier.Builder createAttributes() {
		return Monster.createMobAttributes().add(Attributes.MAX_HEALTH, 16).add(Attributes.FOLLOW_RANGE, 64)
				.add(Attributes.MOVEMENT_SPEED, 0.3).add(Attributes.ATTACK_DAMAGE, 6).add(Attributes.ARMOR, 6)
				.add(Attributes.ARMOR_TOUGHNESS, 2).add(Attributes.KNOCKBACK_RESISTANCE, 0.5)
				.add(Attributes.ATTACK_SPEED, 2);
	}

	@Override
	public MobType getMobType() {
		return MobType.ARTHROPOD;
	}

	@Override
	public boolean dampensVibrations() {
		return true;
	}

	@Override
	public boolean doHurtTarget(Entity entity) {
		if (!super.doHurtTarget(entity)) {
			return false;
		} else {
			if (entity instanceof LivingEntity) {
				((LivingEntity) entity).addEffect(new MobEffectInstance(MobEffects.DARKNESS, 100), this);
			}

			return true;
		}
	}

	@Override
	public void registerGoals() {
		this.goalSelector.addGoal(1, new FloatGoal(this));
		this.goalSelector.addGoal(1, new ClimbOnTopOfPowderSnowGoal(this, this.level()));
		this.targetSelector.addGoal(1, (new HurtByTargetGoal(this)).setAlertOthers());
		this.goalSelector.addGoal(2, new MoleAttackGoal(this, 1.0D, true));
		this.goalSelector.addGoal(4, new FleeSunGoal(this, 1.0D));
		this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0D));
		this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 8.0F));
		this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));

		// Adds attack goals for pretty much everything that isn't silent.
		this.targetSelector.addGoal(2,
				new NearestAttackableTargetGoal<>(this, LivingEntity.class, 0, false, false, LOUD_ENTITY_SELECTOR));
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return SoundEvents.WARDEN_AMBIENT;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource p_33549_) {
		return SoundEvents.SILVERFISH_HURT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return SoundEvents.SILVERFISH_DEATH;
	}

	@Override
	protected void playStepSound(BlockPos pos, BlockState state) {
		this.playSound(SoundEvents.SILVERFISH_STEP, 0.15F, 1.0F);
	}

	@Override
	public double getMyRidingOffset() {
		return 0.1D;
	}

	@Override
	protected float getStandingEyeHeight(Pose p_33540_, EntityDimensions p_33541_) {
		return 0.25F;
	}

	@Override
	public float getSoundVolume() {
		return 1.0f;
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(ATTACKING, false);
	}

	public void setAttacking(boolean attacking) {
		this.entityData.set(ATTACKING, attacking);
	}

	public boolean isAttacking() {
		return this.entityData.get(ATTACKING);
	}
}
