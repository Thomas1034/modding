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

		/*
		 */
		this.addBait(Items.MELON_SEEDS, 0.05, 0.5);
		this.addBait(Items.SUGAR_CANE, 0.05, 0.1);
		this.addBait(Items.WHEAT_SEEDS, 0.1, 0.5);
		this.addBait(Items.BEETROOT_SEEDS, 0.15, 0.5);
		this.addBait(Items.ROTTEN_FLESH, 0.2, 0.75);
		this.addBait(Items.BROWN_MUSHROOM, 0.2, 0.5);
		this.addBait(Items.RED_MUSHROOM, 0.2, 0.5);
		this.addBait(Items.SUGAR, 0.4, 0.8);
		this.addBait(Items.WHEAT, 0.5, 0.5);
		this.addBait(Items.RABBIT, 0.5, 0.3);
		this.addBait(Items.CHICKEN, 0.55, 0.3);
		this.addBait(Items.MUTTON, 0.55, 0.3);
		this.addBait(Items.PORKCHOP, 0.6, 0.3);
		this.addBait(Items.BEEF, 0.55, 0.3);
		this.addBait(Items.COOKIE, 0.6, 0.8);
		this.addBait(Items.BREAD, 0.6, 0.3);
		this.addBait(Items.ENDER_PEARL, 0.6, 0.1);
		this.addBait(Items.PUMPKIN_PIE, 0.8, 0.2);
		this.addBait(Items.SPIDER_EYE, 0.8, 0.8);
		this.addBait(Items.COOKED_RABBIT, 0.8, 0.2);
		this.addBait(Items.COOKED_CHICKEN, 0.85, 0.2);
		this.addBait(Items.COOKED_MUTTON, 0.85, 0.2);
		this.addBait(Items.COOKED_PORKCHOP, 0.9, 0.2);
		this.addBait(Items.COOKED_BEEF, 0.85, 0.2);
		this.addBait(Items.ENDER_EYE, 0.9, 0.05);
		this.addBait(Items.GOLDEN_CARROT, 1.5, 0.2);
		this.addBait(Items.FERMENTED_SPIDER_EYE, 2.5, 0.5);

	}

	protected void addBait(Item item, double catchChance, double consumeChance) {
		this.addBait(ForgeRegistries.ITEMS.getKey(item).getPath() + "_bait", item, catchChance, consumeChance);
	}

	protected void addBait(String name, Item item, double catchChance, double consumeChance) {
		this.add(name).setData(item, catchChance, consumeChance);
	}

}
