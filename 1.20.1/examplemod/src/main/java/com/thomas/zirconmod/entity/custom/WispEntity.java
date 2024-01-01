package com.thomas.zirconmod.entity.custom;

import java.util.EnumSet;
import java.util.Map;

import javax.annotation.Nullable;

import com.thomas.zirconmod.entity.variant.WoodGolemVariant;
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
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.InteractGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.LookAtTradingPlayerGoal;
import net.minecraft.world.entity.ai.goal.MoveTowardsRestrictionGoal;
import net.minecraft.world.entity.ai.goal.TradeWithPlayerGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.item.trading.MerchantOffers;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class WispEntity extends AbstractVillager {
	@Nullable
	private BlockPos wanderTarget;
	private int soldOutTrades = 0;
	private static final int MAX_SKILL = 5;
	public static final EntityDataAccessor<Integer> DATA_ID_TYPE_VARIANT = SynchedEntityData.defineId(WispEntity.class,
			EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> SKILL_LEVEL = SynchedEntityData.defineId(WispEntity.class,
			EntityDataSerializers.INT);

	private static final Map<Integer, VillagerProfession> WISP_PROFESSIONS = Map.of(0, ModVillagers.GEMSMITH.get(), 1,
			ModVillagers.ARCHITECT.get(), 2, ModVillagers.BOTANIST.get(), 3, ModVillagers.CHIEF.get(), 4,
			ModVillagers.SCHOLAR.get(), 5, ModVillagers.TINKERER.get());

	public WispEntity(EntityType<? extends AbstractVillager> p_35267_, Level p_35268_) {
		super(p_35267_, p_35268_);
	}

	protected void registerGoals() {
		this.goalSelector.addGoal(0, new FloatGoal(this));
		this.goalSelector.addGoal(1, new TradeWithPlayerGoal(this));
		this.goalSelector.addGoal(1, new LookAtTradingPlayerGoal(this));
		this.goalSelector.addGoal(2, new WispEntity.WanderToPositionGoal(this, 2.0D, 0.35D));
		this.goalSelector.addGoal(4, new MoveTowardsRestrictionGoal(this, 0.35D));
		this.goalSelector.addGoal(8, new WaterAvoidingRandomStrollGoal(this, 0.35D));
		this.goalSelector.addGoal(9, new InteractGoal(this, Player.class, 3.0F, 1.0F));
		this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Mob.class, 8.0F));
	}

	@Override
	public void tick() {

		super.tick();
		// Restocks six times a day.
		if (this.level().getDayTime() % 8000 == 0) {
			this.restock();
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

	public static AttributeSupplier.Builder createAttributes() {
		return Animal.createLivingAttributes().add(Attributes.MAX_HEALTH, 20D).add(Attributes.FOLLOW_RANGE, 24D)
				.add(Attributes.MOVEMENT_SPEED, 0.25D).add(Attributes.ARMOR_TOUGHNESS, 0.1f)
				.add(Attributes.ATTACK_KNOCKBACK, 0.5f).add(Attributes.ATTACK_DAMAGE, 2f);
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
		if (!itemstack.is(Items.VILLAGER_SPAWN_EGG) && this.isAlive() && !this.isTrading() && !this.isBaby()) {
			if (p_35857_ == InteractionHand.MAIN_HAND) {
				p_35856_.awardStat(Stats.TALKED_TO_VILLAGER);
			}

			if (this.getOffers().isEmpty()) {
				return InteractionResult.sidedSuccess(this.level().isClientSide);
			} else {
				if (!this.level().isClientSide) {
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
		System.out.println("Updating trades for skill level " + this.getSkillLevel());
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

		if (this.wanderTarget != null) {
			tag.put("WanderTarget", NbtUtils.writeBlockPos(this.wanderTarget));
		}

	}

	public void readAdditionalSaveData(CompoundTag tag) {
		super.readAdditionalSaveData(tag);

		if (tag.contains("WanderTarget")) {
			this.wanderTarget = NbtUtils.readBlockPos(tag.getCompound("WanderTarget"));
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
		return this.isTrading() ? SoundEvents.WANDERING_TRADER_TRADE : SoundEvents.WANDERING_TRADER_AMBIENT;
	}

	protected SoundEvent getHurtSound(DamageSource p_35870_) {
		return SoundEvents.WANDERING_TRADER_HURT;
	}

	protected SoundEvent getDeathSound() {
		return SoundEvents.WANDERING_TRADER_DEATH;
	}

	protected SoundEvent getDrinkingSound(ItemStack p_35865_) {
		return p_35865_.is(Items.MILK_BUCKET) ? SoundEvents.WANDERING_TRADER_DRINK_MILK
				: SoundEvents.WANDERING_TRADER_DRINK_POTION;
	}

	protected SoundEvent getTradeUpdatedSound(boolean p_35890_) {
		return p_35890_ ? SoundEvents.WANDERING_TRADER_YES : SoundEvents.WANDERING_TRADER_NO;
	}

	public SoundEvent getNotifyTradeSound() {
		return SoundEvents.WANDERING_TRADER_YES;
	}

	@SuppressWarnings("resource")
	public void aiStep() {
		super.aiStep();
		if (!this.level().isClientSide) {
			// this.maybeDespawn();
		}
	}

	public void setWanderTarget(@Nullable BlockPos p_35884_) {
		this.wanderTarget = p_35884_;
	}

	@Nullable
	BlockPos getWanderTarget() {
		return this.wanderTarget;
	}

	public int getSkillLevel() {
		int level = this.entityData.get(SKILL_LEVEL);
		if (level < 1)
		{
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

	class WanderToPositionGoal extends Goal {
		final WispEntity trader;
		final double stopDistance;
		final double speedModifier;

		WanderToPositionGoal(WispEntity p_35899_, double p_35900_, double p_35901_) {
			this.trader = p_35899_;
			this.stopDistance = p_35900_;
			this.speedModifier = p_35901_;
			this.setFlags(EnumSet.of(Goal.Flag.MOVE));
		}

		public void stop() {
			this.trader.setWanderTarget((BlockPos) null);
			WispEntity.this.navigation.stop();
		}

		public boolean canUse() {
			BlockPos blockpos = this.trader.getWanderTarget();
			return blockpos != null && this.isTooFarAway(blockpos, this.stopDistance);
		}

		public void tick() {
			BlockPos blockpos = this.trader.getWanderTarget();
			if (blockpos != null && WispEntity.this.navigation.isDone()) {
				if (this.isTooFarAway(blockpos, 10.0D)) {
					Vec3 vec3 = (new Vec3((double) blockpos.getX() - this.trader.getX(),
							(double) blockpos.getY() - this.trader.getY(),
							(double) blockpos.getZ() - this.trader.getZ())).normalize();
					Vec3 vec31 = vec3.scale(10.0D).add(this.trader.getX(), this.trader.getY(), this.trader.getZ());
					WispEntity.this.navigation.moveTo(vec31.x, vec31.y, vec31.z, this.speedModifier);
				} else {
					WispEntity.this.navigation.moveTo((double) blockpos.getX(), (double) blockpos.getY(),
							(double) blockpos.getZ(), this.speedModifier);
				}
			}

		}

		private boolean isTooFarAway(BlockPos p_35904_, double p_35905_) {
			return !p_35904_.closerToCenterThan(this.trader.position(), p_35905_);
		}
	}

}
