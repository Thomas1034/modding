package com.thomas.turbulent.datagen;

import com.thomas.turbulent.Turbulent;

import net.minecraft.data.PackOutput;

public class ModBlockTransformerProvider extends BlockTransformerProvider {

	public ModBlockTransformerProvider(PackOutput output) {
		super(output, Turbulent.MOD_ID);
	}

	@Override
	protected void addTransformers() {

	}

}
