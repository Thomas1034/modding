package com.thomas.verdant.datagen;

import com.thomas.verdant.Verdant;
import com.thomas.verdant.block.ModBlocks;
import com.thomas.verdant.growth.VerdantBlockTransformer;

import net.minecraft.data.PackOutput;

public class ModBlockTransformerProvider extends BlockTransformerProvider {

	public ModBlockTransformerProvider(PackOutput output) {
		super(output, Verdant.MOD_ID);
	}

	@Override
	protected void addTransformers() {

		ModBlocks.VERDANT_HEARTWOOD.addTransforms(this);
		ModBlocks.VERDANT.addTransforms(this);

		VerdantBlockTransformer.registerErosion(this.add("erosion"));
		VerdantBlockTransformer.registerErosionWet(this.add("erosion_wet"));
		VerdantBlockTransformer.registerGrowGrasses(this.add("grow_grasses"));
		VerdantBlockTransformer.registerRemoveGrasses(this.add("remove_grasses"));
		VerdantBlockTransformer.registerRoots(this.add("roots"));
		VerdantBlockTransformer.registerHydration(this.add("hydrate"));
		VerdantBlockTransformer.registerDehydration(this.add("dehydrate"));
		VerdantBlockTransformer.registerHoeing(this.add("hoeing"));
		VerdantBlockTransformer.registerToxicAsh(this.add("toxic_ash"));

	}

}
