package com.startraveler.verdant.entity.custom;

import com.startraveler.verdant.mixin.AbstractArrowAccessors;
import com.startraveler.verdant.mixin.EntityGetterInvoker;
import com.startraveler.verdant.mixin.ThrownTridentDataIDGetter;
import com.startraveler.verdant.registry.EntityTypeRegistry;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ThrownTrident;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

import java.lang.ref.WeakReference;
import java.util.UUID;

public class ThrownSpearEntity extends ThrownTrident {

    public static final String LOCKED_X_ROT_TAG = "LockedXRot";
    public static final String LOCKED_Y_ROT_TAG = "LockedYRot";
    public static final String RELATIVE_POS_X_TAG = "RelativePosX";
    public static final String RELATIVE_POS_Y_TAG = "RelativePosY";
    public static final String RELATIVE_POS_Z_TAG = "RelativePosZ";
    public static final String STUCK_ENTITY_ID_TAG = "StuckEntityID";
    private static final double IMPACT_DEPTH = 0.3D; // How deep the spear embeds
    public float lockedXRot;
    public float lockedYRot;
    public Vec3 relativePosition;
    private UUID targetEntityID;
    private boolean isStuck;
    private WeakReference<Entity> cachedEntity;

    public ThrownSpearEntity(EntityType<? extends ThrownTrident> type, Level level) {
        super(type, level);
    }

    public ThrownSpearEntity(Level level, LivingEntity thrower, ItemStack stack) {
        super(EntityTypeRegistry.THROWN_SPEAR.get(), level);
        this.entityData.set(ThrownTridentDataIDGetter.getLoyaltyID(), this.getLoyaltyFromItem(stack));
        this.entityData.set(ThrownTridentDataIDGetter.getFoilID(), stack.hasFoil());
        this.setOwner(thrower);
        double x = thrower.getX();
        double y = thrower.getEyeY() - 0.1;
        double z = thrower.getZ();
        ((AbstractArrowAccessors) this).verdant$setPickupItemStack(stack.copy());
        this.setCustomName(stack.get(DataComponents.CUSTOM_NAME));
        Unit unit = stack.remove(DataComponents.INTANGIBLE_PROJECTILE);
        if (unit != null) {
            this.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
        }
        this.setPos(x, y, z);
    }

    public static float getVisualYRot(Entity entity, float partialTick) {
        return entity.getVisualRotationYInDegrees();
    }

    public static Vec3 translate(Vec3 relativeInGlobal, float xRotOfTarget, float yRotOfTarget) {
        return relativeInGlobal.xRot(-xRotOfTarget).yRot(-yRotOfTarget);
    }

    public Entity getTargetEntity() {
        if (this.targetEntityID == null) {
            // There is no target entity.
            return null;
        }
        Entity target = null;
        // After this point, we know there is a target.
        if (this.cachedEntity != null) {
            // After this point, we know that the target has been selected and cached.
            // We don't know if it's still there, though.
            if (this.cachedEntity.get() instanceof Entity entity) {
                target = entity;
            }
        } else {
            // We should have a target, but it isn't cached yet.
            // This may be null if no entity with this UUID is found!
            target = ((EntityGetterInvoker) this.level()).getEntitiesInvoker().get(this.targetEntityID);
            this.cachedEntity = new WeakReference<>(target);
        }
        // If the target is null, we no longer have a target or the target is inaccessible (and/or garbage collected)
        // This means we should forget its UUID.
        if (target == null) {
            this.forgetTarget();
        } else {
            // We have a target.
            // It may, however, be removed or in a different dimension.
            // First, check if it was removed.
            if (target.isRemoved()) {
                target = null;
                this.forgetTarget();
            } else {
                // If it is not removed, check if it's in the same dimension.
                if (target.level().dimension() != this.level().dimension()) {
                    // If not, forget the target.
                    target = null;
                    this.forgetTarget();
                }
                // The target is valid.
            }
        }
        return target;
    }

    private void forgetTarget() {
        this.targetEntityID = null;
        // No need to keep this around.
        this.cachedEntity = null;
    }

