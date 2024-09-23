package com.thomas.turbulent.datagen;

import com.thomas.turbulent.Turbulent;

import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.GlobalLootModifierProvider;

public class ModGlobalLootModifiersProvider extends GlobalLootModifierProvider {
	public ModGlobalLootModifiersProvider(PackOutput output) {
		super(output, Turbulent.MOD_ID);
	}

	@Override
	protected void start() {
	}
}