package com.thomas.zirconmod.item.custom.wings;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class FeatherWingsItem extends AbstractFlappingWingsItem {

	public FeatherWingsItem(Properties properties) {
		super(properties, 0.3f, 0.3f, 1.5f);
	}

	@Override
	public String getTextureName() {
		return "feather";
	}

	@Override
	public boolean isValidRepairItem(ItemStack thisStack, ItemStack repairItem) {
		return repairItem.is(net.minecraftforge.common.Tags.Items.FEATHERS) || repairItem.is(Items.HONEYCOMB);
	}
}
