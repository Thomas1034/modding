package com.startraveler.verdant.entity.custom;

import com.google.common.collect.Lists;
import com.startraveler.verdant.Constants;
import com.startraveler.verdant.mixin.AbstractArrowAccessors;
import com.startraveler.verdant.mixin.ArrowAccessors;
import com.startraveler.verdant.registry.EntityTypeRegistry;
import com.startraveler.verdant.registry.ItemRegistry;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.protocol.game.ClientboundGameEventPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.util.Unit;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.entity.projectile.ProjectileDeflection;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;

import java.util.List;

public class DartEntity extends Arrow {
    public DartEntity(EntityType<? extends Arrow> type, Level level) {
        super(type, level);
        ((ArrowAccessors) (this)).verdant$updateColor();
        Constants.LOG.warn("Color is: {}", this.getColor());
    }

    public DartEntity(Level level, LivingEntity thrower, ItemStack ammo, ItemStack bow) {
        this(EntityTypeRegistry.DART.get(), thrower, level, ammo, bow);
    }

    protected DartEntity(EntityType<? extends Arrow> entityType, LivingEntity owner, Level level, ItemStack pickupItemStack, ItemStack firedFromWeapon) {
        this(
                entityType,
                owner.getX(),
                owner.getEyeY() - (double) 0.1F,
                owner.getZ(),
                level,
                pickupItemStack,
                firedFromWeapon
        );
        this.setOwner(owner);
    }

    protected DartEntity(EntityType<? extends Arrow> entityType, double x, double y, double z, Level level, ItemStack pickupItemStack, ItemStack firedFromWeapon) {
        this(entityType, level);
        this.setPickupItemStack(pickupItemStack.copy());
        this.setCustomName(pickupItemStack.get(DataComponents.CUSTOM_NAME));
        Unit unit = pickupItemStack.remove(DataComponents.INTANGIBLE_PROJECTILE);
        if (unit != null) {
            this.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
        }

        this.setPos(x, y, z);
        if (firedFromWeapon != null && level instanceof ServerLevel serverlevel) {
            if (firedFromWeapon.isEmpty()) {
                throw new IllegalArgumentException("Invalid weapon firing an arrow");
            }

            ((AbstractArrowAccessors) this).verdant$setFiredFromWeapon(firedFromWeapon.copy());
            int pierceLevel = EnchantmentHelper.getPiercingCount(serverlevel, firedFromWeapon, this.getPickupItem());
            if (pierceLevel > 0) {
                ((AbstractArrowAccessors) this).verdant$setPierceLevel((byte) pierceLevel);
            }
        }

    }

    public DartEntity(Level level, double x, double y, double z, ItemStack itemStack, ItemStack weapon) {
        this(EntityTypeRegistry.DART.get(), x, y, z, level, itemStack, weapon);
    }


    @Override
    protected ItemStack getDefaultPickupItem() {
        return new ItemStack(ItemRegistry.DART.get());
    }


    // Modified from AbstractArrow's onHitEntity
    @Override
    @SuppressWarnings("deprecation")
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        Entity hitEntity = result.getEntity();
        double baseDamage = this.getBaseDamage();
        Entity owner = this.getOwner();
        DamageSource arrowDamageSource = this.damageSources().arrow(this, owner != null ? owner : this);
        if (this.getWeaponItem() != null) {
            Level flag = this.level();
            if (flag instanceof ServerLevel serverlevel) {
                baseDamage = EnchantmentHelper.modifyDamage(
                        serverlevel,
                        this.getWeaponItem(),
                        hitEntity,
                        arrowDamageSource,
                        (float) baseDamage
                );
            }
        }

