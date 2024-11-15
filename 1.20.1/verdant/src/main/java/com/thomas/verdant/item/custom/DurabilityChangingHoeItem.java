package com.thomas.verdant.item.custom;

import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;

public class DurabilityChangingHoeItem extends HoeItem {

	private final int changesEvery;
	private final int changesBy;

	public DurabilityChangingHoeItem(Tier tier, int i, float f, int changesBy, int changesEvery,
			Properties properties) {
		super(tier, i, f, properties);
		this.changesBy = changesBy;
		this.changesEvery = changesEvery;
	}

	@Override
	public void inventoryTick(ItemStack stack, Level level, Entity holder, int iInt, boolean isHeld) {
		// if (isHeld) {
		if (holder instanceof Player player && !player.getAbilities().instabuild) {
			changeDurability(stack, level, holder);
		}
		if (!(holder instanceof Player)) {
			changeDurability(stack, level, holder);
		}
		// }
	}

	// Override the inventory tick function. It will now change durability over
	// time.
	public void changeDurability(ItemStack thisItem, Level level, Entity owner) {
		// Damage the items that the owner is holding if they are of this type.
		int damage = thisItem.getDamageValue();
		damage += getDamageAmt(level.random, thisItem.getMaxDamage());
		if (thisItem.getMaxDamage() <= damage) {
			thisItem.shrink(1);
		} else if (damage < 0) {
			damage = 0;
		}
		thisItem.setDamageValue(damage);
	}

	private int getDamageAmt(RandomSource rand, int maxDamage) {
		int randomval = rand.nextInt(this.changesEvery);
		int retval = randomval == 0 ? this.changesBy : 0;
		return retval;
	}
}