package com.thomas.verdant.entity.custom;

import com.thomas.verdant.block.ModBlocks;
import com.thomas.verdant.block.custom.RopeBlock;
import com.thomas.verdant.entity.ModEntityTypes;
import com.thomas.verdant.item.ModItems;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

public class ThrownRopeEntity extends ThrowableItemProjectile {

	public static final EntityDataAccessor<Integer> DATA_ID_LENGTH = SynchedEntityData.defineId(ThrownRopeEntity.class,
			EntityDataSerializers.INT);
	public static final String DATA_NAME_LENGTH = "Length";

	public ThrownRopeEntity(EntityType<? extends ThrowableItemProjectile> type, LivingEntity thrower, Level level) {
		super(type, thrower, level);
	}

	public ThrownRopeEntity(Level level, LivingEntity thrower) {
		super(ModEntityTypes.THROWN_ROPE.get(), thrower, level);
	}

	public ThrownRopeEntity(EntityType<? extends ThrowableItemProjectile> type, Level level) {
		super(type, level);
	}

	public ThrownRopeEntity(Level level) {
		super(ModEntityTypes.THROWN_ROPE.get(), level);
	}

	@Override
	protected Item getDefaultItem() {
		return ModItems.ROPE_COIL.get();
	}

	@Override
	protected void onHitBlock(BlockHitResult hitResult) {
		super.onHitBlock(hitResult);
		// The level.
		Level level = this.level();

		// Ensure server side.
		if (level.isClientSide) {
			return;
		}

		// Check if it hit a rope.
		BlockPos hitpos = hitResult.getBlockPos();
		BlockState hitState = level.getBlockState(hitpos);
		BlockPos pos;
		if (hitState.is(ModBlocks.ROPE.get())) {
			// Extend the rope, find its bottom.
			pos = hitpos.below();
			while (level.getBlockState(pos).is(ModBlocks.ROPE.get())) {
				pos = pos.below();
			}
		} else {
			// If not, place a rope offset from the block that was hit.
			pos = hitResult.getBlockPos().relative(hitResult.getDirection());
		}

		// The state of the block it was in when it hit.
		BlockState state = level.getBlockState(pos);
		// Rope block
		RopeBlock rope = ((RopeBlock) ModBlocks.ROPE.get());

		// Check if it can place a rope at the given position.
		boolean canPlace = this.level().getBlockState(pos).isAir() && rope.canSurvive(state, this.level(), pos);

		// Store the maximum length of the rope.
		int i = this.getLength();

		// The end of the line.
		BlockPos.MutableBlockPos mutpos = new BlockPos.MutableBlockPos().set(pos);

		if (canPlace) {

			// If a rope can be placed, continue placing them downwards.

			while (level.getBlockState(mutpos).is(BlockTags.REPLACEABLE) && i > 0) {
				setRope(level, mutpos);
				i--;
				mutpos.move(Direction.DOWN);
			}
		}

		Vec3 droppos = canPlace ? mutpos.above().getCenter() : mutpos.getCenter();
		// Drop leftover rope.
		level.addFreshEntity(
				new ItemEntity(level, droppos.x, droppos.y, droppos.z, new ItemStack(ModBlocks.ROPE.get(), i)));

		// Discard the entity
		this.discard();
	}

	// Sets a rope with an effect.
	private void setRope(Level level, BlockPos pos) {
		BlockState rope = ModBlocks.ROPE.get().defaultBlockState();
		level.addDestroyBlockEffect(pos, rope);
		level.destroyBlock(pos, true);
		level.setBlockAndUpdate(pos, rope);
	}

	@Override
	protected boolean canHitEntity(Entity entity) {
		return false;
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(DATA_ID_LENGTH, 0);
	}

	@Override
	public void readAdditionalSaveData(CompoundTag tag) {
		super.readAdditionalSaveData(tag);
		this.entityData.set(DATA_ID_LENGTH, tag.getInt(DATA_NAME_LENGTH));
	}

	@Override
	public void addAdditionalSaveData(CompoundTag tag) {
		super.addAdditionalSaveData(tag);
		tag.putInt(DATA_NAME_LENGTH, this.getLength());
	}

	public int getLength() {
		return this.entityData.get(DATA_ID_LENGTH);
	}

	public void setLength(int length) {
		this.entityData.set(DATA_ID_LENGTH, length);
	}

}
