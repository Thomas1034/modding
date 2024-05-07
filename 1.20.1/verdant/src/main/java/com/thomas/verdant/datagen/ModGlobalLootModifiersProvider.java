package com.thomas.verdant.datagen;

import com.thomas.verdant.Verdant;

import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.GlobalLootModifierProvider;

public class ModGlobalLootModifiersProvider extends GlobalLootModifierProvider {
	public ModGlobalLootModifiersProvider(PackOutput output) {
		super(output, Verdant.MOD_ID);
	}

	@Override
	protected void start() {
	}
}