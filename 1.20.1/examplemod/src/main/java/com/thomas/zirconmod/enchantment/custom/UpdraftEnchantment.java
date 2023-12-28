package com.thomas.zirconmod.enchantment.custom;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class UpdraftEnchantment extends Enchantment {
	
	public UpdraftEnchantment() {
        super(Enchantment.Rarity.VERY_RARE, EnchantmentCategory.WEAPON, new EquipmentSlot[]{EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND});
	}

	@Override
	public void doPostAttack(LivingEntity attacker, Entity target, int level) {
	    double knockbackResistance = (target instanceof LivingEntity) ? 
	    		((LivingEntity)target).getAttribute(Attributes.KNOCKBACK_RESISTANCE).getValue() 
	    		: 
	    		0;
		
		target.setDeltaMovement(target.getDeltaMovement().add(0, (1-knockbackResistance) * 0.2*(level), 0));
	    
	}
	
	@Override
	public boolean isAllowedOnBooks()
	{
		return true;
	}
	
	@Override
	public boolean isTradeable()
	{
		return true;
	}
	
	@Override
	public int getMaxLevel() {
		return 3;
	}
}