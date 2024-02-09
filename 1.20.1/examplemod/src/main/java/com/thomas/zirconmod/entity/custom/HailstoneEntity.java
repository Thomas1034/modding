package com.thomas.zirconmod.entity.custom;

import com.thomas.zirconmod.effect.ModEffects;
import com.thomas.zirconmod.entity.ModEntities;
import com.thomas.zirconmod.item.ModItems;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Blaze;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.TheEndGatewayBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class HailstoneEntity extends ThrowableItemProjectile {

	public HailstoneEntity(EntityType<? extends HailstoneEntity> template, Level level) {
		super(template, level);
	}

	public HailstoneEntity(EntityType<? extends HailstoneEntity> template, double x, double y, double z, Level level) {
		this(template, level);
		this.setPos(x, y, z);
	}

	public HailstoneEntity(EntityType<? extends HailstoneEntity> template, LivingEntity owner, Level level) {
		this(template, owner.getX(), owner.getEyeY() - (double) 0.1F, owner.getZ(), level);
		this.setOwner(owner);
	}

	public HailstoneEntity(Level level) {
		super(ModEntities.HAILSTONE_ENTITY.get(), level);
	}

	public HailstoneEntity(double x, double y, double z, Level level) {
		this(ModEntities.HAILSTONE_ENTITY.get(), level);
		this.setPos(x, y, z);
	}

	public HailstoneEntity(LivingEntity owner, Level level) {
		this(ModEntities.HAILSTONE_ENTITY.get(), owner.getX(), owner.getEyeY() - (double) 0.1F, owner.getZ(), level);
		this.setOwner(owner);
	}

	public HailstoneEntity(Level level, LivingEntity owner) {
		this(ModEntities.HAILSTONE_ENTITY.get(), owner.getX(), owner.getEyeY() - (double) 0.1F, owner.getZ(), level);
		this.setOwner(owner);
	}

	@Override
	protected Item getDefaultItem() {
		return ModItems.HAILSTONE.get();
	}

	private ParticleOptions getParticle() {
		ItemStack itemstack = this.getItemRaw();
		return (ParticleOptions) (itemstack.isEmpty() ? ParticleTypes.ITEM_SNOWBALL
				: new ItemParticleOption(ParticleTypes.ITEM, itemstack));
	}

	public void handleEntityEvent(byte inputByte) {
		if (inputByte == 3) {
			ParticleOptions particleoptions = this.getParticle();

			for (int i = 0; i < 8; ++i) {
				this.level().addParticle(particleoptions, this.getX(), this.getY(), this.getZ(), 0.0D, 0.0D, 0.0D);
			}
		}

	}

	protected void onHitEntity(EntityHitResult hitResult) {
		super.onHitEntity(hitResult);
		Entity entity = hitResult.getEntity();
		int i = entity instanceof Blaze ? 12 : 5;
		if (entity instanceof LivingEntity le && le.level() instanceof ServerLevel) {
			le.addEffect(new MobEffectInstance(ModEffects.FREEZING.get(), 80), this.getOwner());
			this.discard();
		}
		entity.hurt(this.damageSources().thrown(this, this.getOwner()), (float) i);
	}

	@SuppressWarnings("resource")
	protected void onHit(HitResult hitResult) {
		super.onHit(hitResult);

		if (!this.level().isClientSide) {
			this.level().broadcastEntityEvent(this, (byte) 3);
			if (hitResult instanceof BlockHitResult blockHit) {
				this.discard();
				BlockPos pos = blockHit.getBlockPos();
				boolean canFreeze = net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.level(),
						this.getOwner());
				if (this.level().getBlockState(pos).is(Blocks.WATER) && this.level().getBlockState(pos.above()).isAir()
						&& canFreeze) {
					this.level().setBlockAndUpdate(pos, Blocks.FROSTED_ICE.defaultBlockState());
				}
				if (this.level().getBlockState(this.blockPosition()).is(Blocks.WATER)
						&& this.level().getBlockState(this.blockPosition().above()).isAir() && canFreeze) {
					this.level().setBlockAndUpdate(this.blockPosition(), Blocks.FROSTED_ICE.defaultBlockState());
				}
			}
		}
	}

	// Mostly just from the super implementation. Just needed a few tweaks.
	@Override
	public void tick() {
		//super.tick();
		HitResult hitresult = ProjectileUtil.getHitResultOnMoveVector(this, this::canHitEntity);
		boolean flag = false;
		if (hitresult.getType() == HitResult.Type.BLOCK) {
			BlockPos blockpos = ((BlockHitResult) hitresult).getBlockPos();
			BlockState blockstate = this.level().getBlockState(blockpos);
			if (blockstate.is(Blocks.NETHER_PORTAL)) {
				this.handleInsidePortal(blockpos);
				flag = true;
			} else if (blockstate.is(Blocks.END_GATEWAY)) {
				BlockEntity blockentity = this.level().getBlockEntity(blockpos);
				if (blockentity instanceof TheEndGatewayBlockEntity
						&& TheEndGatewayBlockEntity.canEntityTeleport(this)) {
					TheEndGatewayBlockEntity.teleportEntity(this.level(), blockpos, blockstate, this,
							(TheEndGatewayBlockEntity) blockentity);
				}

				flag = true;
			}
		}

		if (hitresult.getType() != HitResult.Type.MISS && !flag
				&& !net.minecraftforge.event.ForgeEventFactory.onProjectileImpact(this, hitresult)) {
			this.onHit(hitresult);
		}

		this.checkInsideBlocks();
		Vec3 vec3 = this.getDeltaMovement();
		double d2 = this.getX() + vec3.x;
		double d0 = this.getY() + vec3.y;
		double d1 = this.getZ() + vec3.z;
		this.updateRotation();
		float f;
		if (this.isInWater()) {
			for (int i = 0; i < 4; ++i) {
				this.level().addParticle(ParticleTypes.BUBBLE, d2 - vec3.x * 0.25D, d0 - vec3.y * 0.25D,
						d1 - vec3.z * 0.25D, vec3.x, vec3.y, vec3.z);
			}

			f = 0.8F;
		} else {
			// No air resistance.
			f = 1.0F;
		}

		this.setDeltaMovement(vec3.scale((double) f));
		if (!this.isNoGravity()) {
			Vec3 vec31 = this.getDeltaMovement();
			this.setDeltaMovement(vec31.x, vec31.y - (double) this.getGravity(), vec31.z);
		}
		
		// If it is standing still, die.
		if (this.getDeltaMovement().length() < 0.01) {
			this.discard();
			return;
		}

		this.setPos(d2, d0, d1);
	}

}
