package com.startraveler.verdant.entity.custom;

import com.startraveler.verdant.entity.goal.BoundedDistanceRangedAttackGoal;
import com.startraveler.verdant.registry.MobEffectRegistry;
import com.startraveler.verdant.util.VerdantTags;
import it.unimi.dsi.fastutil.floats.FloatList;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.Difficulty;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.armadillo.Armadillo;
import net.minecraft.world.entity.animal.golem.IronGolem;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.monster.spider.Spider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class SkullSpiderEntity extends Spider implements RangedAttackMob {
    @SuppressWarnings("unused")
    public SkullSpiderEntity(EntityType<? extends @NotNull SkullSpiderEntity> type, Level level) {
        super(type, level);
    }

    public static AttributeSupplier.Builder createSkullSpiderAttributes() {
        return Spider.createAttributes().add(Attributes.MAX_HEALTH, 20.0F);
    }

    @SuppressWarnings("deprecation")
    public static boolean canSkullSpiderTarget(Mob entity, Entity target) {
        float magicValue = entity.getLightLevelDependentMagicValue();
        return magicValue >= 0.5F || (target instanceof LivingEntity livingEntity && livingEntity.getInBlockState()
                .is(VerdantTags.Blocks.RESTRAINS_FOR_SPIDERS));
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(
                2,
                new AvoidEntityGoal<>(
                        this,
                        Armadillo.class,
                        6.0F,
                        1.0F,
                        1.2,
                        (armadillo) -> !((Armadillo) armadillo).isScared()
                )
        );
        this.goalSelector.addGoal(
                2,
                new BoundedDistanceRangedAttackGoal(this, 1.25F, 20, 60, 20.0F, FloatList.of(6, 16))
        );
        this.goalSelector.addGoal(4, new LeapAtTargetGoal(this, 0.4F));
        this.goalSelector.addGoal(5, new SkullSpiderAttackGoal(this));
        this.goalSelector.addGoal(6, new WaterAvoidingRandomStrollGoal(this, 0.8));
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new SkullSpiderTargetGoal<>(this, Player.class));
        this.targetSelector.addGoal(3, new SkullSpiderTargetGoal<>(this, IronGolem.class));
    }

    @Override
    public @NotNull Vec3 getVehicleAttachmentPoint(Entity entity) {
        return entity.getBbWidth() <= this.getBbWidth() ? new Vec3(
                0.0F,
                0.21875F * this.getScale(),
                0.0F
        ) : super.getVehicleAttachmentPoint(entity);
    }

    @Override
    public boolean doHurtTarget(@NotNull ServerLevel level, @NotNull Entity entity) {
        boolean superDidHurt = super.doHurtTarget(level, entity);
        if (superDidHurt && entity instanceof LivingEntity livingEntity) {
            int duration = 5;
            if (this.level().getDifficulty() == Difficulty.NORMAL) {
                duration = 11;
            } else if (this.level().getDifficulty() == Difficulty.HARD) {
                duration = 25;
            }
            livingEntity.addEffect(
                    new MobEffectInstance(MobEffectRegistry.BROKEN_ARMOR.asHolder(), duration * 20, 0),
                    this
            );
        }
        return superDidHurt;
    }

    @Override
    public void performRangedAttack(@NotNull LivingEntity target, float v) {
        BlockPlacingProjectile projectile = new BlockPlacingProjectile(
                this.level(),
                this,
                Blocks.COBWEB.asItem().getDefaultInstance()
        );
        double d0 = target.getX() - this.getX();
        double d1 = target.getY(1 / 3f) - projectile.getY();
        double d2 = target.getZ() - this.getZ();
        double d3 = Math.sqrt(d0 * d0 + d2 * d2) * (double) 0.15F;
        Level var12 = this.level();
        if (var12 instanceof ServerLevel serverlevel) {
            Projectile.spawnProjectileUsingShoot(
                    projectile,
                    serverlevel,
                    ItemStack.EMPTY,
                    d0,
                    d1 + d3,
                    d2,
                    1.5F,
                    1.0F
            );
        }

        if (!this.isSilent()) {
            this.level().playSound(
                    null,
                    this.getX(),
                    this.getY(),
                    this.getZ(),
                    SoundEvents.COBWEB_PLACE,
                    this.getSoundSource(),
                    1.0F,
                    1.0F + (this.random.nextFloat() - this.random.nextFloat()) * 0.2F
            );
        }
    }

    public static class SkullSpiderAttackGoal extends MeleeAttackGoal {
        public SkullSpiderAttackGoal(Spider spider) {
            super(spider, 1.0F, true);
        }

        public boolean canUse() {
            return super.canUse() && !this.mob.isVehicle();
        }

        public boolean canContinueToUse() {
            if (!SkullSpiderEntity.canSkullSpiderTarget(this.mob, this.mob.getTarget()) && this.mob.getRandom()
                    .nextInt(100) == 0) {
                this.mob.setTarget(null);
                return false;
            } else {
                return super.canContinueToUse();
            }
        }
    }

    public static class SkullSpiderTargetGoal<T extends LivingEntity> extends NearestAttackableTargetGoal<T> {
        public SkullSpiderTargetGoal(Spider spider, Class<T> clazz) {
            super(spider, clazz, true);
        }

        public boolean canUse() {
            return SkullSpiderEntity.canSkullSpiderTarget(this.mob, this.targetMob) || super.canUse();
        }
    }
}
