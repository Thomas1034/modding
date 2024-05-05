package com.thomas.cloudscape.enchantment.custom;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class WindBagEnchantment extends Enchantment {

	public WindBagEnchantment() {
		super(Enchantment.Rarity.UNCOMMON, EnchantmentCategory.BREAKABLE,
				new EquipmentSlot[] { EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND });
	}

	@Override
	public boolean isAllowedOnBooks() {
		return true;
	}

	@Override
	public boolean isTradeable() {
		return false;
	}

	@Override
	public int getMaxLevel() {
		return 2;
	}

}
