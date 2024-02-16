package com.thomas.zirconmod.entity.custom;

import javax.annotation.Nullable;

import com.thomas.zirconmod.block.ModBlocks;
import com.thomas.zirconmod.effect.ModEffects;
import com.thomas.zirconmod.entity.ModEntities;
import com.thomas.zirconmod.entity.ai.NimbulaStayWithinBoundsGoal;
import com.thomas.zirconmod.entity.ai.WithinBoundsFlyingGoal;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.FollowParentGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.FlyingAnimal;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.SpectralArrow;
import net.minecraft.world.entity.projectile.ThrownTrident;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class NimbulaEntity extends Animal implements FlyingAnimal {

	public final AnimationState idleAnimationState = new AnimationState();
	private int idleAnimationTimeout = 0;

	public NimbulaEntity(EntityType<? extends Animal> pEntityType, Level pLevel) {
		super(pEntityType, pLevel);
		this.moveControl = new FlyingMoveControl(this, 20, true);
		this.setNoGravity(true);
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

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(0, new FloatGoal(this));
		this.goalSelector.addGoal(1, new NimbulaStayWithinBoundsGoal(this, 0.5, -48));
		this.goalSelector.addGoal(1, new BreedGoal(this, 1.15D));
		this.goalSelector.addGoal(2, new TemptGoal(this, 1.2D, Ingredient.of(Items.POTION), false));

		this.goalSelector.addGoal(3, new FollowParentGoal(this, 1.1D));

		this.goalSelector.addGoal(4, new WithinBoundsFlyingGoal(this, 1D, -48));
	}

	protected PathNavigation createNavigation(Level p_218342_) {
		FlyingPathNavigation flyingpathnavigation = new FlyingPathNavigation(this, p_218342_);
		flyingpathnavigation.setCanFloat(true);
		return flyingpathnavigation;

	}

	public static AttributeSupplier.Builder createAttributes() {
		return Animal.createLivingAttributes().add(Attributes.MAX_HEALTH, 20D).add(Attributes.FOLLOW_RANGE, 24D)
				.add(Attributes.FLYING_SPEED, 0.25D).add(Attributes.ARMOR_TOUGHNESS, 0.1f)
				.add(Attributes.ATTACK_KNOCKBACK, 0.5f).add(Attributes.ATTACK_DAMAGE, 2f);
	}

	// When the entity is hurt, check if it takes damage from that source. If not,
	// do nothing.
	public boolean hurt(DamageSource damageSource, float f) {
		Entity entity = damageSource.getDirectEntity();
		// Nimbulas are immune to projectiles, unless certain conditions are true.
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

	// Override the breeding event. Instead of placing a child, it places a polyp
	// that will seed clouds and release more nimbulas.
	@Override
	public void spawnChildFromBreeding(ServerLevel level, Animal mate) {
		AgeableMob ageablemob = this.getBreedOffspring(level, mate);
		final net.minecraftforge.event.entity.living.BabyEntitySpawnEvent event = new net.minecraftforge.event.entity.living.BabyEntitySpawnEvent(
				this, mate, ageablemob);
		final boolean cancelled = net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(event);
		if (cancelled) {
			// Reset the "inLove" state for the animals
			this.setAge(6000);
			mate.setAge(6000);
			this.resetLove();
			mate.resetLove();
			return;
		}

		// Act like you're going to add the child to the level to get the side effects
		// (achievements, xp, etc), but don't actually.
		if (ageablemob != null) {
			ageablemob.setBaby(true);
			ageablemob.moveTo(this.getX(), this.getY(), this.getZ(), 0.0F, 0.0F);
			this.finalizeSpawnChildFromBreeding(level, mate, ageablemob);
			// We don't add the child.
			// level.addFreshEntityWithPassengers(ageablemob);
			// Instead, set the block at this position to a polyp.
			BlockPos pos = this.blockPosition();
			if (level.getBlockState(pos).isAir()) {
				level.setBlock(pos, ModBlocks.NIMBULA_POLYP.get().defaultBlockState(), 2);
			}
		}
	}

	@Override
	public void thunderHit(ServerLevel level, LightningBolt bolt) {
		TempestEntity tempest = new TempestEntity(ModEntities.TEMPEST_ENTITY.get(), level);
        if (tempest != null) {
        	this.remove(RemovalReason.DISCARDED);
        	tempest.moveTo(this.position());
        	tempest.setDeltaMovement(this.getDeltaMovement());
        	tempest.setXRot(this.getXRot());
        	tempest.setYRot(this.getYRot());
        	level.addFreshEntity(tempest);
        }
	}

	@Override
	public boolean isInvulnerableTo(DamageSource d) {
		return super.isInvulnerableTo(d) || d.is(DamageTypeTags.IS_FALL);
	}

	@Override
	protected float getStandingEyeHeight(Pose pose, EntityDimensions dims) {
		return this.isBaby() ? 0.5F : 1.0F;
	}

	@Nullable
	@Override
	public AgeableMob getBreedOffspring(ServerLevel pLevel, AgeableMob pOtherParent) {
		return ModEntities.NIMBULA_ENTITY.get().create(pLevel);

	}

	@Override
	public boolean isFood(ItemStack pStack) {
		return pStack.is(Items.POTION);
	}

	@Nullable
	@Override
	protected SoundEvent getAmbientSound() {
		return SoundEvents.AMETHYST_BLOCK_RESONATE;
	}

	@Nullable
	@Override
	protected SoundEvent getHurtSound(DamageSource pDamageSource) {
		return SoundEvents.ALLAY_HURT;
	}

	@Nullable
	@Override
	protected SoundEvent getDeathSound() {
		return SoundEvents.ALLAY_DEATH;
	}

	@Override
	public boolean isFlying() {
		return true;
	}

	@Override
	public boolean removeWhenFarAway(double p_218384_) {
		return false;
	}
}