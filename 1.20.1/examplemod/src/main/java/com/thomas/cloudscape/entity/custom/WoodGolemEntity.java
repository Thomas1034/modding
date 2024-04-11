package com.thomas.cloudscape.entity.custom;

import com.thomas.cloudscape.entity.ai.WoodGolemAttackGoal;
import com.thomas.cloudscape.entity.variant.WoodGolemVariant;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.ClimbOnTopOfPowderSnowGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.GolemRandomStrollInVillageGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MoveBackToVillageGoal;
import net.minecraft.world.entity.ai.goal.MoveTowardsTargetGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.AbstractGolem;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.NaturalSpawner;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.Tags;

public class WoodGolemEntity extends AbstractGolem {
	public static final EntityDataAccessor<Integer> DATA_ID_TYPE_VARIANT = SynchedEntityData
			.defineId(WoodGolemEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Boolean> ATTACKING = SynchedEntityData.defineId(WoodGolemEntity.class,
			EntityDataSerializers.BOOLEAN);
	
	public final AnimationState attackAnimationState = new AnimationState();
	public int attackAnimationTimeout = 0;
	
	public WoodGolemEntity(EntityType<? extends AbstractGolem> p_27508_, Level p_27509_) {
		super(p_27508_, p_27509_);
		this.getNavigation().setCanFloat(true);
	}

	private void setupAnimationStates() {
		if (this.isAttacking() && attackAnimationTimeout <= 0) {
			// Start attacking.
			this.attackAnimationTimeout = 10;
			attackAnimationState.start(this.tickCount);
		} else {
			--attackAnimationTimeout;
		}

		if (!this.isAttacking()) {
			this.attackAnimationState.stop();
		}

	}
	
	@Override
	public void tick() {
		super.tick();

		if (this.level().isClientSide()) {
			setupAnimationStates();
		}
	}
	
	
	protected void registerGoals() {
		this.goalSelector.addGoal(1, new FloatGoal(this));
		this.goalSelector.addGoal(1, new ClimbOnTopOfPowderSnowGoal(this, this.level()));
		this.goalSelector.addGoal(1, new WoodGolemAttackGoal(this, 1.0D, true));
		
		this.goalSelector.addGoal(2, new MoveTowardsTargetGoal(this, 0.9D, 32.0F));
		this.goalSelector.addGoal(2, new MoveBackToVillageGoal(this, 0.6D, false));
		this.goalSelector.addGoal(4, new GolemRandomStrollInVillageGoal(this, 0.6D));
		this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 6.0F));
		this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
		this.targetSelector.addGoal(2, new HurtByTargetGoal(this));
		this.targetSelector.addGoal(3,
				new NearestAttackableTargetGoal<>(this, Mob.class, 5, false, false, (p_28879_) -> {
					return p_28879_ instanceof Enemy && !(p_28879_ instanceof Creeper);
				}));
	}

	public static AttributeSupplier.Builder createAttributes() {
		return AbstractGolem.createMobAttributes().add(Attributes.MAX_HEALTH, 40).add(Attributes.FOLLOW_RANGE, 32)
				.add(Attributes.MOVEMENT_SPEED, 0.25).add(Attributes.ATTACK_DAMAGE, 4).add(Attributes.ARMOR, 0)
				.add(Attributes.ARMOR_TOUGHNESS, 0).add(Attributes.KNOCKBACK_RESISTANCE, 0.1)
				.add(Attributes.ATTACK_SPEED, 0.5);
	}

	protected int decreaseAirSupply(int p_28882_) {
		return p_28882_;
	}

	protected void doPush(Entity p_28839_) {
		// If the entity is an enemy and the golem is attacking, damage it.
		if (p_28839_ instanceof Enemy && this.isAttacking())
			this.doHurtTarget(p_28839_);
		
		if (p_28839_ instanceof Enemy && !(p_28839_ instanceof Creeper) && this.getRandom().nextInt(20) == 0) {
			this.setTarget((LivingEntity) p_28839_);
		}
		super.doPush(p_28839_);
	}

	public void aiStep() {
		super.aiStep();
		if (this.getDeltaMovement().horizontalDistanceSqr() > (double) 2.5000003E-7F && this.random.nextInt(5) == 0) {
			int i = Mth.floor(this.getX());
			int j = Mth.floor(this.getY() - (double) 0.2F);
			int k = Mth.floor(this.getZ());
			BlockPos pos = new BlockPos(i, j, k);
			BlockState blockstate = this.level().getBlockState(pos);
			if (!blockstate.isAir()) {
				this.level().addParticle(new BlockParticleOption(ParticleTypes.BLOCK, blockstate).setPos(pos),
						this.getX() + ((double) this.random.nextFloat() - 0.5D) * (double) this.getBbWidth(),
						this.getY() + 0.1D,
						this.getZ() + ((double) this.random.nextFloat() - 0.5D) * (double) this.getBbWidth(),
						4.0D * ((double) this.random.nextFloat() - 0.5D), 0.5D,
						((double) this.random.nextFloat() - 0.5D) * 4.0D);
			}
		}
	}

	public boolean canAttackType(EntityType<?> p_28851_) {
		if (p_28851_ == EntityType.PLAYER) {
			return false;
		} else {
			return p_28851_ == EntityType.CREEPER ? false : super.canAttackType(p_28851_);
		}
	}

	private float getAttackDamage() {
		return (float) this.getAttributeValue(Attributes.ATTACK_DAMAGE);
	}

	public boolean doHurtTarget(Entity p_28837_) {
		this.level().broadcastEntityEvent(this, (byte) 4);
		float f = this.getAttackDamage();
		float f1 = (int) f > 0 ? f / 2.0F + (float) this.random.nextInt((int) f) : f;
		boolean flag = p_28837_.hurt(damageSources().mobAttack(this), f1);
		if (flag) {
			double d2;
			if (p_28837_ instanceof LivingEntity) {
				LivingEntity livingentity = (LivingEntity) p_28837_;
				d2 = livingentity.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE);
			} else {
				d2 = 0.0D;
			}

			double d0 = d2;
			double d1 = Math.max(0.0D, 0.25D - d0);
			p_28837_.setDeltaMovement(p_28837_.getDeltaMovement().add(0.0D, (double) 0.4F * d1, 0.0D));
			this.doEnchantDamageEffects(this, p_28837_);
		}

		this.playSound(SoundEvents.FENCE_GATE_CLOSE, 1.0F, 1.0F);
		return flag;
	}

	protected SoundEvent getHurtSound(DamageSource p_28872_) {
		return SoundEvents.LADDER_BREAK;
	}

	protected SoundEvent getDeathSound() {
		return SoundEvents.WOOD_BREAK;
	}

	@SuppressWarnings("resource")
	protected InteractionResult mobInteract(Player p_28861_, InteractionHand p_28862_) {
		ItemStack itemstack = p_28861_.getItemInHand(p_28862_);
		if (itemstack.getTags().filter(tag -> tag == Tags.Items.FENCES_WOODEN).count() == 0) {
			return InteractionResult.PASS;
		} else {
			float f = this.getHealth();
			this.heal(5.0F);
			if (this.getHealth() == f) {
				return InteractionResult.PASS;
			} else {
				float f1 = 1.0F + (this.random.nextFloat() - this.random.nextFloat()) * 0.2F;
				this.playSound(SoundEvents.LADDER_PLACE, 1.0F, f1);
				if (!p_28861_.getAbilities().instabuild) {
					itemstack.shrink(1);
				}
				return InteractionResult.sidedSuccess(this.level().isClientSide);
			}
		}
	}

	protected void playStepSound(BlockPos p_28864_, BlockState p_28865_) {
		this.playSound(SoundEvents.LADDER_STEP, 1.0F, 1.0F);
	}

	public void die(DamageSource p_28846_) {
		super.die(p_28846_);
	}

	public boolean checkSpawnObstruction(LevelReader p_28853_) {
		BlockPos blockpos = this.blockPosition();
		BlockPos blockpos1 = blockpos.below();
		BlockState blockstate = p_28853_.getBlockState(blockpos1);
		if (!blockstate.entityCanStandOn(p_28853_, blockpos1, this)) {
			return false;
		} else {
			for (int i = 1; i < 3; ++i) {
				BlockPos blockpos2 = blockpos.above(i);
				BlockState blockstate1 = p_28853_.getBlockState(blockpos2);
				if (!NaturalSpawner.isValidEmptySpawnBlock(p_28853_, blockpos2, blockstate1,
						blockstate1.getFluidState(), EntityType.IRON_GOLEM)) {
					return false;
				}
			}
			return NaturalSpawner.isValidEmptySpawnBlock(p_28853_, blockpos, p_28853_.getBlockState(blockpos),
					Fluids.EMPTY.defaultFluidState(), EntityType.IRON_GOLEM) && p_28853_.isUnobstructed(this);
		}
	}

	public Vec3 getLeashOffset() {
		return new Vec3(0.0D, (double) (0.875F * this.getEyeHeight()), (double) (this.getBbWidth() * 0.4F));
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(DATA_ID_TYPE_VARIANT, 0);
		this.entityData.define(ATTACKING, false);
	}

	@Override
	public void readAdditionalSaveData(CompoundTag tag) {
		super.readAdditionalSaveData(tag);
		this.entityData.set(DATA_ID_TYPE_VARIANT, tag.getInt("Variant"));
	}

	@Override
	public void addAdditionalSaveData(CompoundTag tag) {
		super.addAdditionalSaveData(tag);
		tag.putInt("Variant", this.getTypeVariant());
	}

	public int getTypeVariant() {
		return this.entityData.get(DATA_ID_TYPE_VARIANT);
	}

	public void setTypeVariant(WoodGolemVariant variant) {
		this.entityData.set(DATA_ID_TYPE_VARIANT, variant.getId() & 255);
	}

	public void setAttacking(boolean attacking) {
		this.entityData.set(ATTACKING, attacking);
	}

	public boolean isAttacking() {
		return this.entityData.get(ATTACKING);
	}
}
