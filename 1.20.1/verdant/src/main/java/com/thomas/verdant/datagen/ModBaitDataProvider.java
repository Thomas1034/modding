package com.thomas.verdant.datagen;

import com.thomas.verdant.Verdant;
import com.thomas.verdant.util.baitdata.BaitData;

import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraftforge.registries.ForgeRegistries;

public class ModBaitDataProvider extends DataParseableProvider<BaitData> {

	public ModBaitDataProvider(PackOutput output) {
		super(output, Verdant.MOD_ID, "bait_data", "Bait Data", BaitData::new);
	}

	@Override
	protected void addParseables() {
		this.addBait(Items.WHEAT_SEEDS, 0.1, 0.2);
		
	}

	protected void addBait(Item item, double catchChance, double consumeChance) {
		this.addBait(ForgeRegistries.ITEMS.getKey(item).getPath() + "_bait", item, catchChance, consumeChance);
	}

	protected void addBait(String name, Item item, double catchChance, double consumeChance) {
		this.add(name).setData(item, catchChance, consumeChance);
	}

}
