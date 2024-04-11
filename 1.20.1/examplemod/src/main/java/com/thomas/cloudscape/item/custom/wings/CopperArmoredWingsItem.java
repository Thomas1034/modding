package com.thomas.cloudscape.item.custom.wings;

import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class CopperArmoredWingsItem extends ArmoredWingsItem {

	public CopperArmoredWingsItem(ArmorMaterial material, Type type, Properties properties) {
		super(material, type, properties);
	}

	@Override
	public void inventoryTick(ItemStack stack, Level level, Entity holder, int iInt, boolean isHeld) {
		super.inventoryTick(stack, level, holder, iInt, isHeld);
		if (isHeld)
			oxidize(stack, level, holder);
	}
	
	@SuppressWarnings("removal")
	@Override
	public void onArmorTick(ItemStack stack, Level level, Player player)
	{
		super.onArmorTick(stack, level, player);
		if(!player.getAbilities().instabuild) oxidize(stack, level, player);
	}

	// Override the inventory tick function. It will now decay over time.
	public void oxidize(ItemStack thisItem, Level level, Entity owner) {
		// Damage the items that the owner is holding if they are of this type.
		int damage = thisItem.getDamageValue();
		damage += getDamageAmt(level.random, thisItem.getMaxDamage());
		if (thisItem.getMaxDamage() <= damage)
			thisItem.shrink(1);
		thisItem.setDamageValue(damage);
	}

	private int getDamageAmt(RandomSource rand, int maxDamage) {
		int ticks = 600;
		int randomval = rand.nextInt(ticks);
		int retval = randomval == 0 ? 1 : 0;
		return retval;
	}

}
