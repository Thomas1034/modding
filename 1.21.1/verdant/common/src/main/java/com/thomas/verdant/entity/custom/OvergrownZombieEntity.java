package com.thomas.verdant.entity.custom;

import com.thomas.verdant.VerdantIFF;
import com.thomas.verdant.registry.EntityTypeRegistry;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.*;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class OvergrownZombieEntity extends Zombie {

    public OvergrownZombieEntity(EntityType<? extends OvergrownZombieEntity> type, Level level) {
        super(type, level);
    }

    public OvergrownZombieEntity(Level level) {
        super(EntityTypeRegistry.OVERGROWN_ZOMBIE.get(), level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.FOLLOW_RANGE, 45.0D)
                .add(Attributes.MOVEMENT_SPEED, (double) 0.20F)
                .add(Attributes.ATTACK_DAMAGE, 4.0D)
                .add(Attributes.ARMOR, 10.0D)
                .add(Attributes.SPAWN_REINFORCEMENTS_CHANCE, 0.5);
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
        this.targetSelector.addGoal(
                2,
                new NearestAttackableTargetGoal<>(
                        this,
                        Player.class,
                        true,
                        (entity, level) -> VerdantIFF.isEnemy(entity)
                )
        );
        this.targetSelector.addGoal(
                2,
                new NearestAttackableTargetGoal<>(
                        this,
                        Zombie.class,
                        true,
                        (entity, level) -> VerdantIFF.isEnemy(entity)
                )
        );
        this.targetSelector.addGoal(
                2,
                new NearestAttackableTargetGoal<>(
                        this,
                        Skeleton.class,
                        true,
                        (entity, level) -> VerdantIFF.isEnemy(entity)
                )
        );
        this.targetSelector.addGoal(
                3,
                new NearestAttackableTargetGoal<>(
                        this,
                        AbstractVillager.class,
                        false,
                        (entity, level) -> VerdantIFF.isEnemy(entity)
                )
        );
        this.targetSelector.addGoal(
                3,
                new NearestAttackableTargetGoal<>(
                        this,
                        IronGolem.class,
                        true,
                        (entity, level) -> VerdantIFF.isEnemy(entity)
                )
        );
    }

    @Override
    public boolean canBreakDoors() {
        return true;
    }

    // Prevent drownding
    @Override
    protected boolean convertsInWater() {
        return false;
    }

    @Override
    protected boolean isSunSensitive() {
        return false;
    }

    // Instaconvert skeletons and creepers.
    @Override
    public boolean doHurtTarget(ServerLevel level, Entity entity) {
        if (!super.doHurtTarget(level, entity)) {
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


        return flag;
    }
}