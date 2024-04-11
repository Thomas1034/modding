package com.thomas.cloudscape.item.custom;

import com.thomas.cloudscape.effect.ModEffects;
import com.thomas.cloudscape.network.ModPacketHandler;
import com.thomas.cloudscape.network.PlayerAddVelocityPacket;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class WindBagItem extends Item {

	private double power;
	private int duration;

	public WindBagItem(Properties properties, double power, int duration) {
		super(properties);
		this.power = power;
		this.duration = duration;
	}

	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
		ItemStack itemstack = player.getItemInHand(hand);
		
		player.awardStat(Stats.ITEM_USED.get(this));

		if (player instanceof ServerPlayer sp) {
			// Damage the item.
			if (!player.getAbilities().instabuild) {
				itemstack.hurtAndBreak(1, player, e -> e.broadcastBreakEvent(hand));
			}
			
			// Refill air. Because why not.
			sp.setAirSupply(sp.getMaxAirSupply());
			// Also reset fall distance.
			sp.resetFallDistance();

			// Propel if flying
			if (sp.isFallFlying()) {
				
				sp.addEffect(new MobEffectInstance(ModEffects.PROPELLED.get(), this.duration, (int) this.power * 5));
				// Set a cooldown.
				player.getCooldowns().addCooldown(this, this.duration);
			} else if (!sp.isFallFlying()) {
				// Otherwise, burst charge.
				Vec3 charge = sp.getLookAngle().scale(this.power);
				charge = charge.multiply(1f, 0.75f, 1f);
				if (charge.y < 0) {
					charge = charge.multiply(1f, 0.75f, 1f);
				}

				// Invert if holding shift.
				if (sp.isShiftKeyDown()) {
					charge = charge.scale(-1);
				}
				
				ModPacketHandler.sendToPlayer(new PlayerAddVelocityPacket(charge), sp);
				// Set a cooldown.
				player.getCooldowns().addCooldown(this, (int) (this.power * 10));
			}
		}

		return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
	}
}