        int scaledDamage = Mth.ceil(baseDamage);
        int pierceLevel = this.getPierceLevel();
        if (pierceLevel > 0) {
            if (((AbstractArrowAccessors) (this)).verdant$getPiercingIgnoreEntityIds() == null) {
                ((AbstractArrowAccessors) (this)).verdant$setPiercingIgnoreEntityIds(new IntOpenHashSet(pierceLevel + 2));
            }

            if (((AbstractArrowAccessors) (this)).verdant$getPiercedAndKilledEntities() == null) {
                ((AbstractArrowAccessors) (this)).verdant$setPiercedAndKilledEntities(Lists.newArrayListWithCapacity(
                        pierceLevel + 2));
            }

            if (((AbstractArrowAccessors) (this)).verdant$getPiercingIgnoreEntityIds().size() >= pierceLevel + 1) {
                this.discard();
                return;
            }

            ((AbstractArrowAccessors) (this)).verdant$getPiercingIgnoreEntityIds().add(hitEntity.getId());
        }

        if (owner instanceof LivingEntity livingOwner) {
            livingOwner.setLastHurtMob(hitEntity);
        }

        boolean hitEntityIsEnderMan = hitEntity.getType() == EntityType.ENDERMAN;
        int hitEntityRemainingFireTicks = hitEntity.getRemainingFireTicks();
        if (this.isOnFire() && !hitEntityIsEnderMan) {
            hitEntity.igniteForSeconds(5.0F);
        }


        if (hitEntity.hurtOrSimulate(arrowDamageSource, (float) scaledDamage)) {
            if (hitEntityIsEnderMan) {
                return;
            }

            if (hitEntity instanceof LivingEntity livingentity) {

                this.doKnockback(livingentity, arrowDamageSource);
                Level level = this.level();
                if (level instanceof ServerLevel serverlevel) {
                    EnchantmentHelper.doPostAttackEffectsWithItemSource(
                            serverlevel,
                            livingentity,
                            arrowDamageSource,
                            this.getWeaponItem()
                    );
                }

                this.doPostHurtEffects(livingentity);
                if (livingentity != owner && livingentity instanceof Player && owner instanceof ServerPlayer && !this.isSilent()) {
                    ((ServerPlayer) owner).connection.send(new ClientboundGameEventPacket(
                            ClientboundGameEventPacket.ARROW_HIT_PLAYER,
                            0.0F
                    ));
                }

                if (!hitEntity.isAlive() && ((AbstractArrowAccessors) (this)).verdant$getPiercedAndKilledEntities() != null) {
                    ((AbstractArrowAccessors) (this)).verdant$getPiercedAndKilledEntities().add(livingentity);
                }

                if (!this.level().isClientSide && owner instanceof ServerPlayer serverPlayer) {
                    if (((AbstractArrowAccessors) (this)).verdant$getPiercedAndKilledEntities() != null) {
                        CriteriaTriggers.KILLED_BY_ARROW.trigger(
                                serverPlayer,
                                ((AbstractArrowAccessors) (this)).verdant$getPiercedAndKilledEntities(),
                                ((AbstractArrowAccessors) (this)).verdant$getFiredFromWeapon()
                        );
                    } else if (!hitEntity.isAlive()) {
                        CriteriaTriggers.KILLED_BY_ARROW.trigger(
                                serverPlayer,
                                List.of(hitEntity),
                                ((AbstractArrowAccessors) (this)).verdant$getFiredFromWeapon()
                        );
                    }
                }
            }

            this.playSound(this.getHitGroundSoundEvent(), 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
            if (this.getPierceLevel() <= 0) {
                this.discard();
            }
        } else {
            hitEntity.setRemainingFireTicks(hitEntityRemainingFireTicks);
            this.deflect(ProjectileDeflection.REVERSE, hitEntity, this.getOwner(), false);
            this.setDeltaMovement(this.getDeltaMovement().scale(0.2));
            Level level = this.level();
            if (level instanceof ServerLevel serverLevel) {
                if (this.getDeltaMovement().lengthSqr() < 1.0E-7) {
                    if (this.pickup == AbstractArrow.Pickup.ALLOWED) {
                        this.spawnAtLocation(serverLevel, this.getPickupItem(), 0.1F);
                    }

                    this.discard();
                }
            }
        }

    }
}
