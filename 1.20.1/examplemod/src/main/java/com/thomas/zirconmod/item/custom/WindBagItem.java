package com.thomas.zirconmod.item.custom;

import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class WindBagItem extends Item {

	private double power;
	private Item onUse;

	public WindBagItem(Properties properties, double power) {
		super(properties);
		this.power = power;
		this.onUse = Items.AIR;
	}

	public WindBagItem(Properties properties, double power, Item onUse) {
		super(properties);
		this.power = power;
		this.onUse = onUse;
	}

	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
		ItemStack itemstack = player.getItemInHand(hand);

		player.awardStat(Stats.ITEM_USED.get(this));

		if (!player.getAbilities().instabuild) {
			itemstack.hurtAndBreak(1, player, e -> e.broadcastBreakEvent(hand));
			if (!level.isClientSide && itemstack.getCount() == 0) {
				player.addItem(new ItemStack(this.onUse));
			}
		}

		if (player instanceof ServerPlayer sp) {

			// Refill air. Because why not.
			sp.setAirSupply(sp.getMaxAirSupply());
			
			
			
			// Set a cooldown.
			player.getCooldowns().addCooldown(this, (int) (10 * this.power));

		} else if (player instanceof AbstractClientPlayer acp) {

			Vec3 charge;
			// Boost if not flying
			if (!acp.isFallFlying()) {
				charge = acp.getLookAngle().scale(this.power).multiply(1, 1.5, 1);
			}
			// Gust if flying (TODO)
			else {
				charge = acp.getLookAngle().scale(this.power / 2);
			}

			acp.addDeltaMovement(charge);

		}

		return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
	}
}
