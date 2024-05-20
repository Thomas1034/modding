package com.thomas.verdant.entity.custom;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.level.Level;

public abstract class ModArrowEntity extends AbstractArrow {

	public ModArrowEntity(EntityType<? extends AbstractArrow> type, Level level) {
		super(type, level);
	}

	public ModArrowEntity(EntityType<? extends AbstractArrow> type, Level level, double x, double y, double z) {
		super(type, x, y, z, level);
	}

	public ModArrowEntity(EntityType<? extends AbstractArrow> type, Level level, LivingEntity archer) {
		super(type, archer, level);
	}
}