    @Override
    public void tick() {
        super.tick();
        if (this.isStuck && this.targetEntityID != null) {
            Entity target = this.getTargetEntity();
            if (!(target instanceof LivingEntity livingTarget) || livingTarget.isDeadOrDying() || !target.isAlive() || target.isRemoved()) {
                this.detachFromEntity();
            } else {
                this.teleportToEntity(target);
            }
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult hitResult) {
        Entity hitEntity = hitResult.getEntity();
        if (hitEntity instanceof LivingEntity target) {
            attachToEntity(target);
            if (target.level() instanceof ServerLevel serverLevel) {
                target.hurtServer(serverLevel, this.damageSources().thrown(this, this.getOwner()), 8.0F);
            }
        }
    }

    @Override
    protected boolean tryPickup(Player player) {
        return false;
    }

    @Override
    protected ItemStack getDefaultPickupItem() {
        return new ItemStack(Items.SUGAR);
    }

    @Override
    public void playerTouch(Player player) {
        // TODO fix the condition here
        if (!this.level().isClientSide && (this.inGroundTime > 4 || !this.isStuck) && this.shakeTime <= 0) {
            boolean flag = this.pickup == Pickup.ALLOWED || (this.pickup == Pickup.CREATIVE_ONLY && player.getAbilities().instabuild);
            if (flag) {
                if (this.targetEntityID != null) {
                    this.removeSpearFromEntity(this.getTargetEntity());
                }
                // TODO this doesn't actually work.
                player.take(this, 1);
                this.discard();
            }
        }
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        if (tag.contains(LOCKED_X_ROT_TAG)) {
            this.lockedXRot = tag.getFloat(LOCKED_X_ROT_TAG);
        }
        if (tag.contains(LOCKED_Y_ROT_TAG)) {
            this.lockedYRot = tag.getFloat(LOCKED_Y_ROT_TAG);
        }
        if (tag.contains(RELATIVE_POS_X_TAG) && tag.contains(RELATIVE_POS_Y_TAG) && tag.contains(RELATIVE_POS_Z_TAG)) {
            double x = tag.getFloat(RELATIVE_POS_X_TAG);
            double y = tag.getFloat(RELATIVE_POS_Y_TAG);
            double z = tag.getFloat(RELATIVE_POS_Z_TAG);
            this.relativePosition = new Vec3(x, y, z);
        }
        if (tag.contains(STUCK_ENTITY_ID_TAG)) {
            this.targetEntityID = tag.getUUID(STUCK_ENTITY_ID_TAG);
            this.isStuck = true;
        }

    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        if (this.isStuck) {
            tag.putDouble(RELATIVE_POS_X_TAG, this.relativePosition.x);
            tag.putDouble(RELATIVE_POS_Y_TAG, this.relativePosition.y);
            tag.putDouble(RELATIVE_POS_Z_TAG, this.relativePosition.z);
            tag.putFloat(LOCKED_X_ROT_TAG, this.lockedXRot);
            tag.putFloat(LOCKED_Y_ROT_TAG, this.lockedYRot);
            tag.putUUID(STUCK_ENTITY_ID_TAG, this.targetEntityID);
        }
    }

    @Override
    protected float getWaterInertia() {
        return 0.5F;
    }

    protected void teleportToEntity(Entity target) {
        // Update position relative to stuck entity while maintaining locked rotation
        Vec3 newPos = target.position().add(this.relativePosition);
        this.setPos(newPos.x, newPos.y, newPos.z);
        this.setDeltaMovement(Vec3.ZERO);
    }

    protected void detachFromEntity() {
        Entity stuckEntity = this.getTargetEntity();

        this.removeSpearFromEntity(stuckEntity);
        this.isStuck = false;
        this.targetEntityID = null;
        this.setNoGravity(false);
        this.setDeltaMovement(0, 0, 0);
    }

    protected void attachToEntity(Entity target) {
        // Lock current rotation
        this.lockedXRot = this.getXRot();
        this.lockedYRot = this.getYRot() - target.getVisualRotationYInDegrees();

        Vec3 motion = this.getDeltaMovement().normalize();
        Vec3 impactPoint = this.position().add(motion.scale(IMPACT_DEPTH));

        Vec3 entityPos = target.position();
        this.relativePosition = new Vec3(
                impactPoint.x - entityPos.x,
                impactPoint.y - entityPos.y,
                impactPoint.z - entityPos.z
        );

        this.targetEntityID = target.getUUID();
        this.isStuck = true;

        this.setNoGravity(true);
        this.setDeltaMovement(Vec3.ZERO);

        this.addSpearToEntity(target);
    }

    private void removeSpearFromEntity(Entity target) {
        if (target == null) {
            return;
        }
        // Parameter is nullable!
        // TODO handle spear removal on the target entity's side.
    }

    private void addSpearToEntity(Entity target) {
        // TODO handle adding spear counter to the target.
    }

    @Override
    public void remove(Entity.RemovalReason reason) {
        if (this.targetEntityID != null) {
            this.removeSpearFromEntity(this.getTargetEntity());
        }
        super.remove(reason);
    }

    protected byte getLoyaltyFromItem(ItemStack stack) {
        Level level = this.level();
        int loyaltyAmt;
        if (level instanceof ServerLevel serverlevel) {
            loyaltyAmt = EnchantmentHelper.getTridentReturnToOwnerAcceleration(serverlevel, stack, this);
        } else {
            loyaltyAmt = 0;
        }

        return (byte) Math.clamp(loyaltyAmt, 0, Byte.MAX_VALUE);
    }
}
