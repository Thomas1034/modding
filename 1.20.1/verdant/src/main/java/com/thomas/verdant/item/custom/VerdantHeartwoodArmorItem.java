package com.thomas.verdant.item.custom;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class VerdantHeartwoodArmorItem extends ArmorItem {

	private final int ticks;

	public VerdantHeartwoodArmorItem(ArmorMaterial material, Type type, Properties properties, int ticks) {
		super(material, type, properties);
		this.ticks = ticks;
	}

	// Override the inventory tick function. It will now grow over time.
	@Override
	public void inventoryTick(ItemStack stack, Level level, Entity holder, int i, boolean isHeld) {
		super.inventoryTick(stack, level, holder, i, isHeld);
		if (isHeld && !(holder instanceof ServerPlayer sp && sp.getAbilities().instabuild))
			regrow(stack, level, holder);
	}

	@SuppressWarnings("removal")
	@Override
	public void onArmorTick(ItemStack stack, Level level, Player player) {
		super.onArmorTick(stack, level, player);
		if (!player.getAbilities().instabuild)
			regrow(stack, level, player);
	}

	public void regrow(ItemStack thisItem, Level level, Entity owner) {
		int damage = thisItem.getDamageValue();
		damage -= getDamageAmt(level.random, thisItem.getMaxDamage());
		if (thisItem.getMaxDamage() <= damage)
			thisItem.shrink(1);
		if (damage < 0)
			damage = 0;
		thisItem.setDamageValue(damage);
	}

	private int getDamageAmt(RandomSource rand, int maxDamage) {
		int randomval = rand.nextInt(this.ticks);
		int retval = randomval == 0 ? 1 : 0;
		return retval;
	}

}
