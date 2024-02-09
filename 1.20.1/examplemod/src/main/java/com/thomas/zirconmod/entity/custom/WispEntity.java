package com.thomas.zirconmod.entity.custom;

import java.util.Map;

import javax.annotation.Nullable;

import com.thomas.zirconmod.effect.ModEffects;
import com.thomas.zirconmod.entity.ai.WispFindHomeGoal;
import com.thomas.zirconmod.entity.ai.WispGoHomeWhenThunderingGoal;
import com.thomas.zirconmod.entity.ai.WithinBoundsFlyingGoal;
import com.thomas.zirconmod.entity.variant.WoodGolemVariant;
import com.thomas.zirconmod.item.ModItems;
import com.thomas.zirconmod.util.Utilities;
import com.thomas.zirconmod.villager.ModVillagers;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtTradingPlayerGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.SpectralArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.item.trading.MerchantOffers;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class WispEntity extends AbstractVillager {
	@Nullable
	private BlockPos homeTarget;
	private int soldOutTrades = 0;
	private static final double TELEPORT_HOME_DISTANCE = 48;
	private static final int MAX_SKILL = 5;
	public static final EntityDataAccessor<Integer> DATA_ID_TYPE_VARIANT = SynchedEntityData.defineId(WispEntity.class,
			EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> SKILL_LEVEL = SynchedEntityData.defineId(WispEntity.class,
			EntityDataSerializers.INT);

	public final AnimationState idleAnimationState = new AnimationState();
	private int idleAnimationTimeout = 0;

	private static final Map<Integer, VillagerProfession> WISP_PROFESSIONS = Map.of(0, ModVillagers.GEMSMITH.get(), 1,
			ModVillagers.ARCHITECT.get(), 2, ModVillagers.BOTANIST.get(), 3, ModVillagers.CHIEF.get(), 4,
			ModVillagers.SCHOLAR.get(), 5, ModVillagers.TINKERER.get());

	public WispEntity(EntityType<? extends AbstractVillager> p_35267_, Level p_35268_) {
		super(p_35267_, p_35268_);
		this.moveControl = new FlyingMoveControl(this, 20, true);
		this.setNoGravity(true);
	}

	/*
	 * protected void registerGoals() { this.goalSelector.addGoal(0, new
	 * FloatGoal(this)); this.goalSelector.addGoal(1, new
	 * TradeWithPlayerGoal(this)); this.goalSelector.addGoal(1, new
	 * LookAtTradingPlayerGoal(this)); this.goalSelector.addGoal(2, new
	 * WispEntity.WanderToPositionGoal(this, 2.0D, 0.35D));
	 * this.goalSelector.addGoal(4, new MoveTowardsRestrictionGoal(this, 0.35D));
	 * this.goalSelector.addGoal(8, new WaterAvoidingRandomStrollGoal(this, 0.35D));
	 * this.goalSelector.addGoal(9, new InteractGoal(this, Player.class, 3.0F,
	 * 1.0F)); this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Mob.class,
	 * 8.0F)); }
	 */

	@Override
	protected void registerGoals() {

		this.goalSelector.addGoal(0, new FloatGoal(this));
		this.goalSelector.addGoal(1, new LookAtTradingPlayerGoal(this));

		this.goalSelector.addGoal(2, new WispFindHomeGoal(this, 1.5));
		this.goalSelector.addGoal(3, new WispGoHomeWhenThunderingGoal(this, 2, 1));
		this.goalSelector.addGoal(4, new WithinBoundsFlyingGoal(this, 1.0, -48));
	}

	@Override
	protected float getJumpPower() {
		return 0;// 0.42F * this.getBlockJumpFactor() + this.getJumpBoostPower();
	}

	@Override
	protected PathNavigation createNavigation(Level p_218342_) {
		FlyingPathNavigation navigation = new FlyingPathNavigation(this, p_218342_);
		navigation.setCanOpenDoors(false);
		navigation.setCanFloat(true);
		navigation.setCanPassDoors(true);
		return navigation;
	}

	@SuppressWarnings("resource")
	@Override
	public void tick() {
		super.tick();

		// Keeps movement from exceeding a certain value.
		if (this.getDeltaMovement().length() > this.getFlyingSpeed() * 2) {
			this.setDeltaMovement(this.getDeltaMovement().normalize().scale(this.getFlyingSpeed() * 2));
		}

		// Teleports home if too far away.
		if (this.getHomeTarget() != null && this
				.distanceToSqr(this.getHomeTarget().getCenter()) > TELEPORT_HOME_DISTANCE * TELEPORT_HOME_DISTANCE) {

			Vec3 teleportPos = Utilities.getNearbyRespawn(this.level(), this.getHomeTarget(), 8).getCenter();

			this.teleportTo(teleportPos.x, teleportPos.y, teleportPos.z);

		}

		// Regenerates health.
		if (!this.level().isClientSide && this.isAlive() && this.tickCount % 10 == 0) {
			this.heal(1.0F);
		}

		// If below the world limit, add levitation!
		if (this.getEyePosition().y < this.level().getMinBuildHeight() + 8) {
			this.addEffect(new MobEffectInstance(MobEffects.LEVITATION, 5, 15));
		}

		// Restocks six times a day.
		if (this.level().getDayTime() % 8000 == 0) {
			this.restock();
		}
		
		// Handles animations
		if (this.level().isClientSide()) {
			setupAnimationStates();
		}
	}

	// Restocks the Wisp.
	// If enough trades have been sold out, levels up the Wisp.
	private void restock() {
		int level = this.getSkillLevel();
		for (MerchantOffer merchantOffer : this.getOffers()) {
			if (merchantOffer.isOutOfStock()) {
				soldOutTrades++;
			}
			merchantOffer.resetUses();
		}
		if (soldOutTrades > (level) && level < MAX_SKILL) {
			// System.out.println("Updating skill!");
			this.setSkillLevel(level + 1);
			this.updateTrades();
			// System.out.println("New skill level: " + this.getSkillLevel());
			this.soldOutTrades -= level + 1;
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

	private void setupAnimationStates() {
		if (this.idleAnimationTimeout <= 0) {
			this.idleAnimationTimeout = this.random.nextInt(40) + 80;
			this.idleAnimationState.start(this.tickCount);
		} else {
			--this.idleAnimationTimeout;
		}
	}

	public static AttributeSupplier.Builder createAttributes() {
		return Animal.createLivingAttributes().add(Attributes.MAX_HEALTH, 20D).add(Attributes.FOLLOW_RANGE, 24D)
				.add(Attributes.FLYING_SPEED, 0.25D).add(Attributes.ARMOR_TOUGHNESS, 0.1f)
				.add(Attributes.ATTACK_KNOCKBACK, 0.5f).add(Attributes.ATTACK_DAMAGE, 2f);
	}

	@Override
	public boolean hurt(DamageSource damageSource, float f) {
		Entity entity = damageSource.getDirectEntity();
		// Wisps are immune to projectiles, unless certain conditions are true.
		if (entity instanceof Projectile) {
			// Not immune to spectral arrows.
			if (entity instanceof SpectralArrow)
				return super.hurt(damageSource, f);
			// Not immune if it has amethyst glow.
			else if (this.hasEffect(ModEffects.CITRINE_GLOW.get()))
				return super.hurt(damageSource, f);
			// Otherwise, it is immune
			else
				return false;
		}
		// If it isn't an arrow, is susceptible.
		return super.hurt(damageSource, f);
	}

//	@Override
//	public void onRemovedFromWorld() {
//		super.onRemovedFromWorld();
//		// Sets the home to being unoccupied
//		BlockPos homePos = this.getHome();
//		// Is the home position set?
//		if (homePos == null) {
//			return;
//		}
//		
//		// Is there a bed there?
//		Level level = this.level();
//		if (level.getBlockState(homePos).is(ModBlocks.WISP_BED.get())) {
//			// Sets the bed to unoccupied.
//			level.setBlockAndUpdate(homePos, ModBlocks.WISP_BED.get().defaultBlockState());
//		}
//	}

	@Override
	public boolean isInvulnerableTo(DamageSource d) {
		return super.isInvulnerableTo(d) || d.is(DamageTypeTags.IS_FALL);
	}

	@Override
	protected float getStandingEyeHeight(Pose pose, EntityDimensions dims) {
		return this.isBaby() ? 0.25F : 0.5F;
	}

	@Nullable
	public AgeableMob getBreedOffspring(ServerLevel p_150046_, AgeableMob p_150047_) {
		return null;
	}

	@Override
	protected void playStepSound(BlockPos pos, BlockState state) {
		this.playSound(SoundEvents.SILVERFISH_STEP, 0.15F, 1.0F);
	}

	public boolean showProgressBar() {
		return false;
	}

	@SuppressWarnings("resource")
	public InteractionResult mobInteract(Player p_35856_, InteractionHand p_35857_) {
		ItemStack itemstack = p_35856_.getItemInHand(p_35857_);
		// System.out.println("This wisp's home is: " + this.getHome());

		// The villager will not trade if it is thundering.
		if (!this.level().isThundering() && !itemstack.is(ModItems.WISP_SPAWN_EGG.get()) && this.isAlive()
				&& !this.isTrading() && !this.isBaby()) {
			if (p_35857_ == InteractionHand.MAIN_HAND) {
				p_35856_.awardStat(Stats.TALKED_TO_VILLAGER);
			}

			if (this.getOffers().isEmpty()) {
				return InteractionResult.sidedSuccess(this.level().isClientSide);
			} else {
				if (!this.level().isClientSide) {
					// Sets the wisp's price differential.
					// merchantOffer.setSpecialPriceDiff(level);
					// Opens trading.
					this.setTradingPlayer(p_35856_);
					this.openTradingScreen(p_35856_, this.getDisplayName(), 1);
				}

				return InteractionResult.sidedSuccess(this.level().isClientSide);
			}
		} else {
			return super.mobInteract(p_35856_, p_35857_);
		}
	}

	protected void updateTrades() {

		VillagerProfession profession = this.getProfession();

		int skill = this.getSkillLevel();
		// System.out.println("Updating trades for skill level " +
		// this.getSkillLevel());
		Int2ObjectMap<VillagerTrades.ItemListing[]> professionTrades = VillagerTrades.TRADES.get(profession);

		if (professionTrades != null) {
			VillagerTrades.ItemListing[] levelTrades = professionTrades.get(skill);
			// System.out.println("New trades are: " + levelTrades + ", there are " +
			// levelTrades.length + " of them.");
			if (levelTrades != null) {
				MerchantOffers merchantoffers = this.getOffers();
				this.addOffersFromItemListings(merchantoffers, levelTrades, 2);
			}
		}
	}

	public void addAdditionalSaveData(CompoundTag tag) {
		super.addAdditionalSaveData(tag);
		tag.putInt("Type", this.getTypeVariant());
		tag.putInt("Skill", this.getSkillLevel());
		tag.putInt("SoldOutTrades", this.soldOutTrades);

		// tag.putInt("HomeX", this.getHomePos());

		if (this.homeTarget != null) {
			tag.put("WanderTarget", NbtUtils.writeBlockPos(this.homeTarget));
		}

	}

	public void readAdditionalSaveData(CompoundTag tag) {
		super.readAdditionalSaveData(tag);

		if (tag.contains("WanderTarget")) {
			this.homeTarget = NbtUtils.readBlockPos(tag.getCompound("WanderTarget"));
		}

		this.entityData.set(DATA_ID_TYPE_VARIANT, tag.getInt("Type"));

		this.entityData.set(SKILL_LEVEL, tag.getInt("Skill"));

		if (tag.contains("SoldOutTrades")) {
			this.soldOutTrades = tag.getInt("SoldOutTrades");
		} else {
			this.soldOutTrades = 0;
		}

		this.setAge(Math.max(0, this.getAge()));
	}

	public static VillagerProfession typeToProfession(int type) {

		if (WISP_PROFESSIONS.containsKey(type))
			return WISP_PROFESSIONS.get(type);
		else
			return ModVillagers.GEMSMITH.get();
	}

	public VillagerProfession getProfession() {
		return typeToProfession(this.getTypeVariant());
	}

	public boolean removeWhenFarAway(double p_35886_) {
		return false;
	}

	protected void rewardTradeXp(MerchantOffer p_35859_) {
		if (p_35859_.shouldRewardExp()) {
			int i = 3 + this.random.nextInt(4);
			this.level()
					.addFreshEntity(new ExperienceOrb(this.level(), this.getX(), this.getY() + 0.5D, this.getZ(), i));
		}

	}

	protected SoundEvent getAmbientSound() {
		return this.isTrading() ? SoundEvents.NOTE_BLOCK_BELL.get() : SoundEvents.NOTE_BLOCK_CHIME.get();
	}

	protected SoundEvent getHurtSound(DamageSource p_218369_) {
		return SoundEvents.ALLAY_HURT;
	}

	protected SoundEvent getDeathSound() {
		return SoundEvents.ALLAY_DEATH;
	}

	protected float getSoundVolume() {
		return 0.4F;
	}

	protected SoundEvent getDrinkingSound(ItemStack p_35865_) {
		return p_35865_.is(Items.MILK_BUCKET) ? SoundEvents.WANDERING_TRADER_DRINK_MILK
				: SoundEvents.WANDERING_TRADER_DRINK_POTION;
	}

	protected SoundEvent getTradeUpdatedSound(boolean p_35890_) {
		return p_35890_ ? SoundEvents.ALLAY_ITEM_GIVEN : SoundEvents.ALLAY_ITEM_TAKEN;
	}

	public SoundEvent getNotifyTradeSound() {
		return SoundEvents.NOTE_BLOCK_CHIME.get();
	}

	@SuppressWarnings("resource")
	public void aiStep() {
		super.aiStep();
		if (!this.level().isClientSide) {
			// this.maybeDespawn();
		}
	}

	public void setHomeTarget(@Nullable BlockPos p_35884_) {
		this.homeTarget = p_35884_;
	}

	@Nullable
	public BlockPos getHomeTarget() {
		return this.homeTarget;
	}

	public int getSkillLevel() {
		int level = this.entityData.get(SKILL_LEVEL);
		if (level < 1) {
			this.setSkillLevel(1);
			return 1;
		}

		return level;
	}

	public void setSkillLevel(int skillLevel) {
		this.entityData.set(SKILL_LEVEL, Utilities.max(skillLevel, 1));
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(DATA_ID_TYPE_VARIANT, 0);
		this.entityData.define(SKILL_LEVEL, 1);
	}

	public int getTypeVariant() {
		return this.entityData.get(DATA_ID_TYPE_VARIANT);
	}

	public void setTypeVariant(WoodGolemVariant variant) {
		this.entityData.set(DATA_ID_TYPE_VARIANT, variant.getId() & 255);
	}

}
