package com.thomas.cloudscape.item.custom.wings;

import com.thomas.cloudscape.ZirconMod;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class DragonflyWingsItem extends AbstractHoverWingsItem {

	public DragonflyWingsItem(Properties properties) {
		super(properties);
	}

	@Override
	public ResourceLocation getTextureLocation() {
		return new ResourceLocation(ZirconMod.MOD_ID, "textures/entity/feather_wings.png");
	}

	@Override
	public boolean isValidRepairItem(ItemStack thisStack, ItemStack repairItem) {
		return repairItem.is(Items.DRAGON_BREATH);
	}

}
