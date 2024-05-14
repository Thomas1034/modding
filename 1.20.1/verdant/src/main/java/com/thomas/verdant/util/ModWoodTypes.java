package com.thomas.verdant.util;

import com.thomas.verdant.Verdant;

import net.minecraft.world.level.block.state.properties.WoodType;

public class ModWoodTypes {
	public static final WoodType VERDANT = WoodType
			.register(new WoodType(Verdant.MOD_ID + ":verdant", ModBlockSetType.VERDANT));
	public static final WoodType VERDANT_HEARTWOOD = WoodType
			.register(new WoodType(Verdant.MOD_ID + ":verdant_heartwood", ModBlockSetType.VERDANT_HEARTWOOD));
}
