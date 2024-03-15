package com.thomas.zirconmod.item.custom;

import com.thomas.zirconmod.effect.ModEffects;

import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class WindBagItem extends Item {

	private double power;
	private int duration;
	private Item onUse;

	public WindBagItem(Properties properties, double power, int duration) {
		super(properties);
		this.power = power;
		this.onUse = Items.AIR;
		this.duration = duration;
	}

	public WindBagItem(Properties properties, double power, int duration, Item onUse) {
		super(properties);
		this.power = power;
		this.onUse = onUse;
		this.duration = duration;
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

			// Propel if flying
			if (sp.isFallFlying()) {
				sp.addEffect(new MobEffectInstance(ModEffects.PROPELLED.get(), this.duration, (int) this.power * 5));
				// Set a cooldown.
				player.getCooldowns().addCooldown(this, this.duration);
			}

		} else if (player instanceof AbstractClientPlayer acp) {

			// Boost if not flying
			if (!acp.isFallFlying()) {
				Vec3 charge = acp.getLookAngle().scale(this.power).multiply(1, 1.5, 1);
				acp.addDeltaMovement(charge);
				// Set a cooldown.
				player.getCooldowns().addCooldown(this, (int) (this.power * 10));
			}

		}

		return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
	}
}
