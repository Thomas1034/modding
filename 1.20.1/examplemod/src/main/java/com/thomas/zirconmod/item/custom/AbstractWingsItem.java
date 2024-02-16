package com.thomas.zirconmod.item.custom;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ElytraItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public abstract class AbstractWingsItem extends ElytraItem {

	public AbstractWingsItem(Properties p_41132_) {
		super(p_41132_);
	}

	public static boolean isFlyEnabled(ItemStack p_41141_) {
		return p_41141_.getDamageValue() < p_41141_.getMaxDamage() - 1;
	}

	public abstract boolean isValidRepairItem(ItemStack p_41134_, ItemStack p_41135_);

	public InteractionResultHolder<ItemStack> use(Level p_41137_, Player p_41138_, InteractionHand p_41139_) {
		return this.swapWithEquipmentSlot(this, p_41137_, p_41138_, p_41139_);
	}

	@Override
	public boolean canElytraFly(ItemStack stack, net.minecraft.world.entity.LivingEntity entity) {
		return ElytraItem.isFlyEnabled(stack);
	}

	@SuppressWarnings("resource")
	@Override
	public boolean elytraFlightTick(ItemStack stack, LivingEntity entity, int flightTicks) {
		if (!entity.level().isClientSide) {
			int nextFlightTick = flightTicks + 1;
			if (nextFlightTick % 10 == 0) {
				if (nextFlightTick % 20 == 0) {
					decreaseDurability(stack, entity);
					entity.level().playSound(entity, entity.blockPosition(), SoundEvents.ENDER_DRAGON_FLAP, null, 1, 1);
				}
				
				entity.gameEvent(net.minecraft.world.level.gameevent.GameEvent.ELYTRA_GLIDE);
			}
		}
		return true;
	}

	public SoundEvent getEquipSound() {
		return SoundEvents.ARMOR_EQUIP_ELYTRA;
	}

	protected void decreaseDurability(ItemStack thisStack, LivingEntity wearer) {
		decreaseDurabilityPublic(thisStack, wearer);
	}

	public static void decreaseDurabilityPublic(ItemStack thisStack, LivingEntity wearer) {
		thisStack.hurtAndBreak(1, wearer, e -> e.broadcastBreakEvent(net.minecraft.world.entity.EquipmentSlot.CHEST));

	}

	public abstract ResourceLocation getTexture();
}
