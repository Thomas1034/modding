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
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Endermite;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class TimbermiteEntity extends Monster {
    private static final int MAX_LIFE = 2400;
    private static final int XP_REWARD = 15;
    private int life;

    public TimbermiteEntity(EntityType<? extends Monster> type, Level level) {
        super(type, level);
        this.xpReward = XP_REWARD;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 16.0)
                .add(Attributes.MOVEMENT_SPEED, 0.4)
                .add(Attributes.ATTACK_DAMAGE, 4.0)
                .add(Attributes.STEP_HEIGHT, 1.5)
                .add(Attributes.ATTACK_KNOCKBACK, 0.1)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.5)
                .add(Attributes.BURNING_TIME, 0.25)
                .add(Attributes.ARMOR_TOUGHNESS, 4.0)
                .add(Attributes.SAFE_FALL_DISTANCE, 8.0)
                .add(Attributes.ARMOR, 6.0);
    }


    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(1, new ClimbOnTopOfPowderSnowGoal(this, this.level()));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.2F, false));
        this.goalSelector.addGoal(3, new WaterAvoidingRandomStrollGoal(this, 1.0F));
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(
                1,
                (new HurtByTargetGoal(this)).setAlertOthers(Endermite.class, TimbermiteEntity.class)
        );
        this.targetSelector.addGoal(
                2,
                new NearestAttackableTargetGoal<>(
                        this,
                        Player.class,
                        true,
                        (entity, level) -> VerdantIFF.isEnemy(entity)
                )
        );
    }

    public void tick() {
        this.yBodyRot = this.getYRot();
        super.tick();
    }

    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENDERMITE_AMBIENT;
    }

    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("Lifetime", this.life);
    }

    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.life = compound.getInt("Lifetime");
    }

    protected void playStepSound(BlockPos pos, BlockState block) {
        this.playSound(SoundEvents.ENDERMITE_STEP, 0.15F, 1.0F);
    }

    protected Entity.MovementEmission getMovementEmission() {
        return MovementEmission.EVENTS;
    }

    public void setYBodyRot(float offset) {
        this.setYRot(offset);
        super.setYBodyRot(offset);
    }

    public void aiStep() {
        super.aiStep();
        if (this.level().isClientSide) {
            if (this.level().random.nextInt(20) == 0) {
                for (int i = 0; i < 2; ++i) {
                    this.level().addParticle(
                            ParticleTypes.HAPPY_VILLAGER,
                            this.getRandomX(0.5F),
                            this.getRandomY(),
                            this.getRandomZ(0.5F),
                            (this.random.nextDouble() - (double) 0.5F) * (double) 2.0F,
                            -this.random.nextDouble(),
                            (this.random.nextDouble() - 0.5F) * 2.0F
                    );
                }
            }
        } else {
            if (!this.isPersistenceRequired()) {
                ++this.life;
            }

            if (this.life >= MAX_LIFE) {
                this.discard();
            }
        }

    }

    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.ENDERMITE_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.ENDERMITE_DEATH;
    }
}
