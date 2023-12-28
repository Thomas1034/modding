package com.thomas.zirconmod.item.custom;

import com.thomas.zirconmod.ZirconMod;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class SeaWingsItem extends AbstractWaterWingsItem {

	public SeaWingsItem(Properties properties) {
		super(properties, 0.05f, 8f);
	}

	@Override
	public ResourceLocation getTexture() {
		return new ResourceLocation(ZirconMod.MOD_ID, "textures/entity/feather_wings.png");
	}

	@Override
	public boolean isValidRepairItem(ItemStack thisStack, ItemStack repairItem) {
		return repairItem.is(Items.PRISMARINE_CRYSTALS);
	}

}
