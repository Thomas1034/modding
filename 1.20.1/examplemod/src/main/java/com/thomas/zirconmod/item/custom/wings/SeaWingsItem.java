package com.thomas.zirconmod.item.custom.wings;

import com.thomas.zirconmod.ZirconMod;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class SeaWingsItem extends AbstractWaterWingsItem {

	public SeaWingsItem(Properties properties) {
		super(properties, 0.05f, 8f);
	}

	@Override
	public ResourceLocation getTextureLocation() {
		return new ResourceLocation(ZirconMod.MOD_ID, "textures/entity/feather_wings.png");
	}

	@Override
	public boolean isValidRepairItem(ItemStack thisStack, ItemStack repairItem) {
		return repairItem.is(Items.PRISMARINE_CRYSTALS);
	}

}
