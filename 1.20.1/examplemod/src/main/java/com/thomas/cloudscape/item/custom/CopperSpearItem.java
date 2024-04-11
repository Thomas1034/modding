package com.thomas.cloudscape.item.custom;

import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;

public class CopperSpearItem extends SpearItem {

	public CopperSpearItem(Tier tier, int p_43270_, float p_43271_, Properties properties_) {
		super(tier, p_43270_, p_43271_, properties_);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void inventoryTick(ItemStack stack, Level level, Entity holder, int iInt, boolean isHeld) {
		if (isHeld) {
			if (holder instanceof Player player && !player.getAbilities().instabuild) {
				oxidize(stack, level, holder);
			}
			if (!(holder instanceof Player)) {
				oxidize(stack, level, holder);
			}
		}
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
		int ticks = 120;
		int randomval = rand.nextInt(ticks);
		int retval = randomval == 0 ? 1 : 0;
		return retval;
	}

}
