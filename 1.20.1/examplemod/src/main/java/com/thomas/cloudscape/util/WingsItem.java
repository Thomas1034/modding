package com.thomas.cloudscape.util;

import com.thomas.cloudscape.Cloudscape;
import com.thomas.cloudscape.item.custom.wings.AbstractWingsItem;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public interface WingsItem {
	
	default ResourceLocation getTextureLocation() {
		return new ResourceLocation(Cloudscape.MOD_ID, "textures/entity/wings/" + this.getTextureName() + ".png");
	}
	
	default String getTextureName() {
		return "elytra";
	}
	
	default SoundEvent getEquipSound() {
		return SoundEvents.ARMOR_EQUIP_ELYTRA;
	}
	
	default void decreaseDurability(ItemStack thisStack, LivingEntity wearer) {
		decreaseDurabilityPublic(thisStack, wearer);
	}
	
	default void decreaseDurability(ItemStack thisStack, LivingEntity wearer, int amount) {
		decreaseDurabilityPublic(thisStack, wearer, amount);
	}
	
	default Item breaksInto() {
		return Items.ELYTRA;
	}

	public static void decreaseDurabilityPublic(ItemStack thisStack, LivingEntity wearer) {
		thisStack.hurtAndBreak(1, wearer, e -> e.broadcastBreakEvent(net.minecraft.world.entity.EquipmentSlot.CHEST));
		if (!AbstractWingsItem.isFlyEnabled(thisStack)) {
			wearer.setItemSlot(EquipmentSlot.CHEST, new ItemStack(((WingsItem) thisStack.getItem()).breaksInto()));
		}
	}
	

	public static void decreaseDurabilityPublic(ItemStack thisStack, LivingEntity wearer, int amount) {
		thisStack.hurtAndBreak(amount, wearer, e -> e.broadcastBreakEvent(net.minecraft.world.entity.EquipmentSlot.CHEST));
		if (!AbstractWingsItem.isFlyEnabled(thisStack)) {
			wearer.setItemSlot(EquipmentSlot.CHEST, new ItemStack(((WingsItem) thisStack.getItem()).breaksInto()));
		}
	}
}
