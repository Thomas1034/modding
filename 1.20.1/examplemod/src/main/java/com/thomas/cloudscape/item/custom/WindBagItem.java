package com.thomas.cloudscape.item.custom;

import com.thomas.cloudscape.effect.ModEffects;
import com.thomas.cloudscape.enchantment.ModEnchantments;
import com.thomas.cloudscape.util.MotionHelper;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;

public class WindBagItem extends Item {

	private double power;
	private int duration;
	private Item useRemainder;

	public WindBagItem(Properties properties, double power, int duration, Item remainder) {
		super(properties);
		this.power = power;
		this.duration = duration;
		this.useRemainder = remainder;
	}

	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
		return ItemUtils.startUsingInstantly(level, player, hand);
	}

	public int getUseDuration(ItemStack stack) {
		return 1;
	}

	public UseAnim getUseAnimation(ItemStack stack) {
		return UseAnim.BLOCK;
	}

	public ItemStack finishUsingItem(ItemStack usedItem, Level level, LivingEntity user) {
		// If the item is being used by a player, then get that player.
		// Otherwise, it's null.
		Player player = user instanceof Player ? (Player) user : null;

		// If the player is a server player, trigger any criteria that rely on this item
		// being used.
		if (player instanceof ServerPlayer) {
			CriteriaTriggers.CONSUME_ITEM.trigger((ServerPlayer) player, usedItem);
		}

		// If the level is not client side, trigger its use.
		if (!level.isClientSide) {
			// Save the power, can be modified by enchantments.
			double power = this.power;

			// The player using the item.
			ServerPlayer serverPlayer = (ServerPlayer) player;

			// Amplify the power based on the enchantments.
			Integer surgeLevel = usedItem.getAllEnchantments().get(ModEnchantments.SURGE.get());
			power *= 1 + (surgeLevel == null ? 0 : 0.25 * surgeLevel);

			// Get the direction this player is looking.
			Vec3 lookAngle = serverPlayer.getLookAngle().normalize();
			// Get this player's velocity.
			Vec3 velocity = MotionHelper.getVelocity(serverPlayer);
			// Calculates the component of the velocity in the direction of the player's
			// look angle.
			double lookComponent = lookAngle.dot(velocity);
			// The velocity to be added
			Vec3 addedVelocity = Vec3.ZERO;

			// If the component in the direction of the player's look angle is less than the
			// strength of the item, boost accordingly.
			if (lookComponent < power) {
				addedVelocity = lookAngle.scale(power - lookComponent);
			}

			// Reverse if crouching.
			if (serverPlayer.isShiftKeyDown()) {
				// System.out.println("Holding down shift.");
				addedVelocity = addedVelocity.reverse();
			} else {
				// System.out.println("Not holding down shift.");
			}

			// Add the propulsion effect.
			serverPlayer.addEffect(new MobEffectInstance(ModEffects.PROPELLED.get(), this.duration * 4,
					(int) (this.power) * 5, false, true), serverPlayer);

			// Reset fall distance if it was aimed upward.
			if (addedVelocity.y > 0) {
				serverPlayer.resetFallDistance();
			}

			// Actually add the velocity
			MotionHelper.addVelocity(serverPlayer, addedVelocity);

			// Add a cooldown.
			double cooldown = this.duration;
			Integer quickChargeLevel = usedItem.getAllEnchantments().get(Enchantments.QUICK_CHARGE);
			cooldown /= 1 + (quickChargeLevel == null ? 1 : quickChargeLevel);
			serverPlayer.getCooldowns().addCooldown(usedItem.getItem(), (int) cooldown);

			// System.out.println("Used! With stats:");
			// System.out.println("Power: " + this.power);
			// System.out.println("Duration: " + this.duration);

		}

		// If the player is using the item, award a statistic for it being used.
		// Then use it up.
		if (player != null) {
			player.awardStat(Stats.ITEM_USED.get(this));
			if (!player.getAbilities().instabuild) {
				// System.out.println("Damaging item.");
				usedItem.setDamageValue(usedItem.getDamageValue() + 1);

				if (usedItem.getDamageValue() == usedItem.getMaxDamage()) {
					// System.out.println("Item is used up.");
					usedItem.shrink(1);
				} else {
					// System.out.println("Item is not used up.");
				}
				// System.out.println("Shrinking item stack.");
			}
			// System.out.println("Not shrinking item stack.");
		}

		// If the entity using the item is not a player or the player is not in
		// creative,
		// check if the item has been used up. If so, return the empty version of this
		// item.
		if (player == null || !player.getAbilities().instabuild)

		{
			if (usedItem.isEmpty()) {
				return new ItemStack(this.useRemainder);
			}

			// if (player != null) {
			// player.getInventory().add(new ItemStack(this.useRemainder));
			// }
		}

		// Trigger a game event, for sculk and such stuff.
		user.gameEvent(GameEvent.ITEM_INTERACT_FINISH);
		return usedItem;
	}


	// TODO Add enchantment for blasting away close entities
	// Call it Burst, three levels for increased radius and effect.
	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
		return super.canApplyAtEnchantingTable(stack, enchantment) || enchantment == ModEnchantments.SURGE.get()
				|| enchantment == Enchantments.QUICK_CHARGE;
	}

}
