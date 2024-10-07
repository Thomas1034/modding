package com.thomas.verdant.datagen;

import com.thomas.verdant.Verdant;
import com.thomas.verdant.util.itemtonumber.ItemToNumberMap;

import net.minecraft.data.PackOutput;

public class ModItemToNumberProvider extends DataParseableProvider<ItemToNumberMap> {

	public ModItemToNumberProvider(PackOutput output) {
		super(output, Verdant.MOD_ID, "item_to_number_maps", "Item to Number Maps", ItemToNumberMap::new);
	}

	@Override
	protected void addParseables() {

	}
}
