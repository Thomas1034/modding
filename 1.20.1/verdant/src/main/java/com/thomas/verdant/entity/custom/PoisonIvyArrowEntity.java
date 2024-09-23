package com.thomas.verdant.entity.custom;

import com.thomas.verdant.entity.ModEntityTypes;
import com.thomas.verdant.item.ModItems;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;

public class PoisonIvyArrowEntity extends ModArrowEntity {

	public PoisonIvyArrowEntity(EntityType<? extends PoisonIvyArrowEntity> type, Level level) {
		super(type, level);
	}

	public PoisonIvyArrowEntity(Level level, double x, double y, double z) {
		super(ModEntityTypes.POISON_IVY_ARROW.get(), level, x, y, z);
	}

	public PoisonIvyArrowEntity(Level level, LivingEntity archer) {
		super(ModEntityTypes.POISON_IVY_ARROW.get(), level, archer);
	}

	// Apply poison to the hit entity, if it is a living entity.
	@Override
	public void onHitEntity(EntityHitResult hitResult) {
		super.onHitEntity(hitResult);
		Entity target = hitResult.getEntity();
		Entity owner = this.getOwner();

		if (target instanceof LivingEntity livingTarget && !target.level().isClientSide) {
			livingTarget.addEffect(new MobEffectInstance(MobEffects.POISON, 40, 1), owner);
		}
	}

	@Override
	protected ItemStack getPickupItem() {
		// System.out.println("Picking up.");
		return new ItemStack(ModItems.POISON_ARROW.get(), 1);
	}

}
