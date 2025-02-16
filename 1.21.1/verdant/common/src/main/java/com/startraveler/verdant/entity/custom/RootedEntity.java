/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * If you modify this file, please include a notice stating the changes:
 * Example: "Modified by [Your Name] on [Date] - [Short Description of Changes]"
 */
package com.startraveler.verdant.entity.custom;

import com.startraveler.verdant.VerdantIFF;
import com.startraveler.verdant.registry.EntityTypeRegistry;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.ConversionParams;
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
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class RootedEntity extends Zombie {

    public RootedEntity(EntityType<? extends RootedEntity> type, Level level) {
        super(type, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.FOLLOW_RANGE, 45.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.20F)
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
        this.targetSelector.addGoal(1, (new HurtByTargetGoal(this)).setAlertOthers(RootedEntity.class));
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

    // Prevent drowning
    @Override
    protected boolean convertsInWater() {
        return false;
    }

    @Override
    protected boolean isSunSensitive() {
        return false;
    }

    // Instantly convert skeletons and creepers.
    // Currently, doesn't actually remove the dead entity!
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
        boolean converted = false;
        if ((level.getDifficulty() == Difficulty.NORMAL || level.getDifficulty() == Difficulty.HARD) && entity instanceof Villager villager) {
            if (level.getDifficulty() != Difficulty.HARD && this.random.nextBoolean()) {
                return flag;
            }

            if (this.convertVillagerToZombieVillager(level, villager)) {
                flag = false;
                converted = true;
            }
        } else if (entity instanceof Zombie zombie) {
            if (this.convertZombieToRooted(level, zombie)) {
                flag = false;
                converted = true;
            }
        } else if (entity instanceof Skeleton skeleton) {
            if (this.convertSkeletonToBogged(level, skeleton)) {
                flag = false;
                converted = true;
            }
        }

        if (converted) {
            level.sendParticles(
                    ParticleTypes.HAPPY_VILLAGER,
                    entity.getX(),
                    entity.getY() + entity.getEyeHeight(),
                    entity.getZ(),
                    8,
                    0.5,
                    1,
                    0.5,
                    1
            );
            level.sendParticles(
                    ParticleTypes.SPORE_BLOSSOM_AIR,
                    entity.getX(),
                    entity.getY() + entity.getEyeHeight(),
                    entity.getZ(),
                    64,
                    2,
                    4,
                    2,
                    1
            );
        }

        return flag;
    }

    public boolean convertZombieToRooted(ServerLevel level, Zombie zombie) {
        RootedEntity newZombie = zombie.convertTo(
                EntityTypeRegistry.ROOTED.get(),
                ConversionParams.single(zombie, true, true),
                oz -> oz.handleAttributes(oz.level().getCurrentDifficultyAt(oz.blockPosition()).getSpecialMultiplier())

        );
        return newZombie != null;
    }

    public boolean convertSkeletonToBogged(ServerLevel level, Skeleton skeleton) {
        Bogged newSkeleton = skeleton.convertTo(
                EntityType.BOGGED, ConversionParams.single(skeleton, true, true), bg -> {
                }
        );
        return newSkeleton != null;
    }


}
