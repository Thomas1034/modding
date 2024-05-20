package com.thomas.verdant.item.custom;

import com.thomas.verdant.entity.custom.PoisonIvyArrowEntity;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class PoisonIvyArrowItem extends ArrowItem {

	public PoisonIvyArrowItem(Properties properties) {
		super(properties);
	}

	@Override
	public AbstractArrow createArrow(Level level, ItemStack stack, LivingEntity archer) {
		PoisonIvyArrowEntity arrow = new PoisonIvyArrowEntity(level, archer);
		//System.out.println("Created new " + arrow.getClass());
		return arrow;
	}

	@Override
	public boolean isInfinite(ItemStack stack, ItemStack bow, net.minecraft.world.entity.player.Player player) {
		return false;
		
	}

}
