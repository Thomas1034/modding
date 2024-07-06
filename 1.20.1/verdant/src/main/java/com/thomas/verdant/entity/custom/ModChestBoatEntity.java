package com.thomas.verdant.entity.custom;

import com.thomas.verdant.entity.ModEntityType;
import com.thomas.verdant.item.ModItems;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.entity.vehicle.ChestBoat;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

public class ModChestBoatEntity extends ChestBoat {
	private static final EntityDataAccessor<Integer> DATA_ID_TYPE = SynchedEntityData.defineId(Boat.class,
			EntityDataSerializers.INT);

	public ModChestBoatEntity(EntityType<? extends ChestBoat> pEntityType, Level pLevel) {
		super(pEntityType, pLevel);
	}

	public ModChestBoatEntity(Level pLevel, double x, double y, double z) {
		this(ModEntityType.VERDANT_CHEST_BOAT.get(), pLevel);
		this.setPos(x, y, z);
		this.xo = x;
		this.yo = y;
		this.zo = z;
	}

	@Override
	public Item getDropItem() {
		switch (getModVariant()) {
		case VERDANT -> ModItems.VERDANT_BOAT.get();
		case VERDANT_HEARTWOOD -> ModItems.VERDANT_HEARTWOOD_BOAT.get();
		}
		return super.getDropItem();
	}

	public void setVariant(ModBoatEntity.Type variant) {
		this.entityData.set(DATA_ID_TYPE, variant.ordinal());
	}

	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(DATA_ID_TYPE, ModBoatEntity.Type.VERDANT.ordinal());
	}

	protected void addAdditionalSaveData(CompoundTag tag) {
		tag.putString("Type", this.getModVariant().getSerializedName());
	}

	protected void readAdditionalSaveData(CompoundTag tag) {
		if (tag.contains("Type", 8)) {
			this.setVariant(ModBoatEntity.Type.byName(tag.getString("Type")));
		}
	}

	public ModBoatEntity.Type getModVariant() {
		return ModBoatEntity.Type.byId(this.entityData.get(DATA_ID_TYPE));
	}
}