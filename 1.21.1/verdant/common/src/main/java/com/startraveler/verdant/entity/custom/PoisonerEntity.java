package com.startraveler.verdant.entity.custom;

import com.startraveler.verdant.registry.PotionRegistry;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Witch;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ThrownPotion;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


// TODO find a way to prevent arrows from working as well.
// TODO chance to grow tall grass when hit to break combos.
public class PoisonerEntity extends Witch {
    public PoisonerEntity(EntityType<? extends Witch> type, Level level) {
        super(type, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 40.0F)
                .add(Attributes.MOVEMENT_SPEED, 0.35F);
    }

    public void performRangedAttack(LivingEntity target, float distanceFactor) {
        if (!this.isDrinkingPotion()) {
            Vec3 motionPredictionVector = target.getDeltaMovement();
            double xDistanceToTarget = target.getX() + motionPredictionVector.x - this.getX();
            double heightDifference = target.getEyeY() - (double) 1.1F - this.getY();
            double zDistanceToTarget = target.getZ() + motionPredictionVector.z - this.getZ();
            double distanceToTarget = Math.sqrt(xDistanceToTarget * xDistanceToTarget + zDistanceToTarget * zDistanceToTarget);
            Holder<Potion> potionToInflict = Potions.HARMING;
            List<MobEffectInstance> extraEffectsToInflict = new ArrayList<>();

            if (target instanceof Raider) {
                if (target.getHealth() <= 4.0F) {
                    potionToInflict = Potions.HEALING;
                } else {
                    potionToInflict = Potions.REGENERATION;
                }
                extraEffectsToInflict.addAll(PotionRegistry.ADRENALINE.get().getEffects());
                if (this.random.nextFloat() < 0.3) {
                    extraEffectsToInflict.addAll(PotionRegistry.STRONG_ANTIDOTE.get().getEffects());
                }
                this.setTarget(null);

            } else if (distanceToTarget >= (double) 8.0F && !target.hasEffect(MobEffects.MOVEMENT_SLOWDOWN)) {
                potionToInflict = Potions.SLOWNESS;
                extraEffectsToInflict.addAll(PotionRegistry.STRONG_COLLOID.get().getEffects());
            } else if (target.getHealth() >= 8.0F && !target.hasEffect(MobEffects.POISON)) {
                potionToInflict = Potions.STRONG_POISON;
            } else if (distanceToTarget <= (double) 3.0F && !target.hasEffect(MobEffects.WEAKNESS) && this.random.nextFloat() < 0.25F) {
                potionToInflict = Potions.WEAKNESS;
                extraEffectsToInflict.addAll(PotionRegistry.STRONG_BLURRED.get().getEffects());
            } else {
                extraEffectsToInflict.addAll(PotionRegistry.BROKEN_ARMOR.get().getEffects());
            }

            Level level = this.level();
            if (level instanceof ServerLevel serverLevel) {
                ItemStack itemstack = new ItemStack(Items.SPLASH_POTION);
                itemstack.set(
                        DataComponents.POTION_CONTENTS,
                        new PotionContents(
                                Optional.of(potionToInflict),
                                Optional.empty(),
                                extraEffectsToInflict,
                                Optional.empty()
                        )
                );

                Projectile.spawnProjectileUsingShoot(
                        ThrownPotion::new,
                        serverLevel,
                        itemstack,
                        this,
                        xDistanceToTarget,
                        heightDifference + distanceToTarget * 0.2,
                        zDistanceToTarget,
                        0.75F,
                        4.0F
                );
            }

            if (!this.isSilent()) {
                this.level().playSound(
                        null,
                        this.getX(),
                        this.getY(),
                        this.getZ(),
                        SoundEvents.WITCH_THROW,
                        this.getSoundSource(),
                        1.0F,
                        0.8F + this.random.nextFloat() * 0.4F
                );
            }
        }

    }

    @Override
    public boolean canBeLeader() {
        return true;
    }
}
