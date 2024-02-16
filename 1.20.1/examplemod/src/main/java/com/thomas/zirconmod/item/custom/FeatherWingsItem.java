package com.thomas.zirconmod.item.custom;

import com.thomas.zirconmod.ZirconMod;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class FeatherWingsItem extends AbstractFlappingWingsItem {

	public FeatherWingsItem(Properties properties) {
		super(properties, 0.3f, 0.3f, 1.5f);
	}
	
	@Override
	public ResourceLocation getTexture() {
		return new ResourceLocation(ZirconMod.MOD_ID, "textures/entity/feather_wings.png");
	}

	@Override
	public boolean isValidRepairItem(ItemStack thisStack, ItemStack repairItem) {
		return repairItem.is(net.minecraftforge.common.Tags.Items.FEATHERS) || repairItem.is(Items.HONEYCOMB);
	}

	// When the feather wings break, replaces with an elytra.
	@Override
	protected void decreaseDurability(ItemStack thisStack, LivingEntity wearer) {
		if (thisStack.getDamageValue() == thisStack.getMaxDamage() - 2) {
			if (wearer.getItemBySlot(this.getEquipmentSlot()).is(thisStack.getItem()))
				wearer.setItemSlot(this.getEquipmentSlot(), new ItemStack(Items.ELYTRA));
		} else {
			super.decreaseDurability(thisStack, wearer);
		}
	}
}
