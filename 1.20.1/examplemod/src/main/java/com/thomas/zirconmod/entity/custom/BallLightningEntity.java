package com.thomas.zirconmod.entity.custom;

import java.util.List;

import com.thomas.zirconmod.block.ModBlocks;
import com.thomas.zirconmod.block.custom.LightningBlock;
import com.thomas.zirconmod.block.custom.UnstableLightningBlock;
import com.thomas.zirconmod.entity.ModEntities;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Fireball;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public class BallLightningEntity extends Fireball {

	private int detectionRange;

	public BallLightningEntity(EntityType<? extends BallLightningEntity> template, Level level) {
		super(template, level);
	}

	public BallLightningEntity(Level level, LivingEntity owner, double x, double y, double z,
			int power) {
		super(ModEntities.BALL_LIGHTNING_ENTITY.get(), owner, x, y, z, level);
		this.detectionRange = power;
	}

	@Override
	public ItemStack getItem() {
		ItemStack itemstack = this.getItemRaw();
		return itemstack.isEmpty() ? new ItemStack(ModBlocks.LIGHTNING_BLOCK.get()) : itemstack;
	}

	@SuppressWarnings({ "resource" })
	@Override
	protected void onHit(HitResult p_37218_) {
		super.onHit(p_37218_);
		if (!this.level().isClientSide) {
			// Creates lightning blocks
			UnstableLightningBlock.placeCluster(this.level(), this.blockPosition(), this.detectionRange, this.random);
			
			LightningBlock.addLightningBoltAtChecked((ServerLevel) this.level(), this.blockPosition());

			this.discard();
		}
	}

	@SuppressWarnings("resource")
	@Override
	protected void onHitEntity(EntityHitResult p_37216_) {
		super.onHitEntity(p_37216_);
		if (!this.level().isClientSide) {
			Entity entity = p_37216_.getEntity();
			Entity entity1 = this.getOwner();
			entity.hurt(this.damageSources().lightningBolt(), 6.0F);
			if (entity1 instanceof LivingEntity) {
				this.doEnchantDamageEffects((LivingEntity) entity1, entity);
			}
		}
	}

	@Override
	public void tick() {
		super.tick();

		if (this.level() instanceof ServerLevel sl) {
			// Checks for nearby entities, then detonates if need be.
			List<LivingEntity> entities = this.level().getEntitiesOfClass(LivingEntity.class,
					this.getBoundingBox().inflate(this.detectionRange + 1));
			//System.out.println(entities.size() + " " + this.detectionRange);
			// If there are any other than the owner, detonate as if a block was hit.
			if (entities.size() > 0 && !(entities.size() == 1 && entities.get(0).equals(this.getOwner()))) {
				HitResult fictitiousHit = new BlockHitResult(this.position(), this.getDirection(), this.blockPosition(),
						true);
				this.onHit(fictitiousHit);
			}
		}
	}

	@Override
	protected boolean shouldBurn() {
		return false;
	}

	public void addAdditionalSaveData(CompoundTag p_37222_) {
		super.addAdditionalSaveData(p_37222_);
		p_37222_.putByte("DetectionRange", (byte) this.detectionRange);
	}

	public void readAdditionalSaveData(CompoundTag p_37220_) {
		super.readAdditionalSaveData(p_37220_);
		if (p_37220_.contains("DetectionRange", 99)) {
			this.detectionRange = p_37220_.getByte("DetectionRange");
		}

	}
}
