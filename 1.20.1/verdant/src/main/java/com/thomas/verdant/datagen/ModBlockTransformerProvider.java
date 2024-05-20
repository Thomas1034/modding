package com.thomas.verdant.datagen;

import com.thomas.verdant.Verdant;
import com.thomas.verdant.block.VerdantTransformationHandler;
import com.thomas.verdant.transforms.BlockTransformerProvider;

import net.minecraft.data.PackOutput;

public class ModBlockTransformerProvider extends BlockTransformerProvider {

	public ModBlockTransformerProvider(PackOutput output) {
		super(output, Verdant.MOD_ID);
	}

	@Override
	protected void addTransformers() {
		
		VerdantTransformationHandler.registerErosion(this.add("erosion"));
		VerdantTransformationHandler.registerErosionWet(this.add("erosion_wet"));
		VerdantTransformationHandler.registerGrowGrasses(this.add("grow_grasses"));
		VerdantTransformationHandler.registerRemoveGrasses(this.add("remove_grasses"));
		VerdantTransformationHandler.registerRoots(this.add("roots"));
		VerdantTransformationHandler.registerHydration(this.add("hydrate"));
		VerdantTransformationHandler.registerDehydration(this.add("dehydrate"));
		VerdantTransformationHandler.registerHoeing(this.add("hoeing"));
		
	}

}
