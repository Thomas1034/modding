package com.thomas.verdant.entity.custom;

import com.thomas.verdant.entity.ModEntityType;
import com.thomas.verdant.overgrowth.EntityOvergrowthEffects;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MoveThroughVillageGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.ZombieAttackGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Blaze;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class OvergrownZombieEntity extends Zombie {

	public OvergrownZombieEntity(EntityType<? extends Zombie> type, Level level) {
		super(type, level);
	}

	public OvergrownZombieEntity(Level level) {
		super(ModEntityType.OVERGROWN_ZOMBIE.get(), level);
	}

	// Prevent drownding
	@Override
	protected boolean convertsInWater() {
		return false;
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
		this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
		this.addBehaviourGoals();
	}

	@Override
	protected void addBehaviourGoals() {
		this.goalSelector.addGoal(2, new ZombieAttackGoal(this, 1.0D, false));
		this.goalSelector.addGoal(3, new AvoidEntityGoal<>(this, Blaze.class, 6.0F, 1.0D, 1.2D));
		this.goalSelector.addGoal(6, new MoveThroughVillageGoal(this, 1.0D, true, 4, this::canBreakDoors));
		this.goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, 1.0D));
		this.targetSelector.addGoal(1, (new HurtByTargetGoal(this)).setAlertOthers(OvergrownZombieEntity.class));
		this.targetSelector.addGoal(2,
				new NearestAttackableTargetGoal<>(this, Player.class, true, EntityOvergrowthEffects::isEnemy));
		this.targetSelector.addGoal(2,
				new NearestAttackableTargetGoal<>(this, Zombie.class, true, EntityOvergrowthEffects::isEnemy));
		this.targetSelector.addGoal(2,
				new NearestAttackableTargetGoal<>(this, Skeleton.class, true, EntityOvergrowthEffects::isEnemy));
		//this.targetSelector.addGoal(2,
		//		new NearestAttackableTargetGoal<>(this, Creeper.class, true, EntityOvergrowthEffects::isEnemy));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, false,
				EntityOvergrowthEffects::isEnemy));
		this.targetSelector.addGoal(3,
				new NearestAttackableTargetGoal<>(this, IronGolem.class, true, EntityOvergrowthEffects::isEnemy));
	}

	public static AttributeSupplier.Builder createAttributes() {
		return Monster.createMonsterAttributes().add(Attributes.FOLLOW_RANGE, 45.0D)
				.add(Attributes.MOVEMENT_SPEED, (double) 0.15F).add(Attributes.ATTACK_DAMAGE, 4.0D)
				.add(Attributes.ARMOR, 8.0D).add(Attributes.SPAWN_REINFORCEMENTS_CHANCE, 0.5);
	}

	@Override
	protected boolean isSunSensitive() {
		return false;
	}

	@Override
	public boolean canBreakDoors() {
		return true;
	}

	// Instaconvert skeletons and creepers.
	@Override
	public boolean doHurtTarget(Entity entity) {
		if (!super.doHurtTarget(entity)) {
			return false;
		} else {
			if (entity instanceof Skeleton) {
				((Skeleton) entity).die(this.damageSources().mobAttack(this));
			} else if (entity instanceof Creeper) {
				((Creeper) entity).die(this.damageSources().mobAttack(this));
			}

			return true;
		}
	}

	@Override
	public boolean killedEntity(ServerLevel level, LivingEntity entity) {
		boolean flag = super.killedEntity(level, entity);
		if (entity instanceof Zombie zombie && net.minecraftforge.event.ForgeEventFactory.canLivingConvert(entity,
				ModEntityType.OVERGROWN_ZOMBIE.get(), (timer) -> {
				})) {
			OvergrownZombieEntity overgrownZombie = zombie.convertTo(ModEntityType.OVERGROWN_ZOMBIE.get(), false);
			if (overgrownZombie != null) {
				overgrownZombie.finalizeSpawn(level, level.getCurrentDifficultyAt(overgrownZombie.blockPosition()),
						MobSpawnType.CONVERSION, new Zombie.ZombieGroupData(false, true), (CompoundTag) null);
				net.minecraftforge.event.ForgeEventFactory.onLivingConvert(entity, overgrownZombie);
				if (!this.isSilent()) {
					level.levelEvent((Player) null, 1026, this.blockPosition(), 0);
				}

				flag = false;
			}
		} else if (entity instanceof Skeleton skeleton && net.minecraftforge.event.ForgeEventFactory
				.canLivingConvert(entity, ModEntityType.OVERGROWN_SKELETON.get(), (timer) -> {
				})) {
			OvergrownSkeletonEntity overgrownZombie = skeleton.convertTo(ModEntityType.OVERGROWN_SKELETON.get(), false);
			if (overgrownZombie != null) {
				overgrownZombie.finalizeSpawn(level, level.getCurrentDifficultyAt(overgrownZombie.blockPosition()),
						MobSpawnType.CONVERSION, new Zombie.ZombieGroupData(false, true), (CompoundTag) null);
				net.minecraftforge.event.ForgeEventFactory.onLivingConvert(entity, overgrownZombie);
				if (!this.isSilent()) {
					level.levelEvent((Player) null, 1026, this.blockPosition(), 0);
				}

				flag = false;
			}
		}

		return flag;
	}
}
